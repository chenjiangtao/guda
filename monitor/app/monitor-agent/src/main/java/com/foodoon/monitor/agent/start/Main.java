/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.start;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foodoon.monitor.agent.GarTask;
import com.foodoon.monitor.agent.SqlTask;
import com.foodoon.monitor.agent.config.TaskFactory;
import com.foodoon.monitor.agent.config.app.AppConfigurer;

/**
 * 
 * @author gang
 * @version $Id: Main.java, v 0.1 2013-4-21 下午3:16:07 gang Exp $
 */
public class Main {

    public static void main(String[] args) throws IOException {
        App.check();
        App.set();
        AppConfigurer.initAppConfig(App.root_properties);
        System.setProperty("log4j.home",
            getDefStr(AppConfigurer.getAppProperties().get("log4j.home"), "/home/admin"));

        ApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath*:spring/spring-*.xml");
        SqlTask task = (SqlTask) context.getBean("sqlTask");
        task.runTask(TaskFactory.getDbInfos());

        GarTask gartask = (GarTask) context.getBean("garTask");

        gartask.runTask(TaskFactory.getSysInfos());
    }

    public static String getDefStr(Object obj, String def) {
        if (obj == null) {
            return def;
        }
        return String.valueOf(obj);
    }

}
