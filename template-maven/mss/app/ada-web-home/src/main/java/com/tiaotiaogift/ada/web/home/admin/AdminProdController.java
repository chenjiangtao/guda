/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.ProdMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.Prod;
import com.tiaotiaogift.ada.common.dal.page.Page;
import com.tiaotiaogift.ada.web.home.admin.form.ProdForm;

/**
 * 
 * @author gang
 * @version $Id: AdminProdController.java, v 0.1 2012-12-23 下午9:01:59 gang Exp $
 */
@Controller
public class AdminProdController {

    @Autowired
    private ProdMapper prodMapper;

    @RequestMapping(value = "/admin/adminProd.htm", method = RequestMethod.GET)
    public String doGet(Integer pageId, String type, HttpServletRequest request, ModelMap modelMap) {
        if (pageId == null) {
            pageId = 1;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", (pageId - 1) * 8);
        params.put("type", type);
        List<Prod> res = prodMapper.selectByType(params);
        int count = prodMapper.selectCountByType(params);
        Page p = new Page(pageId, count, res, 8);
        modelMap.put("res", p);

        return "admin/adminProd.vm";
    }

    @RequestMapping(value = "/admin/adminProdEdit.htm", method = RequestMethod.GET)
    public String doEdit(ProdForm prodForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        if (prodForm != null && prodForm.getId() != null) {
            modelMap.addAttribute("label","修改产品信息");
            Prod res = prodMapper.selectByPrimaryKey(prodForm.getId());
            prodForm.setName(res.getName());
            prodForm.setDescrib(res.getDescrib());
            prodForm.setDiscount(res.getDiscount());
            prodForm.setImg(res.getImg());
            prodForm.setPrice(res.getPrice());
            prodForm.setType(res.getType());
        }else{
            modelMap.addAttribute("label","发布新产品");
        }
        return "admin/adminProdEdit.vm";
    }

    @RequestMapping(value = "/admin/adminProdEdit.htm", method = RequestMethod.POST)
    public String doSave(ProdForm prodForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {

        Prod p = new Prod();
        p.setDescrib(prodForm.getDescrib());
        p.setDiscount(prodForm.getDiscount());
        p.setGmtCreated(new Date());
        p.setId(prodForm.getId());
        p.setName(prodForm.getName());
        p.setPrice(prodForm.getPrice());
        p.setType(prodForm.getType());
        int res = 0;
        if(!StringUtils.hasText(p.getId())){
            p.setId(UUID.randomUUID().toString());
            res = prodMapper.insert(p);
        }else{
         res = prodMapper.updateByPrimaryKeySelective(p);
        }
        if(res ==1){
            modelMap.addAttribute("tips","修改成功");
        }else{
            modelMap.addAttribute("tips","修改失败");
        }
        return "admin/adminProdResult.vm";
    }
    
    @RequestMapping(value = "/admin/adminProdDel.htm", method = RequestMethod.GET)
    public String doDel(String id,  HttpServletRequest request,
                         ModelMap modelMap) {

        int res = prodMapper.deleteByPrimaryKey(id);
        if(res ==1){
            modelMap.addAttribute("tips","删除成功");
        }else{
            modelMap.addAttribute("tips","删除失败");
        }

        return "admin/adminProdResult.vm";
    }

}
