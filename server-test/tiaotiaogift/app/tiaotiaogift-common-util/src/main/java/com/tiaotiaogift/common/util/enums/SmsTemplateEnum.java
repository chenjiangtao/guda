/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.util.enums;

/**
 * 
 * @author gang
 * @version $Id: SmsTemplateEnum.java, v 0.1 2013-4-27 上午10:37:56 gang Exp $
 */
public enum SmsTemplateEnum {

    /**  */
    normal("1", "普通短信"),
    /**  */
    taobao("2", "淘宝");

    private String value;
    private String description;

    private SmsTemplateEnum(String value, String description) {
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
        SmsTemplateEnum[] values = SmsTemplateEnum.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
                return values[i].getDescription();
            }
        }
        return "";
    }

}
