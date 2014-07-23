/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.warn.modem;

import org.smslib.AGateway;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message.MessageEncodings;
import org.smslib.Message.MessageTypes;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

/**
 * 
 * @author foodoon
 * @version $Id: Send.java, v 0.1 2013-6-8 上午9:52:12 foodoon Exp $
 */
public class Send {

    private Service            srv;

    private SerialModemGateway gateway;

    public static void main(String[] args) throws Exception {
        System.out.println("发送测试短信给:" + args[0]);
        Send send = new Send();
        send.doIt(args[2], args[3], getInt(args[4]), args[5]);
        //  send.doIt("", "", 0, "");

        OutboundMessage msg = new OutboundMessage(args[0], args[1]);
        //OutboundMessage msg = new OutboundMessage("13758233926", "Hello from SMSLib1111111!");
        //        msg.setStatusReport(true);
        msg.setEncoding(MessageEncodings.ENCUCS2);
        try {
            send.getSrv().sendMessage(msg);
            System.out.println("测试短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getInt(String str) {
        return Integer.parseInt(str);
    }

    public void doIt(String id, String port, int baudRate, String manufacturer) throws Exception {

        gateway = new SerialModemGateway(id, port, baudRate, manufacturer, ""); // 设置端口与波特率
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
                System.out.println(String.valueOf(gateway));
                System.out.println(String.valueOf(om));
            }
        });

        System.out.println("GSM MODEM 初始化成功");
        try {
            srv.addGateway(gateway);
            srv.startService();
            System.out.println("GSM MODEM 服务启动成功");
            //  ReceiveThread receiveThread = new ReceiveThread("recv-thread");
            // receiveThread.start();
            // System.out.println("接收线程启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (srv != null) {
                try {
                    srv.removeGateway(gateway);
                    srv.stopService();
                    srv = null;
                } catch (Exception e1) {
                    e.printStackTrace();
                }
                try {
                    srv.stopService();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                srv = null;

            }

        }
    }

    class GatewayStatusNotification implements IGatewayStatusNotification {
        public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId()
                               + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    class InboundNotification implements IInboundMessageNotification {

        /** 
         * @see org.smslib.IInboundMessageNotification#process(org.smslib.AGateway, org.smslib.Message.MessageTypes, org.smslib.InboundMessage)
         */
        public void process(AGateway gatewayId, MessageTypes msgType, InboundMessage msg) {
            System.out.println(String.valueOf(msg));
            if (msgType == MessageTypes.INBOUND) {
                System.out.println(">>> New Inbound message detected from Gateway: " + gatewayId);

            } else if (msgType == MessageTypes.STATUSREPORT)
                System.out.println(">>> New Inbound Status Report message detected from Gateway: "
                                   + gatewayId);
            try {
                // Uncomment following line if you wish to delete the message upon arrival.
                srv.deleteMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class CallNotification implements ICallNotification {
        /** 
         * @see org.smslib.ICallNotification#process(org.smslib.AGateway, java.lang.String)
         */
        public void process(AGateway gatewayId, String callerId) {
            System.out.println(">>> New call detected from Gateway: " + gatewayId + " : "
                               + callerId);
        }
    }

    public Service getSrv() {
        return srv;
    }

    public void setSrv(Service srv) {
        this.srv = srv;
    }

    public SerialModemGateway getGateway() {
        return gateway;
    }

    public void setGateway(SerialModemGateway gateway) {
        this.gateway = gateway;
    }

}
