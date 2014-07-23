package com.tiaotiaogift.web.home.ums;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.biz.common.mail.MailService;
import com.tiaotiaogift.biz.common.mail.template.VelocityHelper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.User;

import com.tiaotiaogift.web.home.ums.form.FindPasswordForm;


@Controller
public class FindPasswordController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/findPassword.htm", method = RequestMethod.GET)
	public String doGet( FindPasswordForm form, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		return "ums/password/findPassword.vm";

	}

	@RequestMapping(value = "/findPassword.htm", method = RequestMethod.POST)
	public String doPOST(@Valid FindPasswordForm form, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "ums/password/findPassword.vm";
		}
		User u = userMapper.selectByEmail(form.getEmail());

		if (u!=null&&u.getUserId().equals(form.getUserName())) {
			User temp = new User();
			temp.setId(u.getId());
			temp.setCode(UUID.randomUUID().toString());

			sendMail(request, u.getUserId(), temp.getCode(), u.getEmail());
			userMapper.updateByPrimaryKeySelective(temp);
			modelMap.addAttribute("form", form);
			return "ums/password/findPasswordSuccess.vm";
		} else {
			result.rejectValue("userName", "user-mail-notmatch", "用户名和邮箱不匹配");
			return "ums/password/findPasswordError.vm";
		}

	}

	private void sendMail(HttpServletRequest request, String userName,
			String code, String mailTo) {
		String root = request.getServletContext().getRealPath("/");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		String url = getFullContextPath(request) + "/resetPassword.htm?code="
				+ code + "&u=" + userName;
		params.put("resetUrl", url);
		String content;
		try {
			content = VelocityHelper.renderVm(root, File.separator + "home"
					+ File.separator + "mailTemplate" + File.separator
					+ "findPassword.vm", params);
			mailService.sendMail("UMS365用户重置密码", content, mailTo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static String getFullContextPath(HttpServletRequest request) {
		// if (defaultBox == null) {
		String url = request.getRequestURL().toString();
		String path = request.getServletPath();
		return url.substring(0, url.indexOf(path));

	}

}
