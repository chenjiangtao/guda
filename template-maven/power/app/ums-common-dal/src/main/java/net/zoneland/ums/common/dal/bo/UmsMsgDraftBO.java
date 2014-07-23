/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

/**
 * 
 * @author ypz
 * @version $Id: UmsMsgDraftBO.java, v 0.1 2012-9-6 下午03:31:03 ypz Exp $
 */
public class UmsMsgDraftBO extends BasePojo {
    /**  */
    private static final long serialVersionUID = -9104566301346069163L;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
