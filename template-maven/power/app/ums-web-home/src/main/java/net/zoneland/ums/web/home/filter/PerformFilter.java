/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.zoneland.ums.common.util.Counter;

/**
 * 
 * @author gag
 * @version $Id: PerformFilter.java, v 0.1 2012-10-11 下午6:33:28 gag Exp $
 */
public class PerformFilter implements Filter {

    private static final Counter counter = new Counter("webservice");

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
        counter.count();
        chain.doFilter(request, response);
    }

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

}
