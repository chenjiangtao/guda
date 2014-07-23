/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.start.test;

import java.net.UnknownHostException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foodoon.monitor.agent.GarTask;
import com.foodoon.monitor.agent.config.app.AppConfigurer;
import com.foodoon.monitor.agent.start.App;

/**
 * 
 * @author gang
 * @version $Id: Main.java, v 0.1 2013-4-21 下午3:16:07 gang Exp $
 */
public class TestMain {

    public static void main(String[] args) throws UnknownHostException {

        App.setTest();
        AppConfigurer.initAppConfig(App.root_properties);

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:test-*.xml");
        //GarTask task = new GarTask();

        GarTask task = (GarTask) context.getBean("garTask");

        task.runTask(TestTaskFactory.getSysInfos());
    }

}
