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
 * @version $Id: QController.java, v 0.1 2012-12-2 上午11:49:23 gang Exp $
 */
@Controller
@RequestMapping("q")
public class QController {

    @RequestMapping(value = "/q.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "q/q.vm";

    }

    @RequestMapping(value = "/monitor.htm", method = RequestMethod.GET)
    public String doGetMonitor(HttpServletRequest request, ModelMap modelMap) {

        return "q/monitor.vm";

    }

}
