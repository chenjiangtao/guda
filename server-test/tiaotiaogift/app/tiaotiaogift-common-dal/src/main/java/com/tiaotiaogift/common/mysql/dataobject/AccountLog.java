package com.tiaotiaogift.common.mysql.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AccountLog {
    private String  id;

    private String  userId;

    private Integer amountBefore;

    private Integer sum;

    private String  action;

    private Integer amountAfter;

    private Date    gmtCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(Integer amountBefore) {
        this.amountBefore = amountBefore;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public Integer getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(Integer amountAfter) {
        this.amountAfter = amountAfter;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}