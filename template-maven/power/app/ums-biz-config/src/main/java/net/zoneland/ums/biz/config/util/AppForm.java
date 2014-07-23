/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.util;

/**
 * 
 * @author Administrator
 * @version $Id: AppForm.java, v 0.1 2012-10-16 上午9:36:35 Administrator Exp $
 */
public class AppForm {

    private String appId;

    private String appName;

    /**
     * 
     */
    public AppForm() {
        super();
    }

    /**
     * @param appId
     * @param appName
     */
    public AppForm(String appId, String appName) {
        super();
        this.appId = appId;
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }
}
