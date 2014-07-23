/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process.impl;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.SocketContext;
import net.zoneland.ums.biz.msg.socket.SocketOperator;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gang
 * @version $Id: ProcessLogin.java, v 0.1 2012-8-12 上午9:51:49 gang Exp $
 */
public class LoginProcessImpl implements SocketProcess {

    @Autowired
    private AppInfoService appInfoService;

    /** 
     * @see net.zoneland.ums.service.process.SocketProcess#doProcess(net.zoneland.ums.service.ServiceRequest)
     */
    public ProcessResult doProcess(ServiceRequest serviceRequest) {
        if (serviceRequest == null) {
            return new ProcessResult(false);
        }
        boolean res = appInfoService.verifyPassword(serviceRequest.getAppId(),
            serviceRequest.getPassword());
        if (res) {
            //如果是socket请求的，需要记录登录状态，邮件请求的不用记录登录状态
            if (serviceRequest.getClientIp() != null) {
                SocketOperator oper = SocketContext.createLoginContext(serviceRequest.getAppId(),
                    serviceRequest.getClientIp(), serviceRequest.getClientPort(),
                    serviceRequest.getServerPort());
                if (oper == null) {
                    return new ProcessResult(false, CodeConstants.newFailure("当前登录应用过多"));
                }
            }
            return new ProcessResult(true, CodeConstants.SUCCESS);
        } else {
            return new ProcessResult(false, CodeConstants.newFailure("用户名或者密码不正确"));
        }
    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

}
