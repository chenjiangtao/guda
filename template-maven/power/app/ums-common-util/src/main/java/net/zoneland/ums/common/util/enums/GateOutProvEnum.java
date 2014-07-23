/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author gag
 * @version $Id: GateOutProvEnum.java, v 0.1 2012-12-20 下午12:47:56 gag Exp $
 */
public enum GateOutProvEnum {
    /**  */
    can("1", "支持"),
    /**  */
    cannot("0", "不支持");

    private String value;
    private String description;

    private GateOutProvEnum(String value, String description) {
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

    public static String getDescription(String value) {
        for (GateOutProvEnum msg : GateOutProvEnum.values()) {
            if (msg.value.equals(value)) {
                return msg.description;
            }
        }
        return "";
    }

    public static String getNameByValue(String value) {
        GateOutProvEnum[] msgInStatusEnum = GateOutProvEnum.values();
        for (int i = 0, len = msgInStatusEnum.length; i < len; ++i) {
            if (msgInStatusEnum[i].value.equals(value)) {
                return msgInStatusEnum[i].description;
            }
        }
        return null;
    }

    /**
     * 根据类型获取各大运营商enum
     * @param type
     * @return
     */
    public static List<GateOutProvEnum> getEnumByType(String type) {
        List<GateOutProvEnum> list = new ArrayList<GateOutProvEnum>();
        if (type != null && !"".equals(type)) {
            GateOutProvEnum[] msgInStatusEnum = GateOutProvEnum.values();
            for (int i = 0; i < msgInStatusEnum.length; i++) {
                if (msgInStatusEnum[i].getValue().startsWith(type.toUpperCase())) {
                    list.add(msgInStatusEnum[i]);
                }
            }
        }

        return list;
    }

}
