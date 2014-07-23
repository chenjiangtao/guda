/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.admin.BlackListBiz;
import net.zoneland.ums.biz.config.admin.TelDescribeService;
import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.config.gateway.GatewayRuleService;
import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.biz.msg.flow.FlowControl;
import net.zoneland.ums.biz.msg.in.OutReplyService;
import net.zoneland.ums.biz.msg.process.Checker;
import net.zoneland.ums.biz.msg.process.MessageProcess;
import net.zoneland.ums.biz.msg.process.service.MsgTempService;
import net.zoneland.ums.biz.msg.process.service.ResolveUserService;
import net.zoneland.ums.biz.msg.process.service.SaveMsgService;
import net.zoneland.ums.biz.msg.process.service.impl.ResolveUserServiceImpl;
import net.zoneland.ums.biz.msg.queue.QueueMessage;
import net.zoneland.ums.biz.msg.queue.QueueMsgConvertor;
import net.zoneland.ums.biz.msg.queue.QueueService;
import net.zoneland.ums.biz.user.UmsOrganizationBiz;
import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.SerialNoHelper;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.constants.LoginInfoConstants;
import net.zoneland.ums.common.util.enums.msg.IdentityEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * The Class MessageProcessImpl.
 *
 * @author wangyong
 * @version $Id: MessageProcessImpl.java, v 0.1 2012-8-13 下午6:10:46 wangyong Exp
 *          $
 */
public class MessageProcessImpl implements MessageProcess {

    /** The Constant logger. */
    private final static Logger logger          = Logger.getLogger(MessageProcessImpl.class);

    private final static int    PHONELENGTH     = 11;

    private final static int    PHONELENGTHOF86 = 13;

    /** The msg codec type. */
    private Integer             msgCodecType;

    /** The save msg service. */
    @Autowired
    private SaveMsgService      saveMsgService;

    /** The ums user info mapper. */
    @Autowired
    private UmsUserInfoMapper   umsUserInfoMapper;

    /** The ums contact mapper. */
    @Autowired
    private UmsContactMapper    umsContactMapper;

    /** The queue service. */
    @Autowired
    private QueueService        queueService;

    /** The ums msg out mapper. */
    @Autowired
    private UmsMsgOutMapper     umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper  umsMsgOutUcsMapper;

    /** The resolve user service. */
    @Autowired
    private ResolveUserService  resolveUserService;

    /** The flow control. */
    @Autowired
    private FlowControl         flowControl;

    /** The gateway rule service. */
    @Autowired
    private GatewayRuleService  gatewayRuleService;

    /** The keyword checker. */
    @Autowired
    private Checker             keywordChecker;

    /** The black list biz. */
    @Autowired
    private BlackListBiz        blackListBiz;

    /** The tel describe service. */
    @Autowired
    private TelDescribeService  telDescribeService;

    /** The app info service. */
    @Autowired
    private AppInfoService      appInfoService;

    /** The ums organization biz. */
    @Autowired
    private UmsOrganizationBiz  umsOrganizationBiz;

    @Autowired
    private OutReplyService     outReplyService;

    @Autowired
    private MsgTempService      msgTempService;

    /**
     * 解析消息，流量检查，外省号码检查，黑名单检查，保存消息，转化为队列消息，保存到队列。.
     *
     * @param primitiveMessage
     *            the primitive message
     * @return true, if successful
     * @see net.zoneland.ums.biz.msg.process.MessageProcess#process(net.zoneland.ums.biz.msg.PrimitiveMessage)
     */
    public boolean process(PrimitiveMessage primitiveMessage) {
        // 检查关键词
        if (!keywordChecker.doCheck(primitiveMessage)) {
            return false;
        }
        // 解析接收方
        Map<String, HashSet<String>> map = resolveUserService.getRecvIdList(primitiveMessage
            .getRecvId());
        HashSet<String> correctPhoneList = map.get("correctUserIdList");
        HashSet<String> errorPhoneList = map.get("errorPhoneList");// 存放不在用户，组织或群组中的错误的手机号
        HashSet<String> errorUserNameList = map.get("errorUserNameList");// 存放用户，组织或群组中手机号为空的用户名
        correctPhoneList = convert2Phone(correctPhoneList, errorUserNameList);
        return doProcess(primitiveMessage, correctPhoneList, errorPhoneList, errorUserNameList);
    }

