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
 * @version $Id: Smgp3Msg.java, v 0.1 2012-12-10 上午9:27:12 gag Exp $
 */
public class Smgp3Msg {

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
    private Integer tp_pid;
    private Integer tp_udhi;
    private String  linkId;
    private String  msgSrc;
    private Integer ChargeUserType;
    private Integer ChargeTermType;
    private String  ChargeTermPseudo;
    private Integer DestTermType;
    private String  DestTermPseudo;
    private Integer pkTotal;
    private Integer pkNumber;
    private Integer submitMsgType;
    private Integer spDealResult;
    private String  mserviceId;

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

    public Integer getTp_pid() {
        return tp_pid;
    }

    public void setTp_pid(Integer tp_pid) {
        this.tp_pid = tp_pid;
    }

    public Integer getTp_udhi() {
        return tp_udhi;
    }

    public void setTp_udhi(Integer tp_udhi) {
        this.tp_udhi = tp_udhi;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getMsgSrc() {
        return msgSrc;
    }

    public void setMsgSrc(String msgSrc) {
        this.msgSrc = msgSrc;
    }

    public Integer getChargeUserType() {
        return ChargeUserType;
    }

    public void setChargeUserType(Integer chargeUserType) {
        ChargeUserType = chargeUserType;
    }

    public Integer getChargeTermType() {
        return ChargeTermType;
    }

    public void setChargeTermType(Integer chargeTermType) {
        ChargeTermType = chargeTermType;
    }

    public String getChargeTermPseudo() {
        return ChargeTermPseudo;
    }

    public void setChargeTermPseudo(String chargeTermPseudo) {
        ChargeTermPseudo = chargeTermPseudo;
    }

    public Integer getDestTermType() {
        return DestTermType;
    }

    public void setDestTermType(Integer destTermType) {
        DestTermType = destTermType;
    }

    public String getDestTermPseudo() {
        return DestTermPseudo;
    }

    public void setDestTermPseudo(String destTermPseudo) {
        DestTermPseudo = destTermPseudo;
    }

    public Integer getPkTotal() {
        return pkTotal;
    }

    public void setPkTotal(Integer pkTotal) {
        this.pkTotal = pkTotal;
    }

    public Integer getPkNumber() {
        return pkNumber;
    }

    public void setPkNumber(Integer pkNumber) {
        this.pkNumber = pkNumber;
    }

    public Integer getSubmitMsgType() {
        return submitMsgType;
    }

    public void setSubmitMsgType(Integer submitMsgType) {
        this.submitMsgType = submitMsgType;
    }

    public Integer getSpDealResult() {
        return spDealResult;
    }

    public void setSpDealResult(Integer spDealResult) {
        this.spDealResult = spDealResult;
    }

    public String getMserviceId() {
        return mserviceId;
    }

    public void setMserviceId(String mserviceId) {
        this.mserviceId = mserviceId;
    }

}
