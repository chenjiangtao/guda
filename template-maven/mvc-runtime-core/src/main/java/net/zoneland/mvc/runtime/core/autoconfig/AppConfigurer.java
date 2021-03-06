/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.autoconfig;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;

import net.zoneland.mvc.runtime.core.config.ConfigrationFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @author gag
 * @version $Id: AppConfigurer.java, v 0.1 2012-5-10 下午1:23:25 gag Exp $
 */
public class AppConfigurer {

    protected static final Log               logger                    = LogFactory
                                                                           .getLog(AppConfigurer.class);

    public static final String               appLocation               = "/WEB-INF/app-config.xml";

    private static Properties                appProperties;

    private static CreateAppConfigProperties createAppConfigProperties = new CreateAppConfigProperties();

    public static void initAppConfig(ServletContext servletContext) {
        try {
            String location = WebUtils.getRealPath(servletContext, appLocation);
            String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
            logger.info("Initializing app-config from [" + resolvedLocation + "]");

            File file = ResourceUtils.getFile(resolvedLocation);
            if (file.exists()) {
                logger.info("create app.config,from:[" + resolvedLocation + "].");
                appProperties = createAppConfigProperties.createaAppConfig(file.getAbsolutePath());
                logger.info("create app.config successful.");
            } else {
                appProperties = ConfigrationFactory.getTestProperties();
                logger.warn("无法根据xml创建app.config.当前是开发环境?");
            }

        } catch (Exception e) {
            logger.error("创建app.config错误", e);

        }
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
