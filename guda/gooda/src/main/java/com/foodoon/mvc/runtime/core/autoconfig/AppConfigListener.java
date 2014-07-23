/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.autoconfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author gag
 * @version $Id: AppConfigListener.java, v 0.1 2012-5-10 下午1:22:07 gag Exp $
 */
public class AppConfigListener implements ServletContextListener {

    /** 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        AppConfigurer.initAppConfig(sce.getServletContext());
    }

    /** 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
