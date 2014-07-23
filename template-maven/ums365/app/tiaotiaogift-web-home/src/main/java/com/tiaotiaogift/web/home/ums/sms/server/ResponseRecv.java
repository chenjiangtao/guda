/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.sms.server;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author gang
 * @version $Id: ResponseRecv.java, v 0.1 2013-5-9 上午11:26:53 gang Exp $
 */
public class ResponseRecv {

    private String  code;

    private List<?> obj = Collections.EMPTY_LIST;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<?> getObj() {
        return obj;
    }

    public void setObj(List<?> obj) {
        this.obj = obj;
    }

}
