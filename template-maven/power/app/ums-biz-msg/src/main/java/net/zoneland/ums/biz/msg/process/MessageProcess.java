/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process;

import java.util.List;

import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 * 消息处理中心
 *
 * @author wangyong
 * @version $Id: MessageProcess.java, v 0.1 2012-8-13 下午6:06:32 wangyong Exp $
 */
public interface MessageProcess {

    /**
     * 1）消息前置处理<br/>
     *   A.检查黑名单<br/>
     *   B.检查关键词<br/>
     * 2）消息处理<br/>
     *   A.解析接收方<br/>
     *   B.解析接收方的接收规则<br/>
     *   C.生成消息，并存入sms_msg_send_info，同时将立即发送的消息方法消息队列
     *
     * @param primitiveMessage
     * @return
     */

    boolean process(PrimitiveMessage primitiveMessage);

    /**
     *获取发送的网关的spNumber
     *
     * @param umsMsgOut
     * @return
     */
    UmsGateWayInfo getGateway(UmsMsgOut umsMsgOut);

    boolean processMsg(List<UmsMsgOut> umsMsgOutList);

    /**
     * 处理iphone客户端的请求
     * @param primitiveMessage
     * @return
     */
    public boolean processIphone(PrimitiveMessage primitiveMessage);
}
