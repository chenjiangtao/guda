/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 用户信息
 * @author gag
 * @version $Id: OperationPrincipal.java, v 0.1 2012-5-21 下午1:29:13 gag Exp $
 */
public class OperationPrincipal implements Serializable, Principal {

    /**  */
    private static final long   serialVersionUID = -983578979828320117L;

    /**当前登录ID  */
    private String              logonId;

    /** 当前userId */
    private String              userId;

    /** 当前用户名 */
    private String              userName;

    /** 用户邮箱 */
    private String              email;

    /** 用户电话 */
    private String              userPhone;

    /** 当前用户省份 */
    private String              provinceId;

    /** 请求来源IP地址 */
    private String              ip;

    /** 请求来源host */
    private String              hostName;

    private Map<String, Object> attrMap          = new HashMap<String, Object>();

    /**
     * Getter method for property <tt>ip</tt>.
     * 
     * @return property value of ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Setter method for property <tt>ip</tt>.
     * 
     * @param ip value to be assigned to property ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Getter method for property <tt>hostName</tt>.
     * 
     * @return property value of hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Setter method for property <tt>hostName</tt>.
     * 
     * @param hostName value to be assigned to property hostName
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Getter method for property <tt>provinceId</tt>.
     * 
     * @return property value of provinceId
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * Setter method for property <tt>provinceId</tt>.
     * 
     * @param provinceId value to be assigned to property provinceId
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>logonId</tt>.
     * 
     * @return property value of logonId
     */
    public String getLogonId() {
        return logonId;
    }

    /**
     * Setter method for property <tt>logonId</tt>.
     * 
     * @param logonId value to be assigned to property logonId
     */
    public void setLogonId(String logonId) {
        this.logonId = logonId;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>userPhone</tt>.
     * 
     * @return property value of userPhone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * Setter method for property <tt>userPhone</tt>.
     * 
     * @param userPhone value to be assigned to property userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /** 
     * @see java.security.Principal#getName()
     */
    public String getName() {
        return null;
    }

    public Object getAttrVal(String key) {
        return attrMap.get(key);
    }

    public void setAttrMap(String key, Object val) {
        this.attrMap.put(key, val);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
