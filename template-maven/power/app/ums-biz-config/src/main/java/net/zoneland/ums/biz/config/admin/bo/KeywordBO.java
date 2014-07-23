/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.bo;

import net.zoneland.ums.common.dal.dataobject.UmsKeywordInfo;

/**
 * 
 * @author gang
 * @version $Id: KeywordBO.java, v 0.1 2012-9-5 下午10:29:34 gang Exp $
 */
public class KeywordBO {

    private UmsKeywordInfo umsKeywordInfo;

    private String         appName;

    public UmsKeywordInfo getUmsKeywordInfo() {
        return umsKeywordInfo;
    }

    public void setUmsKeywordInfo(UmsKeywordInfo umsKeywordInfo) {
        this.umsKeywordInfo = umsKeywordInfo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
