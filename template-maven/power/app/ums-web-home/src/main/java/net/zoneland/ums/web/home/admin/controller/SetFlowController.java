/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.FlowConfigBiz;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.web.home.admin.form.FlowForm;
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
 *
 * @author gag
 * @version $Id: SetFlowController.java, v 0.1 2012-9-6 下午4:01:04 gag Exp $
 */
@Controller
public class SetFlowController extends BaseController {

    private static final Logger logger = Logger.getLogger(SetFlowController.class);

    @Autowired
    private FlowConfigBiz       flowConfigBiz;

    @RequestMapping(value = "admin/flow/updateflow.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, String status, String id, String appName,
                        FlowForm flowForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        if (!StringUtils.hasText(id)) {
            addErrorMsg(modelMap, "无法找到对应的应用");
            return "admin/flowconfig/updateflow.vm";
        }
        UmsAppInfo ums = new UmsAppInfo();
        try {
            ums = flowConfigBiz.findAppById(id);
        } catch (Exception e) {
            logger.error("流量时出现系统异常", e);
            addErrorMsg(modelMap, "系统异常！");
            return "admin/flowconfig/updateflow.vm";
        }
        flowForm.setAppId(ums.getAppId());
        flowForm.setAppName(ums.getAppName());
        flowForm.setFlowDay(getString(ums.getFlowDay()));
        flowForm.setFlowMonth(getString(ums.getFlowMonth()));
        flowForm.setId(ums.getId());
        modelMap.addAttribute("appInfo", ums);
        modelMap.addAttribute("pageId", pageId);
        modelMap.addAttribute("appName", appName);
        modelMap.addAttribute("status", status);
        modelMap.addAttribute("flowForm", flowForm);
        return "admin/flowconfig/updateflow.vm";
    }

    @RequestMapping(value = "admin/flow/updateflow.htm", method = RequestMethod.POST)
    public String doPost(@Valid FlowForm flowForm, BindingResult result, ModelMap modelMap) {

        if (!StringUtils.hasText(flowForm.getId())) {
            addErrorMsg(modelMap, "无法找到对应的应用");
            return "admin/flowconfig/updateflow.vm";
        }
        Integer flowDay = getInteger(flowForm.getFlowDay());
        Integer flowMonth = getInteger(flowForm.getFlowMonth());
        if (flowDay != null && flowMonth != null) {
            if (flowDay > flowMonth) {
                addErrorMsg(modelMap, "月流量要大于等于日流量！");
                return "admin/flowconfig/updateflow.vm";
            }
        }
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        umsAppInfo.setFlowDay(flowDay);
        umsAppInfo.setFlowMonth(flowMonth);
        umsAppInfo.setId(flowForm.getId());
        umsAppInfo.setGmtModified(new Date());
        try {
            int num = flowConfigBiz.modfiyAppFlow(umsAppInfo);//更新数据库，成功返回1
            if (num == 1) {
                addErrorMsg(modelMap, "流量设置成功");
            } else {
                addErrorMsg(modelMap, "流量设置失败");
            }
        } catch (Exception e) {
            logger.error("保存流量错误", e);
            addErrorMsg(modelMap, "流量设置失败。" + e.getMessage());
        }

        return "admin/flowconfig/updateflow.vm";
    }

    private Integer getInteger(String str) {
        if (StringUtils.hasText(str)) {
            try {
                int res = Integer.parseInt(str);
                return res;
            } catch (Exception e) {
                return 0;
            }
        }
        return null;
    }

    private String getString(Integer num) {
        if (num == null) {
            return null;
        }
        return String.valueOf(num);
    }

}
