/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process.impl;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;

/**
 * 
 * @author gang
 * @version $Id: ActiveTestImpl.java, v 0.1 2012-8-26 下午2:57:38 gang Exp $
 */
public class ActiveTestProcessImpl implements SocketProcess {

    /** 
     * @see net.zoneland.ums.service.process.SocketProcess#doProcess(net.zoneland.ums.service.ServiceRequest)
     */
    public ProcessResult doProcess(ServiceRequest serviceRequest) {
        return new ProcessResult(true, CodeConstants.SUCCESS);
    }

}
