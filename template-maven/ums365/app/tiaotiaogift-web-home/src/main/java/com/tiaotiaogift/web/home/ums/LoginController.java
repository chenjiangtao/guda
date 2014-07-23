/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums;

import java.io.IOException;

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

import com.tiaotiaogift.common.dal.NotifyMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.Notify;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.MD5;
import com.tiaotiaogift.web.home.filter.AccessFilter;
import com.tiaotiaogift.web.home.filter.RoleInfo;
import com.tiaotiaogift.web.home.taobao.TaobaoCallController;
import com.tiaotiaogift.web.home.ums.form.LoginForm;

/**
 * 
 * @author gag
 * @version $Id: LoginController.java, v 0.1 2012-12-13 下午4:05:56 gag Exp $
 */
@Controller
public class LoginController {

    private Logger             logger              = LoggerFactory.getLogger(LoginController.class);

    public static final String NEED_CHECK_CODE_KEY = "NEED_CHECK_CODE_KEY";
    public static final String NEED_CHECK_CODE_VAL = "1";

    public static final String GRADE_ATTR          = "GRADE_ATTR";

    public static final String ADMIN_ATTR          = "ADMIN_ATTR";

    public static final String LINK_ID_ATTR        = "LINK_ID_ATTR";

    @Autowired
    private UserMapper         userMapper;

    @Autowired
    private NotifyMapper       notifyMapper;

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String doGet(LoginForm loginForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap, HttpServletResponse response) throws IOException {
        if (NEED_CHECK_CODE_VAL.equals(request.getSession().getAttribute(NEED_CHECK_CODE_KEY))) {
            modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
        }
        String refer = request.getParameter("refer");
        modelMap.addAttribute("refer", refer);
        if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) != null) {
            response.sendRedirect("ums/index.htm");
            return "";
        }
        return "ums/login.vm";
    }

    @RequestMapping(value = "/loginOut.htm", method = RequestMethod.GET)
    public String doLoginOut(LoginForm loginForm, BindingResult result, HttpServletRequest request,
                             ModelMap modelMap) {
        SecurityContextHolder.clear();
        request.getSession().removeAttribute(AccessFilter.OPERATIONATTR);
        request.getSession().invalidate();
        return "ums/login.vm";
    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public String doPost(@Valid LoginForm loginForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap, HttpServletResponse response) {
        String refer = request.getParameter("refer");
        modelMap.addAttribute("refer", refer);
        if (result.hasErrors()) {
            return "ums/login.vm";
        }
        if (NEED_CHECK_CODE_VAL.equals(request.getSession().getAttribute(NEED_CHECK_CODE_KEY))) {
            modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
            if (!StringUtils.hasText(loginForm.getCheckCode())) {
                result.rejectValue("checkCode", "need-checkCode", "验证码不能为空");
                return "ums/login.vm";
            }
            String kaptchaExpected = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (!loginForm.getCheckCode().equals(kaptchaExpected)) {
                result.rejectValue("checkCode", "checkCode-error", "验证码输入错误");
                logger.warn("登录失败,验证码输入错误." + loginForm);
                return "ums/login.vm";
            }
        }
        User user = userMapper.selectValidByUserId(loginForm.getUserName());
        if (user == null || !user.getPassword().equals(MD5.md5(loginForm.getPassword()))) {
            result.rejectValue("password", "error-password", "用户名或者密码错误");
            request.getSession().setAttribute(NEED_CHECK_CODE_KEY, NEED_CHECK_CODE_VAL);
            modelMap.addAttribute("needCheckCode", NEED_CHECK_CODE_VAL);
            logger.warn("登录失败,用户名或者密码错误." + loginForm);
            return "ums/login.vm";
        }
        request.getSession().removeAttribute(NEED_CHECK_CODE_KEY);
        logger.info("login success." + loginForm);
        Notify notify = notifyMapper.selectByUserId(user.getId());
        String notifyRule = null;
        if (notify != null) {
            notifyRule = notify.getRule();
        }
        login(user, request, response, notifyRule);
        try {
            if (StringUtils.hasText(refer)) {
                String red = java.net.URLDecoder.decode(refer, "UTF-8");
                if (red.indexOf("loginOut.htm") > -1 || red.indexOf("login.htm") > -1) {
                    response.sendRedirect("ums/index.htm");
                } else {
                    response.sendRedirect(red);
                }
            } else {
                response.sendRedirect("ums/index.htm");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "ums/login.vm";
    }

    public static void login(User userInfo, HttpServletRequest request,
                             HttpServletResponse response, String notifyRule) {
        if (userInfo != null) {
            //  List<RoleInfo> roles = umsUserInfoBiz.getRoleByUserId(userInfo.getId());
            OperationPrincipal p = new OperationPrincipal();
            p.setLogonId(userInfo.getUserId());
            p.setUserId(userInfo.getId());
            p.setUserName(userInfo.getUserId());
            p.setUserPhone(userInfo.getPhone());
            p.setEmail(userInfo.getEmail());
            p.setIp(request.getRemoteAddr());
            p.setAttrMap(GRADE_ATTR, userInfo.getGrade());
            p.setAttrMap(LINK_ID_ATTR, userInfo.getLinkId());
            if (StringUtils.hasText(notifyRule)) {
                p.setAttrMap(TaobaoCallController.NOTIFY_RULE, notifyRule);
            }
            if (userInfo.getUserId().contains("admin")) {
                p.setAttrMap(ADMIN_ATTR, "ADMIN");
            }
            RoleInfo role = new RoleInfo();
            role.setRoleName("ROLE_LOGIN");
            OperationContext context = new OperationContext(p, new RoleInfo[] { role });
            OperationContextHolder.setOperationContext(context);

            request.getSession().setAttribute(AccessFilter.OPERATIONATTR, context);
        }
    }

}
