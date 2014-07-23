/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal.test;

import net.zoneland.ums.common.mysql.dal.FilterMessageMapper;
import net.zoneland.ums.common.mysql.dataobject.FilterMessage;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: FilterMessageTest.java, v 0.1 2012-11-15 下午3:52:58 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class FilterMessageTest extends BaseMysqlDaoTest {

    @Autowired
    private FilterMessageMapper filterMessageMapper;

    @Test
    public void test() {
        FilterMessage f = filterMessageMapper.selectByContent("test");
        System.out.println(f);
    }

}
