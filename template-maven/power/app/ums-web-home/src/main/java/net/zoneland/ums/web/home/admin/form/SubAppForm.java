/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author wangyong
 * @version $Id: SubAppForm.java, v 0.1 2012-9-11 下午3:23:47 wangyong Exp $
 */
public class SubAppForm {

    private String id;

    private String appId;

    @Pattern(regexp = "^[0-9]{2}$", message = "应用Id为两个个数字")
    private String appSubId;

    private String appSubCode;

    @Size(max = 16, message = "长度不能超过16位")
    private String appSubName;

    @Pattern(regexp = "^[0-9]{0,3}$", message = "请输入数字0~999")
    private String priority;

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
     * Getter method for property <tt>appId</tt>.
     *
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     *
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * Getter method for property <tt>appSubId</tt>.
     *
     * @return property value of appSubId
     */
    public String getAppSubId() {
        return appSubId;
    }

    /**
     * Setter method for property <tt>appSubId</tt>.
     *
     * @param appSubId value to be assigned to property appSubId
     */
    public void setAppSubId(String appSubId) {
        this.appSubId = appSubId == null ? null : appSubId.trim();
    }

    /**
     * Getter method for property <tt>appSubCode</tt>.
     *
     * @return property value of appSubCode
     */
    public String getAppSubCode() {
        return appSubCode;
    }

    /**
     * Setter method for property <tt>appSubCode</tt>.
     *
     * @param appSubCode value to be assigned to property appSubCode
     */
    public void setAppSubCode(String appSubCode) {
        this.appSubCode = appSubCode == null ? null : appSubCode.trim();
    }

    /**
     * Getter method for property <tt>appSubName</tt>.
     *
     * @return property value of appSubName
     */
    public String getAppSubName() {
        return appSubName;
    }

    /**
     * Setter method for property <tt>appSubName</tt>.
     *
     * @param appSubName value to be assigned to property appSubName
     */
    public void setAppSubName(String appSubName) {
        this.appSubName = appSubName == null ? null : appSubName.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public UmsAppSub cloneUmsAppSub() {
        UmsAppSub umsAppSub = new UmsAppSub();
        ObjectBuilder.copyObject(this, umsAppSub);
        umsAppSub.setAppSubCode(this.getAppSubId());
        return umsAppSub;
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

}
