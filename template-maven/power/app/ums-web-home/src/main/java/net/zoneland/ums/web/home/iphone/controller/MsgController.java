/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.gateway.util.Base64;
import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationPrincipal;
import net.zoneland.ums.biz.config.constants.UmsConstants;
import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.biz.msg.process.MessageProcess;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.common.dal.bo.UmsMsgOutIphone;
import net.zoneland.ums.common.util.GsonHelper;
import net.zoneland.ums.common.util.enums.msg.IdentityEnum;
import net.zoneland.ums.web.home.base.BaseController;
import net.zoneland.ums.web.home.filter.AccessFilter;
import net.zoneland.ums.web.home.iphone.form.DelMsgRequest;
import net.zoneland.ums.web.home.iphone.form.FetchMsgRequest;
import net.zoneland.ums.web.home.iphone.form.FetchMsgResponse;
import net.zoneland.ums.web.home.iphone.form.IphoneCodeConstants;
import net.zoneland.ums.web.home.iphone.form.Response;
import net.zoneland.ums.web.home.iphone.form.SendMsgRequest;
import net.zoneland.ums.web.home.iphone.form.SetMsgStatusRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yangjuanying
 * @version $Id: SendMsgServiceImpl.java, v 0.1 2012-9-26 下午3:45:28 yangjuanying Exp $
 */
@Controller
public class MsgController extends BaseController {

    private final static Logger logger = Logger.getLogger(MsgController.class);

    @Autowired
    private MessageProcess      messageProcess;

    @Autowired
    private MsgSearchService    msgSearchService;

