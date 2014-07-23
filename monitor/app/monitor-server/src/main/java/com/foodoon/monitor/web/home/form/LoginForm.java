/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author foodoon
 * @version $Id: LoginForm.java, v 0.1 2013-6-5 下午10:02:34 foodoon Exp $
 */
public class LoginForm {

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;

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

}
