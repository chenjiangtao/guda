/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author foodoon
 * @version $Id: ConfigController.java, v 0.1 2013-5-21 下午3:42:09 foodoon Exp $
 */
@Controller
public class ConfigController {

    @RequestMapping(value = "config.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "config.vm";

    }

}
