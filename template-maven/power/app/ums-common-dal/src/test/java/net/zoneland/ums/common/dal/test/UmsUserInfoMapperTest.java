/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author XuFan
 * @version $Id: UmsUserInfoMapperTest.java, v 0.1 Aug 14, 2012 5:15:19 PM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsUserInfoMapperTest extends BaseDaoTest {

    @Autowired
    private UmsUserInfoMapper umsUserInfoMapper;

    @Autowired
    private UmsAppSubMapper   umsAppSubMapper;

    public void test1() {
        System.out.println(umsUserInfoMapper.getUserName("anonymous"));
    }

    @Test
    public void testgetUserByUserId() {
        long start = System.currentTimeMillis();
        System.out.println(umsUserInfoMapper.getUserByUserId("wangyong"));
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(umsUserInfoMapper.getUserByUserId("wangyong"));
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test2() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("subAppId", "1235");
        params.put("appId", "01d7dca8-0918-4a8a-b813-27f81243a49d");
        System.out.println(umsAppSubMapper.getSubAppName(params));
    }

    public void test3() {
        //UmsUserInfo umsUserInfo = new UmsUserInfo();
        //PageSearch ps = new PageSearch(umsUserInfo, 0, 2);
        // System.out.println(umsUserInfoMapper.searchSelectiveByPage(ps));
    }

    public void test4() {
        UmsUserInfo umsUserInfo = new UmsUserInfo();
        umsUserInfo.setUserName("一");
        System.out.println(umsUserInfoMapper.getAllUserNum(umsUserInfo));
    }

}
