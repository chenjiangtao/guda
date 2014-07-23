package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsGateWayInfoMapper {

    @Log(name = "网关配置", comments = "删除网关")
    @CacheEvict(value = "GatewayInfo", allEntries = true)
    int deleteByPrimaryKey(String id);

    @Log(name = "网关配置", comments = "新增网关")
    @CacheEvict(value = "GatewayInfo", allEntries = true)
    int insert(UmsGateWayInfo record);

    @Cacheable(value = "GatewayInfo")
    UmsGateWayInfo selectByPrimaryKey(String id);

    @Log(name = "网关配置", comments = "修改网关")
    @CacheEvict(value = "GatewayInfo", allEntries = true)
    int updateByPrimaryKey(UmsGateWayInfo record);

    @Log(name = "网关配置", comments = "修改网关状态")
    @CacheEvict(value = "GatewayInfo", allEntries = true)
    int updateStatusByPrimaryKey(Map<String, String> map);

    //更新网关启用的状态
    int updateGatewayState(Map<String, String> map);

    /**
     *分页查询网关信息
     *
     * @param map 包括地起始页及最后页
     * @return
     */
    List<UmsGateWayInfo> selectAll(Map<String, Object> map);

    /**
     *获取网关个数
     *
     * @return
     */
    int selectCount();

    /**
     * 查询所有网关信息
     *
     * @return
     */
    List<UmsGateWayInfo> findAll();

    List<UmsGateWayInfo> loadValidAll(String status);

    /**
     * 根据网关类型查找可用的网关
     * @param type
     * @return
     */
    @Cacheable(value = "GatewayInfo")
    List<UmsGateWayInfo> findWithType(String type);

    @Cacheable(value = "GatewayInfo")
    List<UmsGateWayInfo> findWithParam(Map<String, Object> map);

    UmsGateWayInfo findOneValid();

    /**
     * 根据网关类型查找所有的网关
     * @param type
     * @return
     */
    @Cacheable(value = "GatewayInfo")
    List<UmsGateWayInfo> findAllByType(String type);

}