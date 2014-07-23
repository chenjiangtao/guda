/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import javax.validation.constraints.Pattern;

/**
 *
 * @author gag
 * @version $Id: FlowForm.java, v 0.1 2012-9-6 下午5:36:50 gag Exp $
 */
public class FlowForm {

    private String id;

    @Pattern(regexp = "^[0-1]?[0-9]{0,8}$", message = "请输入数字范围是0~199999999")
    private String flowDay;

    @Pattern(regexp = "^[0-1]?[0-9]{0,8}$", message = "请输入数字范围是0~199999999")
    private String flowMonth;

    private String appId;

    private String appName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
        this.flowDay = flowDay;
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
        this.flowMonth = flowMonth;
    }

}
