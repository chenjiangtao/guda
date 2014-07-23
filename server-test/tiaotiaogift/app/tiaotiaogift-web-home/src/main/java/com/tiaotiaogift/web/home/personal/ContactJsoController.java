/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.personal;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.common.dal.ContactMapper;
import com.tiaotiaogift.common.mysql.dataobject.Contact;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.personal.form.ContactForm;
import com.tiaotiaogift.web.home.ums.vo.Response;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

/**
 * 
 * @author gang
 * @version $Id: ContactJsoController.java, v 0.1 2013-4-27 下午2:08:21 gang Exp $
 */
@Controller
public class ContactJsoController extends BaseJsonController {

    @Autowired
    private ContactMapper contactMapper;

    @RequestMapping(value = "/personal/contact.json")
    public void doGet(ContactForm form, BindingResult result, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {
        if (form.getPage() < 1) {
            form.setPage(1);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", OperationContextHolder.getPrincipal().getUserId());
        params.put("contactName", filter2Null(form.getContactName()));
        params.put("orderStatus", filter2Null(form.getOrderStatus()));

        params.put("limit", form.getRows());
        params.put("start", (form.getPage() - 1) * form.getRows());
        List<Contact> contacts = contactMapper.searchByParams(params);
        int count = contactMapper.searchCountByParams(params);
        UiVo v = new UiVo();
        v.setRows(contacts);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }

    private String filter2Null(String str) {
        if (StringUtils.hasText(str)) {
            return str.trim();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/personal/saveContact.json")
    public void doSave(ContactForm form, BindingResult result, HttpServletRequest request,
                       HttpServletResponse response, ModelMap modelMap) {

        Contact temp = new Contact();
        temp.setName(form.getName());
        temp.setPhone(form.getPhone());
        int row = 0;
        if (StringUtils.hasText(form.getId())) {
            temp.setId(form.getId());
            row = contactMapper.updateByPrimaryKeySelective(temp);
        } else {
            temp.setId(UUID.randomUUID().toString());
            temp.setUserId(OperationContextHolder.getPrincipal().getUserId());
            temp.setGmtCreated(new Date());
            row = contactMapper.insert(temp);
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

    @RequestMapping(value = "/personal/delContact.json")
    public void doDel(ContactForm form, HttpServletRequest request, HttpServletResponse response,
                      ModelMap modelMap) {
        int row = 0;
        if (StringUtils.hasText(form.getId())) {
            String id = form.getId();
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; ++i) {
                row += contactMapper.deleteByPrimaryKey(ids[i]);
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
