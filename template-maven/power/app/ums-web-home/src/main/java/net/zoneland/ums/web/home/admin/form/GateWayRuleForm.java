/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import java.util.Date;

import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayRule;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author wangyong
 * @version $Id: GateWayRuleForm.java, v 0.1 2012-8-31 下午4:33:33 wangyong Exp $
 */
public class GateWayRuleForm {

    private String id;

    @NotEmpty(message = "网关不能为空")
    @Size(max = 36, message = "长度不能超过36位")
    private String gatewayId;

    @Size(max = 100, message = "使用正则表达式长度并不能超过100位")
    private String receiveRegx;

    @Size(max = 500, message = "使用正则表达式并长度不能超过500位")
    private String appIdRegx;

    @Size(max = 200, message = "使用正则表达式并内容长度不能超过200位")
    private String contentRegx;

    private String gatewayName;

    private String type;

    private Date   gmtModified;

    /**
     * Getter method for property <tt>gatewayId</tt>.
     *
     * @return property value of gatewayId
     */
    public String getGatewayId() {
        return gatewayId;
    }

    /**
     * Setter method for property <tt>gatewayId</tt>.
     *
     * @param gatewayId value to be assigned to property gatewayId
     */
    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId == null ? null : gatewayId.trim();
    }

    /**
     * Getter method for property <tt>receiveRegx</tt>.
     *
     * @return property value of receiveRegx
     */
    public String getReceiveRegx() {
        return receiveRegx;
    }

    /**
     * Setter method for property <tt>receiveRegx</tt>.
     *
     * @param receiveRegx value to be assigned to property receiveRegx
     */
    public void setReceiveRegx(String receiveRegx) {
        this.receiveRegx = receiveRegx == null ? null : receiveRegx.trim();
    }

    /**
     * Getter method for property <tt>appIdRegx</tt>.
     *
     * @return property value of appIdRegx
     */
    public String getAppIdRegx() {
        return appIdRegx;
    }

    /**
     * Setter method for property <tt>appIdRegx</tt>.
     *
     * @param appIdRegx value to be assigned to property appIdRegx
     */
    public void setAppIdRegx(String appIdRegx) {
        this.appIdRegx = appIdRegx == null ? null : appIdRegx.trim();
    }

    /**
     * Getter method for property <tt>contentRegx</tt>.
     *
     * @return property value of contentRegx
     */
    public String getContentRegx() {
        return contentRegx;
    }

    /**
     * Setter method for property <tt>contentRegx</tt>.
     *
     * @param contentRegx value to be assigned to property contentRegx
     */
    public void setContentRegx(String contentRegx) {
        this.contentRegx = contentRegx == null ? null : contentRegx.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
        this.gatewayName = gatewayName;
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
        this.type = type;
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
        this.id = id;
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

    /**
     *把UmsGateWayRule中与UmsGarewayrule中属性的值付给UmsGatewayRule
     *
     * @return
     */
    public UmsGatewayRule cloneUmsGateWayRule() {
        UmsGatewayRule umsGatewayRule = new UmsGatewayRule();
        ObjectBuilder.copyObject(this, umsGatewayRule);
        Date date = new Date();
        umsGatewayRule.setGmtCreated(date);
        umsGatewayRule.setGmtModified(date);
        return umsGatewayRule;
    }

}
