/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.form;

import net.zoneland.ums.biz.msg.socket.CodeConstants;

/**
 * 
 * @author gag
 * @version $Id: Response.java, v 0.1 2012-9-4 上午8:16:30 gag Exp $
 */
public class Response {

    private String code = CodeConstants.SUCCESS;

    private Object desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

}
