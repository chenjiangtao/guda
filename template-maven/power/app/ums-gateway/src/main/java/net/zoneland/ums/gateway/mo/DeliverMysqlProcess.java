/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.mo;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.mysql.dal.FilterMessageMapper;
import net.zoneland.ums.common.mysql.dal.InErrorMapper;
import net.zoneland.ums.common.mysql.dal.InReadyMapper;
import net.zoneland.ums.common.mysql.dal.OutReplyMapper;
import net.zoneland.ums.common.mysql.dal.RecvidAppMapper;
import net.zoneland.ums.common.mysql.dataobject.FilterMessage;
import net.zoneland.ums.common.mysql.dataobject.InError;
import net.zoneland.ums.common.mysql.dataobject.InReady;
import net.zoneland.ums.common.mysql.dataobject.OutReply;
import net.zoneland.ums.common.util.StringRegUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: DeliverMysqlProcess.java, v 0.1 2012-11-15 下午3:57:33 gag Exp $
 */
public class DeliverMysqlProcess implements Process {

    private final Logger        logger = LoggerFactory.getLogger("UMS-GATEWAY-MO");

    @Autowired
    private RecvidAppMapper     recvidAppMapper;

    @Autowired
    private InErrorMapper       inErrorMapper;

    @Autowired
    private OutReplyMapper      outReplyMapper;

    @Autowired
    private FilterMessageMapper filterMessageMapper;

    @Autowired
    private InReadyMapper       inRedayMapper;
    //通过参数配置
    private String              mediaId;

    @Autowired
    private SocketProcess       sendMsgProcess;

    public void saveDeliverMsg(String spNumber, String sendNumber, String recvNumber,
                               String recvMsg, String mediaId, int data_code) {
        mediaId = this.mediaId;
        String content = recvMsg != null ? StringUtils.trim(recvMsg) : null;
        String src_appID = null;
        String src_appSerialNO = null;
        recvNumber = StringUtils.trim(recvNumber);
        String src_sendID = recvNumber;
        String src_reply = null;
        String subApp = null;
        String retCode = null;
        int ack = 0;
        String replyNO = this.getReplyNumber(recvNumber, spNumber);
        if (replyNO != null && replyNO.startsWith("0") && replyNO.length() != 7) {
            if (data_code == 4 || data_code == 21) {
                try {
                    src_appID = recvidAppMapper.selectAppId(sendNumber);
                } catch (Exception e) {
                    logger.info("查询发送的APPID出错或者查出为空", e);
                    src_appID = null;
                }
                if (src_appID == null) {
                    try {
                        insertIn_Error(sendNumber, recvNumber, recvMsg, mediaId, data_code);
                    } catch (Exception e) {
                        logger.info("插入上行错误表出错", e);
                    }
                    return;
                }
            } else {
                ack = 1;
                try {
                    OutReply outReply = outReplyMapper.selectByReplyDes(replyNO);
                    if (outReply.getRecvid().endsWith(sendNumber)) {
                        src_appID = outReply.getAppid();
                        src_appSerialNO = outReply.getAppserialno();
                        src_sendID = outReply.getSendid();
                        src_reply = outReply.getReply();
                        subApp = outReply.getSubapp();
                        ack = outReply.getAck() == null ? 0 : outReply.getAck();
                        retCode = "1002";
                    }

                } catch (Exception e) {
                    logger.info("查询回复号出错或者返回结果为空", e);
                    src_appID = null;
                }
                if (src_appID == null) {
                    insertIn_Error(sendNumber, recvNumber, recvMsg, mediaId, data_code);
                    return;
                }

            }
        } else if (replyNO != null && replyNO.length() > 0) {
            //这里replyNO只能是一个appid
            String tempStr = replyNO;
            if (replyNO.length() > 4) {
                replyNO = tempStr.substring(0, 4);
                subApp = tempStr.substring(4, tempStr.length());
            }
            src_appID = replyNO;
        } else {
            try {
                FilterMessage filterMessage = filterMessageMapper.selectByContent(content);
                if (filterMessage != null) {
                    src_appID = filterMessage.getAppid();
                    src_sendID = recvNumber;
                }
            } catch (Exception e) {
            }
            logger.debug(src_appID);
        }
        int serialNo = 0;
        try {
            serialNo = Integer.valueOf(StringUtils.trim(SerialNO.getInstance().getSerial()));
        } catch (Exception e) {
            logger.error("数据转化错误", e);
        }
        String currentTime = Util.getCurrentTimeStr("yyyyMMddHHmmss");
        InReady inReady = new InReady();
        inReady.setBatchno(currentTime);
        inReady.setSerialno(serialNo);
        inReady.setRetcode(retCode);
        inReady.setErrmsg(null);
        inReady.setStatusflag(0);
        inReady.setAppid(src_appID);
        inReady.setAppserialno(src_appSerialNO);
        inReady.setMediaid(mediaId);
        inReady.setSendid(sendNumber);
        inReady.setRecvid(recvNumber);
        inReady.setSubmittime(new Date());
        inReady.setContent(content);
        inReady.setAck((byte) ack);
        inReady.setReply(src_reply);
        inReady.setMsgtype((byte) data_code);
        inReady.setSubapp(subApp);
        try {
            logger.info("插入上行表的信息：" + inReady);
            inRedayMapper.insert(inReady);
        } catch (Exception e) {
            logger.error("插入上行表出错", e);
        }
        if (src_reply != null && StringRegUtils.isPhoneNumber(src_reply) && content.length() > 0) {
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
            ProcessResult pr = sendMsgProcess.doProcess(sr);
            if (logger.isInfoEnabled()) {
                logger.info("transmit...收到上行短信,处理回复消息,结果:" + pr + "sendid:" + sendNumber
                            + ",recvNumber:" + recvNumber + ",recvMsg:" + recvMsg);
            }
            inRedayMapper.fillingInReady(currentTime, serialNo, "0");

        }

    }

