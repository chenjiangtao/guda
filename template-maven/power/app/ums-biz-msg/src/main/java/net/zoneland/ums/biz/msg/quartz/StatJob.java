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
 * @version $Id: StatJob.java, v 0.1 2013-1-23 下午4:30:43 Administrator Exp $
 */
public class StatJob implements Job2 {

    private static final Logger logger           = LoggerFactory.getLogger("UMS-QUARTZ");

    /**  */
    private static final long   serialVersionUID = -8509418780280183660L;

    /**
     * @see net.zoneland.ums.biz.msg.quartz.support.Job2#executeInternal(org.springframework.context.ApplicationContext)
     */
    public void executeInternal(ApplicationContext cxt) {
        logger.info("开始短信统计！");
        UmsMsgStatBiz umsMsgStatBiz = cxt.getBean(UmsMsgStatBiz.class);
        try {
            umsMsgStatBiz.statMsg();
        } catch (Exception e) {
            logger.error("短信统计!", e);
        }
        logger.info("短信统计结束！");
    }

}
