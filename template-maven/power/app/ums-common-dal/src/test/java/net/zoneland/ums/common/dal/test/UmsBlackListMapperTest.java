/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsBlackListMapper;
import net.zoneland.ums.common.dal.dataobject.UmsBlackList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author XuFan
 * @version $Id: UmsBlackListMapperTest.java, v 0.1 Aug 20, 2012 2:07:41 PM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsBlackListMapperTest extends BaseDaoTest {

    @Autowired
    private UmsBlackListMapper umsBlackListMapper;

    public void testSearchPage() {
        UmsBlackList record = new UmsBlackList();
        record.setId(UUID.randomUUID().toString());
        record.setUserId("13857981393");
        record.setAppId("ums");
        record.setGmtCreated(new Date());
        record.setGmtModified(new Date());
        System.out.println(umsBlackListMapper.insertSelective(record));
    }

    public void testselectByPhoneNumber() {
        List<String> list = umsBlackListMapper.selectByPhoneNumber("13588754574");
        System.out.println(list.size());
    }

    @Test
    public void testDel() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phoneNumber", "13455879954");
        params.put("appId", "1000");
        Integer i = umsBlackListMapper.delBlackList(params);
        System.out.println(i);
    }
}
