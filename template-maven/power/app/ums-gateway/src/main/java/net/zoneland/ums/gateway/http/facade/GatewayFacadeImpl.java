/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.http.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.common.util.Counter;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.MessageFactory;
import net.zoneland.ums.gateway.Result;
import net.zoneland.ums.gateway.TheadFactory;
import net.zoneland.ums.gateway.UmsGatewayFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author gag
 * @version $Id: GatewayFacadeImpl.java, v 0.1 2012-9-3 上午9:42:56 gag Exp $
 */
@Controller
public class GatewayFacadeImpl implements GatewayFacade {

    private final static Logger   logger  = Logger.getLogger(GatewayFacadeImpl.class);

    private static final Counter  counter = new Counter("gateway-httpinvoke");

    private final ExecutorService es      = Executors.newFixedThreadPool(30);

    @Autowired
    private MessageFactory        messageFactory;

    @Autowired
    private UmsGatewayFactory     umsGatewayFactory;

    @Autowired
    private GatewayService        gatewayService;

    @RequestMapping(value = "invoke/gatewayService")
    public Result sendMsg(Message msg) {
        counter.count();
        try {
            //boolean res = messageFactory.sendMsg(msg);
            GateEnum gateEnum = umsGatewayFactory.getGatewayType(msg.getGatewayId());
            if (GateEnum.SGIP == gateEnum || GateEnum.SMGP == gateEnum
                || GateEnum.SMGP3 == gateEnum) {
                SendThread send = TheadFactory.newSendThread(messageFactory, msg, gateEnum);
                if (send == null) {
                    logger.warn(gateEnum.getDescription() + "线程池已满," + msg);
                    return new Result(false, gateEnum.getDescription() + "线程池已满");
                }
                try {
                    Boolean res = send.call();
                    if (res == null || !res) {
                        return new Result(false, "网关发送消息失败");
                    } else {
                        return new Result();
                    }
                } finally {
                    TheadFactory.releaseThread(gateEnum);
                }
            } else {
                SendThread send = new SendThread(messageFactory, msg);
                Boolean res = send.call();
                if (res == null || !res) {
                    return new Result(false, "网关发送消息失败");
                } else {
                    return new Result();
                }
            }
        } catch (Exception e) {
            logger.error("发送消息失败,msg[" + msg + "].", e);
            if (e instanceof InterruptedException) {
                if (!umsGatewayFactory.hasGateway(msg.getGatewayId())) {
                    logger.error("InterruptedException restart gateway:" + msg.getGatewayId());
                    boolean res = umsGatewayFactory.closeGatewayTemp(msg.getGatewayId());
                    if (res) {
                        openGateway(msg.getGatewayId());
                    }
                } else {
                    logger
                        .error("InterruptedException restart gateway...gateway is started...gatewayid:"
                               + msg.getGatewayId());
                }
            }
            return new Result(false, e.getMessage());
        }

    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    /**
     * @see net.zoneland.ums.gateway.http.facade.GatewayFacade#closeGateway(java.lang.String)
     */
    public Result closeGateway(String id) {
        try {
            if (!umsGatewayFactory.hasGateway(id)) {
                return new Result(true, "网关已经关闭");
            }
            boolean res = umsGatewayFactory.closeGateway(id);
            if (res) {
                return new Result();
            } else {
                return new Result(false, "关闭网关失败");
            }
        } catch (Exception e) {
            logger.error("关闭网关失败", e);
            return new Result(false, "关闭网关失败" + e.getMessage());
        }

    }

    public Result openGateway(String id) {
        try {
            if (umsGatewayFactory.hasGateway(id)) {
                return new Result(true, "网关已经开启");
            }
            boolean res = umsGatewayFactory.openGateway(id);
            if (res) {
                return new Result();
            } else {
                return new Result(false, "开启网关失败");
            }
        } catch (Exception e) {
            logger.error("关闭网关失败", e);
            return new Result(false, "开启网关失败" + e.getMessage());
        }
    }

    public boolean isOpen(String id) {
        return umsGatewayFactory.hasGateway(id);
    }

    public List<Boolean> isOpen(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<Boolean> listResult = new ArrayList<Boolean>();
        for (int i = 0; i < list.size(); i++) {
            if (umsGatewayFactory.hasGateway(list.get(i))) {
                listResult.add(true);
            } else {
                listResult.add(false);
            }
        }
        logger.info("查询网关状态：" + listResult);
        return listResult;
    }

    @RequestMapping(value = "invoke/gatewayServiceBatch")
    public List<Result> sendMsgBatch(List<Message> msgs) {
        List<Result> res = new ArrayList<Result>();
        if (msgs == null || msgs.size() == 0) {
            Result r = new Result(false, "消息为空");
            res.add(r);
            return res;
        }
        Iterator<Message> it = msgs.iterator();
        while (it.hasNext()) {
            Message msg = it.next();
            res.add(sendMsg(msg));
        }
        return res;
    }
}
