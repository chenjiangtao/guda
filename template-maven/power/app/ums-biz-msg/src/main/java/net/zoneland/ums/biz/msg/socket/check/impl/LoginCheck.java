/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.check.impl;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.SocketContext;
import net.zoneland.ums.biz.msg.socket.check.Check;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;

/**
 * 
 * @author gang
 * @version $Id: FlowFilter.java, v 0.1 2012-8-12 上午8:54:38 gang Exp $
 */
public class LoginCheck implements Check {

    /** 
     * @see net.zoneland.ums.service.check.Check#check(net.zoneland.ums.service.ServiceRequest)
     */
    public ProcessResult check(ServiceRequest serviceRequest) {
        if (serviceRequest == null) {
            return new ProcessResult(false, "请求对象为空");
        }
        if (CodeConstants.REQUEST_1001.equals(serviceRequest.getRequestCode())
            || CodeConstants.REQUEST_1005.equals(serviceRequest.getRequestCode())
            || CodeConstants.REQUEST_1003.equals(serviceRequest.getRequestCode())) {
            return new ProcessResult(true);
        } else {
            boolean res = SocketContext.isLogin(serviceRequest.getAppId(),
                serviceRequest.getClientIp(), serviceRequest.getClientPort());
            if (!res) {
                return new ProcessResult(false, "需要先登录");
            }
        }
        return new ProcessResult(true);
    }

}
