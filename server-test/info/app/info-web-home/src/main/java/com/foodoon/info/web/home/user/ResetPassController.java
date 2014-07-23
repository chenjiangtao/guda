package com.foodoon.info.web.home.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.dataobject.UserExample;
import com.foodoon.info.common.util.MD5;
import com.foodoon.info.web.home.user.form.ResetPassForm;

@Controller
public class ResetPassController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user/resetPass.htm", method = RequestMethod.GET)
    public String doGet(ResetPassForm form, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        if (form.getU() == null || form.getCode() == null) {
            return "user/password/resetPassError.vm";
        }
        UserExample uexa = new UserExample();
        uexa.createCriteria().andUserIdEqualTo(form.getU());
        List<User> users = userMapper.selectByExample(uexa);

        if (users.size() == 0) {
            return "user/password/resetPassError.vm";
        }
        User u = users.get(0);
        if (form.getCode().equals(u.getCode())) {
            modelMap.addAttribute("form", form);
            return "user/password/resetPass.vm";
        } else {
            return "user/password/resetPassError.vm";
        }

    }

    @RequestMapping(value = "/user/resetPass.htm", method = RequestMethod.POST)
    public String doPOST(@Valid ResetPassForm form, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            return "user/password/resetPass.vm";
        }
        if (!form.getPassword().equals(form.getPassword2())) {
            result.rejectValue("password2", "password2-notmatch", "两次输入的密码不一致");
            return "user/password/resetPass.vm";
        }
        UserExample uexa = new UserExample();
        uexa.createCriteria().andUserIdEqualTo(form.getU());
        List<User> users = userMapper.selectByExample(uexa);

        if (users.size() == 0) {
            return "user/password/resetPassError.vm";
        }
        User u = users.get(0);
        User temp = new User();
        temp.setId(u.getId());
        temp.setPassword(MD5.md5(form.getPassword()));
        temp.setCode("");
        userMapper.updateByPrimaryKeySelective(temp);
        return "user/password/resetPassSuccess.vm";

    }

}
