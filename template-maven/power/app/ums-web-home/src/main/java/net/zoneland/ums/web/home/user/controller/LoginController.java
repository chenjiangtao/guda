/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationPrincipal;
import net.zoneland.ums.biz.config.admin.UmsAreaBiz;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.Base64;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.constants.LoginInfoConstants;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.web.home.base.BaseController;
import net.zoneland.ums.web.home.filter.AccessFilter;
import net.zoneland.ums.web.home.velocity.VelocityToolboxView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

/**
 * 登录控制层
 *
 * @author XuFan
 * @version $Id: LoginController.java, v 0.1 Aug 29, 2012 10:26:49 AM XuFan Exp
 *          $
 */
@Controller
public class LoginController extends BaseController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private UmsUserInfoBiz      umsUserInfoBiz;

    @Autowired
    private UmsAreaBiz          umsAreaBiz;

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
                                                                                                    throws UnsupportedEncodingException {

        Map<String, String> map = getLoginInfo(request);
        if (StringUtils.isNotEmpty(map.get("userId"))
            && StringUtils.isNotEmpty(map.get("password"))) {
            String userId = map.get("userId");
            String password = map.get("password");
            String refer = request.getParameter("refer");
            UmsUserInfo userInfo = umsUserInfoBiz.loginHandle(StringUtils.trim(userId),
                StringUtils.trim(password));
            if (userInfo != null) {
                if (userInfo.getUserId().equals("isNotExistUserId")) {
                    return "requestUser.vm";
                }
                login(userInfo, userId, password, request, response);
                try {
                    if (StringUtils.isNotEmpty(refer)) {
                        response.sendRedirect(VelocityToolboxView.getReqHost(request)
                                              + java.net.URLDecoder.decode(refer, "UTF-8"));
                    } else {
                        response.sendRedirect(VelocityToolboxView.getReqHost(request)
                                              + "/msg/msg.htm");
                    }
                } catch (Exception e) {
                    logger.error("redirct错误", e);
                }
            } else {
                addMsg(modelMap, "用户名密码错误！");
            }

            return "login.vm";
        }
        Object bo = request.getSession().getAttribute(AccessFilter.OPERATIONATTR);
        String refer = request.getParameter("refer");
        if (bo != null) {
            try {
                response.sendRedirect(VelocityToolboxView.getReqHost(request) + "/msg/msg.htm");
            } catch (Exception e) {
                logger.error("redirct错误", e);
            }
        }
        String code = request.getParameter("errorCode");
        if (StringUtils.isNotEmpty(refer)) {
            modelMap.addAttribute("refer",
                java.net.URLEncoder.encode(HtmlUtils.htmlUnescape(refer), "UTF-8"));
        }
        if ("deny".equals(code)) {
            modelMap.addAttribute("errorMsg", "对不起，您权限不足。");
        } else {
            modelMap.addAttribute("errorMsg", code);
        }
        return "login.vm";
    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public String doPost(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("name");
        String password = request.getParameter("password");

        String refer = request.getParameter("refer");
        UmsUserInfo userInfo = umsUserInfoBiz.loginHandle(StringUtils.trim(userId),
            StringUtils.trim(password));
        if (userInfo == null) {
            addMsg(modelMap, "用户名密码错误！");
            try {
                response.sendRedirect(VelocityToolboxView.getReqHost(request)
                                      + "/loginError.htm?errorCode=" + "001");
                return "login.vm";
            } catch (IOException e) {
                logger.error("redirct错误", e);
            }
        }
        if (userInfo.getUserId().equals("isNotExistUserId")) {
            return "requestUser.vm";
        }
        if (!StringRegUtils.isPhoneNumber(userInfo.getPhone())) {
            return "requestPhone.vm";
        }
        login(userInfo, userId, password, request, response);
        try {
            if (StringUtils.isNotEmpty(refer)) {
                response.sendRedirect(VelocityToolboxView.getReqHost(request)
                                      + java.net.URLDecoder.decode(refer, "UTF-8"));
            } else {
                response.sendRedirect(VelocityToolboxView.getReqHost(request) + "/msg/msg.htm");
            }
        } catch (Exception e) {
            logger.error("redirct错误", e);
        }
        return "login.vm";
    }

    @RequestMapping(value = "/loginError.htm", method = RequestMethod.GET)
    public String doErrorGet(ModelMap modelMap, HttpServletRequest request,
                             HttpServletResponse response) {
        String code = request.getParameter("errorCode");
        String errorMsg = "";
        if ("001".equals(code)) {
            errorMsg = "用户名密码错误！";
        }
        addMsg(modelMap, errorMsg);
        return "login.vm";
    }

    private void login(UmsUserInfo userInfo, String userId, String password,
                       HttpServletRequest request, HttpServletResponse response) {
        if (userInfo != null) {
            List<RoleInfo> roles = umsUserInfoBiz.getRoleByUserId(userInfo.getId());
            OperationPrincipal p = new OperationPrincipal();
            p.setLogonId(userId);
            p.setUserId(userInfo.getId());
            p.setUserName(userInfo.getUserName());
            p.setUserPhone(userInfo.getPhone());
            p.setEmail(userInfo.getEmail());
            p.setIp(request.getRemoteAddr());
            p.setProvinceId(umsAreaBiz.getAreaCodes(userInfo.getId()));// 获取所属单位
            p.setAttrMap(LoginInfoConstants.IS_ORG_ADMIN_ATTR, userInfo.getOrgAdmin());
            p.setAttrMap(LoginInfoConstants.ORG_ID_ATTR, userInfo.getOrganizationId());
            p.setAttrMap(LoginInfoConstants.ORG_NAME_ATTR, userInfo.getOrgName());
            RoleInfo[] ro = null;
            if (roles != null && roles.size() == 0) {
                RoleInfo role = new RoleInfo();
                role.setRoleName(RoleNameEnum.NORMAL.getValue());
                ro = new RoleInfo[1];
                ro[0] = role;
            } else {
                ro = new RoleInfo[roles.size()];
                roles.toArray(ro);
            }

            OperationContext context = new OperationContext(p, ro);
            OperationContextHolder.setOperationContext(context);

            request.getSession().setAttribute(AccessFilter.OPERATIONATTR, context);
        }
    }

    private Map<String, String> getLoginInfo(HttpServletRequest request)
                                                                        throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        String remember_box = "";
        String userId = "";
        String password = "";
        Map<String, String> map = new HashMap<String, String>();
        if (cookies != null) {
            for (int i = 0, len = cookies.length; i < len; i++) {
                Cookie cookie = cookies[i];
                if ("remember_box".equalsIgnoreCase(cookie.getName())) {
                    remember_box = cookie.getValue();

                }
                if ("userName".equalsIgnoreCase(cookie.getName())) {
                    userId = cookie.getValue();
                }
                if ("passWord".equalsIgnoreCase(cookie.getName())) {
                    password = new String(Base64.decodeBase64(cookie.getValue()), "UTF-8");
                }
            }
        }
        map.put("userId", userId);
        map.put("password", password);
        map.put("remember_box", remember_box);
        return map;
    }
}