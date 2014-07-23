/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.send;

import com.tiaotiaogift.common.mysql.dataobject.MsgOut;

/**
 * 
 * @author gang
 * @version $Id: SendProntxy.java, v 0.1 2013-5-7 上午7:34:31 gang Exp 
 */
public interface SendProxy {

    public boolean send(MsgOut msgOut);

}
