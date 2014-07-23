package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsContactMapper {

    @Log(name = "联系人维护", comments = "删除联系人")
    @CacheEvict(value = "contactCache", allEntries = true)
    int deleteByPrimaryKey(String id);

    @Log(name = "联系人维护", comments = "新增联系人")
    @CacheEvict(value = "contactCache", allEntries = true)
    int insert(UmsContact record);

    @Log(name = "联系人维护", comments = "新增联系人")
    @CacheEvict(value = "contactCache", allEntries = true)
    int insertSelective(UmsContact record);

    @Cacheable(value = "contactCache")
    UmsContact selectByPrimaryKey(String id);

    @Log(name = "联系人维护", comments = "修改联系人")
    @CacheEvict(value = "contactCache", allEntries = true)
    int updateByPrimaryKeySelective(UmsContact record);

    @Log(name = "联系人维护", comments = "修改联系人")
    @CacheEvict(value = "contactCache", allEntries = true)
    int updateByPrimaryKey(UmsContact record);

    /**
     * 按条件查找我的联系人总数
     * 
     * @param umsContact
     * @return
     */
    int searchAllNum(UmsContact umsContact);

    /**
     * 按条件分页显示我的联系人信息
     * 
     * @param ps
     * @return
     */
    List<UmsContact> searchMyContactByPage(PageSearch ps);

    /**
     * 判断联系人手机号是否存在
     * 
     * @param contactMap
     * @return
     */
    UmsContact searchByUserIdAndPhone(Map<String, String> contactMap);

    /**
     * 查找某个用户的所有联系人
     * 
     * @param userId
     * @return
     */
    @Cacheable(value = "contactCache")
    List<UmsContact> searchByUserId(String userId);

    /**
     * 根据联系人名字模糊查找联系人，只查找前50条，供选择收件人模糊查询使用
     * 
     * @param map
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsContact> getContactsByUserName(Map<String, Object> map);

    /**
     * 根据联系人姓名模糊查询当前用户的联系人手机号
     * 
     * @param params
     * @return
     */
    List<String> getMyContactsByUserName(Map<String, Object> params);
}