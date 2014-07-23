/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.monitor.dal.AgentMapper;
import com.foodoon.monitor.dal.dataobject.Agent;
import com.foodoon.monitor.web.home.vo.TreeNode;

/**
 * 
 * @author gang
 * @version $Id: AgentController.java, v 0.1 2013-4-18 下午1:33:41 gang Exp $
 */
@Controller
public class AgentController extends BaseController {

    @Autowired
    private AgentMapper agentMapper;

    @RequestMapping(value = "agent.json")
    public void doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Agent> list = agentMapper.selectAll();
        Iterator<Agent> it = list.iterator();
        List<TreeNode> r = new ArrayList<TreeNode>();

        TreeNode root = new TreeNode();
        root.setId("root");
        root.setText("服务器");
        Set<TreeNode> c = new HashSet<TreeNode>();
        root.setChildren(c);
        r.add(root);
        while (it.hasNext()) {
            Agent agent = it.next();
            TreeNode t = new TreeNode();
            t.setId(agent.getIp());
            t.setText(agent.getHost() + "(" + agent.getIp() + ")");
            c.add(t);
        }

        super.ajaxReturn(r, response);

    }

}
