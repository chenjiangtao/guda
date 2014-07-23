/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.filing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.zoneland.ums.common.dal.UmsFilingMapper;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: UmsMsgOutFiling.java, v 0.1 2012-10-19 下午2:57:46 gag Exp $
 */
public class DBFilingImpl implements DBFiling {

    /**设置归档日志*/
    private static final Logger logger                 = LoggerFactory.getLogger("UMS-FILING");

    private static final String UMS_MSG_OUT_PREFIX     = "UMS_MSG_OUT_";

    private static final String UMS_MSG_OUT_UCS_PREFIX = "UMS_MSG_OUT_UCS_";

    private static final String UMS_MSG_IN_PREFIX      = "UMS_MSG_IN_";

    @Autowired
    private UmsFilingMapper     umsFilingMapper;

    /** 
     * @see net.zoneland.ums.biz.msg.filing.DBFiling#filing()
     */
    public void filing(int repeatInterval) {
        SimpleDateFormat monFormat = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar d = Calendar.getInstance();
        d.add(Calendar.MONTH, 0 - repeatInterval);
        Date date = DateHelper.getEndOfMonth(d.getTime());
        String mon = monFormat.format(date);
        logger.warn("开始归档，，，归档结束日期:[" + dateFormat.format(date) + "],归档月份:[" + mon + "]");
        try {
            boolean res = filingUmsMsgOut(mon, date);
            if (res) {
                logger.warn("归档ums_msg_out successful");
            }
        } catch (Exception e) {
            logger.error("归档ums_msg_out错误.", e);
        }
        try {
            boolean res = filingUmsMsgOutUcs(mon, date);
            if (res) {
                logger.warn("归档ums_msg_out_ucs successful");
            }
        } catch (Exception e) {
            logger.error("归档ums_msg_out_ucs错误.", e);
        }
        try {
            boolean res = filingUmsMsgIn(mon, date);
            if (res) {
                logger.warn("归档ums_msg_in successful");
            }
        } catch (Exception e) {
            logger.error("归档ums_msg_in错误.", e);
        }
    }

    private boolean filingUmsMsgOut(String mon, Date date) {
        umsFilingMapper.filingUmsMsgOut(UMS_MSG_OUT_PREFIX + mon, date);
        return true;
    }

    private boolean filingUmsMsgOutUcs(String mon, Date date) {

        umsFilingMapper.filingUmsMsgOutUcs(UMS_MSG_OUT_UCS_PREFIX + mon, date);
        return true;
    }

    private boolean filingUmsMsgIn(String mon, Date date) {
        umsFilingMapper.filingUmsMsgIn(UMS_MSG_IN_PREFIX + mon, date);
        return true;
    }

    public static void main(String[] args) {
        Calendar d = Calendar.getInstance();
        SimpleDateFormat monFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        d.add(Calendar.MONTH, -3);
        System.out.println(monFormat.format(DateHelper.getEndOfMonth(d.getTime())));
    }

}
