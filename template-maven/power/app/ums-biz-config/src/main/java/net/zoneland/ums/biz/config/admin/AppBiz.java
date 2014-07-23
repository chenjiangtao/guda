/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.page.PageResult;

/**
 *
 * @author wangyong
 * @version $Id: AppBiz.java, v 0.1 2012-9-1 下午9:20:08 wangyong Exp $
 */
public interface AppBiz {

    /**
     *通过appId获得应用相关信息
     *
     * @return
     */
    public UmsAppInfo selectById(String id);

    /**
     *增加应用
     *
     * @param umsAppInfo
     * @return
     */
    public boolean insert(UmsAppInfo umsAppInfo);

    /**
     * 分页查询应用
     *
     * @param umsAppInfo
     * @param curPage
     * @return
     */
    PageResult<UmsAppInfo> showAllApp(UmsAppInfo umsAppInfo, int curPage);

    /**
     *更新应用
     *
     * @param umsAppInfo
     * @return
     */
    boolean updateApp(UmsAppInfo umsAppInfo);

    /**
     *通过ID删除应用</br>
     *同时会删除以该appI作为外键的相关内容，例如黑名单，关键词等
     *
     * @param id
     * @return
     */
    boolean delAppById(String id);

    /**
     *判断appId是否已经被注册
     *
     * @param appId
     * @return
     */
    boolean isExist(String appId);

    /**
     * 根据ID变化状态，如果是可用的就转化不可用，如果是不可用的就转为可用
     *
     * @param id
     * @return
     */
    String changeStatusById(String id);

}
