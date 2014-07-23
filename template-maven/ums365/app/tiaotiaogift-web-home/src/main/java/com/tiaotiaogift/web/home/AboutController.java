/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author gang
 * @version $Id: AboutController.java, v 0.1 2012-12-2 上午11:50:01 gang Exp $
 */
@Controller
@RequestMapping("about")
public class AboutController {

    @RequestMapping(value = "/about.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "about/about.vm";

    }

}