    @RequestMapping(value = "/sendMsg.json")
    public void sendMsgJson(String json, HttpServletRequest request,
                            HttpServletResponse httpResponse) {
        SendMsgRequest smr = GsonHelper.gson().fromJson(json, SendMsgRequest.class);
        String res = validSendMsgRequest(smr);
        Response response = new Response();
        if ("1".equals(res)) {
            if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) == null) {
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("未登录，请先登录。");
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;
            }
            OperationPrincipal userInfo = OperationContextHolder.getPrincipal();
            PrimitiveMessage pm = new PrimitiveMessage();
            pm.setRecvId(smr.getRecv());
            pm.setAppId(UmsConstants.APP_ID);
            pm.setIdentity(IdentityEnum.person.getValue());
            pm.setSendId(userInfo.getUserPhone());
            pm.setUserId(userInfo.getUserId());
            pm.setContent(smr.getMsgContent());
            pm.setAck(new Integer(0));
            boolean result = messageProcess.processIphone(pm);
            if (result) {
                response.setMsg(pm.getComments());
            } else {
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg(pm.getComments());
            }
            jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
        } else {
            response.setCode(IphoneCodeConstants.FAILURE);
            response.setMsg(res);
            jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
        }
    }

    /**
     * 
     * @param smr
     * @return
     */
    private String validSendMsgRequest(SendMsgRequest smr) {
        if (smr == null) {
            return "请求参数不能为空";
        }

        if (!StringUtils.hasText(smr.getRecv())) {
            return "recv不能为空";
        }
        if (!StringUtils.hasText(smr.getMsgContent())) {
            return "msgContent不能为空";
        }
        if (smr.getRecv().length() > 6000) {
            return "接收方手机不能超过500个";
        }
        if (smr.getMsgContent().length() > 650) {
            return "消息长度不能超过650";
        }
        smr.setMsgContent(new String(Base64.decode(smr.getMsgContent())));
        return "1";
    }

    @RequestMapping(value = "/fetchMsg.json")
    public void fetchMsgJson(String json, HttpServletRequest request,
                             HttpServletResponse httpResponse) {
        FetchMsgRequest fmr = GsonHelper.gson().fromJson(json, FetchMsgRequest.class);
        String res = validFetchMsgRequest(fmr);// 验证获取消息请求参数是否为空
        FetchMsgResponse response = new FetchMsgResponse();
        if ("1".equals(res)) {

            if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) == null) {
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("未登录，请先登录。");
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;
            }
            OperationPrincipal userInfo = OperationContextHolder.getPrincipal();
            try {
                Integer num = msgSearchService.findCountByRecvId(userInfo.getUserPhone(),
                    getSinceTime(fmr.getSinceTime()));
                response.setTotal(num);
                if (num != null && num > 0) {
                    List<UmsMsgOutIphone> results = msgSearchService.findByRecvId(
                        userInfo.getUserPhone(), getSinceTime(fmr.getSinceTime()), fmr.getPage(),
                        fmr.getCount());
                    Iterator<UmsMsgOutIphone> it = results.iterator();
                    while (it.hasNext()) {
                        UmsMsgOutIphone phone = it.next();
                        try {
                            phone.setContent(Base64.encode(phone.getContent().getBytes("UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            phone.setContent(Base64.encode(phone.getContent().getBytes()));
                        }
                    }
                    response.setPage(fmr.getPage());
                    response.setPageSize(fmr.getCount());
                    response.setResults(results);
                }
                response.setCode(IphoneCodeConstants.SUCCESS);
            } catch (Exception e) {
                logger.error("查询消息错误", e);
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("查询消息错误");
            }
        } else {
            response.setCode(IphoneCodeConstants.FAILURE);
            response.setMsg(res);
        }
        jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
    }

    @RequestMapping(value = "/delMsg.json")
    public void delMsgJson(String json, HttpServletRequest request, HttpServletResponse httpResponse) {
        DelMsgRequest req = GsonHelper.gson().fromJson(json, DelMsgRequest.class);
        String res = validDelMsgRequest(req);
        Response response = new Response();
        if ("1".equals(res)) {

            if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) == null) {
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("未登录，请先登录。");
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;
            }
            try {
                boolean result = msgSearchService.delMsg(req.getMsgId());
                if (result) {
                    response.setCode(IphoneCodeConstants.SUCCESS);
                } else {
                    response.setCode(IphoneCodeConstants.FAILURE);
                    response.setMsg("删除消息错误");
                }
            } catch (Exception e) {
                logger.error("删除消息错误", e);
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("删除消息错误");
            }

        } else {
            response.setCode(IphoneCodeConstants.FAILURE);
            response.setMsg(res);
        }
        jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
    }

    @RequestMapping(value = "/setMsgStatus.json")
    public void setMsgStatusJson(String json, HttpServletRequest request,
                                 HttpServletResponse httpResponse) {
        SetMsgStatusRequest req = GsonHelper.gson().fromJson(json, SetMsgStatusRequest.class);
        String res = validSetMsgStatusRequest(req);
        Response response = new Response();
        if ("1".equals(res)) {
            if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) == null) {
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("未登录，请先登录。");
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;
            }
            try {
                boolean result = msgSearchService.setMsgRead(req.getMsgId());
                if (result) {
                    response.setCode(IphoneCodeConstants.SUCCESS);
                } else {
                    response.setCode(IphoneCodeConstants.FAILURE);
                    response.setMsg("删除消息错误");
                }
            } catch (Exception e) {
                logger.error("删除消息错误", e);
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("删除消息错误");
            }

        } else {
            response.setCode(IphoneCodeConstants.FAILURE);
            response.setMsg(res);
        }
        jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
    }

    private String validFetchMsgRequest(FetchMsgRequest fmr) {
        if (fmr == null) {
            return "请求参数不能为空";
        }

        if (!StringUtils.hasText(fmr.getSinceTime())) {
            return "时间不能为空";
        }
        if (fmr.getPage() <= 0) {
            return "页码必须大于0";
        }
        if (fmr.getCount() <= 0) {
            return "每页记录数必须大于0";
        }
        return "1";
    }

    private Date getSinceTime(String sinceTime) throws ParseException {
        if ("0".equals(sinceTime)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.parse(sinceTime);
    }

    private String validSetMsgStatusRequest(SetMsgStatusRequest req) {
        if (req == null) {
            return "请求参数不能为空";
        }

        if (!StringUtils.hasText(req.getMsgId())) {
            return "msgid不能为空";
        }
        return "1";
    }

    private String validDelMsgRequest(DelMsgRequest req) {
        if (req == null) {
            return "请求参数不能为空";
        }

        if (!StringUtils.hasText(req.getMsgId())) {
            return "msgid不能为空";
        }
        return "1";
    }

    public void setMessageProcess(MessageProcess messageProcess) {
        this.messageProcess = messageProcess;
    }

}
