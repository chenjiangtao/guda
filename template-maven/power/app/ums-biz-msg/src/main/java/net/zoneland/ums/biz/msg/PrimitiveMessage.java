/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg;

import java.util.Date;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.msg.IdentityEnum;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author wangyong
 * @version $Id: PrimitiveMessage.java, v 0.1 2012-8-13 上午10:39:37 wangyong Exp $
 */
public class PrimitiveMessage {

    /**批号YYYYMMDDHHMMSS*/
    private String  batchNo;

    /** 消息发送方手机号 */
    private String  sendId;

    /**消息发送方Id*/
    private String  userId;

    /** 消息接收方 */
    private String  recvId;

    /** 消息所属应用ID */
    private String  appId;

    /** 消息指定的发送日期 */
    private Date    sendTime;

    /** 消息内容 */
    private String  content;

    /** 错误等描述 */
    private String  comments;

    /**消息ID*/
    private String  msgId;

    private Integer ack;

    /**渠道类型*/
    private String  mediaId;

    /**消息优先级*/
    private Integer priority;

    private String  reply;

    /**消息发送名义*/
    private String  identity;

    /**消息类型*/
    private Integer msgType;

    /**群发短信标识*/
    private String  groupSerial;

    /**有效时间*/
    private Date    validTime;

    private String  replyDes;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupSerial() {
        return groupSerial;
    }

    public void setGroupSerial(String groupSerial) {
        this.groupSerial = groupSerial;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    /**
     * Getter method for property <tt>replyDes</tt>.
     *
     * @return property value of replyDes
     */
    public String getReplyDes() {
        return replyDes;
    }

    /**
     * Setter method for property <tt>replyDes</tt>.
     *
     * @param replyDes value to be assigned to property replyDes
     */
    public void setReplyDes(String replyDes) {
        this.replyDes = replyDes;
    }

    public UmsMsgOut cloneUmsMsgOut() {
        UmsMsgOut umsMsgOut = new UmsMsgOut();
        ObjectBuilder.copyObject(this, umsMsgOut);
        String identity = this.getIdentity();
        String content = "";
        if (this.appId.equalsIgnoreCase(UmsConstants.APP_ID)) {
            if (identity != null) {
                if (identity.equalsIgnoreCase(IdentityEnum.person.getValue())) {
                    content = this.getContent() + "["
                              + SecurityContextHolder.getContext().getPrincipal().getUserName()
                              + "][浙江省电力]";
                } else if (identity.equalsIgnoreCase(IdentityEnum.org.getValue())) {
                    content = this.getContent() + "[组织名字][浙江省电力]";
                }
                umsMsgOut.setContent(content);
            }
        }
        umsMsgOut.setDocount(0);
        return umsMsgOut;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
