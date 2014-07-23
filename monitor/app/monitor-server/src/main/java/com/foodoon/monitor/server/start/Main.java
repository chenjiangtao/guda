/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.server.start;

import com.foodoon.monitor.server.config.app.AppConfigurer;

/**
 * 
 * @author gang
 * @version $Id: Main.java, v 0.1 2013-4-21 下午3:16:07 gang Exp $
 */
public class Main {

    public static void main(String[] args) throws Exception {

        App.set();
        AppConfigurer.initAppConfig(App.root_properties);
        JettyServer jetty = new JettyServer();
        int httpPort = getStr(AppConfigurer.getAppProperties().get("http.port"));
        int ajpPort = getStr(AppConfigurer.getAppProperties().get("ajp.port"));
        System.out.println("start listen http port:" + httpPort);
        System.out.println("start listen ajp port:" + ajpPort);
        //jetty.setJettyPort(9090);
        jetty.start(httpPort, ajpPort);
    }

    private static int getStr(Object str) {
        if (str == null) {
            return 0;
        }
        try {
            return Integer.parseInt(String.valueOf(str));
        } catch (Exception e) {

        }
        return 0;
    }

}
