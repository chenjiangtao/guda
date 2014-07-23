/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author gag
 * @version $Id: DenyController.java, v 0.1 2012-9-8 下午1:21:36 gag Exp $
 */
@Controller
@RequestMapping("/accessRefuse.htm")
public class DenyController {

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "accessRefuse.vm";
    }

}
