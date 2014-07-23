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
 * @version $Id: Cmpp30Msg.java, v 0.1 2012-12-10 上午9:00:33 gag Exp $
 */
public class Cmpp30Msg {

    private Integer pk_Total;
    private Integer pk_Number;
    private Integer registered_Delivery;
    private Integer msg_Level;
    private String  service_Id;
    private Integer fee_UserType;
    private String  fee_Terminal_Id;
    private Integer fee_Terminal_Type;
    private Integer tp_Pid;
    private Integer tp_Udhi;
    private Integer msg_Fmt;
    private String  msg_Src;
    private String  fee_Type;
    private String  fee_Code;
    private Date    valid_Time;
    private Date    at_Time;
    private String  src_Terminal_Id;
    private String  dest_Terminal_Id;
    private Integer dest_Terminal_Type;
    private String  msg_Content;
    private String  LinkID;

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

    public Integer getPk_Total() {
        return pk_Total;
    }

    public void setPk_Total(Integer pk_Total) {
        this.pk_Total = pk_Total;
    }

    public Integer getPk_Number() {
        return pk_Number;
    }

    public void setPk_Number(Integer pk_Number) {
        this.pk_Number = pk_Number;
    }

    public Integer getRegistered_Delivery() {
        return registered_Delivery;
    }

    public void setRegistered_Delivery(Integer registered_Delivery) {
        this.registered_Delivery = registered_Delivery;
    }

    public Integer getMsg_Level() {
        return msg_Level;
    }

    public void setMsg_Level(Integer msg_Level) {
        this.msg_Level = msg_Level;
    }

    public String getService_Id() {
        return service_Id;
    }

    public void setService_Id(String service_Id) {
        this.service_Id = service_Id;
    }

    public Integer getFee_UserType() {
        return fee_UserType;
    }

    public void setFee_UserType(Integer fee_UserType) {
        this.fee_UserType = fee_UserType;
    }

    public String getFee_Terminal_Id() {
        return fee_Terminal_Id;
    }

    public void setFee_Terminal_Id(String fee_Terminal_Id) {
        this.fee_Terminal_Id = fee_Terminal_Id;
    }

    public Integer getFee_Terminal_Type() {
        return fee_Terminal_Type;
    }

    public void setFee_Terminal_Type(Integer fee_Terminal_Type) {
        this.fee_Terminal_Type = fee_Terminal_Type;
    }

    public Integer getTp_Pid() {
        return tp_Pid;
    }

    public void setTp_Pid(Integer tp_Pid) {
        this.tp_Pid = tp_Pid;
    }

    public Integer getTp_Udhi() {
        return tp_Udhi;
    }

    public void setTp_Udhi(Integer tp_Udhi) {
        this.tp_Udhi = tp_Udhi;
    }

    public Integer getMsg_Fmt() {
        return msg_Fmt;
    }

    public void setMsg_Fmt(Integer msg_Fmt) {
        this.msg_Fmt = msg_Fmt;
    }

    public String getMsg_Src() {
        return msg_Src;
    }

    public void setMsg_Src(String msg_Src) {
        this.msg_Src = msg_Src;
    }

    public String getFee_Type() {
        return fee_Type;
    }

    public void setFee_Type(String fee_Type) {
        this.fee_Type = fee_Type;
    }

    public String getFee_Code() {
        return fee_Code;
    }

    public void setFee_Code(String fee_Code) {
        this.fee_Code = fee_Code;
    }

    public Date getValid_Time() {
        return valid_Time;
    }

    public void setValid_Time(Date valid_Time) {
        this.valid_Time = valid_Time;
    }

    public Date getAt_Time() {
        return at_Time;
    }

    public void setAt_Time(Date at_Time) {
        this.at_Time = at_Time;
    }

    public String getSrc_Terminal_Id() {
        return src_Terminal_Id;
    }

    public void setSrc_Terminal_Id(String src_Terminal_Id) {
        this.src_Terminal_Id = src_Terminal_Id;
    }

    public String getDest_Terminal_Id() {
        return dest_Terminal_Id;
    }

    public void setDest_Terminal_Id(String dest_Terminal_Id) {
        this.dest_Terminal_Id = dest_Terminal_Id;
    }

    public Integer getDest_Terminal_Type() {
        return dest_Terminal_Type;
    }

    public void setDest_Terminal_Type(Integer dest_Terminal_Type) {
        this.dest_Terminal_Type = dest_Terminal_Type;
    }

    public String getMsg_Content() {
        return msg_Content;
    }

    public void setMsg_Content(String msg_Content) {
        this.msg_Content = msg_Content;
    }

    public String getLinkID() {
        return LinkID;
    }

    public void setLinkID(String linkID) {
        LinkID = linkID;
    }

}
