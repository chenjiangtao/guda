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
 * @author wangyong
 * @version $Id: UmsMsgOutBo.java, v 0.1 2012-8-16 上午10:09:26 wangyong Exp $
 */
public class UmsMsgOutBo extends BasePojo {

    /**  */
    private static final long serialVersionUID = 1L;

    private String            appId;

    /**发送方Id*/
    private String            userId;

    /**发送方手机号*/
    private String            sendId;

    private String            recvId;

    private String            status;

    private String            mediaId;

    private String            content;

    private Date              gmtModified;

    private Date              sendTime;
    /**接收方姓名*/
    private String            recvName;
    /**发送方姓名*/
    private String            userName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Getter method for property <tt>recvName</tt>.
     * 
     * @return property value of recvName
     */
    public String getRecvName() {
        return recvName;
    }

    /**
     * Setter method for property <tt>recvName</tt>.
     * 
     * @param recvName value to be assigned to property recvName
     */
    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
