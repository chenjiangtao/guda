/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.template;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.common.dal.SmsTemplateMapper;
import com.tiaotiaogift.common.mysql.dataobject.SmsTemplate;
import com.tiaotiaogift.common.util.enums.SmsTemplateEnum;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.vo.Response;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

/**
 * 
 * @author gang
 * @version $Id: SmsTemplateJsonController.java, v 0.1 2013-4-27 上午9:52:00 gang Exp $
 */
@Controller
public class SmsTemplateJsonController extends BaseJsonController {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @RequestMapping(value = "/ums/loadSmsTemplate.json")
    public void doGet(Integer page, Integer rows, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {
        if (page == null || page < 1) {
            page = 1;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("userId", OperationContextHolder.getPrincipal().getUserId());
        params.put("start", (page - 1) * rows);
        params.put("limit", rows);
        List<SmsTemplate> list = new ArrayList<SmsTemplate>();
        int count = 0;
        list = smsTemplateMapper.selectByUserId(params);
        count = smsTemplateMapper.selectCountByUserId(params);

        UiVo v = new UiVo();
        v.setRows(list);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }

    //ums/saveSmsTemplate.json

    @RequestMapping(value = "/ums/saveSmsTemplate.json")
    public void doSave(SmsTemplateForm smsTemplateForm, HttpServletRequest request,
                       HttpServletResponse response, ModelMap modelMap) {
        SmsTemplate temp = new SmsTemplate();
        temp.setContent(smsTemplateForm.getContent());

        temp.setGmtModify(new Date());
        temp.setType(SmsTemplateEnum.normal.getDescription());
        int row = 0;
        if (StringUtils.hasText(smsTemplateForm.getId())) {
            temp.setId(smsTemplateForm.getId());
            row = smsTemplateMapper.updateByPrimaryKeySelective(temp);
        } else {
            temp.setId(UUID.randomUUID().toString());
            temp.setUserId(OperationContextHolder.getPrincipal().getUserId());
            temp.setGmtCreated(new Date());
            row = smsTemplateMapper.insert(temp);
        }

        Response res = new Response();
        if (row == 1) {
            res.setMsg("保存成功");
            res.setSuccess(true);
        } else {
            res.setMsg("保存失败");
        }
        super.ajaxReturn(res, response);
    }

    @RequestMapping(value = "/ums/delSmsTemplate.json")
    public void doDel(SmsTemplateForm smsTemplateForm, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {
        int row = 0;
        if (StringUtils.hasText(smsTemplateForm.getId())) {
            String id = smsTemplateForm.getId();
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; ++i) {
                row += smsTemplateMapper.deleteByPrimaryKey(ids[i]);
            }

        }

        Response res = new Response();
        if (row > 0) {
            res.setMsg("删除" + row + "条记录成功");
            res.setSuccess(true);
        } else {
            res.setMsg("删除失败");
        }
        super.ajaxReturn(res, response);
    }
}
