package com.tiaotiaogift.web.home.ums;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.enums.UserStatusEnum;
import com.tiaotiaogift.web.home.ums.form.RegForm;

@Controller
public class RegValidController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/reg/valid.htm", method = RequestMethod.GET)
	public String doGet(RegForm regForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		String userName = request.getParameter("u");
		String code = request.getParameter("code");
		if (userName != null && code != null) {
			User u = userMapper.selectByUserId(userName);
			if (u != null && code.equals(u.getCode())) {
				User newU = new User();
				newU.setId(u.getId());
				newU.setStatus(UserStatusEnum.OK.getValue());
				userMapper.updateByPrimaryKeySelective(newU);
				return "ums/regSuccess.vm";
			}
		}
		String errorTips = "对不起，邮箱验证失败，用户尚未注册";	
		modelMap.addAttribute("errorTips", errorTips);
		return "ums/regFail.vm";
		
	}

}
