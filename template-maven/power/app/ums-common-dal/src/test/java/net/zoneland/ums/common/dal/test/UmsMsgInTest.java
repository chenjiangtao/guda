/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.zoneland.ums.common.dal.UmsMsgInMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author XuFan
 * @version $Id: UmsMsgInTest.java, v 0.1 Aug 25, 2012 1:48:47 PM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsMsgInTest extends BaseDaoTest {
    @Autowired
    private UmsMsgInMapper umsMsgInMapper;

    //@Test
    public void testGetTodayMsgIn() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        System.out.println(date);
        System.out.println(umsMsgInMapper.getMsgInToday(date));
    }

    //    @Test
    //    public void testFiling() {
    //        Calendar d = Calendar.getInstance();
    //        /// String mon = String.valueOf(d.get(Calendar.MONTH));
    //        d.add(Calendar.MONTH, -3);
    //        Date date = DateHelper.getEndOfMonth(d.getTime());
    ////        String mon = DateHelper.monFormat.format(date);
    //        Map<String, Object> params = new HashMap<String, Object>(6);
    //        params.put("tableName", "UMS_MSG_IN_" + mon);
    //        params.put("gmtCreated", date);
    ////        System.out.println(DateHelper.dateFormat.format(date));
    //        int c = umsMsgInMapper.fileSelectUmsMsgIn("UMS_MSG_IN_" + mon);
    //        if (c > 0) {
    //            System.out.println("已经归档");
    //            return;
    //        }
    //        umsMsgInMapper.fileCreateUmsMsgIn(params);
    //
    //        int res = umsMsgInMapper.fileInsertUmsMsgIn(params);
    //        System.out.println(res);
    //        if (res > 0) {
    //            umsMsgInMapper.fileDeleteUmsMsgIn(params);
    //
    //        }
    //
    //    }
}
