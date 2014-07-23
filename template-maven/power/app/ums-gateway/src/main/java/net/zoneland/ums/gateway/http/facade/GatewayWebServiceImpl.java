/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway.http.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.zoneland.gateway.comm.PException;
import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.MessageFactory;
import net.zoneland.ums.gateway.Result;
import net.zoneland.ums.gateway.UmsGatewayFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Administrator
 * @version $Id: GatewayWebServiceImpl.java, v 0.1 2013-1-9 下午5:26:50 Administrator Exp $
 */
public class GatewayWebServiceImpl implements GatewayWebService {

    private final static Logger logger = Logger.getLogger(GatewayWebServiceImpl.class);

    @Autowired
    private MessageFactory      messageFactory;

    @Autowired
    private UmsGatewayFactory   umsGatewayFactory;

    /** 
     * @see net.zoneland.ums.gateway.http.facade.GatewayFacade#sendMsg(net.zoneland.ums.gateway.Message)
     */

    public Result sendMsg(Message msg) {
        try {
            boolean res = messageFactory.sendMsg(msg);
            if (res) {
                return new Result();
            } else {
                return new Result(false, "网关发送消息失败");
            }
        } catch (Exception e) {
            logger.error("发送消息失败,msg[" + msg + "].", e);
            if (e instanceof PException) {
                logger.error("restart gateway:" + msg.getGatewayId());
                boolean res = umsGatewayFactory.closeGatewayTemp(msg.getGatewayId());
                if (res) {
                    umsGatewayFactory.openGateway(msg.getGatewayId());
                }
            }
            return new Result(false, e.getMessage());
        }

    }

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

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    /** 
     * @see net.zoneland.ums.gateway.http.facade.GatewayFacade#closeGateway(java.lang.String)
     */
    public Result closeGateway(String id) {
        if (!umsGatewayFactory.hasGateway(id)) {
            return new Result(true, "网关已经关闭");
        }
        try {
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

    /** 
     * @see net.zoneland.ums.gateway.http.facade.GatewayFacade#openGateway(java.lang.String)
     */
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

    /** 
     * @see net.zoneland.ums.gateway.http.facade.GatewayFacade#isOpen(java.lang.String)
     */
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
        return listResult;
    }

}
