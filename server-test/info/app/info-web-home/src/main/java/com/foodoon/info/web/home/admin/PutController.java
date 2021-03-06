/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.info.web.home.admin;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.web.home.admin.form.QueryForm;

/**
 * 
 * @author gag
 * @version $Id: PutController.java, v 0.1 2012-12-12 下午2:27:58 gag Exp $
 */
@Controller
public class PutController {



    @RequestMapping(value = "/admin/doc.htm", method = RequestMethod.GET)
    public String dofind(QueryForm queryForm, HttpServletRequest request, ModelMap modelMap) {
//        List<Doc> doc = docMapper.selectByPageId((queryForm.getPageId() - 1) * 20);
   //     modelMap.put("res", doc);
        return "admin/doc.vm";
    }

    @RequestMapping(value = "/admin/put.htm", method = RequestMethod.POST)
    public String doPut(Doc docForm, HttpServletRequest request, ModelMap modelMap) {
       // docForm.setId(UUID.randomUUID().toString());
      //  docForm.setGmtCreated(new Date());
     //   docMapper.insert(docForm);

        return "admin/put.vm";
    }

    @RequestMapping(value = "/admin/put.htm", method = RequestMethod.GET)
    public String doGet(Doc docForm, HttpServletRequest request, ModelMap modelMap) {
    //    Doc doc = docMapper.selectByPrimaryKey(docForm.getId());
    //    modelMap.put("res", doc);
        return "admin/put.vm";
    }

    @RequestMapping(value = "/admin/docdel.htm", method = RequestMethod.GET)
    public String doDel(Doc docForm, HttpServletRequest request, ModelMap modelMap) {
      //  int res = docMapper.deleteByPrimaryKey(docForm.getId());

        return "admin/doc.vm";
    }

}
