/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gag
 * @version $Id: UmsMsgOutIphone.java, v 0.1 2012-10-16 上午10:47:17 gag Exp $
 */
public class UmsMsgOutIphone {

    private String id;

    private String sendId;

    private String userId;

    private String recvId;

    private String content;

    private String mobileStatus;

    private Date   gmtCreated;

    private Date   gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId == null ? null : recvId.trim();
    }

    public String getContent() {
        return content;

    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus == null ? null : mobileStatus.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
