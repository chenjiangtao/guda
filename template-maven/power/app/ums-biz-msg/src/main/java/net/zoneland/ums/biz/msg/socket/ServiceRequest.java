/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket;

import java.util.Date;
import java.util.UUID;

import net.zoneland.ums.common.util.helper.StringHelper;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gag
 * @version $Id: SocketRequest.java, v 0.1 2012-8-10 下午3:07:56 gag Exp $
 */
public class ServiceRequest {

    private final UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }

    /** 请求码 1003  1002，，， */
    private String requestCode;

    /** 扩展字段 */
    private String subType;

    /** 应用ID */
    private String appId;

    /** 子应用ID */
    private String subAppId;

    /** 应用密码，登录用 */
    private String password;

    /** 批次号 */
    private String batchNo;

    /** 流水号 */
    private String serialNo;

    /** 消息ID，发送消息 */
    private String id;

    /** 消息编码类型 */
    private int    msgType;

    /** 消息接收方 */
    private String umsTo;

    /** 消息内容 */
    private String msg;

    /** 回执，回复类型 */
    private int    ack;

    /** 回复号 */
    private String reply;

    /** 消息优先级 */
    private int    priority;

    /** 消息重试次数 */
    private int    rep;

    /**  */
    private String checkFlag;

    private String clientIp;

    private int    clientPort;

    private int    serverPort;

    private String orgNo;

    private String flowNo;

    private String createUser;

    private Date   sendDate;

    private int    validTime;

    private int    maxCount;

    private String templateId;

    private String bizName;

    private String bizType;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getAppId() {
        return StringHelper.replaceHideChar(appId);
    }

    public void setAppId(String appId) {
        this.appId = StringHelper.replaceHideChar(appId);
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = StringHelper.replaceHideChar(subAppId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringHelper.replaceHideChar(password);
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUmsTo() {
        return umsTo;
    }

    public void setUmsTo(String umsTo) {
        this.umsTo = umsTo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getAck() {
        return ack;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

}
