/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home.admin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.NewsMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.News;
import com.tiaotiaogift.ada.common.dal.page.Page;
import com.tiaotiaogift.ada.web.home.admin.form.NewsForm;

/**
 * 
 * @author gang
 * @version $Id: AdminNewsController.java, v 0.1 2012-12-26 下午10:35:03 gang Exp $
 */
@Controller
public class AdminNewsController {
    

    @Autowired
    private NewsMapper newsMapper;
    
    @RequestMapping(value = "/admin/adminNews.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, String type, HttpServletRequest request, ModelMap modelMap) {
        if (pageId == null) {
            pageId = 1;
        }
        List<News> res = newsMapper.selectByPage10((pageId - 1) * 10);
        int count = newsMapper.selectCount();
        Page p = new Page(pageId, count, res, 10);
        modelMap.put("res", p);

        return "admin/adminNews.vm";
    }
    
    @RequestMapping(value = "/admin/adminNewsEdit.htm", method = RequestMethod.GET)
    public String doEdit(NewsForm newsForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        if (newsForm != null && newsForm.getId() != null) {
            modelMap.addAttribute("label","修改新闻");
            News res = newsMapper.selectByPrimaryKey(newsForm.getId());
            newsForm.setId(res.getId());
            newsForm.setContent(res.getContent());
            newsForm.setSource(res.getSource());
            newsForm.setTitle(res.getTitle());
        }else{
            modelMap.addAttribute("label","发布新闻");
        }
        return "admin/adminNewsEdit.vm";
    }

    @RequestMapping(value = "/admin/adminNewsEdit.htm", method = RequestMethod.POST)
    public String doSave(NewsForm newsForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {

        News n= new News();
      n.setContent(newsForm.getContent());
      n.setGmtCreated(new Date());
      n.setSource(newsForm.getSource());
      n.setId(newsForm.getId());
      n.setTitle(newsForm.getTitle());
      int res = 0;
      if(!StringUtils.hasText(n.getId())){
          n.setId(UUID.randomUUID().toString());
          res = newsMapper.insert(n);
      }else{
         res = newsMapper.updateByPrimaryKeySelective(n);
      }
        if(res ==1){
            modelMap.addAttribute("tips","修改成功");
        }else{
            modelMap.addAttribute("tips","修改失败");
        }
        return "admin/adminNewsResult.vm";
    }
    
    @RequestMapping(value = "/admin/adminNewsDel.htm", method = RequestMethod.GET)
    public String doDel(String id,  HttpServletRequest request,
                         ModelMap modelMap) {

        int res = newsMapper.deleteByPrimaryKey(id);
        if(res ==1){
            modelMap.addAttribute("tips","删除成功");
        }else{
            modelMap.addAttribute("tips","删除失败");
        }

        return "admin/adminNewsResult.vm";
    }

}
