/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.msg.form;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.bo.UmsMsgOutBo;

/**
 *
 * @author wangyong
 * @version $Id: MsgSendInfoForm.java, v 0.1 2012-8-22 下午9:34:12 wangyong Exp $
 */
public class MsgSendInfoForm {

    private String id;

    private String recvName; // 模糊匹配出的接收方姓名

    private String status;

    private String startTime;

    private String endTime;

    private String recvId;

    private String sendId;

    private String userId;    // 发送方手机号

    private String userName;  // 发送方姓名

    private String appId;

    private String appName;

    private String recipient; // 输入接收方姓名模糊查询，为与jquery-autocomplete框架自动匹配出的接收方姓名属性recvName作区分

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName == null ? null : recvName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId == null ? null : recvId.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public UmsMsgOutBo cloneUmsMsgOutBo() {
        UmsMsgOutBo umsMsgOutBo = new UmsMsgOutBo();
        ObjectBuilder.copyObject(this, umsMsgOutBo);
        return umsMsgOutBo;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MsgSendInfoForm [id=" + id + ", recvName=" + recvName + ", status=" + status
               + ", startTime=" + startTime + ", endTime=" + endTime + ", recvId=" + recvId
               + ", sendId=" + sendId + ", userId=" + userId + ", userName=" + userName
               + ", appId=" + appId + ", appName=" + appName + ", recipient=" + recipient + "]";
    }

    /**
     * Getter method for property <tt>recipient</tt>.
     * 
     * @return property value of recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Setter method for property <tt>recipient</tt>.
     * 
     * @param recipient value to be assigned to property recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
    }

}
