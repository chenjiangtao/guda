/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.dal.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tiaotiaogift.common.dal.MsgOutMapper;

/**
 * 
 * @author gang
 * @version $Id: MsgOutTest.java, v 0.1 2013-5-5 上午10:13:35 gang Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class MsgOutTest extends BaseDaoTest {

    @Autowired
    private MsgOutMapper msgOutMapper;

    @Test
    public void testInsert() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gmtModify", new Date());
        params.put("status", "2");

        List<String> msgIds = new ArrayList<String>();
        msgIds.add("d8f025a5-a9f5-4349-8b8a-88763ac231f2");
        params.put("ids", msgIds);
        int res = msgOutMapper.updateStatusBatch(params);
        System.out.println(res);
    }

    public MsgOutMapper getMsgOutMapper() {
        return msgOutMapper;
    }

    public void setMsgOutMapper(MsgOutMapper msgOutMapper) {
        this.msgOutMapper = msgOutMapper;
    }

}
