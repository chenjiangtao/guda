/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 应用配置文件的目录及URL属性。jetty加载的应用使用app-test.config，作为war包部署的，采用app.config。
 * 
 * @author gag
 * @version $Id: AppConfigLocation.java, v 0.1 2012-5-10 下午1:54:23 gag Exp $
 */
public class AppConfigLocation {

    public static final String default_props                = "app.config";

    public static final String default_test_props           = "app-test.config";

    public static String       default_props_location_linux = "/zoneland/" + default_props;
    public static String       default_props_location_win   = System.getProperty("user.home") + File.separator
                                                              + default_props;

    public static String getLocation() {
        String configPath = default_props_location_linux;
        if (isWindows()) {
            configPath = default_props_location_win;
        }
        File configFile = new File(configPath);
        configFile.getParentFile().mkdirs();
        return configPath;
    }

    /**
     * 当前操作系统是否为windows
     * 
     * @return true -》windows
     */
    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Windows")) {
            return true;
        }
        return false;
    }

    public static void main(String args[]) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -7);
        SimpleDateFormat sdf = new SimpleDateFormat("'.'yyyy-MM-dd");
        System.out.println(sdf.format(calendar.getTime()));
    }
}
