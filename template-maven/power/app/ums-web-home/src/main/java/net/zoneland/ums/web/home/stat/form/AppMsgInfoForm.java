/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.stat.form;

import org.apache.commons.lang.StringUtils;

/**
 * 应用消息界面form
 * @author XuFan
 * @version $Id: AppMsgOutForm.java, v 0.1 Aug 22, 2012 1:16:41 PM XuFan Exp $
 */
public class AppMsgInfoForm {

    private String pageId;

    private String startTime;

    private String endTime;

    private String status;

    private String msgDest;    // 发送方手机号

    private String msgdestName; // 发送方人员姓名

    private String msgSrc;     // 接收方手机号

    private String msgsrcName; // 接收方人员姓名

    private String appId;

    private String appName;

    private String subApp;

    private String supAppName;

    private String gatewaytype;

    private String orgNo;      // 组织号，营销系统产生

    private String flowNo;     // 流程号，营销系统产生

    private String createUser; // 生成人员，营销系统产生

    private String bizName;    // 业务系统，营销系统产生

    private String bizType;    // 业务类型，营销系统产生

    private String content;    // 短信内容

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
        this.orgNo = StringUtils.trim(orgNo);
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
        this.flowNo = StringUtils.trim(flowNo);
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
        this.createUser = StringUtils.trim(createUser);
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
        this.bizName = StringUtils.trim(bizName);
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
        this.bizType = StringUtils.trim(bizType);
    }

    public String getGatewaytype() {
        return gatewaytype;
    }

    public void setGatewaytype(String gatewaytype) {
        this.gatewaytype = StringUtils.trim(gatewaytype);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = StringUtils.trim(appName);
    }

    public String getSupAppName() {
        return supAppName;
    }

    public void setSupAppName(String supAppName) {
        this.supAppName = StringUtils.trim(supAppName);
    }

    public String getMsgdestName() {
        return msgdestName;
    }

    public void setMsgdestName(String msgdestName) {
        this.msgdestName = StringUtils.trim(msgdestName);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = StringUtils.trim(status);
    }

    public String getMsgDest() {
        return msgDest;
    }

    public void setMsgDest(String msgDest) {
        this.msgDest = StringUtils.trim(msgDest);
    }

    public String getMsgSrc() {
        return msgSrc;
    }

    public void setMsgSrc(String msgSrc) {
        this.msgSrc = StringUtils.trim(msgSrc);
    }

    public String getMsgsrcName() {
        return msgsrcName;
    }

    public void setMsgsrcName(String msgsrcName) {
        this.msgsrcName = StringUtils.trim(msgsrcName);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = StringUtils.trim(appId);
    }

    public String getSubApp() {
        return subApp;
    }

    public void setSubApp(String subApp) {
        this.subApp = StringUtils.trim(subApp);
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
        this.content = StringUtils.trim(content);
    }

    /**
     * Getter method for property <tt>pageId</tt>.
     * 
     * @return property value of pageId
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * Setter method for property <tt>pageId</tt>.
     * 
     * @param pageId value to be assigned to property pageId
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

}
