/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.queue;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

/**
 *本类主要处理队列的相关消息.</br>
 *1.不同队列的存放.</br>
 *2.队列取消息.
 * @author wangyong
 * @version $Id: QueueFactory.java, v 0.1 2012-8-15 上午11:16:14 wangyong Exp $
 */

public class QueueFactory {

    private static final Logger         logger           = Logger.getLogger(QueueFactory.class);

    private int                         capacity         = 5000;

    @Autowired
    private UmsGateWayInfoMapper        umsGateWayInfoMapper;

    //普通的队列
    ArrayBlockingQueue<QueueMessage>    priorityQueue;

    //专门针对营销的短信
    ArrayBlockingQueue<QueueMessage>    marketQueue;

    //专为营销定时任务发送的队列
    ArrayBlockingQueue<QueueMessage>    delayQueueForMarket;

    //专为数据短信服务的队列
    ArrayBlockingQueue<QueueMessage>    numberQueue;

    //95598短信
    ArrayBlockingQueue<QueueMessage>    queueFor95598;

    //紧急消息队列，用于处理一些比较紧急的短信
    PriorityBlockingQueue<QueueMessage> urgentQueue;

    private final static int            URGENTPRIORITY   = 7;

    private AtomicInteger               QueueCount       = new AtomicInteger(0);

    //记录目前一共多少1112定时短信放入这个队列
    private AtomicInteger               dealyQueueCount  = new AtomicInteger(0);

    //记录目前一共多少1112放入这个队列
    private final AtomicInteger         marketQueueCount = new AtomicInteger(0);

    //记录目前一共多少数据短信放入这个队列
    private final AtomicInteger         numberQueueCount = new AtomicInteger(0);

    //记录紧急队列消息的数量
    private AtomicInteger               urgentQueueCount = new AtomicInteger(0);

    //用来记录今天一共多少消息放入队列
    private AtomicInteger               QueueTotalCount  = new AtomicInteger(0);

    //用来记录今天一共多少消息放入95998队列
    private AtomicInteger               count95598       = new AtomicInteger(0);

    /**
     * 自定义构造函数，设置容量与比较方式。
     * @param capacity
     * @param priorityQueue
     */
    public void init() {
        logger.info("初始化队列容量：" + capacity);
        this.priorityQueue = new ArrayBlockingQueue<QueueMessage>(capacity);
        this.delayQueueForMarket = new ArrayBlockingQueue<QueueMessage>(capacity);
        this.queueFor95598 = new ArrayBlockingQueue<QueueMessage>(capacity);
        this.marketQueue = new ArrayBlockingQueue<QueueMessage>(capacity);
        this.numberQueue = new ArrayBlockingQueue<QueueMessage>(capacity);
        this.urgentQueue = new PriorityBlockingQueue<QueueMessage>(capacity,
            new Comparator<QueueMessage>() {
                public int compare(QueueMessage obj1, QueueMessage obj2) {
                    if (obj1.getPriority() == null || obj2.getPriority() == null) {
                        return 0;
                    }
                    return -(obj1.getPriority() - obj2.getPriority());
                }
            });
    }

    /**
     * 消息保存到队列中，对短信的不同方式进行不同队列的保存
     * @param message
     * @return
     */
    public boolean offer(QueueMessage queueMessage) {
        if (queueMessage == null) {
            return false;
        }
        logger.info("放入队列的短信状态：" + MsgInfoStatusEnum.getDescription(queueMessage.getStatus()));
        queueMessage.setContent(HtmlUtils.htmlUnescape(queueMessage.getContent()));
        int priority = queueMessage.getPriority() == null ? 0 : queueMessage.getPriority();
        //对于优先级大于7的短信放入紧急队列
        if (priority > URGENTPRIORITY) {
            boolean urgentResult = urgentQueue.offer(queueMessage);
            if (urgentResult) {
                urgentQueueCount.getAndIncrement();
                QueueTotalCount.getAndIncrement();
            }
            return urgentResult;
        }
        //针对营销大批量定时短信的单独处理
        if (MsgInfoStatusEnum.wait.getValue().equals(queueMessage.getStatus())
            && UmsConstants.MARKETAPPID.equals(queueMessage.getAppId())) {
            boolean delayResult = delayQueueForMarket.offer(queueMessage);
            if (delayResult) {
                dealyQueueCount.getAndIncrement();
                QueueTotalCount.getAndIncrement();
            }
            return delayResult;
        }
        //针对营销大批量非定时任务短信发送的处理
        if (UmsConstants.MARKETAPPID.equals(queueMessage.getAppId())) {
            boolean result = marketQueue.offer(queueMessage);
            if (result) {
                marketQueueCount.getAndIncrement();
                QueueTotalCount.getAndIncrement();
            }
            return result;
        }
        //针对数据短信发送的处理
        int msgType = queueMessage.getContentType() == null ? 0 : queueMessage.getContentType();
        if (msgType != 15) {
            boolean result = numberQueue.offer(queueMessage);
            if (result) {
                numberQueueCount.getAndIncrement();
                QueueTotalCount.getAndIncrement();
            }
            return result;
        }
        //针对95598开头网关的短信处理
        if (queueMessage.getSendId() != null && queueMessage.getSendId().startsWith("95598")) {
            boolean result95598 = queueFor95598.offer(queueMessage);
            if (result95598) {
                count95598.getAndIncrement();
                QueueTotalCount.getAndIncrement();
            }
            return result95598;
        }
        boolean result = priorityQueue.offer(queueMessage);
        if (result) {
            QueueCount.getAndIncrement();
            QueueTotalCount.getAndIncrement();
        }
        return result;
    }

