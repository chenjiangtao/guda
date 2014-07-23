/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.biz.config.appadmin.MsgTemplateBiz;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.biz.msg.flow.FlowControl;
import net.zoneland.ums.biz.msg.in.LockAppService;
import net.zoneland.ums.biz.msg.in.MsgInService;
import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.Base64;
import net.zoneland.ums.common.util.FlowChecker;
import net.zoneland.ums.common.util.GsonHelper;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.service.webservice.form.FetchRequest;
import net.zoneland.ums.service.webservice.form.FetchTemplateRequest;
import net.zoneland.ums.service.webservice.form.MessageInVo;
import net.zoneland.ums.service.webservice.form.Response;
import net.zoneland.ums.service.webservice.form.SendCommonRequest;
import net.zoneland.ums.service.webservice.form.SendRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.reflect.TypeToken;

/**
 *
 * @author gag
 * @version $Id: MsgServiceImpl.java, v 0.1 2012-9-3 下午3:59:26 gag Exp $
 */
public class MsgServiceImpl implements MsgService {

    private static final Logger logger          = Logger.getLogger(MsgServiceImpl.class);

    @Autowired
    private FlowControl         flowControl;

    @Autowired
    private SocketProcess       sendMsgProcess;

    @Autowired
    private MsgInService        msgInService;

    @Autowired
    private MsgTemplateBiz      msgTemplateBiz;

    @Autowired
    private KeywordBiz          keywordBiz;

    @Autowired
    private LockAppService      lockAppService;

    //private FlowChecker         sendMsgChecker  = new FlowChecker(3);

    private final FlowChecker   fetchMsgChecker = new FlowChecker(1);

    public String sendMsgJson(String json) {
        if (logger.isInfoEnabled()) {
            logger.info("request json:[" + json + "],\n convert to [" + decodeBase64(json) + "]");
        }
        SendRequest sr = GsonHelper.gson().fromJson(decodeBase64(json), SendRequest.class);
        return buildResponse(processMsg(sr));
    }

    public String sendMsgListJson(String json) {
        if (logger.isInfoEnabled()) {
            logger.info("request json:[" + json + "],\n convert to [" + decodeBase64(json) + "]");
        }
        List<SendRequest> sendRequests = GsonHelper.gson().fromJson(decodeBase64(json),
            new TypeToken<List<SendRequest>>() {
            }.getType());
        List<Response> listResult = new ArrayList<Response>();
        for (int i = 0, size = sendRequests.size(); i < size; i++) {
            SendRequest sendRequest = sendRequests.get(i);
            listResult.add(processMsg(sendRequest));
        }
        return buildResponse(listResult);
    }

    /**
     * @see net.zoneland.ums.service.webservice.MsgService#sendCommonMsgListJson(java.lang.String)
     */
    public String sendCommonMsgListJson(String json) {
        if (logger.isInfoEnabled()) {
            logger.info("request common list json:[" + json + "],\n convert to ["
                        + decodeBase64(json) + "]");
        }
        List<SendCommonRequest> sendCommonRequests = GsonHelper.gson().fromJson(decodeBase64(json),
            new TypeToken<List<SendCommonRequest>>() {
            }.getType());
        List<Response> listResult = new ArrayList<Response>();
        for (int i = 0, size = sendCommonRequests.size(); i < size; i++) {
            SendCommonRequest sendCommonRequest = sendCommonRequests.get(i);
            listResult.add(process(sendCommonRequest));
        }
        return buildResponse(listResult);
    }

    public String sendCommonMsgJson(String json) {
        if (logger.isInfoEnabled()) {
            logger.info("request common json:[" + json + "],\n convert to [" + decodeBase64(json)
                        + "]");
        }
        SendCommonRequest sr = GsonHelper.gson().fromJson(decodeBase64(json),
            SendCommonRequest.class);
        return buildResponse(process(sr));
    }

