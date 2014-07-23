/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.admin;

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

import com.foodoon.monitor.dal.UserMapper;
import com.foodoon.monitor.dal.dataobject.User;
import com.foodoon.monitor.web.home.MD5;
import com.foodoon.monitor.web.home.form.PasswordForm;

/**
 * 
 * @author foodoon
 * @version $Id: SysPassController.java, v 0.1 2013-6-7 下午9:38:33 foodoon Exp $
 */
@Controller
public class SysPassController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/sysPass.htm", method = RequestMethod.GET)
    public String doGet(PasswordForm passwordForm, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return "sysPass.vm";
    }

    @RequestMapping(value = "/sysPass.htm", method = RequestMethod.POST)
    public String doSave(@Valid PasswordForm passwordForm, BindingResult result,
                         HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute("errorMsg", "输入框不能为空");
            return "sysPass.vm";
        }
        if (!passwordForm.getNewPass().equals(passwordForm.getNewPassSec())) {
            modelMap.addAttribute("errorMsg", "两次密码不一致");
            return "sysPass.vm";
        }
        User user = userMapper.selectValidByUserId(OperationContextHolder.getPrincipal()
            .getLogonId());
        if (user == null || !user.getPassword().equals(MD5.md5(passwordForm.getOld()))) {
            modelMap.addAttribute("errorMsg", "旧密码不正确");
            return "sysPass.vm";
        }
        user.setPassword(MD5.md5(passwordForm.getNewPass()));
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res == 1) {
            return "sysPassSuccess.vm";
        } else {
            modelMap.addAttribute("errorMsg", "修改失败，服务器错误");
            return "sysPass.vm";
        }
    }

}
