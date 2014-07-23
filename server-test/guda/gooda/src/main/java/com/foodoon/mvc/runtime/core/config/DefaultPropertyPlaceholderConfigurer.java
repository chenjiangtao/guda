/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.config;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * 加载应用配置文件app.config，这里区分开发环境和线上环境
 * @author gag
 * @version $Id: DefaultPropertyPlaceholderConfigurer.java, v 0.1 2012-4-26 ����8:55:57 gag Exp $
 */
public class DefaultPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements
                                                                                       InitializingBean {

    /**
     * 查找配置文件路径
     */
    public void setConfigLocation() {
        String configPath = AppConfigLocation.getLocation();
        File file = new File(configPath);
        if (file.exists()) {
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            super.setLocation(fileSystemResource);
            if (logger.isInfoEnabled()) {
                logger.info("load app.config from file system [" + fileSystemResource.getPath()
                            + "] success.");
            }

        } else {
            if (!setLocationWithClassPath(AppConfigLocation.default_props)) {
                if (logger.isWarnEnabled()) {
                    logger.warn("cant not find load app.config ,try load app-test.config....");
                }
                if (!setLocationWithClassPath(AppConfigLocation.default_test_props)) {
                    throw new RuntimeException("cannot find app.config or app-test.config!!");
                }
            }

        }
    }

    /**
     * 设置配置文件路径
     * 
     * @param fileUri
     * @return
     */
    public boolean setLocationWithClassPath(String fileUri) {
        ClassPathResource classPathResource = new ClassPathResource(fileUri);
        if (classPathResource.exists()) {
            super.setLocation(classPathResource);
            if (logger.isWarnEnabled()) {
                logger.warn("load [" + fileUri + "] from classpath [" + classPathResource.getPath()
                            + "] success.");
            }
            return true;
        }
        return false;
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        setConfigLocation();
    }
}
