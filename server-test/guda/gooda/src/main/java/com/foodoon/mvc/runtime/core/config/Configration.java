/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.config;

import java.util.Map;

/**
 * 
 * @author gag
 * @version $Id: Configration.java, v 0.1 2012-4-25 下午7:12:15 gag Exp $
 */
public interface Configration {

    public static final String APP_NAME          = "app_name";

    public static final String APP_RUN_MODE      = "run_mode";

    public static final String APP_ROOT          = "app_root";

    public static final String HTDOCS_ROOT       = "htdocs_root";

    public static final String APP_RUN_MODE_PROD = "prod";

    public static final String APP_RUN_MODE_TEST = "test";

    public Map<String, String> getConfig();

    public String getPropertyValue(String key);

    public String getAppName();

    public String getRunMode();

    public String getAppRoot();

    public String getHtdocsRoot();

}
