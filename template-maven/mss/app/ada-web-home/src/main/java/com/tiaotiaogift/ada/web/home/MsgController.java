/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.MsgMapper;
import com.tiaotiaogift.ada.common.dal.MsgReplyMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.dataobject.MsgReply;
import com.tiaotiaogift.ada.common.dal.page.Page;
import com.tiaotiaogift.ada.web.home.form.MsgForm;
import com.tiaotiaogift.ada.web.home.form.MsgVo;

/**
 * 
 * @author gang
 * @version $Id: MsgController.java, v 0.1 2012-12-21 下午6:31:00 gang Exp $
 */
@Controller
public class MsgController {
    
    @Autowired
    private MsgMapper msgMapper;
    
    @Autowired
    private MsgReplyMapper msgReplyMapper;
    
    @RequestMapping(value = "msg/msg.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, MsgForm msgForm, BindingResult result,HttpServletRequest request, ModelMap modelMap) {
        if(pageId == null){
            pageId=1;
        }
        List<Msg> newmsg = msgMapper.selectNewMsg();
        modelMap.put("newMsg", newmsg);
        List<Msg>list = msgMapper.selectMsgByPage((pageId-1)*5);
       
        Iterator<Msg> it =  list.iterator();
        List<MsgVo> res = new ArrayList<MsgVo>();
        while(it.hasNext()){
            MsgVo vo = new MsgVo();
            Msg msg =  it.next();
            List<MsgReply> reply = msgReplyMapper.selectByMsgId(msg.getId());
            vo.setMsgReplys(reply);
            vo.setMsg(msg);
            res.add(vo);
        }
       int count = msgMapper.selectMsgCount();
        Page p = new Page(pageId.intValue(), count, res);
        modelMap.addAttribute("res",p);
        return "msg/msg.vm";

    }
    
    @RequestMapping(value = "msg/msg.htm", method = RequestMethod.POST)
    public String doSave(@Valid MsgForm msgForm, BindingResult result,HttpServletRequest request, ModelMap modelMap) {
        if(result.hasErrors()){

            List<Msg> newmsg = msgMapper.selectNewMsg();
            modelMap.put("newMsg", newmsg);
            List<Msg>list = msgMapper.selectMsgByPage(0);
           
            Iterator<Msg> it =  list.iterator();
            List<MsgVo> res = new ArrayList<MsgVo>();
            while(it.hasNext()){
                MsgVo vo = new MsgVo();
                Msg msg =  it.next();
                List<MsgReply> reply = msgReplyMapper.selectByMsgId(msg.getId());
                vo.setMsgReplys(reply);
                vo.setMsg(msg);
                res.add(vo);
            }
           int count = msgMapper.selectMsgCount();
            Page p = new Page(1, count, res);
            modelMap.addAttribute("res",p);
            return "msg/msg.vm";
        }
        Msg msg = new Msg();
        msg.setId(UUID.randomUUID().toString());
        msg.setContent(msgForm.getContent());
        msg.setContactName(msgForm.getContactName());
        msg.setContactInfo(msgForm.getContactInfo());
        msg.setGmtCreated(new Date());
        msgMapper.insert(msg);
        return "msg/msgSuccess.vm";

    }

}
