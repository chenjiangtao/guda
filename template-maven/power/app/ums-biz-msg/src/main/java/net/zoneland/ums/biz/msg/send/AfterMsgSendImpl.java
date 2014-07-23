/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.send;

import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.biz.msg.in.MsgInService;
import net.zoneland.ums.biz.msg.queue.QueueMessage;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.SerialNoHelper;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: AfterMsgSendImpl.java, v 0.1 2012-9-8 下午2:21:41 gag Exp $
 */
public class AfterMsgSendImpl implements AfterMsgSend {

    private final static Logger logger = Logger.getLogger(AfterMsgSendImpl.class);

    @Autowired
    private SocketProcess       sendMsgProcess;

    @Autowired
    private MsgInService        msgInService;

    /**
     * @see net.zoneland.ums.biz.msg.send.AfterMsgSend#afterSendSuccess(net.zoneland.ums.biz.msg.queue.QueueMessage)
     */
    public boolean afterSendSuccess(QueueMessage message) {
        if (logger.isInfoEnabled()) {
            logger.info("消息:" + message + "进入后置处理");
        }
        boolean res = false;
        if ((message.getAck() == 1 || message.getAck() == 3)) {
            if (StringRegUtils.isPhoneNumber(message.getReply())) {
                ServiceRequest sr = new ServiceRequest();
                sr.setRequestCode(CodeConstants.REQUEST_1002);
                sr.setAck(0);
                sr.setAppId(message.getAppId());
                sr.setSubAppId(message.getSubAppId());

                sr.setId(message.getAppSerialNo());
                sr.setMsg("成功发送给:" + buildMsg(message.getRecvIdArray()));
                sr.setUmsTo(message.getReply());
                sr.setMsgType(0);
                ProcessResult pr = sendMsgProcess.doProcess(sr);
                if (logger.isInfoEnabled()) {
                    logger.info("个人消息回执处理结果:" + pr + ",msg:" + message);
                }
                res = true;
            } else {
                List<String> ids = message.getRecvIdArray();
                Iterator<String> itIds = ids.iterator();
                while (itIds.hasNext()) {
                    UmsMsgIn umsMsgIn = new UmsMsgIn();
                    umsMsgIn.setBatchNo(DateHelper.getStrDateTime());
                    umsMsgIn.setSerialNo(String.valueOf(SerialNoHelper.nextSerial()));
                    umsMsgIn.setRetCode("1001");
                    umsMsgIn.setStatus("0");
                    umsMsgIn.setAppId(message.getAppId());
                    umsMsgIn.setAppSerialNo(message.getAppSerialNo());
                    umsMsgIn.setMediaId(message.getMediaId());
                    umsMsgIn.setSendId(message.getSendId());
                    umsMsgIn.setRecvId(itIds.next());
                    umsMsgIn.setContent("回执:成功发送");
                    umsMsgIn.setAck(1);
                    umsMsgIn.setMsgType(message.getContentType());
                    umsMsgIn.setReply(message.getReply());
                    umsMsgIn.setSubApp(message.getSubAppId());
                    umsMsgIn.setDocount(0);
                    msgInService.saveMsgIn(umsMsgIn);
                }
                if (logger.isInfoEnabled()) {
                    logger.info("应用消息回执处理成功:msg:" + message);
                }
                res = true;
            }
        }
        return res;
    }

    public static String buildMsg(List<String> abc) {
        if (abc == null) {
            return null;
        }
        if (abc.size() < 6) {
            return abc.toString();
        } else {
            return abc.subList(0, 5) + "等共" + abc.size() + "个手机号";
        }
    }

}
