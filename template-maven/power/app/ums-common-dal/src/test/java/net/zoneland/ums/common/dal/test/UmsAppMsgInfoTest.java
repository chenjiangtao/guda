/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author XuFan
 * @version $Id: UmsAppMsgInfoTest.java, v 0.1 Aug 22, 2012 10:11:41 AM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsAppMsgInfoTest extends BaseDaoTest {
    @Autowired
    private UmsMsgOutMapper umsMsgOutMapper;

    @Autowired
    UmsAppInfoMapper        umsAppInfoMapper;

    @Test
    public void testSelectNum() {
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("ums");
        params.put("appIdList", list);
        //params.put("status", value);
        System.out.println(umsMsgOutMapper.searchAppMsgNum(params));

    }

    @Test
    public void testSelectByPage() {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("ums");
        params.put("appIdList", list);
        params.put("searchObj", appMsgInfoBO);
        params.put("recordFirst", 0);
        params.put("recordEnd", 2);
        System.out.println(umsMsgOutMapper.searchAppMsgByPage(params));
    }

    @Test
    public void getAppName() {
        System.out.println(umsAppInfoMapper.getAppNameByAppId("ums"));
    }
}
