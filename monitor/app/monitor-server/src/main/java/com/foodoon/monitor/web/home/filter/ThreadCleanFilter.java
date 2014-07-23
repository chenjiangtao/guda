/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;

/**
 * 
 * @author gang
 * @version $Id: ThreadCleanFilter.java, v 0.1 2012-8-30 上午6:40:40 gang Exp $
 */
public class ThreadCleanFilter implements Filter {

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

            OperationContext context = (OperationContext) httpRequest.getSession().getAttribute(
                AccessFilter.OPERATIONATTR);
            if (context == null) {
                SecurityContextHolder.clear();
            } else {
                SecurityContextHolder.setContext(context);
            }
        }
        chain.doFilter(request, response);
    }

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

}
