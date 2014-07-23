/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.MsgInRuleBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.UmsMsgInRuleMapper;
import net.zoneland.ums.common.dal.bo.UmsMsgInRuleBo;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInRule;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 上行规则维护业务层
 * 
 * @author yangjuanying
 * @version $Id: MsgInRuleBizImpl.java, v 0.1 2012-10-13 下午10:45:05 yangjuanying Exp $
 */
public class MsgInRuleBizImpl implements MsgInRuleBiz {

    @Autowired
    private UmsAppInfoMapper   umsAppInfoMapper;

    @Autowired
    private UmsAppSubMapper    umsAppSubMapper;

    @Autowired
    private UmsMsgInRuleMapper umsMsgInRuleMapper;

    /** 
     * 保存新增上行规则
     * 
     * @see net.zoneland.ums.biz.config.admin.MsgInRuleBiz#saveMsgInRule(net.zoneland.ums.common.dal.dataobject.UmsMsgInRule)
     */
    public boolean saveMsgInRule(UmsMsgInRule umsMsgInRule) {
        if (StringUtils.isEmpty(umsMsgInRule.getSubAppId())) {
            umsMsgInRule.setSubAppId("");
        }
        List<UmsMsgInRule> umsMsgInRules = umsMsgInRuleMapper.getMsgInRule(umsMsgInRule);// 获取上行规则
        if (umsMsgInRules != null && umsMsgInRules.size() > 0) {// 如果获取到了，说明已存在该上行规则
            return false;
        }
        umsMsgInRule.setId(UUID.randomUUID().toString());
        Date date = new Date();
        umsMsgInRule.setGmtCreated(date);
        umsMsgInRule.setGmtModified(date);
        umsMsgInRuleMapper.insert(umsMsgInRule);
        return true;
    }

    /** 
     * 分页显示上行规则表
     * 
     * 
     */
    public PageResult<UmsMsgInRuleBo> searchMsgInRuleByPage(UmsMsgInRuleBo umsMsgInRuleBo,
                                                            int pageId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(umsMsgInRuleBo.getWord())) {// 如果内容关键词不为空
            params.put("word", umsMsgInRuleBo.getWord());
        }
        // 按应用名查询
        String appName = umsMsgInRuleBo.getAppName();
        boolean appNameFlag = false;
        if (StringUtils.isNotEmpty(appName)) {
            params.put("appName", appName);
            List<String> appIdList = umsAppInfoMapper.getAppIdByAppName(params);
            if (appIdList != null && appIdList.size() > 0) {
                params.put("appIdList", appIdList);
            } else {
                appNameFlag = true;
            }
        }
        int total = 0;
        if (appNameFlag) {
            PageResult<UmsMsgInRuleBo> result = new PageResult<UmsMsgInRuleBo>(total, pageId);
            result.setResults(null);
            return result;
        }
        total = umsMsgInRuleMapper.searchMsgInRuleNum(params);//返回记录总条数
        PageResult<UmsMsgInRuleBo> result = new PageResult<UmsMsgInRuleBo>(total, pageId);
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgInRuleBo> list = umsMsgInRuleMapper.searchSelectiveByPage(params);//分页查询结果集
        for (int i = 0; i < list.size(); i++) {
            params.put("subAppId", list.get(i).getSubAppId());
            UmsAppInfo umsAppInfo = umsAppInfoMapper.selectAppByAppId(list.get(i).getAppId());
            if (umsAppInfo != null) {
                params.put("appId", umsAppInfo.getId());
                list.get(i).setSubAppId(umsAppSubMapper.getSubAppName(params));// 把subAppId转换成子应用名显示
            }
            list.get(i).setAppName(
                StringUtils.trim(umsAppInfoMapper.getAppNameByAppId(list.get(i).getAppId())));// 把appId转换成appName显示
        }
        result.setResults(list);
        return result;
    }

    /**
     * 根据主键ID删除该上行规则记录
     * 
     * @see net.zoneland.ums.biz.config.admin.MsgInRuleBiz#deleteMsgInRule(java.lang.String)
     */
    public boolean deleteMsgInRule(String id) {
        return umsMsgInRuleMapper.deleteByPrimaryKey(id) == 1;
    }

    /** 
     * 通过ID查找上行规则记录
     * 
     * @see net.zoneland.ums.biz.config.admin.MsgInRuleBiz#getMsgInRuleById(java.lang.String)
     */
    public UmsMsgInRule getMsgInRuleById(String id) {
        UmsMsgInRule msgInRule = umsMsgInRuleMapper.selectByPrimaryKey(id);
        if (msgInRule == null) {
            return new UmsMsgInRule();
        }
        return msgInRule;
    }

    /** 
     * @see net.zoneland.ums.biz.config.admin.MsgInRuleBiz#update(net.zoneland.ums.common.dal.dataobject.UmsMsgInRule)
     */
    public boolean update(UmsMsgInRule umsMsgInRule) {
        if (StringUtils.isEmpty(umsMsgInRule.getSubAppId())) {
            umsMsgInRule.setSubAppId("");
        }
        boolean isExist = false;
        List<UmsMsgInRule> umsMsgInRules = umsMsgInRuleMapper.getMsgInRule(umsMsgInRule);// 获取上行规则
        if (umsMsgInRules != null && umsMsgInRules.size() > 0) {// 如果获取到了，说明已存在该上行规则
            isExist = true;
            for (UmsMsgInRule testUmsMsgInRule : umsMsgInRules) {
                if (testUmsMsgInRule.getId().equals(umsMsgInRule.getId())) {
                    return true;
                }
            }
        }
        if (isExist) {
            return false;
        }
        Date date = new Date();
        umsMsgInRule.setGmtModified(date);
        int res = umsMsgInRuleMapper.updateByPrimaryKey(umsMsgInRule);
        if (res != 1) {
            return false;
        }
        return true;
    }

    /** 
     * @see net.zoneland.ums.biz.config.admin.MsgInRuleBiz#selectRule(java.lang.String)
     */
    public UmsMsgInRule findRule(String word) {
        if (word == null) {
            return null;
        }
        List<UmsMsgInRule> rules = umsMsgInRuleMapper.selectAll();
        Iterator<UmsMsgInRule> it = rules.iterator();
        while (it.hasNext()) {
            UmsMsgInRule rule = it.next();
            if (word.startsWith(rule.getWord())) {
                return rule;
            }
        }
        return null;
    }
}
