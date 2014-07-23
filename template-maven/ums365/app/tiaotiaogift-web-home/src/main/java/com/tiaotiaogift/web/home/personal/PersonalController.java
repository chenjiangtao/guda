/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.personal;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.web.home.personal.form.PasswordForm;
import com.tiaotiaogift.web.home.ums.admin.form.UserForm;

/**
 * 
 * @author foodoon
 * @version $Id: PersonalController.java, v 0.1 2013-8-7 上午8:35:42 foodoon Exp $
 */
@Controller
public class PersonalController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/personal/personal.htm", method = RequestMethod.GET)
    public String doGet(PasswordForm form, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        UserForm userForm = new UserForm();
        userForm.setId(OperationContextHolder.getPrincipal().getUserId());
        userForm.setPhone(OperationContextHolder.getPrincipal().getUserPhone());
        userForm.setEmail(OperationContextHolder.getPrincipal().getEmail());
        userForm.setUserId(OperationContextHolder.getPrincipal().getUserName());
        modelMap.addAttribute("userForm", userForm);

        return "ums/personal/personal.vm";
    }

}
