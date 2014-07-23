/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 * 
 * @author gag
 * @version $Id: MsgStatusIphoneEnum.java, v 0.1 2012-10-16 下午1:02:15 gag Exp $
 */
public enum MsgStatusIphoneEnum {

    init("0", "未读"), /**  */
    read("1", "已读"), /**  */
    del("2", "已删除");
    /**  */

    private String value;
    private String description;

    private MsgStatusIphoneEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

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
        for (MsgStatusIphoneEnum msg : MsgStatusIphoneEnum.values()) {
            if (msg.value.equals(value)) {
                return msg.description;
            }
        }
        return "";
    }

    public static String getNameByValue(String value) {
        MsgStatusIphoneEnum[] msgInStatusEnum = MsgStatusIphoneEnum.values();
        for (int i = 0, len = msgInStatusEnum.length; i < len; ++i) {
            if (msgInStatusEnum[i].value.equals(value)) {
                return msgInStatusEnum[i].description;
            }
        }
        return null;
    }

}
