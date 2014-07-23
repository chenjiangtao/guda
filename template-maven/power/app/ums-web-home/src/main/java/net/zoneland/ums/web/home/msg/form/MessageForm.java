/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.msg.form;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.Size;

import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.common.dal.dataobject.UmsMsgDraft;
import net.zoneland.ums.common.util.enums.msg.IdentityEnum;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author wangyong
 * @version $Id: MessageForm.java, v 0.1 2012-8-13 上午10:12:01 wangyong Exp $
 */
public class MessageForm {

    /** 草稿ID */
    private String  draftId;

    /** 消息发送手机号 */
    private String  sendId;

    /**消息发送方*/
    private String  userId;

    /** 消息接收方 */
    @NotEmpty(message = "接收方不能为空")
    @Size(min = 0, max = 1000, message = "接收方长度不能超过300个字")
    private String  recvId;

    /** 消息接收方名字 */

    private String  msgDestName;

    /** 消息指定的发送日期 */

    private Date    sendTime;

    /** 消息内容 */
    @NotEmpty(message = "消息内容不能为空")
    @Size(min = 0, max = 2000, message = "消息内容不能超过600个字符")
    private String  content  = "";

    /**回执回复等*/
    private Integer ack      = 0;

    /**消息发送名义*/
    private String  identity = IdentityEnum.person.getValue();

    /**消息类型*/
    private Integer msgType;

    /**有效时间*/
    private String  validTime;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getDraftId() {
        return draftId;
    }

    /**消息优先级*/
    private Integer priority;

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getMsgDestName() {
        return msgDestName;
    }

    public void setMsgDestName(String msgDestName) {
        this.msgDestName = msgDestName;
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
        this.content = content == null ? null : content.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public PrimitiveMessage clonePrimitiveMessage() {
        PrimitiveMessage primitiveMessage = new PrimitiveMessage();
        ObjectBuilder.copyObject(this, primitiveMessage);
        primitiveMessage.setAppId(UmsConstants.APP_ID);
        primitiveMessage.setGroupSerial(UUID.randomUUID().toString());
        primitiveMessage.setBatchNo(DateHelper.getStrDateTime());
        primitiveMessage.setMsgId("0");
        primitiveMessage.setMsgType(0);
        primitiveMessage.setAck(2);
        if (validTime != null) {
            long time = 0;
            time = Integer.valueOf(validTime) * 60 * 1000;
            if (this.sendTime != null) {
                Date validDate = new Date(this.sendTime.getTime() + time);
                primitiveMessage.setValidTime(validDate);
            } else {
                primitiveMessage.setValidTime(new Date(new Date().getTime() + time));
            }
        }
        return primitiveMessage;
    }

    public UmsMsgDraft cloneUmsMsgDraft() {
        UmsMsgDraft umsMsgDraft = new UmsMsgDraft();
        ObjectBuilder.copyObject(this, umsMsgDraft);
        umsMsgDraft.setId(this.draftId);
        return umsMsgDraft;

    }

}
