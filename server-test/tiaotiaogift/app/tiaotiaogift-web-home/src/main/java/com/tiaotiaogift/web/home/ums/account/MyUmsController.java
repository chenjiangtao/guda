/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.account;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.web.home.ums.form.LoginForm;

/**
 * 
 * @author gang
 * @version $Id: MyUmsController.java, v 0.1 2013-4-30 上午10:16:03 gang Exp $
 */
@Controller
public class MyUmsController {

    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping(value = "/account/myUms.htm", method = RequestMethod.GET)
    public String doGet(LoginForm loginForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap, HttpServletResponse response) throws IOException {

        Account account = accountMapper.selectByUserId(OperationContextHolder.getPrincipal()
            .getUserId());
        modelMap.addAttribute("account", account);
        return "ums/account/myUms.vm";
    }

}
