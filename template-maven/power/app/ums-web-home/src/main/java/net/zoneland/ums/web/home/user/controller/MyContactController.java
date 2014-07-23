/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.ums.biz.user.UmsContactBiz;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.base.BaseController;
import net.zoneland.ums.web.home.user.form.MyContactForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 我的联系人控制层
 * 
 * @author yangjuanying
 * @version $Id: MyContactController.java, v 0.1 2013-2-1 下午5:54:15 yangjuanying Exp $
 */
@Controller
public class MyContactController extends BaseController {

    private final Logger  logger = Logger.getLogger(MyContactController.class);

    @Autowired
    private UmsContactBiz umsContactBiz;

    @RequestMapping(value = "/user/myContact.htm")
    public String doGet(MyContactForm myContactForm, HttpServletRequest request, ModelMap modelMap) {
        PageResult<UmsContact> result = getPageResult(myContactForm, modelMap);
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("myContactForm", myContactForm);
        return "/user/myContact/myContactList.vm";
    }

    private PageResult<UmsContact> getPageResult(MyContactForm myContactForm, ModelMap modelMap) {
        if (myContactForm == null) {
            myContactForm = new MyContactForm();
        }
        int pageId = StringHelper.parseInt(myContactForm.getPageId());
        UmsContact umsContact = getContact(myContactForm);
        PageResult<UmsContact> result = new PageResult<UmsContact>();
        try {
            result = umsContactBiz.searchMyContactByPage(umsContact, pageId);
        } catch (Exception e) {
            logger.info("查询我的联系人出现系统异常:", e);
            addMsg(modelMap, "系统异常！");
        }
        return result;
    }

    @RequestMapping("/user/addContact.ajax")
    public void addContact(HttpServletRequest request, HttpServletResponse response,
                           ModelMap modelMap) {
        String phone = StringUtils.trim(request.getParameter("phone"));
        String id = StringUtils.trim(request.getParameter("id"));
        UmsContact umsContact = new UmsContact();
        try {
            umsContact = umsContactBiz.searchByPhone(phone);
        } catch (Exception e) {
            logger.info("判断联系人是否存在出现系统异常:", e);
            addMsg(modelMap, "系统异常！");
        }
        if (StringUtils.isEmpty(id)) {
            if (umsContact != null) {
                ajaxReturn("exist", response);
            } else {
                ajaxReturn("notExist", response);
            }
        } else {
            if (!umsContact.getId().equalsIgnoreCase(id)) {
                ajaxReturn("exist", response);
            } else {
                ajaxReturn("notExist", response);
            }
        }
    }

    @RequestMapping(value = "/user/addOrUpdateContact.htm", method = RequestMethod.POST)
    public String doPost(@Valid MyContactForm myContactForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            addMsg(modelMap, "数据格式不符合！");
            PageResult<UmsContact> pageResult = new PageResult<UmsContact>();
            modelMap.addAttribute("result", pageResult);
            return "/user/myContact/myContactList.vm";
        }
        String userId = OperationContextHolder.getPrincipal().getUserId();
        UmsContact umsContact = myContactForm.cloneUmsContact();
        umsContact.setUserId(userId);
        boolean res = false;
        String id = myContactForm.getId();
        try {
            if (StringHelper.isEmpty(id)) {
                res = umsContactBiz.insertContact(umsContact);
                if (res) {
                    addMsg(modelMap, "添加成功");
                } else {
                    addMsg(modelMap, "添加失败");
                }
            } else {
                umsContact.setId(id);
                res = umsContactBiz.updateContact(umsContact);
                if (res) {
                    addMsg(modelMap, "修改成功");
                } else {
                    addMsg(modelMap, "修改失败");
                }
            }
        } catch (Exception e) {
            logger.info("添加或修改我的联系人出现系统异常:", e);
            addMsg(modelMap, "系统异常！");
        }
        doGet(myContactForm, request, modelMap);
        return "/user/myContact/myContactList.vm";
    }

    @RequestMapping(value = "/user/delContact.htm", method = RequestMethod.GET)
    public String deleteContact(MyContactForm myContactForm, ModelMap modelMap,
                                HttpServletRequest request) {
        String id = request.getParameter("id");
        int pageId = StringHelper.parseInt(myContactForm.getPageId());
        PageResult<UmsContact> pageResult = new PageResult<UmsContact>();
        UmsContact umsContact = getContact(myContactForm);
        if (StringUtils.isNotEmpty(id)) {
            try {
                boolean res = umsContactBiz.deleteContact(id);// 根据当前所选联系人ID在联系人中删除该记录
                if (res) {
                    addMsg(modelMap, "删除成功");
                } else {
                    addMsg(modelMap, "删除失败");
                }
                pageResult = umsContactBiz.searchMyContactByPage(umsContact, pageId);// 分页显示联系人列表
                modelMap.addAttribute("result", pageResult);
                modelMap.addAttribute("myContactForm", myContactForm);
            } catch (Exception e) {
                logger.error("删除联系人出错", e);
                addMsg(modelMap, "删除联系人出错");
            }
        }
        return "/user/myContact/myContactList.vm";
    }

    /**
     * 转换前端form和pojo属性
     * 
     * @param myContactForm
     * @return
     */
    private UmsContact getContact(MyContactForm myContactForm) {
        String userId = OperationContextHolder.getPrincipal().getUserId();
        UmsContact umsContact = new UmsContact();
        umsContact.setUserName(myContactForm.getSearchUserName());
        umsContact.setPhone(myContactForm.getSearchPhone());
        umsContact.setEmail(myContactForm.getSearchEmail());
        umsContact.setUserId(userId);
        umsContact.setOrderBy("GMT_CREATED");
        umsContact.setOrderbyType("DESC");
        return umsContact;
    }
}
