/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.warn.modem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smslib.AGateway;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message;
import org.smslib.Message.MessageEncodings;
import org.smslib.Message.MessageTypes;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

/**
 * 
 * @author gag
 * @version $Id: ModemProxy.java, v 0.1 2012-12-18 上午11:13:51 gag Exp $
 */
public class ModemProxy {

    private Logger             logger = LoggerFactory.getLogger(ModemProxy.class);

    private Service            srv;

    private SerialModemGateway gateway;

    private ReceiveThread      recvThread;

    private String             modemId;

    private String             port;

    private int                baudRate;

    private String             manufacturer;

    public ModemProxy() {

    }

    public boolean isStart() {
        return srv != null;
    }

    public void doInit() {
        try {
            init();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.schedule(new MonitorTask(), 30000, 5 * 60 * 1000);
    }

    public void init() {
        try {
            gateway = new SerialModemGateway(modemId, port, baudRate, manufacturer, ""); // 设置端口与波特率

        } catch (Exception e) {
            throw new RuntimeException("初始化网关失败,modemId:" + modemId + ",port" + port + ",baudRate:"
                                       + baudRate + ",manufacturer:" + manufacturer);
        }
        //gateway = new SerialModemGateway("modem.com9", "COM9", 9600, "wavecom", "");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
        srv = Service.getInstance();
        srv.setGatewayStatusNotification(new GatewayStatusNotification());//网关变化通知
        srv.setInboundMessageNotification(new InboundNotification());//接收处理
        srv.setCallNotification(new CallNotification()); //调用提醒
        srv.setOutboundMessageNotification(new IOutboundMessageNotification() {

            public void process(AGateway gateway, OutboundMessage om) {
                logger.info(String.valueOf(gateway));
                logger.info(String.valueOf(om));
            }
        });

        logger.info("GSM MODEM 初始化成功");
        try {
            srv.addGateway(gateway);
            srv.startService();
            logger.info("GSM MODEM 服务启动成功");
            //  ReceiveThread receiveThread = new ReceiveThread("recv-thread");
            // receiveThread.start();
            // logger.info("接收线程启动成功");
        } catch (Exception e) {
            if (srv != null) {
                try {
                    srv.removeGateway(gateway);
                    srv.stopService();
                    srv = null;
                } catch (Exception e1) {
                    logger.error("", e);
                }
                try {
                    srv.stopService();
                } catch (Exception e1) {
                    logger.error("", e);
                }
                srv = null;

            }
            logger.error("", e);
        }

    }

    class GatewayStatusNotification implements IGatewayStatusNotification {
        public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
            logger.info(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: "
                        + oldStatus + " -> NEW: " + newStatus);
        }
    }

    class InboundNotification implements IInboundMessageNotification {

        /** 
         * @see org.smslib.IInboundMessageNotification#process(org.smslib.AGateway, org.smslib.Message.MessageTypes, org.smslib.InboundMessage)
         */
        public void process(AGateway gatewayId, MessageTypes msgType, InboundMessage msg) {
            logger.info(String.valueOf(msg));
            if (msgType == MessageTypes.INBOUND) {
                logger.info(">>> New Inbound message detected from Gateway: " + gatewayId);
                ModemMessage m = new ModemMessage();
                Message.MessageEncodings encode = msg.getEncoding();
                if (MessageEncodings.valueOf(encode.name()) == MessageEncodings.ENCUCS2) {
                    m.setContent(msg.getText());
                    m.setMobilePhone(msg.getOriginator().substring(
                        msg.getOriginator().indexOf("86") + 2));
                    logger.info("mm " + m);

                }
            } else if (msgType == MessageTypes.STATUSREPORT)
                logger.info(">>> New Inbound Status Report message detected from Gateway: "
                            + gatewayId);
            try {
                // Uncomment following line if you wish to delete the message upon arrival.
                srv.deleteMessage(msg);
            } catch (Exception e) {
                logger.error("Oops!!! Something gone bad...", e);
            }
        }
    }

    class CallNotification implements ICallNotification {
        /** 
         * @see org.smslib.ICallNotification#process(org.smslib.AGateway, java.lang.String)
         */
        public void process(AGateway gatewayId, String callerId) {
            logger.info(">>> New call detected from Gateway: " + gatewayId + " : " + callerId);
        }
    }

    class ReceiveThread extends WatchThread {

        public ReceiveThread(String name) {
            super(name);
        }

        @Override
        public void task() {

            List<InboundMessage> inMessages = new ArrayList<InboundMessage>();
            try {
                int result = srv.readMessages(inMessages, MessageClasses.ALL);
                logger.info("receive " + result + " messages");
                for (int i = 0; i < inMessages.size(); i++) {
                    Object o = inMessages.get(i);
                    if (o instanceof InboundMessage) {
                        InboundMessage msg = (InboundMessage) inMessages.get(i);
                        logger.info("Source Info " + msg);
                        ModemMessage m = new ModemMessage();
                        m.setContent(msg.getText());
                        m.setMobilePhone(msg.getOriginator());
                        logger.info("mm " + m);

                        srv.deleteMessage(msg);
                    }

                }
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                logger.error("", e);
            }

        }
    }

    public boolean send(ModemMessage message) throws IOException {
        ModemMessage m = (ModemMessage) message;
        logger.info("wait send message " + m);
        boolean result = false;
        OutboundMessage msg = new OutboundMessage(m.getMobilePhone(), m.getContent());
        //OutboundMessage msg = new OutboundMessage("13758233926", "Hello from SMSLib1111111!");
        //        msg.setStatusReport(true);
        msg.setEncoding(MessageEncodings.ENCUCS2);
        try {
            result = srv.sendMessage(msg);

        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    public void close() {

        try {
            //receiveThread.kill();
            Service.getInstance().stopService();
            if (srv != null) {
                srv.removeGateway(gateway);
            }
            logger.info("GSM MODEM 服务停止完成");
        } catch (Exception e) {
            logger.error("", e);
        }
        try {
            if (srv != null) {
                srv.removeGateway(gateway);
            }
            logger.info("GSM MODEM 服务停止完成");
        } catch (Exception e) {
            logger.error("", e);
        }
        if (recvThread != null) {
            recvThread.kill();
        }

    }

    class MonitorTask extends TimerTask {

        public void run() {

            logger.warn("网关状态:[" + isStart() + "]");
            if (!isStart()) {
                logger.warn("try start gateway...");
                try {
                    close();
                } catch (Exception e) {
                    logger.error("", e);
                }
                try {
                    init();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        ModemProxy proxy = new ModemProxy();
        proxy.send(new ModemMessage("13588754574", "test11111111111111"));

        proxy.close();
    }

    public void setModemId(String modemId) {
        this.modemId = modemId;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
