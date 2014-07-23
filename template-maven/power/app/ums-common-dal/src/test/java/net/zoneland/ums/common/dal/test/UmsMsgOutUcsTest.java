/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Administrator
 * @version $Id: UmsMsgOutUcsTest.java, v 0.1 2012-10-11 下午6:06:54 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsMsgOutUcsTest extends BaseDaoTest {
    @Autowired
    private UmsMsgOutUcsMapper umsMsgOutUcsMapper;

    //@Test
    public void testSelectDelayLimit1000() {
        Map<String, Object> map = new HashMap<String, Object>(6);
        map.put("status", MsgInfoStatusEnum.wait.getValue());
        map.put("sendTime", new Date());

    }

    //    @Test
    //    public void testFiling() {
    //        Calendar d = Calendar.getInstance();
    //        /// String mon = String.valueOf(d.get(Calendar.MONTH));
    //        d.add(Calendar.MONTH, -3);
    //        Date date = DateHelper.getEndOfMonth(d.getTime());
    //        String mon = DateHelper.monFormat.format(date);
    //        Map<String, Object> params = new HashMap<String, Object>(6);
    //        params.put("tableName", "UMS_MSG_OUT_UCS_" + mon);
    //        params.put("gmtCreated", date);
    //        System.out.println(DateHelper.dateFormat.format(date));
    //        int c = umsMsgOutUcsMapper.fileSelectUmsMsgOutUcs("UMS_MSG_OUT_UCS_" + mon);
    //        if (c > 0) {
    //            System.out.println("已经归档");
    //            return;
    //        }
    //        umsMsgOutUcsMapper.fileCreateUmsMsgOutUcs(params);
    //
    //        int res = umsMsgOutUcsMapper.fileInsertUmsMsgOutUcs(params);
    //        System.out.println(res);
    //        if (res > 0) {
    //            umsMsgOutUcsMapper.fileDeleteUmsMsgOutUcs(params);
    //
    //        }
    //
    //    }
}
