/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal.test;

import net.zoneland.ums.common.mysql.dal.OutReplyMapper;
import net.zoneland.ums.common.mysql.dataobject.OutReply;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: OutReplyTest.java, v 0.1 2012-11-15 下午3:34:58 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class OutReplyTest extends BaseMysqlDaoTest {

    @Autowired
    private OutReplyMapper outReplyMapper;

    @Test
    public void test() {
        OutReply out = outReplyMapper.selectByReplyDes("13588754575");
        System.out.println(out.getReplydes());
    }

}
