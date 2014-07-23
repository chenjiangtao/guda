/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.user;


/**
 * 
 * @author gag
 * @version $Id: RoleNameEnum.java, v 0.1 2012-8-29 下午6:46:24 gag Exp $
 */
public enum RoleNameEnum {

    /**  */
    ADMIN("ROLE_ADMIN", "系统管理员"),
    /**  */
    APP("ROLE_APP", "应用管理员"),
    /**  */
    QUERY("ROLE_QUERY", "短信查询"),
    /**  */
    NORMAL("ROLE_NORMAL", "普通员工");

    private String value;
    private String description;

    private RoleNameEnum(String value, String description) {
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

    public static String getDescriptionByValue(String value) {
        if (value == null || value.equals("")) {
            return "";
        }
        RoleNameEnum[] values = RoleNameEnum.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
                return values[i].getDescription();
            }
        }
        return "";
    }
}
