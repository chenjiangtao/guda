/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.appadmin.form;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author wangyong
 * @version $Id: MsgTemplateForm.java, v 0.1 2012-10-12 下午4:07:44 wangyong Exp $
 */
public class MsgTemplateForm {

    private String id;

    @Pattern(regexp = "^[0-9a-zA-Z]{1,10}$", message = "模版Id必须为0-10位数字")
    private String templateId;

    @Pattern(regexp = "^[1-3]{1}$", message = "模版类型1-3的数字")
    private String type;

    @NotEmpty(message = "模版内容不能容为空")
    @Size(max = 600, message = "长度不能600")
    private String content;

    @Pattern(regexp = "^[1-3]?$", message = "短信类型1-3的数字")
    private String msgType;

    @Pattern(regexp = "^[0-9]{4}$", message = "应用Id必须为4位数字")
    private String appId;

    @Pattern(regexp = "^[0-9]{4}|[0-9]{0}$", message = "子应用Id必须为4位数字")
    private String subAppId;

    @Pattern(regexp = "^[0-9]{0,3}$", message = "优先级为0-999的数字")
    private String priority;

    private Date   startTime;

    private Date   endTime;

    @Size(max = 160, message = "长度不能超过160")
    private String recvComments;

    private String validTimeScopeStart;

    private String validTimeScopeEnd;

    private String validTimeScope;

    @Size(max = 50, message = "长度不能超过50")
    private String bizType;

    @Size(max = 50, message = "长度不能超过50")
    private String bizName;

    @Size(max = 50, message = "长度不能超过50")
    private String subBizType;

    @Pattern(regexp = "^[1-2]?$", message = "异常处理为1-2的数字")
    private String errorAction;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId == null ? null : subAppId.trim();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRecvComments() {
        return recvComments;
    }

    public void setRecvComments(String recvComments) {
        this.recvComments = recvComments == null ? null : recvComments.trim();
    }

    public String getValidTimeScope() {
        return validTimeScope;
    }

    public void setValidTimeScope(String validTimeScope) {
        this.validTimeScope = validTimeScope == null ? null : validTimeScope;
    }

    public String getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(String errorAction) {
        this.errorAction = errorAction == null ? null : errorAction.trim();
    }

    public UmsMsgTemplate cloneUmsMsgTemplate() {
        UmsMsgTemplate umsMsgTemplate = new UmsMsgTemplate();
        if ("".equalsIgnoreCase(this.priority)) {
            priority = null;
        }
        if (StringUtils.isNotEmpty(this.validTimeScopeStart)
            || StringUtils.isNotEmpty(this.validTimeScopeEnd)) {
            this.validTimeScope = validTimeScopeStart + "-" + validTimeScopeEnd;
        }
        ObjectBuilder.copyObject(this, umsMsgTemplate);
        if (priority != null) {
            umsMsgTemplate.setPriority(Integer.valueOf(this.priority));
        }
        return umsMsgTemplate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id
     *            value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getSubBizType() {
        return subBizType;
    }

    public void setSubBizType(String subBizType) {
        this.subBizType = subBizType;
    }

    /**
     * Getter method for property <tt>validTimeScopeStart</tt>.
     * 
     * @return property value of validTimeScopeStart
     */
    public String getValidTimeScopeStart() {
        return validTimeScopeStart;
    }

    /**
     * Setter method for property <tt>validTimeScopeStart</tt>.
     * 
     * @param validTimeScopeStart
     *            value to be assigned to property validTimeScopeStart
     */
    public void setValidTimeScopeStart(String validTimeScopeStart) {
        this.validTimeScopeStart = validTimeScopeStart;
    }

    /**
     * Getter method for property <tt>validTimeScopeEnd</tt>.
     * 
     * @return property value of validTimeScopeEnd
     */
    public String getValidTimeScopeEnd() {
        return validTimeScopeEnd;
    }

    /**
     * Setter method for property <tt>validTimeScopeEnd</tt>.
     * 
     * @param validTimeScopeEnd
     *            value to be assigned to property validTimeScopeEnd
     */
    public void setValidTimeScopeEnd(String validTimeScopeEnd) {
        this.validTimeScopeEnd = validTimeScopeEnd;
    }

}
