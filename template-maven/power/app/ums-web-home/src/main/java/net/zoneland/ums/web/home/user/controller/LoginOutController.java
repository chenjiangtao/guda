/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.web.home.filter.AccessFilter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author gang
 * @version $Id: LoginOutController.java, v 0.1 2012-8-30 下午9:57:31 gang Exp $
 */
@Controller
@RequestMapping("user/loginOut.htm")
public class LoginOutController {

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap model)
                                                                                                 throws IOException {
        request.getSession().removeAttribute(AccessFilter.OPERATIONATTR);
        request.getSession().invalidate();// 释放session，退出后使cookie值JSESSIONID有变化
        SecurityContextHolder.clear();
        //        Cookie[] cookies = request.getCookies();
        //        for (Cookie cookie : cookies) {
        //            cookie.setMaxAge(0);
        //            response.addCookie(cookie);
        //        }
        return "login.vm";

    }

}
