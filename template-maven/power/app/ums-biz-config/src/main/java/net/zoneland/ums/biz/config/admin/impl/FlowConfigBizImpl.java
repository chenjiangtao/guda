/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.admin.FlowConfigBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.page.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 流量配置业务类
 * @author XuFan
 * @version $Id: FlowConfigBizImpl.java, v 0.1 Aug 27, 2012 9:24:42 AM XuFan Exp $
 */
public class FlowConfigBizImpl implements FlowConfigBiz {

    @Autowired
    private UmsAppInfoMapper umsAppInfoMapper;

    /**
     * 管理员查询全部应用
     *
     * @param umsAppInfo
     * @param curPage
     * @return
     */
    public PageResult<UmsAppInfo> findAppWithPage(String appName, String status, int curPage) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.hasText(appName)) {
            params.put("appName", appName);
        }
        if (StringUtils.hasText(status)) {
            params.put("status", status);
        }
        int total = umsAppInfoMapper.getAppNum(params);
        PageResult<UmsAppInfo> result = new PageResult<UmsAppInfo>(total, curPage);

        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());

        List<UmsAppInfo> list = umsAppInfoMapper.searchAppByPage(params);

        result.setResults(list);
        return result;
    }

    /**
     * 修改流量配置
     *
     * @param umsAppInfo
     * @return
     */
    public int modfiyAppFlow(UmsAppInfo umsAppInfo) {
        return umsAppInfoMapper.updateFlow(umsAppInfo);
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.FlowConfigBiz#findAppById(java.lang.String)
     */
    public UmsAppInfo findAppById(String id) {
        return umsAppInfoMapper.selectByPrimaryKey(id);
    }

}
