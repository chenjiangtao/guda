/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsAppSubMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author wangyong
 * @version $Id: UmsAppSubMapperTest.java, v 0.1 2012-10-16 下午3:22:58 wangyong Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsAppSubMapperTest extends BaseDaoTest {

    @Autowired
    private UmsAppSubMapper umsAppSubMapper;

    @Test
    public void testGetAppByNameAndAppId() {
        Map<String, String> map = new HashMap<String, String>();
        //        map.put("appSubName", "");
        map.put("appId", "15718d48-41ef-49b0-8fd1-b75e8e6d6171");
        //        map.put("subAppId", "12");
        //        map.put("appSubName", "12");
        System.out.println(umsAppSubMapper.getSubAppByAppId(map));
    }
}
