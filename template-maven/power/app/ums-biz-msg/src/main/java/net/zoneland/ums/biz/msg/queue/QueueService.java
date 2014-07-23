/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.queue;

import java.util.Collection;

/**
 *
 * @author wangyong
 * @version $Id: QueueService.java, v 0.1 2012-8-24 下午9:51:52 wangyong Exp $
 */
public interface QueueService {

    /**
     *群发短信加入对队
     *
     * @param umsMsgOutList
     * @return
     */
    public boolean offerMessageList(Collection<QueueMessage> queueMessageList);

    public boolean offerMessageListNoUpdate(Collection<QueueMessage> queueMessageList);

    /**
     *消息放入队列
     *
     * @param queueMessage
     * @return
     */
    public boolean offerQueueMessage(QueueMessage queueMessage);
}
