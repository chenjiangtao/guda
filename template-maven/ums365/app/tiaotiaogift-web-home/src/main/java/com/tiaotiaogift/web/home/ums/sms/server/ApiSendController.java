/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.sms.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.biz.common.MsgOutEnum;
import com.tiaotiaogift.biz.common.account.AccountService;
import com.tiaotiaogift.biz.common.gateway.SmsMsg;
import com.tiaotiaogift.biz.common.gateway.SmsService;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.ContentUtil;
import com.tiaotiaogift.common.util.MD5;
import com.tiaotiaogift.common.util.StringRegUtils;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.sms.RecvController;

/**
 * 
 * @author gang
 * @version $Id: SendController.java, v 0.1 2013-5-5 上午8:12:46 gang Exp $
 */
@Controller
public class ApiSendController extends BaseJsonController {

    private Logger         log = LoggerFactory.getLogger(RecvController.class);

    @Autowired
    private MsgOutMapper   msgOutMapper;

    @Autowired
    private UserMapper     userMapper;

    @Autowired
    private AccountMapper  accountMapper;

    @Autowired
    private SmsService     smsService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/api/sms/send.htm")
    public void doGet(SendForm sendForm, HttpServletRequest request, HttpServletResponse response,
                      ModelMap modelMap) {
        Response resp = new Response();
        if (checkParams(sendForm) != 1) {
            resp.setCode("2");
            super.ajaxReturnObjText(resp, response);//参数不完整
            return;
        }
        if (!checkToken(sendForm)) {
            resp.setCode("11");
            super.ajaxReturnObjText(resp, response);//token验证错误
            return;
        }
        User user = userMapper.selectValidByUserId(sendForm.getUserName());
        if (user == null || !user.getPassword().equals(sendForm.getPassword())) {
            resp.setCode("3");
            super.ajaxReturnObjText(resp, response);//用户名或者密码错误
            return;
        }
        String[] recvs = sendForm.getMobile().split("；");
        if (recvs.length == 1 && sendForm.getMobile().indexOf(";") > -1) {
            recvs = sendForm.getMobile().split(";");
        }
        if (recvs.length > 1000) {
            super.ajaxReturnObjText("4", response);//超过最大值1000条
            return;
        }
        Account account = accountMapper.selectByUserId(user.getId());

        if (account.getBalance() < ContentUtil.getSmsCount(recvs.length, sendForm.getContent())) {
            resp.setCode("5");
            super.ajaxReturnObjText(resp, response);//余额不足
            return;
        }
        int count = 0;
        List<String> fail = new ArrayList<String>();
        StringBuilder failBuf = new StringBuilder();
        StringBuilder succBuf = new StringBuilder();
        List<String> outs = new ArrayList<String>(recvs.length);
        for (int i = 0, len = recvs.length; i < len; ++i) {
            if (StringRegUtils.isPhoneNumber(recvs[i])) {
                MsgOut out = new MsgOut();
                out.setId(UUID.randomUUID().toString());
                out.setRecvId(recvs[i]);
                succBuf.append(recvs[i]).append(";");
                out.setSendId(user.getId());
                String content = sendForm.getContent();
                out.setContent(content);
                out.setStatus(MsgOutEnum.init.getValue());
                out.setGmtCreated(new Date());
                out.setGmtModify(new Date());
                msgOutMapper.insert(out);
                outs.add(out.getId());
                ++count;

            } else {
                fail.add(recvs[i]);
                failBuf.append(recvs[i]).append(";");
                log.warn("接收方:[" + recvs[i] + "]不是手机号，丢弃");
            }
        }
        if (count > 0) {
            int trueCount = ContentUtil.getSmsCount(count, sendForm.getContent());
            accountService.send(user.getId(), trueCount);
            SmsMsg msg = new SmsMsg();
            msg.setContent(sendForm.getContent());
            msg.setRecv(succBuf.toString().substring(0, succBuf.toString().length() - 1));
            msg.setLinkId(String.valueOf(user.getLinkId()));
            String res = smsService.sendSms(msg);
            if ("0".equals(res)) {
                accountService.sendApiSuccess(outs, user.getId(), trueCount);
                resp.setCode("1");
                resp.setMsg("success");
                super.ajaxReturnObjText(resp, response);//发送成功
                return;
            } else {
                accountService.sendApiFail(outs, user.getId(), trueCount);
                resp.setCode("9");
                super.ajaxReturnObjText(resp, response);//运营商错误
                return;
            }

        }
        resp.setCode("8");
        super.ajaxReturnObjText(resp, response);//接收方有误
        return;
    }

    private int checkParams(SendForm sendForm) {
        if (StringUtils.hasText(sendForm.getUserName())
            && StringUtils.hasText(sendForm.getPassword())
            && StringUtils.hasText(sendForm.getMobile())
            && StringUtils.hasText(sendForm.getContent())
            && StringUtils.hasText(sendForm.getToken())) {
            return 1;
        }
        return 2;
    }

    public static boolean checkToken(SendForm sendForm) {
        String str1 = sendForm.getUserName().substring(sendForm.getUserName().length() - 1,
            sendForm.getUserName().length());
        String str2 = sendForm.getPassword().substring(sendForm.getPassword().length() - 1,
            sendForm.getPassword().length());
        if (sendForm.getToken().equals(MD5.md5(sendForm.getMobile() + str1 + str2))) {
            return true;
        }
        return false;
    }

}
