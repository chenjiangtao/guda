/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.biz.common.mail.MailService;
import com.tiaotiaogift.biz.common.mail.template.VelocityHelper;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.MD5;
import com.tiaotiaogift.common.util.enums.UserStatusEnum;
import com.tiaotiaogift.web.home.filter.AccessFilter;
import com.tiaotiaogift.web.home.filter.RoleInfo;
import com.tiaotiaogift.web.home.ums.form.RegForm;
import com.tiaotiaogift.web.mvc.Form;

/**
 * 
 * @author gag
 * @version $Id: RegController.java, v 0.1 2012-12-14 下午12:08:06 gag Exp $
 */
@Controller
public class RegController {

    private Logger        logger = LoggerFactory.getLogger(RegController.class);

    @Autowired
    private UserMapper    userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MailService   mailService;

    @RequestMapping(value = "/reg.htm", method = RequestMethod.GET)
    public String doGet(RegForm regForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {

        return "ums/reg.vm";
    }

    @RequestMapping(value = "/reg.htm", method = RequestMethod.POST)
    @Form
    public String doReg(@Valid RegForm regForm, BindingResult result, HttpServletRequest request,
                        HttpServletResponse response, ModelMap modelMap) {

        if (result.hasErrors()) {
            return "ums/reg.vm";
        }
        if (!regForm.getPassword().equals(regForm.getSecPassword())) {
            result.rejectValue("secPassword", "diffen-pass", "两次输入的密码不一致");
            return "ums/reg.vm";
        }
        User u = userMapper.selectByUserId(regForm.getUserName());
        User u2 = userMapper.selectByEmail(regForm.getEmail());
        modelMap.addAttribute("regForm", regForm);
        if (u == null && u2 == null) {
            User user = new User();
            String id = UUID.randomUUID().toString();
            user.setId(id);
            user.setUserId(regForm.getUserName());
            user.setPassword(MD5.md5(regForm.getPassword()));
            user.setPhone(regForm.getPhone());
            user.setEmail(regForm.getEmail());
            user.setStatus(UserStatusEnum.EMAIL_NOT_CHECK.getValue());
            user.setCode(UUID.randomUUID().toString());
            user.setGmtCreated(new Date());
            user.setGrade(0);
            // login(user, request, response);
            sendMail(request, user.getUserId(), user.getCode(), user.getEmail());
            Integer linkId = userMapper.selectMaxLinkId();
            if (linkId == null) {
                user.setLinkId(1);
            } else {
                user.setLinkId(linkId + 1);
            }
            userMapper.insert(user);

            Account a = new Account();
            a.setBalance(3);
            a.setBalanceLock(0);
            a.setUserId(id);
            a.setId(UUID.randomUUID().toString());
            a.setGmtModify(new Date());
            accountMapper.insert(a);
            return "ums/regTips.vm";
        } else {

            String errorTips = "未知原因";
            if (u != null) {
                errorTips = String.format("用户名%1$s已经被注册.", regForm.getUserName());
            }
            if (u2 != null) {
                errorTips = String.format("邮箱%1$s已经被注册.", regForm.getEmail());
            }
            modelMap.addAttribute("errorTips", errorTips);
            return "ums/regFail.vm";
        }

    }

    private void sendMail(HttpServletRequest request, String userName, String code, String mailTo) {
        String root = request.getServletContext().getRealPath("/");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        String url = FindPasswordController.getFullContextPath(request) + "/reg/valid.htm?code="
                     + code + "&u=" + userName;
        params.put("validUrl", url);
        try {
            String content = VelocityHelper.renderVm(root, File.separator + "home" + File.separator
                                                           + "mailTemplate" + File.separator
                                                           + "mailValid.vm", params);
            mailService.sendMail("UMS365用户激活", content, mailTo);
            mailService.sendMail("UMS365用户注册提醒", content, "52313882@qq.com");
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    protected void login(User userInfo, HttpServletRequest request, HttpServletResponse response) {
        if (userInfo != null) {
            // List<RoleInfo> roles =
            // umsUserInfoBiz.getRoleByUserId(userInfo.getId());
            OperationPrincipal p = new OperationPrincipal();
            p.setLogonId(userInfo.getUserId());
            p.setUserId(userInfo.getId());
            p.setUserName(userInfo.getUserId());
            p.setUserPhone(userInfo.getPhone());
            p.setEmail(userInfo.getEmail());
            p.setIp(request.getRemoteAddr());
            RoleInfo role = new RoleInfo();
            role.setRoleName("ROLE_LOGIN");
            OperationContext context = new OperationContext(p, new RoleInfo[] { role });
            OperationContextHolder.setOperationContext(context);

            request.getSession().setAttribute(AccessFilter.OPERATIONATTR, context);
        }
    }

}
