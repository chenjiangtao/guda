/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.MsgMapper;
import com.tiaotiaogift.ada.common.dal.ProdMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.dataobject.Prod;
import com.tiaotiaogift.ada.common.dal.page.Page;

/**
 * 
 * @author gang
 * @version $Id: ProdController.java, v 0.1 2012-12-2 上午11:51:27 gang Exp $
 */
@Controller
public class ProdController {
    
    @Autowired
    private MsgMapper msgMapper;
    
    @Autowired
    private ProdMapper prodMapper;


    

    @RequestMapping(value = "prod/prod.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId,String type,HttpServletRequest request, ModelMap modelMap) {
  if(pageId == null){
            pageId =1;
        }
        List<Msg>list = msgMapper.selectNewMsg();
        modelMap.put("newMsg", list);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("start", (pageId-1)*8);
        params.put("type", type);
        List<Prod> res = prodMapper.selectByType(params);
        int count = prodMapper.selectCountByType(params);
        Page p = new Page(pageId, count, res,8);
        modelMap.put("res", p);
        modelMap.put("type", type);
        return "prod/prod.vm";

    }
    
    @RequestMapping(value = "prod/prodDetail.htm", method = RequestMethod.GET)
    public String doGetDetail(String id,HttpServletRequest request, ModelMap modelMap) {

        List<Msg>list = msgMapper.selectNewMsg();
        modelMap.put("newMsg", list);
       
        Prod res = prodMapper.selectByPrimaryKey(id);
        modelMap.put("prod", res);
        if(res!=null){
        modelMap.put("type", res.getType());
        }

        return "prod/prodDetail.vm";

    }



}
