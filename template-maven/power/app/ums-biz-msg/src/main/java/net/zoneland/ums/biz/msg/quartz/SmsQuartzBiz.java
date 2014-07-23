/**
 *
 */
package net.zoneland.ums.biz.msg.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.msg.process.MessageProcess;
import net.zoneland.ums.biz.msg.queue.QueueFactory;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author louguodong
 * 定时任务 业务操作类
 *
 */
public class SmsQuartzBiz {

    private static final Logger logger     = LoggerFactory.getLogger("UMS-QUARTZ");

    @Autowired
    private UmsMsgOutMapper     umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper  umsMsgOutUcsMapper;

    @Autowired
    private MessageProcess      messageProcess;

    @Autowired
    private QueueFactory        queueFactory;

    public static final int     QUEUELIMIT = 3000;

    /**
     * 处理发送失败的消息
     * @throws Exception
     */
    public void dealFailureSms() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", UmsConstants.MARKETAPPID);
        map.put("status", MsgInfoStatusEnum.failure.getValue());
        List<UmsMsgOut> listFailure = new ArrayList<UmsMsgOut>();
        if (queueFactory.getMarketQueueCount().get() < QUEUELIMIT) {
            listFailure.addAll(umsMsgOutMapper.selectByStatusLimit1000ForMarket(map));
            logger.info("营销短信失败状态的的数量：" + listFailure.size());
        } else {
            logger.info("营销短信失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (queueFactory.getQueueCount().get() < QUEUELIMIT) {
            listFailure.addAll(umsMsgOutMapper.selectByStatusLimit1000ForNotMarket(map));
        } else {
            logger.info("非营销短信失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (queueFactory.getNumberQueueCount().get() < QUEUELIMIT) {
            listFailure.addAll(umsMsgOutUcsMapper.selectByStatusLimit2000(MsgInfoStatusEnum.failure
                .getValue()));
        } else {
            logger.info("数据短信失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (logger.isInfoEnabled()) {
            logger.info("处理发送失败的消息，状态error, 数量:" + listFailure.size());
        }
        if (listFailure != null && listFailure.size() > 0) {
            messageProcess.processMsg(listFailure);
        }
        map.put("status", MsgInfoStatusEnum.unkown_error.getValue());
        List<UmsMsgOut> listError = new ArrayList<UmsMsgOut>();
        if (queueFactory.getMarketQueueCount().get() < QUEUELIMIT) {
            listError = umsMsgOutMapper.selectByStatusLimit1000ForMarket(map);
            logger.info("营销短信未知失败状态的的数量：" + listError.size());
        } else {
            logger.info("营销短信未知失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (queueFactory.getQueueCount().get() < QUEUELIMIT) {
            listError.addAll(umsMsgOutMapper.selectByStatusLimit1000ForNotMarket(map));
        } else {
            logger.info("other短信未知失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (queueFactory.getNumberQueueCount().get() < QUEUELIMIT) {
            listError.addAll(umsMsgOutUcsMapper
                .selectByStatusLimit2000(MsgInfoStatusEnum.unkown_error.getValue()));
        } else {
            logger.info("number短信未知失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (logger.isInfoEnabled()) {
            logger.info("处理发送失败的消息，状态unkown error, 数量:" + listError.size());
        }
        if (listError != null && listError.size() > 0) {
            messageProcess.processMsg(listError);
        }
        map.put("status", MsgInfoStatusEnum.init.getValue());
        List<UmsMsgOut> listInit = new ArrayList<UmsMsgOut>();
        if (queueFactory.getMarketQueueCount().get() < QUEUELIMIT) {
            listInit = umsMsgOutMapper.selectByStatusLimit1000ForMarket(map);
            logger.info("营销短信init状态的的数量：" + listInit.size());
        } else {
            logger.info("market短信未知失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (queueFactory.getQueueCount().get() < QUEUELIMIT) {
            listInit.addAll(umsMsgOutMapper.selectByStatusLimit1000ForNotMarket(map));
        } else {
            logger.info("other短信未知失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (queueFactory.getNumberQueueCount().get() < QUEUELIMIT) {
            listInit.addAll(umsMsgOutUcsMapper.selectByStatusLimit2000(MsgInfoStatusEnum.init
                .getValue()));
        } else {
            logger.info("number短信未知失败状态短信由于短信队列数量超过限制，先不捞取");
        }
        if (logger.isInfoEnabled()) {
            logger.info("处理发送失败的消息，状态init, 数量:" + listInit.size());
        }
        if (listInit != null && listInit.size() > 0) {
            messageProcess.processMsg(listInit);
        }
        if (logger.isInfoEnabled()) {
            logger.info("发送失败的消息处理完毕~~~~~~~~~~~~~~~~~");
        }

    }

    /**
     * 处理定时发送的消息,每次捞取前都先会判断当前队列的数量,如果数量小于3000的话我们就会进行短信捞取.
     * @throws Exception
     */
    public void dealDelaySms() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("处理定时发送的消息~~~~~~~~~~~~~~~~~");
        }
        Map<String, Object> map = new HashMap<String, Object>(6);
        map.put("status", MsgInfoStatusEnum.wait.getValue());
        map.put("sendTime", new Date());
        map.put("appId", UmsConstants.MARKETAPPID);
        List<UmsMsgOut> list = new ArrayList<UmsMsgOut>();
        //捞取营销的定时任务短信
        if (queueFactory.getDealyQueueCount().get() < QUEUELIMIT) {
            list = umsMsgOutMapper.selectDelayLimit1000ForMarket(map);
        }
        //捞取非营销的定时任务短信
        if (queueFactory.getQueueCount().get() < QUEUELIMIT) {
            list.addAll(umsMsgOutMapper.selectDelayLimit1000ForNotMarket(map));
        }
        //捞取数据短信的定时任务短信
        if (queueFactory.getNumberQueueCount().get() < QUEUELIMIT) {
            list.addAll(umsMsgOutUcsMapper.selectDelayLimit1000(map));
        }
        if (logger.isInfoEnabled()) {
            logger.info("处理定时的消息,状态wait,数量:" + list.size());
        }
        if (list != null && list.size() > 0) {
            messageProcess.processMsg(list);
        } else {
            logger.info("没有需要定时发送的消息~~~~~~~~~~~~~~~~~");
        }
    }
}
