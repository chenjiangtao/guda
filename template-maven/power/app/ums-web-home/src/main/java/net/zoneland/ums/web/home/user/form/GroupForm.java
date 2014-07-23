/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author ypz
 * @version $Id: GroupForm.java, v 0.1 2012-9-10 下午04:21:38 ypz Exp $
 */
public class GroupForm {

    private int    pageId = 1;

    @NotEmpty(message = "群组名称不能为空")
    @Size(max = 90, message = "群组名称不能超过90个字符")
    private String groupName;

    private String id;

    private String relJson;

    private String searchName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id;
    }

    public String getRelJson() {
        return relJson;
    }

    public void setRelJson(String relJson) {
        this.relJson = relJson == null ? null : relJson;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName == null ? null : searchName;
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
        this.pageId = pageId;
    }

}
