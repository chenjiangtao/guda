/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.AppBiz;
import net.zoneland.ums.biz.config.admin.AppSubBiz;
import net.zoneland.ums.biz.config.admin.BlackListBiz;
import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsFlowLogMapper;
import net.zoneland.ums.common.dal.UmsMsgInRuleMapper;
import net.zoneland.ums.common.dal.UmsMsgTemplateMapper;
import net.zoneland.ums.common.dal.UmsUserRoleAppRelMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.MD5;
import net.zoneland.ums.common.util.enums.app.AppStateEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 * @version $Id: AppBizImpl.java, v 0.1 2012-9-1 下午9:26:31 Administrator Exp $
 */
public class AppBizImpl implements AppBiz {

    private final Logger            logger = Logger.getLogger(AppBizImpl.class);

    @Autowired
    private UmsAppInfoMapper        umsAppInfoMapper;

    @Autowired
    private AppSubBiz               appSubBiz;

    @Autowired
    private BlackListBiz            blackListBiz;

    @Autowired
    private KeywordBiz              keywordBiz;

    @Autowired
    private UmsFlowLogMapper        umsFlowLogMapper;

    @Autowired
    private UmsMsgInRuleMapper      umsMsgInRuleMapper;

    @Autowired
    private UmsMsgTemplateMapper    umsMsgTemplateMapper;

    @Autowired
    private UmsUserRoleAppRelMapper umsUserRoleAppRelMapper;

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#selectById()
     */
    public UmsAppInfo selectById(String id) {
        if (logger.isInfoEnabled()) {
            logger.info("获取应用相关信息！");
        }
        if (id == null) {
            if (logger.isInfoEnabled()) {
                logger.info("id为空");
            }
            return new UmsAppInfo();
        }
        try {
            UmsAppInfo umsAppInfo = umsAppInfoMapper.selectByPrimaryKey(id);
            return umsAppInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return new UmsAppInfo();
        }

    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#insert(net.zoneland.ums.common.dal.dataobject.UmsAppInfo)
     */
    public boolean insert(UmsAppInfo umsAppInfo) {
        if (umsAppInfo == null) {
            return false;
        }
        String id = UUID.randomUUID().toString();
        umsAppInfo.setId(id);
        Date date = new Date();
        umsAppInfo.setGmtCreated(date);
        umsAppInfo.setGmtModified(date);
        //注册默认位是可用的应用状态，状态修改到显示页面修改！
        umsAppInfo.setStatus(AppStateEnum.ENABLED.getValue());
        //密码进行MD5加密
        String password = MD5.md5(umsAppInfo.getPassword());
        umsAppInfo.setPassword(password);
        //对一些对应属性的但是不同类型的非空字符串进行转化
        umsAppInfoMapper.insert(umsAppInfo);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#showAllApp(net.zoneland.ums.common.dal.dataobject.UmsAppInfo, int)
     */
    public PageResult<UmsAppInfo> showAllApp(UmsAppInfo umsAppInfo, int curPage) {

        if (logger.isInfoEnabled()) {
            logger.info("开始查询全部应用");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        /*
         * 查询条件
         */
        params.put("appName", umsAppInfo.getAppName());
        params.put("appId", umsAppInfo.getAppId());
        params.put("status", umsAppInfo.getStatus());
        int total = umsAppInfoMapper.getAppNum(params);
        PageResult<UmsAppInfo> result = new PageResult<UmsAppInfo>(total, curPage);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsAppInfo> list = umsAppInfoMapper.searchAppByPage(params);
        result.setResults(list);
        return result;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#updateApp(net.zoneland.ums.common.dal.dataobject.UmsAppInfo)
     */
    public boolean updateApp(UmsAppInfo umsAppInfo) {
        if (umsAppInfo == null) {
            return false;
        }
        if (logger.isInfoEnabled()) {
            logger.info("更新应用！");
        }
        int update = umsAppInfoMapper.updateByPrimaryKey(umsAppInfo);
        if (update == 1) {
            return true;
        }
        return false;

    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#delAppById(java.lang.String)
     */
    public boolean delAppById(String id) {
        if (StringHelper.isEmpty(id)) {
            return false;
        }
        UmsAppInfo umsAppInfo = umsAppInfoMapper.selectByPrimaryKey(id);
        if (umsAppInfo == null) {
            return false;
        }
        String appId = umsAppInfo.getAppId();
        umsUserRoleAppRelMapper.deleteByAppId(id);// 删除应用时同时要删除应用管理员所能管理的该应用的用户角色关联表的记录
        blackListBiz.deleteBlackListByAppId(appId);// 通过appId删除与该应用相关的黑名单
        keywordBiz.deleteKeywordByAppId(appId);// 通过appId删除与该应用相关的关键字
        umsFlowLogMapper.deleteByAppId(appId);// 通过appId删除与该应用相关的流量日志
        umsMsgInRuleMapper.deleteByAppId(appId);// 通过appId删除与该应用相关的上行规则
        umsMsgTemplateMapper.deleteByAppId(appId);// 通过appId删除与该应用相关的短信模版
        appSubBiz.deleteByAppId(id);// 通过应用ID删除相关子应用
        umsAppInfoMapper.deleteByPrimaryKey(id);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#isExist(java.lang.String)
     */
    public boolean isExist(String appId) {
        UmsAppInfo umsAppInfo = umsAppInfoMapper.selectAppByAppId(appId);
        if (umsAppInfo != null) {
            return true;
        }
        return false;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.AppBiz#changeStatusById(java.lang.String)
     */
    public String changeStatusById(String id) {
        if (StringHelper.isEmpty(id)) {
            return null;
        }
        UmsAppInfo umsAppInfo = umsAppInfoMapper.selectByPrimaryKey(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        if (AppStateEnum.ENABLED.getValue().equalsIgnoreCase(umsAppInfo.getStatus())) {
            map.put("status", AppStateEnum.DISABLED.getValue());
        } else {
            map.put("status", AppStateEnum.ENABLED.getValue());
        }
        Date date = new Date();
        map.put("gmtModified", date);
        umsAppInfoMapper.updateStatus(map);
        if (umsAppInfo.getStatus().equalsIgnoreCase(AppStateEnum.ENABLED.getValue())) {
            return AppStateEnum.DISABLED.getValue();
        }
        return AppStateEnum.ENABLED.getValue();
    }
}
