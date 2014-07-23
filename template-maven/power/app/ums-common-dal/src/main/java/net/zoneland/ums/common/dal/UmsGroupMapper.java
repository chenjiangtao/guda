package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.bo.UmsGroupBo;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsGroupMapper {

    @Log(name = "群组维护", comments = "删除群组")
    @CacheEvict(value = "orgCache", allEntries = true)
    int deleteByPrimaryKey(String id);

    @Log(name = "群组维护", comments = "新增群组")
    @CacheEvict(value = "orgCache", allEntries = true)
    int insert(UmsGroup record);

    UmsGroup selectByPrimaryKey(String id);

    @Log(name = "群组维护", comments = "修改群组")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByPrimaryKeySelective(UmsGroup record);

    @Log(name = "群组维护", comments = "修改群组")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByPrimaryKey(UmsGroup record);

    /**
     *这个方方法是通过用户查询其所拥有的群组
     *
     * @param userId
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsGroup> getGroupsByUserId(String userId);

    /**
     *通过群组名字模糊查找群组
     *
     * @param groupName
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsGroup> getGroupsByGroupName(Map<String, Object> map);

    /**
     *通过用户的Id与群组名字寻找群组
     *
     * @param map
     * @return
     */
    UmsGroup findGroupByNameAndUserId(Map<String, Object> map);

    /**
     * 分页查询我的群组列表
     *
     * @param pagesearch
     * @return
     */
    List<UmsGroup> searchMyGroupByPage(PageSearch pagesearch);

    int searchAllNum(UmsGroupBo bo);
}