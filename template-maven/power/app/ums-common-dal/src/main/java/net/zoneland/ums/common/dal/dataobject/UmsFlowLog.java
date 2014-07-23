package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsFlowLog {

    private String  id;

    private String  appId;

    private Integer flowMonTotal;

    private Integer flowToday;

    private Date    gmtCreated;

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

    public Integer getFlowMonTotal() {
        return flowMonTotal;
    }

    public void setFlowMonTotal(Integer flowMonTotal) {
        this.flowMonTotal = flowMonTotal;
    }

    public Integer getFlowToday() {
        return flowToday;
    }

    public void setFlowToday(Integer flowToday) {
        this.flowToday = flowToday;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}