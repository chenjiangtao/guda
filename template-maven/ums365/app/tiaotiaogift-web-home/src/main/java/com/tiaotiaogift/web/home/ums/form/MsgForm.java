/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.form;

import java.util.Date;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author gag
 * @version $Id: MsgForm.java, v 0.1 2012-12-13 上午10:33:27 gag Exp $
 */
public class MsgForm {

    @NotEmpty(message = "接收方不能为空")
    private String recvId;

    @NotEmpty(message = "消息内容不能为空")
    @Size(min = 0, max = 500, message = "消息内容不能超过500个字,包括标点符号")
    private String content;

    private Date   sendTime;

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
