/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 * 
 * @author gang
 * @version $Id: MsgInStatusEnum.java, v 0.1 2012-8-26 下午3:50:58 gang Exp $
 */
public enum MsgInStatusEnum {

    init("0", "初始化"), /**  */
    send("2", "已发送"), /**  */
    success("1", "完成");
    /**  */

    private String value;
    private String description;

    private MsgInStatusEnum(String value, String description) {
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
        for (MsgInStatusEnum msg : MsgInStatusEnum.values()) {
            if (msg.value.equals(value)) {
                return msg.description;
            }
        }
        return "";
    }

    public static String getNameByValue(String value) {
        MsgInStatusEnum[] msgInStatusEnum = MsgInStatusEnum.values();
        for (int i = 0, len = msgInStatusEnum.length; i < len; ++i) {
            if (msgInStatusEnum[i].value.equals(value)) {
                return msgInStatusEnum[i].description;
            }
        }
        return null;
    }

}
