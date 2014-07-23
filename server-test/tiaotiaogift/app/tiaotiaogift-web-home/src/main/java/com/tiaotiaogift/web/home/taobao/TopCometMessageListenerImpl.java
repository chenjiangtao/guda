/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taobao.api.ApiException;
import com.taobao.api.domain.NotifyItem;
import com.taobao.api.domain.NotifyTrade;
import com.taobao.api.internal.stream.message.TopCometMessageListener;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.tiaotiaogift.biz.common.taobao.SendMessageService;
import com.tiaotiaogift.biz.common.taobao.TaobaoService;
import com.tiaotiaogift.common.dal.NotifyMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.Notify;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.DateUtil;
import com.tiaotiaogift.common.util.MessageDecode;
import com.tiaotiaogift.common.util.enums.NotifyEnum;

/**
 * 
 * @author foodoon
 * @version $Id: TopCometMessageListenerImpl.java, v 0.1 2013-7-29 上午8:37:34 foodoon Exp $
 */
@Service("TopCometMessageListener")
public class TopCometMessageListenerImpl implements TopCometMessageListener {

    private final static Logger    logger = Logger.getLogger(TopCometMessageListenerImpl.class);

    @Autowired
    private SendMessageService     sendMessageService;

    @Autowired
    private UserMapper             userMapper;

    @Autowired
    private TaobaoService          taobaoService;

