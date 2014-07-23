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
 * ������
 *
 * @author gag
 * @version $Id: BlankController.java, v 0.1 2012-4-26 ����9:16:33 gag Exp $
 */
@Controller
@RequestMapping("/*.htm")
public class BlankController {

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        String path = request.getServletPath();
        //        if (logger.isInfoEnabled()) {
        //            logger.info("test url:" + request.getServletPath());
        //        }
        return path.substring(0, path.length() - 3) + "vm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(HttpServletRequest request, ModelMap modelMap) {
        String path = request.getServletPath();
        return path.substring(0, path.length() - 3) + "vm";
    }

}
