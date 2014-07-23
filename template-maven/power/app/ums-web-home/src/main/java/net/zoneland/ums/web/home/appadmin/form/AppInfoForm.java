/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.appadmin.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author XuFan
 * @version $Id: AppInfoForm.java, v 0.1 Aug 15, 2012 4:51:51 PM XuFan Exp $
 */
public class AppInfoForm {

    private String pageId;

    private String id;

    @Pattern(regexp = "^[0-9]{4}$", message = "应用Id必须为4位数字")
    private String appId;

    @NotEmpty(message = "应用名称不能为空")
    @Size(max = 12, message = "应用名称不能超过12位")
    private String appName;

    private String appCode;

    @Size(max = 500, message = "IP地址不能超过500位")
    private String ip;

    @Pattern(regexp = "^([1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5])?$", message = "端口号为1025-65535之间的数字")
    private String port;

    @NotEmpty(message = "用户名不能为空")
    @Size(max = 15, message = "用户名长度不能超过16位")
    private String username;

    @Pattern(regexp = "^[0-9a-zA-Z]{4,20}$", message = "密码必须为4~20位的数字或字母")
    private String password;

    @Size(max = 10, message = "短信签名长度不能超过10位")
    private String signName;

    @Pattern(regexp = "^[0-1]?[0-9]{0,8}$", message = "请输入数字范围是0~199999999")
    private String flowDay;

    @Pattern(regexp = "^[0-1]?[0-9]{0,8}$", message = "请输入数字范围是0~199999999")
    private String flowMonth;

    @Pattern(regexp = "^[0-9]{0,3}$", message = "优先级必须为1位0~9的数字")
    private String priority;

    @Pattern(regexp = "^[0-9]{0,2}$", message = "必须为0~99之间的数字")
    private String fee;

    @Pattern(regexp = "^[0-9]{0,2}$", message = "必须为0~99之间的数字")
    private String feeType;

    private String status;

    private String gmtCreated;

    private String gmtModified;

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

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated == null ? null : gmtCreated.trim();
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified == null ? null : gmtModified.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * Getter method for property <tt>signName</tt>.
     *
     * @return property value of signName
     */
    public String getSignName() {
        return signName;
    }

    /**
     * Setter method for property <tt>signName</tt>.
     *
     * @param signName value to be assigned to property signName
     */
    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    public UmsAppInfo cloneUmsAppInfo() {
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        ObjectBuilder.copyObject(this, umsAppInfo);
        umsAppInfo.setAppCode(this.appId);
        return umsAppInfo;
    }

    /**
     * Getter method for property <tt>flowDay</tt>.
     *
     * @return property value of flowDay
     */
    public String getFlowDay() {
        return flowDay;
    }

    /**
     * Setter method for property <tt>flowDay</tt>.
     *
     * @param flowDay value to be assigned to property flowDay
     */
    public void setFlowDay(String flowDay) {
        this.flowDay = flowDay == null ? null : flowDay.trim();
    }

    /**
     * Getter method for property <tt>flowMonth</tt>.
     *
     * @return property value of flowMonth
     */
    public String getFlowMonth() {
        return flowMonth;
    }

    /**
     * Setter method for property <tt>flowMonth</tt>.
     *
     * @param flowMonth value to be assigned to property flowMonth
     */
    public void setFlowMonth(String flowMonth) {
        this.flowMonth = flowMonth == null ? null : flowMonth.trim();
    }

    /**
     * Getter method for property <tt>priority</tt>.
     *
     * @return property value of priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Setter method for property <tt>priority</tt>.
     *
     * @param priority value to be assigned to property priority
     */
    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    /**
     * Setter method for property <tt>fee</tt>.
     *
     * @param fee value to be assigned to property fee
     */
    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    /**
     * Setter method for property <tt>feeType</tt>.
     *
     * @param feeType value to be assigned to property feeType
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    /**
     * Getter method for property <tt>fee</tt>.
     *
     * @return property value of fee
     */
    public String getFee() {
        return fee;
    }

    /**
     * Getter method for property <tt>feeType</tt>.
     *
     * @return property value of feeType
     */
    public String getFeeType() {
        return feeType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Getter method for property <tt>pageId</tt>.
     * 
     * @return property value of pageId
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * Setter method for property <tt>pageId</tt>.
     * 
     * @param pageId value to be assigned to property pageId
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
