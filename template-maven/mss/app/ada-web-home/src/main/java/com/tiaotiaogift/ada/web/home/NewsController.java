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
import com.tiaotiaogift.ada.common.dal.NewsMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.dataobject.News;

/**
 * 
 * @author gang
 * @version $Id: NewsController.java, v 0.1 2012-12-21 下午6:26:42 gang Exp $
 */
@Controller
public class NewsController {

    @Autowired
    private MsgMapper  msgMapper;

    @Autowired
    private NewsMapper newsMapper;

    @RequestMapping(value = "news/news.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, HttpServletRequest request, ModelMap modelMap) {
        if (pageId == null) {
            pageId = 1;
        }
        modelMap.put("pageId", pageId);
        List<Msg> list = msgMapper.selectNewMsg();
        modelMap.put("newMsg", list);
        List<News> res = newsMapper.selectByPage((pageId - 1));
        if (res.size() == 1) {
            modelMap.put("currentNew", res.get(0));
            if(pageId>1){
                List<News> temp = newsMapper.selectByPage((pageId - 2));
                modelMap.put("nextNew", temp.get(0));
            }
        } else if (res.size() == 2) {
            if (pageId == 1) {
                //第一篇
                modelMap.put("currentNew", res.get(0));
                modelMap.put("lastNew", res.get(1));
            } else {
                modelMap.put("currentNew", res.get(1));
                modelMap.put("nextNew", res.get(0));
                
            }
        } else if (res.size() == 3) {
            if (pageId == 1) {
                modelMap.put("currentNew", res.get(0));
                modelMap.put("lastNew", res.get(1));
            }else{
                modelMap.put("currentNew", res.get(1));
                modelMap.put("nextNew", res.get(0));
                modelMap.put("lastNew", res.get(2));
            }
        }
        return "news/news.vm";

    }

}
