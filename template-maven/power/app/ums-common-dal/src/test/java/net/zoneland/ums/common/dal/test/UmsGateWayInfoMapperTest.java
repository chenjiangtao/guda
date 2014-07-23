/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.List;

import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: UmsGateWayInfoMapperTest.java, v 0.1 2012-8-24 上午10:38:23 gag Exp $
 */
public class UmsGateWayInfoMapperTest extends BaseDaoTest {

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    public void testSelectByGatewayId() {
        umsGateWayInfoMapper.selectByPrimaryKey("11");
    }

    public void testloadAll() {
        umsGateWayInfoMapper.loadValidAll("1");
    }

    @Test
    public void testFindWithType() {

        List<UmsGateWayInfo> cmpp = umsGateWayInfoMapper.findWithType("CMPP30");
        System.out.println(cmpp.size());
        cmpp = umsGateWayInfoMapper.findWithType("CMPP30");
        System.out.println(cmpp.size());

    }

}
