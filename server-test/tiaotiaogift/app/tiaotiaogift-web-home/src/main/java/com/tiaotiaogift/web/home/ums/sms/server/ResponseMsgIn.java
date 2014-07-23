/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.sms.server;

import java.util.Date;

/**
 * 
 * @author gang
 * @version $Id: ResponseMsgIn.java, v 0.1 2013-5-9 上午11:28:51 gang Exp $
 */
public class ResponseMsgIn {

    private String mobile;

    private String content;

    private Date   gmtCreated;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

}
