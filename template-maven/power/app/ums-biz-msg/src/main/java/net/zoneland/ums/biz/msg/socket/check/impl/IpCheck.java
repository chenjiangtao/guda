/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.check.impl;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.check.Check;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gang
 * @version $Id: IpCheck.java, v 0.1 2012-8-12 上午9:58:27 gang Exp $
 */
public class IpCheck implements Check {

    @Autowired
    private AppInfoService appInfoService;

    /** 
     * @see net.zoneland.ums.biz.msg.socket.check.Check#check(net.zoneland.ums.biz.msg.socket.ServiceRequest)
     */
    public ProcessResult check(ServiceRequest serviceRequest) {
        if (CodeConstants.REQUEST_1003.equals(serviceRequest.getRequestCode())
            || CodeConstants.REQUEST_1005.equals(serviceRequest.getRequestCode())) {
            return new ProcessResult();
        } else {
            boolean res = appInfoService.validateIp(serviceRequest.getAppId(),
                serviceRequest.getClientIp());
            if (res) {
                return new ProcessResult();
            } else {
                return new ProcessResult(false, "非法客户端IP");
            }
        }
    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

}
