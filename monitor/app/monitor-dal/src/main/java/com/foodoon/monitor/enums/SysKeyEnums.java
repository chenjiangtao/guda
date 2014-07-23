/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.enums;

/**
 * 
 * @author gang
 * @version $Id: SysEnums.java, v 0.1 2013-4-18 下午2:58:40 gang Exp $
 */
public enum SysKeyEnums {

    /**  */
    cpu("cpu", "cpu"),
    /**  */
    mem("mem", "内存"),
    /**  */
    io("io", "磁盘io"),
    /**  */
    net("net", "网络"),
    /**  */
    load("load", "负载");

    private String value;
    private String desc;

    private SysKeyEnums(String value, String description) {
        this.value = value;
        this.desc = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getDescByValue(String value) {
        if (value == null || value.equals("")) {
            return "";
        }
        SysKeyEnums[] values = SysKeyEnums.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
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
