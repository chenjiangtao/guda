/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.sms.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.common.dal.MsgInMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgIn;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.FlowChecker;
import com.tiaotiaogift.common.util.enums.MsgInStatusEnum;
import com.tiaotiaogift.web.home.BaseJsonController;

/**
 * 
 * @author gang
 * @version $Id: RecvController.java, v 0.1 2013-5-9 上午11:01:43 gang Exp $
 */
@Controller
public class ApiRecvController extends BaseJsonController {

    @Autowired
    private UserMapper  userMapper;

    @Autowired
    private MsgInMapper msgInMapper;

    private FlowChecker check = new FlowChecker(1);

    @RequestMapping(value = "/api/sms/recv.htm")
    public void doGet(SendForm sendForm, HttpServletRequest request, HttpServletResponse response,
                      ModelMap modelMap) {
        ResponseRecv resp = new ResponseRecv();
        if (checkParams(sendForm) != 1) {
            resp.setCode("2");
            super.ajaxReturnObjText(resp, response);//参数不完整
            return;
        }
        if (!ApiSendController.checkToken(sendForm)) {
            resp.setCode("11");
            super.ajaxReturnObjText(resp, response);//token验证错误
            return;
        }
        if (!check.checker(sendForm.getUserName())) {
            resp.setCode("7");
            super.ajaxReturnObjText(resp, response);//请求过于频繁拿
            return;
        }
        User user = userMapper.selectValidByUserId(sendForm.getUserName());
        if (user == null || !user.getPassword().equals(sendForm.getPassword())) {
            resp.setCode("3");
            super.ajaxReturnObjText(resp, response);//用户名或者密码错误
            return;
        }
        List<MsgIn> msgIns = msgInMapper.fetchMsgIn(user.getId());
        List<ResponseMsgIn> ins = new ArrayList<ResponseMsgIn>(msgIns.size());
        List<String> ids = new ArrayList<String>(msgIns.size());
        if (msgIns.size() > 0) {
            Iterator<MsgIn> ms = msgIns.iterator();
            while (ms.hasNext()) {
                MsgIn m = ms.next();
                ids.add(m.getId());
                ResponseMsgIn r = new ResponseMsgIn();
                r.setContent(m.getContent());
                r.setGmtCreated(m.getGmtCreated());
                r.setMobile(m.getSendId());
                ins.add(r);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("status", MsgInStatusEnum.success.getValue());
            params.put("ids", ids);
            int rows = msgInMapper.updateStatusBatch(params);
            if (rows == ids.size()) {
                resp.setCode("1");
                resp.setObj(ins);
                super.ajaxReturnObjText(resp, response);//
                return;
            }
        } else {
            resp.setCode("1");
            super.ajaxReturnObjText(resp, response);//
            return;
        }

        resp.setCode("8");
        super.ajaxReturnObjText(resp, response);//其他错误
        return;
    }

    private int checkParams(SendForm sendForm) {
        if (StringUtils.hasText(sendForm.getUserName())
            && StringUtils.hasText(sendForm.getPassword())
            && StringUtils.hasText(sendForm.getToken())) {
            return 1;
        }
        return 2;
    }

}
