/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.filter;

import java.util.List;

import net.zoneland.mvc.runtime.core.security.OperationRole;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gang
 * @version $Id: RoleInfo.java, v 0.1 2012-8-30 上午6:43:51 gang Exp $
 */
public class RoleInfo implements OperationRole {

    /**  */
    private static final long serialVersionUID = -2760555967505202716L;

    private String            roleName;

    private List<String>      appId;

    public RoleInfo() {

    }

    public RoleInfo(String roleName) {
        this.roleName = roleName;
    }

    public RoleInfo(String roleName, List<String> appIds) {
        this.roleName = roleName;
        this.appId = appIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getAppId() {
        return appId;
    }

    public void setAppId(List<String> appId) {
        this.appId = appId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
