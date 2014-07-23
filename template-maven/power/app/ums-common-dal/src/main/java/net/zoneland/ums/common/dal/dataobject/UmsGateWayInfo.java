package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsGateWayInfo {
    private String  id;

    private String  gatewayName;

    private String  gatewayState;

    private String  type;

    private String  spNumber;

    private String  host;

    private Integer port;

    private String  localHost;

    private Integer localPort;

    private String  sourceAddr;

    private String  sharedSecret;

    private String  version;

    private Integer readTimeout;

    private Integer reconnectInterval;

    private Integer transactionTimeout;

    private Integer heartbeatInterval;

    private Integer heartbeatNoresponseout;

    private Integer debug;

    private String  corpId;

    private String  status;

    private Date    gmtCreated;

    private Date    gmtModified;

    private String  isOutProv;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName == null ? null : gatewayName.trim();
    }

    public String getGatewayState() {
        return gatewayState;
    }

    public void setGatewayState(String gatewayState) {
        this.gatewayState = gatewayState == null ? null : gatewayState.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSpNumber() {
        return spNumber;
    }

    public void setSpNumber(String spNumber) {
        this.spNumber = spNumber == null ? null : spNumber.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost == null ? null : localHost.trim();
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr == null ? null : sourceAddr.trim();
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret == null ? null : sharedSecret.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getReconnectInterval() {
        return reconnectInterval;
    }

    public void setReconnectInterval(Integer reconnectInterval) {
        this.reconnectInterval = reconnectInterval;
    }

    public Integer getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(Integer transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public Integer getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(Integer heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Integer getHeartbeatNoresponseout() {
        return heartbeatNoresponseout;
    }

    public void setHeartbeatNoresponseout(Integer heartbeatNoresponseout) {
        this.heartbeatNoresponseout = heartbeatNoresponseout;
    }

    public Integer getDebug() {
        return debug;
    }

    public void setDebug(Integer debug) {
        this.debug = debug;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId == null ? null : corpId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getIsOutProv() {
        return isOutProv;
    }

    public void setIsOutProv(String isOutProv) {
        this.isOutProv = isOutProv == null ? null : isOutProv.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}