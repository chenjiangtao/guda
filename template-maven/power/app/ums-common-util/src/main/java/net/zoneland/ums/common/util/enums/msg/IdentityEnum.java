/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 * 
 * @author ypz
 * @version $Id: IdentityEnum.java, v 0.1 2012-9-8 下午02:29:43 ypz Exp $
 */
public enum IdentityEnum {
    person("0","个人"),org("1","部门");
    
    private String value;
    private String description;

    private IdentityEnum(String value, String description) {
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
