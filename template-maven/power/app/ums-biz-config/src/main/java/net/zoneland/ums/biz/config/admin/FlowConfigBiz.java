/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 流量配置接口
 * @author XuFan
 * @version $Id: FlowConfigBiz.java, v 0.1 Aug 27, 2012 10:07:53 AM XuFan Exp $
 */
public interface FlowConfigBiz {
    /**
     * 管理员查询全部应用
     *
     * @param umsAppInfo
     * @param curPage
     * @return
     */
    public PageResult<UmsAppInfo> findAppWithPage(String appName, String status, int curPage);

    public UmsAppInfo findAppById(String id);

    /**
     * 修改流量配置
     *
     * @param umsAppInfo
     * @return
     */
    public int modfiyAppFlow(UmsAppInfo umsAppInfo);
}
