/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.start.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foodoon.monitor.agent.SqlTask;
import com.foodoon.monitor.agent.config.TaskFactory;

/**
 * 
 * @author foodoon
 * @version $Id: TestCron.java, v 0.1 2013-5-25 上午6:28:21 foodoon Exp $
 */
public class TestCron {

    public static void main(String[] args) {
        System.out.println(TaskFactory.getDbInfos().size());
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:test-*.xml");
        //GarTask task = new GarTask();

        SqlTask task = (SqlTask) context.getBean("sqlTask");
        task.runTask(TestTaskFactory.getDbInfos());
    }

}
