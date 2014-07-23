/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.ClassifyMapper;
import com.foodoon.info.common.dal.DetailMapper;
import com.foodoon.info.common.dal.SubClassifyMapper;
import com.foodoon.info.common.dal.page.Page;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;
import com.foodoon.info.web.home.form.PageForm;

/**
 * 类AdminPubController.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月18日 上午9:17:45
 */
@Controller
public class AdminPubController {

    @Autowired
    private ClassifyMapper    classifyMapper;

    @Autowired
    private SubClassifyMapper subClassifyMapper;

    @Autowired
    private DetailMapper      detailMapper;

    @RequestMapping(value = "/admin/pub.htm", method = RequestMethod.GET)
    public String doGet(PageForm form, HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        form.calPage();
        params.put("start", (form.getPage() - 1) * form.getRows());
        params.put("rows", form.getRows());

        List<DetailWithBLOBs> details = detailMapper.search(params);
        int c = detailMapper.searchCount(params);
        modelMap.put("form", form);
        modelMap.put("details", new Page(form.getPage(), c, details));
        return "admin/pub.vm";
    }

}
