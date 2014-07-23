/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway.http.facade;

import java.util.List;

import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.Result;

/**
 * 
 * @author Administrator
 * @version $Id: GatewayWebService.java, v 0.1 2013-1-9 下午5:26:24 Administrator Exp $
 */
public interface GatewayWebService {

    /**
     * 发送消息
     * @param msg
     * @return
     */
    Result sendMsg(Message msg);
    
    
    List<Result> sendMsgBatch(List<Message> msgs);

    /**
     * 关闭网关
     * @param id 网关ID
     * @return
     */
    Result closeGateway(String id);

    /**
     * 开启网关
     * @param id 网关ID
     * @return
     */
    Result openGateway(String id);

    /**
     * 查询网关是否正常
     * @param id
     * @return
     */
    boolean isOpen(String id);

    List<Boolean> isOpen(List<String> list);

}
