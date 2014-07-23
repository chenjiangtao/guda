/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.ums.biz.config.admin.UmsActionLogBiz;
import net.zoneland.ums.common.dal.bo.UmsActionLogBo;
import net.zoneland.ums.common.dal.dataobject.UmsActionLog;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.OperationLogForm;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日志查询 控制类
 * @author louguodong
 * @version $Id: SmsActionLogController.java, v 0.1 2012-5-10 下午3:40:29 louguodong Exp $
 */
@Controller
@RequestMapping("log")
public class ActionLogController extends BaseController {

    private final static Logger logger = Logger.getLogger(ActionLogController.class);

    @Autowired
    private UmsActionLogBiz     umsActionLogBiz;

    @RequestMapping("log.htm")
    public String logView(OperationLogForm logForm, Integer pageId, HttpServletRequest request,
                          ModelMap modelMap) {
        UmsActionLogBo bo = new UmsActionLogBo();
        if (pageId == null || pageId < 1) {
            pageId = 1;
        }
        bo.setOrderBy("GMT_CREATED");
        if (logForm != null) {
            if (logForm.getStartTime() != null && !"".equals(logForm.getStartTime())) {
                try {
                    Calendar cstart = DateHelper.gc(logForm.getStartTime() + " 00:00:00",
                        "yyyy-MM-dd HH:mm:ss");
                    bo.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化出错：" + e);
                }
            } else {
                Date date = new Date();
                String startTime = DateHelper.getPreTenDayStrDateByFormat(date, "yyyy-MM-dd");
                logForm.setStartTime(startTime);
                startTime = startTime + " 00:00:00";
                try {
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    bo.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化出错", e);
                }
            }
            if (logForm.getEndTime() != null && !"".equals(logForm.getEndTime())) {
                try {
                    Calendar cend = DateHelper.gc(logForm.getEndTime() + " 23:59:59",
                        "yyyy-MM-dd HH:mm:ss");
                    bo.setEndTime(new Timestamp(cend.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化出错：" + e);
                }
            } else {
                Date date = new Date();
                String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
                logForm.setEndTime(endTime);
                endTime = endTime + " 23:59:59";
                try {
                    Calendar cEnd = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    bo.setEndTime(new Timestamp(cEnd.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化出错", e);
                }
            }
            bo.setOperatorName(!StringUtils.hasText(logForm.getOperatorName()) ? null : logForm
                .getOperatorName());//操作员
            bo.setOperatorMenu(!StringUtils.hasText(logForm.getOperatorMenu()) ? null : logForm
                .getOperatorMenu());//操作模块
            bo.setOperatorType(!StringUtils.hasText(logForm.getOperatorType()) ? null : logForm
                .getOperatorType());//操作类型
        }
        try {
            PageResult<UmsActionLog> result = this.umsActionLogBiz.searchByPage(bo, pageId);
            modelMap.addAttribute("result", result);
            modelMap.addAttribute("log", logForm);
        } catch (Exception e) {
            logger.error("查询日志操作出错：" + e);
        }
        return "admin/log/log.vm";
    }

}
