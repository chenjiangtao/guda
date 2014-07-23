/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.biz.common.MsgOutEnum;
import com.tiaotiaogift.biz.common.account.AccountService;
import com.tiaotiaogift.biz.common.gateway.SmsMsg;
import com.tiaotiaogift.biz.common.gateway.SmsService;
import com.tiaotiaogift.biz.common.mail.template.VelocityHelper;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.ContactMapper;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.common.mysql.dataobject.Contact;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.common.util.ContentUtil;
import com.tiaotiaogift.common.util.StringRegUtils;
import com.tiaotiaogift.web.home.ums.form.MsgForm;
import com.tiaotiaogift.web.mvc.Form;

/**
 * 
 * @author gag
 * @version $Id: IndexController.java, v 0.1 2012-12-7 下午4:33:28 gag Exp $
 */
@Controller
public class UmsController {

    private Logger         logger = LoggerFactory.getLogger(UmsController.class);

    @Autowired
    private MsgOutMapper   msgOutMapper;

    @Autowired
    private AccountMapper  accountMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContactMapper  contactMapper;

    @Autowired
    private SmsService     smsService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @RequestMapping(value = "/ums/index.htm", method = RequestMethod.GET)
    public String doGet(MsgForm msgForm, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {
        MsgOut msg = msgOutMapper.selectRecentByUserId(OperationContextHolder.getPrincipal()
            .getUserId());
        if (msg != null) {
            msgForm = new MsgForm();
            msgForm.setContent(msg.getContent());
            modelMap.addAttribute("msgForm", msgForm);
        }
        return "ums/index.vm";
    }

    @RequestMapping(value = "/ums/index.htm", method = RequestMethod.POST)
    @Form
    public String doPost(@Valid MsgForm msgForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {

        if (result.hasErrors()) {
            return "ums/index.vm";
        }
        logger.info("send:" + msgForm);
        String[] recvs = msgForm.getRecvId().split("；");
        if (recvs.length == 1 && msgForm.getRecvId().indexOf(";") > -1) {
            recvs = msgForm.getRecvId().split(";");
        }
        recvs = filter(recvs);
        // Object grade = OperationContextHolder.getPrincipal().getAttrVal(LoginController.GRADE_ATTR);

        //预付费用户
        Account account = accountMapper.selectByUserId(OperationContextHolder.getPrincipal()
            .getUserId());
        if (account.getBalance() < ContentUtil.getSmsCount(recvs.length, msgForm.getContent())) {
            result.rejectValue("recvId", "exceed-maxcount", "抱歉，您的余额已不足，您可以进入我的账户充值后，再发送短信。");
            return "ums/index.vm";
        }

        if (recvs.length + msgOutMapper.selectCountToday() > 100) {
            result.rejectValue("recvId", "exceed-maxcount", "对不起，已经超过今天的短信发送上限100条");
            return "ums/index.vm";
        }
        int count = 0;
        List<String> fail = new ArrayList<String>();
        StringBuilder failBuf = new StringBuilder();
        StringBuilder succBuf = new StringBuilder();
        List<MsgOut> outs = new ArrayList<MsgOut>(recvs.length);
        msgForm.setContent(msgForm.getContent().replaceAll("\\n", ""));
        for (int i = 0, len = recvs.length; i < len; ++i) {
            if (StringRegUtils.isPhoneNumber(recvs[i])) {
                MsgOut out = new MsgOut();
                out.setId(UUID.randomUUID().toString());
                out.setRecvId(recvs[i]);
                out.setSendId(OperationContextHolder.getPrincipal().getUserId());
                String content = msgForm.getContent();
                if (content.indexOf("${") > -1) {
                    content = renderContent(content, recvs[i]);
                }
                out.setContent(content);
                out.setStatus(MsgOutEnum.init.getValue());
                out.setGmtCreated(new Date());
                out.setGmtModify(new Date());
                msgOutMapper.insert(out);
                outs.add(out);
                succBuf.append(recvs[i]).append(";");
                ++count;

            } else {
                fail.add(recvs[i]);
                failBuf.append(recvs[i]).append(";");
                logger.warn("接收方:[" + recvs[i] + "]不是手机号，丢弃");
            }
        }
        String msg = "";
        if (fail.size() > 0) {
            msg = String.format("发送给%s个手机成功，以下号码：%s由于不是手机号，没有发送。", count, failBuf.toString());
        } else {
            msg = String.format("发送给%s个手机成功。", count);
        }
        msg = sendBatch(outs);
        modelMap.addAttribute("msg", msg);
        return "ums/sendResult.vm";
    }

    private String[] filter(String[] str) {
        if (str == null) {
            return null;
        }
        Set<String> s = new HashSet<String>();
        for (int i = 0, len = str.length; i < len; ++i) {
            s.add(str[i]);
        }
        String[] temp = new String[s.size()];
        Iterator<String> t = s.iterator();
        int j = 0;
        while (t.hasNext()) {
            temp[j++] = t.next();
        }
        return temp;
    }

    private String sendBatch(List<MsgOut> outs) {
        Map<String, List<MsgOut>> maps = groupBy(outs);
        Iterator<List<MsgOut>> it = maps.values().iterator();
        int success = 0;
        int fail = 0;
        StringBuilder buf = new StringBuilder();
        while (it.hasNext()) {
            List<MsgOut> list = it.next();
            if (doSend(list)) {
                success += list.size();
            } else {
                fail += list.size();
                Iterator<MsgOut> o = list.iterator();
                while (o.hasNext()) {
                    buf.append(o.next().getRecvId()).append(";");
                }
            }
        }
        if (fail > 0) {
            return String.format("发送给%s个手机成功，以下号码：%s由于运营商返回错误，发送失败。", success,
                buf.substring(0, buf.length() - 1));
        } else {
            return String.format("发送给%s个手机成功。", success);
        }

    }

    private boolean doSend(List<MsgOut> outs) {
        if (outs == null || outs.size() == 0) {
            return true;
        }
        Iterator<MsgOut> it = outs.iterator();
        StringBuilder succBuf = new StringBuilder();
        List<String> ids = new ArrayList<String>(outs.size());
        while (it.hasNext()) {
            MsgOut out = it.next();
            ids.add(out.getId());
            succBuf.append(out.getRecvId()).append(";");
        }
        int trueCount = ContentUtil.getSmsCount(outs.size(), outs.get(0).getContent());
        accountService.send(OperationContextHolder.getPrincipal().getUserId(), trueCount);
        SmsMsg sms = new SmsMsg();
        sms.setContent(outs.get(0).getContent());
        sms.setRecv(succBuf.toString().substring(0, succBuf.toString().length() - 1));
        sms.setLinkId(String.valueOf(OperationContextHolder.getPrincipal().getAttrVal(
            LoginController.LINK_ID_ATTR)));
        String res = smsService.sendSms(sms);

        if ("0".equals(res)) {
            accountService.sendApiSuccess(ids, OperationContextHolder.getPrincipal().getUserId(),
                trueCount);
            return true;
        } else {
            accountService.sendApiFail(ids, OperationContextHolder.getPrincipal().getUserId(),
                trueCount);
            return false;
        }

    }

    private Map<String, List<MsgOut>> groupBy(List<MsgOut> outs) {
        Map<String, List<MsgOut>> maps = new HashMap<String, List<MsgOut>>();
        if (outs == null) {
            return maps;
        }
        Iterator<MsgOut> it = outs.iterator();
        while (it.hasNext()) {
            MsgOut out = it.next();
            List<MsgOut> list = maps.get(out.getContent());
            if (list == null) {
                list = new ArrayList<MsgOut>();
                list.add(out);
                maps.put(out.getContent(), list);
            } else {
                list.add(out);
            }
        }
        return maps;
    }

    private String renderContent(String content, String recvId) {
        Map<String, Object> selparams = new HashMap<String, Object>();
        selparams.put("userId", OperationContextHolder.getPrincipal().getUserId());
        selparams.put("phone", recvId);
        Contact contact = contactMapper.selectByPhone(selparams);
        if (contact != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taobaoName", contact.getTaobaoName());
            params.put("taobaoOrder", contact.getTaobaoId());
            params.put("deliveryName", contact.getDeliveryName());
            params.put("deliveryNo", contact.getDeliveryNo());
            params.put("paipaiName", contact.getPaipaiName());
            params.put("paipaiOrder", contact.getPaipaiId());
            try {
                return VelocityHelper.renderString(content, params);
            } catch (Exception e) {
                logger.error("解析短信模版错误", e);
            }
        } else {
            logger.error("无法找到用户" + OperationContextHolder.getPrincipal().getLogonId() + ",联系人手机为:"
                         + recvId + "的联系人");
        }
        return content;

    }
}
