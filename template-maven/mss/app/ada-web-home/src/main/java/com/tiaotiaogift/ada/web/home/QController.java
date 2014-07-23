/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.MsgMapper;
import com.tiaotiaogift.ada.common.dal.QuestionMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.dataobject.Question;
import com.tiaotiaogift.ada.common.dal.page.Page;

/**
 * 
 * @author gang
 * @version $Id: QController.java, v 0.1 2012-12-2 上午11:49:23 gang Exp $
 */
@Controller
public class QController {
    
    @Autowired
    private MsgMapper msgMapper;
    
    @Autowired
    private QuestionMapper questionMapper;

    @RequestMapping(value = "q/q.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId,HttpServletRequest request, ModelMap modelMap) {
        if(pageId == null){
            pageId =1;
        }
        List<Msg>list = msgMapper.selectNewMsg();
        modelMap.put("newMsg", list);
        
        List<Question> res = questionMapper.selectByPage((pageId-1)*5);
        int count = questionMapper.selectCount();
        Page p = new Page(pageId, count, res,5);
        modelMap.put("res", p);
        return "q/q.vm";

    }



}
