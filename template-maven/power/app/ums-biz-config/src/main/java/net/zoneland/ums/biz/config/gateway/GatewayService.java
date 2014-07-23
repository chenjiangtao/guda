/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.gateway;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 网关连接信息业务类
 * @author gag
 * @version $Id: GatewayService.java, v 0.1 2012-8-23 下午4:17:05 gag Exp $
 */
public interface GatewayService {

    /**
     * 根据网关ID查找网关
     * @param gatewayId 网关ID
     * @return
     */
    UmsGateWayInfo findGateway(String gatewayId);

    /**
     *添加网关信息
     *
     * @param umsGateWayInfo
     * @return
     */
    boolean insert(UmsGateWayInfo umsGateWayInfo);

    /**
     *更新网关信息
     *
     * @param umsGateWayInfo
     * @return
     */
    boolean update(UmsGateWayInfo umsGateWayInfo);

    /**
     *分页查询网关信息
     *
     * @param pageId 第几页
     * @param pageSize 页面显示个数
     * @return
     */
    public PageResult<UmsGateWayInfo> findAllGateway(int pageId);

    /**
     *根据网关ID删除对应网关
     *
     * @param id 网关ID
     * @return
     */
    public boolean deleteGateway(String id);

    /**
     *查询所有网关信息
     *
     * @return
     */
    public List<UmsGateWayInfo> findAll();

    /**
     * 查询所有网关启用的
     * @return
     */
    List<UmsGateWayInfo> loadValidAll();

    boolean updateStatusById(String status, String id);

    boolean updateGatewayStateById(String gatewayState, String id);

}
