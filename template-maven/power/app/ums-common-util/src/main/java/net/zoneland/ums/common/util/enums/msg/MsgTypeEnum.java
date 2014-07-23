/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 * 
 * @author gang
 * @version $Id: MsgTypeEnum.java, v 0.1 2013-3-27 上午10:43:35 gang Exp $
 */
public enum MsgTypeEnum {

    in("in", "上行"), out("out", "下行");

    private String value;
    private String description;

    private MsgTypeEnum(String value, String description) {
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

}
