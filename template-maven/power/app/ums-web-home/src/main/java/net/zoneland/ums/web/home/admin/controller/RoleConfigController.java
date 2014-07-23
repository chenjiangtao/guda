/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.RoleConfigBiz;
import net.zoneland.ums.biz.config.admin.UmsAreaBiz;
import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.biz.config.util.RoleConfigAssignment;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.UserInfoForm;
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
 * 角色分配控制层
 * @author XuFan
 * @version $Id: RoleConfigController.java, v 0.1 Aug 28, 2012 4:44:56 PM XuFan Exp $
 */
@Controller
public class RoleConfigController extends BaseController {

    private static final Logger logger    = Logger.getLogger(RoleConfigController.class);

    static Map<String, String>  roleNames = new LinkedHashMap<String, String>();

    static {
        roleNames.put(RoleNameEnum.APP.getValue(), RoleNameEnum.APP.getDescription());
        roleNames.put(RoleNameEnum.ADMIN.getValue(), RoleNameEnum.ADMIN.getDescription());
        roleNames.put(RoleNameEnum.QUERY.getValue(), RoleNameEnum.QUERY.getDescription());
        roleNames.put(RoleNameEnum.NORMAL.getValue(), RoleNameEnum.NORMAL.getDescription());
    }

    @Autowired
    private RoleConfigBiz       roleConfigBiz;

    @Autowired
    private UmsAreaBiz          umsAreaBiz;

    /**
     * 拦截角色分配get请求，分页显示权限配置用户信息
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param modelMap ModelMap视图对象
     * @param request request请求
     * @return
     */
    @RequestMapping(value = "admin/role/roleconfig.htm", method = RequestMethod.GET)
    public String doGet(UserInfoForm userInfoForm, BindingResult result, ModelMap modelMap,
                        HttpServletRequest request) {
        String pageId = request.getParameter("pageId");// 获得当前页数      
        if (StringHelper.isNotEmpty(pageId)) {
            int pageReturnId = Integer.parseInt(pageId);// 将当前页转换成int类型
            if (pageReturnId > 1) {// 如果当前页不是空值          
                return doPost(userInfoForm, result, pageReturnId, modelMap);
            }
        }
        return doPost(userInfoForm, result, 1, modelMap);// 如果当前页数取得的是空值就显示第一页
    }

    /**
     * 拦截角色分配查询post请求,条件查询分页显示权限配置用户信息
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param pageId 当前页
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/role/roleconfig.htm", method = RequestMethod.POST)
    public String doPost(@Valid UserInfoForm userInfoForm, BindingResult result, Integer pageId,
                         ModelMap modelMap) {
        UmsUserInfo umsUserInfo = new UmsUserInfo();
        umsUserInfo = parseUmsUserInfo(userInfoForm, umsUserInfo);// 将表单数据解析存入UmsUserInfo对象
        PageResult<UmsUserInfo> pageResult = null;
        try {
            pageResult = roleConfigBiz.showAllUser(umsUserInfo, pageId);// 分页显示全部用户信息
            userInfoForm = new UserInfoForm(pageResult.getCurPage(), userInfoForm.getUserId(),
                userInfoForm.getUserName(), userInfoForm.getOrgId(), userInfoForm.getCheckId(),
                userInfoForm.getPhone());
        } catch (Exception e) {
            logger.error("权限配置查询用户信息出错", e);
            addErrorMsg(modelMap, "权限配置查询用户信息出错");
        }
        modelMap.addAttribute("result", pageResult);// 分页显示用户结果集
        modelMap.addAttribute("user", userInfoForm);// 提供前台显示的用户信息form
        return "admin/role/roleconfig.vm";
    }

    /**
     * 将UserInfoForm表单提交的数据解析并存入UmsUserInfo对象
     * 
     * @param userInfoForm 用户信息表单对象
     * @param umsUserInfo 用户信息对象
     * @return
     */
    private UmsUserInfo parseUmsUserInfo(UserInfoForm userInfoForm, UmsUserInfo umsUserInfo) {
        umsUserInfo.setUserName(StringHelper.isEmpty(userInfoForm.getUserName()) ? null
            : userInfoForm.getUserName());// 根据员工姓名查询
        umsUserInfo.setUserId((StringHelper.isEmpty(userInfoForm.getUserId()) ? null : userInfoForm
            .getUserId()));// 根据员工工号查询
        umsUserInfo.setPhone(StringHelper.isEmpty(userInfoForm.getPhone()) ? null : userInfoForm
            .getPhone());// 根据手机号查询
        return umsUserInfo;
    }

