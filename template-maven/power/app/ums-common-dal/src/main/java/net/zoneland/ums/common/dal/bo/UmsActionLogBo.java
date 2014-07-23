/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

import java.util.Date;

/**
 * 
 * @author ypz
 * @version $Id: UmsActionLogBo.java, v 0.1 2012-9-7 上午10:38:41 ypz Exp $
 */
public class UmsActionLogBo extends BasePojo {

    /**  */
    private static final long serialVersionUID = -4307031123194138276L;
    private String            id;
    private String            operatorMenu;
    private String            operatorName;
    private String            operatorType;
    private String            operatorId;
    private String            operatorIp;
    private String            comment;
    private Date              gmtCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getOperatorMenu() {
        return operatorMenu;
    }

    public void setOperatorMenu(String operatorMenu) {
        this.operatorMenu = operatorMenu;
    }

    
}
