package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsMsgOut {
    private String  id;

    private String  batchNo;

    private String  serialNo;

    private String  sendId;

    private String  userId;

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

    private Integer retry;

    private Integer priority;

    private String  batchMode;

    private Integer contentMode;

    private String  timeSetFlag;

    private Date    setTime;

    private String  replyDes;

    private Integer fee;

    private Integer feeType;

    private String  msgId;

    private Integer docount;

    private Date    sendTime;

    private Date    validTime;

    private String  groupSerial;

    private String  orgNo;

    private String  flowNo;

    private String  createUser;

    private String  bizType;

    private String  bizName;

    private String  identity;

    private String  mobileStatus;

    private String  templateId;

    private String  host;

    private Date    gmtCreated;

    private Date    gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId == null ? null : recvId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
        this.status = status == null ? null : status.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getSubApp() {
        return subApp;
    }

    public void setSubApp(String subApp) {
        this.subApp = subApp == null ? null : subApp.trim();
    }

    public String getAppSerialNo() {
        return appSerialNo;
    }

    public void setAppSerialNo(String appSerialNo) {
        this.appSerialNo = appSerialNo == null ? null : appSerialNo.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode == null ? null : retCode.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
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
        this.reply = reply == null ? null : reply.trim();
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getBatchMode() {
        return batchMode;
    }

    public void setBatchMode(String batchMode) {
        this.batchMode = batchMode == null ? null : batchMode.trim();
    }

    public Integer getContentMode() {
        return contentMode;
    }

    public void setContentMode(Integer contentMode) {
        this.contentMode = contentMode;
    }

    public String getTimeSetFlag() {
        return timeSetFlag;
    }

    public void setTimeSetFlag(String timeSetFlag) {
        this.timeSetFlag = timeSetFlag == null ? null : timeSetFlag.trim();
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public String getReplyDes() {
        return replyDes;
    }

    public void setReplyDes(String replyDes) {
        this.replyDes = replyDes == null ? null : replyDes.trim();
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public Integer getDocount() {
        return docount;
    }

    public void setDocount(Integer docount) {
        this.docount = docount;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public String getGroupSerial() {
        return groupSerial;
    }

    public void setGroupSerial(String groupSerial) {
        this.groupSerial = groupSerial == null ? null : groupSerial.trim();
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName == null ? null : bizName.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus == null ? null : mobileStatus.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}