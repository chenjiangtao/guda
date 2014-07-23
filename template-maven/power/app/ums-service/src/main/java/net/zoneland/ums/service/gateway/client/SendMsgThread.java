/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.gateway.client;

import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.biz.msg.out.MsgOutService;
import net.zoneland.ums.biz.msg.queue.QueueFactory;
import net.zoneland.ums.biz.msg.queue.QueueMessage;
import net.zoneland.ums.biz.msg.send.AfterMsgSend;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.Result;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 多线程发送消息.
 * @author gang
 * @version $Id: SendMsgThread.java, v 0.1 2012-9-7 下午9:22:41 gang Exp $
 */

public class SendMsgThread {

    private final static Logger    logger            = Logger.getLogger(SendMsgThread.class);

    private final static Logger    SENDSTAT          = Logger.getLogger("SENDSTAT");

    public static final String     PHONE_PREFIX      = "86";

    public int                     invokeRecvIdCount = 1;

    public final static String     URGENTTHREAD      = "urgent";

    public final static String     DELAYMARKETTHREAD = "delayMarket";

    public final static String     MARKETTHREAD      = "Market";

    public final static String     OTHERTHREAD       = "other";

    public final static String     NUMBERTHREAD      = "number";

    public final static String     THREAD95598       = "95598";

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private QueueFactory           queueFactory;

    @Autowired
    private SendMsgClient          sendMsgClient;

    @Autowired
    private MsgOutService          msgOutService;

    @Autowired
    private AfterMsgSend           afterMsgSend;

    private Integer                sendThreadCount;

    private Integer                sendThreadSleepMillis;

    public static final Long       SGIPSLEEP         = 150L;

    @Autowired
    private GatewayService         gatewayService;

    /**
     * 应用启动的时候，启动发送线程.
     */
    public void startup() {
        if (sendThreadCount == null || sendThreadCount <= 0) {
            return;
        }
        if (sendThreadSleepMillis == null || sendThreadSleepMillis <= 0) {
            return;
        }
        //营销定时短信数量很大，我们做特殊处理线程翻倍
        for (int i = 0; i < sendThreadCount * 2; ++i) {
            taskExecutor.execute(new SendThread("营销定时任[" + i + "]", DELAYMARKETTHREAD));
            taskExecutor.execute(new SendThread("营销普通短信[" + i + "]", MARKETTHREAD));
        }
        for (int i = 0; i < sendThreadCount; ++i) {
            taskExecutor.execute(new SendThread("紧急消息[" + i + "]", URGENTTHREAD));
            taskExecutor.execute(new SendThread("95598[" + i + "]", THREAD95598));
            taskExecutor.execute(new SendThread("other[" + i + "]", OTHERTHREAD));
            taskExecutor.execute(new SendThread("数据短信[" + i + "]", NUMBERTHREAD));
        }
    }

    class SendThread implements Runnable {

        private String name        = "";

        private String queueChoose = "";

        public SendThread(String name, String queueChoose) {
            this.name = name;
            this.queueChoose = queueChoose;
        }

        public void run() {
            for (;;) {
                try {
                    sendMsg(queueChoose, name);
                } catch (Exception e) {
                    logger.error("发送消息错误", e);
                }
                try {
                    Thread.sleep(sendThreadSleepMillis);
                } catch (InterruptedException e) {
                    logger.error(name + "发送消息线程中断", e);
                }
            }
        }

    }

