/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.common.dal.test;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tiaotiaogift.common.dal.DocMapper;
import com.tiaotiaogift.common.mysql.dataobject.Doc;

/**
 * 
 * @author gag
 * @version $Id: DocTest.java, v 0.1 2012-12-12 下午2:08:48 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class DocTest extends BaseDaoTest {

    @Autowired
    private DocMapper docMapper;

    public void testInsert() {
        Doc d = new Doc();
        d.setId(UUID.randomUUID().toString());
        d.setCode("123");
        d.setContent("test");
        d.setTitle("网关");
        d.setType("news");
        docMapper.insert(d);

    }

    @Test
    public void testSelect() {

        List<Doc> list = docMapper.selectByPageId(0);
        System.out.println(list.size());

    }

}
