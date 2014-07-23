/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.gateway.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsGatewayRuleMapper;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.util.enums.GateStateEnum;
import net.zoneland.ums.common.util.page.PageResult;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: GatewayServiceIml.java, v 0.1 2012-8-23 下午4:49:35 gag Exp $
 */
public class GatewayServiceImpl implements GatewayService {

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    @Autowired
    private UmsGatewayRuleMapper umsGatewayRuleMapper;

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#findGateway(java.lang.String)
     */
    public UmsGateWayInfo findGateway(String gatewayId) {
        if (gatewayId == null) {
            return null;
        }
        return umsGateWayInfoMapper.selectByPrimaryKey(gatewayId);

    }

    public PageResult<UmsGateWayInfo> findAllGateway(int pageId) {
        Map<String, Object> map = new HashMap<String, Object>();

        int count = umsGateWayInfoMapper.selectCount();
        if (count < 1) {
            return new PageResult<UmsGateWayInfo>();
        }
        PageResult<UmsGateWayInfo> pageResult = new PageResult<UmsGateWayInfo>(count, pageId);
        map.put("start", pageResult.getFirstrecode());
        map.put("end", pageResult.getEndrecode());
        List<UmsGateWayInfo> gatewayInfolist = umsGateWayInfoMapper.selectAll(map);
        pageResult.setResults(gatewayInfolist);
        return pageResult;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#delGateway(java.lang.String)
     */
    public boolean deleteGateway(String id) {
        if (id == null) {
            return false;
        }
        umsGateWayInfoMapper.deleteByPrimaryKey(id);
        umsGatewayRuleMapper.deleteByGatewayId(id);
        return true;
    }

    public void setUmsGateWayInfoMapper(UmsGateWayInfoMapper umsGateWayInfoMapper) {
        this.umsGateWayInfoMapper = umsGateWayInfoMapper;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#selectAll()
     */
    public List<UmsGateWayInfo> findAll() {
        List<UmsGateWayInfo> list = umsGateWayInfoMapper.findAll();
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#loadValidAll()
     */
    public List<UmsGateWayInfo> loadValidAll() {
        return umsGateWayInfoMapper.loadValidAll("1");
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#insert(net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo)
     */
    public boolean insert(UmsGateWayInfo umsGateWayInfo) {
        if (umsGateWayInfo == null) {
            return false;
        }
        Date date = new Date();
        umsGateWayInfo.setGmtCreated(date);
        umsGateWayInfo.setGmtModified(date);
        umsGateWayInfo.setId(UUID.randomUUID().toString());
        //默认网关都是不可用的
        umsGateWayInfo.setStatus(GateStateEnum.DISABLED.getValue());
        umsGateWayInfo.setGatewayState(GateStateEnum.ENABLED.getValue());
        umsGateWayInfoMapper.insert(umsGateWayInfo);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#update(net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo)
     */
    public boolean update(UmsGateWayInfo umsGateWayInfo) {
        if (umsGateWayInfo == null) {
            return false;
        }
        Date date = new Date();
        umsGateWayInfo.setGmtModified(date);
        umsGateWayInfoMapper.updateByPrimaryKey(umsGateWayInfo);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#updateStatusById(java.lang.String, java.lang.String)
     */
    public boolean updateStatusById(String status, String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("status", status);
        int result = umsGateWayInfoMapper.updateStatusByPrimaryKey(map);
        return result == 1;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayService#updateGatewayStateById(java.lang.String, java.lang.String)
     */
    public boolean updateGatewayStateById(String gatewayState, String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("gatewayState", gatewayState);
        int result = umsGateWayInfoMapper.updateGatewayState(map);
        return result == 1;
    }
}
