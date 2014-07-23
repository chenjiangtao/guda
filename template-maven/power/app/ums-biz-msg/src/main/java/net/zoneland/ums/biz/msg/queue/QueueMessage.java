/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.queue;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author wangyong
 * @version $Id: QueueMessage.java, v 0.1 2012-9-2 下午9:14:33 wangyong Exp $
 */
public class QueueMessage {

    /** 接收方手机号 */
    private List<String> recvIdArray;

    private Integer      priority;

    /** 消息ID */
    private List<String> idArray;

    private String       content;    //消息内容

    private String       mediaId;    //通道ID

    private Integer      feeType;

    private Integer      fee;

    private Integer      contentType;

    private Integer      ack;

    private Date         sendTime;

    /** 发送方手机号 */
    private String       sendId;

    private String       replyDes;

    private String       reply;

    private String       appId;

    private String       subAppId;

    private String       appSerialNo;

    private String       batchNo;

    private String       serialNo;

    private String       status;

    /**
     * Getter method for property <tt>recvIdArray</tt>.
     *
     * @return property value of recvIdArray
     */
    public List<String> getRecvIdArray() {
        return recvIdArray;
    }

    /**
     * Setter method for property <tt>recvIdArray</tt>.
     *
     * @param recvIdArray value to be assigned to property recvIdArray
     */
    public void setRecvIdArray(List<String> recvIdArray) {
        this.recvIdArray = recvIdArray;
    }

    /**
     * Getter method for property <tt>priority</tt>.
     *
     * @return property value of priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Setter method for property <tt>priority</tt>.
     *
     * @param priority value to be assigned to property priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Getter method for property <tt>idArray</tt>.
     *
     * @return property value of idArray
     */
    public List<String> getIdArray() {
        return idArray;
    }

    /**
     * Setter method for property <tt>idArray</tt>.
     *
     * @param idArray value to be assigned to property idArray
     */
    public void setIdArray(List<String> idArray) {
        this.idArray = idArray;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReplyDes() {
        return replyDes;
    }

    public void setReplyDes(String replyDes) {
        this.replyDes = replyDes;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSerialNo() {
        return appSerialNo;
    }

    public void setAppSerialNo(String appSerialNo) {
        this.appSerialNo = appSerialNo;
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

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
