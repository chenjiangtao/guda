/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BlankController {

    private final static Logger logger = LoggerFactory.getLogger(BlankController.class);

    @RequestMapping(value = "*/*.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        if (logger.isInfoEnabled()) {
            logger.info("test url" + request.getRequestURL());
        }
        String path = request.getRequestURI();
        if (logger.isInfoEnabled()) {
            logger.info("test url:" + path);
        }
        String p = path.substring(1, path.length() - 3);
        return p + "vm";

    }

    @RequestMapping(value = "*.htm", method = RequestMethod.GET)
    public String doGet0(HttpServletRequest request, ModelMap modelMap) {
        if (logger.isInfoEnabled()) {
            logger.info("test url" + request.getRequestURL());
        }
        String path = request.getRequestURI();
        if (logger.isInfoEnabled()) {
            logger.info("test url:" + path);
        }
        String p = path.substring(1, path.length() - 3);
        return p + "vm";

    }

}
