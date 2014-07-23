package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsAppInfoMapper {

    @Log(name = "应用维护", comments = "删除应用")
    @CacheEvict(value = "selectAppByAppIdCache", allEntries = true)
    int deleteByPrimaryKey(String id);

    @Log(name = "应用维护", comments = "新增应用")
    @CacheEvict(value = "selectAppByAppIdCache", allEntries = true)
    int insert(UmsAppInfo record);

    UmsAppInfo selectByPrimaryKey(String id);

    @Log(name = "应用维护", comments = "更新应用")
    @CacheEvict(value = "selectAppByAppIdCache", allEntries = true)
    int updateByPrimaryKeySelective(UmsAppInfo record);

    @Log(name = "应用维护", comments = "更新应用")
    @CacheEvict(value = "selectAppByAppIdCache", allEntries = true)
    int updateByPrimaryKey(UmsAppInfo record);

    /**
     * 列出全部应用ID和应用名
     *
     * @return
     */
    List<UmsAppInfo> selectAll();

    /**
     * 查询全部符合条件的记录数量
     *
     * @return
     */
    int searchAllNum(UmsAppInfo umsAppInfo);

    /**
     * 分页显示应用信息
     *
     * @param params 分页条件
     * @return
     */
    List<UmsAppInfo> searchSelectiveByPage(Map<String, Object> params);

    /**
     * 查询条件分页记录数量
     *
     * @param params
     * @return
     */
    int searchAllApp(Map<String, Object> params);

    /**
     * 根据appId查询应用信息
     *
     * @param params
     * @return
     */
    UmsAppInfo selectByAppId(Map<String, Object> params);

    /**
     * 验证应用账户密码
     *
     * @param params
     * @return
     */
    int selectWithAppIdPassword(Map<String, String> params);

    /**
     * 根据appId获取appName
     *
     * @param appId
     * @return
     */
    @Cacheable(value = "selectAppByAppIdCache")
    String getAppNameByAppId(String id);

    /**
     * 通过appId查询应用
     *
     * @param appId
     * @return
     */
    @Cacheable(value = "selectAppByAppIdCache")
    UmsAppInfo selectAppByAppId(String appId);

    @Cacheable(value = "selectAppByAppIdCache")
    UmsAppInfo selectValidAppByAppId(Map<String, Object> params);

    /**
     * 管理员配置应用流量，条件查询分页显示全部应用
     *
     * @param params
     * @return
     */
    List<UmsAppInfo> searchAppByPage(Map<String, Object> params);

    /**
     * 管理员配置应用流量，条件查询记录总数
     *
     * @param params
     * @return
     */
    int getAppNum(Map<String, Object> params);

    /**
     * 修改流量配置
     *
     * @param umsAppInfo
     * @return
     */
    @Log(name = "流量配置", comments = "更新应用流量")
    int updateFlow(UmsAppInfo umsAppInfo);

    /**
     * 根据名称 模糊查询
     * @param appName
     * @return
     */
    List<UmsAppInfo> selectAppLikeName(Map<String, Object> params);

    /**
     *根据ID修改状态
     *
     * @param map
     * @return
     */
    @Log(name = "应用维护", comments = "更新应用状态")
    int updateStatus(Map<String, Object> map);

    /**
     * 通过名字跟应用ID获得应用
     *
     * @param params
     * @return
     */
    List<UmsAppInfo> getAppByNameAndAppId(Map<String, String> params);

    /**
     * 通过名字跟应用ID获得应用管理员所获得的应用
     *
     * @param params
     * @return
     */
    List<UmsAppInfo> getAppAdminAppsByName(Map<String, String> params);

    /**
    * 根据应用名模糊查询应用ID
    *
    * @param params
    * @return
    */
    List<String> getAppIdByAppName(Map<String, Object> params);
}