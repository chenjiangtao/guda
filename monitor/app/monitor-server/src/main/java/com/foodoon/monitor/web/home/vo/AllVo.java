/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.vo;

/**
 * @author foodoon
 * @version $Id: AllVo.java, v 0.1 2013-6-20 下午4:09:17 foodoon Exp $
 */
public class AllVo {

    private String     ip;

    private String     host;

    private int        dbTotal;

    private int        dbWarn;

    private int        serverTotal;

    private int        serverWarn;

    private Object[][] arrays;

    private String     arrayStr;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getDbTotal() {
        return dbTotal;
    }

    public void setDbTotal(int dbTotal) {
        this.dbTotal = dbTotal;
    }

    public void addDbTotal(int dbTotal) {
        this.dbTotal = this.dbTotal + dbTotal;
    }

    public int getDbWarn() {
        return dbWarn;
    }

    public void setDbWarn(int dbWarn) {
        this.dbWarn = dbWarn;
    }

    public void addDbWarn(int dbWarn) {
        this.dbWarn = this.dbWarn + dbWarn;
    }

    public int getServerTotal() {
        return serverTotal;
    }

    public void setServerTotal(int serverTotal) {
        this.serverTotal = serverTotal;
    }

    public void addServerTotal(int serverTotal) {
        this.serverTotal = this.serverTotal + serverTotal;
    }

    public int getServerWarn() {
        return serverWarn;
    }

    public void setServerWarn(int serverWarn) {
        this.serverWarn = serverWarn;
    }

    public void addServerWarn(int serverWarn) {
        this.serverWarn = this.serverWarn + serverWarn;
    }

    public Object[][] getArrays() {
        return arrays;
    }

    public void setArrays(Object[][] arrays) {
        this.arrays = arrays;
    }

    public void stat() {
        if ((dbTotal + dbWarn + serverTotal + serverWarn) > 0) {
            arrays = new Object[4][2];
            arrays[0][0] = "数据库统计" + (dbTotal - dbWarn) + "次";
            arrays[0][1] = dbTotal - dbWarn;

            arrays[1][0] = "数据库警告" + dbWarn + "次";
            arrays[1][1] = dbWarn;

            arrays[2][0] = "主机统计" + (serverTotal - serverWarn) + "次";
            arrays[2][1] = serverTotal - serverWarn;

            arrays[3][0] = "主机警告" + serverWarn + "次";
            arrays[3][1] = serverWarn;
        }
    }

    public String getArrayStr() {
        return arrayStr;
    }

    public void setArrayStr(String arrayStr) {
        this.arrayStr = arrayStr;
    }

}
