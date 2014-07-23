/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.vo;

/**
 * 
 * @author foodoon
 * @version $Id: SysVo.java, v 0.1 2013-5-28 下午3:21:45 foodoon Exp $
 */
public class SysVo {

    private String  ip;

    private String  k;

    private String  gmtCreated;

    private String  valText;

    private boolean warn;

    private Integer warnVal;

    public Integer getWarnVal() {
        return warnVal;
    }

    public void setWarnVal(Integer warnVal) {
        this.warnVal = warnVal;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getValText() {
        return valText;
    }

    public void setValText(String valText) {
        this.valText = valText;
    }

    public boolean isWarn() {
        return warn;
    }

    public void setWarn(boolean warn) {
        this.warn = warn;
    }

}
