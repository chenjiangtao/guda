/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.admin.GatewayGroupBiz;
import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroupRel;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 有关网关分组维护的控制类
 * 创建网关分组，删除网关分组，增加网关分组成员，删除网关分组成员等
 * 
 * @author yangjuanying
 * @version $Id: GatewayGroupController.java, v 0.1 2013-1-6 上午10:10:26 yangjuanying Exp $
 */
@Controller
@RequestMapping("/admin")
public class GatewayGroupController extends BaseController {

    private final Logger     logger                    = Logger
                                                           .getLogger(GatewayGroupController.class);
    @Autowired
    private GatewayGroupBiz  gatewayGroupBiz;

    @Autowired
    private GatewayService   gatewayService;

    private final static int GATEWAY_GROUP_NAME_LENGTH = 30;

    /**
     * 显示网关分组
     * 
     * @return
     */
    @RequestMapping(value = "/gateGroupList.htm")
    public String doList(HttpServletRequest request, ModelMap modelMap) {
        List<UmsGateWayInfo> umsGatewayInfos = new ArrayList<UmsGateWayInfo>();
        try {
            umsGatewayInfos = gatewayService.findAll();
            if (umsGatewayInfos != null) {
                modelMap.addAttribute("gatewayInfoSize", umsGatewayInfos.size());
            }
            modelMap.addAttribute("results", umsGatewayInfos);
        } catch (Exception e) {
            logger.error("显示网关分组时，查询网关出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            modelMap.addAttribute("results", umsGatewayInfos);
        }
        return "/admin/gatewayGroup/gatewayGroupList.vm";
    }

    /**
     * 在给网关分组添加网关之前，根据传入的网关分组主键ID，在关联表中查出其对应的关联表信息
     * 
     * @param request
     * @param modelMap
     * @param response
     */
    @RequestMapping(value = "/beforeAddgateGroup.ajax")
    public void doBeforeAdd(HttpServletRequest request, ModelMap modelMap,
                            HttpServletResponse response) {
        String gatewayGroupId = request.getParameter("gatewayGroupId");
        try {
            List<UmsGatewayGroupRel> umsGatewayGroupRels = gatewayGroupBiz
                .queryByGatewayGroupId(gatewayGroupId);
            ajaxReturn(umsGatewayGroupRels, response);
        } catch (Exception e) {
            logger.error("显示网关分组时，查询网关出现系统异常！", e);
            ajaxReturn(false, response);
        }
    }

    /**
     * 增加网关分组</br>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/addGatewayGroup.ajax")
    public void doAdd(HttpServletRequest request, HttpServletResponse response) {
        String gatewayGroupId = request.getParameter("gatewayGroupId");// 网关分组主键ID
        String gatewayIdStr = request.getParameter("gatewayIdStr");// 网关分组成员ID，用逗号分隔成字符串
        AjaxResult ajaxResult = new AjaxResult();
        try {
            boolean save = gatewayGroupBiz.saveGatewayGroup(gatewayGroupId, gatewayIdStr);
            if (save) {
                ajaxResult.setResult(true);
                ajaxResult.setInfo("添加成功");
                ajaxReturn(ajaxResult, response);
            } else {
                ajaxResult.setResult(false);
                ajaxResult.setInfo("添加失败，请重新添加或联系管理员");
                ajaxReturn(ajaxResult, response);
            }
        } catch (Exception e) {
            logger.error("网关分组添加网关时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("网关分组添加网关时出现系统异常");
            ajaxReturn(ajaxResult, response);
        }
    }

    /**
     * 根据网关主键查询出网关信息</br>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/queryGatewayInfoById.ajax")
    public void doQuery(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");// 网关分组或网关的主键ID
        AjaxResult ajaxResult = new AjaxResult();
        try {
            UmsGateWayInfo umsGateWayInfo = gatewayService.findGateway(id);
            if (umsGateWayInfo != null) {
                ajaxResult.setResult(true);
                ajaxResult.setInfo("umsGateWayInfo");
                ajaxResult.setResultObject(umsGateWayInfo);
                ajaxReturn(ajaxResult, response);
            }
            List<UmsGateWayInfo> umsGateWayInfos = gatewayGroupBiz.queryGatewayInfoById(id);
            if (umsGateWayInfos == null || umsGateWayInfos.size() <= 0) {
                ajaxResult.setResultObject(false);
                ajaxResult.setResult(false);
                ajaxResult.setInfo("noGroupMembers");
                ajaxReturn(ajaxResult, response);
            }
            ajaxResult.setResult(true);
            ajaxResult.setInfo("umsGateWayInfos");
            ajaxResult.setResultObject(umsGateWayInfos);
            ajaxReturn(ajaxResult, response);
        } catch (Exception e) {
            logger.error("网关分组右侧显示列表时，查询网关时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("error");
            ajaxReturn(ajaxResult, response);
        }
    }

    /**
     * 通过网关分组主键ID删除整个网关分组(包括删除其关联表的数据)
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delGatewayGroup.ajax")
    public void doDel(HttpServletRequest request, HttpServletResponse response) {
        String gatewayGroupId = request.getParameter("gatewayGroupId");
        try {
            gatewayGroupBiz.delGatewayGroupById(gatewayGroupId);
        } catch (Exception e) {
            logger.error("删除网关分组时出现系统异常！", e);
            ajaxReturn("error", response);
        }
        ajaxReturn(true, response);
    }

    /**
     * 通过网关分组主键ID和网关的主键ID删除所对应的网关分组关联表的数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delGatewayGroupRel.ajax")
    public void doDelRel(HttpServletRequest request, HttpServletResponse response) {
        String gatewayId = request.getParameter("gatewayId");
        String gatewayGroupId = request.getParameter("gatewayGroupId");
        try {
            boolean res = gatewayGroupBiz.delGatewayGroupRelByIds(gatewayId, gatewayGroupId);
            if (!res) {
                ajaxReturn(false, response);
            }
            ajaxReturn(true, response);
        } catch (Exception e) {
            logger.error("在网关分组中删除网关时，出现系统异常！", e);
            ajaxReturn("error", response);
        }
    }

    /**
     * 更新网关分组名称
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateGatewayGroup.ajax")
    public void doUpdate(HttpServletRequest request, HttpServletResponse response) {
        String gatewayGroupId = request.getParameter("gatewayGroupId");
        String gatewayGroupName = request.getParameter("gatewayGroupName");
        // String parentId = request.getParameter("parentId");
        if (!checkGatewayGroupName(gatewayGroupName)) {
            ajaxReturn(false, response);
        }
        boolean result = false;
        try {
            //            gatewayGroupBiz.queryGatewayGroupById(gatewayGroupId);
            //            if (umsArea != null && umsArea.getAreaName() != null
            //                && !umsArea.getAreaName().equalsIgnoreCase(areaName)) {
            //                boolean areaNameExist = umsAreaBiz.isExistOfAreaName(areaName, parentId);
            //                if (areaNameExist) {
            //                    ajaxReturn("areaNameExist", response);
            //                    return;
            //                }
            //            }
            result = gatewayGroupBiz.updateByGatewayGroupName(gatewayGroupId, gatewayGroupName);
        } catch (Exception e) {
            logger.error("更新单位时出现系统异常！", e);
            ajaxReturn("error", response);
        }
        if (result) {
            ajaxReturn(true, response);
        } else {
            ajaxReturn(false, response);
        }
    }

    /**
     * 检查网关分组名长度
     * 
     * @param gatewayGroupName
     * @return
     */
    private boolean checkGatewayGroupName(String gatewayGroupName) {
        if (StringUtils.isEmpty(gatewayGroupName)) {
            return false;
        }
        if (gatewayGroupName.length() > GATEWAY_GROUP_NAME_LENGTH) {
            return false;
        }
        return true;
    }
}
