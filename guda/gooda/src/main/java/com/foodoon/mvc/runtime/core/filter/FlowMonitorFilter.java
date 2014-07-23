/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 娴侀噺鎺у埗鍣�
 * @author gag
 * @version $Id: RequestMonitorFilter.java, v 0.1 2012-5-26 涓嬪崍8:28:49 gag Exp $
 */

public class FlowMonitorFilter implements Filter {

    private final static Logger logger    = Logger.getLogger(FlowMonitorFilter.class);

    @Autowired
    private FlowChecker         flowChecker;

    private String              exceedUrl = "busy.html";

    /** 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /** 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                                             throws IOException,
                                                                                             ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String uri = httpRequest.getRequestURI();
            if (!flowChecker.checker(uri)) {
                if (logger.isInfoEnabled()) {
                    logger.info("[" + uri + "]");
                }
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect(exceedUrl);
            }
        }
        chain.doFilter(request, response);
    }

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * Getter method for property <tt>exceedUrl</tt>.
     * 
     * @return property value of exceedUrl
     */
    public String getExceedUrl() {
        return exceedUrl;
    }

    /**
     * Setter method for property <tt>exceedUrl</tt>.
     * 
     * @param exceedUrl value to be assigned to property exceedUrl
     */
    public void setExceedUrl(String exceedUrl) {
        this.exceedUrl = exceedUrl;
    }

    /**
     * Getter method for property <tt>flowChecker</tt>.
     * 
     * @return property value of flowChecker
     */
    public FlowChecker getFlowChecker() {
        return flowChecker;
    }

    /**
     * Setter method for property <tt>flowChecker</tt>.
     * 
     * @param flowChecker value to be assigned to property flowChecker
     */
    public void setFlowChecker(FlowChecker flowChecker) {
        this.flowChecker = flowChecker;
    }

}
