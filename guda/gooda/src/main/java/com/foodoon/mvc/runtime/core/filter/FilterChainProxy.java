/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * 
 * @author gag
 * @version $Id: FilterChainProxy.java, v 0.1 2012-5-23 下午4:43:30 gag Exp $
 */
public class FilterChainProxy extends GenericFilterBean {

    /**
     * Setter method for property <tt>filterChains</tt>.
     * 
     * @param filterChains value to be assigned to property filterChains
     */
    public void setFilterChains(List<Filter> filterChains) {
        this.filterChains = filterChains;
    }

    private List<Filter> filterChains;

    /** 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                                             throws IOException,
                                                                                             ServletException {
        if (filterChains == null || filterChains.size() == 0) {

            chain.doFilter(request, response);

            return;
        }
        VirtualFilterChain vfc = new VirtualFilterChain(chain, filterChains);
        vfc.doFilter(request, response);
    }

    private static class VirtualFilterChain implements FilterChain {
        private final FilterChain  originalChain;
        private final List<Filter> additionalFilters;
        private final int          size;
        private int                currentPosition = 0;

        private VirtualFilterChain(FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
            this.size = additionalFilters.size();
        }

        public void doFilter(ServletRequest request, ServletResponse response) throws IOException,
                                                                              ServletException {
            if (currentPosition == size) {
                originalChain.doFilter(request, response);
            } else {
                currentPosition++;
                Filter nextFilter = additionalFilters.get(currentPosition - 1);
                nextFilter.doFilter(request, response, this);
            }
        }
    }

}
