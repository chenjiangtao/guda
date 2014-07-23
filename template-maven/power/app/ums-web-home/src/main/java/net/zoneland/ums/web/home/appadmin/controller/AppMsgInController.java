/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.appadmin.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.appadmin.AppConfigBiz;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.appadmin.form.AppMsgOutForm;
import net.zoneland.ums.web.home.stat.form.AppMsgInfoForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yangjuanying
 * @version $Id: AppMsgInController.java, v 0.1 2012-12-11 上午1:20:58 yangjuanying Exp $
 */
@Controller
@RequestMapping("appAdmin")
public class AppMsgInController {

    private static final Logger logger = Logger.getLogger(AppMsgInController.class);

    @Autowired
    private AppConfigBiz        appConfigBiz;

    @Autowired
    private MsgSearchService    msgSearchService;

    /**
     * 查询应用【上行短信】的初始化界面
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "appMsgInInit.htm")
    public String appMsgInInit(AppMsgOutForm form, HttpServletRequest request, ModelMap modelMap) {
        Date date = new Date();
        String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
        form.setStartTime(startTime);
        String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
        form.setEndTime(endTime);
        List<String> appIds = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        try {
            if (appIds != null && appIds.size() > 0) {
                list = appConfigBiz.getApp(appIds);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        UmsAppInfo info = list.get(i);
                        if (info != null && !"".equals(info.getAppId())) {
                            appIdList.add(info.getAppId());// 获取当前应用角色所能管理的应用APPID
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("查询统计应用【上行短信】初始化页面出现异常：", e);
        }
        modelMap.addAttribute("init", 0);//标记这是初始化页面要显示的提示
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("apps", list);//获取应用下拉框
        modelMap.addAttribute("statelist", MsgInStatusEnum.values());//状态
        return "appAdmin/appMsgIn.vm";
    }

    /**
     * 拦截查询应用上行短信的get和post请求
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "appMsgIn.htm")
    public String appMsgInSearchPost(AppMsgOutForm form, HttpServletRequest request,
                                     ModelMap modelMap) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO = getAppMsgInfoBO(form);
        List<String> appIds = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        PageResult<UmsMsgIn> result = new PageResult<UmsMsgIn>();
        try {
            if (appIds != null && appIds.size() > 0) {
                list = appConfigBiz.getApp(appIds);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        UmsAppInfo info = list.get(i);
                        if (info != null && !"".equals(info.getAppId())) {
                            appIdList.add(info.getAppId());// 获取当前应用角色所能管理的应用APPID
                        }
                    }
                }
            }
            int pageId = StringHelper.parseInt(form.getPageId());
            result = appConfigBiz.searchAppMsgIn(appMsgInfoBO, appIdList, pageId);
        } catch (Exception e) {
            logger.error("查询统计应用【上行短信】出现异常：", e);
        }
        modelMap.addAttribute("appId", form.getAppId());//回填应用select用
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("apps", list);//获取应用下拉框
        modelMap.addAttribute("statelist", MsgInStatusEnum.values());//状态
        return "appAdmin/appMsgIn.vm";
    }

    /**
     * 查询应用上行短信页面的详细短信信息
     * 
     * @param form
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/appMsgInDetail.htm")
    public String appMsgInDetail(AppMsgInfoForm form, ModelMap modelMap, HttpServletRequest request) {
        String id = request.getParameter("id");
        UmsMsgIn umsMsgIn = new UmsMsgIn();
        if (StringHelper.isNotEmpty(id)) {
            umsMsgIn = msgSearchService.getMsgInById(id);
        }
        modelMap.addAttribute("msgInfo", umsMsgIn);
        modelMap.addAttribute("msgId", id);
        modelMap.addAttribute("msg", form);
        return "appAdmin/appMsgInDetail.vm";
    }

    /**
     * 获取应用管理员所管理的应用
     * 
     * @return
     */
    private List<String> getRoleAppIds() {
        List<String> appids = null;
        try {
            RoleInfo[] roles = (RoleInfo[]) SecurityContextHolder.getContext().getAuthorities();
            if (roles != null && roles.length > 0) {
                for (int i = 0, len = roles.length; i < len; ++i) {
                    if (RoleNameEnum.APP.getValue().equals(roles[i].getRoleName())) {
                        appids = roles[i].getAppId();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取用户管理应用的权限出错！", e);
        }
        return appids;
    }

    /**
     * 将前端页面获取到的对象转换成为业务层提供操作的对象
     * 
     * @param form
     * @return
     */
    private AppMsgInfoBO getAppMsgInfoBO(AppMsgOutForm form) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        if (form != null) {
            if (form.getStartTime() != null && !"".equals(form.getStartTime())) {
                try {
                    String startTime = form.getStartTime() + " 00:00:00";
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
                form.setStartTime(startTime);
                startTime = startTime + " 00:00:00";
                try {
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            if (form.getEndTime() != null && !"".equals(form.getEndTime())) {
                try {
                    String endTime = form.getEndTime() + " 23:59:59";
                    Calendar cend = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setEndTime(new Timestamp(cend.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
                form.setEndTime(endTime);
                endTime = endTime + " 23:59:59";
                try {
                    Calendar cEnd = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setEndTime(new Timestamp(cEnd.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            appMsgInfoBO.setStatus("".equals(form.getStatus()) ? null : form.getStatus());// 获取条件：状态
            appMsgInfoBO.setAppName(StringUtils.isEmpty(form.getAppName()) ? null : form
                .getAppName());
            if ("".equals(form.getAppId())) {// 应用ID
                appMsgInfoBO.setAppId(null);
            } else {
                appMsgInfoBO.setAppId(form.getAppId());
            }
            appMsgInfoBO.setMsgdestName(StringUtils.isEmpty(form.getMsgdestName()) ? null : form
                .getMsgdestName());// 发送人员姓名
            appMsgInfoBO.setSendId(StringUtils.isEmpty(form.getMsgDest()) ? null : form
                .getMsgDest());// 发送方手机号
            appMsgInfoBO.setMsgsrcName(StringUtils.isEmpty(form.getMsgsrcName()) ? null : form
                .getMsgsrcName());// 接收人员姓名
            appMsgInfoBO.setRecvId(StringUtils.isEmpty(form.getMsgSrc()) ? null : form.getMsgSrc());// 获取接收方手机号
            appMsgInfoBO.setBizType(StringUtils.isEmpty(form.getBizType()) ? null : form
                .getBizType());// 业务类型
            appMsgInfoBO.setBizName(StringUtils.isEmpty(form.getBizName()) ? null : form
                .getBizName());// 业务系统
            appMsgInfoBO.setCreateUser(StringUtils.isEmpty(form.getCreateUser()) ? null : form
                .getCreateUser());// 生成人员
            appMsgInfoBO.setOrgNo(StringUtils.isEmpty(form.getOrgNo()) ? null : form.getOrgNo());//  组织号
            appMsgInfoBO.setFlowNo(StringUtils.isEmpty(form.getFlowNo()) ? null : form.getFlowNo());// 流程号
            if ("".equals(form.getGatewaytype())) {
                appMsgInfoBO.setGatewaytype(null);
            } else {
                appMsgInfoBO.setGatewaytype(form.getGatewaytype());
            }
        }
        return appMsgInfoBO;
    }
}
