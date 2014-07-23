/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.util;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsAppSub;

/**
 * 
 * @author wangyong
 * @version $Id: SearchAppBiz.java, v 0.1 2012-10-16 上午9:33:34 wangyong Exp $
 */
public interface SearchAppBiz {

    /**
     * 根据应用名模糊匹配获得应用
     * 
     * @param name
     * @return
     */
    List<AppForm> getAppByName(String name);

    /**
     * 应用管理员下根据应用名模糊匹配获得应用
     * 
     * @param name
     * @return
     */
    List<AppForm> getAppByAppName(String name, List<String> appIds);
    
    /**
     * 根据父应用的AppId获得子应用集合
     * 
     * @param appId
     * @return
     */
    List<UmsAppSub> getAppSubByAppId(String appId);
}
