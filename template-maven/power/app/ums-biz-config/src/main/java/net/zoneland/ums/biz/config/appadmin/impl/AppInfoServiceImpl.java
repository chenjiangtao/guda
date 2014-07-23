/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.appadmin.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.util.MD5;
import net.zoneland.ums.common.util.enums.app.AppStateEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 
 * @author gag
 * @version $Id: AppInfoServiceImpl.java, v 0.1 2012-8-22 下午3:01:13 gag Exp $
 */
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private UmsAppInfoMapper umsAppInfoMapper;

    @Autowired
    private UmsAppSubMapper  umsAppSubMapper;

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.AppInfoService#verifyPassword(java.lang.String, java.lang.String)
     */
    public boolean verifyPassword(String appId, String password) {
        if (appId == null) {
            return false;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", appId);
        map.put("password", MD5.md5(password));
        return umsAppInfoMapper.selectWithAppIdPassword(map) > 0;
    }

    public void setUmsAppInfoMapper(UmsAppInfoMapper umsAppInfoMapper) {
        this.umsAppInfoMapper = umsAppInfoMapper;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.AppInfoService#findByAppId(java.lang.String)
     */
    public UmsAppInfo findByAppId(String appId) {
        if (appId == null) {
            return null;
        }
        return umsAppInfoMapper.selectAppByAppId(appId);
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.AppInfoService#validateIp(java.lang.String, java.lang.String)
     */
    public boolean validateIp(String appId, String ip) {
        if (appId == null || ip == null) {
            return false;
        }
        UmsAppInfo appInfo = umsAppInfoMapper.selectAppByAppId(appId);
        if (appInfo == null) {
            return false;
        }
        if (appInfo.getIp() == null || !StringUtils.hasText(appInfo.getIp())) {
            return true;
        }
        if ("*".equals(appInfo.getIp())) {
            return true;
        }
        // 是否包含指定IP地址
        Pattern pattern = Pattern.compile(appInfo.getIp());
        Matcher matcher = pattern.matcher(ip);
        return matcher.find();

    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.AppInfoService#findPriority(java.lang.String, java.lang.String)
     */
    public int findPriority(String appId, String subAppId) {
        if (StringUtils.hasText(subAppId) && StringUtils.hasText(appId)) {
            Map<String, Object> params = new HashMap<String, Object>(6);
            params.put("subAppId", subAppId);
            params.put("appId", appId);
            UmsAppSub sub = umsAppSubMapper.selectBySubAppId(params);
            if (sub == null || sub.getPriority() == null) {
                UmsAppInfo app = umsAppInfoMapper.selectAppByAppId(appId);
                if (app == null) {
                    return 0;
                }
                if (app.getPriority() == null) {
                    return 0;
                }
                return app.getPriority().intValue();
            }
            return sub.getPriority().intValue();
        }
        UmsAppInfo app = umsAppInfoMapper.selectAppByAppId(appId);
        if (app == null) {
            return 0;
        }
        if (app.getPriority() == null) {
            return 0;
        }
        return app.getPriority().intValue();

    }

    public void setUmsAppSubMapper(UmsAppSubMapper umsAppSubMapper) {
        this.umsAppSubMapper = umsAppSubMapper;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.AppInfoService#findValidByAppId(java.lang.String)
     */
    public UmsAppInfo findValidByAppId(String appId) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("status", AppStateEnum.ENABLED.getValue());
        params.put("appId", appId);
        return umsAppInfoMapper.selectValidAppByAppId(params);
    }

}