    private String decodeBase64(String json) {
        try {
            return new String(Base64.decodeBase64(json.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return new String(Base64.decodeBase64(json.getBytes()));

    }

    /**
     * @see net.zoneland.ums.service.webservice.MsgService#fetchMsgJson(java.lang.String)
     */
    public String fetchMsgJson(String json) {
        FetchRequest fr = GsonHelper.gson().fromJson(decodeBase64(json), FetchRequest.class);
        String res = validSendRequest(fr);
        if ("1".equals(res)) {
            if (!fetchMsgChecker.checker(fr.getAppId())) {
                if (logger.isInfoEnabled()) {
                    logger.info("fetch requency!:" + fr);
                }
                Response response = new Response();
                response.setCode(CodeConstants.FREQUENCY_FAILURE);
                response.setDesc(Collections.EMPTY_LIST);
                return buildResponse(response);
            }

            ServiceRequest serviceRequest = convert2Fetch(fr);
            List<UmsMsgIn> ins = Collections.emptyList();
            boolean lockResult = false;
            try {
                lockResult = lockAppService.lockApp(serviceRequest.getAppId());
                if (lockResult) {
                    logger.info("lock app success.appid:" + serviceRequest.getAppId());
                    ins = msgInService.fetchMsgIn(serviceRequest.getAppId(),
                        serviceRequest.getSubAppId(), MsgInStatusEnum.send.getValue(),
                        serviceRequest.getMaxCount());
                } else {
                    logger.warn("lock app fail.appid:" + serviceRequest.getAppId());
                }

            } catch (Exception e) {
                logger.error("fetch msgIn error .appid:" + serviceRequest.getAppId(), e);
                Response response = new Response();
                response.setCode(CodeConstants.FAILURE);
                response.setDesc(Collections.EMPTY_LIST);
                return buildResponse(response);
            } finally {
                if (lockResult) {
                    lockAppService.releaseApp(serviceRequest.getAppId());
                    logger.info("release app success.appid:" + serviceRequest.getAppId());
                }
            }

            if (ins.size() == 0) {
                Response response = new Response();
                response.setCode(CodeConstants.SUCCESS);
                response.setDesc(Collections.EMPTY_LIST);
                return buildResponse(response);
            }

            Response response = new Response();
            response.setCode(CodeConstants.SUCCESS);
            List<MessageInVo> inVos = new ArrayList<MessageInVo>();
            Iterator<UmsMsgIn> its = ins.iterator();
            while (its.hasNext()) {
                UmsMsgIn in = its.next();
                MessageInVo inVo = new MessageInVo();
                ObjectBuilder.copyObject(in, inVo);
                inVos.add(inVo);
            }
            response.setDesc(inVos);
            return buildResponse(response);
        } else {
            Response response = new Response();
            response.setCode(CodeConstants.FAILURE);
            response.setDesc(Collections.EMPTY_LIST);
            return buildResponse(response);
        }

    }

    private String buildResponse(Object response) {
        if (response == null) {
            return "";
        }
        String result = "";
        try {
            result = Base64
                .encodeBase64String(GsonHelper.gson().toJson(response).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    /**
     * @see net.zoneland.ums.service.webservice.MsgService#fetchTemplateJson(java.lang.String)
     */
    public String fetchTemplateJson(String json) {
        FetchTemplateRequest fr = GsonHelper.gson().fromJson(decodeBase64(json),
            FetchTemplateRequest.class);
        String res = validFetchTemplateRequest(fr);
        if ("1".equals(res)) {
            try {
                List<UmsMsgTemplate> lists = msgTemplateBiz.fetchTemplate(filter(fr.getAppId()),
                    filter(fr.getSubAppId()), filter(fr.getTemplateId()));
                if (lists != null) {
                    Iterator<UmsMsgTemplate> its = lists.iterator();
                    while (its.hasNext()) {
                        UmsMsgTemplate temp = its.next();
                        String content = HtmlUtils.htmlUnescape(temp.getContent());
                        temp.setContent(content);
                    }
                }
                return buildResponse(lists);
            } catch (Exception e) {
                logger.error("fetchTemplateJson  数据库错误", e);
                return buildResponse(Collections.EMPTY_LIST);
            }
        } else {
            return buildResponse(Collections.EMPTY_LIST);
        }
    }

    private Response processMsg(SendRequest sr) {
        String res = validSendRequest(sr);
        if ("1".equals(res)) {
            ServiceRequest service = convert2Send(sr);
            //目前webservice发送的请求应该都是用户短信
            service.setMsgType(3);
            String[] recvId = StringHelper.splitRecvId(service.getUmsTo());
            try {
                String keyResult = keywordBiz.includeKeyword(service.getMsg(), service.getAppId());
                if (StringHelper.isNotEmpty(keyResult)) {
                    Response response = new Response();
                    response.setCode(CodeConstants.KEYWORD_FAILURE);
                    response.setDesc("消息内容含有非法关键词。");
                    return (response);
                }
                int flow = flowControl.checkFlow(sr.getAppId(), recvId.length);
                if (flow > 0) {
                    // 超过流量
                    String result = String.valueOf(flow).substring(0, 1);
                    Response response = new Response();
                    if ("1".equalsIgnoreCase(result)) {
                        response.setDesc("日流量超过阀值。"
                                         + Integer.valueOf(String.valueOf(flow).substring(1)));
                    } else if ("2".equalsIgnoreCase(result)) {
                        response.setDesc("月流量超过阀值。"
                                         + Integer.valueOf(String.valueOf(flow).substring(1)));
                    } else {
                        response.setDesc("流量超过阀值。");
                    }
                    response.setCode(CodeConstants.FLOW_FAILURE);

                    return (response);

                } else {
                    ProcessResult pr = sendMsgProcess.doProcess(service);
                    if (pr.isSuccess()) {
                        Response response = new Response();
                        response.setCode(CodeConstants.SUCCESS);
                        response.setDesc(pr.getMsg());
                        return (response);
                    } else {
                        Response response = new Response();
                        response.setCode(CodeConstants.RECV_FAILURE);
                        response.setDesc(pr.getMsg());
                        return (response);
                    }
                }
            } catch (Exception e) {
                logger.error("send msg 数据库错误", e);
                Response response = new Response();
                response.setCode(CodeConstants.OTHER_FAILURE);
                response.setDesc("其他错误");
                return (response);
            }
        } else {
            Response response = new Response();
            response.setCode(CodeConstants.ARG_FAILURE);
            response.setDesc(res);
            return (response);
        }
    }

    private Response process(SendCommonRequest sr) {

        String res = validCommonSendRequest(sr);
        if ("1".equals(res)) {
            ServiceRequest service = convert2CommonSend(sr);
            //目前webservice发送的请求应该都是用户短信
            String[] recvId = StringHelper.splitRecvId(service.getUmsTo());
            try {
                String keyResult = keywordBiz.includeKeyword(service.getMsg(), service.getAppId());
                if (StringHelper.isNotEmpty(keyResult)) {
                    Response response = new Response();
                    response.setCode(CodeConstants.KEYWORD_FAILURE);
                    response.setDesc("消息内容含有非法关键词。");
                    return (response);
                }
                int flow = flowControl.checkFlow(sr.getAppId(), recvId.length);
                if (flow > 0) {
                    // 超过流量
                    String result = String.valueOf(flow).substring(0, 1);
                    Response response = new Response();
                    if ("1".equalsIgnoreCase(result)) {
                        response.setDesc("日流量超过阀值。"
                                         + Integer.valueOf(String.valueOf(flow).substring(1)));
                    } else if ("2".equalsIgnoreCase(result)) {
                        response.setDesc("月流量超过阀值。"
                                         + Integer.valueOf(String.valueOf(flow).substring(1)));
                    } else {
                        response.setDesc("流量超过阀值。");
                    }
                    response.setCode(CodeConstants.FLOW_FAILURE);

                    return (response);

                } else {
                    ProcessResult pr = sendMsgProcess.doProcess(service);
                    if (pr.isSuccess()) {
                        Response response = new Response();
                        response.setCode(CodeConstants.SUCCESS);
                        response.setDesc(pr.getMsg());
                        return (response);
                    } else {
                        Response response = new Response();
                        response.setCode(CodeConstants.RECV_FAILURE);
                        response.setDesc(pr.getMsg());
                        return (response);
                    }
                }
            } catch (Exception e) {
                logger.error("send msg 数据库错误", e);
                Response response = new Response();
                response.setCode(CodeConstants.OTHER_FAILURE);
                response.setDesc("ERROR-999其他错误");
                return (response);
            }
        } else {
            Response response = new Response();
            response.setCode(CodeConstants.ARG_FAILURE);
            response.setDesc(res);
            return (response);
        }

    }

    private String validSendRequest(FetchRequest sr) {
        if (sr == null) {
            return "请求参数不能为空";

        }
        if (!StringUtils.hasText(sr.getAppId())) {
            return "appid不能为空";
        }

        if (sr.getMaxCount() < 0 || sr.getMaxCount() > 100) {
            return "取消息记录数量必须在0到100之间";
        }
        if (sr.getMaxCount() == 0) {
            sr.setMaxCount(1);
        }
        return "1";
    }

    private String filter(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        return StringUtils.trimWhitespace(str);

    }

    private String validSendRequest(SendRequest sr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if (sr == null) {
            return "请求参数不能为空";

        }
        if (!StringUtils.hasText(sr.getAppId())) {
            return "appid不能为空";
        }
        if (!StringUtils.hasText(sr.getRecvId())) {
            return "接收方不能为空";
        }
        if (sr.getRecvId().length() > 6000) {
            return "接收方手机不能超过500个";
        }
        if (!StringUtils.hasText(sr.getContent())) {
            return "消息内容不能为空";
        }
        if (sr.getContent().length() > 650) {
            return "消息长度不能超过650";
        }
        if (StringUtils.hasText(sr.getSendDate())) {
            try {
                Date sendTime = dateFormat.parse(sr.getSendDate());
                if (sendTime.getTime() < new Date().getTime()) {
                    return "定时发送时间不能小于当前时间";
                }
            } catch (ParseException e) {
                logger.error("定时发送时间格式错误", e);
                return "定时发送时间格式错误";
            }
        }
        return "1";
    }

    private String validCommonSendRequest(SendCommonRequest sr) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if (sr == null) {
            return "请求参数不能为空";

        }
        if (!StringUtils.hasText(sr.getAppId())) {
            return "appid不能为空";
        }
        if (!StringUtils.hasText(sr.getRecvId())) {
            return "接收方不能为空";
        }
        if (sr.getRecvId().length() > 6000) {
            return "接收方手机不能超过500个";
        }
        if (!StringUtils.hasText(sr.getContent())) {
            return "消息内容不能为空";
        }
        if (sr.getContent().length() > 650) {
            return "消息长度不能超过650";
        }
        if (StringUtils.hasText(sr.getSendDate())) {
            try {
                Date sendTime = dateFormat.parse(sr.getSendDate());
                if (sendTime.getTime() < new Date().getTime()) {
                    return "定时发送时间不能小于当前时间";
                }
            } catch (ParseException e) {
                logger.error("定时发送时间格式错误", e);
                return "定时发送时间格式错误";
            }
        }
        return "1";
    }

    private String validFetchTemplateRequest(FetchTemplateRequest sr) {
        if (sr == null) {
            return "请求参数不能为空";

        }
        if (!StringUtils.hasText(sr.getAppId()) && !StringUtils.hasText(sr.getTemplateId())) {
            return "appid或者templateId不能都为空";
        }

        return "1";
    }

    private ServiceRequest convert2Send(SendRequest sr) {
        ServiceRequest service = new ServiceRequest();
        service.setRequestCode(CodeConstants.REQUEST_1002);
        service.setAck(getInt(sr.getAck()));
        service.setAppId(sr.getAppId());
        service.setMsg(sr.getContent());
        service.setCreateUser(sr.getCreateUser());
        service.setFlowNo(sr.getFlowNo());
        if (StringUtils.hasText(sr.getAreaNo())) {
            service.setOrgNo(sr.getAreaNo());
        }
        //兼容营销发送的单位号是orgNo字段
        if (StringUtils.hasText(sr.getOrgNo())) {
            service.setOrgNo(sr.getOrgNo());
        }
        service.setPriority(getInt(sr.getPriority()));
        service.setUmsTo(sr.getRecvId());
        service.setReply(sr.getReply());
        service.setRep(getInt(sr.getRetry()));
        service.setSubAppId(sr.getSubAppId());
        service.setSendDate(getDate(sr.getSendDate()));
        service.setValidTime(getInt(sr.getValidTime()));
        service.setTemplateId(sr.getTemplateId());
        service.setBizName(sr.getBizName());
        service.setBizType(sr.getBizType());
        return service;
    }

    private ServiceRequest convert2CommonSend(SendCommonRequest sr) {
        ServiceRequest service = new ServiceRequest();
        service.setRequestCode(CodeConstants.REQUEST_1002);
        service.setAck(getInt(sr.getAck()));
        service.setAppId(sr.getAppId());
        service.setMsg(sr.getContent());
        service.setOrgNo(sr.getAreaNo());
        service.setPriority(getInt(sr.getPriority()));
        service.setUmsTo(sr.getRecvId());
        service.setReply(sr.getReply());
        service.setRep(getInt(sr.getRetry()));
        service.setSubAppId(sr.getSubAppId());
        service.setSendDate(getDate(sr.getSendDate()));
        service.setValidTime(getInt(sr.getValidTime()));
        service.setTemplateId(sr.getTemplateId());
        if (sr.getMsgType() == null) {
            service.setMsgType(3);
        } else {
            service.setMsgType(Integer.valueOf(sr.getMsgType()));
        }

        return service;
    }

    private ServiceRequest convert2Fetch(FetchRequest sr) {
        ServiceRequest service = new ServiceRequest();
        service.setRequestCode(CodeConstants.REQUEST_3004);
        service.setAppId(sr.getAppId());
        service.setSubAppId(sr.getSubAppId());
        service.setMaxCount(sr.getMaxCount());
        return service;
    }

    private int getInt(String val) {
        if (val == null) {
            return 0;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {

        }
        return 0;
    }

    private Date getDate(String val) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if (val == null) {
            return null;
        }
        try {
            return dateFormat.parse(val);
        } catch (ParseException e) {

        }
        return null;
    }

    public void setFlowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
    }

    public void setSendMsgProcess(SocketProcess sendMsgProcess) {
        this.sendMsgProcess = sendMsgProcess;
    }

    public void setMsgInService(MsgInService msgInService) {
        this.msgInService = msgInService;
    }

}
