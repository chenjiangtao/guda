/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;


/**
 * 获取消息请求参数
 * 
 * @author yangjuanying
 * @version $Id: FetchMsgRequest.java, v 0.1 2012-9-26 下午4:02:21 yangjuanying Exp $
 */
public class FetchMsgRequest {

    private String token;    // 服务器生成的唯一序列号

    private int    page;     // 消息页码，1开始

    private int    count;    // 每页的记录数量

    private String sinceTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSinceTime() {
        return sinceTime;
    }

    public void setSinceTime(String sinceTime) {
        this.sinceTime = sinceTime;
    }

}
