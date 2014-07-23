/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.zoneland.gateway.comm.cmpp.CMPPSMProxy;
import net.zoneland.gateway.comm.cmpp.message.CMPPSubmitMessage;
import net.zoneland.gateway.comm.cmpp.message.CMPPSubmitRepMessage;
import net.zoneland.gateway.comm.cmpp3.CMPP30SMProxy;
import net.zoneland.gateway.comm.cmpp3.message.CMPP30SubmitMessage;
import net.zoneland.gateway.comm.cmpp3.message.CMPP30SubmitRepMessage;
import net.zoneland.gateway.comm.sgip.SGIPSMProxy;
import net.zoneland.gateway.comm.sgip.message.SGIPSubmitMessage;
import net.zoneland.gateway.comm.sgip.message.SGIPSubmitRepMessage;
import net.zoneland.gateway.comm.smgp.SMGPSMProxy;
import net.zoneland.gateway.comm.smgp.message.SMGPSubmitMessage;
import net.zoneland.gateway.comm.smgp.message.SMGPSubmitRespMessage;
import net.zoneland.gateway.comm.smgp3.SMGP3SMProxy;
import net.zoneland.gateway.comm.smgp3.message.SMGP3SubmitMessage;
import net.zoneland.gateway.comm.smgp3.message.SMGP3SubmitRespMessage;
import net.zoneland.gateway.message.factory.CMPP30MsgFactory;
import net.zoneland.gateway.message.factory.CMPPMsgFactory;
import net.zoneland.gateway.message.factory.SGIPMsgFactory;
import net.zoneland.gateway.message.factory.SMGP3MsgFactory;
import net.zoneland.gateway.message.factory.SMGPMsgFactory;
import net.zoneland.ums.common.util.enums.GateEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gang
 * @version $Id: MessageFactory.java, v 0.1 2012-9-1 下午5:11:43 gang Exp $
 */
public class MessageFactory {

    private static final Logger logger     = Logger.getLogger(MessageFactory.class);

    @Autowired
    private CMPPMsgFactory      cmppMsgFactory;

    @Autowired
    private CMPP30MsgFactory    cmpp30MsgFactory;

    @Autowired
    private SGIPMsgFactory      sgipMsgFactory;

    @Autowired
    private SMGPMsgFactory      smgpMsgFactory;

    @Autowired
    private SMGP3MsgFactory     smgp3MsgFactory;

    @Autowired
    private UmsGatewayFactory   umsGatewayFactory;

    private int                 fmt_cmpp   = 8;

    private int                 fmt_cmpp30 = 8;

    private int                 fmt_sgip   = 8;

    private int                 fmt_smgp   = 8;

    private int                 fmt_smgp3  = 8;

