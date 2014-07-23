/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.form;

import java.util.Date;

/**
 * 
 * @author gang
 * @version $Id: MessageInVo.java, v 0.1 2012-9-11 下午10:09:31 gang Exp $
 */
public class MessageInVo {

    private String  batchNo;

    private String  serialNo;

    private String  sendId;

    private String  recvId;

    private String  content;

    private Integer msgType;

    private String  status;

    private String  appId;

    private String  subApp;

    private String  appSerialNo;

    private String  mediaId;

    private String  retCode;

    private String  errorMsg;

    private Integer ack;

    private String  reply;

    private Integer docount;

    private Date    gmtCreated;

    private Date    gmtModified;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
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

    public String getAppSerialNo() {
        return appSerialNo;
    }

    public void setAppSerialNo(String appSerialNo) {
        this.appSerialNo = appSerialNo;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getDocount() {
        return docount;
    }

    public void setDocount(Integer docount) {
        this.docount = docount;
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

}
