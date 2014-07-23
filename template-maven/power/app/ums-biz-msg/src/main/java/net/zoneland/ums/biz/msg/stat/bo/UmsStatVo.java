/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.stat.bo;

import net.zoneland.ums.common.dal.dataobject.UmsStat;

/**
 * 
 * @author gang
 * @version $Id: UmsStatVo.java, v 0.1 2013-3-27 上午11:02:08 gang Exp $
 */
public class UmsStatVo {

    private String  appName;

    private UmsStat umsStat;

    public UmsStatVo() {

    }

    public UmsStatVo(UmsStat umsStat) {
        this.umsStat = umsStat;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public UmsStat getUmsStat() {
        return umsStat;
    }

    public void setUmsStat(UmsStat umsStat) {
        this.umsStat = umsStat;
    }

}
