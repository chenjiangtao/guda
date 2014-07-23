/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process.impl;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Administrator
 * @version $Id: MailLoginProcessImpl.java, v 0.1 2013-1-10 下午3:22:34 Administrator Exp $
 */
public class MailLoginProcessImpl implements SocketProcess {

    @Autowired
    private AppInfoService appInfoService;

    /** 
     * @see net.zoneland.ums.biz.msg.socket.process.SocketProcess#doProcess(net.zoneland.ums.biz.msg.socket.ServiceRequest)
     */
    public ProcessResult doProcess(ServiceRequest serviceRequest) {
        if (serviceRequest == null) {
            return new ProcessResult(false);
        }
        UmsAppInfo appInfo = appInfoService.findByAppId(serviceRequest.getAppId());
        if (appInfo == null) {
            return new ProcessResult(false, "应用没有注册");
        }
        if (appInfo.getEmailMd5() != null
            && appInfo.getEmailMd5().equals(serviceRequest.getPassword())) {
            return new ProcessResult(true);
        }
        return new ProcessResult(false, "密码不正确");
    }

}
