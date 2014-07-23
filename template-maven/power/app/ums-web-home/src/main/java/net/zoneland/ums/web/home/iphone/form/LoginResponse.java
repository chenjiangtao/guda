/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;


/**
 * 
 * @author gag
 * @version $Id: LoginResponse.java, v 0.1 2012-10-18 下午1:37:58 gag Exp $
 */
public class LoginResponse {

    private String code = IphoneCodeConstants.SUCCESS;

    private Object msg;

    private String phone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
