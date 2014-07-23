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
 * @version $Id: SmgpMsg.java, v 0.1 2012-12-10 上午9:06:11 gag Exp $
 */
public class SmgpMsg {
    private Integer msgType;
    private Integer needReport;
    private Integer priority;
    private String  serviceId;
    private String  feeType;
    private String  feeCode;
    private String  fixedFee;
    private Integer msgFormat;
    private Date    validTime;
    private Date    atTime;
    private String  srcTermId;
    private String  chargeTermId;
    private String  destTermId;
    private String  msgContent;
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

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getNeedReport() {
        return needReport;
    }

    public void setNeedReport(Integer needReport) {
        this.needReport = needReport;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public String getFixedFee() {
        return fixedFee;
    }

    public void setFixedFee(String fixedFee) {
        this.fixedFee = fixedFee;
    }

    public Integer getMsgFormat() {
        return msgFormat;
    }

    public void setMsgFormat(Integer msgFormat) {
        this.msgFormat = msgFormat;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getAtTime() {
        return atTime;
    }

    public void setAtTime(Date atTime) {
        this.atTime = atTime;
    }

    public String getSrcTermId() {
        return srcTermId;
    }

    public void setSrcTermId(String srcTermId) {
        this.srcTermId = srcTermId;
    }

    public String getChargeTermId() {
        return chargeTermId;
    }

    public void setChargeTermId(String chargeTermId) {
        this.chargeTermId = chargeTermId;
    }

    public String getDestTermId() {
        return destTermId;
    }

    public void setDestTermId(String destTermId) {
        this.destTermId = destTermId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

}
