package com.foodoon.demo.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.demo.biz.OrgBiz;
import com.foodoon.demo.dao.domain.OrgDO;
import com.foodoon.demo.web.form.OrgEditForm;
import com.foodoon.demo.web.form.OrgForm;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;
import com.foodoon.tools.web.util.RequestUtil;


@Controller
public class OrgAction {


    @Autowired
    private OrgBiz orgBiz;

    @RequestMapping(value = "org/list.htm", method = RequestMethod.GET)
    public String list(HttpServletRequest request, ModelMap modelMap) {
        int pageId = RequestUtil.getInt(request, "pageNo");
        int pageSize = RequestUtil.getInt(request, "pageSize");
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setPageNo(pageId);
        baseQuery.setPageSize(pageSize);
        BizResult bizResult = orgBiz.list(baseQuery);
        if (bizResult.success) {
            modelMap.putAll(bizResult.data);
            return "org/list.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "org/edit.htm", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, ModelMap modelMap, OrgEditForm orgEditForm,
        BindingResult result, Map<String,Object> model) {
        int id = RequestUtil.getInt(request, "id");
        BizResult bizResult = orgBiz.detail(id);
        if (bizResult.success) {
            modelMap.putAll(bizResult.data);
            orgEditForm.initForm(((OrgDO)bizResult.data.get("orgDO")));
            return "org/edit.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "org/detail.htm", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, ModelMap modelMap) {
        int id = RequestUtil.getInt(request, "id");
        BizResult bizResult = orgBiz.detail(id);
        if (bizResult.success) {
            modelMap.putAll(bizResult.data);
            return "org/detail.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "org/create.htm", method = RequestMethod.GET)
    public String create(HttpServletRequest request, ModelMap modelMap, OrgForm orgForm,
        BindingResult result, Map<String,Object> model) {
        return "org/create.vm";
    }

    @RequestMapping(value = "org/doCreate.htm", method = RequestMethod.POST)
    public String doCreate(HttpServletRequest request, ModelMap modelMap,@Valid OrgForm orgForm,
        BindingResult result, Map<String,Object> model) {
        if (result.hasErrors()) {
            return "org/create.vm";
        }
        OrgDO orgDO = orgForm.toDO();
        BizResult bizResult = orgBiz.create(orgDO);
        if (bizResult.success) {
            return "redirect:/org/list.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "org/doUpdate.htm", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap,@Valid OrgEditForm orgEditForm,
        BindingResult result, Map<String,Object> model) {
        if (result.hasErrors()) {
            return "org/edit.vm";
        }
        OrgDO orgDO = orgEditForm.toDO();
        BizResult bizResult = orgBiz.update(orgDO);
        if (bizResult.success) {
            return "redirect:/org/list.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "org/doDelete.htm", method = RequestMethod.POST)
    public String doDelete(HttpServletRequest request, ModelMap modelMap) {
        int id = RequestUtil.getInt(request, "id");
        BizResult bizResult = orgBiz.delete(id);
        if (bizResult.success) {
            return "org/list.vm";
        } else {
            return "common/error.vm";
        }

    }



}
