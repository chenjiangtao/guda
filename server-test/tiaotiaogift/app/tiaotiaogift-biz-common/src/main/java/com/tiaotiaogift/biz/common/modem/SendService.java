/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.modem;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.tiaotiaogift.biz.common.MsgOutEnum;
import com.tiaotiaogift.biz.common.account.AccountService;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.ContentUtil;

/**
 * 
 * @author gag
 * @version $Id: SendService.java, v 0.1 2012-12-18 下午1:53:53 gag Exp $
 */
public class SendService implements InitializingBean {

    private Logger                 logger = LoggerFactory.getLogger(SendService.class);

    @Autowired
    private MsgOutMapper           msgOutMapper;

    @Autowired
    private ModemProxy             modemProxy;

    @Autowired
    private AccountService         accountService;

    @Autowired
    private UserMapper             userMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public SendService() {

    }

    class SendThread implements Runnable {

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            while (true) {
                if (modemProxy != null && modemProxy.isStart()) {
                    List<MsgOut> outs = msgOutMapper.selectWait();
                    if (outs.size() > 0) {
                        Iterator<MsgOut> it = outs.iterator();
                        while (it.hasNext()) {
                            MsgOut out = it.next();
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

                            try {
                                Thread.sleep(30 * 1000);
                            } catch (InterruptedException e) {
                                logger.error("", e);
                            }
                        }
                    } else {
                        logger.info("no msg need send");
                        try {
                            Thread.sleep(8 * 1000);
                        } catch (InterruptedException e) {
                            logger.error("", e);
                        }
                    }
                }
            }
        }

    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        taskExecutor.execute(new SendThread());

    }

}
