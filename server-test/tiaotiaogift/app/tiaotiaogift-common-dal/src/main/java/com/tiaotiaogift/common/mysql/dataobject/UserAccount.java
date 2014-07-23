/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.mysql.dataobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gang
 * @version $Id: UserAccount.java, v 0.1 2013-4-30 下午9:27:09 gang Exp $
 */
public class UserAccount extends User {

    private Integer balance;

    private Integer balanceLock;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalanceLock() {
        return balanceLock;
    }

    public void setBalanceLock(Integer balanceLock) {
        this.balanceLock = balanceLock;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
