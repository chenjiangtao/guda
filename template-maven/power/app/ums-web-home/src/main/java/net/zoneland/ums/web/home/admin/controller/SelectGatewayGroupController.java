/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.admin.GatewayGroupBiz;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroup;
import net.zoneland.ums.common.dal.dataobject.ZTree;
import net.zoneland.ums.web.home.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yangjuanying
 * @version $Id: SelectGatewayGroupController.java, v 0.1 2013-1-7 下午1:01:04 yangjuanying Exp $
 */
@Controller
@RequestMapping("/admin")
public class SelectGatewayGroupController extends BaseController {

    @Autowired
    private GatewayGroupBiz gatewayGroupBiz;

    @RequestMapping("/getFirstGatewayGroup.ajax")
    public void getFirstOrg(HttpServletRequest request, HttpServletResponse response) {
        List<ZTree> zTreeFirstList = gatewayGroupBiz.queryGatewayGroupForSelector();
        ajaxReturn(zTreeFirstList, response);
    }

    @RequestMapping("/getChildGatewayGroup.ajax")
    public void getChildrenOrg(HttpServletRequest request, HttpServletResponse response) {
        String gatewayGroupId = request.getParameter("id");
        // 解析页面传来的parentId；
        List<ZTree> zTreeList = gatewayGroupBiz.queryGatewayInfosByGatewayGroupId(gatewayGroupId);
        //        Collections.sort(zTreeList, new Mycomparator());
        if (zTreeList != null && zTreeList.size() > 0) {
            ajaxReturn(zTreeList, response);
        }
    }

    /**
     * 新增网关分组时，用UUID产生网关分组ID
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/createGatewayGroupId.ajax")
    public void createGatewayGroupId(HttpServletRequest request, HttpServletResponse response) {
        UmsGatewayGroup umsGatewayGroup = gatewayGroupBiz.createNewGatewayGroup();
        ajaxReturn(umsGatewayGroup, response);
    }
}
