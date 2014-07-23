/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.GateOutProvEnum;
import net.zoneland.ums.common.util.enums.GateStateEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.gateway.Result;
import net.zoneland.ums.gateway.http.facade.GatewayFacade;
import net.zoneland.ums.web.home.admin.form.GateWayForm;
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
 * 网关配置类：增加，删除，查询
 * @author wangyong
 * @version $Id: GateWayController.java, v 0.1 2012-8-28 下午4:04:20 Administrator Exp $
 */
@Controller
@RequestMapping("/gateway")
public class GateWayController extends BaseController {

    private final static Logger logger = Logger.getLogger(GateWayController.class);

    @Autowired
    private GatewayService      gatewayService;

    @Autowired
    private GatewayFacade       gatewayFacade;

    /**
     *分页显示网关相关信息<br/>
     *注意点：网关状态不是指数据库里存的值，而是通过特定的测试方法去测试网关是否启用的
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String doList(HttpServletRequest request, ModelMap modelMap) {
        if (logger.isInfoEnabled()) {
            logger.info("显示网关信息");
        }
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        PageResult<UmsGateWayInfo> pageResult = new PageResult<UmsGateWayInfo>();
        try {
            pageResult = gatewayService.findAllGateway(page);
        } catch (Exception e) {
            logger.error("查询网关时出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            modelMap.addAttribute("results", pageResult);
            return "/admin/gateway/gateway.vm";
        }
        if (pageResult.getResults() != null) {
            List<UmsGateWayInfo> list = pageResult.getResults();
            List<String> gatewayIdList = new ArrayList<String>();
            for (int i = 0, size = list.size(); i < size; i++) {
                gatewayIdList.add(list.get(i).getId());
            }
            //获得网关状态
            try {
                List<Boolean> listResult = gatewayFacade.isOpen(gatewayIdList);
                for (int i = 0, size = list.size(); i < size; i++) {
                    UmsGateWayInfo umsGateWayInfo = list.get(i);
                    if (GateStateEnum.ENABLED.getValue().equals(umsGateWayInfo.getStatus())) {
                        if (!listResult.get(i)
                            || GateStateEnum.ERROR.getValue().equals(
                                umsGateWayInfo.getGatewayState())) {
                            list.get(i).setStatus(GateStateEnum.ERROR.getValue());
                        }
                    }
                }
            } catch (Exception e) {
                for (int i = 0, size = list.size(); i < size; i++) {
                    //存在系统异常！
                    list.get(i).setStatus("4");
                }
                logger.error("查询网关状态出现系统异常！", e);
            }
        }
        modelMap.addAttribute("results", pageResult);
        return "/admin/gateway/gateway.vm";
    }

    /**
     *网关参数编辑
     *
     * @param gateWayForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    public String addGet(GateWayForm gateWayForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        modelMap.addAttribute("type", GateEnum.values());
        modelMap.addAttribute("outProv", GateOutProvEnum.values());
        return "/admin/gateway/addgateway.vm";

    }

    /**
     *注册网关拦截post请求：<br/>
     *1.验证输入数据的正确性。<br/>
     *2.对象转化。（注册时默认网关都是可用的）<br/>
     *3.添加网关。
     *4.返回添加页面并提示添加结果信息
     * @param gateWayForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public String addPost(@Valid GateWayForm gateWayForm, BindingResult result,
                          HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("type", GateEnum.values());
        modelMap.addAttribute("outProv", GateOutProvEnum.values());
        if (result.hasErrors()) {
            modelMap.addAttribute("message", "数据格式不符合！");
            return "/admin/gateway/addgateway.vm";
        }

        UmsGateWayInfo umsGateWayInfo = gateWayForm.cloneUmsGateWayInfo();
        parstInteger(gateWayForm, umsGateWayInfo);

        try {
            gatewayService.insert(umsGateWayInfo);
            modelMap.addAttribute("umsGateWayInfo", umsGateWayInfo);
            return "/admin/gateway/addgatewaySuccess.vm";
        } catch (Exception e) {
            logger.error("添加网关时出现系统异常", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/admin/gateway/addgateway.vm";
        }
    }

    /**
     *更新网关拦截get请求<br/>
     *注意点：因为form对象跟数据库对象的属性有不相同的，所以要进行转化。
     * @param gateWayForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.GET)
    public String updateGateway(GateWayForm gateWayForm, BindingResult result,
                                HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("gatewayId");
        if (StringUtils.isEmpty(id)) {
            modelMap.addAttribute("message", "请选择网关！");
            return "/admin/gateway/gateway.vm";
        }
        UmsGateWayInfo umsGateWayInfo = new UmsGateWayInfo();
        try {
            umsGateWayInfo = gatewayService.findGateway(id);
        } catch (Exception e) {
            logger.error("系统异常", e);
            modelMap.addAttribute("message", "系统异常！");
        }
        if (umsGateWayInfo == null) {
            modelMap.addAttribute("message", "网关不存在！");
            doList(request, modelMap);
            return "/admin/gateway/gateway.vm";
        }
        ObjectBuilder.copyObject(umsGateWayInfo, gateWayForm);
        parstString(umsGateWayInfo, gateWayForm);
        modelMap.addAttribute("gateWayForm", gateWayForm);
        modelMap.addAttribute("type", GateEnum.values());
        modelMap.addAttribute("outProv", GateOutProvEnum.values());
        return "/admin/gateway/update.vm";

    }

    /**
     *更新网关拦截post请求:<br/>
     *1.验证输入数据的正确性。<br/>
     *2.判断密码是否被修改。（这里应该可以不做判断，因为密码是明码显示的，在应用更新的时候一定要判断。）<br/>
     * @param gateWayForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public String update(@Valid GateWayForm gateWayForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("type", GateEnum.values());
        modelMap.addAttribute("outProv", GateOutProvEnum.values());

        //验证数据的正确性
        if (result.hasErrors()) {
            modelMap.addAttribute("message", "数据格式不符合！");
            return "/admin/gateway/update.vm";
        }
        //更新网关
        UmsGateWayInfo umsGateWayInfo = new UmsGateWayInfo();
        umsGateWayInfo = gateWayForm.cloneUmsGateWayInfo();
        try {
            //两个对象属性有所不同要进行转化。
            parstInteger(gateWayForm, umsGateWayInfo);
            gatewayService.update(umsGateWayInfo);
            modelMap.addAttribute("umsGateWayInfo", umsGateWayInfo);
            return "/admin/gateway/updateSuccess.vm";
        } catch (Exception e) {
            logger.error("修改网关出现系统异常", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/admin/gateway/update.vm";
        }
    }

    /**
     *删除网关<br/>
     *1.判断网关是否存在。<br/>
     *2.如果存在就删除该网关，如果不存在就提示用户。<br/>
     *3.返回当前页面。
     * @param gateWayForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.GET)
    public String delGateway(GateWayForm gateWayForm, BindingResult result,
                             HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        try {
            UmsGateWayInfo umsGateWayInfo = gatewayService.findGateway(id);
            if (umsGateWayInfo == null) {
                modelMap.addAttribute("message", "网关不存在");
                PageResult<UmsGateWayInfo> pageResult = gatewayService.findAllGateway(page);
                modelMap.addAttribute("results", pageResult);
                return "/admin/gateway/gateway.vm";
            }
            gatewayService.deleteGateway(id);
        } catch (Exception e) {
            logger.error("删除网关出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常");
            PageResult<UmsGateWayInfo> pageResult = new PageResult<UmsGateWayInfo>();
            modelMap.addAttribute("results", pageResult);
            return "/admin/gateway/gateway.vm";
        }
        modelMap.addAttribute("message", "删除成功！");
        PageResult<UmsGateWayInfo> pageResult = gatewayService.findAllGateway(page);
        modelMap.addAttribute("results", pageResult);
        return "/admin/gateway/gateway.vm";

    }

    /**
     *修改网关状态<br/>
     *1.先判断网关状态。<br/>
     *2.然后改变当前状态。<br/>
     * @param appInfoForm
     * @param result
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping(value = "/status.ajax", method = RequestMethod.POST)
    public void statusPost(GateWayForm gateWayForm, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response,
                           ModelMap modelMap) {
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if ("open".equals(action)) {
            logger.info("开启网关");
            try {
                boolean test = gatewayFacade.isOpen(id);
                UmsGateWayInfo umsGateWayInfo = gatewayService.findGateway(id);
                //判断是不是可用的，判断Map里是否存在，判断网关启用之后的结果
                if (test && GateStateEnum.ENABLED.getValue().equals(umsGateWayInfo.getStatus())
                    && GateStateEnum.ENABLED.getValue().equals(umsGateWayInfo.getGatewayState())) {
                    logger.info("网关已开启");
                    ajaxReturn(GateStateEnum.ENABLED.getValue(), response);
                } else {
                    gatewayService.updateStatusById(GateStateEnum.ENABLED.getValue(), id);
                    Result res = new Result();
                    if (gatewayFacade.closeGateway(id).isSuccess()) {
                        res = gatewayFacade.openGateway(id);
                    }
                    if (res.isSuccess()) {
                        logger.info("启用成功");
                        ajaxReturn(GateStateEnum.ENABLED.getValue(), response);
                    } else {
                        logger.info("启用失败");
                        ajaxReturn(GateStateEnum.ERROR.getValue(), response);
                    }
                }
            } catch (Exception e) {
                logger.error("修改状态出现系统异常！", e);
                modelMap.addAttribute("message", "系统异常");
                ajaxReturn(GateStateEnum.ERROR.getValue(), response);
            }
        }
        if ("close".equals(action)) {
            logger.info("关闭网关");
            try {
                Result res = new Result();
                res = gatewayFacade.closeGateway(id);
                if (res.isSuccess()) {
                    gatewayService.updateStatusById(GateStateEnum.DISABLED.getValue(), id);
                    ajaxReturn(GateStateEnum.DISABLED.getValue(), response);
                } else {
                    ajaxReturn(GateStateEnum.ERROR.getValue(), response);
                }
            } catch (Exception e) {
                logger.error("修改状态出现系统异常！", e);
                modelMap.addAttribute("message", "系统异常");
                ajaxReturn(GateStateEnum.ERROR.getValue(), response);
            }
        }
    }

    /**
     *把String类型转为Integer
     *
     * @param gatewayForm
     * @param umsGateWayInfo
     * @return
     */
    private UmsGateWayInfo parstInteger(GateWayForm gatewayForm, UmsGateWayInfo umsGateWayInfo) {
        if (StringHelper.isNotEmpty(gatewayForm.getPort())) {
            umsGateWayInfo.setPort(Integer.valueOf(gatewayForm.getPort()));
        }
        if (StringHelper.isNotEmpty(StringUtils.trim(gatewayForm.getLocalPort()))) {
            umsGateWayInfo
                .setLocalPort(Integer.valueOf(StringUtils.trim(gatewayForm.getLocalPort())));
        }
        if (StringHelper.isNotEmpty(gatewayForm.getReadTimeout())) {
            umsGateWayInfo.setReadTimeout(Integer.valueOf(gatewayForm.getReadTimeout()));
        }
        if (StringHelper.isNotEmpty(gatewayForm.getReconnectInterval())) {
            umsGateWayInfo
                .setReconnectInterval(Integer.valueOf(gatewayForm.getReconnectInterval()));
        }
        if (StringHelper.isNotEmpty(gatewayForm.getTransactionTimeout())) {
            umsGateWayInfo.setTransactionTimeout(Integer.valueOf(gatewayForm
                .getTransactionTimeout()));
        }
        if (StringHelper.isNotEmpty(gatewayForm.getHeartbeatInterval())) {
            umsGateWayInfo
                .setHeartbeatInterval(Integer.valueOf(gatewayForm.getHeartbeatInterval()));
        }
        if (StringHelper.isNotEmpty(gatewayForm.getHeartbeatNoresponseout())) {
            umsGateWayInfo.setHeartbeatNoresponseout(Integer.valueOf(gatewayForm
                .getHeartbeatNoresponseout()));
        }
        if (StringHelper.isNotEmpty(gatewayForm.getDebug())) {
            umsGateWayInfo.setDebug(Integer.valueOf(gatewayForm.getDebug()));
        }
        return umsGateWayInfo;
    }

