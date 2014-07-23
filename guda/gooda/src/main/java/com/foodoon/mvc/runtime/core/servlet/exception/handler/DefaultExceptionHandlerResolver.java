/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.servlet.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author gag
 * @version $Id: DefaultExceptionHandlerResolver.java, v 0.1 2012-5-1 下午11:35:47 gag Exp $
 */
public class DefaultExceptionHandlerResolver implements HandlerExceptionResolver {

    public static final String errorPage = "error.htm";

    /** 
     * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView("redirect:" + errorPage);
        return mv;
    }

}
