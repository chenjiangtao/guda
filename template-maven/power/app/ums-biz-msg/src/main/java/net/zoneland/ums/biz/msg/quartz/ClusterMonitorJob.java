/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.quartz;

import net.zoneland.ums.biz.msg.quartz.support.Job2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Administrator
 * @version $Id: ClusterMonitorJob.java, v 0.1 2013-1-15 上午11:23:25 Administrator Exp $
 */
public class ClusterMonitorJob implements Job2 {

    private static final Logger logger           = LoggerFactory.getLogger("UMS-QUARTZ");

    private static final long   serialVersionUID = 1L;

    /**
     * @see net.zoneland.ums.biz.msg.quartz.support.Job2#executeInternal(org.springframework.context.ApplicationContext)
     */
    public void executeInternal(ApplicationContext cxt) {
        //logger.info("定时任务启动~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~集群检测。");
        ClusterMonitorBiz clusterMonitorBiz = cxt.getBean(ClusterMonitorBiz.class);
        try {
            //clusterMonitorBiz.ClusterMoitor();
        } catch (Exception e) {
            logger.error("集群监测出现错误!", e);
        }
    }
}
