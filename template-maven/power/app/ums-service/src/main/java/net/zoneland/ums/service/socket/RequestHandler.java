/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.MsgHandler;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.SocketContext;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.common.util.FlowChecker;
import net.zoneland.ums.service.socket.codec.DecodeResult;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: RequestHandler.java, v 0.1 2012-8-10 上午9:39:11 gag Exp $
 */
public class RequestHandler extends IoHandlerAdapter implements InitializingBean {

    protected static final Logger stat                      = LoggerFactory.getLogger("STATISTIC");

    private static final Logger   logger                    = LoggerFactory
                                                                .getLogger(RequestHandler.class);

    public static final String    SERVICE_REQUEST_ATTR      = "SERVICE_REQUEST_ATTR";

    public static final String    SERVICE_REQUEST_LOGIN_APP = "SERVICE_REQUEST_LOGIN_APP";

    public static final String    SERVICE_REQUEST_FLAG      = "SERVICE_REQUEST_FLAG";

    @Autowired
    private MsgHandler            msgHandler;

    private final FlowChecker     sendMsgChecker            = new FlowChecker(10);

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionCreated(org.apache.mina.core.session.IoSession)
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info(session.getId() + "，session create");
        InetSocketAddress s = (InetSocketAddress) session.getRemoteAddress();
        logger.warn("sessioncreate 。。。" + s.getHostName() + "-" + s.getAddress());

    }

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(org.apache.mina.core.session.IoSession)
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {

        logger.info(session.getId() + "，session open");
        InetSocketAddress s = (InetSocketAddress) session.getRemoteAddress();

    }

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionClosed(org.apache.mina.core.session.IoSession)
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info(session.getId() + "，session close:" + session);
        //需要移除登录信息
        removeLoginInfo(session);
    }

    private void removeLoginInfo(IoSession session) {
        Object obj = session.getAttribute(SERVICE_REQUEST_ATTR);
        logger.info("sessionid" + session + ",remove:" + obj);
        if (obj != null) {
            ServiceRequest sr = (ServiceRequest) obj;
            SocketContext.removeLoginContext(sr.getAppId(), sr.getClientIp(), sr.getClientPort());
            session.removeAttribute(SERVICE_REQUEST_ATTR);
            session.removeAttribute(SERVICE_REQUEST_LOGIN_APP);
        }
    }

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionIdle(org.apache.mina.core.session.IoSession, org.apache.mina.core.session.IdleStatus)
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.info(session.getId() + "，session idle");
        session.close(false);

    }

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.info(session.getId() + "，session exception ", cause);

    }

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        if (message instanceof DecodeResult) {
            DecodeResult dr = (DecodeResult) message;
            if (dr.isSuccess()) {

                try {
                    ProcessResult pr = msgHandler.processMsg(dr.getServiceRequest(),
                        String.valueOf(session.getAttribute(SERVICE_REQUEST_LOGIN_APP)));
                    if (!CodeConstants.REQUEST_1005.equals(dr.getServiceRequest().getRequestCode())) {
                        session.write(pr.getMsg());
                    }

                    if (pr.isSuccess()
                        && CodeConstants.REQUEST_1001.equals(dr.getServiceRequest()
                            .getRequestCode())) {
                        session.setAttribute(SERVICE_REQUEST_ATTR, dr.getServiceRequest());
                        session.setAttribute(SERVICE_REQUEST_LOGIN_APP, dr.getServiceRequest()
                            .getAppId());
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info(session.getId() + "，请求:{" + dr.getServiceRequest() + "},处理结果：["
                                    + pr + "].");
                    }

                } catch (Exception e) {
                    logger.error(session.getId() + "，消息处理异常，request:[" + dr + "]", e);
                    if (!CodeConstants.REQUEST_1005.equals(dr.getServiceRequest().getRequestCode())) {
                        session.write(CodeConstants.newFailure("内部错误"));
                    }
                }

            } else {
                if (logger.isInfoEnabled()) {
                    logger.info(session.getId() + "，请求:{" + dr + "},解析失败。");
                }
                if (!CodeConstants.REQUEST_1005.equals(dr.getServiceRequest().getRequestCode())) {
                    session.write(CodeConstants.newFailure(dr.getMsg()));
                }
            }

        }

    }

    /** 
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageSent(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {

    }

    public void setMsgHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    class MonitorTask extends TimerTask {

        @Override
        public void run() {
            Set<String> set = new HashSet<String>(SocketContext.getMap().keySet());
            StringBuilder buff = new StringBuilder();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String key = it.next();
                String appId = "";
                if (key != null) {
                    appId = key.split("-")[0];
                }
                buff.append(key).append("---login count[")
                    .append(SocketContext.getMapLoginCount().get(appId)).append("]\n");
            }

            stat.warn(buff.toString());
        }
    }

    public void afterPropertiesSet() throws Exception {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MonitorTask(), 1, 60 * 1000);
    }

}
