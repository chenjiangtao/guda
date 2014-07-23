/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.info.common.dal.DetailMapper;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;
import com.foodoon.info.web.home.form.SearchForm;

/**
 * 类SearchController.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月15日 下午12:32:16
 */

@Controller
@RequestMapping("/search.htm")
public class SearchController {

    @Autowired
    private DetailMapper detailMapper;

    @RequestMapping()
    public String doSearch(SearchForm form, HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key", fillNull(form.getK()));
        form.calPage();
        params.put("start", (form.getPage() - 1) * form.getRows());
        params.put("rows", form.getRows());

        List<DetailWithBLOBs> details = detailMapper.search(params);

        modelMap.put("form", form);
        modelMap.put("details", details);
        return "search.vm";

    }

    private String fillNull(String str) {
        if (StringUtils.hasText(str)) {
            return str.trim();
        }
        return null;
    }

}
