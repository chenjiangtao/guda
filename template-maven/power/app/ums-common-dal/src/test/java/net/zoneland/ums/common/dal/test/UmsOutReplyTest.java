/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.Date;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsOutAppReplyMapper;
import net.zoneland.ums.common.dal.UmsOutReplyMapper;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author Administrator
 * @version $Id: UmsOutReplyTest.java, v 0.1 2012-11-22 上午9:36:11 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsOutReplyTest extends BaseDaoTest {

    @Autowired
    private UmsOutReplyMapper    umsOutReplyMapper;

    @Autowired
    private UmsOutAppReplyMapper umsOutAppReplyMapper;

    //    @Test
    public void test() {
        UmsOutReply umsOutReply = new UmsOutReply();
        umsOutReply.setId(UUID.randomUUID().toString());
        umsOutReply.setGmtCreated(new Date());
        umsOutReply.setReply("12131");
        //        umsOutReply.setReplyDes("1231231");
        umsOutReply.setBatchNo("20121122102310");
        int result = umsOutReplyMapper.insert(umsOutReply);
        System.out.println("执行结果:" + result);
    }

    // @Test
    public void test1() {
        UmsOutReply umsOutReply = new UmsOutReply();
        umsOutReply.setId(UUID.randomUUID().toString());
        umsOutReply.setGmtCreated(new Date());
        //        umsOutReply.setReply("12131");
        //        umsOutReply.setReplyDes("1231231");
        //        umsOutReply.setBatchNo("20121122102310");
        System.out.println(umsOutAppReplyMapper);
        int result = umsOutAppReplyMapper.insert(umsOutReply);
        System.out.println("执行结果:" + result);
    }

    @Test
    public void test3() {
        System.out.println(DateHelper.getLastDay());
        int result = umsOutAppReplyMapper.deleteLastWeekReply(DateHelper.getLastDay());
        System.out.println("执行结果:" + result);
    }
}
