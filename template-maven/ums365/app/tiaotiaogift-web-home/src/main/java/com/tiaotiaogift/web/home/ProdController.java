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
 * @version $Id: ProdController.java, v 0.1 2012-12-2 上午11:51:27 gang Exp $
 */
@Controller
@RequestMapping("prod")
public class ProdController {

    @RequestMapping(value = "/prod.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "prod/prod.vm";

    }

    @RequestMapping(value = "/ums.htm", method = RequestMethod.GET)
    public String doGetUms(HttpServletRequest request, ModelMap modelMap) {

        return "prod/ums.vm";

    }

    @RequestMapping(value = "/monitor.htm", method = RequestMethod.GET)
    public String doGetMonitor(HttpServletRequest request, ModelMap modelMap) {

        return "prod/monitor.vm";

    }

}
