/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.send;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiaotiaogift.biz.common.MsgOutEnum;
import com.tiaotiaogift.biz.common.account.AccountService;
import com.tiaotiaogift.biz.common.modem.ModemMessage;
import com.tiaotiaogift.biz.common.modem.ModemProxy;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.ContentUtil;

/**
 * 
 * @author gang
 * @version $Id: ModemSendProxyImpl.java, v 0.1 2013-5-7 上午7:35:14 gang Exp $
 */
public class ModemSendProxyImpl implements SendProxy {

    private Logger         logger = LoggerFactory.getLogger(ModemSendProxyImpl.class);

    @Autowired
    private ModemProxy     modemProxy;

    @Autowired
    private MsgOutMapper   msgOutMapper;

    @Autowired
    private UserMapper     userMapper;

    @Autowired
    private AccountService accountService;

    /** 
     * @see com.tiaotiaogift.biz.common.send.SendProxy#send(com.tiaotiaogift.common.mysql.dataobject.MsgOut)
     */
    public boolean send(MsgOut out) {
        if (modemProxy != null && modemProxy.isStart()) {
            ModemMessage mm = new ModemMessage();
            mm.setContent(out.getContent());
            mm.setMobilePhone(out.getRecvId());
            try {
                boolean res = modemProxy.send(mm);

                if (res) {
                    MsgOut o = new MsgOut();
                    o.setId(out.getId());
                    o.setStatus(MsgOutEnum.success.getValue());
                    o.setGmtModify(new Date());
                    msgOutMapper.updateByPrimaryKeySelective(o);
                    logger.info("send success! msg [" + out + "]");
                    User user = userMapper.selectByPrimaryKey(out.getSendId());
                    if (user.getGrade() != null && user.getGrade() == 5) {
                        accountService.sendSuccess(out.getSendId(),
                            ContentUtil.getSmsCount(1, out.getContent()));
                        logger.info("发送成功，余额变动 用户 [" + user + "]余额减少1");
                    }
                } else {
                    MsgOut o = new MsgOut();
                    o.setId(out.getId());
                    o.setStatus(MsgOutEnum.fail.getValue());
                    o.setGmtModify(new Date());
                    msgOutMapper.updateByPrimaryKeySelective(o);
                    logger.warn("send faild! msg [" + out + "]");
                    User user = userMapper.selectByPrimaryKey(out.getSendId());
                    if (user.getGrade() != null && user.getGrade() == 5) {
                        accountService.sendFail(out.getSendId(),
                            ContentUtil.getSmsCount(1, out.getContent()));
                        logger.info("发送失败，余额变动 用户 [" + user + "]余额增加1");
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return false;
    }

}
