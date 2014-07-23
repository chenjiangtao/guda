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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.monitor.dal.WarnMapper;
import com.foodoon.monitor.dal.dataobject.Warn;
import com.foodoon.monitor.web.home.form.WarnForm;
import com.foodoon.monitor.web.home.vo.Response;

/**
 * 
 * @author gang
 * @version $Id: SaveWarnController.java, v 0.1 2013-4-19 下午9:51:05 gang Exp $
 */
@Controller
public class SaveWarnController extends BaseController {

    @Autowired
    private WarnMapper warnMapper;

    @RequestMapping(value = "saveWarn.json")
    public void doGet(@Valid WarnForm warnForm, BindingResult result, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {
        Response res = new Response();
        if (result.hasErrors()) {
            String msg = result.getAllErrors().get(0).getDefaultMessage();
            res.setMsg(msg);
            super.ajaxReturn(res, response);
            return;
        }
        if (warnForm.getEmail() == null && warnForm.getPhone() == null) {
            res.setMsg("请至少选择一个告警通知方式");
            super.ajaxReturn(res, response);
            return;
        }
        if (warnForm.getEmail() != null) {
            if (warnForm.getEmailVal() == null || warnForm.getEmailVal().indexOf("%40") > -1) {
                res.setMsg("选择邮件告警的情况下，请您输入接收告警的邮件");
                super.ajaxReturn(res, response);
                return;
            } else {
                warnForm.setEmailVal(warnForm.getEmailVal().replaceAll("%40", "@"));
            }
        }
        if (warnForm.getPhone() != null && warnForm.getPhoneVal() == null) {
            res.setMsg("选择短信告警的情况下，请您输入接收告警的手机号");
            super.ajaxReturn(res, response);
            return;
        }

        Warn warn = new Warn();
        warn.setCondition(warnForm.getCond());
        warn.setEmail(warnForm.getEmailVal());
        warn.setPhone(warnForm.getPhoneVal());
        if (warnForm.getEmail() != null) {
            warn.setWarn("email");
        }
        if (warnForm.getPhone() != null) {
            warn.setWarn("phone");
        }
        if (warnForm.getEmail() != null && warnForm.getPhone() != null) {
            warn.setWarn("email,phone");
        }
        warn.setIp(warnForm.getIp());
        warn.setK(warnForm.getK());
        warn.setVal(Float.valueOf(warnForm.getVal()));
        int rows = 0;
        if (StringUtils.hasText(warnForm.getId())) {
            warn.setId(warnForm.getId());
            if ("email".equals(warn.getWarn())) {
                warn.setPhone(null);
            } else if ("phone".equals(warn.getWarn())) {
                warn.setEmail(null);
            }
            rows = warnMapper.updateByPrimaryKey(warn);
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ip", warn.getIp());
            params.put("k", warn.getK());
            Warn ww = warnMapper.selectByIpAndKey(params);
            if (ww == null) {
                warn.setId(UUID.randomUUID().toString());
                warn.setGmtCreated(new Date());
                rows = warnMapper.insert(warn);
            } else {
                res.setMsg("已经存在主机[" + warn.getIp() + "]对指标[" + warn.getK() + "]的监控条件,您可以修改原有的告警条件");
                super.ajaxReturn(res, response);
                return;
            }
        }
        if (rows == 1) {
            res.setSuccess(true);
            res.setMsg("保存成功");
        } else {
            res.setMsg("保存失败");
        }
        super.ajaxReturn(res, response);

    }

    @RequestMapping(value = "delWarn.json")
    public void doDel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Response res = new Response();
        String id = request.getParameter("id");
        int row = warnMapper.deleteByPrimaryKey(id);
        if (row == 1) {
            res.setSuccess(true);
            res.setMsg("删除成功");
        } else {
            res.setMsg("删除错误");
        }
        super.ajaxReturn(res, response);

    }

}
