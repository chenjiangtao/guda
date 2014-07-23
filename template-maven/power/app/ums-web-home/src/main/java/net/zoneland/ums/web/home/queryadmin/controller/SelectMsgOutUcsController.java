/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.queryadmin.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.queryadmin.SelectMsgOutUcsBiz;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
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
 * @author yangjuanying
 * @version $Id: SelectMsgOutUcsController.java, v 0.1 2012-12-11 下午7:33:09 yangjuanying Exp $
 */
@Controller
public class SelectMsgOutUcsController {

    private static final Logger logger = Logger.getLogger(SelectMsgOutUcsController.class);

    @Autowired
    private SelectMsgOutUcsBiz  selectMsgOutUcsBiz;

    @Autowired
    private MsgSearchService    msgSearchService;

    /**
     * 初始化时的【短信查询】页面(刚进入短信查询页面时，默认显示什么都不查)
     * 
     * @param form
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAdmin/selectMsgOutUcsInit.htm")
    public String doGet(AppMsgInfoForm form, ModelMap modelMap) throws Exception {
        Date date = new Date();
        String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
        form.setStartTime(startTime);
        String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
        form.setEndTime(endTime);
        modelMap.addAttribute("init", 0);//标记这是初始化页面要显示的提示
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());// 状态
        return "/queryAdmin/selectMsgOutUcs.vm";
    }

    /**
     * 拦截条件查询【短信查询数据短信】的get和post请求
     * 
     * @param form
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAdmin/selectMsgOutUcs.htm")
    public String doPost(AppMsgInfoForm form, HttpServletRequest request,
                         HttpServletResponse response, ModelMap modelMap) throws Exception {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO = getAppMsgInfoBO(form);// 将前端页面获取到的对象转换成为业务层提供操作的对象
        int pageId = StringHelper.parseInt(form.getPageId());
        PageResult<UmsMsgOut> result = new PageResult<UmsMsgOut>();
        try {
            result = selectMsgOutUcsBiz.selectMsgOutUcs(appMsgInfoBO, pageId);
        } catch (Exception e) {
            logger.error("查询【短信查询数据短信】出现异常：", e);
        }
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());// 状态
        return "/queryAdmin/selectMsgOutUcs.vm";
    }

    /**
     * 【短信查询】中数据短信页面的详细短信信息
     * 
     * @param form
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/queryAdmin/selectMsgOutUcsDetail.htm")
    public String selectMsgOutUcsDetail(AppMsgInfoForm form, ModelMap modelMap,
                                        HttpServletRequest request) {
        String id = request.getParameter("id");
        UmsMsgOutDO umsMsgOut = new UmsMsgOutDO();
        if (StringHelper.isNotEmpty(id)) {
            umsMsgOut = msgSearchService.getDataMsgInfoById(id);
        }
        modelMap.addAttribute("msgInfo", umsMsgOut);
        modelMap.addAttribute("msgId", id);
        modelMap.addAttribute("msg", form);
        return "queryAdmin/selectMsgOutUcsDetail.vm";
    }

    /**
     * 将前端页面获取到的对象转换成为业务层提供操作的对象
     * 
     * @param form
     * @return
     */
    private AppMsgInfoBO getAppMsgInfoBO(AppMsgInfoForm form) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        if (form != null) {
            if (form.getStartTime() != null && !"".equals(form.getStartTime())) {
                try {
                    String startTime = form.getStartTime() + " 00:00:00";
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
                form.setStartTime(startTime);
                startTime = startTime + " 00:00:00";
                try {
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            if (form.getEndTime() != null && !"".equals(form.getEndTime())) {
                try {
                    String endTime = form.getEndTime() + " 23:59:59";
                    Calendar cend = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setEndTime(new Timestamp(cend.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
                form.setEndTime(endTime);
                endTime = endTime + " 23:59:59";
                try {
                    Calendar cEnd = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setEndTime(new Timestamp(cEnd.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            appMsgInfoBO.setStatus("".equals(form.getStatus()) ? null : form.getStatus());// 获取条件：状态
            appMsgInfoBO.setAppName(StringUtils.isEmpty(form.getAppName()) ? null : form
                .getAppName());
            if ("".equals(form.getAppId())) {// 应用ID
                appMsgInfoBO.setAppId(null);
            } else {
                appMsgInfoBO.setAppId(form.getAppId());
            }
            appMsgInfoBO.setSendId(StringUtils.isEmpty(form.getMsgDest()) ? null : form
                .getMsgDest());// 发送方手机号
            appMsgInfoBO.setRecvId(StringUtils.isEmpty(form.getMsgSrc()) ? null : form.getMsgSrc());// 获取接收方手机号
        }
        return appMsgInfoBO;
    }
}
