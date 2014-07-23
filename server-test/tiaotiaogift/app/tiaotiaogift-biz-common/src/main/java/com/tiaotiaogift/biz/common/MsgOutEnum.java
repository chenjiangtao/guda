/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common;

/**
 * 
 * @author gag
 * @version $Id: MsgOutEnum.java, v 0.1 2012-12-18 下午2:17:49 gag Exp $
 */
public enum MsgOutEnum {

    init("0", "待发送"), /**  */
    fail("2", "发送失败"), /**  */
    success("1", "发送成功");
    /**  */

    private String value;
    private String description;

    private MsgOutEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获取中文描述
     *
     * @param value
     * @return
     */
    public static String getDescription(String value) {
        for (MsgOutEnum msg : MsgOutEnum.values()) {
            if (msg.value.equals(value)) {
                return msg.description;
            }
        }
        return "";
    }

    public static String getNameByValue(String value) {
        MsgOutEnum[] msgInStatusEnum = MsgOutEnum.values();
        for (int i = 0, len = msgInStatusEnum.length; i < len; ++i) {
            if (msgInStatusEnum[i].value.equals(value)) {
                return msgInStatusEnum[i].description;
            }
        }
        return null;
    }

}
