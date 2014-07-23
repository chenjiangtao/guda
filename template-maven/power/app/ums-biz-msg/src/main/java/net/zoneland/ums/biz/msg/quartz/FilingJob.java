/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.zoneland.ums.biz.msg.filing.DBFiling;
import net.zoneland.ums.biz.msg.quartz.support.Job2;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @author gag
 * @version $Id: FilingJob.java, v 0.1 2012-10-22 上午8:55:18 gag Exp $
 */
public class FilingJob implements Job2 {

    private int                 repeatInterval;

    /**  */
    private static final long   serialVersionUID = -4611638831000138909L;

    /**设置归档日志*/
    private static final Logger logger           = LoggerFactory.getLogger("UMS-FILING");

    /** 
     * @see net.zoneland.ums.biz.msg.quartz.support.Job2#executeInternal(org.springframework.context.ApplicationContext)
     */
    public void executeInternal(ApplicationContext cxt) {
        Calendar d = Calendar.getInstance();
        int mon = d.get(Calendar.MONTH) + 1;
        //if (mon % repeatInterval == 0) {
        logger.info("当前月份为:" + mon + "，开始归档数据");
        DBFiling filing = cxt.getBean(DBFiling.class);
        filing.filing(repeatInterval);
        //        } else {
        //            logger.info("当前月份为:" + mon + "，不需要归档数据");
        //        }

    }

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar d = Calendar.getInstance();
        d.add(Calendar.MONTH, -3);
        String mon = String.valueOf(d.get(Calendar.MONTH));
        Date date = DateHelper.getEndOfMonth(d.getTime());
        System.out.println(mon);
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
