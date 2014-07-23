/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsContact;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author yangjuanying
 * @version $Id: MyContactForm.java, v 0.1 2013-2-2 下午5:10:36 yangjuanying Exp $
 */
public class MyContactForm {

    private String id;

    @Size(max = 36, message = "联系人姓名不能超过12位")
    private String userName;

    @NotEmpty(message = "手机号不能为空")
    @Size(max = 20, message = "手机号格式不正确且不能超过20位")
    private String phone;

    @Size(max = 36, message = "电子邮箱地址不能超过36位")
    @Pattern(regexp = "^(\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+)|\\s*$", message = "电子邮箱地址格式不正确")
    private String email;

    private String gmtCreated;

    private String pageId;

    private String searchUserName;

    private String searchPhone;

    private String searchEmail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(String searchUserName) {
        this.searchUserName = searchUserName == null ? null : searchUserName.trim();
    }

    public String getSearchPhone() {
        return searchPhone;
    }

    public void setSearchPhone(String searchPhone) {
        this.searchPhone = searchPhone == null ? null : searchPhone.trim();
    }

    public String getSearchEmail() {
        return searchEmail;
    }

    public void setSearchEmail(String searchEmail) {
        this.searchEmail = searchEmail == null ? null : searchEmail.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public UmsContact cloneUmsContact() {
        UmsContact umsContact = new UmsContact();
        ObjectBuilder.copyObject(this, umsContact);
        return umsContact;
    }
}
