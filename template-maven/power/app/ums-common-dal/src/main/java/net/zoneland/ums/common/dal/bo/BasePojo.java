/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 查询公用条件类
 * @author XuFan
 * @version $Id: BasePojo.java, v 0.1 Aug 14, 2012 5:35:54 PM XuFan Exp $
 */
public class BasePojo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String            orderBy;

    private String            orderbyType      = "desc";

    private Timestamp         startTime;                //开始时间 

    private Timestamp         endTime;                  //结束时间

    private int               pageId;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderbyType() {
        return orderbyType;
    }

    public void setOrderbyType(String orderbyType) {
        this.orderbyType = orderbyType;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

}
