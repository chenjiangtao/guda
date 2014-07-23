/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.personal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.web.home.ums.admin.form.UserForm;
import com.tiaotiaogift.web.mvc.Form;

/**
 * 
 * @author foodoon
 * @version $Id: ModifyUserController.java, v 0.1 2013-7-29 上午8:46:24 foodoon Exp $
 */
@Controller
public class ModifyUserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/personal/modifyUser.htm", method = RequestMethod.GET)
    public String doGet(UserForm userForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        userForm = new UserForm();
        userForm.setId(OperationContextHolder.getPrincipal().getUserId());
        userForm.setPhone(OperationContextHolder.getPrincipal().getUserPhone());
        userForm.setEmail(OperationContextHolder.getPrincipal().getEmail());
        userForm.setUserId(OperationContextHolder.getPrincipal().getUserName());
        modelMap.addAttribute("userForm", userForm);
        return "ums/personal/modifyUser.vm";
    }

    @Form
    @RequestMapping(value = "/personal/modifyUser.htm", method = RequestMethod.POST)
    public String doSave(@Valid UserForm userForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            return "ums/personal/modifyUser.vm";
        }
        if (OperationContextHolder.getPrincipal().getUserId().equals(userForm.getId())) {
            User user = new User();
            user.setId(userForm.getId());
            if (!OperationContextHolder.getPrincipal().getEmail().equals(userForm.getEmail())) {
                user.setEmail(userForm.getEmail());
            }
            if (!OperationContextHolder.getPrincipal().getUserPhone().equals(userForm.getPhone())) {
                user.setPhone(userForm.getPhone());
            }
            if (user.getEmail() != null || user.getPhone() != null) {
                int res = userMapper.updateByPrimaryKeySelective(user);
                if (res == 1) {
                    if (user.getPhone() != null) {
                        OperationContextHolder.getPrincipal().setUserPhone(user.getPhone());
                    }
                    if (user.getEmail() != null) {
                        OperationContextHolder.getPrincipal().setEmail(user.getEmail());
                    }
                    return "ums/personal/modifyUserSuccess.vm";
                }
            } else {
                modelMap.addAttribute("tips", "资料没有做过修改");
            }
        }

        return "ums/personal/modifyUser.vm";
    }

}
