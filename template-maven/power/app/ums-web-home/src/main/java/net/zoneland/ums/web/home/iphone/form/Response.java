/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;


/**
 * 
 * @author gag
 * @version $Id: Response.java, v 0.1 2012-9-4 上午8:16:30 gag Exp $
 */
public class Response {

    private String code = IphoneCodeConstants.SUCCESS;

    private Object msg;

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

}
