/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.gateway.GatewayRuleService;
import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayRule;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.GateWayRuleForm;

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
 * @author wangyong
 * @version $Id: GatewayRuleController.java, v 0.1 2012-8-31 下午4:44:56 wangyong Exp $
 */
@Controller
@RequestMapping("/gatewayRule")
public class GatewayRuleController {

    public static final Logger logger = Logger.getLogger(GateWayController.class);

    @Autowired
    private GatewayService     gatewayService;

    @Autowired
    private GatewayRuleService gatewayRuleService;

    /**
     *显示网关规则<br/>
     *要进行对象转化，在规则里添加网关名字与网关类型。
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        PageResult<UmsGatewayRule> pageResult = new PageResult<UmsGatewayRule>();
        try {
            pageResult = gatewayRuleService.findRule(page);
        } catch (Exception e) {
            logger.error("显示网关规则的时候出现系统异常", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/admin/gateway/ruleList.vm";
        }
        List<GateWayRuleForm> ruleFormList = change(pageResult.getResults());
        PageResult<GateWayRuleForm> result = new PageResult<GateWayRuleForm>();
        ObjectBuilder.copyObject(pageResult, result);
        result.setResults(ruleFormList);
        modelMap.addAttribute("results", result);
        return "/admin/gateway/ruleList.vm";
    }

    /**
     *跳转到添加页面
     *
     * @param gateWayRuleForm
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    public String addGet(GateWayRuleForm gateWayRuleForm, HttpServletRequest request,
                         ModelMap modelMap) {
        List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
        modelMap.addAttribute("gateways", gatewayList);
        return "/admin/gateway/ruleAdd.vm";
    }

    /**
     *增加网关规则<br/>
     *1.先验证输入数据的正确性。<br/>
     *2.添加网关规则。<br>
     *3.返回添加页面并且提示成功与否
     * @param gateWayRuleForm
     * @param request
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public String addPost(@Valid GateWayRuleForm gateWayRuleForm, BindingResult result,
                          HttpServletRequest request, ModelMap modelMap) {
        //验证输入数据的正确性
        if (result.hasErrors()) {
            List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            modelMap.addAttribute("message", "数据格式有误！");
            return "/admin/gateway/ruleAdd.vm";
        }
        //插入网关规则   
        try {
            UmsGatewayRule umsGatewayRule = gateWayRuleForm.cloneUmsGateWayRule();
            boolean res = gatewayRuleService.add(umsGatewayRule);
            if (res) {
                modelMap.addAttribute("umsGatewayRule", umsGatewayRule);
                return "/admin/gateway/ruleAddSuccess.vm";
            }
            List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            modelMap.addAttribute("message", "添加失败!");
            return "/admin/gateway/ruleAdd.vm";
        } catch (Exception e) {
            logger.error("添加网关规则出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常，添加失败!");
            List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            return "/admin/gateway/ruleAdd.vm";
        }
    }

    /**
     *跳转到更新页面
     *
     * @param gateWayRuleForm
     * @param request
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.GET)
    public String updateGet(GateWayRuleForm gateWayRuleForm, HttpServletRequest request,
                            BindingResult result, ModelMap modelMap) {
        String id = request.getParameter("ruleId");
        if (StringUtils.isEmpty(id)) {
            modelMap.addAttribute("message", "请选择规则！");
            return "/admin/gateway/ruleUpdate.vm";
        }
        UmsGatewayRule umsGatewayRule = null;
        List<UmsGateWayInfo> gatewayList = null;
        gateWayRuleForm.setId(id);
        try {
            umsGatewayRule = gatewayRuleService.findGatewayRuleById(id);
            gatewayList = gatewayService.findAll();

        } catch (Exception e) {
            modelMap.addAttribute("message", "数据库出错！");
            return "/admin/gateway/ruleUpdate.vm";
        }
        modelMap.addAttribute("gateways", gatewayList);
        ObjectBuilder.copyObject(umsGatewayRule, gateWayRuleForm);
        return "/admin/gateway/ruleUpdate.vm";
    }

    /**
     *更新网关规则：<br/>
     *1.验证数据的准确性。<br/>
     *2.转化对象。<br/>
     *3.更新网关规则。
     * @param gateWayRuleForm
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public String updatePost(@Valid GateWayRuleForm gateWayRuleForm, BindingResult result,
                             HttpServletRequest request, ModelMap modelMap) {
        //验证输入数据的正确性
        if (result.hasErrors()) {
            List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            return "/admin/gateway/ruleUpdate.vm";
        }
        //更新网关规则
        try {
            UmsGatewayRule umsGatewayRule = gateWayRuleForm.cloneUmsGateWayRule();
            boolean res = gatewayRuleService.update(umsGatewayRule);
            if (res) {
                modelMap.addAttribute("umsGatewayRule", umsGatewayRule);
                return "/admin/gateway/ruleUpdateSuccess.vm";
            }
            List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            modelMap.addAttribute("message", "修改失败!");
            return "/admin/gateway/ruleUpdate.vm";
        } catch (Exception e) {
            logger.error("更新网关规则出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常，修改失败!");
            List<UmsGateWayInfo> gatewayList = gatewayService.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            return "/admin/gateway/ruleUpdate.vm";
        }

    }

    /**
     *删除网关规则<br/>
     *1.获得当前页<br/>
     *2.检查规则是否存在。<br/>
     *3.如果存在就去删除规则，如果不存在就提示用户。<br/>
     *4.返回之前页面。
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.GET)
    public String del(HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        try {
            boolean exist = gatewayRuleService.isExist(id);
            if (!exist) {
                modelMap.addAttribute("message", "规则不存在");
                PageResult<GateWayRuleForm> result = getResult(page);
                modelMap.addAttribute("results", result);
                return "/admin/gateway/ruleList.vm";
            }
            gatewayRuleService.delRule(id);
        } catch (Exception e) {
            logger.error("删除网关规则时出现系统异常", e);
            PageResult<GateWayRuleForm> result = new PageResult<GateWayRuleForm>();
            modelMap.addAttribute("message", "删除失败");
            modelMap.addAttribute("results", result);
            return "/admin/gateway/ruleList.vm";
        }
        modelMap.addAttribute("message", "删除成功");
        PageResult<GateWayRuleForm> result = getResult(page);
        modelMap.addAttribute("results", result);
        return "/admin/gateway/ruleList.vm";
    }

    private int parseInt(String pageId) {
        if (StringUtils.isBlank(pageId)) {
            return 1;
        }
        try {
            int curPage = Integer.parseInt(pageId);
            if (curPage <= 1) {
                return 1;
            }
            return curPage;
        } catch (Exception e) {
            return 1;
        }
    }

    private PageResult<GateWayRuleForm> getResult(int page) {
        PageResult<UmsGatewayRule> pageResult = gatewayRuleService.findRule(page);
        List<GateWayRuleForm> ruleFormList = change(pageResult.getResults());
        PageResult<GateWayRuleForm> result = new PageResult<GateWayRuleForm>();
        ObjectBuilder.copyObject(pageResult, result);
        result.setResults(ruleFormList);
        return result;
    }

    /**
     *对象转化：<br/>
     *1.在规则里添加网关名字与网关类型。
     *
     * @param ruleList
     * @return
     */
    private List<GateWayRuleForm> change(List<UmsGatewayRule> ruleList) {

        List<GateWayRuleForm> ruleFormList = new ArrayList<GateWayRuleForm>();
        if (ruleList == null) {
            return ruleFormList;
        }
        for (int i = 0, size = ruleList.size(); i < size; i++) {
            GateWayRuleForm gateWayRuleForm = new GateWayRuleForm();
            UmsGatewayRule umsGatewayRule = ruleList.get(i);
            String gatewayId = umsGatewayRule.getGatewayId();
            UmsGateWayInfo umsGateWayInfo = gatewayService.findGateway(gatewayId);
            //如果没有找到对应网关的话就continue,页面也不会显示该规则。
            if (umsGateWayInfo == null) {
                continue;
            }
            String gatewayName = umsGateWayInfo.getGatewayName();
            String type = GateEnum.getDescription(umsGateWayInfo.getType());
            ObjectBuilder.copyObject(umsGatewayRule, gateWayRuleForm);
            gateWayRuleForm.setGatewayName(gatewayName);
            gateWayRuleForm.setType(type);
            ruleFormList.add(gateWayRuleForm);
        }
        return ruleFormList;
    }
}
