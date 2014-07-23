/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;

/**
 * 提供给前台显示的黑名单信息表单
 * 
 * @author yangjuanying
 * @version $Id: BlackListForm.java, v 0.1 2012-9-9 下午12:55:47 yangjuanying Exp $
 */
public class BlackListForm {

    private int              curPage = 1;

    @Size(max = 11, message = "手机号长度不能超过11位")
    private String           userId;
    private List<UmsAppInfo> umsAppInfos;

    @Pattern(regexp = "^[0-9]{4}$", message = "应用Id为四个数字")
    private String           appId;

    /**
     * Getter method for property <tt>umsAppInfos</tt>.
     * 
     * @return property value of umsAppInfos
     */
    public List<UmsAppInfo> getUmsAppInfos() {
        return umsAppInfos;
    }

    /**
     * Setter method for property <tt>umsAppInfos</tt>.
     * 
     * @param umsAppInfos value to be assigned to property umsAppInfos
     */
    public void setUmsAppInfos(List<UmsAppInfo> umsAppInfos) {
        this.umsAppInfos = umsAppInfos;
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
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * Getter method for property <tt>curPage</tt>.
     * 
     * @return property value of curPage
     */
    public int getCurPage() {
        return curPage;
    }

    /**
     * Setter method for property <tt>curPage</tt>.
     * 
     * @param curPage value to be assigned to property curPage
     */
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

}
