/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.util.page.PageResult;

/**
 *
 * @author wangyong
 * @version $Id: AppSubBiz.java, v 0.1 2012-9-11 下午12:50:55 wangyong Exp $
 */
public interface AppSubBiz {

    /**
     * 分页查询子应用.
     * @param appId 应用ID
     * @param page  当前页数
     * @return 返回分页查询的结果
     */
    PageResult<UmsAppSub> getAppSub(String appId, int page);

    /**
     *判断该应用下的子应用是否存在.
     *
     * @param subAppId
     * @param appId
     * @return
     */
    boolean isExist(String subAppId, String appId);

    /**
     *插入子应用.
     * @param umsAppSub 子应用
     * @return 返回插入的结果，true表示成功，false表示失败
     */
    boolean insert(UmsAppSub umsAppSub);

    /**
     *查询通过Id查询子应用
     *
     * @param id
     * @return
     */
    UmsAppSub findById(String id);

    /**
     *通过Id删除子应用
     *
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     *更新子应用
     *
     * @param umsAppSub
     * @return
     */
    boolean update(UmsAppSub umsAppSub);

    /**
     * 通过AppId删除子应用
     *
     * @param appId
     * @return
     */
    boolean deleteByAppId(String appId);

    /**
     * 根据应用ID获得其应用下的所有子应用。
     * @param appId
     * @return
     */
    List<UmsAppSub> getAppSub(String appId);
}
