package com.foodoon.monitor.dal.dataobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Config {
    private String id;

    private String agentId;

    private String sqlType;

    private String sqlUrl;

    private String sqlPassword;

    private String sqlSchema;

    private String k;

    private String warnCond;

    private Float  warnVal;

    private String warnPhone;

    private String warnEmail;

    private String sqlText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType == null ? null : sqlType.trim();
    }

    public String getSqlUrl() {
        return sqlUrl;
    }

    public void setSqlUrl(String sqlUrl) {
        this.sqlUrl = sqlUrl == null ? null : sqlUrl.trim();
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public void setSqlPassword(String sqlPassword) {
        this.sqlPassword = sqlPassword == null ? null : sqlPassword.trim();
    }

    public String getSqlSchema() {
        return sqlSchema;
    }

    public void setSqlSchema(String sqlSchema) {
        this.sqlSchema = sqlSchema == null ? null : sqlSchema.trim();
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k == null ? null : k.trim();
    }

    public String getWarnCond() {
        return warnCond;
    }

    public void setWarnCond(String warnCond) {
        this.warnCond = warnCond == null ? null : warnCond.trim();
    }

    public Float getWarnVal() {
        return warnVal;
    }

    public void setWarnVal(Float warnVal) {
        this.warnVal = warnVal;
    }

    public String getWarnPhone() {
        return warnPhone;
    }

    public void setWarnPhone(String warnPhone) {
        this.warnPhone = warnPhone == null ? null : warnPhone.trim();
    }

    public String getWarnEmail() {
        return warnEmail;
    }

    public void setWarnEmail(String warnEmail) {
        this.warnEmail = warnEmail == null ? null : warnEmail.trim();
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText == null ? null : sqlText.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}