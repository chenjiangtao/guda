/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.personal.form;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gang
 * @version $Id: PasswordForm.java, v 0.1 2012-12-16 上午10:49:11 gang Exp $
 */
public class PasswordForm {

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String oldPassword;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String password;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String secPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecPassword() {
        return secPassword;
    }

    public void setSecPassword(String secPassword) {
        this.secPassword = secPassword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
