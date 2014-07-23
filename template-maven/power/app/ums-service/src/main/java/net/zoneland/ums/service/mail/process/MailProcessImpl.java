/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail.process;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gang
 * @version $Id: MailProcessImpl.java, v 0.1 2012-8-28 下午10:29:49 gang Exp $
 */
public class MailProcessImpl implements MailProcess {

    private static final Logger logger = Logger.getLogger(MailProcessImpl.class);

    @Autowired
    private SocketProcess       mailLoginProcess;

    @Autowired
    private SocketProcess       sendMsgProcess;

    /** 
     * @see net.zoneland.ums.service.mail.process.MailProcess#processMsg(net.zoneland.ums.biz.msg.socket.ServiceRequest)
     */
    public ProcessResult processMsg(ServiceRequest sr) {
        ProcessResult pr = mailLoginProcess.doProcess(sr);
        if (pr.isSuccess()) {
            pr = sendMsgProcess.doProcess(sr);
            if (logger.isInfoEnabled()) {
                logger.info("邮件接口处理结果:" + pr);
            }
            if (pr.isSuccess()) {
                return new ProcessResult();
            } else {
                return new ProcessResult(false, CodeConstants.MAIL_OTHER_ERROR);
            }
        } else {
            return new ProcessResult(false, CodeConstants.MAIL_LOGIN_FAIL);
        }
    }

}
