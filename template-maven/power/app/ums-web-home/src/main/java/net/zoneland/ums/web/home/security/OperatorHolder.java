/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.security;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationRole;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.util.constants.LoginInfoConstants;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.enums.user.UserOrgAdmin;

/**
 *
 * @author gag
 * @version $Id: OperatorHolder.java, v 0.1 2012-8-30 下午1:13:41 gag Exp $
 */
public class OperatorHolder {

    /**
     * 判断当前登录角色是否为管理员
     * @return
     */
    public static boolean isAdmin() {
        OperationRole[] roles = OperationContextHolder.getOperationContext().getAuthorities();
        if (roles != null && roles.length > 0) {
            for (int i = 0, len = roles.length; i < len; ++i) {
                RoleInfo info = (RoleInfo) roles[i];
                if (RoleNameEnum.ADMIN.getValue().equals(info.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前登录角色是否为应用管理员
     * @return
     */
    public static boolean isAppAdmin() {
        OperationRole[] roles = OperationContextHolder.getOperationContext().getAuthorities();
        if (roles != null && roles.length > 0) {
            for (int i = 0, len = roles.length; i < len; ++i) {
                RoleInfo info = (RoleInfo) roles[i];
                if (RoleNameEnum.APP.getValue().equals(info.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前登录角色是否为普通用户
     * @return
     */
    public static boolean isNormal() {
        OperationRole[] roles = OperationContextHolder.getOperationContext().getAuthorities();
        if (roles != null && roles.length > 0) {
            for (int i = 0, len = roles.length; i < len; ++i) {
                RoleInfo info = (RoleInfo) roles[i];
                if (RoleNameEnum.NORMAL.getValue().equals(info.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前登录角色是否为查询短信角色
     * @return
     */
    public static boolean isQuery() {
        OperationRole[] roles = OperationContextHolder.getOperationContext().getAuthorities();
        if (roles != null && roles.length > 0) {
            for (int i = 0, len = roles.length; i < len; ++i) {
                RoleInfo info = (RoleInfo) roles[i];
                if (RoleNameEnum.QUERY.getValue().equals(info.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前登录角色是否为部门负责人
     * @return
     */
    public static boolean isOrgAdmin() {
        Object obj = OperationContextHolder.getOperationContext().getPrincipal()
            .getAttrVal(LoginInfoConstants.IS_ORG_ADMIN_ATTR);
        if (obj != null) {
            if (UserOrgAdmin.ORG_ADMIN.getValue().equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }
}
