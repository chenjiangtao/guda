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
import com.tiaotiaogift.ada.common.dal.PublishMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.dataobject.Publish;

/**
 * 
 * @author gang
 * @version $Id: OdmController.java, v 0.1 2012-12-21 下午6:28:50 gang Exp $
 */
@Controller
public class OdmController {
    
    @Autowired
    private MsgMapper msgMapper;
    
    @Autowired
    private PublishMapper publishMapper;


    
    @RequestMapping(value = "odm/odm.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        List<Msg>list = msgMapper.selectNewMsg();
        modelMap.put("newMsg", list);
        List<Publish> res = publishMapper.selectPublishByType("odm");
        modelMap.put("contents", res);
        return "odm/odm.vm";

    }

}
