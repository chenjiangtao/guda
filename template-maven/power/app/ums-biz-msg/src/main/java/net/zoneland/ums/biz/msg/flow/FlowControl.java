/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.flow;

import java.util.Date;

/**
 *
 * @author gag
 * @version $Id: FlowControl.java, v 0.1 2012-8-27 上午11:14:19 gag Exp $
 */
public interface FlowControl {

    /**
     * 判断发送msgCount数量的消息,是否超过了流量，
     * 如果没超过则将该数量增加到数据中。
     * 返回 -1 表示没有配置流量阀值，不需要检查
     * 返回0表示没有超过流量
     * 返回大于0表示超过流量，返回值为超过的流量
     * @param appId
     * @param msgCount
     * @return
     */
    int checkFlow(String appId, int msgCount);

    /**
     * 针对定时发送的消息的流量检查
     * 返回 -1 表示没有配置流量阀值，不需要检查
     * 返回0表示没有超过流量
     * 返回大于0表示超过流量，返回值为超过的流量
     * @param appId
     * @param msgCount
     * @param sendTime
     * @return
     */
    int checkFlowWithSendTime(String appId, int msgCount, Date sendTime);

}
