/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gag
 * @version $Id: RegForm.java, v 0.1 2012-12-14 下午12:57:40 gag Exp $
 */
public class RegForm extends BaseForm {

    @Size(min = 4, max = 20, message = "用户名长度4到20个字")
    @Pattern(regexp = "^[0-9a-zA-Z]{4,20}$", message = "用户名必须为4~20位的数字或字母")
    private String userName;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String password;

    private String secPassword;

    @Pattern(regexp = "^[1][345678][0-9]{9}$", message = "手机格式不正确")
    private String phone;

    @Size(min = 2, max = 36, message = "邮箱长度不超过36个字符")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "该地址不符合邮箱格式")
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
