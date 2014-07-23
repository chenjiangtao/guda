package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

public class UmsGatewayGroup {
    private String id;

    private String gatewayGroupName;

    private Date gmtCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGatewayGroupName() {
        return gatewayGroupName;
    }

    public void setGatewayGroupName(String gatewayGroupName) {
        this.gatewayGroupName = gatewayGroupName == null ? null : gatewayGroupName.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}