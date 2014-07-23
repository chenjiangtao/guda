/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 *
 * @author gag
 * @version $Id: AckEnum.java, v 0.1 2012-8-22 下午4:57:11 gag Exp $
 */
public enum AckEnum {

    no_response("0", "不需要回执回复"), report("1", "需要回执"), reply("2", "需要回复"), reportAndReply("3",
                                                                                         "回执且回复");

    private String value;
    private String description;

    private AckEnum(String value, String description) {
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
