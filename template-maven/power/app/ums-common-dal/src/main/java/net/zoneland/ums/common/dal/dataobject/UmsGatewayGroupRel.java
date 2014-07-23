package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

public class UmsGatewayGroupRel {
    private String id;

    private String gatewayGroupId;

    private String gatewayId;

    private Date gmtCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGatewayGroupId() {
        return gatewayGroupId;
    }

    public void setGatewayGroupId(String gatewayGroupId) {
        this.gatewayGroupId = gatewayGroupId == null ? null : gatewayGroupId.trim();
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId == null ? null : gatewayId.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}