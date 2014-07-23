package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsUserInfoMapper {

    @Cacheable(value = "orgCache")
    UmsUserInfo selectByPrimaryKey(String id);

    @Log(name = "用户资料维护", comments = "修改用户")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByPrimaryKeySelective(UmsUserInfo record);

    @Log(name = "用户资料维护", comments = "修改用户")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateByPrimaryKey(UmsUserInfo record);

    /**
     *通过组织ID查询UMS_USER_INFO表里的用户信息
     *
     * @param orgId
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsUserInfo> getUsersByOrgId(String orgId);

    /**
     *通过组织ID获得用户的ID
     *
     * @param orgId
     * @return
     */
    @Cacheable(value = "orgCache")
    List<String> getUserIdByOrgId(String orgId);

    /**
     *通过ID获得用户手机号
     *
     * @param id
     * @return
     */
    @Cacheable(value = "orgCache")
    String getPhoneById(String id);

    /**
     * 根据用户名模糊查找，只查找前50条，供选择收件人模糊查询使用
     *
     * @param userName
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsUserInfo> getUsersByUserName(String userName);

    /**
     * 根据用户手机号查找用户
     *
     * @param phone
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsUserInfo> getUsersByPhone(String phone);

    /**
     * 模糊匹配用户手机号查找用户
     *
     * @param recvId
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsUserInfo> getUsersByRecvId(String recvId);

    /**
     * 根据userId查找用户
     *
     * @param userId
     * @return
     */
    @Cacheable(value = "userCache")
    UmsUserInfo getUserByUserId(String userId);

    /**
     * 通过id查找userName
     *
     * @param userId
     * @return
     */
    @Cacheable(value = "orgCache")
    String getUserName(String id);

    /**
     * 条件查询分页显示全部用户信息
     *
     * @return
     */
    List<UmsUserInfo> searchSelectiveByPage(PageSearch ps);

    /**
     * 获得分页记录数量
     *
     * @param umsUserInfo
     * @return
     */
    int getAllUserNum(UmsUserInfo umsUserInfo);

    //    /**
    //     *通过用户Id修改密码
    //     *
    //     * @param map
    //     * @return
    //     */
    //    @Log(name = "用户资料维护", comments = "修改密码")
    //    int updatePassword(Map<String, String> map);

    /**
     *通过用户Id修改是否是部门管理员的属性字段
     *
     * @param map
     * @return
     */
    @Log(name = "权限维护", comments = "修改是否是部门管理员")
    @CacheEvict(value = "orgCache", allEntries = true)
    int updateOrgAdmin(Map<String, String> map);

    /**
     * 通过主键Id查找是否是部门管理员字段
     *
     * @param id
     * @return
     */
    String getOrgAdminById(String id);

    /**
     * 根据用户名模糊查找用户手机号集合
     *
     * @param recvName
     * @return
     */
    @Cacheable(value = "orgCache")
    List<String> getAllUsersByUserName(String recvName);
}