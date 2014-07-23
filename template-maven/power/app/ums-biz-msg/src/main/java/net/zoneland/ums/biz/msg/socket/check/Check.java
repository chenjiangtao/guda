/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.check;

import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;

/**
 * 
 * @author gang
 * @version $Id: Check.java, v 0.1 2012-8-12 上午7:30:41 gang Exp $
 */
public interface Check {

    ProcessResult check(ServiceRequest serviceRequest);

}