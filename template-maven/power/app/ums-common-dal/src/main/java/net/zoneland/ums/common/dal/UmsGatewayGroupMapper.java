package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroup;

public interface UmsGatewayGroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(UmsGatewayGroup record);

    int insertSelective(UmsGatewayGroup record);

    UmsGatewayGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsGatewayGroup record);

    int updateByPrimaryKey(UmsGatewayGroup record);

    /**
     * 查询所有的网关分组
     * 
     * @return
     */
    List<UmsGatewayGroup> selectAllGatewayGroup();
}