    @Autowired
    private NotifyMapper           notifyMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onReceiveMsg(java.lang.String)
     */
    public void onReceiveMsg(String arg0) {
        logger.info("onReceiveMsg:" + arg0);
        ProcMessage proc = null;
        try {
            Object obj = MessageDecode.decodeMsg(arg0);
            if (obj == null) {
                return;
            }
            if (obj instanceof NotifyTrade) {
                NotifyTrade trade = (NotifyTrade) obj;
                StringBuilder buf = new StringBuilder();
                if (NotifyEnum.TradeCreate.getCode().equalsIgnoreCase(trade.getStatus())) {
                    buf.append("买家").append(trade.getBuyerNick()).append("刚刚拍下了商品，待付款金额:")
                        .append(trade.getPayment()).append(",拍下时间:")
                        .append(DateUtil.getChsDay(trade.getModified()));
                    User user = userMapper.selectByUserId(trade.getSellerNick());
                    if (user != null) {
                        proc = new ProcMessage(user.getId(), buf.toString(), user.getPhone(),
                            sendMessageService);
                    }
                } else if (NotifyEnum.TradeBuyerPay.getCode().equalsIgnoreCase(trade.getStatus())) {
                    buf.append("买家").append(trade.getBuyerNick()).append("已经付款，待付款金额:")
                        .append(trade.getPayment()).append(",付款时间:")
                        .append(DateUtil.getChsDay(trade.getModified())).append(",等待您发货。");
                    User user = userMapper.selectByUserId(trade.getSellerNick());
                    if (user != null) {
                        proc = new ProcMessage(user.getId(), buf.toString(), user.getPhone(),
                            sendMessageService);
                    }
                } else if (NotifyEnum.TradeSellerShip.getCode().equalsIgnoreCase(trade.getStatus())) {
                    buf.append("您在小店购买的宝贝已经发货。请注意查收。有任何问题请联系我们的客服人员。").append(",发货时间:")
                        .append(DateUtil.getChsDay(trade.getModified())).append("。");
                    User user = userMapper.selectByUserId(trade.getSellerNick());
                    //查找买家电话
                    Notify notify = notifyMapper.selectByUserId(user.getId());
                    if (notify == null) {
                        logger.warn("用户" + user + "查询notify 为空,丢弃短信." + trade);
                        return;
                    }
                    TradeFullinfoGetResponse fullresponse = taobaoService.queryFullTrade(
                        TaobaoCallController.appKey, TaobaoCallController.appSecret,
                        notify.getSessionKey(), trade.getTid());
                    if (user != null && fullresponse != null && fullresponse.getTrade() != null
                        && StringUtils.hasText(fullresponse.getTrade().getReceiverMobile())) {
                        proc = new ProcMessage(user.getId(), buf.toString(), fullresponse
                            .getTrade().getReceiverMobile(), sendMessageService);
                    }
                } else if (NotifyEnum.TradeSuccess.getCode().equalsIgnoreCase(trade.getStatus())) {
                    buf.append("买家").append(trade.getBuyerNick()).append("已经确认收货.")
                        .append(",确认时间:").append(DateUtil.getChsDay(trade.getModified()))
                        .append("。");
                    User user = userMapper.selectByUserId(trade.getSellerNick());
                    if (user != null) {
                        proc = new ProcMessage(user.getId(), buf.toString(), user.getPhone(),
                            sendMessageService);
                    }
                } else if (NotifyEnum.TradeTimeoutRemind.getCode().equalsIgnoreCase(
                    trade.getStatus())) {

                } else if (NotifyEnum.TradeRated.getCode().equalsIgnoreCase(trade.getStatus())) {
                    buf.append("买家").append(trade.getBuyerNick()).append("已经评价.").append(",评价时间:")
                        .append(DateUtil.getChsDay(trade.getModified())).append("。");
                    User user = userMapper.selectByUserId(trade.getSellerNick());
                    if (user != null) {
                        proc = new ProcMessage(user.getId(), buf.toString(), user.getPhone(),
                            sendMessageService);
                    }
                } else if (NotifyEnum.TradeSuccess.getCode().equalsIgnoreCase(trade.getStatus())) {
                    buf.append("买家").append(trade.getBuyerNick()).append("已经确认收货.")
                        .append(",确认收货时间:").append(DateUtil.getChsDay(trade.getModified()))
                        .append("。");
                    User user = userMapper.selectByUserId(trade.getSellerNick());
                    if (user != null) {
                        proc = new ProcMessage(user.getId(), buf.toString(), user.getPhone(),
                            sendMessageService);
                    }
                }
            } else if (obj instanceof NotifyItem) {
                NotifyItem item = (NotifyItem) obj;
                StringBuilder buf = new StringBuilder();
                if (NotifyEnum.ItemSkuZeroStock.getCode().equalsIgnoreCase(item.getStatus())) {
                    buf.append("商品").append(item.getTitle()).append("已经售空.")
                        .append(",请及时下架或者增加库存。");
                    User user = userMapper.selectByUserId(item.getNick());
                    if (user != null) {
                        proc = new ProcMessage(user.getId(), buf.toString(), user.getPhone(),
                            sendMessageService);
                    }
                }

            }
        } catch (ApiException e) {
            logger.error("", e);
        }
        if (proc != null) {
            taskExecutor.execute(proc);
        }

    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onClientKickOff()
     */
    public void onClientKickOff() {
        logger.info("onClientKickOff");
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onConnectMsg(java.lang.String)
     */
    public void onConnectMsg(String arg0) {
        logger.info("onConnectMsg:" + arg0);
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onDiscardMsg(java.lang.String)
     */
    public void onDiscardMsg(String arg0) {
        logger.info("onDiscardMsg:" + arg0);
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onException(java.lang.Exception)
     */
    public void onException(Exception arg0) {
        logger.info("onException:", arg0);
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onHeartBeat()
     */
    public void onHeartBeat() {
        logger.info("onHeartBeat");
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onOtherMsg(java.lang.String)
     */
    public void onOtherMsg(String arg0) {
        logger.info("onOtherMsg:" + arg0);
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onServerKickOff()
     */
    public void onServerKickOff() {
        logger.info("onServerKickOff");
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onServerRehash()
     */
    public void onServerRehash() {
        logger.info("onServerRehash");
    }

    /** 
     * @see com.taobao.api.internal.stream.message.TopCometMessageListener#onServerUpgrade(java.lang.String)
     */
    public void onServerUpgrade(String arg0) {
        logger.info("onServerUpgrade");
    }

}
