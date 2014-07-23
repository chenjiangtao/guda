/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.util.enums;

/**
 * 
 * @author gang
 * @version $Id: ActionEnum.java, v 0.1 2013-5-1 上午10:22:49 gang Exp $
 */
public enum ActionEnum {

    /**  */
    charge("charge", "充值"),
    /**  */
    decrease("decrease", "扣除");

    private String value;
    private String description;

    private ActionEnum(String value, String description) {
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
        ActionEnum[] values = ActionEnum.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
                return values[i].getDescription();
            }
        }
        return "";
    }

}
