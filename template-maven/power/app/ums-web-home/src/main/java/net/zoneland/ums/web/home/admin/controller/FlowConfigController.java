/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.admin.FlowConfigBiz;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.enums.app.AppStateEnum;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.appadmin.form.AppInfoForm;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 流量配置控制层
 * 
 * @author XuFan
 * @version $Id: FlowConfigController.java, v 0.1 Aug 27, 2012 10:10:31 AM XuFan
 *          Exp $
 */
@Controller
public class FlowConfigController extends BaseController {

    private static final Logger logger = Logger.getLogger(FlowConfigController.class);

    @Autowired
    private FlowConfigBiz       flowConfigBiz;

    /**
     * 拦截流量配置页面get请求
     * 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "admin/flow/flowlist.htm", method = RequestMethod.GET)
    public String doGet(String appName, String status, Integer pageId, ModelMap modelMap) {
        if (pageId == null || pageId < 1) {
            pageId = 1;
        }
        PageResult<UmsAppInfo> result = null;

        try {
            result = flowConfigBiz.findAppWithPage(StringUtils.trimAllWhitespace(appName),
                StringUtils.trimAllWhitespace(status), pageId);
        } catch (Exception e) {
            logger.error("查询全部应用出错", e);
            addErrorMsg(modelMap, "查询全部应用出错");
        }
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("appName", appName);
        modelMap.addAttribute("status", status);
        modelMap.addAttribute("stateEnum", AppStateEnum.values());
        return "admin/flowconfig/flowlist.vm";
    }

    /**
     * 拦截流量配置页面post请求
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "admin/flow/flowlist.htm", method = RequestMethod.POST)
    public String doPost(AppInfoForm form, HttpServletRequest request, ModelMap modelMap) {

        PageResult<UmsAppInfo> result = null;
        try {
            int curPage = parseInt(form.getPageId());
            result = flowConfigBiz.findAppWithPage(
                StringUtils.trimAllWhitespace(form.getAppName()),
                StringUtils.trimAllWhitespace(form.getStatus()), curPage);
        } catch (Exception e) {
            logger.error("查询全部应用出错", e);
            addErrorMsg(modelMap, "查询全部应用出错");
        }

        modelMap.addAttribute("result", result);
        modelMap.addAttribute("appName", form.getAppName());
        modelMap.addAttribute("status", form.getStatus());
        modelMap.addAttribute("stateEnum", AppStateEnum.values());
        return "admin/flowconfig/flowlist.vm";
    }

    @RequestMapping("admin/flow/flowSubmit.ajax")
    public String modfiyFlow(HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap) throws IOException {

        String flowDay = request.getParameter("flowDay");// 获取日流量
        String flowMonth = request.getParameter("flowMonth");// 获取月流量
        String id = request.getParameter("id");// 获取id
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        umsAppInfo.setFlowDay("".equals(flowDay) ? null : Integer.parseInt(flowDay));
        umsAppInfo.setFlowMonth("".equals(flowMonth) ? null : Integer.parseInt(flowMonth));
        umsAppInfo.setId(id);
        umsAppInfo.setGmtModified(new Date());
        int num = 0;
        try {
            num = flowConfigBiz.modfiyAppFlow(umsAppInfo);// 更新数据库，成功返回1
        } catch (Exception e) {
            logger.error("数据库出错", e);
            addErrorMsg(modelMap, "数据库出错");
            return "admin/flowconfig/flowlist.vm";
        }

        if (num == 1) {
            String msg = "流量配置成功!";
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            String msg = "流量配置失败!";
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            response.getWriter().close();
        }
        return "admin/flowconfig/flowlist.vm";
    }

    /**
     * 转化第几页
     * 
     * @param pageId
     * @return
     */
    private int parseInt(String pageId) {
        if (!StringUtils.hasText(pageId)) {
            return 1;
        }
        int curPage = Integer.parseInt(pageId);
        if (curPage <= 1) {// 如果输入的页数比1小，则返回第一页
            return 1;
        }
        try {
            return curPage;
        } catch (Exception e) {
            return 1;
        }
    }
}
