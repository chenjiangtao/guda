package com.foodoon.info.web.home.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.dataobject.UserExample;
import com.foodoon.info.common.util.enums.UserStatusEnum;
import com.foodoon.info.web.home.user.form.RegForm;

@Controller
public class RegValidController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user/reg/valid.htm", method = RequestMethod.GET)
    public String doGet(RegForm regForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        String userName = request.getParameter("u");
        String code = request.getParameter("code");
        if (userName != null && code != null) {
            UserExample uexa = new UserExample();
            uexa.createCriteria().andUserIdEqualTo(regForm.getUserName());
            List<User> users = userMapper.selectByExample(uexa);

            if (users.size() == 1 && code.equals(users.get(0).getCode())) {
                User newU = new User();
                newU.setId(users.get(0).getId());
                newU.setStatus(UserStatusEnum.OK.getValue());
                userMapper.updateByPrimaryKeySelective(newU);
                return "user/regSuccess.vm";
            }
        }
        String errorTips = "对不起，邮箱验证失败，用户尚未注册";
        modelMap.addAttribute("errorTips", errorTips);
        return "user/regFail.vm";

    }

}
