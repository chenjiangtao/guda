/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;

/**
 * 发送消息请求参数
 * 
 * @author yangjuanying
 * @version $Id: SendRequest.java, v 0.1 2012-9-26 下午3:05:36 yangjuanying Exp $
 */
public class SendMsgRequest {

    private String token;     // 服务器生成的唯一序列号
    private String recv;      // 接收方号码，多个以英文逗号”,”分隔
    private String msgContent; // 发送消息内容

    public String getRecv() {
        return recv;
    }

    public void setRecv(String recv) {
        this.recv = recv;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
