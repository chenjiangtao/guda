/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.taobao;

import java.util.Date;
import java.util.UUID;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiaotiaogift.biz.common.MsgOutEnum;
import com.tiaotiaogift.biz.common.account.AccountService;
import com.tiaotiaogift.biz.common.gateway.SmsMsg;
import com.tiaotiaogift.biz.common.gateway.SmsService;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.common.util.ContentUtil;

/**
 * 
 * @author foodoon
 * @version $Id: SendMessageServiceImpl.java, v 0.1 2013-8-4 下午6:52:45 foodoon Exp $
 */
@Service("sendMessageService")
public class SendMessageServiceImpl implements SendMessageService {

    private final static Logger logger = Logger.getLogger(SendMessageServiceImpl.class);

    @Autowired
    private MsgOutMapper        msgOutMapper;

    @Autowired
    private AccountMapper       accountMapper;

    @Autowired
    private AccountService      accountService;

    @Autowired
    private SmsService          smsService;

    @Autowired
    private UserMapper          userMapper;

    /** 
     * @see com.tiaotiaogift.biz.common.taobao.SendMessageService#sendMsg(java.lang.String, java.lang.String, java.lang.String)
     */
    public void sendMsg(String userId, String content, String recv) {
        if (logger.isInfoEnabled()) {
            logger.info("send taobao msg:userId:" + userId + ",recv:" + recv + ",content:"
                        + content);
        }
        MsgOut out = new MsgOut();
        out.setId(UUID.randomUUID().toString());
        out.setRecvId(recv);
        out.setSendId(userId);
        out.setContent(content);
        out.setStatus(MsgOutEnum.init.getValue());
        out.setGmtCreated(new Date());
        out.setGmtModify(new Date());
        msgOutMapper.insert(out);

        int trueCount = ContentUtil.getSmsCount(1, content);
        accountService.send(userId, trueCount);
        SmsMsg sms = new SmsMsg();
        sms.setContent(content);
        sms.setRecv(recv);
        sms.setLinkId(String.valueOf(OperationContextHolder.getPrincipal().getAttrVal(
            "LINK_ID_ATTR")));
        String res = smsService.sendSms(sms);

        if ("0".equals(res)) {
            accountService.sendSuccess(userId, trueCount);
        } else {
            accountService.sendFail(userId, trueCount);
        }
    }

}
