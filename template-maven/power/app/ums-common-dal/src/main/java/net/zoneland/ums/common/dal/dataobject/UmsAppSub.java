package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsAppSub {
    private String  id;

    private String  appId;

    private String  appSubId;

    private String  appSubCode;

    private String  appSubName;

    private Integer priority;

    private Date    gmtCreated;

    private Date    gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppSubId() {
        return appSubId;
    }

    public void setAppSubId(String appSubId) {
        this.appSubId = appSubId == null ? null : appSubId.trim();
    }

    public String getAppSubCode() {
        return appSubCode;
    }

    public void setAppSubCode(String appSubCode) {
        this.appSubCode = appSubCode == null ? null : appSubCode.trim();
    }

    public String getAppSubName() {
        return appSubName;
    }

    public void setAppSubName(String appSubName) {
        this.appSubName = appSubName == null ? null : appSubName.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}