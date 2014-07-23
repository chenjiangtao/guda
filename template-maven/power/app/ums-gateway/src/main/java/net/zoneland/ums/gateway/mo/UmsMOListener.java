/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.mo;

import java.util.Map;

import net.zoneland.gateway.MoListener;
import net.zoneland.gateway.comm.PMessage;
import net.zoneland.gateway.comm.cmpp.message.CMPPDeliverMessage;
import net.zoneland.gateway.comm.cmpp3.message.CMPP30DeliverMessage;
import net.zoneland.gateway.comm.sgip.message.SGIPDeliverMessage;
import net.zoneland.gateway.comm.smgp.message.SMGPDeliverMessage;
import net.zoneland.gateway.comm.smgp3.message.SMGP3DeliverMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: UmsMOListener.java, v 0.1 2012-8-29 上午10:05:25 gag Exp $
 */
public class UmsMOListener implements MoListener, InitializingBean {

    private static Logger       logger = Logger.getLogger("UMS-GATEWAY-MO");

    @Autowired
    private DeliverProcess      deliverProcess;

    @Autowired
    private DeliverMysqlProcess deliverMysqlProcess;

    private String              dbName;

    private Process             process;

    /**
     * @see net.zoneland.gateway.MoListener#OnTerminate()
     */
    public void OnTerminate() {
    }

    /**
     * @see net.zoneland.gateway.MoListener#onDeliver(net.zoneland.gateway.comm.PMessage,
     *      java.util.Map)
     */
    public void onDeliver(PMessage arg0, Map<String, String> arg1) {
        if (logger.isInfoEnabled()) {
            logger.info("收到上行短信:" + arg0);
        }
        Object obj = arg1.get("spNumber");
        String spNum = "";
        if (obj != null) {
            spNum = obj.toString();
        }

        if (arg0 instanceof CMPPDeliverMessage) {
            CMPPDeliverMessage msg = (CMPPDeliverMessage) arg0;
            if (msg.getRegisteredDeliver() == 1) {
                if (logger.isInfoEnabled()) {
                    logger.info("收到状态报告，忽略短信:" + msg);
                }
                return;
            }

            if (msg.getMsgLength() <= 0) {
                logger.warn("消息长度为空!srcid:[" + msg.getSrcterminalId() + "],destId:["
                            + msg.getDestnationId() + "],gatewayId:"
                            + String.valueOf(arg1.get("gatewayId")));
            } else {
                //                process.saveDeliverMsg(spNum, msg.getSrcterminalId(), msg.getDestnationId(),
                //                    msg.getMessageContentStr(), String.valueOf(arg1.get("gatewayId")),
                //                    msg.getMsgFmt());
                deliverProcess.saveDeliverMsg(spNum, msg.getSrcterminalId(), msg.getDestnationId(),
                    msg.getMessageContentStr(), String.valueOf(arg1.get("gatewayId")),
                    msg.getMsgFmt());

            }
        } else if (arg0 instanceof CMPP30DeliverMessage) {
            CMPP30DeliverMessage msg = (CMPP30DeliverMessage) arg0;
            if (msg.getRegisteredDeliver() == 1) {
                if (logger.isInfoEnabled()) {
                    logger.info("收到状态报告，忽略短信:" + msg);
                }
                return;
            }
            if (msg.getMsgLength() <= 0) {
                logger.warn("消息长度为空!srcid:[" + msg.getSrcterminalId() + "],destId:["
                            + msg.getDestnationId() + "],gatewayId:"
                            + String.valueOf(arg1.get("gatewayId")));
            } else {
                //                process.saveDeliverMsg(spNum, msg.getSrcterminalId(), msg.getDestnationId(),
                //                    msg.getMessageContentStr(), String.valueOf(arg1.get("gatewayId")),
                //                    msg.getMsgFmt());

                deliverProcess.saveDeliverMsg(spNum, msg.getSrcterminalId(), msg.getDestnationId(),
                    msg.getMessageContentStr(), String.valueOf(arg1.get("gatewayId")),
                    msg.getMsgFmt());

            }
        } else if (arg0 instanceof SGIPDeliverMessage) {
            SGIPDeliverMessage msg = (SGIPDeliverMessage) arg0;

            if (msg.getMsgLength() <= 0) {
                logger.warn("消息长度为空!srcid:[" + msg.getUserNumber() + "],destId:["
                            + msg.getSPNumber() + "],gatewayId:"
                            + String.valueOf(arg1.get("gatewayId")));
            } else {
                //                process.saveDeliverMsg(spNum, msg.getUserNumber(), msg.getSPNumber(),
                //                    msg.getMsgContentStr(), String.valueOf(arg1.get("gatewayId")), msg.getMsgFmt());

                deliverProcess.saveDeliverMsg(spNum, msg.getUserNumber(), msg.getSPNumber(),
                    msg.getMsgContentStr(), String.valueOf(arg1.get("gatewayId")), msg.getMsgFmt());

            }
        } else if (arg0 instanceof SMGPDeliverMessage) {

            SMGPDeliverMessage msg = (SMGPDeliverMessage) arg0;
            if (msg.getIsReport() == 1) {
                if (logger.isInfoEnabled()) {
                    logger.info("收到状态报告，忽略短信:" + msg);
                }
                return;
            }
            if (msg.getMsgLength() <= 0) {
                logger.warn("消息长度为空!srcid:[" + msg.getSrcTermID() + "],destId:["
                            + msg.getDestTermID() + "],gatewayId:"
                            + String.valueOf(arg1.get("gatewayId")));
            } else {
                //                process.saveDeliverMsg(spNum, msg.getSrcTermID(), msg.getDestTermID(),
                //                    msg.getMsgContentStr(), String.valueOf(arg1.get("gatewayId")),
                //                    msg.getMsgFormat());
                deliverProcess.saveDeliverMsg(spNum, msg.getSrcTermID(), msg.getDestTermID(),
                    msg.getMsgContentStr(), String.valueOf(arg1.get("gatewayId")),
                    msg.getMsgFormat());

            }
        } else if (arg0 instanceof SMGP3DeliverMessage) {

            SMGP3DeliverMessage msg = (SMGP3DeliverMessage) arg0;
            if (msg.getIsReport() == 1) {
                if (logger.isInfoEnabled()) {
                    logger.info("收到状态报告，忽略短信:" + msg);
                }
                return;
            }
            if (msg.getMsgLength() <= 0) {
                logger.warn("消息长度为空!srcid:[" + msg.getSrcTermID() + "],destId:["
                            + msg.getDestTermID() + "],gatewayId:"
                            + String.valueOf(arg1.get("gatewayId")));
            } else {
                //                process.saveDeliverMsg(spNum, msg.getSrcTermID(), msg.getDestTermID(),
                //                    msg.getMessageContentStr(), String.valueOf(arg1.get("gatewayId")),
                //                    msg.getMsgFormat());
                deliverProcess.saveDeliverMsg(spNum, msg.getSrcTermID(), msg.getDestTermID(),
                    msg.getMessageContentStr(), String.valueOf(arg1.get("gatewayId")),
                    msg.getMsgFormat());

            }
        } else {
            logger.error("接收到上行未知类型消息:" + arg0);
        }

    }

