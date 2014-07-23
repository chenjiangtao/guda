/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.BlackListBiz;
import net.zoneland.ums.biz.config.admin.TelDescribeService;
import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.config.gateway.GatewayRuleService;
import net.zoneland.ums.biz.msg.in.OutReplyService;
import net.zoneland.ums.biz.msg.process.service.SaveMsgService;
import net.zoneland.ums.biz.msg.queue.QueueMessage;
import net.zoneland.ums.biz.msg.queue.QueueMsgConvertor;
import net.zoneland.ums.biz.msg.queue.QueueService;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgTemplateMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;
import net.zoneland.ums.common.util.Host;
import net.zoneland.ums.common.util.SerialNoHelper;
import net.zoneland.ums.common.util.enums.GateOutProvEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.StringHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author gag
 * @version $Id: SendMsgImpl.java, v 0.1 2012-8-22 下午3:51:13 gag Exp $
 */
public class SendMsgProcessImpl implements SocketProcess {

    private final Logger         logger          = LoggerFactory
                                                     .getLogger(SendMsgProcessImpl.class);

    public static final String   REPLY_RREFIX    = "1";

    public static final String   NO_REPLY_RREFIX = "2";

    @Autowired
    private TelDescribeService   telDescribeService;

    @Autowired
    private AppInfoService       appInfoService;

    @Autowired
    private GatewayRuleService   gatewayRuleService;

    @Autowired
    private SaveMsgService       saveMsgService;

    @Autowired
    private BlackListBiz         blackListBiz;

    @Autowired
    private QueueService         queueService;

    @Autowired
    private UmsMsgTemplateMapper umsMsgTemplateMapper;

    @Autowired
    private OutReplyService      outReplyService;

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    public ProcessResult doProcess(ServiceRequest serviceRequest) {
        List<String> failed = new ArrayList<String>();
        List<String> outOfProv = new ArrayList<String>();
        List<String> blackList = new ArrayList<String>();
        List<String> needSend = new ArrayList<String>();
        StringBuilder failedBuf = new StringBuilder();
        StringBuilder outOfProvBuf = new StringBuilder();
        StringBuilder blackListBuf = new StringBuilder();
        String[] recvId = StringHelper.splitRecvId(serviceRequest.getUmsTo());
        for (int i = 0; i < recvId.length; i++) {
            //如果为空且长度小于11位的话就认为是非法手机号
            if (!StringUtils.hasText(recvId[i]) || recvId[i].length() < 11) {
                failed.add(recvId[i]);
                failedBuf.append(recvId[i]).append(";");
                continue;
            }
            //如果是86开头的话就把他截掉前两位
            if (recvId[i].startsWith("86") && recvId[i].length() == 13) {
                recvId[i] = recvId[i].substring(2);
            }
            if (telDescribeService.isOutProvince(recvId[i])) {
                outOfProv.add(recvId[i]);
                outOfProvBuf.append(recvId[i]).append(";");
            } else {
                //检查是否在黑名单
                boolean res = blackListBiz.isBlackList(recvId[i], serviceRequest.getAppId());
                if (res) {
                    blackList.add(recvId[i]);
                    blackListBuf.append(recvId[i]).append(";");
                } else {
                    needSend.add(recvId[i]);
                }
            }
        }
        int outProvCount = outOfProv.size();
        UmsAppInfo umsAppInfo = umsAppInfoMapper.selectAppByAppId(serviceRequest.getAppId());
        if (umsAppInfo == null) {
            return new ProcessResult(false, CodeConstants.newFailure("应用不存在！"));
        }
        boolean res = saveMsgOut(needSend, serviceRequest, false);

        boolean outProv = GateOutProvEnum.can.getValue().equals(umsAppInfo.getIsOutProv());
        if (outProv && outProvCount > 0) {
            logger.info("应用" + serviceRequest.getAppId() + "发送外省号码数量:" + outProvCount);
            res = saveMsgOut(outOfProv, serviceRequest, true);
        }
        if (res) {
            logger.info("需要发送的消息：" + needSend.size());
            if (failed.size() == 0 && outOfProv.size() == 0 && needSend.size() > 0) {
                logger.info("需要发送的消息outOfProv：" + outOfProv.size());
                return new ProcessResult(true, CodeConstants.SUCCESS);
            }

            String retMsg = String.format("1088-发送给%d个手机成功", needSend.size() + outProvCount
                                                             - outOfProv.size());

            if (!outProv) {
                if (outOfProv.size() > 0) {
                    retMsg += String.format("；%d个[%s]为浙江省外号码，发送失败", outOfProv.size(),
                        outOfProvBuf.toString());
                    saveErrorMsgOut(outOfProv, serviceRequest, MsgInfoStatusEnum.error.getValue(),
                        "接收方为浙江省外号码");
                }
            }
            if (failed.size() > 0) {
                saveErrorMsgOut(failed, serviceRequest, MsgInfoStatusEnum.error.getValue(),
                    "接收方号码不正确");
                retMsg += String.format("；%d个[%s]为非法号码，发送失败", failed.size(), failedBuf.toString());
                if (needSend.size() + outProvCount - outOfProv.size() == 0) {
                    return new ProcessResult(false, retMsg);
                }
            }
            if (blackList.size() > 0) {
                retMsg += String.format("；%d个[%s]拒绝接收消息，不发送", blackList.size(),
                    blackListBuf.toString());
                saveErrorMsgOut(outOfProv, serviceRequest, MsgInfoStatusEnum.refuse.getValue(),
                    "接收方拒绝接收消息");
                if (needSend.size() + outProvCount - outOfProv.size() == 0) {
                    return new ProcessResult(false, retMsg);
                }
            }
            return new ProcessResult(true, retMsg);
        } else {
            return new ProcessResult(false, CodeConstants.newFailure("接收方为外省号码或者不是手机号，无法发送"));
        }
    }

