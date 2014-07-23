/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.HtmlUtils;

/**
 * 对提交的字符串进行转义，如'<','>','&'等
 * @author gag
 * @version $Id: HtmlCharFilter.java, v 0.1 2012-5-26 下午8:30:07 gag Exp $
 */

public class HtmlEscapeFilter implements Filter {

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
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String uri = httpServletRequest.getRequestURI();
            if (uri != null && uri.endsWith(".htm")) {
                request = new Request(httpServletRequest);
            }
        }
        chain.doFilter(request, response);
    }

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    class Request extends HttpServletRequestWrapper {

        /** 
         * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
         */
        @Override
        public String[] getParameterValues(String name) {

            String[] values = super.getParameterValues(name);
            if (values == null) {
                return null;
            }
            for (int i = 0; i < values.length; i++) {
                values[i] = HtmlUtils.htmlEscape(values[i]);
            }
            return values;

        }

        /** 
         * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
         */
        @Override
        public String getParameter(String name) {
            return HtmlUtils.htmlEscape(super.getParameter(name));
        }

        /** 
         * @see javax.servlet.ServletRequestWrapper#getParameterMap()
         */
        @Override
        public Map<String, String> getParameterMap() {
            @SuppressWarnings("unchecked")
            Map<String, String> map = super.getParameterMap();
            if (map == null) {
                return null;
            }
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                map.put(key, HtmlUtils.htmlEscape(map.get(key)));
            }
            return map;
        }

        /**
         * @param request
         */
        public Request(HttpServletRequest request) {
            super(request);
        }

    }

}
