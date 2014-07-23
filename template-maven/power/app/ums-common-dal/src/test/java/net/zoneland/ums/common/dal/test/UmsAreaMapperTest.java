/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import net.zoneland.ums.common.dal.UmsAreaMapper;
import net.zoneland.ums.common.dal.dataobject.UmsArea;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author yangjuanying
 * @version $Id: UmsAreaTest.java, v 0.1 2012-10-18 上午11:35:40 yangjuanying Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsAreaMapperTest extends BaseDaoTest {

    @Autowired
    private UmsAreaMapper umsAreaMapper;

    @Test
    public void testSelectByPrimaryKey() {
        UmsArea u = new UmsArea();
        String id = "14937214-f5d6-4ea5-9485-cd613c7d1746";
        u = umsAreaMapper.selectByPrimaryKey(id);
        System.out.println(u.getAreaCode());
    }

}
