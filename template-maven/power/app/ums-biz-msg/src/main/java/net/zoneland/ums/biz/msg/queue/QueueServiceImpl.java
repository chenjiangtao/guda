/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.queue;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.Host;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangyong
 * @version $Id: QueueService.java, v 0.1 2012-8-16 下午3:42:00 wangyong Exp $
 */
public class QueueServiceImpl implements QueueService {

    private static final Logger logger = Logger.getLogger(QueueServiceImpl.class);

    private UmsUserInfoMapper   umsUserInfoMapper;

    private UmsMsgOutMapper     umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper  umsMsgOutUcsMapper;

    private QueueFactory        queueFactory;

    /**
     *把消息放到队列里
     * @param messageList
     * @return
     */
    public boolean offerMessageList(Collection<QueueMessage> queueMessageList) {
        if (queueMessageList == null) {
            return false;
        }
        Iterator<QueueMessage> it = queueMessageList.iterator();
        while (it.hasNext()) {
            QueueMessage queueMessage = it.next();
            List<String> recvIdArray = queueMessage.getRecvIdArray();
            for (int j = 0, len = recvIdArray.size(); j < len; j++) {
                UmsMsgOut umsMsgOut = new UmsMsgOut();
                umsMsgOut.setId(queueMessage.getIdArray().get(j));
                umsMsgOut.setStatus(MsgInfoStatusEnum.ready.getValue());
                umsMsgOut.setHost(Host.getHost());
                umsMsgOut.setGmtModified(new Date());
                if (queueMessage.getContentType() == 15) {
                    umsMsgOutMapper.updateStatusById(umsMsgOut);
                } else {
                    umsMsgOutUcsMapper.updateStatusById(umsMsgOut);
                }
            }
            boolean offer = false;
            offer = queueFactory.offer(queueMessage);
            if (!offer) {
                for (int j = 0, len = recvIdArray.size(); j < len; j++) {
                    UmsMsgOut umsMsgOut = new UmsMsgOut();
                    umsMsgOut.setId(queueMessage.getIdArray().get(j));
                    umsMsgOut.setStatus(MsgInfoStatusEnum.init.getValue());
                    umsMsgOut.setGmtModified(new Date());
                    if (queueMessage.getContentType() == 15) {
                        umsMsgOutMapper.updateStatusById(umsMsgOut);
                    } else {
                        umsMsgOutUcsMapper.updateStatusById(umsMsgOut);
                    }
                }
            }
            logger.info("放入队列成功！");
        }
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.msg.queue.QueueService#offerQueueMessage(net.zoneland.ums.biz.msg.queue.QueueMessage)
     */
    public boolean offerQueueMessage(QueueMessage queueMessage) {
        logger.info("开始放入队列！");

        boolean result = queueFactory.offer(queueMessage);
        return result;
    }

    public UmsUserInfoMapper getUmsUserInfoMapper() {
        return umsUserInfoMapper;
    }

    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    public UmsMsgOutMapper getUmsMsgOutMapper() {
        return umsMsgOutMapper;
    }

    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

    public QueueFactory getQueueFactory() {
        return queueFactory;
    }

    public void setQueueFactory(QueueFactory queueFactory) {
        this.queueFactory = queueFactory;
    }

    /**
     * @see net.zoneland.ums.biz.msg.queue.QueueService#offerMessageListNoUpdate(java.util.Collection)
     */
    public boolean offerMessageListNoUpdate(Collection<QueueMessage> queueMessageList) {
        if (queueMessageList == null) {
            return false;
        }
        Iterator<QueueMessage> it = queueMessageList.iterator();
        while (it.hasNext()) {
            QueueMessage queueMessage = it.next();
            List<String> recvIdArray = queueMessage.getRecvIdArray();
            boolean offer = false;
            offer = queueFactory.offer(queueMessage);
            if (!offer) {
                for (int j = 0, len = recvIdArray.size(); j < len; j++) {
                    UmsMsgOut umsMsgOut = new UmsMsgOut();
                    umsMsgOut.setId(queueMessage.getIdArray().get(j));
                    umsMsgOut.setStatus(MsgInfoStatusEnum.init.getValue());
                    umsMsgOut.setGmtModified(new Date());
                    if (queueMessage.getContentType() == 15) {
                        umsMsgOutMapper.updateStatusById(umsMsgOut);
                    } else {
                        umsMsgOutUcsMapper.updateStatusById(umsMsgOut);
                    }

                }
            }

        }
        return true;
    }
}
