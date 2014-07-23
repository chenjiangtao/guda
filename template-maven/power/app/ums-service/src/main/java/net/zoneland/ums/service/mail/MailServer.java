/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
 * @author gag
 * @version $Id: MailServer.java, v 0.1 2012-8-29 上午8:10:54 gag Exp $
 */
public class MailServer {

    private static final Logger    logger = Logger.getLogger(MailServer.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private MailReceiveHelper      mailReceiveHelper;

    private String                 dealInitServer;

    public void startup() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            logger.error("", e1);
        }
        logger.info("当前服务器hostname：" + hostName);
        if (StringUtils.isNotEmpty(dealInitServer)) {
            if (dealInitServer.indexOf(hostName) != -1) {
                taskExecutor.execute(new Runnable() {
                    public void run() {
                        for (;;) {
                            try {
                                if (logger.isInfoEnabled()) {
                                    logger.info("处理邮件接口");
                                }
                                mailReceiveHelper.receiveAndProcessMsg();
                                if (logger.isInfoEnabled()) {
                                    logger.info("处理邮件接口完成");
                                }
                            } catch (Exception e) {
                                logger.error("处理邮件错误", e);
                            }
                            try {
                                Thread.sleep(60 * 1000);
                            } catch (InterruptedException e) {
                                logger.error("", e);
                            }
                        }
                    }
                });
            }
        }
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setMailReceiveHelper(MailReceiveHelper mailReceiveHelper) {
        this.mailReceiveHelper = mailReceiveHelper;
    }

    public void setDealInitServer(String dealInitServer) {
        this.dealInitServer = dealInitServer;
    }

}