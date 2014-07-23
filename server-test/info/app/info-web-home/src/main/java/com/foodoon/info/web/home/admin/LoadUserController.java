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

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.web.home.BaseAjaxController;
import com.foodoon.info.web.home.vo.Response;
import com.foodoon.info.web.home.vo.UiVo;

/**
 * 类LoadDataController.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月18日 上午10:05:02
 */
@Controller
public class LoadUserController extends BaseAjaxController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/admin/loadUser.json", method = RequestMethod.POST)
    public void doGet(Integer page, Integer rows, HttpServletResponse response, ModelMap modelMap) {
        if (page == null || page < 1) {
            page = 1;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", (page - 1) * rows);
        params.put("rows", rows);

        List<User> users = userMapper.searchByPage(params);
        int c = userMapper.searchCountByPage(params);

        UiVo v = new UiVo();
        v.setRows(users);
        v.setTotal(c);
        super.ajaxReturn(v, response);

    }

    @RequestMapping(value = "/admin/delUser.json", method = RequestMethod.POST)
    public void doDel(String id, HttpServletResponse response, ModelMap modelMap) {

        int result = 0;
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; ++i) {
            result += userMapper.deleteByPrimaryKey(ids[i]);
        }

        Response res = new Response();
        if (result > 0) {
            res.setMsg("删除" + result + "条记录成功");
            res.setSuccess(true);
        } else {
            res.setMsg("删除失败");
        }
        super.ajaxReturn(res, response);

    }

}
