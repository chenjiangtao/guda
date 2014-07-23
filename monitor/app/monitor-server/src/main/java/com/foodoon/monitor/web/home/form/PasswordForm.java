/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author foodoon
 * @version $Id: PasswordForm.java, v 0.1 2013-6-7 下午9:48:49 foodoon Exp $
 */
public class PasswordForm {

    @NotEmpty(message = "不能为空")
    private String old;

    @NotEmpty(message = "不能为空")
    private String newPass;

    @NotEmpty(message = "不能为空")
    private String newPassSec;

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPassSec() {
        return newPassSec;
    }

    public void setNewPassSec(String newPassSec) {
        this.newPassSec = newPassSec;
    }

}
