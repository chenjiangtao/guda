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
 * @version $Id: FetchTemplateRequest.java, v 0.1 2012-10-18 上午8:48:52 gag Exp $
 */
public class FetchTemplateRequest {

    private String appId;

    private String subAppId;

    private String templateId;

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

    public String getTemplateId() {
        if (StringUtils.hasText(templateId)) {
            return templateId.trim();
        } else {
            return null;
        }
    }

    public void setTemplateId(String templateId) {
        if (StringUtils.hasText(templateId)) {
            this.templateId = templateId.trim();
        } else {
            this.templateId = null;
        }
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
