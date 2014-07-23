/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 *接收消息后负责解析消息，包括消息接收人，接收规则,并存储消息.
 * @author wangyong
 * @version $Id: ResolveRecieveMsg.java, v 0.1 2012-8-14 上午10:12:17 wangyong Exp $
 */
public interface ResolveMsgService {

    /**
     *解析消息，初步分为，正确的消息与错误的消息。
     *正确的消息是：接收方是手机号及用户ID，群组ID，组织ID组成的消息
     *错误的消息：不是正确接收方的就是错误的消息
     * @param primitiveMessage
     * @param umsMsgOutList
     * @param errorUmsMsgOut
     * @return
     */
    public Map<String, List<UmsMsgOut>> gerUmsMsgOutList(PrimitiveMessage primitiveMessage);
}
