package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import net.zoneland.ums.common.dal.bo.BasePojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsAppInfo extends BasePojo {
    /**  */
    private static final long serialVersionUID = 1133015339865092138L;

    private String            id;

    private String            appId;

    private String            appName;

    private String            appCode;

    private String            ip;

    private String            port;

    private String            username;

    private String            password;

    private Integer           flowDay;

    private Integer           flowMonth;

    private Integer           priority;

    private Integer           fee;

    private Integer           feeType;

    private String            signName;

    private String            status;

    private String            isOutProv;

    private String            emailMd5;

    private Date              gmtCreated;

    private Date              gmtModified;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getFlowDay() {
        return flowDay;
    }

    public void setFlowDay(Integer flowDay) {
        this.flowDay = flowDay;
    }

    public Integer getFlowMonth() {
        return flowMonth;
    }

    public void setFlowMonth(Integer flowMonth) {
        this.flowMonth = flowMonth;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getIsOutProv() {
        return isOutProv;
    }

    public void setIsOutProv(String isOutProv) {
        this.isOutProv = isOutProv == null ? "" : isOutProv;
    }

    public String getEmailMd5() {
        return emailMd5;
    }

    public void setEmailMd5(String emailMd5) {
        this.emailMd5 = emailMd5;
    }

}