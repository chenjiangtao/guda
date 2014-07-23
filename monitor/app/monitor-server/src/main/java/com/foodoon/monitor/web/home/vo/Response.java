/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.vo;

/**
 * 
 * @author gang
 * @version $Id: Response.java, v 0.1 2013-4-19 下午10:02:16 gang Exp $
 */
public class Response {

    private boolean success;

    private String  msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
