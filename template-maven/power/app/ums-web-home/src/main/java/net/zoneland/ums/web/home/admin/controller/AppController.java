/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.AppBiz;
import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.MD5;
import net.zoneland.ums.common.util.enums.GateOutProvEnum;
import net.zoneland.ums.common.util.enums.app.AppStateEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.AppInfoForm;
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
 * 应用管理控制器：增删改查。
 * 
 * @author wangyong
 * @version $Id: AppController.java, v 0.1 2012-9-1 下午8:53:12 wangyong Exp $
 */
@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

    private final static Logger logger = Logger.getLogger(AppController.class);

    @Autowired
    private AppBiz              appBiz;

    /**
     * 分页显示应用信息<br/>
     * 1.页面上不输入数据的话，但是仍是空格不是空。所以我们要做判断并且转化。<br/>
     * 
     * @param appInfoForm
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm")
    public String doList(AppInfoForm appInfoForm, HttpServletRequest request, ModelMap modelMap) {
        if (logger.isInfoEnabled()) {
            logger.info("分页显示应用信息！");
        }
        if ("".equalsIgnoreCase(appInfoForm.getAppName())) {
            appInfoForm.setAppName(null);
        }
        if ("".equalsIgnoreCase(appInfoForm.getAppId())) {
            appInfoForm.setAppId(null);
        }
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        UmsAppInfo umsAppInfo = appInfoForm.cloneUmsAppInfo();
        PageResult<UmsAppInfo> pageResult = null;
        try {
            pageResult = appBiz.showAllApp(umsAppInfo, page);
        } catch (Exception e) {
            logger.error("数据库异常！", e);
            modelMap.addAttribute("message", "数据库异常");
            return "/admin/app/appList.vm";
        }
        String localAppId = UmsConstants.APP_ID;
        modelMap.addAttribute("localAppId", localAppId);
        modelMap.addAttribute("results", pageResult);
        modelMap.addAttribute("enable", AppStateEnum.ENABLED.getValue());
        return "/admin/app/appList.vm";
    }

    /**
     * get方式拦截增加应用的请求，跳转页面
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    public String add(AppInfoForm appInfoForm, BindingResult result, HttpServletRequest request,
                      ModelMap modelMap) {
        modelMap.addAttribute("outProv", GateOutProvEnum.values());
        return "/admin/app/add.vm";
    }

    /**
     * post方式增加应用<br/>
     * 1.验证输入数据的正确性。<br/>
     * 2.要判断月流量大于日流量。<br/>
     * 3.判断应用ID是否存在。<br/>
     * 4.对象转化<br/>
     * 5.添加应用。<br/>
     * 6.返回添加页面，并且提示信息。
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public String addApp(@Valid AppInfoForm appInfoForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute("message", "数据格式有误");
            return "/admin/app/add.vm";
        }

        String flowDay = appInfoForm.getFlowDay();
        String flowMontn = appInfoForm.getFlowMonth();
        if (StringHelper.isNotEmpty(flowDay) && StringHelper.isNotEmpty(flowMontn)) {
            if (Integer.valueOf(flowDay) > Integer.valueOf(flowMontn)) {
                modelMap.addAttribute("message", "月流量要大于日流量");
                return "/admin/app/add.vm";
            }
        }
        try {
            boolean exist = appBiz.isExist(appInfoForm.getAppId());
            if (exist) {
                modelMap.addAttribute("message", "添加失败，应用ID已经存在");
                return "/admin/app/add.vm";
            }
            UmsAppInfo umsAppInfo = appInfoForm.cloneUmsAppInfo();
            parstInteger(appInfoForm, umsAppInfo);
            appBiz.insert(umsAppInfo);
            modelMap.addAttribute("umsAppInfo", umsAppInfo);
            modelMap.addAttribute("outProv", GateOutProvEnum.values());
            return "/admin/app/addSuccess.vm";
        } catch (Exception e) {
            logger.error("系统异常！", e);
            modelMap.addAttribute("message", "添加失败！");
        }
        return "/admin/app/add.vm";
    }

    /**
     * get方式拦截更新应用请求，跳转页面
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.GET)
    public String updateGet(AppInfoForm appInfoForm, BindingResult result,
                            HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        modelMap.addAttribute("outProv", GateOutProvEnum.values());
        if (StringUtils.isEmpty(id)) {
            modelMap.addAttribute("message", "请选择应用！");
            return "/admin/app/update.vm";
        }
        UmsAppInfo umsAppInfo = null;
        try {
            umsAppInfo = appBiz.selectById(id);
        } catch (Exception e) {
            logger.error("数据库异常", e);
            modelMap.addAttribute("message", "数据库异常！");
            return "/admin/app/update.vm";
        }

        ObjectBuilder.copyObject(umsAppInfo, appInfoForm);
        parstString(umsAppInfo, appInfoForm);
        return "/admin/app/update.vm";
    }

    /**
     * Post方式方式更新应用<br/>
     * 1.验证输入数据的正确性。<br/>
     * 2.要判断月流量大于日流量。<br/>
     * 3.判断应用ID是否存在与添加的话是有点区别，就是先判断ID是否改变， 如果没有改变的话不判断是否存在，如果改变了的话就判断是否存在。<br/>
     * 4.对象转化，并且补充信息。<br/>
     * 5.更新对象信息<br/>
     * 6.返回更新页面并且提示信息。
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public String updateApp(@Valid AppInfoForm appInfoForm, BindingResult result,
                            HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute("message", "数据格式有误");
            return "/admin/app/update.vm";
        }
        if ("".equalsIgnoreCase(appInfoForm.getId()) || appInfoForm.getId() == null) {
            modelMap.addAttribute("message", "请选择更新的应用");
            return "/admin/app/update.vm";
        }

        String flowDay = appInfoForm.getFlowDay();
        String flowMontn = appInfoForm.getFlowMonth();
        if (StringHelper.isNotEmpty(flowDay) && StringHelper.isNotEmpty(flowMontn)) {
            if (Integer.valueOf(flowDay) > Integer.valueOf(flowMontn)) {
                modelMap.addAttribute("message", "月流量要大于日流量");
                return "/admin/app/update.vm";
            }
        }
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        modelMap.addAttribute("outProv", GateOutProvEnum.values());
        try {
            UmsAppInfo test = appBiz.selectById(appInfoForm.getId());
            boolean exist = false;
            if (test == null) {
                modelMap.addAttribute("message", "修改失败，应用已不存在！");
                return "/admin/app/update.vm";
            }
            if (!test.getAppId().equalsIgnoreCase(appInfoForm.getAppId())) {
                exist = appBiz.isExist(appInfoForm.getAppId());
            }
            if (exist) {
                modelMap.addAttribute("message", "修改失败，应用ID已经存在");
                return "/admin/app/update.vm";
            }
            umsAppInfo = appInfoForm.cloneUmsAppInfo();
            // 对一些对应属性的但是不同类型的非空字符串进行转化
            parstInteger(appInfoForm, umsAppInfo);
            // 判断密码有没变化，如果有变化的话就重新设置
            if (test.getPassword().equalsIgnoreCase(appInfoForm.getPassword())) {
                umsAppInfo.setPassword(test.getPassword());
            } else {
                String password = MD5.md5(umsAppInfo.getPassword());
                umsAppInfo.setPassword(password);
            }
            umsAppInfo.setGmtModified(new Date());
            boolean update = appBiz.updateApp(umsAppInfo);
            if (!update) {
                modelMap.addAttribute("message", "修改失败，可能应用已经不存在！");
                return "/admin/app/update.vm";
            }
            modelMap.addAttribute("umsAppInfo", umsAppInfo);
            return "/admin/app/updateSuccess.vm";
        } catch (Exception e) {
            logger.error("系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
        }
        return "/admin/app/update.vm";
    }

    /**
     * 修改状态<br/>
     * 1.先查询当前状态。<br/>
     * 2.改变当前状态<br/>
     * 3.返回修改结果
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping(value = "/status.ajax", method = RequestMethod.POST)
    public void statusGet(AppInfoForm appInfoForm, BindingResult result,
                          HttpServletRequest request, HttpServletResponse response,
                          ModelMap modelMap) {
        String id = request.getParameter("id");
        String test = "";
        try {
            test = appBiz.changeStatusById(id);
        } catch (Exception e) {
            logger.error("系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            ajaxReturn("3", response);
        }
        modelMap.addAttribute("message", "状态修改成功！");
        ajaxReturn(test, response);
    }

    /**
     * 删除应用<br/>
     * 1.判断当前应用是否存在。 2.如果存在就删除当前应用，如果不存在的话就提示信息。
     * 3.删除当前应用的话，会同时删除黑名单应用，关键词应用及子应用。
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.GET)
    public String delApp(AppInfoForm appInfoForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        String id = request.getParameter("id");
        String pageId = request.getParameter("pageId");
        String appName = request.getParameter("appName");
        String appId = request.getParameter("appId");
        appInfoForm.setAppName(appName);
        appInfoForm.setAppId(appId);
        int page = parseInt(pageId);
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        ObjectBuilder.copyObject(appInfoForm, umsAppInfo);
        UmsAppInfo test = null;
        try {
            test = appBiz.selectById(id);
            if (test == null) {
                modelMap.addAttribute("message", "应用不存在");
                PageResult<UmsAppInfo> pageResult = appBiz.showAllApp(umsAppInfo, page);
                modelMap.addAttribute("results", pageResult);
                return "/admin/app/appList.vm";
            }
            appBiz.delAppById(id);// 通过id删除相关应用
        } catch (Exception e) {
            logger.error("删除应用出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            PageResult<UmsAppInfo> pageResult = new PageResult<UmsAppInfo>();
            modelMap.addAttribute("results", pageResult);
            return "/admin/app/appList.vm";
        }

        modelMap.addAttribute("message", "删除成功！");
        PageResult<UmsAppInfo> pageResult = appBiz.showAllApp(umsAppInfo, page);
        modelMap.addAttribute("results", pageResult);
        return "/admin/app/appList.vm";

    }

    /**
     * 把appInfoForm中与umsAppInfod对象属性的非空的字符串转化为Integer类型
     * 
     * @param appInfoForm
     * @param umsAppInfo
     * @return
     */
    private UmsAppInfo parstInteger(AppInfoForm appInfoForm, UmsAppInfo umsAppInfo) {
        if (StringHelper.isNotEmpty(appInfoForm.getFlowDay())) {
            umsAppInfo.setFlowDay(Integer.valueOf(appInfoForm.getFlowDay()));
        }
        if (StringHelper.isNotEmpty(appInfoForm.getFlowMonth())) {
            umsAppInfo.setFlowMonth(Integer.valueOf(appInfoForm.getFlowMonth()));
        }
        if (StringHelper.isNotEmpty(appInfoForm.getFee())) {
            umsAppInfo.setFee(Integer.valueOf(appInfoForm.getFee()));
        }
        if (StringHelper.isNotEmpty(appInfoForm.getFeeType())) {
            umsAppInfo.setFeeType(Integer.valueOf(appInfoForm.getFeeType()));
        }
        if (StringHelper.isNotEmpty(appInfoForm.getPriority())) {
            umsAppInfo.setPriority(Integer.valueOf(appInfoForm.getPriority()));
        }
        return umsAppInfo;
    }

    private UmsAppInfo parstString(UmsAppInfo umsAppInfo, AppInfoForm appInfoForm) {
        if (umsAppInfo.getFlowDay() != null) {
            appInfoForm.setFlowDay(String.valueOf(umsAppInfo.getFlowDay()));
        }
        if (umsAppInfo.getFlowMonth() != null) {
            appInfoForm.setFlowMonth(String.valueOf(umsAppInfo.getFlowMonth()));
        }
        if (umsAppInfo.getFee() != null) {
            appInfoForm.setFee(String.valueOf(umsAppInfo.getFee()));
        }
        if (umsAppInfo.getFeeType() != null) {
            appInfoForm.setFeeType(String.valueOf(umsAppInfo.getFeeType()));
        }
        if (umsAppInfo.getPriority() != null) {
            appInfoForm.setPriority(String.valueOf(umsAppInfo.getPriority()));
        }
        return umsAppInfo;
    }

    /**
     * 转化第几页
     * 
     * @param pageId
     * @return
     */
    private int parseInt(String pageId) {
        if (StringUtils.isBlank(pageId)) {
            return 1;
        }
        try {
            int curPage = Integer.parseInt(pageId);
            if (curPage <= 1) {// 如果输入的页数比1小，则返回第一页
                return 1;
            }
            return curPage;
        } catch (Exception e) {
            return 1;
        }
    }
}
