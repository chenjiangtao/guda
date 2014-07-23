/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.monitor.server.config.app;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author gag
 * @version $Id: AppConfigurer.java, v 0.1 2012-5-10 下午1:23:25 gag Exp $
 */
public class AppConfigurer {

    protected static final Log               logger                    = LogFactory
                                                                           .getLog(AppConfigurer.class);

    public static final String               appLocation               = "app-config.xml";

    public static final String               LOG_HOME_KEY              = "log4j.home";

    private static Properties                appProperties;

    private static CreateAppConfigProperties createAppConfigProperties = new CreateAppConfigProperties();

    public static void initAppConfig(String destLocation) {
        try {
            ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = currentClassLoader.getResourceAsStream(appLocation);

            logger.info("create monitor-server.properties...");
            appProperties = createAppConfigProperties.createaAppConfig(is, destLocation);
            logger
                .info("create monitor-agent.properties successful.location[" + destLocation + "]");

            System.setProperty(LOG_HOME_KEY, filterNull(appProperties.getProperty(LOG_HOME_KEY)));

        } catch (Exception e) {
            logger.error("创建monitor-agent.properties错误", e);

        }
    }

    private static String filterNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * Getter method for property <tt>appProperties</tt>.
     * 
     * @return property value of appProperties
     */
    public static Properties getAppProperties() {
        return appProperties;
    }

}
