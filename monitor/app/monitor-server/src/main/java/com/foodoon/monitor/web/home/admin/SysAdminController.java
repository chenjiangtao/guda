/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.monitor.dal.AgentMapper;
import com.foodoon.monitor.dal.SysMapper;
import com.foodoon.monitor.web.home.BaseController;
import com.foodoon.monitor.web.home.vo.Response;

/**
 * 
 * @author foodoon
 * @version $Id: SysAdminController.java, v 0.1 2013-6-7 下午10:05:52 foodoon Exp $
 */
@Controller
public class SysAdminController extends BaseController {

    @Autowired
    private SysMapper   sysMapper;

    @Autowired
    private AgentMapper agentMapper;

    @RequestMapping(value = "delSys.json")
    public void doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        Date t = getDate(request.getParameter("t"));

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("time", t);

        int r = sysMapper.delByTime(params);
        Response res = new Response();
        if (r > 0) {
            res.setSuccess(true);
            res.setMsg("成功删除" + r + "条记录");
        } else {
            res.setMsg("没有删除任何记录");
        }
        super.ajaxReturn(res, response);

    }

    @RequestMapping(value = "delAgent.json")
    public void doDel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        String ip = (request.getParameter("ip"));

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("ip", ip);

        int rr = sysMapper.delByTime(params);
        Response res = new Response();
        int r = agentMapper.deleteByIp(ip);
        if (r > 0) {
            res.setSuccess(true);
            res.setMsg("成功删除" + r + "条指标及对应的" + rr + "条指标数据");
        } else {
            res.setMsg("没有删除任何记录");
        }
        super.ajaxReturn(res, response);

    }

    private Date getDate(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {

        }
        return null;
    }

}
