/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsKeywordInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsKeywordInfo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author XuFan
 * @version $Id: UmsKeywordInfoMapperTest.java, v 0.1 Aug 17, 2012 10:47:10 AM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsKeywordInfoMapperTest extends BaseDaoTest {

    @Autowired
    private UmsKeywordInfoMapper umsKeywordInfoMapper;

    @Test
    public void testSerachPageNum() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyword", "req");
        params.put("appId", "testums6");
        System.out.println(umsKeywordInfoMapper.searchAllNum(params));
    }

    @Test
    public void testSerachPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("first", "1");
        params.put("end", "3");
        System.out.println(umsKeywordInfoMapper.searchKeywordByPage(params).get(0));
    }

    public void testInsetKeyword() {
        UmsKeywordInfo record = new UmsKeywordInfo();
        record.setAppId("ums2");
        record.setKeyWord("凤姐");
        record.setId(UUID.randomUUID().toString());
        record.setGmtCreated(new Date());
        record.setGmtModified(new Date());
        System.out.println(umsKeywordInfoMapper.insertSelective(record));
    }

    public void testGetKey() {
        UmsKeywordInfo umsKeywordInfo = new UmsKeywordInfo();
        umsKeywordInfo.setAppId("ums2");
        umsKeywordInfo.setKeyWord("凤姐");
        System.out.println(umsKeywordInfoMapper.getKeyword(umsKeywordInfo));
    }

    public void testSelectByAppId() {
        umsKeywordInfoMapper.selectByAppId("111");
    }
}