    //因为采用invoke发送每次发送的数据量是有一定的限制的，所以我们这里做了分批处理。
    private void sendMsg(String queueChoose, String name) {
        QueueMessage qmsg = takeQueueMessage(queueChoose);
        if (qmsg == null) {
            return;
        }
        long start = System.currentTimeMillis();
        UmsGateWayInfo umsGateWayInfo = gatewayService.findGateway(qmsg.getMediaId());
        //控制联通电信的发送速度，防止超过流量导致程序挂掉！
        if (GateEnum.SGIP.getValue().equals(umsGateWayInfo.getType())
            || GateEnum.SMGP.getValue().equals(umsGateWayInfo.getType())
            || GateEnum.SMGP3.getValue().equals(umsGateWayInfo.getType())) {
            try {
                if (logger.isInfoEnabled()) {
                    logger.info("********联通或者电信短信沉睡" + SGIPSLEEP + "ms");
                }
                Thread.sleep(SGIPSLEEP);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        List<String> recvIds = qmsg.getRecvIdArray();
        List<String> iDs = qmsg.getIdArray();
        boolean sendResult = false;
        boolean res = invokeSendMsg(qmsg, recvIds, iDs);
        if (res) {
            sendResult = true;
        }
        if (sendResult) {
            afterMsgSend.afterSendSuccess(qmsg);
        }
        long end = System.currentTimeMillis();

        SENDSTAT.info("发送线程："
                      + name
                      + " 结果:"
                      + sendResult
                      + " ID:"
                      + qmsg.getIdArray().get(0)
                      + " 发送网关："
                      + (umsGateWayInfo == null ? qmsg.getMediaId() : umsGateWayInfo
                          .getGatewayName()) + " 耗费时间：" + (end - start));

    }

    /**
     * 因为网关发送短信的话要在开头添加86才能发送
     *
     * @param recvIdArray
     * @return
     */
    private String[] append86(List<String> recvIdArray) {
        if (recvIdArray == null) {
            return null;
        }
        if (recvIdArray.size() == 0) {
            return null;
        }
        Iterator<String> it = recvIdArray.iterator();
        String[] newIds = new String[recvIdArray.size()];
        int i = 0;
        while (it.hasNext()) {
            String phone = it.next();
            if (phone != null && phone.length() == 11) {
                newIds[i] = PHONE_PREFIX + phone;
            } else {
                newIds[i] = phone;
            }
            ++i;
        }
        return newIds;
    }

    /**
     * 更新短信的状态发送成功还是发送失败.
     *
     * @param ids
     * @param status
     * @param errorMsg
     * @param msgType
     */
    private void updateStatus(List<String> ids, String status, String errorMsg, int msgType) {
        Iterator<String> it = ids.iterator();
        while (it.hasNext()) {
            String id = it.next();
            boolean res = false;
            if (msgType == 15) {
                res = msgOutService.updateMsgOut(id, status, errorMsg);
            } else {
                res = msgOutService.updateMsgOutUcs(id, status, errorMsg);
            }
            if (logger.isInfoEnabled()) {
                logger.info("更新消息:[" + id + "]，" + "短信组数量" + ids.size() + ",状态:[" + status
                            + "],结果:" + res);
            }
        }
    }

    public boolean invokeSendMsg(QueueMessage qmsg, List<String> recvIds, List<String> ids) {
        Message msg = new Message();
        msg.setAck(qmsg.getAck());
        msg.setContent(qmsg.getContent());
        if (qmsg.getContentType() != null) {
            msg.setContentType(qmsg.getContentType());
        }
        // msg.setFee(qmsg.getFee().toString());
        // msg.setFeeType(qmsg.getFeeType().toString());
        msg.setGatewayId(qmsg.getMediaId());
        msg.setRecvId(append86(recvIds));
        msg.setSendId(qmsg.getSendId());
        msg.setPriority(9);
        boolean sendResult = false;
        try {
            Result res = sendMsgClient.sendMsg(msg);
            if (res.isSuccess()) {
                sendResult = res.isSuccess();
                if (logger.isInfoEnabled()) {
                    logger.info("成功消息:" + qmsg + "发送成功");
                }
                updateStatus(ids, MsgInfoStatusEnum.success.getValue(), "发送成功",
                    qmsg.getContentType());
            } else {
                logger.error("失败消息:" + qmsg + "发送失败,错误" + res.getErrorMsg());
                updateStatus(ids, MsgInfoStatusEnum.failure.getValue(), res.getErrorMsg(),
                    qmsg.getContentType());
            }
        } catch (Exception e) {
            logger.error("异常处理的消息:" + Thread.currentThread().getName() + msg + "发送失败，错误:", e);
            updateStatus(ids, MsgInfoStatusEnum.failure.getValue(), "网关异常", qmsg.getContentType());
            try {
                Thread.sleep(10 * 1000L);
            } catch (InterruptedException e1) {

            }
        }
        return sendResult;
    }

    public QueueMessage takeQueueMessage(String queueChoose) {
        QueueMessage qmsg = null;
        if (URGENTTHREAD.equals(queueChoose)) {
            qmsg = queueFactory.takeUrgentQ();
        } else if (THREAD95598.equals(queueChoose)) {
            qmsg = queueFactory.take95598Q();
        } else if (DELAYMARKETTHREAD.equals(queueChoose)) {
            qmsg = queueFactory.takeDelayMarketQ();
        } else if (MARKETTHREAD.equals(queueChoose)) {
            qmsg = queueFactory.takeMarketQ();
        } else if (NUMBERTHREAD.equals(queueChoose)) {
            qmsg = queueFactory.takeNumberQ();
        } else {
            qmsg = queueFactory.takeQ();
        }
        return qmsg;
    }

    public void setQueueFactory(QueueFactory queueFactory) {
        this.queueFactory = queueFactory;
    }

    public void setSendMsgClient(SendMsgClient sendMsgClient) {
        this.sendMsgClient = sendMsgClient;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setMsgOutService(MsgOutService msgOutService) {
        this.msgOutService = msgOutService;
    }

    public void setAfterMsgSend(AfterMsgSend afterMsgSend) {
        this.afterMsgSend = afterMsgSend;
    }

    public void setSendThreadCount(Integer sendThreadCount) {
        this.sendThreadCount = sendThreadCount;
    }

    public void setSendThreadSleepMillis(Integer sendThreadSleepMillis) {
        this.sendThreadSleepMillis = sendThreadSleepMillis;
    }

    public int getInvokeRecvIdCount() {
        return invokeRecvIdCount;
    }

    public void setInvokeRecvIdCount(int invokeRecvIdCount) {
        this.invokeRecvIdCount = invokeRecvIdCount;
    }

}
