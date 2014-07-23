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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.admin.bo.AppInfoBO;
import net.zoneland.ums.biz.config.appadmin.AppConfigBiz;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.app.AppStateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.appadmin.form.AppInfoForm;
import net.zoneland.ums.web.home.appadmin.form.AppMsgOutForm;
import net.zoneland.ums.web.home.appadmin.form.AppPwdForm;
import net.zoneland.ums.web.home.base.BaseController;
import net.zoneland.ums.web.home.stat.form.AppMsgInfoForm;

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
 * @author XuFan
 * @version $Id: AppInfoController.java, v 0.1 Aug 15, 2012 4:43:44 PM XuFan Exp $
 */
@Controller
@RequestMapping("appAdmin")
public class AppInfoController extends BaseController {

    private static final Logger logger = Logger.getLogger(AppInfoController.class);

    @Autowired
    private AppConfigBiz        appConfigBiz;

    @Autowired
    private MsgSearchService    msgSearchService;

    /**
     * 查询应用请求
     *
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/appList.htm")
    public String dopost(AppInfoForm form, HttpServletRequest request, Integer pageId,
                         ModelMap modelMap) {
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        umsAppInfo.setAppName("".equals(form.getAppName()) ? null : form.getAppName());//根据应用名查询
        //umsAppInfo.setAppId("".equals(form.getAppId()) ? null : form.getAppId());//应用id
        umsAppInfo.setAppCode("".equals(form.getAppCode()) ? null : form.getAppCode());//根据应用code查询
        umsAppInfo.setStatus("".equals(form.getStatus()) ? null : form.getStatus());//根据应用状态查询
        PageResult<AppInfoBO> result = null;
        List<String> appids = getRoleAppIds();
        try {
            if (pageId == null || pageId < 1) {
                pageId = 1;
            }
            result = appConfigBiz.searchAppInfo(umsAppInfo, appids, pageId);
        } catch (Exception e) {
            logger.error("查询应用出错", e);
            addErrorMsg(modelMap, "查询应用出错");
        }
        modelMap.addAttribute("statelist", AppStateEnum.values());
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("app", form);
        return "appAdmin/appList.vm";
    }

    /**
     * get拦截修改应用url请求
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/appPWEdit.htm", method = RequestMethod.GET)
    public String getAppPWEdit(String id, Integer pageId, HttpServletRequest request,
                               ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("pageId", pageId);
        return "appAdmin/appPWEdit.vm";
    }

    /**
     * post拦截修改应用密码请求
     *
     * @param id
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/appPWEdit.htm", method = RequestMethod.POST)
    public String postAppPWEdit(@Valid AppPwdForm pwdform, BindingResult result,
                                HttpServletRequest request, Integer pageId, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute("id", pwdform.getId());
            modelMap.addAttribute("pageId", pageId);
            return "appAdmin/appPWEdit.vm";
        }
        if (pwdform.getNewPassword() != null
            && pwdform.getNewPassword().equals(pwdform.getRePassword())) {
            try {
                String id = pwdform.getId();
                String newPassword = pwdform.getNewPassword();
                int resultNum = appConfigBiz.modfiyAppPassword(id, newPassword);//查询核对原始密码并修改密码
                if (resultNum == 1) {
                    addMsg(modelMap, "修改应用密码成功");
                    if (logger.isInfoEnabled()) {
                        logger.info("修改应用密码成功");
                    }
                }
                modelMap.addAttribute("id", pwdform.getId());
                modelMap.addAttribute("pageId", pageId);
                return "appAdmin/appPWEdit.vm";
            } catch (Exception e) {
                logger.error("修改应用出错", e);
                addErrorMsg(modelMap, "修改应用出错");
                modelMap.addAttribute("id", pwdform.getId());
                modelMap.addAttribute("pageId", pageId);
                return "appAdmin/appPWEdit.vm";
            }
        } else {
            addErrorMsg(modelMap, "两次输入密码不一致");
            modelMap.addAttribute("id", pwdform.getId());
            modelMap.addAttribute("pageId", pageId);
            return "appAdmin/appPWEdit.vm";
        }

    }

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
     * 查询应用【用户短信】的初始化页面
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "appMsgInit.htm")
    public String appMsgSearchInit(AppMsgOutForm form, HttpServletRequest request, ModelMap modelMap) {
        Date date = new Date();
        String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
        form.setStartTime(startTime);
        String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
        form.setEndTime(endTime);
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        try {
            if (appids != null && appids.size() > 0) {
                list = appConfigBiz.getApp(appids);
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
            logger.error("查询应用【用户短信】初始化页面出现异常：", e);
        }
        modelMap.addAttribute("init", 0);//标记这是初始化页面要显示的提示
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("apps", list);//获取应用下拉框
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());//状态
        return "appAdmin/appMsg.vm";
    }

    /**
     * 拦截查询应用【用户短信】的get和post请求
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "appMsg.htm")
    public String appMsgSearchPost(AppMsgOutForm form, HttpServletRequest request, ModelMap modelMap) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO = getAppMsgInfoBO(form);
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (appids != null && appids.size() > 0) {
            list = appConfigBiz.getApp(appids);
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
        PageResult<UmsMsgOut> result = appConfigBiz.searchAppMsgInfo(appMsgInfoBO, appIdList,
            pageId);
        modelMap.addAttribute("appId", form.getAppId());//回填应用select用
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("apps", list);//获取应用下拉框
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());//状态
        return "appAdmin/appMsg.vm";
    }

    /**
     * 查询应用数据短信页面的详细短信信息
     * 
     * @param form
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/searchAppDataMsgDetail.htm")
    public String searchAppDataMsgDetail(AppMsgInfoForm form, ModelMap modelMap,
                                         HttpServletRequest request) {
        String id = request.getParameter("id");
        UmsMsgOutDO umsMsgOut = new UmsMsgOutDO();
        if (StringHelper.isNotEmpty(id)) {
            umsMsgOut = msgSearchService.getDataMsgInfoById(id);
        }
        modelMap.addAttribute("msgInfo", umsMsgOut);
        modelMap.addAttribute("msgId", id);
        modelMap.addAttribute("msg", form);
        return "appAdmin/appDataMsgDetail.vm";
    }

    /**
     * 查询应用用户短信页面的详细短信信息
     * 
     * @param form
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/searchAppMsgDetail.htm")
    public String searchAppMsgDetail(AppMsgInfoForm form, ModelMap modelMap,
                                     HttpServletRequest request) {
        String id = request.getParameter("id");
        UmsMsgOutDO umsMsgOut = new UmsMsgOutDO();
        if (StringHelper.isNotEmpty(id)) {
            umsMsgOut = msgSearchService.getSendMsgInfoById(id);
        }
        modelMap.addAttribute("msgInfo", umsMsgOut);
        modelMap.addAttribute("msgId", id);
        modelMap.addAttribute("msg", form);
        return "appAdmin/appMsgDetail.vm";
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

    /**
     * 二级下拉框 子应用ajax拦截
     *
     * @param appId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/getAppSub.ajax")
    public void getCateAjax(String appId, HttpServletResponse response) throws Exception {
        if (appId != null && !"".equals(appId)) {
            List<UmsAppSub> list = appConfigBiz.getAppSub(appId);
            List<Map<String, String>> listm = new ArrayList<Map<String, String>>();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    UmsAppSub info = list.get(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Value", info.getAppSubId());
                    map.put("Text", info.getAppSubName());
                    listm.add(map);
                }
            }
            ajaxReturn(listm, response);
        }
    }

    /**
     * 根据名称 查询应用
     * @param appName
     * @param response
     * @throws Exception
     */
    @RequestMapping("/getAppLikeName.ajax")
    public void getAppByName(String q, boolean isadmin, HttpServletResponse response)
                                                                                     throws Exception {
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (q != null && !"".equals(q)) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (!isadmin) {
                List<String> ids = getRoleAppIds();
                if (ids != null && ids.size() > 0) {
                    map.put("appIdList", ids);
                    list = appConfigBiz.getAppByname(map, q);
                    ajaxReturn(list, response);
                } else {
                    logger.info("没有权限数据！");
                    ajaxReturn(list, response);
                }
            } else {
                list = appConfigBiz.getAppByname(map, q);
                ajaxReturn(list, response);
            }
        } else {
            logger.info("传入应用名称为空！");
            ajaxReturn(list, response);
        }

    }

    /**
     * 查询应用【数据短信】的初始化页面
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "appDataMsgInit.htm")
    public String appMsgDataInit(AppMsgOutForm form, HttpServletRequest request, ModelMap modelMap) {
        Date date = new Date();
        String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
        form.setStartTime(startTime);
        String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
        form.setEndTime(endTime);
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        try {
            if (appids != null && appids.size() > 0) {
                list = appConfigBiz.getApp(appids);
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
            logger.error("查询应用【数据短信】初始化页面出现异常：", e);
        }
        modelMap.addAttribute("init", 0);//标记这是初始化页面要显示的提示
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("apps", list);//获取应用下拉框
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());//状态
        return "appAdmin/appDataMsg.vm";
    }

    /**
     * 拦截查询应用【数据短信】的get和post请求
     * 
     * @param form
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "appDataMsg.htm")
    public String appDataMsgSearchPost(AppMsgOutForm form, HttpServletRequest request,
                                       ModelMap modelMap) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO = getAppMsgInfoBO(form);
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        PageResult<UmsMsgOut> result = new PageResult<UmsMsgOut>();
        try {
            if (appids != null && appids.size() > 0) {
                list = appConfigBiz.getApp(appids);
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
            result = appConfigBiz.searchAppDataMsgInfo(appMsgInfoBO, appIdList, pageId);
        } catch (Exception e) {
            logger.error("查询应用【数据短信】出现异常：", e);
        }
        modelMap.addAttribute("appId", form.getAppId());//回填应用select用
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("apps", list);//获取应用下拉框
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());//状态
        return "appAdmin/appDataMsg.vm";
    }
}
