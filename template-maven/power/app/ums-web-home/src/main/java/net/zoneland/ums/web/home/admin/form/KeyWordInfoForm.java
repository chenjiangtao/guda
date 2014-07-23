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
 * 提供给前台显示的关键字信息表单
 * 
 * @author yangjuanying
 * @version $Id: KeyWordInfoForm.java, v 0.1 2012-9-8 下午3:18:00 yangjuanying Exp $
 */
public class KeyWordInfoForm {
    private final int        curPage = 1;

    @Size(max = 36, message = "关键字长度不能超过36个字符,12个汉字")
    private String           keyword;

    @Pattern(regexp = "^[0-9]{4}$", message = "应用Id为四个数字")
    private String           appId;

    private List<UmsAppInfo> umsAppInfos;

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
     * Getter method for property <tt>keyword</tt>.
     * 
     * @return property value of keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Setter method for property <tt>keyword</tt>.
     * 
     * @param keyword value to be assigned to property keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
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
     * Getter method for property <tt>curPage</tt>.
     * 
     * @return property value of curPage
     */
    public int getCurPage() {
        return curPage;
    }
}
