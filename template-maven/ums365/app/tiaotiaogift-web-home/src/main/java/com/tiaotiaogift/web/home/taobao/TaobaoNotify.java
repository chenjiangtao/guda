/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;
import com.taobao.api.internal.stream.message.TopCometMessageListener;

/**
 * 
 * @author foodoon
 * @version $Id: TaobaoNotify.java, v 0.1 2013-7-29 上午8:33:13 foodoon Exp $
 */
@Service("taobaoNotify")
public class TaobaoNotify {

    private static Logger           logger = LoggerFactory.getLogger(TaobaoNotify.class);

    @Autowired
    private TopCometMessageListener topCometMessageListener;

    private boolean                 status = false;

    private Lock                    lock   = new ReentrantLock();

    private TopCometStream          stream;

    public void startLisener() {
        lock.lock();
        try {
            if (!status) {
                Configuration conf = new Configuration(TaobaoCallController.appKey,
                    TaobaoCallController.appSecret, null);
                stream = new TopCometStreamFactory(conf).getInstance();
                stream.setConnectionListener(new ConnectionLifeCycleListenerImpl());
                stream.setMessageListener(topCometMessageListener);
                stream.start();
                logger.info("start stream..");
                status = true;
            }
        } finally {
            lock.unlock();
        }
    }

    public void stoplistener() {
        lock.lock();
        try {
            if (status) {
                if (stream != null) {
                    stream.stop();
                    logger.info("stop stream..");
                }
                status = false;
            }
        } finally {
            lock.unlock();
        }
    }

    public void restartlistener() {
        lock.lock();
        try {
            status = true;
            if (stream != null) {
                stream.stop();
            }
            Configuration conf = new Configuration(TaobaoCallController.appKey,
                TaobaoCallController.appSecret, null);
            stream = new TopCometStreamFactory(conf).getInstance();
            stream.setConnectionListener(new ConnectionLifeCycleListenerImpl());
            stream.setMessageListener(topCometMessageListener);
            stream.start();
        } finally {
            lock.unlock();
        }
    }

}
