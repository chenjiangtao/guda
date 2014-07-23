/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import net.zoneland.gateway.util.ArrayUtil;
import net.zoneland.ums.gateway.MessageFactory;
import net.zoneland.ums.gateway.UmsGatewayFactory;
import net.zoneland.ums.gateway.form.Cmpp30Msg;
import net.zoneland.ums.gateway.form.CmppMsg;
import net.zoneland.ums.gateway.form.SgipMsg;
import net.zoneland.ums.gateway.form.Smgp3Msg;
import net.zoneland.ums.gateway.form.SmgpMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author gag
 * @version $Id: GatewayController.java, v 0.1 2012-12-10 上午8:55:47 gag Exp $
 */
@Controller
public class CmppController {

    private static final Logger logger = LoggerFactory.getLogger(UmsGatewayFactory.class);

    @Autowired
    private MessageFactory      messageFactory;

    @Autowired
    private UmsGatewayFactory   umsGatewayFactory;

    @RequestMapping(value = "cmpp.c")
    public String sendCmpp(CmppMsg cmppMsg, HttpServletRequest request, ModelMap modelMap) {
        logger.info("request:" + cmppMsg);
        if (cmppMsg.getGatewayId() == null) {
            return "test/cmpp.html";
        }
        Object gate = umsGatewayFactory.getGateway(cmppMsg.getGatewayId());
        CMPPSMProxy proxy = (CMPPSMProxy) gate;
        List<CMPPSubmitMessage> msgs = new ArrayList<CMPPSubmitMessage>();
        try {
            List<byte[]> bytesList = ArrayUtil.splitArray(cmppMsg.getMsg_Content(), 67,
                cmppMsg.getMsg_Fmt());
            if (bytesList.size() == 1) {

                CMPPSubmitMessage sm = null;
                sm = new CMPPSubmitMessage(1, 1, cmppMsg.getRegistered_Delivery(),
                    cmppMsg.getMsg_Level(), cmppMsg.getService_Id(), cmppMsg.getFee_UserType(),
                    cmppMsg.getFee_Terminal_Id(), cmppMsg.getTp_Pid(), 0, cmppMsg.getMsg_Fmt(),
                    cmppMsg.getMsg_Src(), cmppMsg.getFee_Type(), cmppMsg.getFee_Code(), null, null,
                    cmppMsg.getSrc_Terminal_Id(), cmppMsg.getDest_Terminal_Id().split(","),
                    bytesList.get(0), cmppMsg.getReserve());
                msgs.add(sm);

            } else {
                for (int i = 0, len = bytesList.size(); i < len; ++i) {
                    CMPPSubmitMessage sm = null;
                    sm = new CMPPSubmitMessage(bytesList.size(), i + 1,
                        cmppMsg.getRegistered_Delivery(), cmppMsg.getMsg_Level(),
                        cmppMsg.getService_Id(), cmppMsg.getFee_UserType(),
                        cmppMsg.getFee_Terminal_Id(), cmppMsg.getTp_Pid(), 1, cmppMsg.getMsg_Fmt(),
                        cmppMsg.getMsg_Src(), cmppMsg.getFee_Type(), cmppMsg.getFee_Code(), null,
                        null, cmppMsg.getSrc_Terminal_Id(), cmppMsg.getDest_Terminal_Id()
                            .split(","), bytesList.get(i), cmppMsg.getReserve());
                    msgs.add(sm);
                }
            }
            Iterator<CMPPSubmitMessage> it = msgs.iterator();
            while (it.hasNext()) {
                CMPPSubmitMessage m = it.next();
                CMPPSubmitRepMessage res = (CMPPSubmitRepMessage) proxy.send(m);
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息:" + m + ",返回：" + res);
                }

            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return "test/cmpp.html";
    }

    @RequestMapping(value = "cmpp30.c")
    public String sendCmpp30(Cmpp30Msg cmpp30Msg, HttpServletRequest request, ModelMap modelMap) {

        logger.info("request:" + cmpp30Msg);
        if (cmpp30Msg.getGatewayId() == null) {
            return "test/cmpp30.html";
        }
        Object gate = umsGatewayFactory.getGateway(cmpp30Msg.getGatewayId());
        CMPP30SMProxy proxy = (CMPP30SMProxy) gate;
        List<CMPP30SubmitMessage> list = new ArrayList<CMPP30SubmitMessage>();
        try {
            List<byte[]> bytesList = ArrayUtil.splitArray(cmpp30Msg.getMsg_Content(), 67,
                cmpp30Msg.getMsg_Fmt());
            if (bytesList.size() == 1) {
                CMPP30SubmitMessage sm = null;
                sm = new CMPP30SubmitMessage(1, 1, cmpp30Msg.getRegistered_Delivery(),
                    cmpp30Msg.getMsg_Level(), cmpp30Msg.getService_Id(),
                    cmpp30Msg.getFee_UserType(), cmpp30Msg.getFee_Terminal_Id(),
                    cmpp30Msg.getFee_Terminal_Type(), cmpp30Msg.getTp_Pid(), 0,
                    cmpp30Msg.getMsg_Fmt(), cmpp30Msg.getMsg_Src(), cmpp30Msg.getFee_Type(),
                    cmpp30Msg.getFee_Code(), null, null, cmpp30Msg.getSrc_Terminal_Id(), cmpp30Msg
                        .getDest_Terminal_Id().split(","), cmpp30Msg.getDest_Terminal_Type(),
                    bytesList.get(0), cmpp30Msg.getLinkID());

                list.add(sm);
            } else {
                for (int i = 0, len = bytesList.size(); i < len; ++i) {
                    CMPP30SubmitMessage sm = null;
                    sm = new CMPP30SubmitMessage(bytesList.size(), i + 1,
                        cmpp30Msg.getRegistered_Delivery(), cmpp30Msg.getMsg_Level(),
                        cmpp30Msg.getService_Id(), cmpp30Msg.getFee_UserType(),
                        cmpp30Msg.getFee_Terminal_Id(), cmpp30Msg.getFee_Terminal_Type(),
                        cmpp30Msg.getTp_Pid(), 1, cmpp30Msg.getMsg_Fmt(), cmpp30Msg.getMsg_Src(),
                        cmpp30Msg.getFee_Type(), cmpp30Msg.getFee_Code(), null, null,
                        cmpp30Msg.getSrc_Terminal_Id(), cmpp30Msg.getDest_Terminal_Id().split(","),
                        cmpp30Msg.getDest_Terminal_Type(), bytesList.get(i), cmpp30Msg.getLinkID());

                    list.add(sm);
                }
            }
            Iterator<CMPP30SubmitMessage> it = list.iterator();
            while (it.hasNext()) {
                CMPP30SubmitMessage m = it.next();
                CMPP30SubmitRepMessage res = (CMPP30SubmitRepMessage) proxy.send(m);
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息:" + m + ",返回：" + res);
                }

            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "test/cmpp30.html";
    }

    @RequestMapping(value = "sgip.c")
    public String sendSgip(SgipMsg sgipMsg, HttpServletRequest request, ModelMap modelMap) {
        logger.info("request:" + sgipMsg);
        if (sgipMsg.getGatewayId() == null) {
            return "test/sgip.html";
        }
        Object gate = umsGatewayFactory.getGateway(sgipMsg.getGatewayId());
        SGIPSMProxy proxy = (SGIPSMProxy) gate;
        List<SGIPSubmitMessage> list = new ArrayList<SGIPSubmitMessage>();
        try {
            List<byte[]> bytesList = ArrayUtil.splitArray(sgipMsg.getMessageContent(), 67,
                sgipMsg.getMessageCoding());
            if (bytesList.size() == 1) {
                SGIPSubmitMessage sm = null;

                sm = new SGIPSubmitMessage(sgipMsg.getSPNumber(), sgipMsg.getChargeNumber(),
                    sgipMsg.getUserNumber().split(","), sgipMsg.getCorpId(),
                    sgipMsg.getServiceType(), sgipMsg.getFeeType(), sgipMsg.getFeeValue(),
                    sgipMsg.getGivenValue(), sgipMsg.getAgentFlag(), sgipMsg.getMorelatetoMTFlag(),
                    sgipMsg.getPriority(), null, null, sgipMsg.getReportFlag(),
                    sgipMsg.getTP_pid(), 0, sgipMsg.getMessageCoding(), sgipMsg.getMessageType(),
                    bytesList.get(0).length, bytesList.get(0), sgipMsg.getReserve());
                list.add(sm);
            } else {
                for (int i = 0, len = bytesList.size(); i < len; ++i) {
                    SGIPSubmitMessage sm = null;

                    sm = new SGIPSubmitMessage(sgipMsg.getSPNumber(), sgipMsg.getChargeNumber(),
                        sgipMsg.getUserNumber().split(","), sgipMsg.getCorpId(),
                        sgipMsg.getServiceType(), sgipMsg.getFeeType(), sgipMsg.getFeeValue(),
                        sgipMsg.getGivenValue(), sgipMsg.getAgentFlag(),
                        sgipMsg.getMorelatetoMTFlag(), sgipMsg.getPriority(), null, null,
                        sgipMsg.getReportFlag(), sgipMsg.getTP_pid(), 1,
                        sgipMsg.getMessageCoding(), sgipMsg.getMessageType(),
                        bytesList.get(i).length, bytesList.get(i), sgipMsg.getReserve());
                    list.add(sm);
                }

            }
            Iterator<SGIPSubmitMessage> it = list.iterator();
            while (it.hasNext()) {
                SGIPSubmitMessage m = it.next();
                SGIPSubmitRepMessage res = (SGIPSubmitRepMessage) proxy.send(m);
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息:" + m + ",返回：" + res);
                }
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return "test/sgip.html";
    }

    @RequestMapping(value = "smgp.c")
    public String sendSmgp(SmgpMsg smgpMsg, HttpServletRequest request, ModelMap modelMap) {
        logger.info("request:" + smgpMsg);
        if (smgpMsg.getGatewayId() == null) {
            return "test/smgp.html";
        }
        Object gate = umsGatewayFactory.getGateway(smgpMsg.getGatewayId());
        SMGPSMProxy proxy = (SMGPSMProxy) gate;
        try {
            List<SMGPSubmitMessage> list = new ArrayList<SMGPSubmitMessage>();
            List<byte[]> bytesList = ArrayUtil.splitArray(smgpMsg.getMsgContent(), 67,
                smgpMsg.getMsgFormat());
            if (bytesList.size() == 1) {
                SMGPSubmitMessage sm = null;

                sm = new SMGPSubmitMessage(smgpMsg.getMsgType(), smgpMsg.getNeedReport(),
                    smgpMsg.getPriority(), smgpMsg.getServiceId(), smgpMsg.getFeeType(),
                    smgpMsg.getFeeCode(), smgpMsg.getFixedFee(), smgpMsg.getMsgFormat(), null,
                    null, smgpMsg.getSrcTermId(), smgpMsg.getChargeTermId(), smgpMsg
                        .getDestTermId().split(","), bytesList.get(0), smgpMsg.getReserve());

                list.add(sm);
            } else {
                for (int i = 0, len = bytesList.size(); i < len; ++i) {
                    SMGPSubmitMessage sm = null;

                    sm = new SMGPSubmitMessage(smgpMsg.getMsgType(), smgpMsg.getNeedReport(),
                        smgpMsg.getPriority(), smgpMsg.getServiceId(), smgpMsg.getFeeType(),
                        smgpMsg.getFeeCode(), smgpMsg.getFixedFee(), smgpMsg.getMsgFormat(), null,
                        null, smgpMsg.getSrcTermId(), smgpMsg.getChargeTermId(), smgpMsg
                            .getDestTermId().split(","), bytesList.get(i), smgpMsg.getReserve());

                    list.add(sm);
                }
            }
            Iterator<SMGPSubmitMessage> it = list.iterator();
            while (it.hasNext()) {
                SMGPSubmitMessage m = it.next();
                SMGPSubmitRespMessage res = (SMGPSubmitRespMessage) proxy.send(m);
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息:" + m + ",返回：" + res);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "test/smgp.html";
    }

    @RequestMapping(value = "smgp3.c")
    public String sendSmgp3(Smgp3Msg smgp3Msg, HttpServletRequest request, ModelMap modelMap) {
        logger.info("request:" + smgp3Msg);
        if (smgp3Msg.getGatewayId() == null) {
            return "test/smgp3.html";
        }
        Object gate = umsGatewayFactory.getGateway(smgp3Msg.getGatewayId());
        SMGP3SMProxy proxy = (SMGP3SMProxy) gate;
        List<SMGP3SubmitMessage> list = new ArrayList<SMGP3SubmitMessage>();
        try {
            List<byte[]> bytesList = ArrayUtil.splitArray(smgp3Msg.getMsgContent(), 67,
                smgp3Msg.getMsgFormat());
            if (bytesList.size() == 1) {
                SMGP3SubmitMessage sm = null;
                sm = new SMGP3SubmitMessage(smgp3Msg.getMsgType(), smgp3Msg.getNeedReport(),
                    smgp3Msg.getPriority(), smgp3Msg.getServiceId(), smgp3Msg.getFeeType(),
                    smgp3Msg.getFeeCode(), smgp3Msg.getFixedFee(), smgp3Msg.getMsgFormat(), null,
                    null, smgp3Msg.getSrcTermId(), smgp3Msg.getChargeTermId(), smgp3Msg
                        .getDestTermId().split(","), bytesList.get(0), smgp3Msg.getReserve(),
                    smgp3Msg.getTp_pid(), 0, smgp3Msg.getLinkId(), smgp3Msg.getMsgSrc(),
                    smgp3Msg.getChargeUserType(), smgp3Msg.getChargeTermType(),
                    smgp3Msg.getChargeTermPseudo(), smgp3Msg.getDestTermType(),
                    smgp3Msg.getDestTermPseudo(), 1, 1, smgp3Msg.getSubmitMsgType(),
                    smgp3Msg.getSpDealResult(), smgp3Msg.getMserviceId());

                list.add(sm);
            } else {
                for (int i = 0, len = bytesList.size(); i < len; ++i) {
                    SMGP3SubmitMessage sm = null;
                    sm = new SMGP3SubmitMessage(smgp3Msg.getMsgType(), smgp3Msg.getNeedReport(),
                        smgp3Msg.getPriority(), smgp3Msg.getServiceId(), smgp3Msg.getFeeType(),
                        smgp3Msg.getFeeCode(), smgp3Msg.getFixedFee(), smgp3Msg.getMsgFormat(),
                        null, null, smgp3Msg.getSrcTermId(), smgp3Msg.getChargeTermId(), smgp3Msg
                            .getDestTermId().split(","), bytesList.get(i), smgp3Msg.getReserve(),
                        smgp3Msg.getTp_pid(), 1, smgp3Msg.getLinkId(), smgp3Msg.getMsgSrc(),
                        smgp3Msg.getChargeUserType(), smgp3Msg.getChargeTermType(),
                        smgp3Msg.getChargeTermPseudo(), smgp3Msg.getDestTermType(),
                        smgp3Msg.getDestTermPseudo(), bytesList.size(), i + 1,
                        smgp3Msg.getSubmitMsgType(), smgp3Msg.getSpDealResult(),
                        smgp3Msg.getMserviceId());

                    list.add(sm);
                }
            }
            Iterator<SMGP3SubmitMessage> it = list.iterator();
            while (it.hasNext()) {
                SMGP3SubmitMessage m = it.next();
                SMGP3SubmitRespMessage res = (SMGP3SubmitRespMessage) proxy.send(m);
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息:" + m + ",返回：" + res);
                }

            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "test/smgp3.html";
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setUmsGatewayFactory(UmsGatewayFactory umsGatewayFactory) {
        this.umsGatewayFactory = umsGatewayFactory;
    }

}
