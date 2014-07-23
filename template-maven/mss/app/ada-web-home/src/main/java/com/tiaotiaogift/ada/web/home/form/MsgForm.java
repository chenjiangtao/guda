/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home.form;

import javax.validation.constraints.Size;

/**
 * 
 * @author gang
 * @version $Id: MsgForm.java, v 0.1 2012-12-23 上午12:31:23 gang Exp $
 */
public class MsgForm {
    
    @Size(min = 2, max = 500, message = "咨询问题长度在2到500个字之间")
    private String content;
    
    @Size(min = 2, max = 10, message = "联系人长度在2到10个字之间")
    private String contactName;
    
    @Size(min = 3, max = 50, message = "联系方式长度在2到50个字之间")
    private String contactInfo;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    
    
    

}
