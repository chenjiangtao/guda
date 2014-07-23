/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import org.apache.log4j.Logger;

import com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener;

/**
 * 
 * @author foodoon
 * @version $Id: ConnectionLifeCycleListenerImpl.java, v 0.1 2013-7-29 上午8:36:59 foodoon Exp $
 */
public class ConnectionLifeCycleListenerImpl implements ConnectionLifeCycleListener {

    private final static Logger logger = Logger.getLogger(ConnectionLifeCycleListenerImpl.class);

    /** 
     * @see com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener#onBeforeConnect()
     */
    public void onBeforeConnect() {
        logger.info("onBeforeConnect");
    }

    /** 
     * @see com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener#onException(java.lang.Throwable)
     */
    public void onException(Throwable arg0) {
        logger.info("onException", arg0);
    }

    /** 
     * @see com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener#onMaxReadTimeoutException()
     */
    public void onMaxReadTimeoutException() {
        logger.info("onMaxReadTimeoutException");
    }

}
