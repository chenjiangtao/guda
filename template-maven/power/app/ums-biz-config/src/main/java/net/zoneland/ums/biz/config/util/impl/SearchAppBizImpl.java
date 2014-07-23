/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.util.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.util.AppForm;
import net.zoneland.ums.biz.config.util.SearchAppBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author wangyong
 * @version $Id: SearchAppBizImpl.java, v 0.1 2012-10-16 上午9:39:46 wangyong Exp $
 */
public class SearchAppBizImpl implements SearchAppBiz {

    @Autowired
    private UmsAppInfoMapper umsAppInfoMapper;

    @Autowired
    private UmsAppSubMapper  umsAppSubMapper;

    /** 
     * @see net.zoneland.ums.biz.config.util.SearchAppBiz#getAppByName(java.lang.String)
     */
    public List<AppForm> getAppByName(String name) {
        List<AppForm> appForms = new ArrayList<AppForm>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("appName", name);
        map.put("appId", name);
        List<UmsAppInfo> umsAppInfos = umsAppInfoMapper.getAppByNameAndAppId(map);
        if (umsAppInfos != null && umsAppInfos.size() > 0) {
            for (UmsAppInfo umsAppInfo : umsAppInfos) {
                if (umsAppInfo.getAppId().equals("0")) {
                    continue;
                }
                AppForm appForm = new AppForm(umsAppInfo.getAppId(), umsAppInfo.getAppName());
                appForms.add(appForm);
            }
        }
        return appForms;
    }

    /** 
     * @see net.zoneland.ums.biz.config.util.SearchAppBiz#getAppByName(java.lang.String)
     */
    public List<AppForm> getAppByAppName(String name, List<String> appIds) {
        List<AppForm> appForms = new ArrayList<AppForm>();
        Map<String, String> map = new HashMap<String, String>();
        if (appIds != null && appIds.size() > 0) {
            for (String appId : appIds) {
                map.put("id", appId);
                map.put("appName", name);
                map.put("appId", name);
                List<UmsAppInfo> umsAppInfos = umsAppInfoMapper.getAppAdminAppsByName(map);
                if (umsAppInfos != null && umsAppInfos.size() > 0) {
                    for (UmsAppInfo umsAppInfo : umsAppInfos) {
                        if (umsAppInfo.getAppId().equals("0")) {
                            continue;
                        }
                        AppForm appForm = new AppForm(umsAppInfo.getAppId(),
                            umsAppInfo.getAppName());
                        appForms.add(appForm);
                    }
                }
            }
        }
        return appForms;
    }

    /**
     * 根据父应用的AppId获得子应用集合
     * 
     * @see net.zoneland.ums.biz.config.util.SearchAppBiz#getAppSubByAppId(java.lang.String)
     */
    public List<UmsAppSub> getAppSubByAppId(String appId) {
        if (StringUtils.isEmpty(appId)) {
            return Collections.emptyList();
        }
        UmsAppInfo umsAppInfo = umsAppInfoMapper.selectAppByAppId(appId);
        if (umsAppInfo == null) {
            return Collections.emptyList();
        }
        appId = umsAppInfo.getId();
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", appId);
        List<UmsAppSub> umsAppSubs = umsAppSubMapper.getSubAppByAppId(map);
        return umsAppSubs;
    }
}
