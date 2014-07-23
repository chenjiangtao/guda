/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process;

import net.zoneland.ums.biz.msg.socket.ServiceRequest;

/**
 * 
 * @author gag
 * @version $Id: SocketProcess.java, v 0.1 2012-8-10 下午4:43:14 gag Exp $
 */
public interface SocketProcess {

    ProcessResult doProcess(ServiceRequest serviceRequest);

}
