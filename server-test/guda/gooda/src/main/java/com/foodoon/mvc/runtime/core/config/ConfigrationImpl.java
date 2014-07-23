/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author gag
 * @version $Id: ConfigrationImpl.java, v 0.1 2012-4-25 下午7:15:48 gag Exp $
 */
public class ConfigrationImpl implements Configration {

    private Map<String, String> appConfigMap = new LinkedHashMap<String, String>();

    /** 
     * @see com.foodoon.mvc.runtime.core.config.Configration#getAppName()
     */
    public String getAppName() {
        return getPropertyValue(APP_NAME);
    }

    /** 
     * @see com.foodoon.mvc.runtime.core.config.Configration#getConfig()
     */
    public Map<String, String> getConfig() {
        return appConfigMap;
    }

    /** 
     * @see com.foodoon.mvc.runtime.core.config.Configration#getPropertyValue(java.lang.String)
     */
    public String getPropertyValue(String key) {
        return appConfigMap.get(key);
    }

    /** 
     * @see com.foodoon.mvc.runtime.core.config.Configration#getRunMode()
     */
    public String getRunMode() {
        return getPropertyValue(APP_RUN_MODE);
    }

    /** 
     * @see com.foodoon.mvc.runtime.core.config.Configration#getAppRoot()
     */
    public String getAppRoot() {
        return getPropertyValue(APP_ROOT);
    }

    public void setProperty(String key, String value) {
        this.appConfigMap.put(key, value);
    }

    public String getHtdocsRoot() {
        // TODO Auto-generated method stub
        return getPropertyValue(HTDOCS_ROOT);
    }

}
