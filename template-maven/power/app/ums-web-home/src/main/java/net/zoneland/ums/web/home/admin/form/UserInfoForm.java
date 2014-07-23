/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import javax.validation.constraints.Size;

/**
 * 提供前台显示的用户信息form
 * 
 * @author XuFan
 * @version $Id: UserInfoForm.java, v 0.1 Aug 28, 2012 4:53:18 PM XuFan Exp $
 */
public class UserInfoForm {

    private int    pageId = 1;

    private String id;

    private String employeeId;

    @Size(max = 40, message = "员工工号不能超过40位")
    private String userId;

    @Size(max = 40, message = "员工姓名不能超过40位")
    private String userName;

    private String orgId;

    private String orgName;

    private String checkId;

    private String checkName;

    private String orgAdmin;

    private String roleName;

    @Size(max = 20, message = "手机号不能超过20位")
    private String phone;

    /**
     * 默认构造器
     */
    public UserInfoForm() {
        super();
    }

    /**
     * 有参构造器
     * 
     * @param pageId
     * @param userId
     * @param userName
     * @param orgId
     * @param checkId
     */
    public UserInfoForm(int pageId, String userId, String userName, String orgId, String checkId,
                        String phone) {
        super();
        this.pageId = pageId;
        this.userId = userId;
        this.userName = userName;
        this.orgId = orgId;
        this.checkId = checkId;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName == null ? null : checkName.trim();
    }

    /**
     * Getter method for property <tt>phone</tt>.
     * 
     * @return property value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for property <tt>phone</tt>.
     * 
     * @param phone value to be assigned to property phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * Getter method for property <tt>orgAdmin</tt>.
     * 
     * @return property value of orgAdmin
     */
    public String getOrgAdmin() {
        return orgAdmin;
    }

    /**
     * Setter method for property <tt>orgAdmin</tt>.
     * 
     * @param orgAdmin value to be assigned to property orgAdmin
     */
    public void setOrgAdmin(String orgAdmin) {
        this.orgAdmin = orgAdmin;
    }

    /**
     * Getter method for property <tt>roleName</tt>.
     * 
     * @return property value of roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Setter method for property <tt>roleName</tt>.
     * 
     * @param roleName value to be assigned to property roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>pageId</tt>.
     * 
     * @return property value of pageId
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * Setter method for property <tt>pageId</tt>.
     * 
     * @param pageId value to be assigned to property pageId
     */
    public void setPageId(int pageId) {
        if (pageId < 1) {
            pageId = 1;
        }
        this.pageId = pageId;
    }
}
