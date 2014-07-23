/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;

/**
 * 删除消息请求参数
 * 
 * @author yangjuanying
 * @version $Id: DelMsgRequest.java, v 0.1 2012-9-26 下午5:34:30 yangjuanying Exp $
 */
public class DelMsgRequest {

    private String token; // 服务器生成的唯一序列号

    private String msgId; // 要删除的消息ID

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
