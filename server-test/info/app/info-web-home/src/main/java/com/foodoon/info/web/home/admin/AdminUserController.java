/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dal.page.Page;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.web.home.form.PageForm;

/**
 * 类AdminUserController.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月18日 上午9:17:31
 */
@Controller
public class AdminUserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/admin/user.htm", method = RequestMethod.GET)
    public String doGet(PageForm form, HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        form.calPage();
        params.put("start", (form.getPage() - 1) * form.getRows());
        params.put("rows", form.getRows());

        List<User> users = userMapper.searchByPage(params);
        int c = userMapper.searchCountByPage(params);
        modelMap.put("form", form);
        modelMap.put("users", new Page(form.getPage(), c, users));
        return "admin/user.vm";
    }

}
