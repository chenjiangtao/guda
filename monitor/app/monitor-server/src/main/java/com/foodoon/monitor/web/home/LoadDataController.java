/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.foodoon.monitor.dal.AgentMapper;
import com.foodoon.monitor.dal.SysMapper;
import com.foodoon.monitor.dal.WarnMapper;
import com.foodoon.monitor.dal.dataobject.Agent;
import com.foodoon.monitor.dal.dataobject.Sys;
import com.foodoon.monitor.dal.dataobject.Warn;
import com.foodoon.monitor.enums.ValueTypeEnums;
import com.foodoon.monitor.web.home.vo.Node;
import com.foodoon.monitor.web.home.vo.SetVo;
import com.foodoon.monitor.web.home.vo.SysVo;
import com.foodoon.monitor.web.home.vo.UiVo;
import com.google.gson.Gson;

/**
 * @author gang
 * @version $Id: LoadDataController.java, v 0.1 2013-4-18 下午3:13:12 gang Exp $
 * @param <DataNode>
 */
@Controller
public class LoadDataController extends BaseController {

    @Autowired
    private SysMapper   sysMapper;

    @Autowired
    private WarnMapper  warnMapper;

    @Autowired
    private AgentMapper agentMapper;

    @RequestMapping(value = "refresh.json")
    public void doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String key = request.getParameter("key");
        String host = request.getParameter("host");
        Date start = getDate(request.getParameter("start"));
        Date end = getDate(request.getParameter("end"));
        Integer isWarn = getInt(request.getParameter("isWarn"));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", host);
        if (start.after(end)) {
            params.put("startTime", end);
            params.put("endTime", start);
        } else {
            params.put("startTime", start);
            params.put("endTime", end);
        }
        params.put("isWarn", isWarn);
        params.put("key", key);

        List<Sys> val = sysMapper.selectByType(params);

