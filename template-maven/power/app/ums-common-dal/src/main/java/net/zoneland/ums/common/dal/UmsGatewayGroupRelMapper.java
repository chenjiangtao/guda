package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroupRel;

public interface UmsGatewayGroupRelMapper {
    int deleteByPrimaryKey(String id);

    int insert(UmsGatewayGroupRel record);

    int insertSelective(UmsGatewayGroupRel record);

    UmsGatewayGroupRel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsGatewayGroupRel record);

    int updateByPrimaryKey(UmsGatewayGroupRel record);

    /**
     * 根据传入的网关分组主键ID，在关联表中查出其对应分组的网关信息
     * 
     * @param gatewayGroupId
     * @return
     */
    List<UmsGatewayGroupRel> selectByGatewayGroupId(String gatewayGroupId);

    /**
     * 通过网关分组主键ID删除网关分组关联表相关信息
     * 
     * @param gatewayGroupId
     */
    void deleteByGatewayGroupId(String gatewayGroupId);

    /**
     * 通过网关分组主键ID和网关的主键ID删除所对应的网关分组关联表的数据
     * 
     * @param gatewayMap
     * @return 
     */
    int deleteByGatewayIds(Map<String, String> gatewayMap);
}