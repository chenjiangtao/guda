/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.info.web.home.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.web.home.admin.form.SaveForm;

/**
 * 
 * @author gag
 * @version $Id: SaveController.java, v 0.1 2012-12-6 下午5:16:07 gag Exp $
 */
@Controller
public class SaveController {

    @RequestMapping(value = "/admin/s.htm", method = RequestMethod.POST)
    public String doSave(SaveForm saveForm, HttpServletRequest request, ModelMap modelMap) {

        return "admin/save.vm";
    }

}
