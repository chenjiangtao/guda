/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import net.zoneland.ums.common.dal.UmsUserRoleRelMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: UmsUserRoleTest.java, v 0.1 2012-8-31 上午10:08:09 gag Exp $
 */
public class UmsUserRoleTest extends BaseDaoTest {

    @Autowired
    private UmsUserRoleRelMapper umsUserRoleRelMapper;

    @Test
    public void test() {
        umsUserRoleRelMapper.selectByUserId("1");
    }

}
