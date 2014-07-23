package com.tiaotiaogift.web.home.ums;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.MD5;
import com.tiaotiaogift.web.home.ums.form.ResetPasswordForm;

@Controller
public class ResetPasswordController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/resetPassword.htm", method = RequestMethod.GET)
	public String doGet(ResetPasswordForm form, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		if (form.getU() == null || form.getCode() == null) {
			return "ums/password/resetPasswordError.vm";
		}
		User u = userMapper.selectByUserId(form.getU());
		if (u == null) {
			return "ums/password/resetPasswordError.vm";
		}
		if (form.getCode().equals(u.getCode())) {
			modelMap.addAttribute("form",form);
			return "ums/password/resetPassword.vm";
		} else {
			return "ums/password/resetPasswordError.vm";
		}

	}
	
	@RequestMapping(value = "/resetPassword.htm", method = RequestMethod.POST)
	public String doPOST(@Valid ResetPasswordForm form, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "ums/password/resetPassword.vm";
		}
		if(!form.getPassword().equals(form.getPasswordSec())){
			result.rejectValue("passwordSec", "passwordSec-notmatch", "两次输入的密码不一致");
			return "ums/password/resetPassword.vm";
		}
		User u = userMapper.selectByUserId(form.getU());
		if (u == null) {
			return "ums/password/resetPasswordError.vm";
		}
		User temp = new User();
		temp.setId(u.getId());
		temp.setPassword(MD5.md5(form.getPassword()));
		temp.setCode("");
		userMapper.updateByPrimaryKeySelective(temp);
		return "ums/password/resetPasswordSuccess.vm";

	}

}
