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

import com.tiaotiaogift.ada.common.dal.QuestionMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Question;
import com.tiaotiaogift.ada.common.dal.page.Page;
import com.tiaotiaogift.ada.web.home.admin.form.QForm;

/**
 * 
 * @author gang
 * @version $Id: AdminQController.java, v 0.1 2012-12-26 下午11:15:34 gang Exp $
 */
@Controller
public class AdminQController {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @RequestMapping(value = "/admin/adminQ.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, String type, HttpServletRequest request, ModelMap modelMap) {
        if (pageId == null) {
            pageId = 1;
        }
        List<Question> res = questionMapper.selectByPage10((pageId - 1) * 10);
        int count = questionMapper.selectCount();
        Page p = new Page(pageId, count, res, 10);
        modelMap.put("res", p);

        return "admin/adminQ.vm";
    }
    
    @RequestMapping(value = "/admin/adminQEdit.htm", method = RequestMethod.GET)
    public String doEdit(QForm qForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        if (qForm != null && qForm.getId() != null) {
            modelMap.addAttribute("label","修改问题");
            Question res = questionMapper.selectByPrimaryKey(qForm.getId());
            qForm.setId(res.getId());
            qForm.setQ(res.getQ());
            qForm.setA(res.getA());
        }else{
            modelMap.addAttribute("label","发布问题");
        }
        return "admin/adminQEdit.vm";
    }

    @RequestMapping(value = "/admin/adminQEdit.htm", method = RequestMethod.POST)
    public String doSave(QForm qForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {

        Question n= new Question();
      n.setA(qForm.getA());
      n.setId(qForm.getId());
      n.setQ(qForm.getQ());
      n.setGmtCreated(new Date());
      int res = 0;
      if(!StringUtils.hasText(n.getId())){
          n.setId(UUID.randomUUID().toString());
          
          res = questionMapper.insert(n);
      }else{
         res = questionMapper.updateByPrimaryKeySelective(n);
      }
        if(res ==1){
            modelMap.addAttribute("tips","修改成功");
        }else{
            modelMap.addAttribute("tips","修改失败");
        }
        return "admin/adminQResult.vm";
    }
    
    @RequestMapping(value = "/admin/adminQDel.htm", method = RequestMethod.GET)
    public String doDel(String id,  HttpServletRequest request,
                         ModelMap modelMap) {

        int res = questionMapper.deleteByPrimaryKey(id);
        if(res ==1){
            modelMap.addAttribute("tips","删除成功");
        }else{
            modelMap.addAttribute("tips","删除失败");
        }

        return "admin/adminQResult.vm";
    }

}
