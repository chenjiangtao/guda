/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zoneland.ums.biz.config.admin.SystemCheckBiz;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author XuFan
 * @version $Id: SystemController.java, v 0.1 Aug 25, 2012 2:34:46 PM XuFan Exp
 *          $
 */
@Controller
@RequestMapping("admin/system")
public class SystemController extends BaseController {

    private static final Logger logger               = Logger.getLogger(SystemController.class);

    public static final String  LAST_QUERY_DATE_ATTR = "LAST_QUERY_DATE_ATTR";

    public static final int     TIMELIMIR            = 5000;

    @Autowired
    private SystemCheckBiz      systemCheck;

    @Autowired
    private EhCacheCacheManager cacheManager;

    /**
     * 查询系统一些数据<br/>
     * 1.因为数据表数据较多，则使用缓存。<br/>
     * 2.判断时间是否是五秒之后。<br/>
     * 3.如果是五秒之前就使用缓存数据。五秒之后就重新查询数据。<br/>
     * 4.返回页面。
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/system.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        HttpSession session = request.getSession();
        Date test = (Date) session.getAttribute(LAST_QUERY_DATE_ATTR);
        String key = session.getId();
        long time = new Date().getTime();
        if (test == null || (time - test.getTime()) > TIMELIMIR) {
            Map<String, Integer> map = Collections.emptyMap();
            try {
                map = systemCheck.getSystemParams();
            } catch (Exception e) {
                logger.error("查询系统出现异常", e);
                return "admin/system/system.vm";
            }
            modelMap.addAttribute("in_count", map.get("in_count"));// 上行数量
            modelMap.addAttribute("Out_count", map.get("Out_count"));// 下行数量
            modelMap.addAttribute("Queue_count", map.get("Queue_count"));// 当前队列中待发送短信量
            modelMap.addAttribute("Recv_count", map.get("Recv_count"));// 当天接收到短信数据量
            modelMap.addAttribute("success_count", map.get("success_count"));// 当天发送成功数量
            modelMap.addAttribute("Send_count", map.get("Send_count"));// 当前发送消息量
            double percent = 0;
            if (map.get("Send_count") != 0) {
                percent = (double) map.get("success_count") / map.get("Send_count");
            }
            modelMap.addAttribute("percent", (double) ((int) (percent * 10000)) / 100);
            session.setAttribute(LAST_QUERY_DATE_ATTR, new Date());
            cacheManager.getCache("systemCache").put(key, modelMap);
        } else {
            ValueWrapper wrapper = cacheManager.getCache("systemCache").get(key);
            modelMap.addAllAttributes((ModelMap) wrapper.get());
        }
        return "admin/system/system.vm";
    }
}
