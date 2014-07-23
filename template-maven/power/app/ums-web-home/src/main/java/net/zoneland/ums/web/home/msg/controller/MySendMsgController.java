/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.msg.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.common.dal.bo.UmsMsgOutBo;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.msg.form.MsgSendInfoForm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author gag
 * @version $Id: SendMsgController.java, v 0.1 2012-9-22 上午11:07:27 gag Exp $
 */
@Controller
@RequestMapping("/msg")
public class MySendMsgController {

    private final static Logger logger    = Logger.getLogger(MySendMsgController.class);

    @Autowired
    private MsgSearchService    msgSearchService;

    static Map<String, String>  statusMap = new LinkedHashMap<String, String>();

    static {
        statusMap.put(MsgInfoStatusEnum.init.getValue(), MsgInfoStatusEnum.init.getDescription());
        statusMap.put(MsgInfoStatusEnum.ready.getValue(), MsgInfoStatusEnum.ready.getDescription());
        statusMap.put(MsgInfoStatusEnum.success.getValue(),
            MsgInfoStatusEnum.success.getDescription());
        statusMap.put(MsgInfoStatusEnum.failure.getValue(),
            MsgInfoStatusEnum.failure.getDescription());
        statusMap.put(MsgInfoStatusEnum.wait.getValue(), MsgInfoStatusEnum.wait.getDescription());
        statusMap.put(MsgInfoStatusEnum.expire.getValue(),
            MsgInfoStatusEnum.expire.getDescription());
        statusMap.put(MsgInfoStatusEnum.error.getValue(), MsgInfoStatusEnum.error.getDescription());
        statusMap.put(MsgInfoStatusEnum.refuse.getValue(),
            MsgInfoStatusEnum.refuse.getDescription());
        statusMap.put(MsgInfoStatusEnum.unkown_error.getValue(),
            MsgInfoStatusEnum.unkown_error.getDescription());
    }

    /**
     * 我发送的消息
     *
     * @param form
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/mysendmsg.htm")
    public String mySendmsgInfo(MsgSendInfoForm form, HttpServletRequest request, ModelMap modelMap)
                                                                                                    throws Exception {
        UmsMsgOutBo bo = new UmsMsgOutBo();
        bo = getBo(form);
        String curPage = request.getParameter("pageId");// 获得当前页数       
        int pageId = StringHelper.parseInt(curPage);
        String uid = SecurityContextHolder.getContext().getPrincipal().getUserId();
        if (uid == null || "".equals(uid)) {
            logger.info("获取不到当前用户");
            PageResult<UmsMsgOutDO> result = new PageResult<UmsMsgOutDO>();
            modelMap.addAttribute("result", result);
            modelMap.addAttribute("msg", form);
            return "msg/mySendmsg.vm";
        }
        String recvId = request.getParameter("recvName");
        bo.setUserId(uid);
        bo.setRecvId(recvId);
        bo.setRecvName(form.getRecipient());// 接收方姓名输入框里输入的数据
        bo.setOrderBy("GMT_MODIFIED");
        PageResult<UmsMsgOutDO> result = msgSearchService.searchSendMsgInfoByPage(bo, pageId);
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("statusMap", statusMap);
        return "msg/mySendmsg.vm";
    }

    @RequestMapping("/sendInfoDetail.htm")
    public String sendInfoDetail(MsgSendInfoForm form, ModelMap modelMap, HttpServletRequest request) {

        String id = request.getParameter("id");
        UmsMsgOutDO umsMsgOut = new UmsMsgOutDO();
        if (StringHelper.isNotEmpty(id)) {
            umsMsgOut = msgSearchService.getSendMsgInfoById(id);
        }
        modelMap.addAttribute("msgInfo", umsMsgOut);
        modelMap.addAttribute("msgId", id);
        modelMap.addAttribute("msg", form);
        return "msg/sendInfoDetail.vm";
    }

    private UmsMsgOutBo getBo(MsgSendInfoForm form) {
        UmsMsgOutBo bo = new UmsMsgOutBo();
        if (form != null) {
            if (form.getStartTime() != null && !"".equals(form.getStartTime())) {
                try {
                    String startTime = form.getStartTime() + " 00:00:00";
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    bo.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String startTime = DateHelper.getPreThirtyDayStrDateByFormat(date, "yyyy-MM-dd");
                form.setStartTime(startTime);
                startTime = startTime + " 00:00:00";
                try {
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    bo.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            if (form.getEndTime() != null && !"".equals(form.getEndTime())) {
                try {
                    String endTime = form.getEndTime() + " 23:59:59";
                    Calendar cend = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    bo.setEndTime(new Timestamp(cend.getTimeInMillis()));
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
                    bo.setEndTime(new Timestamp(cEnd.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            if (form.getRecvId() != null && !"".equals(form.getRecvId())) {
                bo.setRecvId(form.getRecvId());
            } else {
                bo.setRecvId(null);
            }
            if (form.getStatus() != null && !"".equals(form.getStatus())) {
                bo.setStatus(form.getStatus());
            } else {
                bo.setStatus(null);
            }
            if (form.getSendId() != null && !"".equals(form.getSendId())) {
                bo.setSendId(form.getSendId());
            } else {
                bo.setSendId(null);
            }
            if (form.getUserId() != null && !"".equals(form.getUserId())) {
                bo.setUserId(form.getUserId());
            } else {
                bo.setUserId(null);
            }
            if (form.getAppId() != null && !"".equals(form.getAppId())) {
                bo.setAppId(form.getAppId());
            } else {
                bo.setAppId(null);
            }
        }
        return bo;
    }
}
