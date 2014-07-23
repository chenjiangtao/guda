/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.util;

/**
 * 
 * @author foodoon
 * @version $Id: CronUtils.java, v 0.1 2013-5-25 上午6:34:49 foodoon Exp $
 */
public class CronUtils {

    public static int getCron(String cron) {
        if (cron == null) {
            return -1;
        }
        try {
            int res = Integer.parseInt(cron);
            if (res > 0) {
                return res;
            }
        } catch (Exception e) {

        }
        return 0;
    }

}
