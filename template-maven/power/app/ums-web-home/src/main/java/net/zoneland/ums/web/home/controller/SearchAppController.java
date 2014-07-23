/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.controller;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.util.AppForm;
import net.zoneland.ums.biz.config.util.SearchAppBiz;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author wangyong
 * @version $Id: SearchAppController.java, v 0.1 2012-10-16 上午9:26:58 wangyong Exp $
 */
@Controller
public class SearchAppController extends BaseController {

    private final static Logger logger = Logger.getLogger(SearchAppController.class);

    @Autowired
    private SearchAppBiz        searchAppBiz;

    @RequestMapping("/searchApps.ajax")
    public void searchApp(HttpServletRequest request, HttpServletResponse response)
                                                                                   throws Exception {
        String name = StringUtils.trim(request.getParameter("appName"));
        if ("".equalsIgnoreCase(name)) {
            name = null;
        }
        try {
            List<AppForm> appForms = searchAppBiz.getAppByName(name);
            if (appForms == null || appForms.size() == 0) {
                AppForm appForm = new AppForm("应用不存在", "");
                appForms.add(appForm);
            }
            Collections.sort(appForms, new MyAppFormcomparator());
            ajaxReturn(appForms, response);
        } catch (Exception e) {
            logger.error("查询应用出错:", e);
        }
    }

    @RequestMapping("/searchAppAdminApps.ajax")
    public void searchAppAdminApps(HttpServletRequest request, HttpServletResponse response)
                                                                                            throws Exception {
        String appName = request.getParameter("appName").trim();
        if ("".equalsIgnoreCase(appName)) {
            appName = null;
        }
        List<String> appIds = getRoleAppIds();
        List<AppForm> appForms = searchAppBiz.getAppByAppName(appName, appIds);
        if (appForms.size() == 0) {
            AppForm appForm = new AppForm("应用不存在", "");
            appForms.add(appForm);
        }
        Collections.sort(appForms, new MyAppFormcomparator());
        ajaxReturn(appForms, response);
    }

    @RequestMapping("/searchSubApps.ajax")
    public void searchSubApp(HttpServletRequest request, HttpServletResponse response)
                                                                                      throws Exception {
        String appId = StringUtils.trim(request.getParameter("appId"));
        if ("".equalsIgnoreCase(appId)) {
            appId = null;
        }
        List<UmsAppSub> umsAppSubs = searchAppBiz.getAppSubByAppId(appId);
        Collections.sort(umsAppSubs, new MySubAppcomparator());
        ajaxReturn(umsAppSubs, response);
    }

    class MyAppFormcomparator implements Comparator<AppForm> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(AppForm o1, AppForm o2) {

            return collator.compare(o1.getAppId(), o2.getAppId());

        }

    }

    class MySubAppcomparator implements Comparator<UmsAppSub> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsAppSub o1, UmsAppSub o2) {

            return collator.compare(o1.getAppSubName(), o2.getAppSubName());

        }

    }

    private List<String> getRoleAppIds() {
        List<String> appids = null;
        try {
            RoleInfo[] roles = (RoleInfo[]) SecurityContextHolder.getContext().getAuthorities();
            if (roles != null && roles.length > 0) {
                for (int i = 0, len = roles.length; i < len; ++i) {
                    if (RoleNameEnum.APP.getValue().equals(roles[i].getRoleName())) {
                        appids = roles[i].getAppId();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取用户管理应用的权限出错！", e);
        }
        return appids;
    }

}
