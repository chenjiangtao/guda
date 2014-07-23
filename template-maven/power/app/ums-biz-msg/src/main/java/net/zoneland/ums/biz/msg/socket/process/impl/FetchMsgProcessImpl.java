/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import net.zoneland.ums.biz.msg.in.LockAppService;
import net.zoneland.ums.biz.msg.in.MsgInService;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: FetchMsgImpl.java, v 0.1 2012-8-24 下午5:15:12 gag Exp $
 */
public class FetchMsgProcessImpl implements SocketProcess {

    private static final Logger logger = Logger.getLogger(FetchMsgProcessImpl.class);

    @Autowired
    private MsgInService        msgInService;

    @Autowired
    private LockAppService      lockAppService;

    /** 
     * @see net.zoneland.ums.service.process.SocketProcess#doProcess(net.zoneland.ums.service.ServiceRequest)
     */
    public ProcessResult doProcess(ServiceRequest serviceRequest) {
        try {
            String returnMsg = "";
            //socket请求只会取一条数据
            List<UmsMsgIn> umsIns = Collections.emptyList();
            boolean lockResult = false;
            try {
                lockResult = lockAppService.lockApp(serviceRequest.getAppId());
                if (lockResult) {
                    logger.info("lock app success.appid:" + serviceRequest.getAppId());
                    umsIns = msgInService.fetchMsgIn(serviceRequest.getAppId(),
                        serviceRequest.getSubAppId(), MsgInStatusEnum.send.getValue(), 1);
                } else {
                    logger.warn("lock app fail.appid:" + serviceRequest.getAppId());
                }

            } catch (Exception e) {
                logger.error("fetch msgIn error .appid:" + serviceRequest.getAppId(), e);
            } finally {
                if (lockResult) {
                    lockAppService.releaseApp(serviceRequest.getAppId());
                }
                logger.info("release app success.appid:" + serviceRequest.getAppId());
            }
            if (umsIns.size() == 0) {
                return new ProcessResult(true, "1162");
            }
            if ("1004".equals(serviceRequest.getRequestCode())) {
                returnMsg = buildMsgIn(umsIns.get(0), 160);
            } else if ("3004".equals(serviceRequest.getRequestCode())) {
                returnMsg = buildMsgIn(umsIns.get(0), 280);
            } else if ("3012".equals(serviceRequest.getRequestCode())) {
                returnMsg = build3012MsgIn(umsIns.get(0));
            } else {
                returnMsg = "未知请求代码";
            }

            return new ProcessResult(true, returnMsg);

        } catch (Exception e) {
            logger.error("获取消息错误", e);
            return new ProcessResult(true, "1092");
        }

    }

    private String build3012MsgIn(UmsMsgIn umsIn) {
        StringBuffer sb = new StringBuffer();
        if (umsIn != null) {

            sb.append("200100").append(getFixedString(umsIn.getBatchNo(), 14));
            sb.append(getFixedString(umsIn.getSerialNo(), 8));
            sb.append(getFixedString(umsIn.getRetCode(), 4));
            sb.append(getFixedString(umsIn.getAppSerialNo(), 35));
            sb.append(Integer.toString(umsIn.getAck()));
            sb.append(getFixedString(umsIn.getRecvId(), 25));
            sb.append(getFixedString(umsIn.getSendId(), 25));
            sb.append(getFixedString(DateHelper.getStrDateTime(umsIn.getGmtCreated()), 14))
                .append(getFixedString(Integer.toString(umsIn.getMsgType()), 3))
                .append(getFixedString(umsIn.getSubApp(), 3));

            sb.append(umsIn.getContent());
        } else {
            sb.append("1162");
        }
        return sb.toString();
    }

    public String buildMsgIn(UmsMsgIn umsIn, int msgLength) {
        StringBuilder sb = new StringBuilder(180);
        if (umsIn == null) {
            sb.append("1162").append("没有待接收信息");
        } else {
            sb.append("200100").append(getFixedString(umsIn.getBatchNo(), 14))
                .append(getFixedString(umsIn.getSerialNo(), 8))
                .append(getFixedString(umsIn.getRetCode(), 4))
                .append(getFixedString(umsIn.getAppSerialNo(), 35))
                .append(Integer.toString(umsIn.getAck()))
                .append(getFixedString(umsIn.getRecvId(), 15))
                .append(getFixedString(umsIn.getSendId(), 15))
                .append(getFixedString(DateHelper.getStrDateTime(umsIn.getGmtCreated()), 14))
                .append(getFixedString(umsIn.getContent(), msgLength));
        }
        return sb.toString();
    }

    public static String getFixedString(String msg, int len) {

        if (msg == null) {
            return appendWhitespace(" ", len);
        }
        StringBuilder sb = new StringBuilder(msg);

        int length = 0;
        try {
            length = msg.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            length = msg.getBytes().length;
        }
        if (length > len) {
            return msg.substring(0, len);
        }
        for (int i = length + 1; i <= len; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public static String appendWhitespace(String s, int count) {
        if (count <= 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder(s.length() * count);
        for (int i = 0; i < count; i++) {
            buf.append(s);
        }
        return buf.toString();
    }

    public void setMsgInService(MsgInService msgInService) {
        this.msgInService = msgInService;
    }

}
