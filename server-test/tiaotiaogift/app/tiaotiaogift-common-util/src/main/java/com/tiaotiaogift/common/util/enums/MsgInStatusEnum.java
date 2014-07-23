/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.util.enums;

/**
 * 
 * @author gang
 * @version $Id: MsgInStatusEnum.java, v 0.1 2013-5-9 上午11:36:05 gang Exp $
 */
public enum MsgInStatusEnum {

    /**  */
    init("0", "初始化"),
    /**  */
    success("1", "取走");

    private String value;
    private String description;

    private MsgInStatusEnum(String value, String description) {
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
        MsgInStatusEnum[] values = MsgInStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
                return values[i].getDescription();
            }
        }
        return "";
    }

}
