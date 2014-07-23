/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal.test;

import net.zoneland.ums.common.mysql.dal.RecvidAppMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: RecvidAppTest.java, v 0.1 2012-11-15 下午3:54:50 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class RecvidAppTest extends BaseMysqlDaoTest {

    @Autowired
    private RecvidAppMapper recvidAppMapper;

    @Test
    public void test() {
        String out = recvidAppMapper.selectAppId("13588754574");
        System.out.println(out);
    }

}