    private String getReplyNumber(String recvNumber, String spNumber) {
        String replyNO = null;

        replyNO = this.resolveReplyNumber(recvNumber, spNumber);
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.resolveReplyNumber(recvNumber, "95598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.resolveReplyNumber(recvNumber, "1065595598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.resolveReplyNumber(recvNumber, "195598");
        if (replyNO != null) {
            return replyNO;
        }
        replyNO = this.resolveReplyNumber(recvNumber, "11895598");

        return replyNO;

    }

    private String resolveReplyNumber(String recvNumber, String prefix) {
        String replyNO = null;
        if (recvNumber.startsWith(prefix)) {
            int prefix_len = prefix.length();
            if (recvNumber.length() > prefix_len) {
                replyNO = recvNumber.substring(prefix_len, recvNumber.length());

                if (replyNO.startsWith("0")) {
                    //                    Res.log(1, replyNO);
                    //   replyNO = recvNumber.substring(prefix_len, recvNumber.length()).trim();
                    //                            replyFlag = true;
                } else {
                    //replyNO = recvNumber.substring(prefix_len, recvNumber.length()).trim();
                }
            }
        }
        return replyNO;
    }

    public static String getCurrentTimeStr(String format) {
        SimpleDateFormat current = new SimpleDateFormat(format);
        String currentTime = current.format(new Date());
        return currentTime;
    }

    private void insertIn_Error(String sendNumber, String recvNumber, String recvMsg,
                                String mediaId, int data_code) {

        String retCode = null;
        int serialNo = 0;
        try {
            serialNo = Integer.valueOf(StringUtils.trim(SerialNO.getInstance().getSerial()));
        } catch (Exception e) {
            logger.warn("数据转化错误", e);
        }
        String currentTime = Util.getCurrentTimeStr("yyyyMMddHHmmss");
        retCode = "1167";
        InError inError = new InError();
        inError.setBatchno(currentTime);
        inError.setSerialno(serialNo);
        inError.setSequenceno((byte) 0);
        inError.setRetcode(retCode);
        inError.setErrmsg("回复号错误");
        inError.setAppserialno(null);
        inError.setMediaid(mediaId);
        inError.setSendid(sendNumber);
        inError.setRecvid(recvNumber);
        inError.setSubmittime(new Date());
        inError.setContent(recvMsg);
        inError.setAck((byte) 0);
        inError.setReply(null);
        inError.setMsgtype((byte) data_code);
        inError.setSubapp(null);
        inError.setFinishtime(new Date());
        logger.info("插入上行错误表消息：" + inError);
        inErrorMapper.insert(inError);
    }

    /**
     * Getter method for property <tt>mediaId</tt>.
     * 
     * @return property value of mediaId
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * Setter method for property <tt>mediaId</tt>.
     * 
     * @param mediaId value to be assigned to property mediaId
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
