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

import net.zoneland.ums.common.dal.UmsFlowLogMapper;
import net.zoneland.ums.common.dal.dataobject.UmsFlowLog;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: UmsFlowLogMapperTest.java, v 0.1 2012-8-27 下午1:35:22 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsFlowLogMapperTest extends BaseDaoTest {

    @Autowired
    private UmsFlowLogMapper umsFlowLogMapper;

    public void testInsert() {
        UmsFlowLog log = new UmsFlowLog();
        log.setId(UUID.randomUUID().toString());
        log.setAppId("124");
        log.setFlowToday(10);
        log.setGmtCreated(new Date());
        umsFlowLogMapper.insert(log);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", "124");
        params.put("gmtCreated", new Date());

    }

    @Test
    public void testSelectByAppId() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", "124");
        params.put("gmtCreated", new Date());
        List<UmsFlowLog> log = umsFlowLogMapper.selectByAppId(params);
        System.out.println(log);
    }

}
