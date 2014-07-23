/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.monitor.dal.AgentMapper;
import com.foodoon.monitor.dal.SysMapper;
import com.foodoon.monitor.dal.WarnMapper;
import com.foodoon.monitor.dal.dataobject.Agent;
import com.foodoon.monitor.dal.dataobject.Warn;
import com.foodoon.monitor.enums.ValueTypeEnums;
import com.foodoon.monitor.web.home.vo.AllVo;
import com.foodoon.monitor.web.home.vo.StatVo;

/**
 * @author foodoon
 * @version $Id: IndexAllController.java, v 0.1 2013-6-20 下午3:09:46 foodoon Exp $
 */
@Controller
public class IndexAllController extends BaseController {

    @Autowired
    private SysMapper   sysMapper;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private WarnMapper  warnMapper;

    @RequestMapping(value = "indexAll.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "indexAll.vm";

    }

    @RequestMapping(value = "indexServerDetail.htm", method = RequestMethod.GET)
    public String doGetServerDetail(HttpServletRequest request, String ip, ModelMap modelMap) {
        modelMap.addAttribute("currentIp", ip);
        return "indexServerDetail.vm";

    }

    @RequestMapping(value = "indexDbDetail.htm", method = RequestMethod.GET)
    public String doGetDbDetail(HttpServletRequest request, String ip, ModelMap modelMap) {
        modelMap.addAttribute("currentIp", ip);
        return "indexDbDetail.vm";

    }

    @RequestMapping(value = "/indexAll.json")
    public void doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Agent> list = agentMapper.selectAll();
        Iterator<Agent> it = list.iterator();
        Map<String, AllVo> map = new HashMap<String, AllVo>();
        while (it.hasNext()) {
            Agent agent = it.next();
            if (ValueTypeEnums.dbFloat.getValue() == agent.getValueType()
                || ValueTypeEnums.serverFloat.getValue() == agent.getValueType()) {
                AllVo allVo = map.get(agent.getIp());
                if (allVo == null) {
                    allVo = new AllVo();
                    allVo.setHost(agent.getHost());
                    allVo.setIp(agent.getIp());
                    Map<String, Object> p = new HashMap<String, Object>();
                    p.put("ip", agent.getIp());
                    p.put("host", agent.getHost());
                    p.put("valueType", agent.getValueType());
                    int total = sysMapper.selectRecentCount(p);
                    if (ValueTypeEnums.dbFloat.getValue() == agent.getValueType()) {
                        allVo.setDbTotal(total);
                    } else {
                        allVo.setServerTotal(total);
                    }
                    p.put("k", agent.getK());
                    p.put("key", agent.getK());
                    Warn ww = warnMapper.selectByIpAndKey(p);
                    if (ww != null) {
                        String con = "val" + ww.getCondition() + " " + ww.getVal();
                        p.put("condi", con);
                        int warn = sysMapper.selectRecentCount(p);
                        if (ValueTypeEnums.dbFloat.getValue() == agent.getValueType()) {
                            allVo.setDbWarn(warn);
                        } else {
                            allVo.setServerWarn(warn);
                        }
                    }
                    map.put(agent.getIp(), allVo);
                } else {
                    Map<String, Object> p = new HashMap<String, Object>();
                    p.put("ip", agent.getIp());
                    p.put("host", agent.getHost());
                    p.put("valueType", agent.getValueType());
                    if (allVo.getServerTotal() == 0) {
                        p.put("valueType", ValueTypeEnums.serverFloat.getValue());
                        allVo.addServerTotal(sysMapper.selectRecentCount(p));
                    } else if (allVo.getDbTotal() == 0) {
                        p.put("valueType", ValueTypeEnums.dbFloat.getValue());
                        allVo.addDbTotal(sysMapper.selectRecentCount(p));
                    }
                    p.put("k", agent.getK());
                    p.put("key", agent.getK());

                    Warn ww = warnMapper.selectByIpAndKey(p);
                    if (ww != null) {
                        String con = "val " + ww.getCondition() + " " + ww.getVal();
                        p.put("condi", con);
                        int warn = sysMapper.selectRecentCount(p);
                        if (ValueTypeEnums.dbFloat.getValue() == agent.getValueType()) {
                            allVo.addDbWarn(allVo.getDbWarn() + warn);
                        } else {
                            allVo.addServerWarn(allVo.getServerWarn() + warn);
                        }
                    }
                }
            }
        }
        Iterator<AllVo> itt = map.values().iterator();
        while (itt.hasNext()) {
            AllVo v = itt.next();
            v.stat();
            // v.setArrayStr(super.toJson(v.getArrays()));
        }
        super.ajaxReturn(map.values(), response);

    }

    private void statKey(String key, AllVo allVo) {
        StatVo stat = new StatVo();
        stat.setKey(key);

    }

}
