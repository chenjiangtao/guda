package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsAppSubMapper {

    @Log(name = "应用维护", comments = "删除子应用")
    int deleteByPrimaryKey(String id);

    @Log(name = "应用维护", comments = "新增子应用")
    int insert(UmsAppSub record);

    UmsAppSub selectByPrimaryKey(String id);

    @Log(name = "应用维护", comments = "更新子应用")
    int updateByPrimaryKeySelective(UmsAppSub record);

    @Log(name = "应用维护", comments = "更新子应用")
    int updateByPrimaryKey(UmsAppSub record);

    /**
     * 根据appId 查询子应用
     *
     * @param appId
     * @return
     */
    List<UmsAppSub> selectAllSubApp(String appId);

    /**
     * 通过子应用supAppId和父应用appId获取子应用名称
     *
     * @param params
     * @return
     */
    String getSubAppName(Map<String, Object> params);

    /**
     *分页查询子应用
     *
     * @param map
     * @return
     */
    List<UmsAppSub> searchSubByPage(Map<String, Object> map);

    /**
     *获得子应用个数
     *
     * @param appId
     * @return
     */
    int getCountByAppId(String appId);

    /**
     *通过subAppId查询子应用
     *
     * @param subAppId
     * @return
     */
    UmsAppSub selectBySubAppId(Map<String, Object> map);

    /**
     * 通过AppId删除相关子应用
     *
     * @param appId
     * @return
     */
    @Log(name = "应用维护", comments = "删除子应用")
    int deleteByAppId(String appId);

    /**
     * 根据父应用ID获得子应用集合
     * 
     * @param params
     * @return
     */
    List<UmsAppSub> getSubAppByAppId(Map<String, String> params);
}