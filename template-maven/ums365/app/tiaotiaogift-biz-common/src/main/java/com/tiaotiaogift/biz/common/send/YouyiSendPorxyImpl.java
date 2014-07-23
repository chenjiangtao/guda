/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.send;

import com.tiaotiaogift.biz.common.gateway.SmsService;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;

/**
 * 
 * @author gang
 * @version $Id: YouyiSendPorxyImpl.java, v 0.1 2013-5-7 上午9:17:57 gang Exp $
 */
public class YouyiSendPorxyImpl implements SendProxy {

    private SmsService smsService;

    /** 
     * @see com.tiaotiaogift.biz.common.send.SendProxy#send(com.tiaotiaogift.common.mysql.dataobject.MsgOut)
     */
    public boolean send(MsgOut msgOut) {

        return false;
    }

}
