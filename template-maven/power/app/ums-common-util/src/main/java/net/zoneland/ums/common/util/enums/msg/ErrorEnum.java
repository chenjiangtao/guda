/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.enums.msg;

/**
 *
 * @author wangyong
 * @version $Id: ErrorEnum.java, v 0.1 2012-8-14 下午9:57:27 wangyong Exp $
 */
public enum ErrorEnum {
    cannot_find_receive("cannot_find_receive", "无法找到接收方相关信息"), is_blacklist("is_blacklist",
                                                                            "发送方在黑名单中"), include_keyword(
                                                                                                         "include_keyword",
                                                                                                         "内容包含非法关键词"), is_overflow(
                                                                                                                                   "is_overflow",
                                                                                                                                   "流量不够");

    private String description;
    private String code;

    private ErrorEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * Getter method for property <tt>code</tt>.
    *
    * @return property value of code
    */
    public String getCode() {
        return code;
    }

    /**
    * Setter method for property <tt>code</tt>.
    *
    * @param code value to be assigned to property code
    */
    public void setCode(String code) {
        this.code = code;
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
    * Setter method for property <tt>description</tt>.
    *
    * @param description value to be assigned to property description
    */
    public void setDescription(String description) {
        this.description = description;
    }

}