        Map<String, Object> p = new HashMap<String, Object>();
        p.put("ip", host);
        p.put("k", key);
        Warn ww = warnMapper.selectByIpAndKey(p);
        super.ajaxReturnObj(formatStr(val, ww), response);

    }

    @RequestMapping(value = "refreshTab.json")
    public void doGetTab(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String key = request.getParameter("key");
        String host = request.getParameter("host");
        Integer page = getInt(request.getParameter("page"), 1);
        Integer rows = getInt(request.getParameter("rows"), 5);
        Date start = getDate(request.getParameter("start"));
        Date end = getDate(request.getParameter("end"));
        Integer isWarn = getInt(request.getParameter("isWarn"));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", host);
        if (start.after(end)) {
            params.put("startTime", end);
            params.put("endTime", start);
        } else {
            params.put("startTime", start);
            params.put("endTime", end);
        }
        params.put("isWarn", isWarn);
        params.put("start", (page - 1) * rows);
        params.put("rows", page * rows);

        params.put("key", key);
        List<Sys> val = sysMapper.selectByTypePage(params);
        UiVo vo = new UiVo();
        vo.setTotal(sysMapper.selectCountByType(params));
        vo.setRows(convert2Vo(val));
        super.ajaxReturn(vo, response);

    }

    private List<SysVo> convert2Vo(List<Sys> val) {
        if (val == null) {
            return null;
        }
        Iterator<Sys> it = val.iterator();
        List<SysVo> vos = new ArrayList<SysVo>(val.size());
        while (it.hasNext()) {
            Sys sys = it.next();
            SysVo vo = new SysVo();
            vo.setIp(sys.getIp());
            vo.setK(sys.getK());
            if (sys.getValueType() == ValueTypeEnums.server.getValue()
                || sys.getValueType() == ValueTypeEnums.db.getValue()) {
                vo.setValText(sys.getValText());
            } else {
                vo.setValText(String.valueOf(sys.getVal()));
                vo.setWarn(isWarn(sys.getIp(), sys.getK(), sys.getVal()));
            }

            vo.setGmtCreated(format(sys.getGmtCreated()));
            vos.add(vo);
        }
        return vos;
    }

    private boolean isWarn(String ip, String k, float v) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("k", k);
        Warn ww = warnMapper.selectByIpAndKey(params);
        if (ww != null) {
            String con = ww.getCondition();
            Float val = ww.getVal();
            if (">".equals(con) && v > val) {
                return true;
            } else if ("<".equals(con) && v < val) {
                return true;
            } else if ("=".equals(con) && v == val) {
                return true;
            }
        }
        return false;
    }

    private String format(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    private int getInt(String page, int def) {
        if (page == null) {
            return def;
        }
        try {
            return Integer.parseInt(page);
        } catch (Exception e) {

        }
        return def;
    }

    @RequestMapping(value = "set.json")
    public void getSet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        int page = getInt(request.getParameter("page"), 1);
        int rows = getInt(request.getParameter("rows"), 20);
        String ip = request.getParameter("ip");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("rows", page * rows);
        params.put("start", (page - 1) * rows);
        List<Warn> res = warnMapper.selectByPage(params);
        SetVo v = new SetVo();
        v.setRows(res);
        v.setTotal(warnMapper.selectByCount(params));
        super.ajaxReturn(v, response);

    }

    @RequestMapping(value = "getKey.json")
    public void getKey(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        String ip = request.getParameter("ip");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("valueType", ValueTypeEnums.dbFloat.getValue());
        List<Agent> agents = agentMapper.selectByIpAndType(params);

        params.put("valueType", ValueTypeEnums.serverFloat.getValue());
        List<Agent> agentsFloat = agentMapper.selectByIpAndType(params);
        agents.addAll(agentsFloat);
        super.ajaxReturn(agents, response);

    }

    @RequestMapping(value = "getServerKey.json")
    public void getServerKey(HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap) {

        String ip = request.getParameter("ip");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("valueType", ValueTypeEnums.server.getValue());
        List<Agent> agents = agentMapper.selectByIpAndType(params);

        params.put("valueType", ValueTypeEnums.serverFloat.getValue());
        List<Agent> agentsFloat = agentMapper.selectByIpAndType(params);
        agents.addAll(agentsFloat);
        super.ajaxReturn(agents, response);

    }

    @RequestMapping(value = "getServerFloatKey.json")
    public void getServerFloatKey(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap modelMap) {

        String ip = request.getParameter("ip");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("valueType", ValueTypeEnums.serverFloat.getValue());
        List<Agent> agents = agentMapper.selectByIpAndType(params);

        super.ajaxReturn(agents, response);

    }

    @RequestMapping(value = "getDbKey.json")
    public void getDbKey(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        String ip = request.getParameter("ip");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("valueType", ValueTypeEnums.db.getValue());
        List<Agent> agents = agentMapper.selectByIpAndType(params);

        params.put("valueType", ValueTypeEnums.dbFloat.getValue());
        List<Agent> agentsFloat = agentMapper.selectByIpAndType(params);
        agents.addAll(agentsFloat);
        super.ajaxReturn(agents, response);

    }

    @RequestMapping(value = "getDbFloatKey.json")
    public void getDbFloatKey(HttpServletRequest request, HttpServletResponse response,
                              ModelMap modelMap) {

        String ip = request.getParameter("ip");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("valueType", ValueTypeEnums.dbFloat.getValue());
        List<Agent> agents = agentMapper.selectByIpAndType(params);

        super.ajaxReturn(agents, response);

    }

    private String formatStr(List<Sys> sys, Warn warn) {
        if (sys == null || sys.size() == 0) {
            return "";
        }
        Iterator<Sys> s = sys.iterator();

        Object[][] array = new Object[sys.size()][2];
        if (warn == null) {
            int i = 0;
            while (s.hasNext()) {
                Sys ss = s.next();
                array[i][1] = ss.getVal();
                array[i][0] = (getDateStr(ss.getGmtCreated()));
                ++i;
            }
            Gson gson = new Gson();
            return gson.toJson(array);
        } else {
            Object[] res = new Object[2];
            Object[][] warnArray = new Object[sys.size()][2];
            int i = 0;
            while (s.hasNext()) {
                Sys ss = s.next();
                array[i][1] = ss.getVal();
                String d = getDateStr(ss.getGmtCreated());
                array[i][0] = (d);

                warnArray[i][1] = warn.getVal();
                warnArray[i][0] = (d);
                ++i;
            }
            res[0] = array;
            res[1] = warnArray;
            Gson gson = new Gson();
            return gson.toJson(res);

        }

    }

    private String valueOf(Object obj) {
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static Date getDate(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {

        }
        return null;
    }

    public static String getDateStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(date);

    }

    public static Integer getInt(String val) {
        if (val == null) {
            return null;
        }
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {

        }
        return null;
    }

    public static void main(String args[]) {
        List<Node> ns = new ArrayList<Node>();

        Node n = new Node();
        String[][] array = new String[2][2];
        array[0][0] = "111";
        array[0][1] = "222";
        array[1][0] = "333";
        array[1][1] = "444";

        Gson gson = new Gson();
        System.out.println(gson.toJson(gson.toJson(array)));
    }

}
