/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.mo;

import java.util.Date;

import net.zoneland.ums.biz.config.admin.MsgInRuleBiz;
import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.msg.in.MsgInErrorService;
import net.zoneland.ums.biz.msg.in.MsgInService;
import net.zoneland.ums.biz.msg.in.OutReplyService;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.AdminProcess;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInRule;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;
import net.zoneland.ums.common.util.SerialNoHelper;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * 
 * @author gag
 * @version $Id: DeliverProcess.java, v 0.1 2012-8-29 上午10:08:38 gag Exp $
 */
public class DeliverProcess implements Process {

    private final static Logger logger = Logger.getLogger("UMS-GATEWAY-MO");

    @Autowired
    private OutReplyService     outReplyService;

    @Autowired
    private MsgInService        msgInService;

    @Autowired
    private SocketProcess       sendMsgProcess;

    @Autowired
    private SocketProcess       fetchMsgResponseProcess;

    @Autowired
    private MsgInErrorService   msgInErrorService;

    @Autowired
    private AdminProcess        adminProcess;

    @Autowired
    private MsgInRuleBiz        msgInRuleBiz;

    public void saveDeliverMsg(String spNumber, String sendNumber, String recvNumber,
                               String recvMsg, String mediaId, int data_code) {
        String content = recvMsg != null ? recvMsg.trim() : null;

        String src_appID = null;
        String src_appSerialNO = null;
        recvNumber = recvNumber.trim();

        String src_reply = null;
        String subApp = null;
        String retCode = null;
        sendNumber = getSendNumber(sendNumber);
        int ack = 0;
        try {
            String replyNO = getReplyNumber(spNumber, recvNumber);
            if (replyNO != null && (replyNO.startsWith(UmsConstants.UMS_SEND_ID_PREFIX))
                && replyNO.length() == 7) {
                if (data_code == 4 || data_code == 21) {
                    //UCS2数据通讯，不应该发送到955980*这样的号码
                    logger.info("收到的上行短信编码不对应，保存到上行错误表sendid:" + sendNumber + ",recvNumber:"
                                + recvNumber + ",recvMsg:" + recvMsg);
                    msgInErrorService.saveMsgInError(sendNumber, recvNumber, recvMsg, mediaId,
                        data_code);
                    return;

                } else {
                    //回复到0开始的号码是一个正常的回复
                    replyNO = replyNO.substring(1);
                    ack = 2;
                    UmsOutReply outReply = null;
                    //个人回复的消息0,1开头
                    try {
                        if (replyNO.startsWith("0") || replyNO.startsWith("1")) {
                            outReply = outReplyService.findByReplyNum(replyNO);
                        } else {
                            outReply = outReplyService.findByAppReplyNum(replyNO);
                        }
                    } catch (EmptyResultDataAccessException e) {
                        src_appID = null;
                    } catch (IncorrectResultSizeDataAccessException e) {
                        src_appID = null;
                    }
                    //发给A的信息只能A可以回复，避免信息乱串
                    if (outReply != null) {
                        src_appID = outReply.getAppId();
                        src_appSerialNO = String.valueOf(outReply.getAppSerialNo());
                        //src_sendID = outReply.getSendId();
                        src_reply = outReply.getReply();
                        subApp = outReply.getSubApp();
                        ack = outReply.getAck();
                        retCode = "1002";
                    }
                    if (src_appID == null) {
                        logger.info("收到的上行短信有误，找不到对应的应用,保存到上行错误表sendid:" + sendNumber
                                    + ",recvNumber:" + recvNumber + ",recvMsg:" + recvMsg);
                        msgInErrorService.saveMsgInError(sendNumber, recvNumber, recvMsg, mediaId,
                            data_code);
                        return;
                    }
                }

            } else if (replyNO != null) {
                //这里replyNO只能是一个appid
                String tempStr = replyNO;
                if (replyNO.length() > 4) {
                    replyNO = tempStr.substring(0, 4);
                    subApp = tempStr.substring(4, tempStr.length());
                }
                src_appID = replyNO;
            } else {
                // 直接回复到了95598的消息
                logger.info("收到上行短信,直接回复95598消息，sendid:" + sendNumber + ",recvNumber:" + recvNumber
                            + ",recvMsg:" + recvMsg);
                UmsMsgInRule rule = msgInRuleBiz.findRule(recvMsg);
                if (rule != null) {
                    src_appID = rule.getAppId();
                    subApp = rule.getSubAppId();
                }
                if (src_appID == null) {
                    msgInErrorService.saveMsgInError(sendNumber, recvNumber, recvMsg, mediaId,
                        data_code);
                    return;
                }
            }
            String batchNo = DateHelper.getStrDateTime();
            String serialNo = String.valueOf(SerialNoHelper.nextSerial());
            StringBuilder buf = new StringBuilder();
            buf.append("batchNo:").append(batchNo).append("serialNo:").append(serialNo)
                .append("src_appID:").append(src_appID).append("subApp:").append(subApp)
                .append("src_appSerialNO:").append(src_appSerialNO).append("mediaId:")
                .append(mediaId).append("sendNumber:").append(sendNumber).append("recvNumber:")
                .append(recvNumber).append("content:").append(content).append("ack:").append(ack)
                .append("src_reply:").append(src_reply).append("data_code:").append(data_code)
                .append("retCode:").append(retCode);
            if (logger.isInfoEnabled()) {
                logger.info("保存上行数据:" + buf.toString());
            }
            //对于短信内容为空的也要保存下来，方便排错。
            if (content == null) {
                content = "";
            }
            msgInService.saveMsgIn(batchNo, serialNo, src_appID, subApp, src_appSerialNO, mediaId,
                sendNumber, recvNumber, content, ack, src_reply, data_code, retCode, null);

            if (src_reply != null && StringRegUtils.isPhoneNumber(src_reply)
                && content.length() > 0) {
                ServiceRequest sr = new ServiceRequest();
                sr.setRequestCode(CodeConstants.REQUEST_1002);
                sr.setAppId(src_appID);
                sr.setId(String.valueOf(src_appSerialNO));
                sr.setMsg(sendNumber + ":" + content);
                sr.setUmsTo(src_reply);
                sr.setAck(2);
                sr.setReply(sendNumber);
                sr.setMsgType(data_code);
                sr.setSendDate(new Date());
                logger.info("要发送的上行短信：" + sr);
                ProcessResult pr = sendMsgProcess.doProcess(sr);

                if (logger.isInfoEnabled()) {
                    logger.info("收到上行短信,处理回复消息,结果:" + pr);
                }
                processResponse(batchNo, serialNo);

            } else if (src_appID == null) {
                logger.warn("appid为空，管理消息");
                int result = adminProcess.parse(content, sendNumber, src_appID);
                switch (result) {
                    case AdminProcess.ID_QUITSYSTEM:
                        logger.info(String.format("系统因管理员%s的命令而退出运行。", sendNumber));
                        processResponse(batchNo, serialNo);
                        Thread.sleep(15 * 1000); // 15 seconds 

                        System.exit(0);
                        break;
                    case AdminProcess.ID_OK:
                        processResponse(batchNo, serialNo);
                        break;
                    default:
                }

            } else if (content.equals("1111") || content.equals("11111") || content.equals("0000")
                       || content.equals("00000")) {

                adminProcess.parse(content, sendNumber, src_appID);
                processResponse(batchNo, serialNo);
            }

        } catch (Exception e) {
            logger.error("处理上行短信错误", e);
        }

    }

