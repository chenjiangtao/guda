/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author wangyong
 * @version $Id: ReceiveUser.java, v 0.1 2012-8-19 上午10:41:53 wangyong Exp $
 */
public class ReceiveUser {
    //当输入的用户的话,id值就是手机号，如果是群组和组织的话则是id
    private String id;
    private String name;

    public ReceiveUser() {
        super();
    }

    /**
     * @param id
     * @param name
     */
    public ReceiveUser(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
