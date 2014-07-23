/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.msg.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.ums.biz.msg.draft.MsgDraftService;
import net.zoneland.ums.common.dal.dataobject.UmsMsgDraft;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.msg.form.MessageForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author wangyong
 * @version $Id: MessageDraftController.java, v 0.1 2012-8-19 下午5:59:44 wangyong Exp $
 */

@Controller
public class MessageDraftController {

    private static final Logger logger = Logger.getLogger(MessageDraftController.class);

    @Autowired
    private MsgDraftService     msgDraftService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    /**
     *保存草稿
     *
     * @param messageForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/msg/msgSave.htm", method = RequestMethod.POST)
    public String doSavePost(@Validated MessageForm messageForm, BindingResult result,
                             HttpServletRequest request, ModelMap modelMap) {
        if (StringUtils.isBlank(messageForm.getContent())) {
            modelMap.addAttribute("msgSendError", "消息内容不能为空，无法保存。");
            return "/msg/msg.vm";
        }
        UmsMsgDraft umsMsgDraft = messageForm.cloneUmsMsgDraft();
        String userId = OperationContextHolder.getPrincipal().getUserId();
        umsMsgDraft.setUserId(userId);
        if (logger.isInfoEnabled()) {
            logger.info("保存草稿:umsMsgDraft" + umsMsgDraft);
        }
        boolean res = msgDraftService.saveOrUpdateDraft(umsMsgDraft);
        if (res) {
            return "/msg/msgSaveSuccess.vm";
        } else {
            modelMap.addAttribute("msgSendError", "保存消息失败。");
            return "/msg/msg.vm";
        }
    }

    /**
     *查看草稿箱
     *
     * @param messageForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/msg/msgDraft.htm")
    public String doGet(MessageForm messageForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        String pageId = request.getParameter("pageId");
        int page = StringHelper.parseInt(pageId);
        String userId = OperationContextHolder.getPrincipal().getUserId();
        PageResult<UmsMsgDraft> pageResult = msgDraftService.findDraft(userId, page);
        modelMap.addAttribute("results", pageResult);
        return "/msg/msgDraft.vm";
    }

    /**
     *删除草稿
     *
     * @param messageForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/msg/msgDraftDel.htm", method = RequestMethod.GET)
    public String DelDraft(MessageForm messageForm, BindingResult result,
                           HttpServletRequest request, ModelMap modelMap) {

        String draftId = request.getParameter("draftId");
        String pageId = request.getParameter("pageId");
        int page = StringHelper.parseInt(pageId);
        UmsMsgDraft draft = msgDraftService.findDraft(draftId);
        String userId = OperationContextHolder.getPrincipal().getUserId();
        if (draft == null) {
            addErrorMsg(modelMap, "记录不存在。");
            return "/msg/msgDraft.vm";
        }

        if (StringUtils.equals(draft.getUserId(), userId)) {
            boolean res = msgDraftService.delDraft(draftId);
            if (res) {
                addMsg(modelMap, "删除成功。");
            } else {
                addMsg(modelMap, "删除失败。");
            }
        } else {
            addErrorMsg(modelMap, "不允许删除。");
        }
        PageResult<UmsMsgDraft> pageResult = msgDraftService.findDraft(userId, page);
        modelMap.addAttribute("results", pageResult);
        return "/msg/msgDraft.vm";
    }

    private void addErrorMsg(ModelMap modelMap, String msg) {
        modelMap.addAttribute("errorMsg", msg);
    }

    private void addMsg(ModelMap modelMap, String msg) {
        modelMap.addAttribute("msg", msg);
    }
}
