/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.monitor.dal.AgentMapper;
import com.foodoon.monitor.dal.SysMapper;
import com.foodoon.monitor.dal.WarnMapper;
import com.foodoon.monitor.dal.dataobject.Agent;
import com.foodoon.monitor.dal.dataobject.Sys;
import com.foodoon.monitor.dal.dataobject.Warn;
import com.foodoon.monitor.web.home.form.GarForm;
import com.foodoon.monitor.web.home.vo.Response;
import com.foodoon.monitor.web.home.warn.WarnService;

/**
 * @author foodoon
 * @version $Id: ApiController.java, v 0.1 2013-5-30 下午4:24:38 foodoon Exp $
 */
@Controller
public class ApiController extends BaseController {

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private SysMapper   sysMapper;

    @Autowired
    private WarnMapper  warnMapper;

    @Autowired
    private WarnService emailWarnService;

    @Autowired
    private WarnService smsWarnService;

    @RequestMapping(value = "/api/gar.json")
    public void doGet(GarForm form, HttpServletRequest request, HttpServletResponse response,
                      ModelMap modelMap) {
        check(form);

        boolean isWarn = warn(form);
        if (isWarn) {
            insert(form, true);
        } else {
            insert(form, false);
        }
        Response r = new Response();
        r.setSuccess(true);
        super.ajaxReturn(r, response);

    }

    public boolean warn(GarForm form) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (form.getIp() == null || form.getK() == null) {
            throw new RuntimeException("arg 不能为空");
        }
        params.put("ip", form.getIp());
        params.put("k", form.getK());
        Warn ww = warnMapper.selectByIpAndKey(params);
        if (ww != null) {
            String con = ww.getCondition();
            Float val = ww.getVal();
            if (">".equals(con) && form.getVal() > val) {
                emailWarnService.warn(form, ww);
                smsWarnService.warn(form, ww);
                return true;
            } else if ("<".equals(con) && form.getVal() < val) {
                emailWarnService.warn(form, ww);
                smsWarnService.warn(form, ww);
                return true;
            } else if ("=".equals(con) && form.getVal() == val) {
                emailWarnService.warn(form, ww);
                smsWarnService.warn(form, ww);
                return true;
            }
        }
        return false;

    }

    public void check(GarForm form) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key", form.getK());
        params.put("ip", form.getIp());
        Agent a = agentMapper.selectByIpAndKey(params);
        Agent agent = new Agent();
        agent.setId(UUID.randomUUID().toString());
        agent.setComment(form.getComment());
        agent.setGmtCreated(new Date());
        agent.setHost(form.getHost());
        agent.setIp(form.getIp());
        agent.setK(form.getK());
        agent.setValueType(form.getValueType());
        agent.setOrderNum(form.getOrder());
        if (a == null) {

            agentMapper.insert(agent);
        } else {
            if (!a.equals(agent)) {
                a.setComment(form.getComment());
                a.setGmtCreated(new Date());
                a.setHost(form.getHost());
                a.setIp(form.getIp());
                a.setK(form.getK());
                a.setValueType(form.getValueType());
                a.setOrderNum(form.getOrder());
                agentMapper.updateByPrimaryKeySelective(a);
            }
        }
    }

    public void insert(GarForm form, boolean isWarn) {
        Sys sys = new Sys();
        sys.setId(UUID.randomUUID().toString());
        sys.setIp(form.getIp());
        sys.setK(form.getK());
        sys.setVal(form.getVal());
        sys.setValText(filterNull(form.getValText()));
        sys.setValueType(form.getValueType());
        sys.setGmtCreated(new Date());
        if (isWarn) {
            sys.setIsWarn(1);
        } else {
            sys.setIsWarn(0);
        }
        sysMapper.insert(sys);
    }

    private String filterNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

}
