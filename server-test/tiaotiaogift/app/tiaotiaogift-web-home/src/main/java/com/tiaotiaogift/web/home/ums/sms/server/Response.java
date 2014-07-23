/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.sms.server;

/**
 * 
 * @author gang
 * @version $Id: Response.java, v 0.1 2013-5-9 上午9:37:30 gang Exp $
 */
public class Response {

    private String code;

    private String msg = "fail";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
