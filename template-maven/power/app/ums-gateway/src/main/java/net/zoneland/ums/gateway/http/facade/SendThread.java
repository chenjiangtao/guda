/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway.http.facade;

import java.util.concurrent.Callable;

import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.MessageFactory;
import net.zoneland.ums.gateway.ThreadMonitor;

/**
 * 
 * @author gang
 * @version $Id: SendThread.java, v 0.1 2013-4-12 上午10:59:30 gang Exp $
 */
public class SendThread implements Callable<Boolean> {

    private MessageFactory messageFactory;

    private Message        msg;

    public SendThread(MessageFactory messageFactory, Message msg) {
        this.msg = msg;
        this.messageFactory = messageFactory;
    }

    /** 
     * @see java.util.concurrent.Callable#call()
     */
    public Boolean call() throws Exception {
        return doSend(msg);
    }

    public Boolean doSend(Message msg) throws Exception {
        //监控线程的执行时间，对于超时的线程进行中断，默认8秒超时
        ThreadMonitor m = new ThreadMonitor(Thread.currentThread());
        m.start();
        try {

            return messageFactory.sendMsg(msg);
        } finally {
            m.stop();
        }

    }

}
