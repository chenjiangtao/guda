package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsGroupUserRelMapper {

    @Log(name = "群组关联记录维护", comments = "新增群组关联关系")
    @CacheEvict(value = "orgCache", allEntries = true)
    int insert(UmsGroupUserRel record);

    @Log(name = "群组关联记录维护", comments = "新增群组关联关系")
    @CacheEvict(value = "orgCache", allEntries = true)
    int insertSelective(UmsGroupUserRel record);

    UmsGroupUserRel selectByPrimaryKey(String id);

    @Log(name = "群组关联记录维护", comments = "更新群组关联关系")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByPrimaryKey(UmsGroupUserRel record);

    @Log(name = "群组关联记录维护", comments = "更新群组关联关系")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByPrimaryKeySelective(UmsGroupUserRel record);

    /**
     * 通过群组查询用户的信息
     *
     * @param groupId
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsGroupUserRel> selectUserByGroupId(String groupId);

    /**
     *通过群组获得userId
     *
     * @param groupId
     * @return
     */
    List<String> selectUserDescByGroupId(String groupId);

    /**
     *通过备注名称获得用户信息
     *
     * @param name
     * @return
     */
    List<UmsGroupUserRel> getUserByName(String name);

    @Log(name = "群组关联记录维护", comments = "删除群组关联关系")
    @CacheEvict(value = "orgCache", allEntries = true)
    int deleteByGroupId(String groupId);

    /**
     * 根据groupId和UmsGroupUserRel表的主键ID删除群组成员
     *
     * @param memberId
     * @return
     */
    @Log(name = "群组关联记录维护", comments = "删除群组成员")
    @CacheEvict(value = "orgCache", allEntries = true)
    int deleteByPrimaryKey(String memberId);

    /**
     * 根据我的联系人主键ID删除群组关联表中的某个联系人
     * 
     * @param map
     * @return
     */
    @CacheEvict(value = "orgCache", allEntries = true)
    int deleteByContactId(Map<String, Object> map);

    /**
     * 当更新我的联系人时，同事更新群组关联中的我的联系人信息
     * 
     * @param map
     * @return
     */
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByGroupIds(Map<String, Object> map);
}