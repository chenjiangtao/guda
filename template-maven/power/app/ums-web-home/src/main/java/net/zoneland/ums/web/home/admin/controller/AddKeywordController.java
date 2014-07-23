/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.AllAppMsgBiz;
import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.web.home.admin.form.KeyWordInfoForm;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author gag
 * @version $Id: AddKeywordController.java, v 0.1 2012-9-6 下午1:22:51 gag Exp $
 */
@Controller
public class AddKeywordController extends BaseController {

    private final static Logger logger        = Logger.getLogger(AddKeywordController.class);

    private final static int    KEYWORDLENGTH = 500;

    @Autowired
    private KeywordBiz          keywordBiz;

    @Autowired
    private AllAppMsgBiz        allAppMsgBiz;

    /**
     * 拦截添加关键字的get请求，获取添加关键字页面信息
     * 
     * @param keyWordInfoForm 关键字表单信息
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/keyword/keywordsave.htm", method = RequestMethod.GET)
    public String tosaveKeyword(KeyWordInfoForm keyWordInfoForm, ModelMap modelMap) {
        List<UmsAppInfo> appList = new ArrayList<UmsAppInfo>();
        try {
            appList = allAppMsgBiz.getAllApp();// 获取应用信息表全部主键ID，应用AppId和应用名name
        } catch (Exception e) {
            logger.error("保存关键字出错", e);
            addErrorMsg(modelMap, "保存关键字出错");
        }
        modelMap.addAttribute("appId", keyWordInfoForm.getAppId());// 绑定所获取的应用ID
        modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
        return "admin/keyword/addkeyword.vm";

    }

    /**
     * 拦截保存关键字的post请求，根据规则添加具体关键字
     * 
     * @param keyWordInfoForm 关键字表单信息
     * @param result 绑定数据验证
     * @param modelMap ModelMap试图对象
     * @return
     */
    @RequestMapping(value = "admin/keyword/keywordsave.htm", method = RequestMethod.POST)
    public String tosaveKeyword(@Valid KeyWordInfoForm keyWordInfoForm, BindingResult result,
                                ModelMap modelMap) {
        List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();// 获取应用信息表全部主键ID，应用AppId和应用名name   
        if (StringUtils.isEmpty(keyWordInfoForm.getKeyword())) {// 如果表单所获取的关键字是空值
            modelMap.addAttribute("appId", keyWordInfoForm.getAppId());// 绑定所获取的应用ID
            modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
            addErrorMsg(modelMap, "保存错误，输入关键字不能为空");
            return "admin/keyword/addkeyword.vm";
        }
        if (StringUtils.isEmpty(keyWordInfoForm.getAppId())) {// 如果表单所获取的应用ID是空值
            modelMap.addAttribute("appId", keyWordInfoForm.getAppId());// 绑定所获取的应用ID
            modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
            addErrorMsg(modelMap, "请选择要添加的应用");
            return "admin/keyword/addkeyword.vm";
        }
        if (keyWordInfoForm.getKeyword().length() > KEYWORDLENGTH) {// 如果表单所获取的关键字总长度大于500个字符
            modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
            modelMap.addAttribute("appId", keyWordInfoForm.getAppId());// 绑定所获取的应用ID
            addErrorMsg(modelMap, "输入总长度不能超过500个字符");
            return "admin/keyword/addkeyword.vm";
        }
        String[] keys = keyWordInfoForm.getKeyword().split(",");// 关键字以逗号分割
        try {
            String res = keywordBiz.saveKeyword(keyWordInfoForm.getAppId(), keys);// 保存关键字
            addErrorMsg(modelMap, res);
            logger.info("保存关键字成功");
        } catch (Exception e) {
            logger.error("保存关键字出错", e);
            addErrorMsg(modelMap, "保存关键字出错");
        }
        modelMap.addAttribute("appId", keyWordInfoForm.getAppId());// 绑定所获取的应用ID
        modelMap.addAttribute("apps", appList);// 绑定集合提供给应用下拉框
        return "admin/keyword/addkeyword.vm";
    }
}
