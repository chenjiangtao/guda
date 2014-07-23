package com.tiaotiaogift.common.mysql.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Contact {
    private String id;

    private String name;

    private String phone;

    private String email;

    private String userId;

    private String address;

    private String taobaoName;

    private String taobaoId;

    private String taobaoOrderStatus;

    private String paipaiId;

    private String paipaiName;

    private String paipaiOrderStatus;

    private String deliveryNo;

    private String deliveryName;

    private Date   gmtCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTaobaoName() {
        return taobaoName;
    }

    public void setTaobaoName(String taobaoName) {
        this.taobaoName = taobaoName == null ? null : taobaoName.trim();
    }

    public String getTaobaoId() {
        return taobaoId;
    }

    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId == null ? null : taobaoId.trim();
    }

    public String getTaobaoOrderStatus() {
        return taobaoOrderStatus;
    }

    public void setTaobaoOrderStatus(String taobaoOrderStatus) {
        this.taobaoOrderStatus = taobaoOrderStatus == null ? null : taobaoOrderStatus.trim();
    }

    public String getPaipaiId() {
        return paipaiId;
    }

    public void setPaipaiId(String paipaiId) {
        this.paipaiId = paipaiId == null ? null : paipaiId.trim();
    }

    public String getPaipaiName() {
        return paipaiName;
    }

    public void setPaipaiName(String paipaiName) {
        this.paipaiName = paipaiName == null ? null : paipaiName.trim();
    }

    public String getPaipaiOrderStatus() {
        return paipaiOrderStatus;
    }

    public void setPaipaiOrderStatus(String paipaiOrderStatus) {
        this.paipaiOrderStatus = paipaiOrderStatus == null ? null : paipaiOrderStatus.trim();
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo == null ? null : deliveryNo.trim();
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName == null ? null : deliveryName.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}