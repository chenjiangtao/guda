/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.user.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 类RegForm.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月14日 上午10:23:16
 */
public class RegForm {

    @Size(min = 4, max = 20, message = "用户名长度4到20个字")
    @Pattern(regexp = "^[0-9a-zA-Z]{4,20}$", message = "用户名必须为4~20位的数字或字母")
    private String userName;

    @Size(min = 2, max = 36, message = "邮箱长度不超过36个字符")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "该地址不符合邮箱格式")
    private String email;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String password;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String password2;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
