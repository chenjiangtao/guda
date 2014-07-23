package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsUserRoleRel;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsUserRoleRelMapper {

    @Log(name = "权限配置", comments = "分配权限")
    int insert(UmsUserRoleRel record);

    @Log(name = "权限配置", comments = "修改权限")
    int updateByPrimaryKey(UmsUserRoleRel record);

    List<UmsUserRoleRel> selectByUserId(String userId);

    UmsUserRoleRel selectRoleByUserId(String userId);

    /**
     * 通过user表主键id获取该用户的角色名
     *
     * @param id
     * @return
     */
    String getRoleByUser(String id);

    /**
     * 通过用户信息表主键id获取用户角色表主键id
     *
     * @param id
     * @return
     */
    String getRoleUserId(String id);

    /**
     * 通过当前用户Id获取当前用户的角色名
     *
     * @param userId
     * @return
     */
    String getRoleNameByUserId(String userId);
}