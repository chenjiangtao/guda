package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsBlackList;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsBlackListMapper {

    @Log(name = "黑名单维护", comments = "删除黑名单")
    @CacheEvict(value = "blacklistCache", allEntries = true)
    int deleteByPrimaryKey(String id);

    @Log(name = "黑名单维护", comments = "新增黑名单")
    @CacheEvict(value = "blacklistCache", allEntries = true)
    int insertSelective(UmsBlackList record);

    @Log(name = "黑名单维护", comments = "查询黑名单")
    UmsBlackList selectByPrimaryKey(String id);

    /**
     *用于检查黑名单是否存在
     *
     * @param userId
     * @return
     */
    @Log(name = "黑名单维护", comments = "检查黑名单是否存在")
    int getCountByUserId(String userId);

    /**
     * 查看是否有重名的黑名单手机号
     *
     * @param umsBlackList
     * @return
     */
    @Log(name = "黑名单维护", comments = "查询重复黑名单")
    List<UmsBlackList> getUserId(UmsBlackList umsBlackList);

    /**
     * 查看是否有重名的黑名单手机号
     *
     * @param umsBlackList
     * @return
     */
    @Log(name = "黑名单维护", comments = "查询重复黑名单")
    List<UmsBlackList> getByUserIdAndAppId(UmsBlackList umsBlackList);

    /**
     * 分页查找黑名单
     *
     * @param params
     * @return
     */
    @Log(name = "黑名单维护", comments = "分页查询黑名单")
    List<UmsBlackList> searchSelectiveByPage(Map<String, Object> params);

    /**
     * 根据条件查找黑名单个数
     *
     * @param params
     * @return
     */
    @Log(name = "黑名单维护", comments = "查询黑名单")
    int searchNum(Map<String, Object> params);

    /**
     * 通过手机号查找是否已经存在
     *
     * @param params
     * @return
     */
    @Log(name = "黑名单维护", comments = "检查黑名单是否存在")
    int selectByPhone(Map<String, String> params);

    /**
     * 按手机号查询应用名
     *
     * @param phoneNumber
     * @return
     */
    @Log(name = "黑名单维护", comments = "查询黑名单")
    @Cacheable(value = "blacklistCache")
    List<String> selectByPhoneNumber(String phoneNumber);

    /**
     * 删除黑名单
     * @param params
     * @return
     */
    @Log(name = "黑名单维护", comments = "删除黑名单")
    @CacheEvict(value = "blacklistCache", allEntries = true)
    int delBlackList(Map<String, String> params);

    /**
     * 通过AppId删除黑名单
     *
     * @param appId
     * @return
     */
    @Log(name = "黑名单维护", comments = "删除黑名单")
    @CacheEvict(value = "blacklistCache", allEntries = true)
    int deleteBlackListByAppId(String appId);

    /**
     * 通过AppId删除黑名单
     *
     * @param userId
     * @return
     */
    @Log(name = "黑名单维护", comments = "删除黑名单")
    @CacheEvict(value = "blacklistCache", allEntries = true)
    int deleteBlackListByUserId(String userId);

    /**
     * 根据APPID查询黑名单
     *
     * @param appId
     * @return
     */
    @Log(name = "黑名单维护", comments = "查询黑名单")
    List<String> selectByAppId(String appId);

}