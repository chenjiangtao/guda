package com.foodoon.well.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.well.biz.StaffBiz;
import com.foodoon.well.dao.domain.StaffDO;
import com.foodoon.well.web.form.StaffEditForm;
import com.foodoon.well.web.form.StaffForm;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;
import com.foodoon.tools.web.util.RequestUtil;


@Controller
public class StaffAction {


    @Autowired
    private StaffBiz staffBiz;

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
    }

    @RequestMapping(value = "staff/list.htm", method = RequestMethod.GET)
    public String list(HttpServletRequest request, ModelMap modelMap) {
        int pageId = RequestUtil.getInt(request, "pageNo");
        int pageSize = RequestUtil.getInt(request, "pageSize");
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setPageNo(pageId);
        baseQuery.setPageSize(pageSize);
        BizResult bizResult = staffBiz.list(baseQuery);
        if (bizResult.success) {
            modelMap.putAll(bizResult.data);
            return "staff/list.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "staff/edit.htm", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, ModelMap modelMap, StaffEditForm staffEditForm,
        BindingResult result, Map<String,Object> model) {
        int id = RequestUtil.getInt(request, "id");
        BizResult bizResult = staffBiz.detail(id);
        if (bizResult.success) {
            modelMap.putAll(bizResult.data);
            staffEditForm.initForm(((StaffDO)bizResult.data.get("staffDO")));
            return "staff/edit.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "staff/detail.htm", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, ModelMap modelMap) {
        int id = RequestUtil.getInt(request, "id");
        BizResult bizResult = staffBiz.detail(id);
        if (bizResult.success) {
            modelMap.putAll(bizResult.data);
            return "staff/detail.vm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "staff/create.htm", method = RequestMethod.GET)
    public String create(HttpServletRequest request, ModelMap modelMap, StaffForm staffForm,
        BindingResult result, Map<String,Object> model) {
        return "staff/create.vm";
    }

    @RequestMapping(value = "staff/doCreate.htm", method = RequestMethod.POST)
    public String doCreate(HttpServletRequest request, ModelMap modelMap,@Valid StaffForm staffForm,
        BindingResult result, Map<String,Object> model) {
        if (result.hasErrors()) {
            return "staff/create.vm";
        }
        StaffDO staffDO = staffForm.toDO();
        BizResult bizResult = staffBiz.create(staffDO);
        if (bizResult.success) {
            return "redirect:/staff/list.htm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "staff/doUpdate.htm", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap,@Valid StaffEditForm staffEditForm,
        BindingResult result, Map<String,Object> model) {
        if (result.hasErrors()) {
            return "staff/edit.vm";
        }
        StaffDO staffDO = staffEditForm.toDO();
        BizResult bizResult = staffBiz.update(staffDO);
        if (bizResult.success) {
            return "redirect:/staff/list.htm";
        } else {
            return "common/error.vm";
        }

    }

    @RequestMapping(value = "staff/doDelete.htm", method = RequestMethod.POST)
    public String doDelete(HttpServletRequest request, ModelMap modelMap) {
        int id = RequestUtil.getInt(request, "id");
        BizResult bizResult = staffBiz.delete(id);
        if (bizResult.success) {
            return "staff/list.vm";
        } else {
            return "common/error.vm";
        }

    }



}
