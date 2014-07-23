/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.sms;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.common.dal.MsgInMapper;
import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgIn;
import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.web.home.BaseJsonController;

/**
 * 
 * @author gang
 * @version $Id: RecvController.java, v 0.1 2013-5-4 下午10:02:12 gang Exp $
 */
@Controller
public class RecvController extends BaseJsonController {

    private Logger       log = LoggerFactory.getLogger(RecvController.class);

    @Autowired
    private MsgInMapper  msgInMapper;

    @Autowired
    private MsgOutMapper msgOutMapper;

    @RequestMapping(value = "/sms/recv.htm")
    public void doGet(String Mobile, String Message, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {

        MsgIn in = new MsgIn();
        in.setId(UUID.randomUUID().toString());
        in.setContent(Message);

        in.setSendId(Mobile);
        List<MsgOut> out = msgOutMapper.selectByRecvIdLimit(Mobile);
        if (out.size() > 0) {
            in.setRecvId(out.get(0).getSendId());
        }
        in.setGmtCreated(new Date());
        log.info("收到上行短信:" + in);
        msgInMapper.insert(in);

        super.ajaxReturnObj("!!-begin--!!收到通道信息!!-end--!!", response);
    }

}
