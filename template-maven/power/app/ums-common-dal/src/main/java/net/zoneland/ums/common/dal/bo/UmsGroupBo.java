/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;


/**
 * 
 * @author ypz
 * @version $Id: UmsGroupBo.java, v 0.1 2012-9-10 下午03:04:30 ypz Exp $
 */
public class UmsGroupBo extends BasePojo{
    
    private static final long serialVersionUID = 1920736987469194524L;

    private String userId;
    
    private String groupName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName;
    }

}
