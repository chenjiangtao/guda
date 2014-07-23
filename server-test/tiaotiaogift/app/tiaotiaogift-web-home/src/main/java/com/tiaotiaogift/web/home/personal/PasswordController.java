/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
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
import com.tiaotiaogift.common.util.MD5;
import com.tiaotiaogift.web.home.personal.form.PasswordForm;

/**
 * 
 * @author gang
 * @version $Id: PasswordController.java, v 0.1 2012-12-15 下午6:30:31 gang Exp $
 */
@Controller
public class PasswordController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/personal/password.htm", method = RequestMethod.GET)
    public String doGet(PasswordForm form, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {

        return "ums/personal/password.vm";
    }

    @RequestMapping(value = "/personal/password.htm", method = RequestMethod.POST)
    public String doPost(@Valid PasswordForm passwordForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            return "ums/personal/password.vm";
        }
        if (!passwordForm.getPassword().equals(passwordForm.getSecPassword())) {
            result.rejectValue("secPassword", "pass-not-match", "两次密码不一致");
            return "ums/personal/password.vm";
        }
        User user = userMapper
            .selectByPrimaryKey(OperationContextHolder.getPrincipal().getUserId());
        if (!user.getPassword().equals(MD5.md5(passwordForm.getOldPassword()))) {
            result.rejectValue("oldPassword", "old-pass-error", "输入的旧密码不正确");
            return "ums/personal/password.vm";
        }
        user = new User();
        user.setId(OperationContextHolder.getPrincipal().getUserId());
        user.setPassword(MD5.md5(passwordForm.getPassword()));
        try {
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {

        }
        return "ums/personal/passwordResult.vm";
    }
}
