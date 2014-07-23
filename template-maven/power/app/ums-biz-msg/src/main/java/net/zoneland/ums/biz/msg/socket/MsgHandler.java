/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket;

import net.zoneland.ums.biz.msg.socket.process.ProcessResult;

/**
 * 处理原有系统的老接口，包括邮件接口，socket接口
 * @author gag
 * @version $Id: MsgHandler.java, v 0.1 2012-8-28 下午1:27:02 gag Exp $
 */
public interface MsgHandler {

    /**
     * 处理socket消息
     * @param serviceRequest
     * @param loginAppId
     * @return
     */
    ProcessResult processMsg(ServiceRequest serviceRequest, String loginAppId);

}
