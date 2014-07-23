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
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.enums.app.AppStateEnum;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 检查应用是否有效
 * @author gag
 * @version $Id: AppCheck.java, v 0.1 2012-10-10 上午9:29:54 gag Exp $
 */
public class AppCheck implements Check {

    private static final Logger logger = Logger.getLogger(AppCheck.class);

    @Autowired
    private AppInfoService      appInfoService;

    /** 
     * @see net.zoneland.ums.biz.msg.socket.check.Check#check(net.zoneland.ums.biz.msg.socket.ServiceRequest)
     */
    public ProcessResult check(ServiceRequest serviceRequest) {
        if (CodeConstants.REQUEST_1005.equals(serviceRequest.getRequestCode())
            || CodeConstants.REQUEST_1003.equals(serviceRequest.getRequestCode())) {
            return new ProcessResult(true);
        } else {
            String appId = serviceRequest.getAppId();
            if (appId != null && appId.indexOf("1030") > -1) {
                appId = "1030";
            }
            UmsAppInfo app = appInfoService.findValidByAppId(StringUtils.deleteWhitespace(appId));
            if (app != null && AppStateEnum.ENABLED.getValue().equals(app.getStatus())) {
                return new ProcessResult();
            } else {
                logger.warn("登录校验:appId[" + serviceRequest.getAppId() + "]len["
                            + serviceRequest.getAppId().length() + "]appInfo:[" + app + "]");
                return new ProcessResult(false, "当前应用没有注册，或者状态不可用");
            }
        }

    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }
}
