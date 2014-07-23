/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail.process;

import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;

/**
 * 
 * @author gang
 * @version $Id: MailProcess.java, v 0.1 2012-8-28 下午10:24:53 gang Exp $
 */
public interface MailProcess {

    /**
     * 处理邮件消息
     * @param sr
     * @return
     */
    ProcessResult processMsg(ServiceRequest sr);

}
