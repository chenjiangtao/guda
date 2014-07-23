/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.admin.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 
 * @author gang
 * @version $Id: UserForm.java, v 0.1 2013-4-30 下午9:03:15 gang Exp $
 */
public class UserForm {

    @NotNull
    private String  id;

    private String  userId;

    private String  password;

    @Pattern(regexp = "^[1][345678][0-9]{9}$", message = "手机格式不正确")
    private String  phone;

    @Size(min = 2, max = 36, message = "邮箱长度不超过36个字符")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "邮箱格式不正确")
    private String  email;

    private String  status;

    private Integer grade;

    private Integer balance;

    private Integer balanceLock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalanceLock() {
        return balanceLock;
    }

    public void setBalanceLock(Integer balanceLock) {
        this.balanceLock = balanceLock;
    }

}
