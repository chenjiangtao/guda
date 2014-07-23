/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.security;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 当前操作上下文
 * @author gag
 * @version $Id: OperationContext.java, v 0.1 2012-5-21 下午1:25:53 gag Exp $
 */
public class OperationContext {

    /** 用户信息 */
    private OperationPrincipal principal;

    /** 当亲角色 */
    private OperationRole[]    authorities;

    /** 是否认证 */
    private boolean            authenticated = false;

    public OperationContext() {

    }

    public OperationContext(OperationPrincipal principal, OperationRole[] authorities) {
        this.principal = principal;
        this.authorities = authorities;
        this.authenticated = true;

    }

    /**
     * Getter method for property <tt>principal</tt>.
     * 
     * @return property value of principal
     */
    public OperationPrincipal getPrincipal() {
        return principal;
    }

    /**
     * Setter method for property <tt>principal</tt>.
     * 
     * @param principal value to be assigned to property principal
     */
    public void setPrincipal(OperationPrincipal principal) {
        this.principal = principal;
    }

    /**
     * Getter method for property <tt>authorities</tt>.
     * 
     * @return property value of authorities
     */
    public OperationRole[] getAuthorities() {
        return authorities;
    }

    /**
     * Setter method for property <tt>authorities</tt>.
     * 
     * @param authorities value to be assigned to property authorities
     */
    public void setAuthorities(OperationRole[] authorities) {
        this.authorities = authorities;
    }

    /**
     * Getter method for property <tt>authenticated</tt>.
     * 
     * @return property value of authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Setter method for property <tt>authenticated</tt>.
     * 
     * @param authenticated value to be assigned to property authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
