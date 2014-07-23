package com.foodoon.monitor.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Sys {
    private String  id;

    private String  ip;

    private String  k;

    private Float   val;

    private Date    gmtCreated;

    private String  valText;

    private Integer valueType;

    private Integer isWarn;

    public Integer getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(Integer isWarn) {
        this.isWarn = isWarn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k == null ? null : k.trim();
    }

    public Float getVal() {
        return val;
    }

    public void setVal(Float val) {
        this.val = val;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getValText() {
        return valText;
    }

    public void setValText(String valText) {
        this.valText = valText == null ? null : valText.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }
}