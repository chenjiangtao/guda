/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Administrator
 * @version $Id: Password.java, v 0.1 2012-9-5 下午4:51:31 Administrator Exp $
 */
public class PasswordForm {
    
    @NotEmpty(message = "原密码不能为空")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    
    private String rePassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword == null ? null : oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword == null ? null : newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword == null ? null : rePassword;
    }

}
