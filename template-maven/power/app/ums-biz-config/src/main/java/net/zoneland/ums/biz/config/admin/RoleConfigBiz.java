/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.biz.config.util.RoleConfigAssignment;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 角色分配接口
 * @author XuFan
 * @version $Id: RoleBiz.java, v 0.1 Aug 28, 2012 4:42:34 PM XuFan Exp $
 */
public interface RoleConfigBiz {
    /**
     * 权限分配显示所有用户
     * 
     * @param umsUserInfo
     * @param curPage
     * @return
     */
    public PageResult<UmsUserInfo> showAllUser(UmsUserInfo umsUserInfo, int curPage);

    /**
     * 获得分页记录总数
     *
     * @param umsUserInfo
     * @return
     */
    public int getAllUserNum(UmsUserInfo umsUserInfo);

    /**
     * 当点击【保存】按钮时，给用户分配具体的角色
     * 
     * @param userId 用户ID
     * @param appIdStr 应用集合
     * @param isAppRole 判断是否是应用管理员角色
     * @param orgAdmin 是否是部门管理员
     * @return
     */
    public AjaxResult giveRole(String userId, String appIdStr, String isAppRole, String orgAdmin,
                               String customOrgName);

    /** 
     * 点击【分配角色】，进入给用户分配角色页面
     * 
     * @param userId 用户ID
     * @return
     */
    public RoleConfigAssignment roleAssignment(String userId);

    /**
     * 点击【分配单位】，进入给用户分配单位页面
     * 
     * @param userId 用户ID
     * @return
     */
    public RoleConfigAssignment areaAssignment(String userId);
}
