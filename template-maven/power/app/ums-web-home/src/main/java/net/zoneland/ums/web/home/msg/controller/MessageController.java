/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.msg.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.biz.msg.draft.MsgDraftService;
import net.zoneland.ums.biz.msg.process.MessageProcess;
import net.zoneland.ums.common.dal.dataobject.UmsMsgDraft;
import net.zoneland.ums.common.util.enums.msg.IdentityEnum;
import net.zoneland.ums.web.home.msg.form.MessageForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author wangyong
 * @version $Id: MessageController.java, v 0.1 2012-8-9 下午1:32:52 wangyong Exp $
 */

/**
 * 1.发送消息控制类，主要是发送消息
 *
 */
@Controller
public class MessageController {

    private final static Logger logger      = Logger.getLogger(MessageController.class);

    static Map<String, String>  ackMap      = new LinkedHashMap<String, String>();

    static Map<String, String>  identifyMap = new LinkedHashMap<String, String>();

    //    static {
    //        ackMap.put(AckEnum.no_response.getValue(), AckEnum.no_response.getDescription());
    //        ackMap.put(AckEnum.report.getValue(), AckEnum.report.getDescription());
    //        ackMap.put(AckEnum.reply.getValue(), AckEnum.reply.getDescription());
    //        ackMap.put(AckEnum.reportAndReply.getValue(), AckEnum.reportAndReply.getDescription());
    //    }
    static {
        identifyMap.put(IdentityEnum.person.getValue(), IdentityEnum.person.getDescription());
        identifyMap.put(IdentityEnum.org.getValue(), IdentityEnum.org.getDescription());
    }

    @Autowired
    private MessageProcess      messageProcess;

    @Autowired
    private MsgDraftService     msgDraftService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    /**
     * 当/msg/msg.htm是get请求的时候则调用当前方法
     *
     */
    @RequestMapping(value = "/msg/msg.htm", method = RequestMethod.GET)
    public String doGet(MessageForm messageForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        String draftId = request.getParameter("dId");
        if (draftId != null) {
            UmsMsgDraft draft = msgDraftService.findDraft(draftId);
            if (draft != null) {
                String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
                if (StringUtils.equals(userId, draft.getUserId())) {
                    String recvId = draft.getRecvId();
                    messageForm.setRecvId(recvId);
                    messageForm.setMsgDestName(msgDraftService.getRecvName(recvId));
                    messageForm.setAck(draft.getAck());
                    messageForm.setContent(draft.getContent());
                    messageForm.setSendTime(draft.getSendTime());
                    messageForm.setValidTime(draft.getValidTime());
                    messageForm.setIdentity(draft.getIdentity());
                    messageForm.setDraftId(draftId);
                    modelMap.addAttribute("messageForm", messageForm);
                }
            }
        }
        //        modelMap.addAttribute("ackMap", ackMap);
        modelMap.addAttribute("identifyMap", identifyMap);
        return "/msg/msg.vm";
    }

    /**
     *当/msg/msg.htm是post请求的时候则调用当前方法
     *
     * @param messageForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/msg/msg.htm", method = RequestMethod.POST)
    public String doPost(@Valid MessageForm messageForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("ackMap", ackMap);
        modelMap.addAttribute("identifyMap", identifyMap);
        if (result.hasErrors()) {
            return "/msg/msg.vm";
        }
        String validTime = messageForm.getValidTime();
        if (validTime == null || "".equalsIgnoreCase(StringUtils.trim(validTime))) {
            messageForm.setValidTime(null);
        } else {
            if (!StringUtils.trim(validTime).matches("^[0-9]+$")) {
                modelMap.addAttribute("msgSendError", "有效时间请输入数字！");
                return "/msg/msg.vm";
            }
        }
        String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
        PrimitiveMessage primitiveMessage = messageForm.clonePrimitiveMessage();
        primitiveMessage.setUserId(userId);
        if (logger.isInfoEnabled()) {
            logger.info("页面上接收的消息:" + primitiveMessage);
        }
        logger.info("开始处理消息：");
        try {
            boolean res = messageProcess.process(primitiveMessage);
            logger.info("处理消息：" + res);
            if (res) {
                modelMap.addAttribute("msgSendError", primitiveMessage.getComments());
                return "/msg/msgSuccess.vm";
            }
            modelMap.addAttribute("msgSendErrorLength", primitiveMessage.getComments().length());
            modelMap.addAttribute("msgSendError", primitiveMessage.getComments());
            return "/msg/msg.vm";
        } catch (DataAccessException e) {
            modelMap.addAttribute("msgSendError", "处理消息失败，数据库错误。");
            logger.error("处理消息失败:", e);
        } catch (Exception e) {
            modelMap.addAttribute("msgSendError", "处理消息失败" + e.getMessage());
            logger.error("处理消息失败:", e);
        }
        return "/msg/msg.vm";

    }
}
