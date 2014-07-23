/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.gateway.client;

import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.Result;
import net.zoneland.ums.gateway.http.facade.GatewayFacade;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: SendMsgClientImpl.java, v 0.1 2012-9-3 下午2:58:04 gag Exp $
 */
public class SendMsgClientImpl implements SendMsgClient {

    @Autowired
    private GatewayFacade gatewayFacade;

    /** 
     * @see net.zoneland.ums.service.gateway.client.SendMsgClient#sendMsg(net.zoneland.ums.gateway.Message)
     */
    public Result sendMsg(Message msg) {
        //return sendProxy.sendMsg(msg);

        return gatewayFacade.sendMsg(msg);
    }

    public void setGatewayFacade(GatewayFacade gatewayFacade) {
        this.gatewayFacade = gatewayFacade;
    }

}