    /**
     *95598队列取消息.
     *
     * @return
     */
    public QueueMessage take95598Q() {
        QueueMessage res = null;
        try {
            res = queueFor95598.take();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        if (res == null) {
            logger.info("队列已空！");
            return null;
        }
        count95598.getAndDecrement();
        return res;
    }

    /**
     *普通队列取消息.
     *
     * @return
     */
    public QueueMessage takeQ() {
        QueueMessage res = null;
        try {
            res = priorityQueue.take();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        if (res == null) {
            logger.info("队列已空！");
            return null;
        }
        QueueCount.getAndDecrement();
        return res;
    }

    /**
     *营销定时短信队列取消息.
     *
     * @return
     */
    public QueueMessage takeDelayMarketQ() {
        QueueMessage res = null;
        try {
            res = delayQueueForMarket.take();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        if (res == null) {
            logger.info("队列已空！");
            return null;
        }
        dealyQueueCount.getAndDecrement();
        return res;
    }

    /**
     * 紧急消息队列的去消息
     *
     * @return
     */
    public QueueMessage takeUrgentQ() {
        QueueMessage res = null;
        try {
            res = urgentQueue.take();
        } catch (InterruptedException e) {
            logger.error("", e);
        }

        if (res == null) {
            logger.info("队列已空！");
            return null;
        }
        urgentQueueCount.getAndDecrement();
        return res;
    }

    /**
     * 营销普通队列的取消息
     *
     * @return
     */
    public QueueMessage takeMarketQ() {
        QueueMessage res = null;
        try {
            res = marketQueue.take();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        if (res == null) {
            logger.info("队列已空！");
            return null;
        }
        marketQueueCount.getAndDecrement();
        return res;
    }

    /**
     *  数据短信取消息
     *
     * @return
     */
    public QueueMessage takeNumberQ() {
        QueueMessage res = null;
        try {
            res = numberQueue.take();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        if (res == null) {
            logger.info("队列已空！");
            return null;
        }
        numberQueueCount.getAndDecrement();
        return res;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public AtomicInteger getQueueCount() {
        return QueueCount;
    }

    public void setQueueCount(AtomicInteger queueCount) {
        QueueCount = queueCount;
    }

    public AtomicInteger getQueueTotalCount() {
        return QueueTotalCount;
    }

    public void setQueueTotalCount(AtomicInteger queueTotalCount) {
        QueueTotalCount = queueTotalCount;
    }

    public ArrayBlockingQueue<QueueMessage> getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(ArrayBlockingQueue<QueueMessage> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    public AtomicInteger getDealyQueueCount() {
        return dealyQueueCount;
    }

    public void setDealyQueueCount(AtomicInteger dealyQueueCount) {
        this.dealyQueueCount = dealyQueueCount;
    }

    public AtomicInteger getUrgentQueueCount() {
        return urgentQueueCount;
    }

    public void setUrgentQueueCount(AtomicInteger urgentQueueCount) {
        this.urgentQueueCount = urgentQueueCount;
    }

    public UmsGateWayInfoMapper getUmsGateWayInfoMapper() {
        return umsGateWayInfoMapper;
    }

    public void setUmsGateWayInfoMapper(UmsGateWayInfoMapper umsGateWayInfoMapper) {
        this.umsGateWayInfoMapper = umsGateWayInfoMapper;
    }

    public ArrayBlockingQueue<QueueMessage> getDelayQueueForMarket() {
        return delayQueueForMarket;
    }

    public void setDelayQueueForMarket(ArrayBlockingQueue<QueueMessage> delayQueueForMarket) {
        this.delayQueueForMarket = delayQueueForMarket;
    }

    public ArrayBlockingQueue<QueueMessage> getQueueFor95598() {
        return queueFor95598;
    }

    public void setQueueFor95598(ArrayBlockingQueue<QueueMessage> queueFor95598) {
        this.queueFor95598 = queueFor95598;
    }

    public PriorityBlockingQueue<QueueMessage> getUrgentQueue() {
        return urgentQueue;
    }

    public void setUrgentQueue(PriorityBlockingQueue<QueueMessage> urgentQueue) {
        this.urgentQueue = urgentQueue;
    }

    public AtomicInteger getCount95598() {
        return count95598;
    }

    public void setCount95598(AtomicInteger count95598) {
        this.count95598 = count95598;
    }

    public ArrayBlockingQueue<QueueMessage> getMarketQueue() {
        return marketQueue;
    }

    public void setMarketQueue(ArrayBlockingQueue<QueueMessage> marketQueue) {
        this.marketQueue = marketQueue;
    }

    public ArrayBlockingQueue<QueueMessage> getNumberQueue() {
        return numberQueue;
    }

    public void setNumberQueue(ArrayBlockingQueue<QueueMessage> numberQueue) {
        this.numberQueue = numberQueue;
    }

    public AtomicInteger getMarketQueueCount() {
        return marketQueueCount;
    }

    public AtomicInteger getNumberQueueCount() {
        return numberQueueCount;
    }

}
