/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.user;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.form.Form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.biz.common.email.MailService;
import com.foodoon.info.biz.common.email.VelocityHelper;
import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.dataobject.UserExample;
import com.foodoon.info.common.util.MD5;
import com.foodoon.info.common.util.enums.UserStatusEnum;
import com.foodoon.info.web.home.user.form.RegForm;

/**
 * 类RegController.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月14日 上午9:46:44
 */
@Controller
public class RegController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserMapper  userMapper;

    @RequestMapping(value = "/user/reg.htm", method = RequestMethod.GET)
    public String doGet(RegForm regForm, BindingResult bindResult, HttpServletRequest request, ModelMap modelMap) {

        return "user/reg.vm";
    }

    @Form
    @RequestMapping(value = "/user/reg.htm", method = RequestMethod.POST)
    public String doPost(@Valid RegForm regForm, BindingResult bindResult, HttpServletRequest request, ModelMap modelMap) {
        if (bindResult.hasErrors()) {
            return "user/reg.vm";
        }
        if (!regForm.getPassword().equals(regForm.getPassword2())) {
            bindResult.rejectValue("password2", "diffen-pass", "两次输入的密码不一致");
            return "user/reg.vm";
        }
        UserExample uexa = new UserExample();
        uexa.createCriteria().andUserIdEqualTo(regForm.getUserName());
        List<User> users = userMapper.selectByExample(uexa);
        if (users.size() == 0) {
            uexa.clear();
            uexa.createCriteria().andEmailEqualTo(regForm.getEmail());
            List<User> users2 = userMapper.selectByExample(uexa);
            if (users2.size() == 0) {
                User user = new User();
                String id = UUID.randomUUID().toString();
                user.setId(id);
                user.setUserId(regForm.getUserName());
                user.setPassword(MD5.md5(regForm.getPassword()));

                user.setEmail(regForm.getEmail());
                user.setStatus(UserStatusEnum.EMAIL_NOT_CHECK.getValue());
                user.setCode(UUID.randomUUID().toString());
                user.setGmtCreated(new Date());

                int res = userMapper.insert(user);
                if (res == 1) {
                    sendMail(request, user.getUserId(), user.getCode(), user.getEmail());
                } else {
                    modelMap.addAttribute("errorTips", "抱歉，服务错误，请稍微重试。");
                    return "user/regFail.vm";
                }
                return "user/regTips.vm";
            } else {

                bindResult.rejectValue("email", "mail-reg-repeat", "邮箱已经被注册");
                return "user/reg.vm";
            }
        } else {
            bindResult.rejectValue("userName", "user-reg-repeat", "用户名已经被注册");
            return "user/reg.vm";
        }

    }

    private void sendMail(HttpServletRequest request, String userName, String code, String mailTo) {
        String root = request.getServletContext().getRealPath("/");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        String url = getFullContextPath(request) + "/user/reg/valid.htm?code=" + code + "&u=" + userName;
        params.put("validUrl", url);
        try {
            String content = VelocityHelper.renderVm(root, File.separator + "home" + File.separator + "mailTemplate"
                                                           + File.separator + "mailValid.vm", params);
            mailService.sendMail("农贸网用户激活", content, mailTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFullContextPath(HttpServletRequest request) {
        // if (defaultBox == null) {
        String url = request.getRequestURL().toString();
        String path = request.getServletPath();
        return url.substring(0, url.indexOf(path));

    }

}
