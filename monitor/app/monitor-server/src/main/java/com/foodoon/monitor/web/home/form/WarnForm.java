/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author gang
 * @version $Id: WarnForm.java, v 0.1 2013-4-19 下午9:55:09 gang Exp $
 */
public class WarnForm {

    private String id;

    @NotEmpty(message = "ip不能为空")
    private String ip;

    @NotEmpty(message = "监控属性不能为空")
    private String k;

    @NotEmpty(message = "监控条件不能为空")
    private String cond;

    @NotEmpty(message = "监控值不能为空")
    private String val;

    private String email;

    private String emailVal;

    private String phone;

    private String phoneVal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVal() {
        return emailVal;
    }

    public void setEmailVal(String emailVal) {
        this.emailVal = emailVal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneVal() {
        return phoneVal;
    }

    public void setPhoneVal(String phoneVal) {
        this.phoneVal = phoneVal;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
