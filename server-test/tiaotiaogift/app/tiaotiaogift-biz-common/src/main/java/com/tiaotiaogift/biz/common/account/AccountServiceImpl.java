/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.account;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiaotiaogift.biz.common.MsgOutEnum;
import com.tiaotiaogift.common.dal.AccountLogMapper;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.common.mysql.dataobject.AccountLog;
import com.tiaotiaogift.common.util.enums.ActionEnum;

/**
 * 
 * @author gang
 * @version $Id: AccountServiceImpl.java, v 0.1 2013-4-30 下午5:44:38 gang Exp $
 */
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper    accountMapper;

    @Autowired
    private AccountLogMapper accountLogMapper;

    @Autowired
    private MsgOutMapper     msgOutMapper;

    /** 
     * @see com.tiaotiaogift.biz.common.account.AccountService#send(java.lang.String, int)
     */
    public boolean send(String userId, int count) {
        Account a = accountMapper.selectByUserId(userId);
        a.setBalance(a.getBalance() - count);
        a.setBalanceLock(a.getBalanceLock() + count);
        return accountMapper.updateByPrimaryKeySelective(a) == 1;

    }

    /** 
     * @see com.tiaotiaogift.biz.common.account.AccountService#sendSuccess(java.lang.String, int)
     */
    public boolean sendSuccess(String userId, int count) {
        Account a = accountMapper.selectByUserId(userId);
        a.setBalanceLock(a.getBalanceLock() - count);

        int rows = accountMapper.updateByPrimaryKeySelective(a);
        if (rows == 1) {
            AccountLog log = new AccountLog();
            log.setId(UUID.randomUUID().toString());
            log.setAction(ActionEnum.decrease.getDescription());
            log.setAmountAfter(a.getBalance());
            log.setAmountBefore(a.getBalance() + count);
            log.setSum(count);
            log.setUserId(a.getUserId());
            log.setGmtCreated(new Date());
            accountLogMapper.insert(log);
            return true;
        }
        return false;
    }

    /** 
     * @see com.tiaotiaogift.biz.common.account.AccountService#sendFail(java.lang.String, int)
     */
    public boolean sendFail(String userId, int count) {
        Account a = accountMapper.selectByUserId(userId);
        a.setBalance(a.getBalance() + count);
        a.setBalanceLock(a.getBalanceLock() - count);
        return accountMapper.updateByPrimaryKeySelective(a) == 1;
    }

    /** 
     * @see com.tiaotiaogift.biz.common.account.AccountService#sendApiSuccess(java.util.List, java.lang.String, int)
     */
    public boolean sendApiSuccess(List<String> msgIds, String userId, int count) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gmtModify", new Date());
        params.put("status", MsgOutEnum.success.getValue());
        params.put("ids", msgIds);
        int res = msgOutMapper.updateStatusBatch(params);
        if (res == msgIds.size()) {
            Account a = accountMapper.selectByUserId(userId);
            a.setBalanceLock(a.getBalanceLock() - count);
            int rows = accountMapper.updateByPrimaryKeySelective(a);
            if (rows == 1) {
                AccountLog log = new AccountLog();
                log.setId(UUID.randomUUID().toString());
                log.setAction(ActionEnum.decrease.getDescription());
                log.setAmountAfter(a.getBalance());
                log.setAmountBefore(a.getBalance() + count);
                log.setSum(count);
                log.setUserId(a.getUserId());
                log.setGmtCreated(new Date());
                accountLogMapper.insert(log);
                return true;
            }
        }
        return false;
    }

    /** 
     * @see com.tiaotiaogift.biz.common.account.AccountService#sendApiFail(java.util.List, java.lang.String, int)
     */
    public boolean sendApiFail(List<String> msgIds, String userId, int count) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gmtModify", new Date());
        params.put("status", MsgOutEnum.fail.getValue());
        params.put("ids", msgIds);
        int res = msgOutMapper.updateStatusBatch(params);
        if (res == msgIds.size()) {
            Account a = accountMapper.selectByUserId(userId);
            a.setBalance(a.getBalance() + count);
            a.setBalanceLock(a.getBalanceLock() - count);
            return accountMapper.updateByPrimaryKeySelective(a) == 1;
        }
        return false;
    }

}
