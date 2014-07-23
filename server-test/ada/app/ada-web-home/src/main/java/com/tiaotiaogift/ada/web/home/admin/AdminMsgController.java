/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.MsgMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.page.Page;

/**
 * 
 * @author gang
 * @version $Id: AdminMsgController.java, v 0.1 2012-12-26 下午11:48:49 gang Exp $
 */
@Controller
public class AdminMsgController {
    
    @Autowired
    private MsgMapper msgMapper;
    
    @RequestMapping(value = "/admin/adminMsg.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId,HttpServletRequest request, ModelMap modelMap) {
        if (pageId == null) {
            pageId = 1;
        }
        List<Msg> res = msgMapper.selectMsgByPage10((pageId - 1) * 10);
        int count = msgMapper.selectMsgCount();
        Page p = new Page(pageId, count, res, 10);
        modelMap.put("res", p);
        return "admin/adminMsg.vm";
    }

}
