/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

import java.util.Date;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;

/**
 * 
 * @author XuFan
 * @version $Id: AppMsgInfoBO.java, v 0.1 Aug 22, 2012 9:21:27 AM XuFan Exp $
 */
public class AppMsgInfoBO extends BasePojo {
    /**  */
    private static final long serialVersionUID = 4221550929607193343L;

    private String            id;

    private String            userId;

    private String            phone;

    private String            status;

    private String            appId;

    private String            subApp;

    private Date              gmtCreated;

    private String            recvId;

    private String            sendId;

    private UmsAppInfo        app;

    private UmsAppSub         sub;

    private UmsUserInfo       user;

    private String            appName;

    private String            gatewaytype;

    private String            msgdestName;                            //发送人员

    private String            msgsrcName;                             //接收人员

    private String            content;

    private String            orgNo;                                  // 组织号，营销系统产生

    private String            flowNo;                                 // 流程号，营销系统产生

    private String            createUser;                             // 生成人员，营销系统产生

    private String            bizName;                                // 业务系统，营销系统产生

    private String            bizType;                                // 业务类型，营销系统产生

    /**
     * Getter method for property <tt>orgNo</tt>.
     * 
     * @return property value of orgNo
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * Setter method for property <tt>orgNo</tt>.
     * 
     * @param orgNo value to be assigned to property orgNo
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * Getter method for property <tt>flowNo</tt>.
     * 
     * @return property value of flowNo
     */
    public String getFlowNo() {
        return flowNo;
    }

    /**
     * Setter method for property <tt>flowNo</tt>.
     * 
     * @param flowNo value to be assigned to property flowNo
     */
    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    /**
     * Getter method for property <tt>createUser</tt>.
     * 
     * @return property value of createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Setter method for property <tt>createUser</tt>.
     * 
     * @param createUser value to be assigned to property createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Getter method for property <tt>bizName</tt>.
     * 
     * @return property value of bizName
     */
    public String getBizName() {
        return bizName;
    }

    /**
     * Setter method for property <tt>bizName</tt>.
     * 
     * @param bizName value to be assigned to property bizName
     */
    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    /**
     * Getter method for property <tt>bizType</tt>.
     * 
     * @return property value of bizType
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * Setter method for property <tt>bizType</tt>.
     * 
     * @param bizType value to be assigned to property bizType
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * Getter method for property <tt>serialversionuid</tt>.
     * 
     * @return property value of serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMsgdestName() {
        return msgdestName;
    }

    public void setMsgdestName(String msgdestName) {
        this.msgdestName = msgdestName;
    }

    public String getMsgsrcName() {
        return msgsrcName;
    }

    public void setMsgsrcName(String msgsrcName) {
        this.msgsrcName = msgsrcName;
    }

    public String getGatewaytype() {
        return gatewaytype;
    }

    public void setGatewaytype(String gatewaytype) {
        this.gatewaytype = gatewaytype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubApp() {
        return subApp;
    }

    public void setSubApp(String subApp) {
        this.subApp = subApp;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public UmsAppInfo getApp() {
        return app;
    }

    public void setApp(UmsAppInfo app) {
        this.app = app;
    }

    public UmsAppSub getSub() {
        return sub;
    }

    public void setSub(UmsAppSub sub) {
        this.sub = sub;
    }

    public UmsUserInfo getUser() {
        return user;
    }

    public void setUser(UmsUserInfo user) {
        this.user = user;
    }

    /**
     * Getter method for property <tt>appName</tt>.
     * 
     * @return property value of appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Setter method for property <tt>appName</tt>.
     * 
     * @param appName value to be assigned to property appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Getter method for property <tt>content</tt>.
     * 
     * @return property value of content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     * 
     * @param content value to be assigned to property content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
