/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsLockAppMapper;
import net.zoneland.ums.common.dal.dataobject.UmsLockApp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Administrator
 * @version $Id: UmsLockAppTest.java, v 0.1 2013-1-15 下午12:07:15 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsLockAppTest extends BaseDaoTest {

    @Autowired
    private UmsLockAppMapper umsLockAppMapper;

    public void insertTest() {
        UmsLockApp app = new UmsLockApp();
        app.setAppId("1112");
        app.setGmtCreated(new Date());
        String hostName = "172.16.86.103";
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            logger.error("无法获取本地IP", e1);
        }
        app.setHost(hostName);
        umsLockAppMapper.insert(app);
    }

    @Test
    public void deleteTest() {
        String hostName = "172.16.86.103";
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            logger.error("无法获取本地IP", e1);
        }
        Date date = new Date();
        Map<String, Object> lockMap = new HashMap<String, Object>();
        lockMap.put("host", hostName);
        lockMap.put("gmtCreated", date);
        umsLockAppMapper.deleteByHost(lockMap);
    }
}
