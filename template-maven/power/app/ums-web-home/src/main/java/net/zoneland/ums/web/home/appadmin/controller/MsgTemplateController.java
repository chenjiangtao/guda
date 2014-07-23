/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.appadmin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.admin.AppSubBiz;
import net.zoneland.ums.biz.config.appadmin.AppConfigBiz;
import net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.appadmin.form.MsgTemplateForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author wangyong
 * @version $Id: MsgTemplateController.java, v 0.1 2012-10-12 上午10:29:13
 *          wangyong Exp $
 */
@Controller
@RequestMapping("/appAdmin/temp")
public class MsgTemplateController {

    private static final Logger logger = Logger.getLogger(MsgTemplateController.class);

    @Autowired
    private MsgTemplateBiz      msgTemplateBiz;
    @Autowired
    private AppConfigBiz        appConfigBiz;
    @Autowired
    private AppSubBiz           appSubBiz;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    /**
     * 分页显示短信模版
     * 
     * @param msgTemplateForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm")
    public String listGet(MsgTemplateForm msgTemplateForm, BindingResult result,
                          HttpServletRequest request, ModelMap modelMap) {
        UmsMsgTemplate umsMsgTemplate = new UmsMsgTemplate();
        umsMsgTemplate.setTemplateId("".equalsIgnoreCase(msgTemplateForm.getTemplateId()) ? null
            : msgTemplateForm.getTemplateId());
        umsMsgTemplate.setAppId("".equalsIgnoreCase(msgTemplateForm.getAppId()) ? null
            : msgTemplateForm.getAppId());
        String pageId = request.getParameter("pageId");
        int page = parseInt(pageId);
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        if (appids == null || appids.size() == 0) {
            PageResult<UmsMsgTemplate> pageResult = new PageResult<UmsMsgTemplate>();
            modelMap.addAttribute("results", pageResult);
            return "/appAdmin/tempList.vm";
        }
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (appids != null && appids.size() > 0) {
            try {
                list = appConfigBiz.getApp(appids);
            } catch (Exception e) {
                logger.error("显示短信模版时查询应用出现系统异常！", e);
                modelMap.addAttribute("message", "系统异常！");
                return "/appAdmin/tempList.vm";
            }

        }
        if ("".equalsIgnoreCase(msgTemplateForm.getAppId()) || msgTemplateForm.getAppId() == null) {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    UmsAppInfo info = list.get(i);
                    if (info != null && !"".equals(info.getAppId())) {
                        appIdList.add(info.getAppId());// 获取当前应用角色所能管理的应用APPID
                    }
                }
            }
        } else {
            appIdList.add(msgTemplateForm.getAppId());
        }
        try {
            PageResult<UmsMsgTemplate> pageResult = msgTemplateBiz.listUmsTemplate(
                umsMsgTemplate.getTemplateId(), page, appIdList);
            modelMap.addAttribute("results", pageResult);
        } catch (Exception e) {
            logger.error("显示短信模版出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/appAdmin/tempList.vm";
        }
        modelMap.addAttribute("apps", list);// 获取应用下拉框
        modelMap.addAttribute("appId", msgTemplateForm.getAppId());// 回填应用select用
        return "/appAdmin/tempList.vm";
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    public String add(MsgTemplateForm msgTemplateForm, BindingResult result,
                      HttpServletRequest request, ModelMap modelMap) {
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (appids != null && appids.size() > 0) {
            list = appConfigBiz.getApp(appids);
        }
        modelMap.addAttribute("apps", list);// 获取应用下拉框
        return "/appAdmin/addTemp.vm";
    }

    /**
     * 添加短信模版</br> 1.验证数据的有效性。</br> 2.检查模版是否已经存在。</br>
     * 
     * @param msgTemplateForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public String addApp(@Valid MsgTemplateForm msgTemplateForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {

        try {
            List<UmsAppSub> appSubList = appSubBiz.getAppSub(msgTemplateForm.getAppId());
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
            List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
            if (msgTemplateForm.getStartTime() != null && msgTemplateForm.getEndTime() != null) {
                if (appids != null && appids.size() > 0) {
                    list = appConfigBiz.getApp(appids);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                }
                if (msgTemplateForm.getStartTime().getTime() > msgTemplateForm.getEndTime()
                    .getTime()) {
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                    modelMap.addAttribute("message", "数据格式有误");
                    return "/appAdmin/addTemp.vm";
                }
            }
            if (StringUtils.isNotEmpty(msgTemplateForm.getValidTimeScopeStart())
                && StringUtils.isNotEmpty(msgTemplateForm.getValidTimeScopeEnd())) {
                try {
                    if (appids != null && appids.size() > 0) {
                        list = appConfigBiz.getApp(appids);
                        modelMap.addAttribute("apps", list);// 获取应用下拉框
                    }
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = timeFormat.parse(msgTemplateForm.getValidTimeScopeStart());
                    Date date2 = timeFormat.parse(msgTemplateForm.getValidTimeScopeStart());
                    if (date1.getTime() > date2.getTime()) {
                        modelMap.addAttribute("apps", list);// 获取应用下拉框
                        modelMap.addAttribute("message", "数据格式有误");
                        return "/appAdmin/addTemp.vm";
                    }
                } catch (ParseException e) {
                    logger.error("有效时间格式不对", e);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                    modelMap.addAttribute("message", "数据格式有误");
                    return "/appAdmin/addTemp.vm";
                }
            }
            if (result.hasErrors()) {
                if (appids != null && appids.size() > 0) {
                    list = appConfigBiz.getApp(appids);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                }
                modelMap.addAttribute("apps", list);// 获取应用下拉框
                modelMap.addAttribute("message", "数据格式有误");
                return "/appAdmin/addTemp.vm";
            }
            boolean exist = msgTemplateBiz.isExist(msgTemplateForm.getTemplateId());
            if (exist) {
                if (appids != null && appids.size() > 0) {
                    list = appConfigBiz.getApp(appids);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                }
                modelMap.addAttribute("message", "模版ID已经存在！");
                return "/appAdmin/addTemp.vm";
            }
            UmsMsgTemplate umsMsgTemplate = msgTemplateForm.cloneUmsMsgTemplate();
            msgTemplateBiz.saveTemplate(umsMsgTemplate);
            modelMap.addAttribute("umsMsgTemplate", umsMsgTemplate);
            return "/appAdmin/addTempSuccess.vm";
        } catch (Exception e) {
            logger.error("添加模版时系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/appAdmin/addTemp.vm";
        }
    }

    /**
     * 拦截更新模版的get请求。</br> 1.获得要更新的模版Id</br> 2.查询模版</br> 3.跳转页面
     * 
     * @param msgTemplateForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.GET)
    public String updateGet(MsgTemplateForm msgTemplateForm, BindingResult result,
                            HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            modelMap.addAttribute("message", "请选择应用！");
            return "/appAdmin/updateTemp.vm";
        }
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (appids != null && appids.size() > 0) {
            try {
                list = appConfigBiz.getApp(appids);
            } catch (Exception e) {
                logger.error("更新时查询应用的时候系统异常", e);
                modelMap.addAttribute("message", "系统异常！");
                return "/appAdmin/updateTemp.vm";
            }

        }
        modelMap.addAttribute("apps", list);// 获取应用下拉框
        UmsMsgTemplate umsMsgTemplate = msgTemplateBiz.findById(id);
        try {
            List<UmsAppSub> appSubList = appSubBiz.getAppSub(umsMsgTemplate.getAppId());
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
        } catch (Exception e) {
            logger.error("更新时查询子应用的时候系统异常", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/appAdmin/updateTemp.vm";
        }

        ObjectBuilder.copyObject(umsMsgTemplate, msgTemplateForm);
        if (StringUtils.isNotEmpty(umsMsgTemplate.getValidTimeScope())) {
            String[] temp = umsMsgTemplate.getValidTimeScope().split("-");
            if (temp.length == 2) {
                msgTemplateForm.setValidTimeScopeStart(temp[0]);
                msgTemplateForm.setValidTimeScopeEnd(temp[1]);
            }
        }
        msgTemplateForm.setPriority(umsMsgTemplate.getPriority() == null ? null : String
            .valueOf(umsMsgTemplate.getPriority()));
        return "/appAdmin/updateTemp.vm";
    }

    /**
     * 更新短信模版
     * 
     * @param msgTemplateForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public String updatePost(@Valid MsgTemplateForm msgTemplateForm, BindingResult result,
                             HttpServletRequest request, ModelMap modelMap) {

        try {
            List<UmsAppSub> appSubList = appSubBiz.getAppSub(msgTemplateForm.getAppId());
            List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
            List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
            if (msgTemplateForm.getStartTime() != null && msgTemplateForm.getEndTime() != null) {
                if (msgTemplateForm.getStartTime().getTime() > msgTemplateForm.getEndTime()
                    .getTime()) {
                    if (appids != null && appids.size() > 0) {
                        list = appConfigBiz.getApp(appids);
                        modelMap.addAttribute("apps", list);// 获取应用下拉框
                    }
                    modelMap.addAttribute("message", "起始结束时间有误！");
                    return "/appAdmin/updateTemp.vm";
                }
            }
            if (StringUtils.isNotEmpty(msgTemplateForm.getValidTimeScopeStart())
                && StringUtils.isNotEmpty(msgTemplateForm.getValidTimeScopeEnd())) {
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = timeFormat.parse(msgTemplateForm.getValidTimeScopeStart());
                    Date date2 = timeFormat.parse(msgTemplateForm.getValidTimeScopeStart());
                    if (date1.getTime() > date2.getTime()) {
                        if (appids != null && appids.size() > 0) {
                            list = appConfigBiz.getApp(appids);
                            modelMap.addAttribute("apps", list);// 获取应用下拉框
                        }
                        modelMap.addAttribute("message", "有效时间有误！");
                        return "/appAdmin/updateTemp.vm";
                    }
                } catch (ParseException e) {
                    logger.error("有效时间格式不对", e);
                    if (appids != null && appids.size() > 0) {
                        list = appConfigBiz.getApp(appids);
                        modelMap.addAttribute("apps", list);// 获取应用下拉框
                    }
                    modelMap.addAttribute("message", "数据格式有误");
                    return "/appAdmin/updateTemp.vm";
                }
            }

            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            if (result.hasErrors()) {
                if (appids != null && appids.size() > 0) {
                    list = appConfigBiz.getApp(appids);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                }
                modelMap.addAttribute("message", "数据格式有误");
                return "/appAdmin/updateTemp.vm";
            }
            UmsMsgTemplate umsMsgTemplate = msgTemplateBiz.findById(msgTemplateForm.getId());
            if (umsMsgTemplate == null) {
                if (appids != null && appids.size() > 0) {
                    list = appConfigBiz.getApp(appids);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                }
                modelMap.addAttribute("message", "修改失败,模版已经不存在！");
                return "/appAdmin/updateTemp.vm";
            }
            if (!umsMsgTemplate.getTemplateId().equalsIgnoreCase(msgTemplateForm.getTemplateId())) {
                boolean exist = msgTemplateBiz.isExist(msgTemplateForm.getTemplateId());
                if (exist) {
                    if (appids != null && appids.size() > 0) {
                        list = appConfigBiz.getApp(appids);
                        modelMap.addAttribute("apps", list);// 获取应用下拉框
                    }
                    modelMap.addAttribute("message", "模版ID已经存在！");
                    return "/appAdmin/updateTemp.vm";
                }
            }
            umsMsgTemplate = msgTemplateForm.cloneUmsMsgTemplate();
            boolean update = msgTemplateBiz.updateTemplate(umsMsgTemplate);
            if (!update) {
                if (appids != null && appids.size() > 0) {
                    list = appConfigBiz.getApp(appids);
                    modelMap.addAttribute("apps", list);// 获取应用下拉框
                }
                modelMap.addAttribute("message", "更新失败，可能模版已经不存在！");
                return "/appAdmin/updateTemp.vm";
            }
            modelMap.addAttribute("umsMsgTemplate", umsMsgTemplate);
            return "/appAdmin/updateTempSuccess.vm";
        } catch (Exception e) {
            logger.error("更新短信模版时出现系统异常！", e);
            modelMap.addAttribute("message", "系统异常！");
            return "/appAdmin/updateTemp.vm";
        }
    }

    /**
     * 删除模版</br> 1.获得各种参数，方便返回的时候查询条件不丢失。</br> 2.判断模版是否存在。</br> 3.删除存在的模版。</br>
     * 
     * @param msgTemplateForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.GET)
    public String delGet(MsgTemplateForm msgTemplateForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");
        String pageId = request.getParameter("pageId");
        msgTemplateForm.setTemplateId(request.getParameter("tempId"));
        String appId = request.getParameter("appId");
        modelMap.addAttribute("appId", appId);// 回填应用select用
        int page = parseInt(pageId);
        UmsMsgTemplate umsMsgTemplate = msgTemplateForm.cloneUmsMsgTemplate();
        List<String> appids = getRoleAppIds();// 获取当前应用角色所能管理的应用主键ID
        List<String> appIdList = new ArrayList<String>();
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (appids != null && appids.size() > 0) {
            try {
                list = appConfigBiz.getApp(appids);
            } catch (Exception e) {
                modelMap.addAttribute("message", "系统异常！");
                return "/appAdmin/tempList.vm";
            }
        }
        if (appId == null || "".equalsIgnoreCase(appId)) {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    UmsAppInfo info = list.get(i);
                    if (info != null && !"".equals(info.getAppId())) {
                        appIdList.add(info.getAppId());// 获取当前应用角色所能管理的应用APPID
                    }
                }
            }

        } else {
            appIdList.add(appId);
        }
        modelMap.addAttribute("apps", list);// 获取应用下拉框
        try {
            UmsMsgTemplate test = msgTemplateBiz.findById(msgTemplateForm.getId());
            if (test == null) {
                PageResult<UmsMsgTemplate> pageResult = msgTemplateBiz.listUmsTemplate(
                    umsMsgTemplate.getTemplateId(), page, appIdList);
                modelMap.addAttribute("results", pageResult);
                modelMap.addAttribute("message", "该模版已经不存在！");
                return "/appAdmin/tempList.vm";
            }
            msgTemplateBiz.deleteById(id);
        } catch (Exception e) {
            logger.error("删除模版时出现系统异常", e);
            PageResult<UmsMsgTemplate> pageResult = msgTemplateBiz.listUmsTemplate(
                umsMsgTemplate.getTemplateId(), page, appIdList);
            modelMap.addAttribute("results", pageResult);
            modelMap.addAttribute("message", "系统异常！");
            return "/appAdmin/tempList.vm";
        }
        PageResult<UmsMsgTemplate> pageResult = msgTemplateBiz.listUmsTemplate(
            umsMsgTemplate.getTemplateId(), page, appIdList);
        modelMap.addAttribute("results", pageResult);
        modelMap.addAttribute("message", "删除成功！");
        return "/appAdmin/tempList.vm";
    }

    private int parseInt(String pageId) {
        if (StringUtils.isBlank(pageId)) {
            return 1;
        }
        try {
            int page = Integer.parseInt(pageId);
            if (page < 1) {
                page = 1;
            }
            return page;
        } catch (Exception e) {
            return 1;
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

    public MsgTemplateBiz getMsgTemplateBiz() {
        return msgTemplateBiz;
    }

    public void setMsgTemplateBiz(MsgTemplateBiz msgTemplateBiz) {
        this.msgTemplateBiz = msgTemplateBiz;
    }

    public AppConfigBiz getAppConfigBiz() {
        return appConfigBiz;
    }

    public void setAppConfigBiz(AppConfigBiz appConfigBiz) {
        this.appConfigBiz = appConfigBiz;
    }

}