    public boolean sendMsg(Message message) throws IOException {
        if (message == null) {
            return false;
        }
        if (logger.isInfoEnabled()) {
            logger.info("发送消息:" + message);
        }
        GateEnum type = umsGatewayFactory.getGatewayType(message.getGatewayId());
        Object gate = umsGatewayFactory.getGateway(message.getGatewayId());
        if (GateEnum.CMPP == type) {
            int tempType = fmt_cmpp;
            int orgType = getInt(message.getContentType());
            if (orgType == 4 || orgType == 21) {
                tempType = orgType;
            }
            List<CMPPSubmitMessage> list = cmppMsgFactory.buildCMPPSubmitMessage(
                message.getSendId(), message.getRecvId(), message.getContent(), tempType,
                message.getFeeType(), message.getFee(), 3);
            CMPPSMProxy proxy = (CMPPSMProxy) gate;
            Iterator<CMPPSubmitMessage> it = list.iterator();
            int result = -1;
            while (it.hasNext()) {
                CMPPSubmitMessage m = it.next();
                if (!proxy.isClosed()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("发送消息:" + m);
                    }
                    CMPPSubmitRepMessage res = (CMPPSubmitRepMessage) proxy.send(m);
                    if (logger.isInfoEnabled()) {
                        logger.info("返回消息:" + m + ",返回：" + res);
                    }
                    if (res == null) {
                        return true;
                    }
                    result = res.getResult();
                    if (result != 0) {
                        break;
                    }
                }
            }
            return result == 0;

        } else if (GateEnum.CMPP30 == type) {
            int tempType = fmt_cmpp30;
            int orgType = getInt(message.getContentType());
            if (orgType == 4 || orgType == 21) {
                tempType = orgType;
            }
            List<CMPP30SubmitMessage> list = cmpp30MsgFactory.buildCMPP30SubmitMessage(
                message.getSendId(), message.getRecvId(), message.getContent(), tempType,
                message.getFeeType(), message.getFee(), 3);
            CMPP30SMProxy proxy = (CMPP30SMProxy) gate;
            Iterator<CMPP30SubmitMessage> it = list.iterator();
            int result = -1;
            while (it.hasNext()) {
                CMPP30SubmitMessage m = it.next();
                if (!proxy.isClosed()) {
                    CMPP30SubmitRepMessage res = (CMPP30SubmitRepMessage) proxy.send(m);
                    if (logger.isInfoEnabled()) {
                        logger.info("发送消息:" + m + ",返回：" + res);
                    }
                    if (res == null) {
                        return true;
                    }
                    result = res.getResult();
                    if (result != 0) {
                        break;
                    }
                }
            }
            return result == 0;

        } else if (GateEnum.SGIP == type) {
            int tempType = fmt_sgip;
            int orgType = getInt(message.getContentType());
            if (orgType == 4 || orgType == 21) {
                tempType = orgType;
            }

            SGIPSMProxy proxy = (SGIPSMProxy) gate;
            List<SGIPSubmitMessage> list = sgipMsgFactory.buildSGIPSubmitMessage(
                message.getSendId(), message.getRecvId(), message.getContent(), tempType, 0,
                message.getFee(), 3, String.valueOf(proxy.getCorpId()));

            Iterator<SGIPSubmitMessage> it = list.iterator();
            int result = -1;
            while (it.hasNext()) {
                SGIPSubmitMessage m = it.next();
                if (!proxy.isClosed()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("发送消息:" + m);
                    }
                    SGIPSubmitRepMessage res = (SGIPSubmitRepMessage) proxy.send(m);
                    if (logger.isInfoEnabled()) {
                        logger.info("返回消息:" + m + ",返回：" + res);
                    }
                    if (res == null) {
                        return true;
                    }
                    result = res.getResult();
                    if (result != 0) {
                        break;
                    }
                } else {
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        logger.error("联通网关断开睡眠", e);
                    }
                }
            }
            return result == 0;
        } else if (GateEnum.SMGP == type) {
            //由于电信发送速度太快会造成电信运营商返回系统忙的错误，在此增加睡眠降低发送速度
            //            try {
            //                Thread.sleep(180L);
            //            } catch (InterruptedException e) {
            //                logger.error("", e);
            //            }
            int tempType = fmt_smgp;
            int orgType = getInt(message.getContentType());
            if (orgType == 4 || orgType == 21) {
                //电信不支持21编码
                tempType = 4;
            }
            List<SMGPSubmitMessage> list = smgpMsgFactory.buildSMGPSubmitMessage(
                message.getSendId(), message.getRecvId(), message.getContent(), tempType,
                message.getFeeType(), message.getFee(), 3);
            SMGPSMProxy proxy = (SMGPSMProxy) gate;
            Iterator<SMGPSubmitMessage> it = list.iterator();
            int result = -1;
            while (it.hasNext()) {
                SMGPSubmitMessage m = it.next();
                if (!proxy.isClosed()) {
                    SMGPSubmitRespMessage res = (SMGPSubmitRespMessage) proxy.send(m);
                    if (logger.isInfoEnabled()) {
                        logger.info("发送消息:" + m + ",返回：" + res);
                    }
                    if (res == null) {
                        return true;
                    }
                    result = res.getStatus();
                    if (result != 0) {
                        break;
                    }
                }
            }
            return result == 0;
        } else if (GateEnum.SMGP3 == type) {
            //由于电信发送速度太快会造成电信运营商返回系统忙的错误，在此增加睡眠降低发送速度
            //            try {
            //                Thread.sleep(180L);
            //            } catch (InterruptedException e) {
            //                logger.error("", e);
            //            }
            int tempType = fmt_smgp3;
            int orgType = getInt(message.getContentType());
            if (orgType == 4 || orgType == 21) {
                //电信不支持21编码
                tempType = 4;
            }
            List<SMGP3SubmitMessage> list = smgp3MsgFactory.buildSMGP3SubmitMessage(
                message.getSendId(), message.getRecvId(), message.getContent(), tempType,
                message.getFeeType(), message.getFee(), 3);
            SMGP3SMProxy proxy = (SMGP3SMProxy) gate;
            Iterator<SMGP3SubmitMessage> it = list.iterator();
            int result = -1;
            while (it.hasNext()) {
                SMGP3SubmitMessage m = it.next();
                if (!proxy.isClosed()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("发送消息:" + m);
                    }
                    SMGP3SubmitRespMessage res = (SMGP3SubmitRespMessage) proxy.send(m);
                    if (logger.isInfoEnabled()) {
                        logger.info("返回消息:" + m + ",返回：" + res);
                    }
                    if (res == null) {
                        return true;
                    }
                    result = res.getStatus();
                    if (result != 0) {
                        break;
                    }
                } else {
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        logger.error("电信网关断开睡眠", e);
                    }
                }
            }
            return result == 0;
        } else {
            logger.error("未知网关类型。");
        }
        return false;

    }

    private int getInt(Integer val) {
        if (val == null) {
            return 0;
        }
        return val.intValue();
    }

    public void setFmt_cmpp(int fmt_cmpp) {
        this.fmt_cmpp = fmt_cmpp;
    }

    public void setFmt_cmpp30(int fmt_cmpp30) {
        this.fmt_cmpp30 = fmt_cmpp30;
    }

    public void setFmt_sgip(int fmt_sgip) {
        this.fmt_sgip = fmt_sgip;
    }

    public void setFmt_smgp(int fmt_smgp) {
        this.fmt_smgp = fmt_smgp;
    }

    public void setFmt_smgp3(int fmt_smgp3) {
        this.fmt_smgp3 = fmt_smgp3;
    }

}