    private String getSendNumber(String sendNumber) {
        if (sendNumber == null) {
            return null;
        }
        if (sendNumber.startsWith("86") && sendNumber.length() == 13) {
            return sendNumber.substring(2);
        }
        if (sendNumber.startsWith("+86") && sendNumber.length() == 14) {
            return sendNumber.substring(3);
        }
        return sendNumber;
    }

    private void processResponse(String batchNo, String serialNo) {
        ServiceRequest sr = new ServiceRequest();
        sr.setRequestCode(CodeConstants.REQUEST_1005);
        sr.setBatchNo(batchNo);
        sr.setSerialNo(serialNo);
        fetchMsgResponseProcess.doProcess(sr);
    }

    private String getReplyNumber(String spNumber, String recvNumber) {
        String replyNO = null;
        replyNO = this.getReply(recvNumber, spNumber);
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.getReply(recvNumber, "95598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.getReply(recvNumber, "1065920595598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.getReply(recvNumber, "1065595598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.getReply(recvNumber, "195598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.getReply(recvNumber, "106575253195");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.getReply(recvNumber, "11895598");

        return replyNO;
    }

    private String getReply(String recvNumber, String prefix) {
        String replyNO = null;
        if (recvNumber.startsWith(prefix)) {
            int prefix_len = prefix.length();
            if (recvNumber.length() > prefix_len) {
                replyNO = recvNumber.substring(prefix_len, recvNumber.length());
            }
        }
        return replyNO;
    }

}
