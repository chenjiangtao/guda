/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import net.zoneland.ums.common.dal.UmsGatewayRuleMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gang
 * @version $Id: UmsGatewayRuleMapperTest.java, v 0.1 2012-9-4 下午10:20:27 gang Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsGatewayRuleMapperTest extends BaseDaoTest {

    @Autowired
    private UmsGatewayRuleMapper umsGatewayRuleMapper;

    @Test
    public void testSelectAll() {
        umsGatewayRuleMapper.selectAll();
    }

}
