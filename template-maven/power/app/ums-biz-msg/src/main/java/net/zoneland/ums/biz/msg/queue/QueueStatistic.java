/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.queue;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统计队列信息
 * @author gag
 * @version $Id: QueueStat.java, v 0.1 2012-5-30 下午4:02:29 gag Exp $
 */
@Component
public class QueueStatistic implements InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger("STATISTIC");

    @Autowired
    private QueueFactory          queueFactory;

    private long                  date   = System.currentTimeMillis() / 24 / 60 / 60 / 1000;

    public void stat() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new statTask(), 1, 60 * 1000);
    }

    class statTask extends TimerTask {

        /**
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            String sysInfo = getSysInfo();
            if (logger.isInfoEnabled()) {
                logger.info(sysInfo);
            }
            if (System.currentTimeMillis() / 24 / 60 / 60 / 1000 != date) {
                queueFactory.getQueueTotalCount().set(0);
                date = System.currentTimeMillis() / 24 / 60 / 60 / 1000;
            }
        }
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        stat();
    }

    public String getSysInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        StringBuilder buff = new StringBuilder();
        buff.append("\n消息普通队列当前消息数量:[").append(queueFactory.getQueueCount().get()).append("]\n")
            .append("消息营销定时队列当前消息数量:[").append(queueFactory.getDealyQueueCount().get())
            .append("]\n").append("消息营销普通队列当前消息数量:[")
            .append(queueFactory.getMarketQueueCount().get()).append("]\n")
            .append("消息95598队列当前消息数量:[").append(queueFactory.getCount95598().get()).append("]\n")
            .append("数据短信队列当前消息数量:[").append(queueFactory.getNumberQueueCount().get())
            .append("]\n").append("消息紧急队列当前消息数量:[")
            .append(queueFactory.getUrgentQueueCount().get()).append("]\n").append("今天总共发送count:[")
            .append(queueFactory.getQueueTotalCount().get()).append("],堆内存已用：[")
            .append(memoryMXBean.getHeapMemoryUsage().getUsed() / 1024).append("KB/")
            .append(memoryMXBean.getHeapMemoryUsage().getMax() / 1024).append("KB],非堆已用:[")
            .append(memoryMXBean.getNonHeapMemoryUsage().getUsed() / 1024).append("KB]/")
            .append(memoryMXBean.getNonHeapMemoryUsage().getMax() / 1024).append("KB]，系统负载：[")
            .append(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage())
            .append("],当前线程数:[").append(threadMXBean.getThreadCount()).append("].");
        String sysInfo = buff.toString();
        return sysInfo;
    }

}
