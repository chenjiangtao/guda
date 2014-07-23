/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

import net.zoneland.ums.common.dal.dataobject.UmsBlackList;

/**
 * 
 * @author XuFan
 * @version $Id: UmsBlackListBO.java, v 0.1 Aug 23, 2012 5:01:58 PM XuFan Exp $
 */
public class UmsBlackListBO extends BasePojo {
    /**  */
    private static final long serialVersionUID = 2528509397914024414L;

    private UmsBlackList      umsBlackList;

    private String            appName;

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
     * Getter method for property <tt>umsBlackList</tt>.
     * 
     * @return property value of umsBlackList
     */
    public UmsBlackList getUmsBlackList() {
        return umsBlackList;
    }

    /**
     * Setter method for property <tt>umsBlackList</tt>.
     * 
     * @param umsBlackList value to be assigned to property umsBlackList
     */
    public void setUmsBlackList(UmsBlackList umsBlackList) {
        this.umsBlackList = umsBlackList;
    }

}
