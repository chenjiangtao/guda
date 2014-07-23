/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 
 * @author foodoon
 * @version $Id: SchedulerPool.java, v 0.1 2013-5-25 上午8:38:02 foodoon Exp $
 */
public class SchedulerPool {

    private static ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    static {
        scheduler.setPoolSize(100);
        scheduler.initialize();
    }

    public static ThreadPoolTaskScheduler get() {
        return scheduler;
    }

}
