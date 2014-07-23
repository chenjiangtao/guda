/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal.test;

import net.zoneland.ums.common.mysql.dal.InReadyMapper;
import net.zoneland.ums.common.mysql.dataobject.InReady;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: InReadyTest.java, v 0.1 2012-11-15 下午2:10:04 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class InReadyTest extends BaseMysqlDaoTest {

    @Autowired
    private InReadyMapper inReadyMapper;

    @Test
    public void testInsert() {
        InReady in = new InReady();
        in.setBatchno("12");
        in.setSerialno(1);
        int res = inReadyMapper.insert(in);
        System.out.println(res);
    }

}
