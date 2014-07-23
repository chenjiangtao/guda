/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.util.MD5;
import com.foodoon.info.web.home.user.form.SetPassForm;

/**
 * 
 * @author foodoon
 * @version $Id: SetPassController.java, v 0.1 2013年10月19日 上午11:36:33 foodoon Exp $
 */
@Controller
public class SetPassController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/home/setPass.htm", method = RequestMethod.GET)
    public String doGet(SetPassForm form, BindingResult result, HttpServletRequest request,
                        HttpServletResponse response, ModelMap modelMap) throws IOException {

        return "user/home/setPass.vm";
    }

    @RequestMapping(value = "/home/setPass.htm", method = RequestMethod.POST)
    public String doPost(@Valid SetPassForm form, BindingResult result, HttpServletRequest request,
                         HttpServletResponse response, ModelMap modelMap) throws IOException {
        if (result.hasErrors()) {
            return "user/home/setPass.vm";
        }
        if (!form.getNewPass().equals(form.getNewPass2())) {
            result.rejectValue("newPass2", "newPass2-notmatch", "两次输入的密码不一致");
            return "user/home/setPass.vm";
        }
        User u = new User();
        u.setId(OperationContextHolder.getPrincipal().getUserId());
        u.setPassword(MD5.md5(form.getNewPass()));
        int res = userMapper.updateByPrimaryKeySelective(u);
        return "user/home/setPassResult.vm";
    }

}
