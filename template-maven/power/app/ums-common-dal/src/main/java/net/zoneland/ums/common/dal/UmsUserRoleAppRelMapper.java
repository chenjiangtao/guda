package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsUserRoleAppRel;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsUserRoleAppRelMapper {

    @Log(name = "权限配置", comments = "分配权限")
    int insert(UmsUserRoleAppRel record);

    /**
     * 查询该应用管理员管理哪几个应用
     *
     * @param roleUserRelId
     * @return
     */
    List<String> searchAllApp(String roleUserRelId);

    /**
     * 根据角色用户关联ID删除角色用户关联表信息
     *
     * @param roleUserRelId
     * @return
     */
    int deleteByRoleUserRelId(String roleUserRelId);

    /**
     * 在应用维护下删除应用时同时要删除应用管理员所能管理的该应用的用户角色关联表的记录
     * 
     * @param id
     */
    void deleteByAppId(String id);
}