/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.config;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author zhigang.ge
 * @version $Id: ConfigrationFactory.java, v 0.1 2012-4-15 下午07:52:57 zhigang.ge Exp $
 */
public class ConfigrationFactory {

    private static ConfigrationImpl configImpl;

    private static Properties       testProperties;

    public static Configration getConfigration() {
        if (configImpl == null) {
            configImpl = new ConfigrationImpl();
            loadFromConfig();
            print();
        }
        return configImpl;
    }

    private static void loadFromConfig() {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        URL url = currentClassLoader.getResource("test.properties");
        if (url == null) {
            url = currentClassLoader.getResource("test.properties");
        }

        if (url == null) {
            throw new RuntimeException("can not find app's config [app.config]!");
        }

        testProperties = new Properties();
        try {
            testProperties.load(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException("can not find app's config [app.config] details ["
                                       + e.getMessage() + "]");
        }

        Set<Map.Entry<Object, Object>> entrySet = testProperties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            String sysValue = System.getProperty(key);
            if (sysValue != null && sysValue.length() > 0) {
                System.out.println("配置参数 key[" + key + "],系统参数[" + sysValue + "]，value:[" + value
                                   + "]");
                value = sysValue;
            }

            configImpl.setProperty(key, value);
        }

    }

    private static void print() {
        Map<String, String> configs = configImpl.getConfig();
        Set<Map.Entry<String, String>> configSet = configs.entrySet();

        System.out.println("系统参数配置:");
        for (Iterator<Map.Entry<String, String>> iterator = configSet.iterator(); iterator
            .hasNext();) {
            Map.Entry<String, String> entry = iterator.next();

            System.out.println(" | " + entry.getKey() + "  [" + entry.getValue() + "]");
        }
    }

    /**
     * Getter method for property <tt>testProperties</tt>.
     * 
     * @return property value of testProperties
     */
    public static Properties getTestProperties() {
        if (testProperties == null) {
            loadFromConfig();
        }
        return testProperties;
    }

    /**
     * Setter method for property <tt>testProperties</tt>.
     * 
     * @param testProperties value to be assigned to property testProperties
     */
    public static void setTestProperties(Properties testProperties) {
        ConfigrationFactory.testProperties = testProperties;
    }

}
