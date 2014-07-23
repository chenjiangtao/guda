/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.StringUtils;

/**
 * 
 * @author gag
 * @version $Id: FetchRequest.java, v 0.1 2012-9-4 上午9:06:58 gag Exp $
 */
public class FetchRequest {

    private String appId;

    private String subAppId;

    private int    maxCount;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        if (StringUtils.hasText(appId)) {
            this.appId = appId.trim();
        } else {
            this.appId = null;
        }
    }

    public String getSubAppId() {
        if (StringUtils.hasText(subAppId)) {
            return subAppId.trim();
        } else {
            return null;
        }
    }

    public void setSubAppId(String subAppId) {
        if (StringUtils.hasText(subAppId)) {
            this.subAppId = subAppId.trim();
        } else {
            this.subAppId = null;
        }
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
