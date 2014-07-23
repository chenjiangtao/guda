/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.info.web.home.filter;

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
import org.springframework.util.StringUtils;

/**
 * @author gag
 * @version $Id: WelcomeFileFilter.java, v 0.1 2012-5-25 上午9:47:58 gag Exp $
 */

public class WelcomeFileFilter implements Filter {

    private final static Logger logger = Logger.getLogger(WelcomeFileFilter.class);

    private String              welcomeFile;

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            logger.info(httpRequest.getServletPath() + "--" + httpRequest.getRequestURI() + ",equal["
                        + httpRequest.getContextPath() + "]");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (isRootRequest(httpRequest)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + welcomeFile);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isRootRequest(HttpServletRequest request) {
        if (!StringUtils.hasLength(request.getServletPath()) || ("/").equals(request.getServletPath())) {
            if (request.getRequestURI().equals(request.getContextPath() + "/") || request.getRequestURI().equals("/")) {
                logger.info("is root equal[" + request.getContextPath() + "]");
                return true;
            }

        }
        return false;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * Setter method for property <tt>welcomeFile</tt>.
     * 
     * @param welcomeFile value to be assigned to property welcomeFile
     */
    public void setWelcomeFile(String welcomeFile) {
        this.welcomeFile = welcomeFile;
    }

}
