/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.user;

/**
 * 
 * @author yangjuanying
 * @version $Id: UserOrgAdmin.java, v 0.1 2012-9-14 下午1:19:01 yangjuanying Exp $
 */
public enum UserOrgAdmin {

    /**  */
    ORG_ADMIN("1", "部门管理员");

    private String value;
    private String description;

    private UserOrgAdmin(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
