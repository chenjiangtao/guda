/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.List;

import net.zoneland.ums.common.dal.UmsTelDescribeMapper;
import net.zoneland.ums.common.dal.dataobject.UmsTelDescribe;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author Administrator
 * @version $Id: UmsTelDescribeMapperTest.java, v 0.1 2012-11-28 上午11:35:22 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsTelDescribeMapperTest extends BaseDaoTest {

    @Autowired
    private UmsTelDescribeMapper umsTelDescribeMapper;

    @Test
    public void test() {
        List<UmsTelDescribe> lists = umsTelDescribeMapper.selectByTel("551－812");
        System.out.println(lists);
    }
}
