package com.foodoon.monitor.web.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.monitor.web.home.form.SetForm;


@Controller
public class SetController {
	
	@RequestMapping(value = "set.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "set.vm";

    }
	
	@RequestMapping(value = "set.htm", method = RequestMethod.POST)
    public String doPost(SetForm setForm, BindingResult result,HttpServletRequest request, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "set.vm";
		}
        return "set.vm";

    }

}
