/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.MD5;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.fault.XFireFault;
import org.codehaus.xfire.handler.AbstractHandler;
import org.codehaus.xfire.transport.http.XFireServletController;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: AuthHandler.java, v 0.1 2012-9-4 上午9:57:11 gag Exp $
 */
public class AuthHandler extends AbstractHandler {

    @Autowired
    private AppInfoService appInfoService;

    /**
     * @see org.codehaus.xfire.handler.Handler#invoke(org.codehaus.xfire.MessageContext)
     */
    public void invoke(MessageContext cxt) throws Exception {

        String cIp = XFireServletController.getRequest().getRemoteAddr();
        Element header = cxt.getInMessage().getHeader();
        if (header == null) {
            throw new XFireFault("ERROR-001，请求必须包含验证信息", XFireFault.SENDER);
        }
        Element token = header.getChild("AuthenticationToken");
        if (token == null) {
            throw new XFireFault("ERROR-001，请求必须包含身份验证信息", XFireFault.SENDER);
        }

        Element app_el = token.getChild("Appid");
        if (app_el == null) {
            throw new XFireFault("ERROR-002，身份验证信息中缺少用户名", XFireFault.SENDER);
        }
        Element password_el = token.getChild("Password");
        if (password_el == null) {
            throw new XFireFault("ERROR-003，身份验证信息中缺少密码", XFireFault.SENDER);
        }

        String appid = app_el.getValue();
        String password = password_el.getValue();
        UmsAppInfo appInfo = appInfoService.findValidByAppId(appid);
        if (appInfo == null) {
            throw new XFireFault("ERROR-007，根据应用系统ID没有查询到对应的应用，appid:[" + appid + "]",
                XFireFault.SENDER);
        } else {
            if (!appid.equals(appInfo.getAppId())) {
                throw new XFireFault("ERROR-004，APPID没有注册" + appid, XFireFault.SENDER);
            }
            if (!MD5.md5(password).equals(appInfo.getPassword())) {
                throw new XFireFault("ERROR-005，密码错误,appid:" + appid + ",pass:" + password
                                     + ",info" + appInfo, XFireFault.SENDER);
            }
            if (!validateClientIp(appInfo.getIp(), cIp)) {
                throw new XFireFault("ERROR-006，调用接口的客户端ip不符合应用系统配置规则", XFireFault.SENDER);
            }
        }
    }

    public boolean validateClientIp(String regex, String source) {
        if (StringUtils.isEmpty(regex)) {
            return true;
        }
        if (regex.equals("*")) {
            return true;
        }
        boolean returnValue = false;
        // 是否包含指定IP地址
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        returnValue = matcher.find();

        return returnValue;
    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

}
