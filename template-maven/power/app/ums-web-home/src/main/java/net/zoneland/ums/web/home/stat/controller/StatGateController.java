/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.stat.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.zoneland.ums.biz.msg.stat.GateStatService;
import net.zoneland.ums.biz.msg.stat.bo.GateStatBo;
import net.zoneland.ums.biz.msg.stat.bo.UmsStatVo;
import net.zoneland.ums.common.util.enums.msg.MsgTypeEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.stat.form.StatGateForm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author gang
 * @version $Id: StatGateController.java, v 0.1 2013-3-26 下午3:39:55 gang Exp $
 */
@Controller
public class StatGateController {

    private static final Logger logger = Logger.getLogger(StatGateController.class);

    @Autowired
    private GateStatService     gateStatService;

    @RequestMapping(value = "/stat/statGate.htm")
    public String doGetInit(StatGateForm form, ModelMap modelMap) throws Exception {

        GateStatBo bo = convert2(form);
        try {
            PageResult<UmsStatVo> res = gateStatService.searchGateStat(bo);
            modelMap.addAttribute("result", res);
        } catch (Exception e) {
            logger.error("查询统计上行短信初始化页面出现异常：", e);
        }
        modelMap.addAttribute("form", form);
        modelMap.addAttribute("smsTypelist", MsgTypeEnum.values());// 状态
        return "stat/statGate.vm";
    }

    private GateStatBo convert2(StatGateForm form) {
        GateStatBo bo = new GateStatBo();
        if (StringUtils.hasText(form.getSmsType())) {
            bo.setType(form.getSmsType());
        }
        if (!StringUtils.hasText(form.getStartTime())) {
            Date date = new Date();
            String start = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
            form.setStartTime(start);
            String end = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
            form.setEndTime(end);
        }
        Date startTime = getDate(form.getStartTime());
        Date endTime = getDate(form.getEndTime());
        if (startTime != null && endTime != null) {
            if (startTime.after(endTime)) {
                Date t = startTime;
                startTime = endTime;
                endTime = t;
            }
            bo.setStartTime(startTime);
            bo.setEndTime(endTime);
        }

        bo.setPageId(getPageId(form.getPageId()));
        return bo;

    }

    private Date getDate(String date) {
        if (!StringUtils.hasText(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {

        }
        return null;
    }

    private int getPageId(String pageId) {
        if (pageId == null) {
            return 1;
        }
        try {
            return Integer.parseInt(pageId);
        } catch (Exception e) {

        }
        return 1;
    }

}
