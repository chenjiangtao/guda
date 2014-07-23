/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.monitor.dal.AgentMapper;

/**
 * 
 * @author foodoon
 * @version $Id: IndexTabController.java, v 0.1 2013-5-28 下午12:46:15 foodoon Exp $
 */
public class IndexDbController {

    private final static Logger logger = LoggerFactory.getLogger(IndexServerController.class);

    @Autowired
    private AgentMapper         agentMapper;

    @RequestMapping(value = "indexDbGrid.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "indexDbGrid.vm";

    }

    @RequestMapping(value = "indexDbChart.htm", method = RequestMethod.GET)
    public String doGetChart(HttpServletRequest request, ModelMap modelMap) {

        return "indexDbChart.vm";

    }

}
