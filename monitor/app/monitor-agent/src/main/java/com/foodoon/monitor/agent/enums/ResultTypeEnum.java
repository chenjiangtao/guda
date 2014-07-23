/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.enums;

/**
 * 
 * @author foodoon
 * @version $Id: ResultTypeEnum.java, v 0.1 2013-5-25 下午6:52:44 foodoon Exp $
 */
public enum ResultTypeEnum {

    /**  */
    intVal("int"),
    /**  */
    multiVal("multi-value"),

    /**  */
    stringVal("string"),
    /**  */
    floatVal("float");

    private String value;

    private ResultTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ResultTypeEnum getByValue(String value) {
        if (value == null || value.equals("")) {
            return null;
        }
        ResultTypeEnum[] values = ResultTypeEnum.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
                return values[i];
            }
        }
        return null;
    }

}
