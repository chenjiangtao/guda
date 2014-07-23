/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.GatewayGroupBiz;
import net.zoneland.ums.biz.config.gateway.GatewayRuleService;
import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayRule;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.GateWayGroupRuleForm;
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
 * @author yangjuanying
 * @version $Id: GatewayGroupRuleController.java, v 0.1 2013-1-15 下午4:22:46 yangjuanying Exp $
 */
@Controller
@RequestMapping("/admin")
public class GatewayGroupRuleController {

    public static final Logger logger = Logger.getLogger(GatewayGroupRuleController.class);

    @Autowired
    private GatewayService     gatewayService;

    @Autowired
    private GatewayRuleService gatewayRuleService;

    @Autowired
    private GatewayGroupBiz    gatewayGroupBiz;

    /**
     * 显示网关分组规则<br/>
     * 要进行对象转化，在规则里添加网关分组名字和网关分组成员。
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/gateGroupRuleList.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        PageResult<UmsGatewayRule> pageResult = new PageResult<UmsGatewayRule>();
        try {
            pageResult = gatewayRuleService.findRule(page);
        } catch (Exception e) {
            logger.error("显示网关分组规则的时候出现系统异常", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/admin/gatewayGroup/gatewayGroupRuleList.vm";
        }
        List<GateWayGroupRuleForm> ruleFormList = change(pageResult.getResults());
        PageResult<GateWayGroupRuleForm> result = new PageResult<GateWayGroupRuleForm>();
        ObjectBuilder.copyObject(pageResult, result);
        result.setResults(ruleFormList);
        modelMap.addAttribute("results", result);
        return "/admin/gatewayGroup/gatewayGroupRuleList.vm";
    }

    /**
     * 跳转到添加页面
     *
     * @param gateWayRuleForm
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addGateGroupRule.htm", method = RequestMethod.GET)
    public String addGet(GateWayRuleForm gateWayRuleForm, HttpServletRequest request,
                         ModelMap modelMap) {
        List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
        modelMap.addAttribute("gateways", gatewayList);
        return "/admin/gatewayGroup/addGatewayGroupRule.vm";
    }

    /**
     * 增加网关分组规则<br/>
     * 1.先验证输入数据的正确性。<br/>
     * 2.添加网关分组规则。<br>
     * 3.返回添加页面并且提示成功与否
     * 
     * @param gateWayRuleForm
     * @param request
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addGateGroupRule.htm", method = RequestMethod.POST)
    public String addPost(@Valid GateWayRuleForm gateWayRuleForm, BindingResult result,
                          HttpServletRequest request, ModelMap modelMap) {
        //验证输入数据的正确性
        if (result.hasErrors()) {
            List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            modelMap.addAttribute("message", "数据格式有误！");
            return "/admin/gatewayGroup/addGatewayGroupRule.vm";
        }
        //插入网关分组规则   
        try {
            UmsGatewayRule umsGatewayRule = gateWayRuleForm.cloneUmsGateWayRule();
            boolean res = gatewayRuleService.add(umsGatewayRule);
            if (res) {
                modelMap.addAttribute("umsGatewayRule", umsGatewayRule);
                return "/admin/gatewayGroup/addGatewayGroupRuleSuccess.vm";
            }
            List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            modelMap.addAttribute("message", "添加失败!");
            return "/admin/gatewayGroup/addGatewayGroupRule.vm";
        } catch (Exception e) {
            logger.error("添加网关分组规则出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常，添加失败!");
            List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            return "/admin/gatewayGroup/addGatewayGroupRule.vm";
        }
    }

    /**
     * 跳转到更新页面
     *
     * @param gateWayRuleForm
     * @param request
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateGateGroupRule.htm", method = RequestMethod.GET)
    public String updateGet(GateWayRuleForm gateWayRuleForm, HttpServletRequest request,
                            BindingResult result, ModelMap modelMap) {
        String id = request.getParameter("ruleId");
        if (StringUtils.isEmpty(id)) {
            modelMap.addAttribute("message", "请选择规则！");
            return "/admin/gatewayGroup/updateGatewayGroupRule.vm";
        }
        UmsGatewayRule umsGatewayRule = null;
        List<UmsGatewayGroup> gatewayList = null;
        gateWayRuleForm.setId(id);
        try {
            umsGatewayRule = gatewayRuleService.findGatewayRuleById(id);
            gatewayList = gatewayGroupBiz.findAll();

        } catch (Exception e) {
            modelMap.addAttribute("message", "数据库出错！");
            return "/admin/gatewayGroup/updateGatewayGroupRule.vm";
        }
        modelMap.addAttribute("gateways", gatewayList);
        ObjectBuilder.copyObject(umsGatewayRule, gateWayRuleForm);
        return "/admin/gatewayGroup/updateGatewayGroupRule.vm";
    }

    /**
     *更新网关分组规则：<br/>
     *1.验证数据的准确性。<br/>
     *2.转化对象。<br/>
     *3.更新网关分组规则。
     * @param gateWayRuleForm
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateGateGroupRule.htm", method = RequestMethod.POST)
    public String updatePost(@Valid GateWayRuleForm gateWayRuleForm, BindingResult result,
                             HttpServletRequest request, ModelMap modelMap) {
        //验证输入数据的正确性
        if (result.hasErrors()) {
            List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            return "/admin/gatewayGroup/updateGatewayGroupRule.vm";
        }
        //更新网关分组规则
        try {
            UmsGatewayRule umsGatewayRule = gateWayRuleForm.cloneUmsGateWayRule();
            boolean res = gatewayRuleService.update(umsGatewayRule);
            if (res) {
                modelMap.addAttribute("umsGatewayRule", umsGatewayRule);
                return "/admin/gatewayGroup/updateGatewayGroupRuleSuccess.vm";
            }
            List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            modelMap.addAttribute("message", "修改失败!");
            return "/admin/gatewayGroup/updateGatewayGroupRule.vm";
        } catch (Exception e) {
            logger.error("更新网关分组规则出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常，修改失败!");
            List<UmsGatewayGroup> gatewayList = gatewayGroupBiz.findAll();
            modelMap.addAttribute("gateways", gatewayList);
            return "/admin/gatewayGroup/updateGatewayGroupRule.vm";
        }

    }

    /**
     *删除网关分组规则<br/>
     *1.获得当前页<br/>
     *2.检查规则是否存在。<br/>
     *3.如果存在就去删除规则，如果不存在就提示用户。<br/>
     *4.返回之前页面。
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/delGateGroupRule.htm", method = RequestMethod.GET)
    public String del(HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        try {
            boolean exist = gatewayRuleService.isExist(id);
            if (!exist) {
                modelMap.addAttribute("message", "规则不存在");
                PageResult<GateWayGroupRuleForm> result = getResult(page);
                modelMap.addAttribute("results", result);
                return "/admin/gatewayGroup/gatewayGroupRuleList.vm";
            }
            gatewayRuleService.delRule(id);
        } catch (Exception e) {
            logger.error("删除网关分组规则时出现系统异常", e);
            PageResult<GateWayRuleForm> result = new PageResult<GateWayRuleForm>();
            modelMap.addAttribute("message", "删除失败");
            modelMap.addAttribute("results", result);
            return "/admin/gatewayGroup/gatewayGroupRuleList.vm";
        }
        modelMap.addAttribute("message", "删除成功");
        PageResult<GateWayGroupRuleForm> result = getResult(page);
        modelMap.addAttribute("results", result);
        return "/admin/gatewayGroup/gatewayGroupRuleList.vm";
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

    private PageResult<GateWayGroupRuleForm> getResult(int page) {
        PageResult<UmsGatewayRule> pageResult = gatewayRuleService.findRule(page);
        List<GateWayGroupRuleForm> ruleFormList = change(pageResult.getResults());
        PageResult<GateWayGroupRuleForm> result = new PageResult<GateWayGroupRuleForm>();
        ObjectBuilder.copyObject(pageResult, result);
        result.setResults(ruleFormList);
        return result;
    }

    /**
     *对象转化：<br/>
     *1.在规则里添加网关名字与网关类型。
     *
     * @param list
     * @return
     */
    private List<GateWayGroupRuleForm> change(List<UmsGatewayRule> list) {

        List<GateWayGroupRuleForm> ruleFormList = new ArrayList<GateWayGroupRuleForm>();
        if (list == null) {
            return ruleFormList;
        }
        for (int i = 0, size = list.size(); i < size; i++) {
            GateWayGroupRuleForm gateWayGroupRuleForm = new GateWayGroupRuleForm();
            UmsGatewayRule umsGatewayRule = list.get(i);
            String gatewayId = umsGatewayRule.getGatewayId();
            UmsGatewayGroup umsGatewayGroup = gatewayGroupBiz.findGatewayGroup(gatewayId);
            //如果没有找到对应网关分组的话就continue,页面也不会显示该规则。
            if (umsGatewayGroup == null) {
                continue;
            }

            String gatewayName = umsGatewayGroup.getGatewayGroupName();
            ObjectBuilder.copyObject(umsGatewayRule, gateWayGroupRuleForm);
            gateWayGroupRuleForm.setGatewayGroupName(gatewayName);
            List<UmsGateWayInfo> umsGateWayInfos = gatewayGroupBiz
                .queryGatewayInfoById(umsGatewayGroup.getId());
            String memberNames = "";
            if (umsGateWayInfos != null && umsGateWayInfos.size() > 0) {
                for (UmsGateWayInfo umsGateWayInfo : umsGateWayInfos) {
                    memberNames += umsGateWayInfo.getGatewayName() + "("
                                   + GateEnum.getDescription(umsGateWayInfo.getType()) + "),";
                }
            }
            if (StringUtils.isNotEmpty(memberNames)) {
                memberNames = memberNames.substring(0, memberNames.length() - 1);
            }
            gateWayGroupRuleForm.setMemberNames(memberNames);
            ruleFormList.add(gateWayGroupRuleForm);
        }
        return ruleFormList;
    }
}
