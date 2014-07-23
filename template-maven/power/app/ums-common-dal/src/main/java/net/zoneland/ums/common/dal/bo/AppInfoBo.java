/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

/**
 *
 * @author Administrator
 * @version $Id: AppInfoBo.java, v 0.1 2012-9-2 上午10:25:03 Administrator Exp $
 */
public class AppInfoBo extends BasePojo {

    /**  */
    private static final long serialVersionUID = 1L;
    private String            appName;
    private String            appId;

    /**
     * Getter method for property <tt>appName</tt>.
     *
     * @return property value of appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Setter method for property <tt>appName</tt>.
     *
     * @param appName value to be assigned to property appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Getter method for property <tt>appId</tt>.
     *
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     *
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

}