    /**
     * 当点击【保存】按钮时，给用户分配具体的角色
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param request request请求
     * @param response response请求
     * @param modelMap ModelMap视图对象
     */
    @RequestMapping(value = "admin/role/giveRole.ajax", method = RequestMethod.POST)
    public void giveRole(UserInfoForm userInfoForm, BindingResult result,
                         HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String userId = request.getParameter("userId");
        String appIdStr = request.getParameter("appIdStr");
        String isAppRole = request.getParameter("isAppRole");
        String orgAdmin = request.getParameter("isOrgAdmin");
        String customOrgName = request.getParameter("customOrgName");
        AjaxResult ajaxResult = null;
        try {
            ajaxResult = roleConfigBiz.giveRole(userId, appIdStr, isAppRole, orgAdmin,
                customOrgName);// 给用户分配具体角色
        } catch (Exception e) {
            logger.error("给用户分配具体的角色出错", e);
            addErrorMsg(modelMap, "给用户分配具体的角色出错");
        }
        ajaxReturn(ajaxResult, response);
    }

    /**
     * 点击【分配角色】，进入给用户分配角色页面
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param modelMap ModelMap视图对象
     * @param request request请求
     * @param response response请求
     * @return
     */
    @RequestMapping(value = "admin/role/giverole.htm", method = RequestMethod.GET)
    public String roleAssignment(UserInfoForm userInfoForm, BindingResult result,
                                 ModelMap modelMap, HttpServletRequest request,
                                 HttpServletResponse response) {
        String id = request.getParameter("id");// 获取当前所选中员工的ID
        String pageId = request.getParameter("pageId");// 获得当前页数
        try {
            RoleConfigAssignment roleConfigAssignment = roleConfigBiz.roleAssignment(id);// 给用户角色分配权限
            if (roleConfigAssignment == null) {
                return "admin/role/giverole.vm";
            }
            modelMap.addAttribute("umsUserInfo", roleConfigAssignment.getUmsUserInfo());// 绑定获得的用户表信息
            modelMap.addAttribute("orgName", roleConfigAssignment.getOrgName());// 绑定获得的组织名
            if (StringUtils.hasText(roleConfigAssignment.getUmsUserInfo().getOrgName())) {
                modelMap.addAttribute("customOrgName", roleConfigAssignment.getUmsUserInfo()
                    .getOrgName());// 绑定获得的组织名
            } else {
                modelMap.addAttribute("customOrgName", roleConfigAssignment.getOrgName());// 绑定获得的组织名
            }
            modelMap.addAttribute("umsAppInfos", roleConfigAssignment.getUmsAppInfos());// 绑定获得的应用表信息集合
            modelMap.addAttribute("appStartWithABCD", roleConfigAssignment.getAppStartWithABCD());// 以应用名第一个汉字首字母是A或B或C或D开头的集合
            modelMap.addAttribute("appStartWithEFGH", roleConfigAssignment.getAppStartWithEFGH());// 以应用名第一个汉字首字母是E或F或G或H开头的集合
            modelMap.addAttribute("appStartWithIJK", roleConfigAssignment.getAppStartWithIJK());// 以应用名第一个汉字首字母是I或J或K开头的集合
            modelMap.addAttribute("appStartWithLMN", roleConfigAssignment.getAppStartWithLMN());// 以应用名第一个汉字首字母是L或M或N开头的集合
            modelMap.addAttribute("appStartWithOPQR", roleConfigAssignment.getAppStartWithOPQR());// 以应用名第一个汉字首字母是O或P或Q或R开头的集合
            modelMap.addAttribute("appStartWithSTUV", roleConfigAssignment.getAppStartWithSTUV());// 以应用名第一个汉字首字母是S或T或U或V开头的集合
            modelMap.addAttribute("appStartWithWXYZ", roleConfigAssignment.getAppStartWithWXYZ());// 以应用名第一个汉字首字母是W或X或Y或Z开头的集合
            modelMap.addAttribute("appStartWithOthers",
                roleConfigAssignment.getAppStartWithOthers());// 以应用名第一个汉字首字母是非字母的集合
            modelMap.addAttribute("umsAppInfoSizes", roleConfigAssignment.getUmsAppInfos().size());// 绑定获得的应用表信息集合的大小
            modelMap.addAttribute("curRoleName", roleConfigAssignment.getCurRoleName());// 获取当前点击的所要分配权限的用户的角色名
            modelMap.addAttribute("pageId", pageId);// 获取当前页
            modelMap.addAttribute("selectappIds", roleConfigAssignment.getSelectappIds());
            modelMap
                .addAttribute("selectappIdSizes", roleConfigAssignment.getSelectappIds().size());// 绑定当前用户所拥有的应用集合的大小
            modelMap.addAttribute("roleNames", roleNames);// 绑定角色名
        } catch (Exception e) {
            logger.error("分配角色操作出错", e);
            addErrorMsg(modelMap, "分配角色操作出错");
        }
        return "admin/role/giverole.vm";
    }

