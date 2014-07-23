/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.gateway.client;

import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.Result;

/**
 * 
 * @author gag
 * @version $Id: SendMsgClient.java, v 0.1 2012-9-3 下午2:31:05 gag Exp $
 */
public interface SendMsgClient {

    Result sendMsg(Message msg);

}
