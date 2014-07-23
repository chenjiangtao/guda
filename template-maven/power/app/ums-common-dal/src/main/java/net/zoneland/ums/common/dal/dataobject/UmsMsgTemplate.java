package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsMsgTemplate {

	private String id;

	private String templateId;

	private String type;

	private String content;

	private String msgType;

	private String appId;

	private String subAppId;

	private Integer priority;

	private Date startTime;

	private Date endTime;

	private String recvComments;

	private String validTimeScope;

	private String errorAction;

	private String bizType;

	private String bizName;

	private String subBizType;

	private Date gmtCreated;

	private Date gmtModified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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
		this.validTimeScope = validTimeScope == null ? null : validTimeScope
				.trim();
	}

	public String getErrorAction() {
		return errorAction;
	}

	public void setErrorAction(String errorAction) {
		this.errorAction = errorAction == null ? null : errorAction.trim();
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}