    /**
     * 拦截Post请求，根据条件查询用户信息
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param request request请求
     * @param modelMap ModelMap视图对象
     * @return
     */
    @RequestMapping(value = "admin/role/queryrole.htm", method = RequestMethod.POST)
    public String doPost(@Valid UserInfoForm userInfoForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        UmsUserInfo umsUserInfo = new UmsUserInfo();
        umsUserInfo = parseUmsUserInfo(userInfoForm, umsUserInfo);
        PageResult<UmsUserInfo> pageResult = null;
        try {
            pageResult = roleConfigBiz.showAllUser(umsUserInfo, userInfoForm.getPageId());// 分页显示全部用户
            pageResult.setTotal(roleConfigBiz.getAllUserNum(umsUserInfo));// 查询条件的结果总数
        } catch (Exception e) {
            logger.error("查询应用出错", e);
            addErrorMsg(modelMap, "查询应用出错");
        }
        modelMap.addAttribute("result", pageResult);
        modelMap.addAttribute("user", userInfoForm);
        return "admin/role/roleconfig.vm";
    }

    /**
     * 点击【分配单位】，进入给用户分配单位页面
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param modelMap ModelMap视图对象
     * @param request request请求
     * @param response response请求
     * @return
     */
    @RequestMapping(value = "admin/role/giveArea.htm", method = RequestMethod.GET)
    public String areaAssignment(UserInfoForm userInfoForm, BindingResult result,
                                 ModelMap modelMap, HttpServletRequest request,
                                 HttpServletResponse response) {
        String id = request.getParameter("id");// 获取当前所选中员工的ID
        String pageId = request.getParameter("pageId");// 获得当前页数
        try {
            RoleConfigAssignment roleConfigAssignment = roleConfigBiz.areaAssignment(id);// 给用户分配单位
            if (roleConfigAssignment == null) {
                return "admin/role/giveArea.vm";
            }
            modelMap.addAttribute("umsUserInfo", roleConfigAssignment.getUmsUserInfo());// 绑定获得的用户表信息
            modelMap.addAttribute("orgName", roleConfigAssignment.getOrgName());// 绑定获得的组织名
            modelMap.addAttribute("curRoleName",
                roleNames.get(roleConfigAssignment.getCurRoleName()));// 获取当前点击的所要分配单位的用户的角色名
            modelMap.addAttribute("pageId", pageId);// 获取当前页
        } catch (Exception e) {
            logger.error("分配单位操作出错", e);
            addErrorMsg(modelMap, "分配单位操作出错");
        }
        return "admin/role/giveArea.vm";
    }

    /**
     * 当点击【保存】按钮时，给用户分配具体的单位
     * 
     * @param userInfoForm 用户信息表单对象
     * @param result 数据绑定验证
     * @param request request请求
     * @param response response请求
     * @param modelMap ModelMap视图对象
     */
    @RequestMapping(value = "admin/role/giveArea.ajax", method = RequestMethod.POST)
    public void giveArea(UserInfoForm userInfoForm, BindingResult result,
                         HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String areaCodeStr = request.getParameter("areaCodeStr");
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        AjaxResult ajaxResult = null;
        try {
            ajaxResult = umsAreaBiz.giveArea(areaCodeStr, userId, userName);// 给用户分配具体单位
        } catch (Exception e) {
            logger.error("给用户分配具体的单位出错", e);
            addErrorMsg(modelMap, "给用户分配具体的单位出错");
        }
        ajaxReturn(ajaxResult, response);
    }
}
