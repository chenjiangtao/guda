/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.AppSubBiz;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.SubAppForm;
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
 * 子应用控制类
 * 
 * @author Administrator
 * @version $Id: SubAppController.java, v 0.1 2012-9-11 下午12:44:16 Administrator
 *          Exp $
 */
@Controller
@RequestMapping("/subApp")
public class SubAppController extends BaseController {

    private final static Logger logger = Logger.getLogger(AppController.class);

    @Autowired
    private AppSubBiz           appSubBiz;

    /**
     * 分页显示子应用
     * 
     * @param subAppForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm")
    public String doList(SubAppForm subAppForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        if (logger.isInfoEnabled()) {
            logger.info("分页显示子应用信息！");
        }
        String appId = request.getParameter("appId");
        String pageId = request.getParameter("pageId");
        String appName = request.getParameter("appName");
        int curPage = StringHelper.parseInt(pageId);
        PageResult<UmsAppSub> pageResult = new PageResult<UmsAppSub>();
        try {
            pageResult = appSubBiz.getAppSub(appId, curPage);
        } catch (Exception e) {
            logger.error("查询子系统出现异常", e);
            modelMap.addAttribute("message", "系统异常");
            return "/admin/app/subList.vm";
        }
        modelMap.addAttribute("results", pageResult);
        modelMap.addAttribute("appId", appId);
        modelMap.addAttribute("appName", appName);
        modelMap.addAttribute("pageId", pageId);
        return "/admin/app/subList.vm";
    }

    /**
     * 添加或者更新子应用<br/>
     * 1.验证输入数据的正确性<br/>
     * 2.判断ID的是否为空，用来决定是添加还是更新。<br/>
     * 3.执行添加或者更新。<br/>
     * 
     * @param subAppForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addOrUpdate.htm", method = RequestMethod.POST)
    public String doPost(@Valid SubAppForm subAppForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute("message", "数据格式不符合！");
            return "/admin/app/subList.vm";
        }
        if (StringHelper.isEmpty(subAppForm.getId())) {
            addPost(subAppForm, result, request, modelMap);
        } else {
            updatePost(subAppForm, result, request, modelMap);
        }
        String appId = subAppForm.getAppId();
        String pageId = request.getParameter("pageId");
        int page = StringHelper.parseInt(pageId);
        PageResult<UmsAppSub> pageResult = appSubBiz.getAppSub(appId, page);
        modelMap.addAttribute("results", pageResult);
        modelMap.addAttribute("appId", appId);
        return "/admin/app/subList.vm";
    }

    /**
     * 添加子应用<br/>
     * 1.判断父应用下的子应用ID是否已经存在。<br/>
     * 2.转化对象并且补充信息。<br/>
     * 3.添加子应用。<br/>
     * 4.返回也面。
     * 
     * @param subAppForm
     * @param result
     * @param request
     * @param modelMap
     */
    public void addPost(SubAppForm subAppForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        try {
            // 判断父应用下的子应用ID是否已经存在
            boolean exist = appSubBiz.isExist(subAppForm.getAppSubId(), subAppForm.getAppId());
            if (exist) {
                modelMap.addAttribute("message", "添加失败，子应用ID已经存在");
                return;
            }
            UmsAppSub umsAppSub = new UmsAppSub();
            umsAppSub = subAppForm.cloneUmsAppSub();
            if (StringHelper.isEmpty(subAppForm.getPriority())) {
                umsAppSub.setPriority(null);
            } else {
                umsAppSub.setPriority(Integer.valueOf(subAppForm.getPriority()));
            }
            appSubBiz.insert(umsAppSub);
        } catch (Exception e) {
            logger.error("添加子应用时出现系统异常！", e);
            modelMap.addAttribute("message", "添加失败！");
            return;
        }
        modelMap.addAttribute("message", "添加成功！");
        return;
    }

