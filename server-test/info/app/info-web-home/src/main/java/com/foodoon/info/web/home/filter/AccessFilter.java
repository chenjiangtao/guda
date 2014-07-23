/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.info.web.home.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;

import org.apache.log4j.Logger;

/**
 * @author gang
 * @version $Id: AccessFilter.java, v 0.1 2012-8-29 下午11:49:21 gang Exp $
 */
public class AccessFilter implements Filter {

    private final static Logger  logger         = Logger.getLogger(AccessFilter.class);

    public static final String   OPERATIONATTR  = "OPERATION_ATTR";

    private Map<String, String>  acf;

    private final PropertiesUtil propertiesUtil = new PropertiesUtil();

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        URL url = currentClassLoader.getResource("security/info.acf");
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            acf = propertiesUtil.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("can not find acf's config [ums.acf] details ["
                                       + e.getMessage() + "]");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭失败或者已关闭", e);
                }
            }
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                                             throws IOException,
                                                                                             ServletException {
        OperationContext context = SecurityContextHolder.getContext();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();
        String contextPath = httpRequest.getContextPath();
        String queryStr = httpRequest.getQueryString();
        Object bo = httpRequest.getSession().getAttribute(AccessFilter.OPERATIONATTR);
        logger.info("request:" + path + ",上下文:" + bo);
        if (context == null) {
            if (getRole(path) != null) {
                if (!"login.htm".equals(path)) {
                    httpResponse.sendRedirect(contextPath
                                              + "/user/login.htm?errorCode=needLogin&refer="
                                              + getRefer(contextPath + path, queryStr));
                    return;
                }
            } else {

            }

        } else {
            String rr = getRole(path);
            if (rr != null) {
                if (!hasRole(rr, context)) {
                    logger.warn("当前用户:[" + context.getPrincipal().getLogonId() + "]无权限访问:[" + path
                                + "],context:" + context);
                    httpResponse.sendRedirect(contextPath + "/accessRefuse.htm");
                    return;
                }
            }
        }
        chain.doFilter(request, response);

    }

    private String getRefer(String path, String queryStr) throws UnsupportedEncodingException {
        if (path.indexOf("loginOut.htm") > -1) {
            return null;
        }
        if (queryStr == null) {
            return java.net.URLEncoder.encode(path, "UTF-8");
        } else {
            return java.net.URLEncoder.encode(path + "?" + queryStr, "UTF-8");
        }
    }

    private String getRole(String path) {
        Iterator<String> it = acf.keySet().iterator();
        while (it.hasNext()) {
            String p = it.next();
            if (path.matches(p)) {
                return String.valueOf(acf.get(p));
            }
        }
        return null;
    }

    private boolean hasRole(String role, OperationContext context) {
        if (role != null) {
            // 判断当前用户是否有权限
            RoleInfo[] roles = (RoleInfo[]) context.getAuthorities();
            for (int i = 0, len = roles.length; i < len; ++i) {
                if (role.contains(roles[i].getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

}
