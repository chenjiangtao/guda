/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.form;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gag
 * @version $Id: SgipMsg.java, v 0.1 2012-12-10 上午9:02:39 gag Exp $
 */
public class SgipMsg {
    private String  SPNumber;
    private String  ChargeNumber;
    private String  UserNumber;
    private String  CorpId;
    private String  ServiceType;
    private Integer FeeType;
    private String  FeeValue;
    private String  GivenValue;
    private Integer AgentFlag;
    private Integer MorelatetoMTFlag;
    private Integer Priority;
    private Date    ExpireTime;
    private Date    ScheduleTime;
    private Integer ReportFlag;
    private Integer TP_pid;
    private Integer TP_udhi;
    private Integer MessageCoding;
    private Integer MessageType;
    private Integer MessageLen;
    private String  MessageContent;
    private String  reserve;

    private String  gatewayId;

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getSPNumber() {
        return SPNumber;
    }

    public void setSPNumber(String sPNumber) {
        SPNumber = sPNumber;
    }

    public String getChargeNumber() {
        return ChargeNumber;
    }

    public void setChargeNumber(String chargeNumber) {
        ChargeNumber = chargeNumber;
    }

    public String getUserNumber() {
        return UserNumber;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }

    public String getCorpId() {
        return CorpId;
    }

    public void setCorpId(String corpId) {
        CorpId = corpId;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public Integer getFeeType() {
        return FeeType;
    }

    public void setFeeType(Integer feeType) {
        FeeType = feeType;
    }

    public String getFeeValue() {
        return FeeValue;
    }

    public void setFeeValue(String feeValue) {
        FeeValue = feeValue;
    }

    public String getGivenValue() {
        return GivenValue;
    }

    public void setGivenValue(String givenValue) {
        GivenValue = givenValue;
    }

    public Integer getAgentFlag() {
        return AgentFlag;
    }

    public void setAgentFlag(Integer agentFlag) {
        AgentFlag = agentFlag;
    }

    public Integer getMorelatetoMTFlag() {
        return MorelatetoMTFlag;
    }

    public void setMorelatetoMTFlag(Integer morelatetoMTFlag) {
        MorelatetoMTFlag = morelatetoMTFlag;
    }

    public Integer getPriority() {
        return Priority;
    }

    public void setPriority(Integer priority) {
        Priority = priority;
    }

    public Date getExpireTime() {
        return ExpireTime;
    }

    public void setExpireTime(Date expireTime) {
        ExpireTime = expireTime;
    }

    public Date getScheduleTime() {
        return ScheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        ScheduleTime = scheduleTime;
    }

    public Integer getReportFlag() {
        return ReportFlag;
    }

    public void setReportFlag(Integer reportFlag) {
        ReportFlag = reportFlag;
    }

    public Integer getTP_pid() {
        return TP_pid;
    }

    public void setTP_pid(Integer tP_pid) {
        TP_pid = tP_pid;
    }

    public Integer getTP_udhi() {
        return TP_udhi;
    }

    public void setTP_udhi(Integer tP_udhi) {
        TP_udhi = tP_udhi;
    }

    public Integer getMessageCoding() {
        return MessageCoding;
    }

    public void setMessageCoding(Integer messageCoding) {
        MessageCoding = messageCoding;
    }

    public Integer getMessageType() {
        return MessageType;
    }

    public void setMessageType(Integer messageType) {
        MessageType = messageType;
    }

    public Integer getMessageLen() {
        return MessageLen;
    }

    public void setMessageLen(Integer messageLen) {
        MessageLen = messageLen;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

}
