/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.foodoon.monitor.agent.enums.ResultTypeEnum;

/**
 * 
 * @author gang
 * @version $Id: SqlNode.java, v 0.1 2013-4-20 下午1:50:46 gang Exp $
 */
public class SqlNode {

    private String         sql;

    private String         interval;

    private String         key;

    private ResultTypeEnum resultType;

    private String         suffixColumn;

    private String         valueColumn;

    private int            order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    private Map<String, String> map = new HashMap<String, String>();

    public void put(String key, String val) {
        map.put(key, val);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ResultTypeEnum getResultType() {
        return resultType;
    }

    public void setResultType(ResultTypeEnum resultType) {
        this.resultType = resultType;
    }

    public String getSuffixColumn() {
        return suffixColumn;
    }

    public void setSuffixColumn(String suffixColumn) {
        this.suffixColumn = suffixColumn;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

}
