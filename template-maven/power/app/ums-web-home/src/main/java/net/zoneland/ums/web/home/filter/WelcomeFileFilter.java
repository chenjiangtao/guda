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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.web.home.velocity.VelocityToolboxView;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 *
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
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                                             throws IOException,
                                                                                             ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            logger.info("*****base:url:" + httpRequest.getRequestURL());
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (isRootRequest(httpRequest)) {
                String base = VelocityToolboxView.getReqHost(httpRequest);
                logger.info("******base:" + base);
                if (!base.endsWith("/")) {
                    base = base + "/";
                }
                httpResponse.sendRedirect(base + welcomeFile);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isRootRequest(HttpServletRequest request) {
        if (!StringUtils.hasLength(request.getServletPath())
            || ("/").equals(request.getServletPath()) || ("").equals(request.getServletPath())) {
            if (request.getRequestURI().equals(request.getContextPath() + "/")
                || request.getRequestURI().equals(request.getContextPath())) {
                logger.info("equal[" + request.getContextPath() + "]");
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
