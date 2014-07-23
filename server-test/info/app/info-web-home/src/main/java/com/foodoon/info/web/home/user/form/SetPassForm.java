/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home.user.form;

import javax.validation.constraints.Size;

/**
 * 
 * @author foodoon
 * @version $Id: SetPassForm.java, v 0.1 2013年10月19日 下午12:06:59 foodoon Exp $
 */
public class SetPassForm {

    @Size(min = 1, max = 20, message = "密码长度1到20个字")
    private String oldPass;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String newPass;

    @Size(min = 4, max = 20, message = "密码长度4到20个字")
    private String newPass2;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPass2() {
        return newPass2;
    }

    public void setNewPass2(String newPass2) {
        this.newPass2 = newPass2;
    }

}
