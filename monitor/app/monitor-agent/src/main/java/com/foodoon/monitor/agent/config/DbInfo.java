/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gang
 * @version $Id: DbInfo.java, v 0.1 2013-4-20 下午1:45:25 gang Exp $
 */
public class DbInfo {

    private String        driverClass;

    private String        url;

    private String        userName;

    private String        password;

    private List<SqlNode> sqlNode = new ArrayList<SqlNode>();

    public void addSqlNode(SqlNode sqlNode) {
        this.sqlNode.add(sqlNode);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SqlNode> getSqlNode() {
        return sqlNode;
    }

    public void setSqlNode(List<SqlNode> sqlNode) {
        this.sqlNode = sqlNode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

}
