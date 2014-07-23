/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.SmsTemplateMapper;
import com.tiaotiaogift.common.dal.page.Page;
import com.tiaotiaogift.common.mysql.dataobject.SmsTemplate;

/**
 * 
 * @author gang
 * @version $Id: SmsTemplateController.java, v 0.1 2013-4-26 下午2:34:19 gang Exp $
 */
@Controller
public class SmsTemplateController {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @RequestMapping(value = "/ums/smsTemplate.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, HttpServletRequest request, ModelMap modelMap) {

        return "ums/smstemplate/smsTemplate.vm";
    }

    @RequestMapping(value = "/ums/addSmsTemplate.htm", method = RequestMethod.GET)
    public String doAdd(SmsTemplateForm smsTemplateForm, BindingResult bindingResult,
                        HttpServletRequest request, ModelMap modelMap) {

        return "ums/smstemplate/editSmsTemplate.vm";
    }

    @RequestMapping(value = "/ums/selectSmsTemplate.htm", method = RequestMethod.GET)
    public String doSelect(Integer pageId, HttpServletRequest request, ModelMap modelMap) {
        if (pageId == null || pageId < 1) {
            pageId = 1;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("userId", OperationContextHolder.getPrincipal().getUserId());
        params.put("start", (pageId - 1) * 20);
        List<SmsTemplate> list = new ArrayList<SmsTemplate>();
        int count = 0;
        list = smsTemplateMapper.selectByUserId(params);
        count = smsTemplateMapper.selectCountByUserId(params);
        Page p = new Page(pageId.intValue(), count, list);
        modelMap.put("res", p);
        return "ums/smstemplate/selectSmsTemplate.vm";
    }

}
