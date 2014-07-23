/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.gateway.fetch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.tiaotiaogift.biz.common.gateway.SmsService;

/**
 * 
 * @author gang
 * @version $Id: FetchResponse.java, v 0.1 2013-5-7 下午12:12:56 gang Exp $
 */
public class FetchResponse implements InitializingBean {

    private Logger                 logger = LoggerFactory.getLogger(FetchResponse.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private SmsService             smsService;

    public void fetch() {

    }

    class FetchThread implements Runnable {

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            while (true) {
                try {
                    smsService.recvSms();
                } catch (Exception e) {
                    logger.error("", e);
                }
                try {
                    smsService.recv106Sms();
                } catch (Exception e) {
                    logger.error("", e);
                }

                try {
                    Thread.sleep(20 * 1000);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
        }

    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {

        taskExecutor.execute(new FetchThread());
    }

}
