/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.personal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.web.home.ums.form.LoginForm;

/**
 * 
 * @author gang
 * @version $Id: GroupController.java, v 0.1 2012-12-15 上午9:54:50 gang Exp $
 */
@Controller
public class GroupController {

    @RequestMapping(value = "/personal/group.htm", method = RequestMethod.GET)
    public String doGet(LoginForm loginForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {

        return "ums/personal/group.vm";
    }

}
