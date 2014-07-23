/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.appadmin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz;
import net.zoneland.ums.common.dal.UmsMsgTemplateMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author wangyong
 * @version $Id: MsgTemplateBizImpl.java, v 0.1 2012-10-12 下午4:56:08 wangyong Exp $
 */
public class MsgTemplateBizImpl implements MsgTemplateBiz {

    @Autowired
    private UmsMsgTemplateMapper umsMsgTemplateMapper;

    @Autowired
    private AppInfoService       appInfoService;

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#saveTemplate(net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate)
     */
    public boolean saveTemplate(UmsMsgTemplate umsMsgTemplate) {
        if (umsMsgTemplate == null) {
            return false;
        }
        umsMsgTemplate.setId(UUID.randomUUID().toString());
        Date date = new Date();
        umsMsgTemplate.setGmtCreated(date);
        umsMsgTemplate.setGmtModified(date);
        umsMsgTemplateMapper.insert(umsMsgTemplate);
        return false;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#isExist(java.lang.String)
     */
    public boolean isExist(String tempId) {
        if (StringUtils.isEmpty(tempId)) {
            return false;
        }
        UmsMsgTemplate umsMsgTemplate = umsMsgTemplateMapper.findByTempId(tempId);
        if (umsMsgTemplate == null) {
            return false;
        }
        return true;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#listUmsTemplate(net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate, int)
     */
    public PageResult<UmsMsgTemplate> listUmsTemplate(String tempId, int pageId, List<String> appIds) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appIdList", appIds);
        params.put("tempId", tempId);
        int total = umsMsgTemplateMapper.getCountByRequirement(params);
        PageResult<UmsMsgTemplate> result = new PageResult<UmsMsgTemplate>(total, pageId);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgTemplate> list = umsMsgTemplateMapper.searchByPage(params);
        for (UmsMsgTemplate umsMsgTemplate : list) {
            UmsAppInfo umsAppInfo = appInfoService.findByAppId(umsMsgTemplate.getAppId());
            umsMsgTemplate.setAppId(umsAppInfo.getAppName());
        }
        result.setResults(list);
        return result;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#deleteById(java.lang.String)
     */
    public boolean deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        int del = umsMsgTemplateMapper.deleteByPrimaryKey(id);
        if (del == 1) {
            return true;
        }
        return false;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#findById(java.lang.String)
     */
    public UmsMsgTemplate findById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        UmsMsgTemplate umsMsgTemplate = umsMsgTemplateMapper.selectByPrimaryKey(id);
        return umsMsgTemplate;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#updateTemplate(net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate)
     */
    public boolean updateTemplate(UmsMsgTemplate umsMsgTemplate) {
        if (umsMsgTemplate == null) {
            return false;
        }
        umsMsgTemplate.setGmtModified(new Date());
        int update = umsMsgTemplateMapper.updateByPrimaryKey(umsMsgTemplate);
        if (update == 1) {
            return true;
        }
        return false;
    }

    /** 
     * @see net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz#fetchTemplate(java.lang.String, java.lang.String, java.lang.String)
     */
    public List<UmsMsgTemplate> fetchTemplate(String appId, String subAppId, String templateId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        params.put("subAppId", subAppId);
        params.put("templateId", templateId);
        return umsMsgTemplateMapper.searchByAppId(params);
    }

    public void setUmsMsgTemplateMapper(UmsMsgTemplateMapper umsMsgTemplateMapper) {
        this.umsMsgTemplateMapper = umsMsgTemplateMapper;
    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

}
