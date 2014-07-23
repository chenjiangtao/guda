/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.appadmin;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;

/**
 * 第三方应用配置参数，查询等相关业务类
 * @author gag
 * @version $Id: AppInfoService.java, v 0.1 2012-8-22 下午2:58:18 gag Exp $
 */
public interface AppInfoService {

    /**
     * 根据APPID，校验密码
     * @param appId
     * @param password
     * @return
     */
    boolean verifyPassword(String appId, String password);

    /**
     * 根据APPID查找APP信息
     * @param appId
     * @return
     */
    UmsAppInfo findByAppId(String appId);

    /**
     * 查找状态为启用的app
     * @param appId
     * @return
     */
    UmsAppInfo findValidByAppId(String appId);

    /**
     * 校验应用的IP是否在指定范围
     * @param appId
     * @param ip
     * @return
     */
    boolean validateIp(String appId, String ip);

    /**
     * 获取应用的优先级
     * 先获取子应用的优先级，如果子应用有配置优先级则返回
     * 否则获取应用的优先级
     * @param appId
     * @param subAppId
     * @return
     */
    int findPriority(String appId, String subAppId);

}
