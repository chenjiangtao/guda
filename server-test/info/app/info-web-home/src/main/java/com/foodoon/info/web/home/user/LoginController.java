/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationPrincipal;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.dataobject.UserExample;
import com.foodoon.info.common.util.MD5;
import com.foodoon.info.web.home.filter.AccessFilter;
import com.foodoon.info.web.home.filter.RoleInfo;
import com.foodoon.info.web.home.user.form.UserForm;

/**
 * 类LoginController.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月14日 上午9:46:53
 */
@Controller
public class LoginController {

    public static final String NEED_CHECK_CODE_KEY = "NEED_CHECK_CODE_KEY";
    public static final String NEED_CHECK_CODE_VAL = "1";

    @Autowired
    private UserMapper         userMapper;

    @RequestMapping(value = "/user/login.htm", method = RequestMethod.GET)
    public String doGet(UserForm userForm, BindingResult bindResult, HttpServletRequest request,
                        HttpServletResponse response, ModelMap modelMap) throws IOException {
        if (NEED_CHECK_CODE_VAL.equals(request.getSession().getAttribute(NEED_CHECK_CODE_KEY))) {
            modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
        }
        String refer = request.getParameter("refer");
        modelMap.addAttribute("refer", refer);
        if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) != null) {
            response.sendRedirect("index.htm");
            return "";
        }
        return "user/login.vm";
    }

    @net.zoneland.mvc.runtime.core.form.Form
    @RequestMapping(value = "/user/login.htm", method = RequestMethod.POST)
    public String doPost(@Valid UserForm userForm, BindingResult bindResult, HttpServletResponse response,
                         HttpServletRequest request, ModelMap modelMap) {
        String refer = request.getParameter("refer");
        modelMap.addAttribute("refer", refer);
        if (bindResult.hasErrors()) {
            return "user/login.vm";
        }
        if (NEED_CHECK_CODE_VAL.equals(request.getSession().getAttribute(NEED_CHECK_CODE_KEY))) {
            modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
            if (!StringUtils.hasText(userForm.getCheckCode())) {
                bindResult.rejectValue("checkCode", "need-checkCode", "验证码不能为空");
                return "user/login.vm";
            }
            String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (!userForm.getCheckCode().equals(kaptchaExpected)) {
                bindResult.rejectValue("checkCode", "checkCode-error", "验证码输入错误");
                return "user/login.vm";
            }
        }
        UserExample uexa = new UserExample();
        uexa.createCriteria().andUserIdEqualTo(userForm.getUserName());
        List<User> users = userMapper.selectByExample(uexa);
        if (users.size() == 0) {
            bindResult.rejectValue("password", "error-password", "用户名或者密码错误");
            request.getSession().setAttribute(NEED_CHECK_CODE_KEY, NEED_CHECK_CODE_VAL);
            modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
            return "user/login.vm";
        } else {
            User user = users.get(0);
            if (user.getPassword().equals(MD5.md5(userForm.getPassword()))) {
                login(user, request, response);
            } else {
                bindResult.rejectValue("password", "error-password", "用户名或者密码错误");
                request.getSession().setAttribute(NEED_CHECK_CODE_KEY, NEED_CHECK_CODE_VAL);
                modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
                return "user/login.vm";
            }
        }

        request.getSession().removeAttribute(NEED_CHECK_CODE_KEY);

        try {
            if (StringUtils.hasText(refer)) {
                String red = java.net.URLDecoder.decode(refer, "UTF-8");
                if (red.indexOf("loginOut.htm") > -1 || red.indexOf("login.htm") > -1) {
                    response.sendRedirect("/index.htm");
                } else {
                    response.sendRedirect(red);
                }
            } else {
                response.sendRedirect("/index.htm");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/login.vm";
    }

    @RequestMapping(value = "/loginOut.htm", method = RequestMethod.GET)
    public String doLoginOut(UserForm loginForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        SecurityContextHolder.clear();
        request.getSession().removeAttribute(AccessFilter.OPERATIONATTR);
        request.getSession().invalidate();
        return "user/login.vm";
    }

    private void login(User userInfo, HttpServletRequest request, HttpServletResponse response) {
        if (userInfo != null) {
            // List<RoleInfo> roles = umsUserInfoBiz.getRoleByUserId(userInfo.getId());
            OperationPrincipal p = new OperationPrincipal();
            p.setLogonId(userInfo.getUserId());
            p.setUserId(userInfo.getId());
            p.setUserName(userInfo.getUserId());
            p.setUserPhone(userInfo.getPhone());
            p.setEmail(userInfo.getEmail());
            p.setIp(request.getRemoteAddr());

            RoleInfo role = new RoleInfo();
            role.setRoleName("ROLE_LOGIN");
            OperationContext context = new OperationContext(p, new RoleInfo[] { role });
            OperationContextHolder.setOperationContext(context);

            request.getSession().setAttribute(AccessFilter.OPERATIONATTR, context);
        }
    }

}
