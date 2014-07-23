/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.user.form;

import javax.validation.constraints.Size;

/**
 * 类ResetPassForm.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月14日 下午1:37:02
 */
public class ResetPassForm {

    private String code;

    private String u;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String password;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String password2;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
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
