/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.biz.common.mail.MailService;
import com.tiaotiaogift.web.home.ums.form.SuggestionForm;
import com.tiaotiaogift.web.mvc.Form;

/**
 * 
 * @author gang
 * @version $Id: SuggestionController.java, v 0.1 2013-5-10 下午3:06:50 gang Exp $
 */
@Controller
public class SuggestionController {

    private Logger      logger = LoggerFactory.getLogger(SuggestionController.class);

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/suggestion.htm", method = RequestMethod.GET)
    public String doGet(SuggestionForm suggestionForm, BindingResult result,
                        HttpServletRequest request, ModelMap modelMap, HttpServletResponse response)
                                                                                                    throws IOException {

        return "suggestion/suggestion.vm";
    }

    @Form
    @RequestMapping(value = "/suggestion.htm", method = RequestMethod.POST)
    public String doPost(@Valid SuggestionForm suggestionForm, BindingResult result,
                         HttpServletRequest request, ModelMap modelMap, HttpServletResponse response)
                                                                                                     throws IOException {
        if (result.hasErrors()) {
            return "suggestion/suggestion.vm";
        }
        try {
            mailService.sendMail("UMS365用户建议", suggestionForm.getContent(), "52313882@qq.com");
        } catch (Exception e) {
            logger.error("", e);
        }
        return "suggestion/suggestionSuccess.vm";
    }

}
