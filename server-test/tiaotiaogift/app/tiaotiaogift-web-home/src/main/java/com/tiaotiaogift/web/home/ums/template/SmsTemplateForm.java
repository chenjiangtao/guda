/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.template;

/**
 * 
 * @author gang
 * @version $Id: SmsTemplateForm.java, v 0.1 2013-4-26 下午2:37:13 gang Exp $
 */
public class SmsTemplateForm {

    private String  id;

    private String  content;

    private String  type;

    private Integer page = 1;

    private Integer rows;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
