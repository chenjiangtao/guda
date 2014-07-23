/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;

import org.springframework.util.StringUtils;

/**
 * 登录接口请求参数
 * 
 * @author yangjuanying
 * @version $Id: LoginRequest.java, v 0.1 2012-9-25 下午6:55:21 yangjuanying Exp $
 */
public class LoginRequest {

    private String userName; // 登录用户名

    private String password; // 登录密码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = StringUtils.trimWhitespace(userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        
        this.password = StringUtils.trimWhitespace(password);
    }
}