    /**
     * 更新子应用：<br/>
     * 1.先判断子应用ID是否改变，如果改变就判断子应用ID是否已经存在。<br/>
     * 2.如果存在就返回页面，如果不存在则转化对象比并且补充信息(例如，操作时间)。<br/>
     * 3.更新子应用。<br/>
     * 4.返回页面。
     * 
     * @param subAppForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    public void updatePost(SubAppForm subAppForm, BindingResult result, HttpServletRequest request,
                           ModelMap modelMap) {
        try {
            UmsAppSub test = appSubBiz.findById(subAppForm.getId());
            boolean exist = false;
            if (!test.getAppSubId().equalsIgnoreCase(subAppForm.getAppSubId())) {
                exist = appSubBiz.isExist(subAppForm.getAppSubId(), subAppForm.getAppId());
            }
            if (exist) {
                modelMap.addAttribute("message", "修改失败，子应用ID已经存在");
                return;
            }
            UmsAppSub umsAppSub = subAppForm.cloneUmsAppSub();
            if (StringHelper.isNotEmpty(subAppForm.getPriority())) {
                umsAppSub.setPriority(Integer.valueOf(subAppForm.getPriority()));
            } else {
                umsAppSub.setPriority(null);
            }
            Date date = new Date();
            umsAppSub.setGmtModified(date);
            appSubBiz.update(umsAppSub);
            modelMap.addAttribute("message", "修改成功！");
            return;
        } catch (Exception e) {
            logger.error("修改子应用时出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            return;
        }
    }

    /**
     * 删除子应用<br/>
     * 1.判断子应用是否存在。<br/>
     * 2.如果不存在就返回页面并且提示用户，如果存在则执行删除。<br/>
     * 3.返回页面。
     * 
     * @param appInfoForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.GET)
    public String delSubApp(SubAppForm subAppForm, BindingResult result,
                            HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        String pageId = request.getParameter("pageId");
        String appName = request.getParameter("appName");
        String appId = subAppForm.getAppId();
        int page = StringHelper.parseInt(pageId);
        modelMap.addAttribute("appId", appId);
        modelMap.addAttribute("appName", appName);
        try {
            UmsAppSub umsAppSub = appSubBiz.findById(id);
            if (umsAppSub == null) {
                modelMap.addAttribute("message", " 子应用不存在");
                PageResult<UmsAppSub> pageResult = appSubBiz.getAppSub(appId, page);
                modelMap.addAttribute("results", pageResult);
                return "/admin/app/subList.vm";
            }
            appSubBiz.deleteById(id);
        } catch (Exception e) {
            logger.error("删除子应用时出现系统异常", e);
            modelMap.addAttribute("message", "删除失败");
            PageResult<UmsAppSub> pageResult = appSubBiz.getAppSub(appId, page);
            modelMap.addAttribute("results", pageResult);
            return "/admin/app/subList.vm";
        }
        modelMap.addAttribute("message", "删除成功！");
        PageResult<UmsAppSub> pageResult = appSubBiz.getAppSub(appId, page);
        modelMap.addAttribute("results", pageResult);
        return "/admin/app/subList.vm";

    }

    @RequestMapping("/exist.ajax")
    public void addAppIdExist(HttpServletRequest request, HttpServletResponse response) {
        String appSubId = StringUtils.trim(request.getParameter("subAppId"));
        String appId = StringUtils.trim(request.getParameter("appId"));
        String id = StringUtils.trim(request.getParameter("id"));
        if (StringUtils.isEmpty(id)) {
            boolean exist = appSubBiz.isExist(appSubId, appId);
            if (exist) {
                ajaxReturn("exist", response);
            } else {
                ajaxReturn("notExist", response);
            }
        } else {
            UmsAppSub test = appSubBiz.findById(id);
            boolean exist = false;
            if (!test.getAppSubId().equalsIgnoreCase(appSubId)) {
                exist = appSubBiz.isExist(appSubId, appId);
            }
            if (exist) {
                ajaxReturn("exist", response);
            } else {
                ajaxReturn("notExist", response);
            }
        }
    }
}
