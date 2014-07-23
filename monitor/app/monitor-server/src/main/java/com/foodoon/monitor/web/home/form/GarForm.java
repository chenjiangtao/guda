/**

 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.form;

import com.foodoon.monitor.dal.dataobject.Sys;

/**
 * 
 * @author foodoon
 * @version $Id: GarFrom.java, v 0.1 2013-5-30 下午4:26:15 foodoon Exp $
 */
public class GarForm extends Sys {

    private String  comment;

    private Integer valueType;

    private String  host;

    private int     order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
