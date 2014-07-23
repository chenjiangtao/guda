/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.start.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foodoon.monitor.agent.GarTask;
import com.foodoon.monitor.agent.SqlTask;
import com.foodoon.monitor.agent.config.app.AppConfigurer;
import com.foodoon.monitor.agent.start.App;

/**
 * 
 * @author foodoon
 * @version $Id: TestGarAll.java, v 0.1 2013-5-30 下午2:19:55 foodoon Exp $
 */
public class TestGarAll {

    public static void main(String[] args) {

        App.setTest();
        AppConfigurer.initAppConfig(App.root_properties);
        System.out.println(TestTaskFactory.getDbInfos().size());
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:test-*.xml");
        //GarTask task = new GarTask();

        SqlTask task = (SqlTask) context.getBean("sqlTask");
        task.runTask(TestTaskFactory.getDbInfos());

        GarTask gartask = (GarTask) context.getBean("garTask");

        gartask.runTask(TestTaskFactory.getSysInfos());
    }

}
