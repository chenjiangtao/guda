/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal.test;

import net.zoneland.ums.common.mysql.dal.InErrorMapper;
import net.zoneland.ums.common.mysql.dataobject.InError;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: InErrorTest.java, v 0.1 2012-11-15 下午3:27:16 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class InErrorTest extends BaseMysqlDaoTest {

    @Autowired
    private InErrorMapper inErrorMapper;

    @Test
    public void testInsert() {
        InError in = new InError();
        in.setBatchno("1");
        in.setSerialno(1);
        int res = inErrorMapper.insert(in);
        System.out.println(res);
    }

}
