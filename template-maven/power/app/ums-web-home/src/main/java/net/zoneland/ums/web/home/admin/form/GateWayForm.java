/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author wangyong
 * @version $Id: GateWayForm.java, v 0.1 2012-8-29 上午9:41:35 wangyong Exp $
 */
public class GateWayForm {

    @Size(max = 36, message = "长度不能超过36位")
    private String id;

    @NotEmpty(message = "网关名字不能为空")
    @Size(max = 12, message = "网关名字长度不能超过12位")
    private String gatewayName;

    private String gatewayState;

    @NotEmpty(message = "网关类型不能为空")
    @Size(max = 10, message = "字母跟数字组成长度不能超过10位")
    private String type;

    @Pattern(regexp = "^[0-9]{1,20}$", message = "只能是不超过20位的数字")
    private String spNumber;

    @NotEmpty(message = "IP不能为空")
    @Size(max = 20, message = "IP长度不超过20位")
    private String host;

    @Pattern(regexp = "^[1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5]$", message = "端口号是1025-65535的数字")
    private String port;

    @Size(max = 20, message = "IP长度不超过20位")
    private String localHost;

    @Pattern(regexp = "^([1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5])|\\s*$", message = "端口号是1025-65535的数字")
    private String localPort;

    @Pattern(regexp = "^[0-9a-zA-Z]{1,20}$", message = "用户名必须为1~20位数字或字母")
    private String sourceAddr;

    @Pattern(regexp = "^[0-9a-zA-Z]{4,20}$", message = "密码必须为4~20位数字或字母")
    private String sharedSecret;

    @Pattern(regexp = "^([0-9]{1,3})$", message = "请输入以秒为单位的0~999数字")
    private String version;

    @Pattern(regexp = "^([0-9]{1,3})$", message = "请输入以秒为单位的0~999数字")
    private String readTimeout;

    @Pattern(regexp = "^([0-9]{1,3})$", message = "请输入以秒为单位的0~999数字")
    private String reconnectInterval;

    @Pattern(regexp = "^([0-9]{1,3})$", message = "请输入以秒为单位的0~999数字")
    private String transactionTimeout;

    @Pattern(regexp = "^([0-9]{1,3})$", message = "请输入以秒为单位的0~999数字")
    private String heartbeatInterval;

    @Pattern(regexp = "^([0-9]{1,3})$", message = "请输入以秒为单位的0~999数字")
    private String heartbeatNoresponseout;

    @Pattern(regexp = "^[0-1]?$", message = "只能输入1或0，输入1表示调试，输入0表示不调试")
    private String debug;

    @Size(max = 6, message = "公司ID长度不超过6位")
    private String corpId;

    private String isOutProv;

    private String status;

    private Date   gmtCreated;

    private Date   gmtModified;

    public String getIsOutProv() {
        return isOutProv;
    }

    public void setIsOutProv(String isOutProv) {
        this.isOutProv = isOutProv == null ? null : isOutProv.trim();
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * Getter method for property <tt>gatewayName</tt>.
     *
     * @return property value of gatewayName
     */
    public String getGatewayName() {
        return gatewayName;
    }

    /**
     * Setter method for property <tt>gatewayName</tt>.
     *
     * @param gatewayName value to be assigned to property gatewayName
     */
    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName == null ? null : gatewayName.trim();
    }

    /**
     * Getter method for property <tt>gatewayState</tt>.
     *
     * @return property value of gatewayState
     */
    public String getGatewayState() {
        return gatewayState;
    }

    /**
     * Setter method for property <tt>gatewayState</tt>.
     *
     * @param gatewayState value to be assigned to property gatewayState
     */
    public void setGatewayState(String gatewayState) {
        this.gatewayState = gatewayState == null ? null : gatewayState.trim();
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * Getter method for property <tt>spNumber</tt>.
     *
     * @return property value of spNumber
     */
    public String getSpNumber() {
        return spNumber;
    }

    /**
     * Setter method for property <tt>spNumber</tt>.
     *
     * @param spNumber value to be assigned to property spNumber
     */
    public void setSpNumber(String spNumber) {
        this.spNumber = spNumber == null ? null : spNumber.trim();
    }

    /**
     * Getter method for property <tt>host</tt>.
     *
     * @return property value of host
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter method for property <tt>host</tt>.
     *
     * @param host value to be assigned to property host
     */
    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    /**
     * Getter method for property <tt>localHost</tt>.
     *
     * @return property value of localHost
     */
    public String getLocalHost() {
        return localHost;
    }

    /**
     * Setter method for property <tt>localHost</tt>.
     *
     * @param localHost value to be assigned to property localHost
     */
    public void setLocalHost(String localHost) {
        this.localHost = localHost == null ? null : localHost.trim();
    }

    /**
     * Getter method for property <tt>sourceAddr</tt>.
     *
     * @return property value of sourceAddr
     */
    public String getSourceAddr() {
        return sourceAddr;
    }

    /**
     * Setter method for property <tt>sourceAddr</tt>.
     *
     * @param sourceAddr value to be assigned to property sourceAddr
     */
    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr == null ? null : sourceAddr.trim();
    }