    /**
     * @see net.zoneland.gateway.MoListener#onReport(net.zoneland.gateway.comm.PMessage)
     */
    public PMessage onReport(PMessage arg0) {
        return null;
    }

    /**
     * 默认的就db2;
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if ("mysql".equalsIgnoreCase(dbName)) {
            logger.info("数据库：" + deliverMysqlProcess);
            this.process = deliverMysqlProcess;
        } else {
            this.process = deliverProcess;
        }
    }

    /**
     * Getter method for property <tt>dbName</tt>.
     * 
     * @return property value of dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Setter method for property <tt>dbName</tt>.
     * 
     * @param dbName
     *            value to be assigned to property dbName
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Getter method for property <tt>deliverProcess</tt>.
     * 
     * @return property value of deliverProcess
     */
    public DeliverProcess getDeliverProcess() {
        return deliverProcess;
    }

    /**
     * Setter method for property <tt>deliverProcess</tt>.
     * 
     * @param deliverProcess
     *            value to be assigned to property deliverProcess
     */
    public void setDeliverProcess(DeliverProcess deliverProcess) {
        this.deliverProcess = deliverProcess;
    }

    /**
     * Getter method for property <tt>deliverMysqlProcess</tt>.
     * 
     * @return property value of deliverMysqlProcess
     */
    public DeliverMysqlProcess getDeliverMysqlProcess() {
        return deliverMysqlProcess;
    }

    /**
     * Setter method for property <tt>deliverMysqlProcess</tt>.
     * 
     * @param deliverMysqlProcess
     *            value to be assigned to property deliverMysqlProcess
     */
    public void setDeliverMysqlProcess(DeliverMysqlProcess deliverMysqlProcess) {
        this.deliverMysqlProcess = deliverMysqlProcess;
    }

    /**
     * Getter method for property <tt>process</tt>.
     * 
     * @return property value of process
     */
    public Process getProcess() {
        return process;
    }

    /**
     * Setter method for property <tt>process</tt>.
     * 
     * @param process
     *            value to be assigned to property process
     */
    public void setProcess(Process process) {
        this.process = process;
    }

}
