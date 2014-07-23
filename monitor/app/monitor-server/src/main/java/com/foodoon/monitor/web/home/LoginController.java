/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationPrincipal;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.monitor.dal.UserMapper;
import com.foodoon.monitor.dal.dataobject.User;
import com.foodoon.monitor.web.home.filter.AccessFilter;
import com.foodoon.monitor.web.home.filter.RoleInfo;
import com.foodoon.monitor.web.home.form.LoginForm;

/**
 * @author foodoon
 * @version $Id: LoginController.java, v 0.1 2013-6-5 下午8:08:23 foodoon Exp $
 */
@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserMapper          userMapper;

    @RequestMapping(value = "login.htm", method = RequestMethod.GET)
    public String doGet(LoginForm loginForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        String refer = request.getParameter("refer");
        modelMap.addAttribute("refer", refer);
        return "login.vm";

    }

    @RequestMapping(value = "login.htm", method = RequestMethod.POST)
    public String doLogin(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request,
                          HttpServletResponse response, ModelMap modelMap) {
        String refer = request.getParameter("refer");
        modelMap.addAttribute("refer", refer);
        if (result.hasErrors()) {
            modelMap.addAttribute("errorMsg", "用户名或者密码不能为空");
            return "login.vm";
        }
        User user = userMapper.selectValidByUserId(loginForm.getUserName());
        logger.info("根据userid" + loginForm.getUserName() + "查询user:" + user + "pas+s:" + loginForm.getPassword()
                    + ",md5passoword:" + MD5.md5(loginForm.getPassword()));
        if (user == null || !user.getPassword().equals(MD5.md5(loginForm.getPassword()))) {
            modelMap.addAttribute("errorMsg", "用户名或者密码不正确");
            return "login.vm";
        }

        login(user, request, response);
        try {
            if (StringUtils.hasText(refer)) {
                String red = java.net.URLDecoder.decode(refer, "UTF-8");
                if (red.indexOf("loginOut.htm") > -1 || red.indexOf("login.htm") > -1) {
                    response.sendRedirect("indexServerChart.htm");
                } else {
                    response.sendRedirect(red);
                }
            } else {
                response.sendRedirect("indexServerChart.htm");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "login.vm";

    }

    @RequestMapping(value = "/loginOut.htm", method = RequestMethod.GET)
    public String doLoginOut(LoginForm loginForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        SecurityContextHolder.clear();
        request.getSession().removeAttribute(AccessFilter.OPERATIONATTR);
        request.getSession().invalidate();
        return "login.vm";
    }

    private void login(User userInfo, HttpServletRequest request, HttpServletResponse response) {
        if (userInfo != null) {
            // List<RoleInfo> roles = umsUserInfoBiz.getRoleByUserId(userInfo.getId());
            OperationPrincipal p = new OperationPrincipal();
            p.setLogonId(userInfo.getUserId());
            p.setUserId(userInfo.getId());
            p.setUserName(userInfo.getUserId());

            p.setIp(request.getRemoteAddr());

            RoleInfo role = new RoleInfo();
            role.setRoleName("ROLE_LOGIN");
            OperationContext context = new OperationContext(p, new RoleInfo[] { role });
            OperationContextHolder.setOperationContext(context);

            request.getSession().setAttribute(AccessFilter.OPERATIONATTR, context);
        }
    }
}
