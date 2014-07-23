/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.AllAppMsgBiz;
import net.zoneland.ums.biz.config.admin.BlackListBiz;
import net.zoneland.ums.common.dal.bo.UmsBlackListBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.BlackListForm;
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
 * 黑名单控制层
 * @author XuFan
 * @version $Id: BlackListController.java, v 0.1 Aug 20, 2012 4:37:22 PM XuFan Exp $
 */
@Controller
public class BlackListController extends BaseController {

    private static final Logger logger             = Logger.getLogger(BlackListController.class);

    private static final int    BLACKLISTALLLENGTH = 500;
    @Autowired
    private AllAppMsgBiz        allAppMsgBiz;

    @Autowired
    private BlackListBiz        blackListBiz;

    /**
     * 拦截黑名单list的get请求，分页显示黑名单信息
     * 
     * @param blackListForm 黑名单表单信息
     * @param pageId 当前页
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/blacklist/blacklist.htm", method = RequestMethod.GET)
    public String doGet(BlackListForm blackListForm, Integer pageId, ModelMap modelMap) {
        if (StringHelper.isEmpty(pageId) || pageId < 1) {// 如果获取当前页为空
            pageId = 1;// 设置当前页为第一页
        }
        PageResult<UmsBlackListBO> pageResult = null;
        List<UmsAppInfo> appList = new ArrayList<UmsAppInfo>();
        try {
            appList = allAppMsgBiz.getAllApp();// 获取全部应用的主键ID，应用appId和应用名name
            pageResult = blackListBiz.searchByPage(
                // 分页查询黑名单
                StringUtils.deleteWhitespace(blackListForm.getUserId()),
                StringUtils.deleteWhitespace(blackListForm.getAppId()), pageId);
        } catch (Exception e) {
            logger.error("查询黑名单出错", e);
            addErrorMsg(modelMap, "查询黑名单出错");
        }
        modelMap.addAttribute("result", pageResult);// 分页结果集
        modelMap.addAttribute("userId", blackListForm.getUserId());// 绑定黑名单手机号
        modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
        modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
        modelMap.addAttribute("pageId", pageId);// 绑定当前页
        return "admin/blacklist/blacklist.vm";
    }

    /**
     * 拦截黑名单list的post请求，根据查询条件分页显示黑名单信息
     * 
     * @param blackListForm 黑名单表单信息
     * @param result 绑定数据验证
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/blacklist/blacklist.htm", method = RequestMethod.POST)
    public String doPost(BlackListForm blackListForm, Integer pageId, BindingResult result,
                         ModelMap modelMap) {
        PageResult<UmsBlackListBO> pageResult = null;
        try {
            if (pageId == null || pageId < 1) {
                pageId = 1;
            }
            blackListForm.setUmsAppInfos(allAppMsgBiz.getAllApp());// 获取全部应用的主键ID，应用appId和应用名name
            pageResult = blackListBiz.searchByPage(
                // 分页查询黑名单
                StringUtils.deleteWhitespace(blackListForm.getUserId()),
                StringUtils.deleteWhitespace(blackListForm.getAppId()), pageId);
        } catch (Exception e) {
            logger.error("查询黑名单出错", e);
            addErrorMsg(modelMap, "查询黑名单出错");
        }
        modelMap.addAttribute("result", pageResult);// 分页结果集
        modelMap.addAttribute("userId", blackListForm.getUserId());// 绑定黑名单手机号
        modelMap.addAttribute("apps", blackListForm.getUmsAppInfos());// 根据集合获取应用下拉框
        modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
        modelMap.addAttribute("pageId", blackListForm.getCurPage());// 绑定当前页
        return "admin/blacklist/blacklist.vm";
    }

    /**
     * 拦截添加黑名单get请求，获取添加黑名单页面信息
     * 
     * @param blackListForm 黑名单表单信息
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/blacklist/addlist.htm", method = RequestMethod.GET)
    public String saveBlackList(BlackListForm blackListForm, ModelMap modelMap) {
        List<UmsAppInfo> appList = new ArrayList<UmsAppInfo>();
        try {
            appList = allAppMsgBiz.getAllApp();// 获取全部应用的主键ID，应用appId和应用名name
        } catch (Exception e) {
            logger.error("添加黑名单出错", e);
            addErrorMsg(modelMap, "添加黑名单出错");
        }
        modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
        modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
        return "admin/blacklist/addlist.vm";
    }

    /**
     * 拦截添加黑名单post请求，根据规则添加具体黑名单手机号
     * 
     * @param blackListForm 黑名单表单信息
     * @param result 绑定数据验证
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/blacklist/addlist.htm", method = RequestMethod.POST)
    public String saveBlackList(@Valid BlackListForm blackListForm, BindingResult result,
                                ModelMap modelMap) {
        blackListForm.setUmsAppInfos(allAppMsgBiz.getAllApp());// 获取全部应用的主键ID，应用appId和应用名name
        if (StringUtils.isEmpty(blackListForm.getUserId())) {// 判断输入的黑名单手机号是否为空
            modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
            modelMap.addAttribute("apps", blackListForm.getUmsAppInfos());// 根据集合获取应用下拉框 
            addErrorMsg(modelMap, "保存错误，输入手机号不能为空");
            return "admin/blacklist/addlist.vm";
        }
        if (StringUtils.isEmpty(blackListForm.getAppId())) {// 判断是否在下拉框中有选择应用
            modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
            modelMap.addAttribute("apps", blackListForm.getUmsAppInfos());// 根据集合获取应用下拉框
            addErrorMsg(modelMap, "请选择要屏蔽的应用");
            return "admin/blacklist/addlist.vm";
        }
        if (blackListForm.getUserId().length() > BLACKLISTALLLENGTH) {// 判断输入总长度是否大于500个字符
            modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
            modelMap.addAttribute("apps", blackListForm.getUmsAppInfos());// 根据集合获取应用下拉框
            addErrorMsg(modelMap, "输入总长度不能超过500个字符");
            return "admin/blacklist/addlist.vm";
        }
        String[] userIds = blackListForm.getUserId().split(",");// 以逗号分割输入手机号
        try {
            String res = blackListBiz.saveBlackList(userIds, blackListForm.getAppId());// 保存手机号到黑名单表
            addErrorMsg(modelMap, res);
            logger.info("保存黑名单手机号成功");
        } catch (Exception e) {
            logger.error("保存黑名单手机号出错", e);
            addErrorMsg(modelMap, "保存黑名单手机号出错");
        }
        modelMap.addAttribute("appId", blackListForm.getAppId());// 绑定应用ID
        modelMap.addAttribute("apps", blackListForm.getUmsAppInfos());// 根据集合获取应用下拉框
        return "admin/blacklist/addlist.vm";
    }

    /**
     * 拦截删除黑名单的get请求，根据主键ID删除当前所选黑名单手机号
     * 
     * @param blackListForm 黑名单表单信息
     * @param modelMap ModelMap视图对象
     * @param request request请求
     * @return
     */
    @RequestMapping(value = "admin/blacklist/deleteblacklist.htm", method = RequestMethod.GET)
    public String deleteBlackList(BlackListForm blackListForm, ModelMap modelMap,
                                  HttpServletRequest request) {
        String id = request.getParameter("id");
        String userId = request.getParameter("userId");
        String appId = request.getParameter("appId");
        String pageId = request.getParameter("pageId");
        PageResult<UmsBlackListBO> pageResult = null;
        if (StringUtils.isNotEmpty(id)) {
            try {
                boolean res = blackListBiz.deleteBlackList(id);// 根据当前所选黑名单手机号ID在黑名单列表中删除该手机号
                if (res) {
                    addErrorMsg(modelMap, "黑名单删除成功!");
                } else {
                    addErrorMsg(modelMap, "黑名单删除失败!");
                }
                if (StringHelper.isNotEmpty(pageId)) {
                    int curPageId = Integer.parseInt(pageId);
                    pageResult = blackListBiz.searchByPage(userId, appId, curPageId);// 分页显示黑名单列表
                } else {
                    pageResult = blackListBiz.searchByPage(userId, appId, 1);
                }
                List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();// 获取全部应用的主键ID，应用appId和应用名name
                modelMap.addAttribute("result", pageResult);
                modelMap.addAttribute("userId", userId);
                modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
                modelMap.addAttribute("appId", appId);// 绑定应用ID
                modelMap.addAttribute("pageId", pageId);
            } catch (Exception e) {
                logger.error("删除黑名单出错", e);
                addErrorMsg(modelMap, "删除黑名单手机号出错");
            }
        }
        return "admin/blacklist/blacklist.vm";
    }
}
