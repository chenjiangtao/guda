/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.stat.controller;

import net.zoneland.ums.biz.msg.in.MsgInErrorService;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInError;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.stat.form.AppMsgInfoForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author  wangyong
 * @version $Id: MsgInErrorController.java, v 0.1 2012-9-6 上午9:41:55 wangyong Exp $
 */
@Controller
@RequestMapping("/error")
public class MsgInErrorController {

    private static final Logger logger = Logger.getLogger(MsgInErrorController.class);

    @Autowired
    private MsgInErrorService   msgInErrorService;

    /**
     * 初始化时的查询统计上行错误短信页面(刚进入查询统计上行错误短信页面时，默认显示什么都不查)
     * 
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/errorInit.htm")
    public String doGetInit(AppMsgInfoForm form, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("init", 0);//标记这是初始化页面要显示的提示
        return "/stat/msgError.vm";
    }

    @RequestMapping(value = "/list.htm")
    public String doGet(AppMsgInfoForm form, ModelMap modelMap) {
        AppMsgInfoBO msgInErrorBo = new AppMsgInfoBO();
        PageResult<UmsMsgInError> pageResult = new PageResult<UmsMsgInError>();
        msgInErrorBo = getAppMsgErrorBO(form);// 将前端页面获取到的对象转换成为业务层提供操作的对象
        int pageId = StringHelper.parseInt(form.getPageId());
        try {
            pageResult = msgInErrorService.findAll(pageId, msgInErrorBo);
        } catch (Exception e) {
            logger.error("查询上行错误消息出现异常：", e);
        }
        modelMap.addAttribute("result", pageResult);
        modelMap.addAttribute("msg", form);
        return "/stat/msgError.vm";
    }

    /**
     * 将前端页面获取到的对象转换成为业务层提供操作的对象.
     * 
     * @param form
     * @return
     */
    private AppMsgInfoBO getAppMsgErrorBO(AppMsgInfoForm form) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO.setAppName(StringUtils.isEmpty(form.getAppName()) ? null : form.getAppName());
        if ("".equals(form.getAppId())) {// 应用ID
            appMsgInfoBO.setAppId(null);
        } else {
            appMsgInfoBO.setAppId(form.getAppId());
        }
        appMsgInfoBO.setSendId(StringUtils.isEmpty(form.getMsgDest()) ? null : form.getMsgDest());// 获取发送方手机号
        appMsgInfoBO.setContent(StringUtils.isEmpty(form.getContent()) ? null : form.getContent());// 短信内容
        appMsgInfoBO.setRecvId(StringUtils.isEmpty(form.getMsgSrc()) ? null : form.getMsgSrc());// 获取接收方手机号
        return appMsgInfoBO;
    }

    @SuppressWarnings("unused")
    private int parseInt(String pageId) {
        if (!StringUtils.isBlank(pageId)) {
            try {
                return Integer.parseInt(pageId);
            } catch (Exception e) {
            }
        }
        return 1;
    }

}
