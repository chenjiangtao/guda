/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.monitor.dal.AgentMapper;

/**
 * 
 * @author gang
 * @version $Id: IndexController.java, v 0.1 2013-4-18 下午1:15:45 gang Exp $
 */
@Controller
public class IndexServerController {

    private final static Logger logger = LoggerFactory.getLogger(IndexServerController.class);

    @Autowired
    private AgentMapper         agentMapper;

    @RequestMapping(value = "indexServerChart.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "indexServerChart.vm";

    }

    @RequestMapping(value = "indexServerGrid.htm", method = RequestMethod.GET)
    public String doGetGrid(HttpServletRequest request, ModelMap modelMap) {

        return "indexServerGrid.vm";

    }

}
