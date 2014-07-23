/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 *
 * @author wangyong
 * @version $Id: MsgInfoStatusEnum.java, v 0.1 2012-8-14 上午9:57:42 wangyong Exp $
 */
public enum MsgInfoStatusEnum {
    init("0", "初始化"), /**  */
    ready("2", "已发送到队列"), /**  */
    success("1", "发送成功"), /**  */
    failure("3", "发送失败"), /**  */
    wait("5", "等待定时发送"), /**  */
    expire("6", "消息过期"), error("9", "接收方有误"), /**  */
    refuse("8", "接收方不接收该消息"), /**  */
    unkown_error("7", "其他错误");

    private String value;
    private String description;

    private MsgInfoStatusEnum(String value, String description) {
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
        for (MsgInfoStatusEnum msg : MsgInfoStatusEnum.values()) {
            if (msg.value.equals(value)) {
                return msg.description;
            }
        }
        return "";
    }

    public static String getNameByValue(String value) {
        MsgInfoStatusEnum[] msgInfoStatusEnum = MsgInfoStatusEnum.values();
        for (int i = 0, len = msgInfoStatusEnum.length; i < len; ++i) {
            if (msgInfoStatusEnum[i].value.equals(value)) {
                return msgInfoStatusEnum[i].description;
            }
        }
        return null;
    }

    public static String getValueByName(String name) {
        MsgInfoStatusEnum[] msgInfoStatusEnum = MsgInfoStatusEnum.values();
        for (int i = 0, len = msgInfoStatusEnum.length; i < len; ++i) {
            if (msgInfoStatusEnum[i].description.equals(name)) {
                return msgInfoStatusEnum[i].value;
            }
        }
        return null;
    }
}
