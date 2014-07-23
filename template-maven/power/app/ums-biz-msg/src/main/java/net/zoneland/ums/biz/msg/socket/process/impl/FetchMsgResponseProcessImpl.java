/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process.impl;

import net.zoneland.ums.biz.msg.in.MsgInService;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gang
 * @version $Id: FetchMsgResponseImpl.java, v 0.1 2012-8-26 下午3:05:45 gang Exp $
 */
public class FetchMsgResponseProcessImpl implements SocketProcess {

    @Autowired
    private MsgInService msgInService;

    /** 
     * @see net.zoneland.ums.service.process.SocketProcess#doProcess(net.zoneland.ums.service.ServiceRequest)
     */
    public ProcessResult doProcess(ServiceRequest serviceRequest) {
        if (serviceRequest == null) {
            return new ProcessResult(true, CodeConstants.newFailure("请求参数为空"));
        } else {
            boolean res = msgInService.fetchMsgSuccess(serviceRequest.getBatchNo(),
                serviceRequest.getSerialNo());
            if (res) {
                //特殊处理成功必须返回0001
                return new ProcessResult(true, CodeConstants.FAILURE);
            } else {
                return new ProcessResult(true, CodeConstants.newFailure("1091", "更新消息状态失败"));
            }
        }
    }

    public void setMsgInService(MsgInService msgInService) {
        this.msgInService = msgInService;
    }

}