    public boolean processIphone(PrimitiveMessage primitiveMessage) {
        if (!keywordChecker.doCheck(primitiveMessage)) {
            return false;
        }
        String[] recvId = primitiveMessage.getRecvId().split(ResolveUserServiceImpl.split_char);
        Set<String> set = new HashSet<String>(recvId.length * 2);
        for (int i = 0, len = recvId.length; i < len; ++i) {
            String rec = recvId[i];
            if (rec.length() == PHONELENGTH
                || (rec.length() == PHONELENGTHOF86 && rec.startsWith("86"))) {
                set.add(rec);
            }
        }
        return doProcess(primitiveMessage, set, null, null);
    }

    private boolean doProcess(PrimitiveMessage primitiveMessage, Set<String> correctPhoneList,
                              Set<String> errorPhoneList, HashSet<String> errorUserNameList) {
        if (correctPhoneList == null || correctPhoneList.size() == 0) {
            logger.warn("接收方号码不正确,msg:" + primitiveMessage);
            String errorMsg = "消息发送失败！";
            if (errorUserNameList != null && errorUserNameList.size() > 0) {
                errorMsg += "有" + errorUserNameList.size() + "个用户的手机号为空！";
            }
            if (errorPhoneList != null && errorPhoneList.size() > 0) {
                errorMsg += "有" + errorPhoneList.size() + "个手机号码是非法手机号码！";
            }
            primitiveMessage.setComments(errorMsg);
            return false;
        }
        // 流量检查
        boolean flow = flowCheck(primitiveMessage, correctPhoneList);
        if (!flow) {
            return false;
        }
        List<String> failed = new ArrayList<String>();
        List<String> outOfProv = new ArrayList<String>();
        List<String> blackList = new ArrayList<String>();
        List<String> needSend = new ArrayList<String>();
        StringBuilder failedBuf = new StringBuilder();
        StringBuilder outOfProvBuf = new StringBuilder();
        StringBuilder blackListBuf = new StringBuilder();
        StringBuilder errorUserNameBuf = new StringBuilder();
        Iterator<String> it = correctPhoneList.iterator();
        int outOfProvCount = 0;
        int failedCount = 0;
        int blackListCount = 0;
        int errorUserNameCount = 0;
        while (it.hasNext()) {
            String num = it.next();
            if (StringUtils.hasText(num) && num.length() > 7) {
                // 检查是不是外省号码
                if (telDescribeService.isOutProvince(num)) {
                    outOfProv.add(num);
                    outOfProvBuf.append(num).append(";");
                    ++outOfProvCount;
                    if (outOfProvCount % 4 == 0) {
                        outOfProvBuf.append("<br/>");
                    }
                    continue;
                }
            }
            // 检查是否在黑名单
            if (blackListBiz.isBlackList(num, UmsConstants.APP_ID)) {
                blackList.add(num);
                blackListBuf.append(num).append(";");
                ++blackListCount;
                if (blackListCount % 4 == 0) {
                    blackListBuf.append("<br/>");
                }
                continue;
            }
            // 可以发送的号码
            needSend.add(num);
        }
        if (errorPhoneList != null && errorPhoneList.size() > 0) {
            Iterator<String> errorIt = errorPhoneList.iterator();
            while (errorIt.hasNext()) {
                String num = errorIt.next();
                failed.add(num);
                failedBuf.append(num).append(";");
                ++failedCount;
                if (failedCount % 4 == 0) {
                    failedBuf.append("<br/>");
                }
                continue;
            }
        }
        if (errorUserNameList != null && errorUserNameList.size() > 0) {
            Iterator<String> errorUserNameIt = errorUserNameList.iterator();
            while (errorUserNameIt.hasNext()) {
                String num = errorUserNameIt.next();
                errorUserNameBuf.append(num).append(";");
                ++errorUserNameCount;
                if (errorUserNameCount % 4 == 0) {
                    errorUserNameBuf.append("<br/>");
                }
                continue;
            }
        }
        // 存储消息：如果立即发送
        int outProvCount = outOfProv.size();
        logger.info("out-prov-外省号码数量：" + outProvCount);
        if (primitiveMessage.getSendTime() == null) {
            saveMsgOut(needSend, primitiveMessage, false, false);
            saveMsgOut(outOfProv, primitiveMessage, false, true);
        } else {
            // 存储消息：如果定时发送
            saveMsgOut(needSend, primitiveMessage, true, false);
            saveMsgOut(outOfProv, primitiveMessage, true, true);
        }
        if (logger.isInfoEnabled()) {
            logger.info("out-prov-外省号码数量：" + outProvCount + "，未发送外省号码数量:" + outOfProv.size());
        }
        if (blackList.size() == 0 && failed.size() == 0
            && (errorUserNameList != null && errorUserNameList.size() == 0)
            && (needSend.size() + outProvCount - outOfProv.size() > 0)) {
            if (outProvCount - outOfProv.size() > 0) {
                String retMsg = String.format("消息发送成功！发送给%d个手机号成功！其中外省号[%s]个发送成功",
                    needSend.size() + outProvCount - outOfProv.size(),
                    outProvCount - outOfProv.size());
                primitiveMessage.setComments(retMsg);
            } else {
                String retMsg = String.format("消息发送成功！发送给%d个手机号成功！", needSend.size());
                primitiveMessage.setComments(retMsg);
            }
            return true;
        }
        boolean needSendTip = true;
        String retMsg = "消息发送成功！";
        if (needSend.size() > 0) {
            retMsg += String.format("发送给%d个手机号成功！<br/>",
                needSend.size() + outProvCount - outOfProv.size());
        } else {
            needSendTip = false;
            retMsg = "消息发送失败！";
        }
        if (errorUserNameList != null && errorUserNameList.size() > 0) {
            if (needSendTip == true) {
                if (errorUserNameBuf.toString().endsWith("<br/>")) {
                    retMsg += String.format(
                        "有%d个手机号码：用户[%s]的手机号为空",
                        errorUserNameList.size(),
                        errorUserNameBuf.toString().substring(0,
                            errorUserNameBuf.toString().lastIndexOf(";")));
                } else {
                    retMsg += String.format(
                        "有%d个手机号码：用户[%s]的手机号为空",
                        errorUserNameList.size(),
                        errorUserNameBuf.toString().substring(0,
                            errorUserNameBuf.toString().length() - 1));
                }
                retMsg += "，发送失败！<br/>";
            } else {
                if (errorUserNameList != null && errorUserNameList.size() > 0) {
                    retMsg += "有" + errorUserNameList.size() + "个用户手机号为空！";
                }
            }
        }
        if (outOfProv.size() > 0) {
            if (needSendTip == true) {
                if (outOfProvBuf.toString().endsWith("<br/>")) {
                    retMsg += String.format("有%d个手机号码：[%s]为浙江省外号码", outOfProv.size(), outOfProvBuf
                        .toString().substring(0, outOfProvBuf.toString().lastIndexOf(";")));
                } else {
                    retMsg += String.format("有%d个手机号码：[%s]为浙江省外号码", outOfProv.size(), outOfProvBuf
                        .toString().substring(0, outOfProvBuf.toString().length() - 1));
                }
                retMsg += "，发送失败！<br/>";
            } else {
                retMsg += "有" + outOfProv.size() + "个手机号码为浙江省外号码！";
            }

        }
        if (failed.size() > 0) {
            if (needSendTip == true) {
                if (failedBuf.toString().endsWith("<br/>")) {
                    retMsg += String.format("有%d个手机号码：[%s]为非法手机号码", failed.size(), failedBuf
                        .toString().substring(0, failedBuf.toString().lastIndexOf(";")));
                } else {
                    retMsg += String.format("有%d个手机号码：[%s]为非法手机号码", failed.size(), failedBuf
                        .toString().substring(0, failedBuf.toString().length() - 1));
                }
                retMsg += "，发送失败！<br/>";
            } else {
                retMsg += "有" + failed.size() + "个手机号码为非法手机号码！";
            }
            saveErrorMsgOut(failed, primitiveMessage, MsgInfoStatusEnum.error.getValue(),
                "接收方号码不正确");
        }
        if (blackList.size() > 0) {
            if (needSendTip == true) {
                if (blackListBuf.toString().endsWith("<br/>")) {
                    retMsg += String.format("有%d个手机号码：[%s]拒绝接收消息", blackList.size(), blackListBuf
                        .toString().substring(0, blackListBuf.toString().lastIndexOf(";")));
                } else {
                    retMsg += String.format("有%d个手机号码：[%s]拒绝接收消息", blackList.size(), blackListBuf
                        .toString().substring(0, blackListBuf.toString().length() - 1));
                }
                retMsg += "，发送失败！<br/>";
            } else {
                retMsg += "有" + blackList.size() + "个手机号码拒绝接收消息！";
            }
            saveErrorMsgOut(blackList, primitiveMessage, MsgInfoStatusEnum.refuse.getValue(),
                "接收方拒绝接收消息");
        }
        primitiveMessage.setComments(retMsg);
        if (needSend.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Save msg out. 保存消息，并放到队列。
     *
     * @param needSend
     *            the need send
     * @param primitiveMessage
     *            the primitive message
     * @param atTime
     *            the at time
     * @return true, if successful
     */
    private boolean saveMsgOut(List<String> needSend, PrimitiveMessage primitiveMessage,
                               boolean atTime, boolean isOutProv) {
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
        String replyDes = null;
        String phone = SecurityContextHolder.getContext().getPrincipal().getUserPhone();
        while (it.hasNext()) {
            UmsMsgOut out = new UmsMsgOut();
            out.setId(UUID.randomUUID().toString());
            String recvId = it.next();
            out.setUserId(SecurityContextHolder.getContext().getPrincipal().getUserId());
            out.setBatchNo(DateHelper.getStrDateTime());
            out.setSerialNo(String.valueOf(SerialNoHelper.nextSerial()));
            out.setAppId(UmsConstants.APP_ID);
            out.setRecvId(recvId);
            out.setRetry(1);
            out.setDocount(0);
            out.setMsgType(msgCodecType);
            out.setContentMode(msgCodecType);
            // out.setContentMode(sr.getMsgType());
            out.setBatchMode("0");
            out.setContent(primitiveMessage.getContent());
            // out.setSetTime(sr.get)
            out.setAck(primitiveMessage.getAck());
            out.setGroupSerial(groupSerial);
            out.setValidTime(primitiveMessage.getValidTime());
            out.setGmtCreated(date);
            out.setGmtModified(date);
            out.setReply(phone);
            out.setOrgNo(UmsConstants.AREACODE);
            out.setMobileStatus("0");
            if (out.getSendTime() == null) {
                out.setStatus(MsgInfoStatusEnum.ready.getValue());
            } else {
                out.setStatus(MsgInfoStatusEnum.wait.getValue());
            }
            out.setSendTime(primitiveMessage.getSendTime());
            out.setValidTime(primitiveMessage.getValidTime());
            int fee = 2;
            int feeType = 1;
            UmsAppInfo uai = appInfoService.findValidByAppId(UmsConstants.APP_ID);
            if (uai != null) {
                if (uai.getFee() != null) {
                    fee = uai.getFee();
                }
                if (uai.getFeeType() != null) {
                    feeType = uai.getFeeType();
                }
                out.setPriority(uai.getPriority());
            }

            out.setFeeType(feeType);
            out.setFee(fee);
            out.setIdentity(primitiveMessage.getIdentity());
            if (IdentityEnum.person.getValue().equals(primitiveMessage.getIdentity())) {
                appendComments(out,
                    SecurityContextHolder.getContext().getPrincipal().getUserName(), false);
            } else {
                Object orgName = SecurityContextHolder.getContext().getPrincipal()
                    .getAttrVal(LoginInfoConstants.ORG_NAME_ATTR);
                String name = getStr(orgName);
                if (!StringUtils.hasText(name)) {
                    name = umsOrganizationBiz.getOrgNameById(String.valueOf(SecurityContextHolder
                        .getContext().getPrincipal().getAttrVal(LoginInfoConstants.ORG_ID_ATTR)));
                }
                appendComments(out, name, true);
            }
            UmsGateWayInfo gateway = null;
            if (isOutProv) {
                logger.info("out prov:" + recvId);
                gateway = gatewayRuleService.findOutProvGateway(recvId);
            } else {
                gateway = gatewayRuleService.findGatewayWithRule(recvId,
                    primitiveMessage.getContent(), UmsConstants.APP_ID);
            }
            // 无法找到通道,不发送
            if (gateway == null) {
                out.setSendId("95598");
                out.setStatus(MsgInfoStatusEnum.error.getValue());
                out.setErrorMsg("无法找到通道,不发送");
                errorOuts.add(out);
                continue;
            }
            out.setMediaId(gateway.getId());
            if (!StringUtils.hasText(replyDes)) {
                UmsOutReply umsOutReply = outReplyService.getOutReplyByMsg(out);
                if (umsOutReply != null) {
                    replyDes = String.format("%06d", umsOutReply.getReplyDes());
                }
            }
            out.setReplyDes(replyDes);
            fillSendId(out, replyDes, gateway.getSpNumber());
            String host = null;
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                logger.error("获取IP错误", e);
                host = "localhost";
            }
            out.setHost(host);
            // 如果是定时发送
            if (atTime) {
                out.setStatus(MsgInfoStatusEnum.wait.getValue());
                waitOuts.add(out);
            } else {
                outs.add(out);
                ids.add(out.getId());
                recvs.add(out.getRecvId());
            }
            if (isOutProv) {
                needSend.remove(recvId);
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
            logger.error("保存消息失败！", e);
            return false;
        }
    }

    /**
     * 如果有回复号，肯定是需要回复的发送号码采用95598+0+回复号.</br>
     * 如果没有回复号就是95598
     * @param out
     * @param replyDes
     * @param spNumber
     */
    public static void fillSendId(UmsMsgOut out, String replyDes, String spNumber) {
        StringBuilder buf = new StringBuilder();
        buf.append(spNumber);
        if (replyDes != null) {
            buf.append(UmsConstants.UMS_SEND_ID_PREFIX).append(replyDes);
        } else {
            buf.append(out.getAppId());
        }
        out.setSendId(buf.toString());
    }

    /**
     * Append comments.
     *
     * @param out
     *            the out
     * @param signName
     *            the sign name
     */
    private void appendComments(UmsMsgOut out, String signName, boolean isOrg) {
        if (!StringUtils.hasText(signName)) {
            return;
        }
        if (isOrg) {
            out.setContent(out.getContent() + String.format("（%s）", signName));
        } else {
            out.setContent(out.getContent() + String.format("（%s）", signName));
        }
    }

    public static String getStr(Object str) {
        if (str == null) {
            return null;
        }
        return str.toString();
    }

    /**
     * Save error msg out.
     *
     * @param recvIds
     *            the recv ids
     * @param pm
     *            the pm
     * @param status
     *            the status
     * @param errorMsg
     *            the error msg
     */
    private void saveErrorMsgOut(List<String> recvIds, PrimitiveMessage pm, String status,
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
            out.setAppId(UmsConstants.APP_ID);
            out.setRecvId(recvId);
            out.setContent(pm.getContent());
            out.setGmtCreated(date);
            out.setGmtModified(date);
            out.setStatus(status);
            out.setErrorMsg(errorMsg);
            out.setMsgType(msgCodecType);
            out.setContentMode(msgCodecType);
            // out.setContentMode(sr.getMsgType());
            out.setBatchMode("0");
            out.setAck(pm.getAck());
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

    /**
     * Gets the gateway.
     *
     * @param umsMsgOut
     *            the ums msg out
     * @return the gateway
     * @see net.zoneland.ums.biz.msg.process.MessageProcess#getGateway(net.zoneland.ums.common.dal.dataobject.UmsMsgOut)
     */
    public UmsGateWayInfo getGateway(UmsMsgOut umsMsgOut) {
        String recvId = umsMsgOut.getRecvId();
        String appId = umsMsgOut.getAppId();
        String content = umsMsgOut.getContent();
        return gatewayRuleService.findGatewayWithRule(recvId, content, appId);

    }

    /**
     * 定时任务处理消息入队列.
     *
     * @param umsMsgOutList
     *            the ums msg out list
     * @return true, if successful
     */
    public boolean processMsg(List<UmsMsgOut> umsMsgOutList) {
        if (umsMsgOutList == null || umsMsgOutList.size() == 0) {
            return false;
        }
        List<UmsMsgOut> right = new ArrayList<UmsMsgOut>();
        Date currentDate = new Date();
        for (int i = 0, len = umsMsgOutList.size(); i < len; i++) {
            UmsMsgOut msgOut = umsMsgOutList.get(i);
            //进行模版验证，状态改变等
            changeStatus(right, currentDate, msgOut);
        }
        // 转化成QueueMessage列表
        List<QueueMessage> queueMessageList = new ArrayList<QueueMessage>();

        Collection<QueueMessage> messageList = QueueMsgConvertor.groupBySendIdMediaId(right);
        if (messageList != null && messageList.size() > 0) {
            queueMessageList.addAll(messageList);

        }
        // 将消息放入队列
        logger.info("总共有" + queueMessageList.size() + "条消息放人队列");
        if (queueMessageList.size() == 0) {
            return false;
        }
        boolean offer = queueService.offerMessageList(queueMessageList);
        if (offer) {
            if (logger.isInfoEnabled()) {
                logger.info("放入队列成功！");
            }
            return true;
        }
        return false;
    }

    /**
     * 把接收方转换为手机号,接收方手机号为空的转换为用户姓名
     *
     * @param correctPhoneList
     * @param errorUserNameList
     * @return
     */
    private HashSet<String> convert2Phone(HashSet<String> correctPhoneList,
                                          HashSet<String> errorUserNameList) {
        if (correctPhoneList == null || correctPhoneList.size() == 0) {
            return correctPhoneList;
        }
        Iterator<String> it = correctPhoneList.iterator();
        HashSet<String> list = new HashSet<String>();
        while (it.hasNext()) {
            String id = it.next();
            if (StringRegUtils.isPhoneNumber(id)) {
                list.add(id);
                continue;
            }
            UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(id);
            if (umsUserInfo != null) {
                if (StringRegUtils.isNumber(umsUserInfo.getPhone())) {
                    list.add(umsUserInfo.getPhone());
                } else {
                    errorUserNameList.add(umsUserInfo.getUserName());
                }
            } else {
                UmsContact umsContact = umsContactMapper.selectByPrimaryKey(id);
                if (umsContact != null) {
                    if (StringRegUtils.isNumber(umsContact.getPhone())) {
                        list.add(umsContact.getPhone());
                    } else {
                        errorUserNameList.add(umsContact.getUserName());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 比较消息是否过期 并且合并群发的消息.
     *
     * @param map
     *            the map
     * @param nowtime
     *            the nowtime
     * @param msgOut
     *            the msg out
     */
    private void changeStatus(List<UmsMsgOut> right, Date nowtime, UmsMsgOut msgOut) {
        if (nowtime != null && msgOut.getValidTime() != null
            && DateHelper.compareDate(nowtime, msgOut.getValidTime()) > 0) {
            // 消息过期了
            msgOut.setStatus(MsgInfoStatusEnum.expire.getValue());
            msgOut.setErrorMsg(MsgInfoStatusEnum.expire.getDescription());
            msgOut.setGmtModified(new Date());
            if (msgOut.getMsgType() == 15) {
                umsMsgOutMapper.updateStatusById(msgOut);
            } else {
                umsMsgOutUcsMapper.updateStatusById(msgOut);
            }
            return;
        }
        //进行模版验证，如果验证不通过的话就不在发送更新数据库状态
        boolean validateResult = msgTempService.validateMsgTemp(msgOut);
        if (logger.isInfoEnabled()) {
            logger.info("模版验证结果：" + validateResult + " 模版ID：" + msgOut.getTemplateId());
        }
        if (!validateResult) {
            //可能存在两种状态:一种是消息过期状态，一种是定时发送状态
            if (msgOut.getMsgType() == 15) {
                umsMsgOutMapper.updateStatusById(msgOut);
            } else {
                umsMsgOutUcsMapper.updateStatusById(msgOut);
            }
            return;
        }
        // 将recvId为userId的转成手机号
        String recvId = msgOut.getRecvId();
        if (!StringRegUtils.isPhoneNumber(recvId)) {
            recvId = umsUserInfoMapper.getPhoneById(recvId);
            msgOut.setRecvId(recvId);
        }
        if (StringRegUtils.isPhoneNumber(msgOut.getRecvId())) {
            right.add(msgOut);
        } else {
            msgOut.setStatus(MsgInfoStatusEnum.error.getValue());
            msgOut.setErrorMsg("接收方手机号码不正确!");
            if (msgOut.getMsgType() == 15) {
                umsMsgOutMapper.updateStatusById(msgOut);
            } else {
                umsMsgOutUcsMapper.updateStatusById(msgOut);
            }
        }
    }

    /**
     * 流量检查</br>
     *
     * @param primitiveMessage
     * @param correctPhoneSet
     * @return
     */
    private boolean flowCheck(PrimitiveMessage primitiveMessage, Set<String> correctPhoneSet) {
        int flow = 0;
        if (primitiveMessage.getSendTime() == null) {
            flow = flowControl.checkFlow(UmsConstants.APP_ID, correctPhoneSet.size());
        } else {
            flow = flowControl.checkFlowWithSendTime(UmsConstants.APP_ID, correctPhoneSet.size(),
                primitiveMessage.getSendTime());
        }
        if (flow > 0) {
            String result = String.valueOf(flow).substring(0, 1);
            flow = Integer.valueOf(String.valueOf(flow).substring(1));
            if ("1".equalsIgnoreCase(result)) {
                primitiveMessage.setComments("日流量超过阀值,超过数量:" + flow);
            } else if ("2".equalsIgnoreCase(result)) {
                primitiveMessage.setComments("月流量超过阀值,超过数量:" + flow);
            } else {
                primitiveMessage.setComments("流量超过阀值");
            }

            return false;
        }
        return true;
    }

    /**
     * Sets the save msg service.
     *
     * @param saveMsgService
     *            the new save msg service
     */
    public void setSaveMsgService(SaveMsgService saveMsgService) {
        this.saveMsgService = saveMsgService;
    }

    /**
     * Sets the ums user info mapper.
     *
     * @param umsUserInfoMapper
     *            the new ums user info mapper
     */
    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    /**
     * Sets the queue service.
     *
     * @param queueService
     *            the new queue service
     */
    public void setQueueService(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * Sets the ums msg out mapper.
     *
     * @param umsMsgOutMapper
     *            the new ums msg out mapper
     */
    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

    /**
     * Sets the resolve user service.
     *
     * @param resolveUserService
     *            the new resolve user service
     */
    public void setResolveUserService(ResolveUserService resolveUserService) {
        this.resolveUserService = resolveUserService;
    }

    /**
     * Sets the flow control.
     *
     * @param flowControl
     *            the new flow control
     */
    public void setFlowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
    }

    /**
     * Sets the gateway rule service.
     *
     * @param gatewayRuleService
     *            the new gateway rule service
     */
    public void setGatewayRuleService(GatewayRuleService gatewayRuleService) {
        this.gatewayRuleService = gatewayRuleService;
    }

    /**
     * Sets the keyword checker.
     *
     * @param keywordChecker
     *            the new keyword checker
     */
    public void setKeywordChecker(Checker keywordChecker) {
        this.keywordChecker = keywordChecker;
    }

    /**
     * Sets the black list biz.
     *
     * @param blackListBiz
     *            the new black list biz
     */
    public void setBlackListBiz(BlackListBiz blackListBiz) {
        this.blackListBiz = blackListBiz;
    }

    /**
     * Sets the tel describe service.
     *
     * @param telDescribeService
     *            the new tel describe service
     */
    public void setTelDescribeService(TelDescribeService telDescribeService) {
        this.telDescribeService = telDescribeService;
    }

    /**
     * Sets the app info service.
     *
     * @param appInfoService
     *            the new app info service
     */
    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    /**
     * Sets the ums organization biz.
     *
     * @param umsOrganizationBiz
     *            the new ums organization biz
     */
    public void setUmsOrganizationBiz(UmsOrganizationBiz umsOrganizationBiz) {
        this.umsOrganizationBiz = umsOrganizationBiz;
    }

    /**
     * Sets the msg codec type.
     *
     * @param msgCodecType
     *            the new msg codec type
     */
    public void setMsgCodecType(Integer msgCodecType) {
        this.msgCodecType = msgCodecType;
    }

    public UmsContactMapper getUmsContactMapper() {
        return umsContactMapper;
    }

    public void setUmsContactMapper(UmsContactMapper umsContactMapper) {
        this.umsContactMapper = umsContactMapper;
    }
}
