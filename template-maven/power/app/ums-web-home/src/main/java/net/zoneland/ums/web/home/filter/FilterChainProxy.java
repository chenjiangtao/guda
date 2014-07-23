/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @author gag
 * @version $Id: FilterChainProxy.java, v 0.1 2012-5-23 下午4:43:30 gag Exp $
 */
public class FilterChainProxy implements Filter {

    private List<Filter> filterChains;

    private final Object delegateMonitor = new Object();

    /** 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        synchronized (this.delegateMonitor) {
            if (this.filterChains != null) {
                Iterator<Filter> it = filterChains.iterator();
                while (it.hasNext()) {
                    it.next().init(filterConfig);
                }

            }
        }
    }

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

    public void setFilterChains(List<Filter> filterChains) {
        this.filterChains = filterChains;
    }

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

}
