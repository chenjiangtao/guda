/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.send;

import net.zoneland.ums.biz.msg.queue.QueueMessage;

/**
 * 
 * @author gang
 * @version $Id: AfterMsgSend.java, v 0.1 2012-9-8 上午10:54:14 gang Exp $
 */
public interface AfterMsgSend {

    /**
     * 消息发送成功的处理
     * 包括：
     * 1）如果是需要回复的，如果是应用产生的消息，需要生成回复号
     * 2）如果是需要回执的，分两种情况，应用的回执和个人的回执
     *     应用回执则生成回执记录保存到msg_in
     *     个人的回执则生成回执短信，并发送
     * @param message
     * @return
     */
    boolean afterSendSuccess(QueueMessage message);

}
