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

import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: UmsMsgInMapperTest.java, v 0.1 2012-8-25 下午3:40:29 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsMsgInMapperTest extends BaseDaoTest {

    @Autowired
    private UmsMsgInMapper umsMsgInMapper;

    public void testInsert() {
        UmsMsgIn in = new UmsMsgIn();
        in.setId(UUID.randomUUID().toString());
        in.setAppId("1000");
        in.setBatchNo("1119");
        in.setSerialNo("222");
        in.setContent("测试");
        in.setDocount(1);
        in.setGmtCreated(new Date());
        in.setGmtModified(new Date());
        in.setAck(1);
        in.setRecvId("13588754577");
        in.setSendId("13890901212");
        in.setStatus("0");
        in.setMsgType(15);
        umsMsgInMapper.insert(in);
    }

    public void testSelectByAppWithLimit() {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("appId", "1001");
        params.put("subAppId", null);
        params.put("maxCount", 2);
        List<UmsMsgIn> list = umsMsgInMapper.selectByAppWithLimit(params);
        System.out.println(list.size());
    }
}
