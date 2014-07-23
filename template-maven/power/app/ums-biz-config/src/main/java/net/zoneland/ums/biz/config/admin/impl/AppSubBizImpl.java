/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.AppSubBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.UmsMsgInRuleMapper;
import net.zoneland.ums.common.dal.UmsMsgTemplateMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangyong
 * @version $Id: AppSubBizImpl.java, v 0.1 2012-9-11 下午12:52:19 wangyong Exp $
 */
public class AppSubBizImpl implements AppSubBiz {

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    @Autowired
    private UmsAppSubMapper      umsAppSubMapper;

    @Autowired
    private UmsMsgInRuleMapper   umsMsgInRuleMapper;

    @Autowired
    private UmsMsgTemplateMapper umsMsgTemplateMapper;

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#getAppSub(java.lang.String)
     */
    public PageResult<UmsAppSub> getAppSub(String appId, int pageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = umsAppSubMapper.getCountByAppId(appId);
        PageResult<UmsAppSub> pageResult = new PageResult<UmsAppSub>(total, pageId);
        map.put("appId", appId);
        map.put("first", pageResult.getFirstrecode());
        map.put("end", pageResult.getEndrecode());
        List<UmsAppSub> umsAppSubList = umsAppSubMapper.searchSubByPage(map);
        pageResult.setResults(umsAppSubList);
        return pageResult;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#isExist(java.lang.String)
     */
    public boolean isExist(String subAppId, String appId) {
        if (StringHelper.isEmpty(subAppId)) {
            return false;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appId);
        map.put("subAppId", subAppId);
        UmsAppSub umsAppSub = umsAppSubMapper.selectBySubAppId(map);
        if (umsAppSub == null) {
            return false;
        }
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#insert(net.zoneland.ums.common.dal.dataobject.UmsAppSub)
     */
    public boolean insert(UmsAppSub umsAppSub) {
        if (umsAppSub == null) {
            return false;
        }
        Date date = new Date();
        umsAppSub.setId(UUID.randomUUID().toString());
        umsAppSub.setGmtCreated(date);
        umsAppSub.setGmtModified(date);
        umsAppSubMapper.insert(umsAppSub);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#findById(java.lang.String)
     */
    public UmsAppSub findById(String id) {
        UmsAppSub umsAppSub = umsAppSubMapper.selectByPrimaryKey(id);
        return umsAppSub;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#deleteById(java.lang.String)
     */
    public boolean deleteById(String id) {
        if (StringHelper.isEmpty(id)) {
            return false;
        }
        UmsAppSub umsAppSub = umsAppSubMapper.selectByPrimaryKey(id);
        if (umsAppSub == null) {
            return false;
        }
        String subAppId = umsAppSub.getAppSubId();
        umsMsgInRuleMapper.deleteBySubAppId(subAppId);// 通过subAppId删除与该子应用相关的上行规则
        umsMsgTemplateMapper.deleteBySubAppId(subAppId);// 通过subAppId删除与该子应用相关的短信模版
        umsAppSubMapper.deleteByPrimaryKey(id);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#update(net.zoneland.ums.common.dal.dataobject.UmsAppSub)
     */
    public boolean update(UmsAppSub umsAppSub) {
        if (umsAppSub == null) {
            return false;
        }
        umsAppSubMapper.updateByPrimaryKey(umsAppSub);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#deleteByAppId(java.lang.String)
     */
    public boolean deleteByAppId(String appId) {
        if (StringHelper.isEmpty(appId)) {
            return false;
        }
        umsAppSubMapper.deleteByAppId(appId);
        return true;
    }

    /** 
     * 根据应用ID获得其应用下的所有子应用
     * 
     * @see net.zoneland.ums.biz.config.admin.AppSubBiz#getAppSub(java.lang.String)
     */
    public List<UmsAppSub> getAppSub(String appId) {
        UmsAppInfo umsAppInfo = umsAppInfoMapper.selectAppByAppId(appId);
        if (umsAppInfo == null) {
            return new ArrayList<UmsAppSub>();
        }
        return umsAppSubMapper.selectAllSubApp(umsAppInfo.getId());
    }
}