    private void saveErrorMsgOut(List<String> recvIds, ServiceRequest sr, String status,
                                 String errorMsg) {
        Iterator<String> it = recvIds.iterator();
        List<UmsMsgOut> outs = new ArrayList<UmsMsgOut>(recvIds.size());
        Date date = new Date();
        while (it.hasNext()) {
            UmsMsgOut out = new UmsMsgOut();
            out.setId(UUID.randomUUID().toString());
            String recvId = it.next();
            out.setBatchNo(DateHelper.getStrDateTime());
            out.setSerialNo(String.valueOf(SerialNoHelper.nextSerial()));
            out.setAppId(sr.getAppId());
            out.setRecvId(recvId);
            out.setContent(sr.getMsg());
            out.setGmtCreated(date);
            out.setGmtModified(date);
            out.setStatus(status);
            out.setErrorMsg(errorMsg);
            int type = getMsgType(sr.getMsgType());
            out.setMsgType(type);
            out.setAck(sr.getAck());
            out.setTemplateId(sr.getTemplateId());
            out.setCreateUser(sr.getCreateUser());
            out.setBizName(sr.getBizName());
            out.setBizType(sr.getBizType());
            out.setOrgNo(sr.getOrgNo());
            out.setFlowNo(sr.getFlowNo());
            String host = null;
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                logger.error("获取IP错误", e);
                host = "localhost";
            }
            out.setHost(host);
            outs.add(out);
        }
        try {
            saveMsgService.batchSaveMsgOut(outs);
        } catch (Exception e) {
            logger.error("保存消息失败", e);
        }
    }

    private boolean saveMsgOut(List<String> needSend, ServiceRequest sr, boolean isOutProv) {
        if (needSend == null || needSend.size() == 0) {
            return true;
        }
        List<String> newNeedSend = new ArrayList<String>(needSend.size());
        newNeedSend.addAll(needSend);
        List<UmsMsgOut> outs = new ArrayList<UmsMsgOut>(needSend.size());
        List<UmsMsgOut> waitOuts = new ArrayList<UmsMsgOut>(needSend.size());
        List<UmsMsgOut> errorOuts = new ArrayList<UmsMsgOut>(needSend.size());
        List<String> ids = new ArrayList<String>();
        List<String> recvs = new ArrayList<String>();
        Iterator<String> it = newNeedSend.iterator();
        String groupSerial = null;
        if (needSend.size() > 1) {
            groupSerial = UUID.randomUUID().toString();
        }
        Date date = new Date();
        int priority = appInfoService.findPriority(sr.getAppId(), sr.getSubAppId());
        int fee = 2;
        int feeType = 1;
        UmsAppInfo uai = appInfoService.findByAppId(sr.getAppId());
        if (uai != null && uai.getFee() != null && uai.getFeeType() != null) {
            fee = uai.getFee();
            feeType = uai.getFeeType();
        }
        //短信模版处理
        String bizName = sr.getBizName();
        String bizType = sr.getBizType();
        Date sendDate = sr.getSendDate();
        Date validTime = getValidTime(sendDate, sr.getValidTime());
        if (StringUtils.hasText(sr.getTemplateId())) {
            UmsMsgTemplate template = umsMsgTemplateMapper.findByTempId(sr.getTemplateId());
            if (template == null) {
                logger.warn("无法找到对应的模版:[" + sr.getTemplateId() + "]");
            } else {
                if (StringUtils.hasText(template.getBizName())) {
                    bizName = StringUtils.trimWhitespace(template.getBizName());
                }
                if (StringUtils.hasText(template.getBizType())) {
                    bizType = StringUtils.trimWhitespace(template.getBizType());
                }
                if (DateHelper.withIn(template.getStartTime(), template.getEndTime())) {
                    if (template.getPriority() != null) {
                        priority = template.getPriority();
                    }
                    Date temp = DateHelper.getSendDate(sendDate, template.getValidTimeScope());
                    if (temp != null) {
                        logger.info("重新设定的发送时间：*******************************" + temp);
                        sendDate = temp;
                    }
                } else {
                    logger.warn("模版不在有效期内，忽略该模版:[" + sr.getTemplateId() + "]");
                }
            }
        }
        String replyDes = null;
        while (it.hasNext()) {
            UmsMsgOut out = new UmsMsgOut();
            out.setId(UUID.randomUUID().toString());
            String recvId = it.next();
            out.setBatchNo(DateHelper.getStrDateTime());
            out.setSerialNo(String.valueOf(SerialNoHelper.nextSerial()));
            out.setAppId(sr.getAppId());
            out.setRecvId(recvId);
            out.setRetry(1);
            out.setPriority(priority);
            int type = getMsgType(sr.getMsgType());
            out.setMsgType(type);
            out.setContentMode(type);
            out.setBatchMode("0");
            out.setContent(sr.getMsg());
            out.setDocount(0);
            out.setAck(sr.getAck());
            out.setReply(sr.getReply());
            out.setGroupSerial(groupSerial);
            out.setSubApp(sr.getSubAppId());
            out.setSendTime(sendDate);
            out.setValidTime(validTime);
            out.setAppSerialNo(sr.getId());
            out.setGmtCreated(date);
            out.setGmtModified(date);
            out.setTemplateId(sr.getTemplateId());
            out.setBizName(bizName);
            out.setBizType(bizType);
            out.setOrgNo(sr.getOrgNo());// 单位编码
            out.setFlowNo(sr.getFlowNo());// 流程号
            out.setCreateUser(sr.getCreateUser());// 生成人员
            UmsGateWayInfo gateway = null;
            if (isOutProv) {
                gateway = gatewayRuleService.findOutProvGateway(recvId);
            } else {
                gateway = gatewayRuleService
                    .findGatewayWithRule(recvId, sr.getMsg(), sr.getAppId());
            }
            if (gateway != null) {
                out.setMediaId(gateway.getId());
                if (!StringUtils.hasText(replyDes) && StringUtils.hasText(out.getReply())) {
                    UmsOutReply umsOutReply = outReplyService.getOutReplyByMsg(out);
                    if (umsOutReply != null) {
                        replyDes = String.format("%06d", umsOutReply.getReplyDes());
                    }
                } else {
                    UmsOutReply umsOutReply = outReplyService.getOutAppReplyByMsg(out);
                    if (umsOutReply != null) {
                        replyDes = String.format("%06d", umsOutReply.getReplyDes());
                    }
                }
                out.setReplyDes(replyDes);
                fillSendId(out, replyDes, gateway.getSpNumber());
                out.setFeeType(feeType);
                out.setFee(fee);
                out.setHost(Host.getHost());
                if (uai != null) {
                    appendComments(out, uai.getSignName());
                }
                if (out.getSendTime() == null) {
                    out.setStatus(MsgInfoStatusEnum.ready.getValue());
                    ids.add(out.getId());
                    recvs.add(out.getRecvId());
                    outs.add(out);
                } else {
                    out.setStatus(MsgInfoStatusEnum.wait.getValue());
                    waitOuts.add(out);
                }
                if (isOutProv) {
                    needSend.remove(recvId);
                }

            } else {
                //无法找到通道,不发送
                out.setSendId("95598");
                out.setStatus(MsgInfoStatusEnum.error.getValue());
                out.setErrorMsg("无法找到通道,不发送");
                errorOuts.add(out);
            }

        }
        try {
            saveMsgService.batchSaveMsgOut(errorOuts);
            saveMsgService.batchSaveMsgOut(waitOuts);
            if (outs.size() > 0) {
                saveMsgService.batchSaveMsgOut(outs);
                Collection<QueueMessage> msgs = QueueMsgConvertor.groupBySendIdMediaId(outs);
                queueService.offerMessageListNoUpdate(msgs);
                return true;

            }
            if (waitOuts.size() > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("保存消息出错！", e);
            return false;
        }

    }

    /**
     * 有回复号的话，95598+0+回复号，没有的话+appId
     *
     * @param out
     * @param replyDes
     * @param spNumber
     */
    public static void fillSendId(UmsMsgOut out, String replyDes, String spNumber) {
        StringBuilder buf = new StringBuilder();
        buf.append(spNumber);
        if (StringUtils.hasText(replyDes)) {
            buf.append(UmsConstants.APP_SEND_ID_RESPONSE_PREFIX).append(replyDes);
        } else {
            if (out.getAppId() != null) {
                buf.append(StringUtils.trimAllWhitespace(out.getAppId()));
            }
            if (out.getSubApp() != null) {
                buf.append(StringUtils.trimAllWhitespace(out.getSubApp()));
            }
        }
        out.setSendId(buf.toString());
    }

    private Date getValidTime(Date sendTime, int validTime) {
        if (validTime <= 0) {
            return null;
        }
        Calendar startCalendar = Calendar.getInstance();
        if (sendTime == null) {
            startCalendar.setTime(new Date());
        } else {
            startCalendar.setTime(sendTime);
        }
        startCalendar.add(Calendar.MINUTE, validTime);
        return startCalendar.getTime();

    }

    public static boolean appendComments(UmsMsgOut out, String signName) {
        int msgType = out.getMsgType();
        if (StringUtils.hasText(signName) && msgType != 4 && msgType != 21 && msgType != 0) {
            out.setContent(out.getContent() + String.format("【%s】", signName));
        }

        return true;
    }

    public static int getMsgType(int type) {
        if (type == 21 || type == 4) { //PDU
            return type;
        } else if (type == 2) { //UCS2
            return 8;
        } else if (type == 1) { //英文
            return 0;
        } else { //中文
            return 15;
        }
    }

    public void setTelDescribeService(TelDescribeService telDescribeService) {
        this.telDescribeService = telDescribeService;
    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    public void setGatewayRuleService(GatewayRuleService gatewayRuleService) {
        this.gatewayRuleService = gatewayRuleService;
    }

    public void setSaveMsgService(SaveMsgService saveMsgService) {
        this.saveMsgService = saveMsgService;
    }

    public void setBlackListBiz(BlackListBiz blackListBiz) {
        this.blackListBiz = blackListBiz;
    }

    public void setQueueService(QueueService queueService) {
        this.queueService = queueService;
    }

}
