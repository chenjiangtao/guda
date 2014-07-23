/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.enums;

/**
 * 
 * @author foodoon
 * @version $Id: ValueTypeEnums.java, v 0.1 2013-5-29 下午9:58:29 foodoon Exp $
 */
public enum ValueTypeEnums {

    /**  */
    serverFloat(1, "server float"),

    server(4, "server"),
    /**  */
    dbFloat(3, "db float"),
    /**  */
    db(2, "db");

    private int    value;
    private String desc;

    private ValueTypeEnums(int value, String description) {
        this.value = value;
        this.desc = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static String getDescByValue(int value) {
        ValueTypeEnums[] values = ValueTypeEnums.values();
        for (int i = 0; i < values.length; i++) {
            if (value == values[i].getValue()) {
                return values[i].getDesc();
            }
        }
        return "";
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