    /**
     * Getter method for property <tt>sharedSecret</tt>.
     *
     * @return property value of sharedSecret
     */
    public String getSharedSecret() {
        return sharedSecret;
    }

    /**
     * Setter method for property <tt>sharedSecret</tt>.
     *
     * @param sharedSecret value to be assigned to property sharedSecret
     */
    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret == null ? null : sharedSecret.trim();
    }

    /**
     * Getter method for property <tt>version</tt>.
     *
     * @return property value of version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter method for property <tt>version</tt>.
     *
     * @param version value to be assigned to property version
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * Getter method for property <tt>corpId</tt>.
     *
     * @return property value of corpId
     */
    public String getCorpId() {
        return corpId;
    }

    /**
     * Setter method for property <tt>corpId</tt>.
     *
     * @param corpId value to be assigned to property corpId
     */
    public void setCorpId(String corpId) {
        this.corpId = corpId == null ? null : corpId.trim();
    }

    /**
     * Getter method for property <tt>gmtCreated</tt>.
     *
     * @return property value of gmtCreated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * Setter method for property <tt>gmtCreated</tt>.
     *
     * @param gmtCreated value to be assigned to property gmtCreated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property <tt>gmtModified</tt>.
     *
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     *把GateWayForm中的值付给UmsGatewayInfo与其属性相同的值
     *
     * @return
     */
    public UmsGateWayInfo cloneUmsGateWayInfo() {
        UmsGateWayInfo umsGateWayInfo = new UmsGateWayInfo();
        ObjectBuilder.copyObject(this, umsGateWayInfo);
        return umsGateWayInfo;
    }

    /**
     * Getter method for property <tt>port</tt>.
     *
     * @return property value of port
     */
    public String getPort() {
        return port;
    }

    /**
     * Setter method for property <tt>port</tt>.
     *
     * @param port value to be assigned to property port
     */
    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    /**
     * Getter method for property <tt>localPort</tt>.
     *
     * @return property value of localPort
     */
    public String getLocalPort() {
        return localPort;
    }

    /**
     * Setter method for property <tt>localPort</tt>.
     *
     * @param localPort value to be assigned to property localPort
     */
    public void setLocalPort(String localPort) {
        this.localPort = localPort == null ? null : localPort.trim();
    }

    /**
     * Getter method for property <tt>readTimeout</tt>.
     *
     * @return property value of readTimeout
     */
    public String getReadTimeout() {
        return readTimeout;
    }

    /**
     * Setter method for property <tt>readTimeout</tt>.
     *
     * @param readTimeout value to be assigned to property readTimeout
     */
    public void setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout == null ? null : readTimeout.trim();
    }

    /**
     * Getter method for property <tt>reconnectInterval</tt>.
     *
     * @return property value of reconnectInterval
     */
    public String getReconnectInterval() {
        return reconnectInterval;
    }

    /**
     * Setter method for property <tt>reconnectInterval</tt>.
     *
     * @param reconnectInterval value to be assigned to property reconnectInterval
     */
    public void setReconnectInterval(String reconnectInterval) {
        this.reconnectInterval = reconnectInterval == null ? null : reconnectInterval.trim();
    }

    /**
     * Getter method for property <tt>transactionTimeout</tt>.
     *
     * @return property value of transactionTimeout
     */
    public String getTransactionTimeout() {
        return transactionTimeout;
    }

    /**
     * Setter method for property <tt>transactionTimeout</tt>.
     *
     * @param transactionTimeout value to be assigned to property transactionTimeout
     */
    public void setTransactionTimeout(String transactionTimeout) {
        this.transactionTimeout = transactionTimeout == null ? null : transactionTimeout.trim();
    }

    /**
     * Getter method for property <tt>heartbeatInterval</tt>.
     *
     * @return property value of heartbeatInterval
     */
    public String getHeartbeatInterval() {
        return heartbeatInterval;
    }

    /**
     * Setter method for property <tt>heartbeatInterval</tt>.
     *
     * @param heartbeatInterval value to be assigned to property heartbeatInterval
     */
    public void setHeartbeatInterval(String heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval == null ? null : heartbeatInterval.trim();
    }

    /**
     * Getter method for property <tt>heartbeatNoresponseout</tt>.
     *
     * @return property value of heartbeatNoresponseout
     */
    public String getHeartbeatNoresponseout() {
        return heartbeatNoresponseout;
    }

    /**
     * Setter method for property <tt>heartbeatNoresponseout</tt>.
     *
     * @param heartbeatNoresponseout value to be assigned to property heartbeatNoresponseout
     */
    public void setHeartbeatNoresponseout(String heartbeatNoresponseout) {
        this.heartbeatNoresponseout = heartbeatNoresponseout == null ? null
            : heartbeatNoresponseout.trim();
    }

    /**
     * Getter method for property <tt>debug</tt>.
     *
     * @return property value of debug
     */
    public String getDebug() {
        return debug;
    }

    /**
     * Setter method for property <tt>debug</tt>.
     *
     * @param debug value to be assigned to property debug
     */
    public void setDebug(String debug) {
        this.debug = debug == null ? null : debug.trim();
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}