    /**
     *把对象的属性名称相同的，Integer类型转化String类型。
     *
     * @param umsGateWayInfo
     * @param gatewayForm
     * @return
     */
    private GateWayForm parstString(UmsGateWayInfo umsGateWayInfo, GateWayForm gatewayForm) {
        if (umsGateWayInfo.getPort() != null) {
            gatewayForm.setPort(String.valueOf(umsGateWayInfo.getPort()));
        }
        if (umsGateWayInfo.getLocalPort() != null) {
            gatewayForm.setLocalPort(String.valueOf(umsGateWayInfo.getLocalPort()));
        }
        if (umsGateWayInfo.getReadTimeout() != null) {
            gatewayForm.setReadTimeout(String.valueOf(umsGateWayInfo.getReadTimeout()));
        }
        if (umsGateWayInfo.getReconnectInterval() != null) {
            gatewayForm.setReconnectInterval(String.valueOf(umsGateWayInfo.getReconnectInterval()));
        }
        if (umsGateWayInfo.getTransactionTimeout() != null) {
            gatewayForm
                .setTransactionTimeout(String.valueOf(umsGateWayInfo.getTransactionTimeout()));
        }
        if (umsGateWayInfo.getHeartbeatInterval() != null) {
            gatewayForm.setHeartbeatInterval(String.valueOf(umsGateWayInfo.getHeartbeatInterval()));
        }
        if (umsGateWayInfo.getHeartbeatNoresponseout() != null) {
            gatewayForm.setHeartbeatNoresponseout(String.valueOf(umsGateWayInfo
                .getHeartbeatNoresponseout()));
        }
        if (umsGateWayInfo.getDebug() != null) {
            gatewayForm.setDebug(String.valueOf(umsGateWayInfo.getDebug()));
        }
        return gatewayForm;
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
}
