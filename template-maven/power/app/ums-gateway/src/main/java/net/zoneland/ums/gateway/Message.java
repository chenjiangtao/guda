/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway;

import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gang
 * @version $Id: Message.java, v 0.1 2012-9-1 下午5:12:46 gang Exp $
 */
public class Message implements java.io.Serializable {

    private static final long serialVersionUID = 5175618219449312810L;

    private UUID              uuid             = UUID.randomUUID();

    private Boolean           result;

    public Boolean isResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    private String   gatewayId;

    private String[] recvId;

    private String   sendId;

    private String   content;

    private String   feeType;

    private String   fee;

    private Integer  contentType;

    private Integer  ack;

    private Integer  priority;

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String[] getRecvId() {
        return recvId;
    }

    public void setRecvId(String[] recvId) {
        this.recvId = recvId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public synchronized void waitResponse(int sec) {
        if (result == null) {
            try {
                wait(sec * 1000);
            } catch (InterruptedException interruptedexception) {
            }

        }
    }

    public synchronized void onReceive(Boolean res) {
        this.result = res;
        notifyAll();
    }

}
