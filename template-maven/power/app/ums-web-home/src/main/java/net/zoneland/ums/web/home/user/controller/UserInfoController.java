/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.controller;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.user.UmsOrganizationBiz;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 个人资料更新类，包括群组的维护，密码的修改，及手机号、QQ等的修改！
 * 
 * @author wangyong
 * @version $Id: UserInfoController.java, v 0.1 2012-8-25 下午3:57:54 wangyong Exp
 *          $
 */
@Controller
public class UserInfoController extends BaseController {

    private final static Logger logger = Logger.getLogger(UserInfoController.class);
    @Autowired
    private UmsUserInfoBiz      umsUserInfoBiz;

    @Autowired
    private UmsOrganizationBiz  umsOrganizationBiz;

    /**
     * 进入个人资料页面
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/user/user.htm", method = RequestMethod.GET)
    public String doGet(UmsUserInfo userInfo, BindingResult bindResult, HttpServletRequest request,
                        ModelMap modelMap) {
        String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
        UmsUserInfo umsUserInfo = null;
        String orgName = "";
        try {
            umsUserInfo = umsUserInfoBiz.getUmsUserInfoById(userId);
            orgName = umsOrganizationBiz.getOrgNameById(umsUserInfo.getOrganizationId());
        } catch (Exception e) {
            logger.error("查询个人资料时出现异常！", e);
            return "user/user.vm";
        }
        modelMap.addAttribute("orgName", orgName);
        modelMap.addAttribute("userInfo", umsUserInfo);
        return "user/user.vm";
    }

    // /**
    // *修改个人资料！
    // *
    // * @param userInfoForm
    // * @param result
    // * @param request
    // * @param modelMap
    // * @return
    // */
    // @RequestMapping(value = "/user/user.htm", method = RequestMethod.POST)
    // public String upDate(UmsUserInfoForm userInfoForm, BindingResult result,
    // HttpServletRequest request, ModelMap modelMap) {
    // String userId =
    // SecurityContextHolder.getContext().getPrincipal().getUserId();
    // String orgName = userInfoForm.getOrgName();
    //
    // if (userInfoForm != null &&
    // userId.equalsIgnoreCase(userInfoForm.getId())) {
    // UmsUserInfo umsUserInfo = new UmsUserInfo();
    // umsUserInfo = userInfoForm.cloneUmsUserInfo();
    // try {
    // umsUserInfoBiz.updateUmsUserInfo(umsUserInfo);
    // addSuccessMsg(modelMap, "修改成功");
    // } catch (Exception e) {
    // logger.error("个人资料更新异常", e);
    // addErrorMsg(modelMap, "修改失败");
    // }
    // } else {
    // addErrorMsg(modelMap, "修改失败");
    // }
    // modelMap.addAttribute("orgName", orgName);
    // modelMap.addAttribute("userInfo", userInfoForm);
    // return "user/user.vm";
    // }

    // /**
    // *修改个人密码
    // *
    // * @param passwordForm
    // * @param request
    // * @param modelMap
    // * @return
    // */
    // @RequestMapping(value = "/user/password.htm", method =
    // RequestMethod.POST)
    // public String password(@Valid PasswordForm passwordForm,
    // HttpServletRequest request,
    // BindingResult result, ModelMap modelMap) {
    // if (result.hasErrors()) {
    // return "user/password.vm";
    // }
    //
    // if (passwordForm.getNewPassword().equals(passwordForm.getRePassword())) {
    // String userId =
    // SecurityContextHolder.getContext().getPrincipal().getUserId();
    // UmsUserInfo umsUserInfo = umsUserInfoBiz.getUmsUserInfoById(userId);
    // String oldPassword = MD5.md5(passwordForm.getOldPassword());
    // String newPassword = MD5.md5(passwordForm.getNewPassword());
    // if (oldPassword.equals(umsUserInfo.getPassword())) {
    // try {
    // umsUserInfoBiz.updatePassword(userId, newPassword);
    // addSuccessMsg(modelMap, "密码修改成功");
    // } catch (Exception e) {
    // logger.error("修改密码异常", e);
    // addErrorMsg(modelMap, "密码修改失败");
    // }
    // } else {
    // addErrorMsg(modelMap, "原密码不正确");
    // }
    // } else {
    // addErrorMsg(modelMap, "新密码与确认密码不一致");
    // }
    //
    // return "user/password.vm";
    // }
    //
    // /**
    // *进入密码修改页面
    // *
    // * @param passwordForm
    // * @param request
    // * @param modelMap
    // * @return
    // */
    // @RequestMapping(value = "/user/password.htm", method = RequestMethod.GET)
    // public String gopassword(PasswordForm passwordForm, HttpServletRequest
    // request,
    // ModelMap modelMap) {
    // return "user/password.vm";
    // }
}
