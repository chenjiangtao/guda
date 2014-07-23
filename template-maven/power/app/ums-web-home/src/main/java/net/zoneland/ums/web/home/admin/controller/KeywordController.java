/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.List;

import net.zoneland.ums.biz.config.admin.AllAppMsgBiz;
import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.biz.config.admin.bo.KeywordBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsKeywordInfo;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.KeyWordInfoForm;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 关键字控制层 
 * @author XuFan
 * @version $Id: KeywordController.java, v 0.1 Aug 17, 2012 11:08:51 AM XuFan Exp $
 */
@Controller
public class KeywordController extends BaseController {

    private final static Logger logger = Logger.getLogger(KeywordController.class);

    @Autowired
    private KeywordBiz          keywordBiz;

    @Autowired
    private AllAppMsgBiz        allAppMsgBiz;

    /**
     * 拦截关键字页面get请求，分页显示关键字信息
     * 
     * @param keyWordInfoForm 关键字表单信息
     * @param pageId 当前页
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/keyword/keywordlist.htm", method = RequestMethod.GET)
    public String doGet(KeyWordInfoForm keyWordInfoForm, Integer pageId, ModelMap modelMap) {
        if (StringHelper.isEmpty(pageId) || pageId < 1) {// 如果所获取的当前页为空值 
            pageId = 1;// 设置当前页为第一页
        }
        PageResult<KeywordBO> result = null;
        try {
            result = keywordBiz.searchKeyword(
                // 分页显示关键字页面信息
                StringUtils.trimAllWhitespace(keyWordInfoForm.getKeyword()),
                StringUtils.trimAllWhitespace(keyWordInfoForm.getAppId()), pageId);
        } catch (Exception e) {
            logger.error("查询关键字出错", e);
            addErrorMsg(modelMap, "查询关键字出错");
        }
        List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();// 获取应用信息表全部主键ID，应用AppId和应用名name
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
        modelMap.addAttribute("keyword", keyWordInfoForm.getKeyword());
        modelMap.addAttribute("appId", keyWordInfoForm.getAppId());
        modelMap.addAttribute("pageId", pageId);
        return "admin/keyword/keywordlist.vm";
    }

    /**
     * 拦截关键字页面post请求，根据条件分页显示关键字页面信息
     * 
     * @param keyWordInfoForm 关键字表单信息
     * @param result 数据绑定对象
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/keyword/keywordlist.htm", method = RequestMethod.POST)
    public String doPost(KeyWordInfoForm keyWordInfoForm, Integer pageId, BindingResult result,
                         ModelMap modelMap) {
        PageResult<KeywordBO> pageResult = null;
        try {
            if (pageId == null || pageId < 1) {
                pageId = 1;
            }
            keyWordInfoForm.setUmsAppInfos(allAppMsgBiz.getAllApp());// 获取应用信息表全部主键ID，应用AppId和应用名name
            pageResult = keywordBiz.searchKeyword(
                // 根据条件分页显示关键字页面信息
                StringUtils.trimAllWhitespace(keyWordInfoForm.getKeyword()),
                StringUtils.trimAllWhitespace(keyWordInfoForm.getAppId()), pageId);
        } catch (Exception e) {
            logger.error("查询关键字出错", e);
            addErrorMsg(modelMap, "查询关键字出错");
        }
        modelMap.addAttribute("result", pageResult);
        modelMap.addAttribute("keyword", keyWordInfoForm.getKeyword());// 绑定关键字
        modelMap.addAttribute("apps", keyWordInfoForm.getUmsAppInfos());// 绑定集合提供给应用下拉框
        modelMap.addAttribute("appId", keyWordInfoForm.getAppId());// 绑定所获取的应用ID
        modelMap.addAttribute("pageId", pageResult.getCurPage());// 绑定当前页
        return "admin/keyword/keywordlist.vm";
    }

    /**
     * 拦截删除关键字Get请求，根据关键字主键删除该关键字
     * 
     * @param keyWordInfoForm 关键字表单信息
     * @param keyword 关键字
     * @param appId 应用ID
     * @param pageId 当前页
     * @param id 所选的当前关键字主键ID
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/keyword/keywordDelete.htm", method = RequestMethod.GET)
    public String keywordDelete(KeyWordInfoForm keyWordInfoForm, String keyword, String appId,
                                Integer pageId, String id, ModelMap modelMap) {
        if (id != null && !"".equals(id)) {
            try {
                UmsKeywordInfo info = keywordBiz.getKeywordById(id);// 根据主键获得当前关键字信息
                if (info != null) {
                    keywordBiz.deleteKeyword(id);// 如果所获取的关键字信息不为空，则删除该关键字
                    addMsg(modelMap, "删除关键字成功！");
                    if (logger.isDebugEnabled()) {
                        logger.debug("删除关键字成功！");
                    }
                } else {
                    addMsg(modelMap, "没有查到相关的记录！");
                    logger.info("该记录不存在");
                }
            } catch (Exception e) {
                logger.error("删除关键字出错", e);
                addErrorMsg(modelMap, "删除关键字出错");
            }
        } else {
            addErrorMsg(modelMap, "删除关键字出错，关键字ID为空");
        }
        if (StringHelper.isEmpty(pageId)) {
            pageId = 1;
        }
        PageResult<KeywordBO> pageResult = null;
        try {
            pageResult = keywordBiz.searchKeyword(keyword, appId, pageId);// 根据查询条件关键字，应用ID，当前页获取关键字信息
        } catch (Exception e) {
            logger.error("查询关键字出错", e);
            addErrorMsg(modelMap, "查询关键字出错");
        }
        List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();// 获取应用信息表全部主键ID，应用AppId和应用名name
        modelMap.addAttribute("result", pageResult);
        modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("appId", appId);
        modelMap.addAttribute("pageId", pageId);
        return "admin/keyword/keywordlist.vm";
    }
}
