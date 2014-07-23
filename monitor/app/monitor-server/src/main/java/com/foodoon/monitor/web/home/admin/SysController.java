/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.admin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.monitor.dal.AgentMapper;
import com.foodoon.monitor.dal.dataobject.Agent;

/**
 * 
 * @author foodoon
 * @version $Id: SysController.java, v 0.1 2013-6-7 下午8:54:49 foodoon Exp $
 */
@Controller
public class SysController {

    @Autowired
    private AgentMapper agentMapper;

    @RequestMapping(value = "/sys.htm")
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Agent> list = agentMapper.selectAll();
        Iterator<Agent> it = list.iterator();
        HashSet<String> s = new HashSet<String>();
        while (it.hasNext()) {
            Agent agent = it.next();
            s.add(agent.getIp());

        }
        modelMap.addAttribute("agent", s);
        return "sys.vm";
    }
}
