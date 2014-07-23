/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroupRel;
import net.zoneland.ums.common.dal.dataobject.ZTree;

/**
 * 网关分组接口
 * 
 * @author yangjuanying
 * @version $Id: GatewayGroupBiz.java, v 0.1 2013-1-6 上午10:13:54 yangjuanying Exp $
 */
public interface GatewayGroupBiz {

    /**
     * 查询网关分组信息
     * 
     * @return
     */
    List<ZTree> queryGatewayGroupForSelector();

    /**
     * 根据传入的网关分组主键ID，在关联表中查出其对应分组的网关信息，并放入zTree
     * 
     * @param gatewayGroupId
     * @return
     */
    List<ZTree> queryGatewayInfosByGatewayGroupId(String gatewayGroupId);

    /**
     * 点击“添加网关分组”按钮时往数据库中插入一个新的网关分组
     * 
     * @return 
     */
    UmsGatewayGroup createNewGatewayGroup();

    /**
     * 更新网关分组名
     * 
     * @param gatewayGroupId
     * @param gatewayGroupName
     * @return
     */
    boolean updateByGatewayGroupName(String gatewayGroupId, String gatewayGroupName);

    /**
     * 通过网关分组主键ID删除整个网关分组(包括删除其关联表的数据)
     * 
     * @param gatewayGroupId
     */
    void delGatewayGroupById(String gatewayGroupId);

    /**
     * 在网关分组中添加网关，更新相关关联表信息
     * 
     * @param gatewayGroupId
     * @param gatewayIdStr
     * @return
     */
    boolean saveGatewayGroup(String gatewayGroupId, String gatewayIdStr);

    /**
     * 通过网关分组主键ID和网关的主键ID删除所对应的网关分组关联表的数据
     * 
     * @param gatewayId
     * @param gatewayGroupId
     */
    boolean delGatewayGroupRelByIds(String gatewayId, String gatewayGroupId);

    /**
     * 根据传入的网关分组主键ID，在关联表中查出其对应的关联表信息
     * 
     * @param gatewayGroupId
     * @return
     */
    List<UmsGatewayGroupRel> queryByGatewayGroupId(String gatewayGroupId);

    /**
     * 根据传入的网关分组主键ID，在关联表中查出其对应分组的网关信息
     * 
     * @param id
     * @return
     */
    List<UmsGateWayInfo> queryGatewayInfoById(String id);

    /**
     * 查询所有网关分组信息
     * 
     * @return
     */
    List<UmsGatewayGroup> findAll();

    /**
     * 根据主键ID找出对应的网关分组
     * 
     * @param gatewayId
     * @return
     */
    UmsGatewayGroup findGatewayGroup(String gatewayId);

}
