package com.foodoon.info.web.home.user;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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

import com.foodoon.info.biz.common.email.MailService;
import com.foodoon.info.biz.common.email.VelocityHelper;
import com.foodoon.info.common.dal.UserMapper;
import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.dataobject.UserExample;
import com.foodoon.info.web.home.user.form.FindPassForm;

@Controller
public class FindPassController {

    @Autowired
    private UserMapper  userMapper;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/user/findPass.htm", method = RequestMethod.GET)
    public String doGet(FindPassForm form, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

        return "user/password/findPass.vm";

    }

    @RequestMapping(value = "/user/findPass.htm", method = RequestMethod.POST)
    public String doPOST(@Valid FindPassForm findPassForm, BindingResult result, HttpServletRequest request,
                         ModelMap modelMap) {
        if (result.hasErrors()) {
            return "user/password/findPass.vm";
        }
        UserExample uexa = new UserExample();
        uexa.createCriteria().andEmailEqualTo(findPassForm.getEmail()).andUserIdEqualTo(findPassForm.getUserName());
        List<User> users = userMapper.selectByExample(uexa);

        if (users.size() == 1) {
            User user = users.get(0);
            User temp = new User();
            temp.setId(user.getId());
            temp.setCode(UUID.randomUUID().toString());

            sendMail(request, user.getUserId(), temp.getCode(), user.getEmail());
            userMapper.updateByPrimaryKeySelective(temp);
            modelMap.addAttribute("form", findPassForm);
            return "user/password/findPassSuccess.vm";
        } else {
            result.rejectValue("userName", "user-mail-notmatch", "用户名和邮箱不匹配");
            return "user/password/findPassError.vm";
        }

    }

    private void sendMail(HttpServletRequest request, String userName, String code, String mailTo) {
        String root = request.getServletContext().getRealPath("/");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        String url = getFullContextPath(request) + "/user/resetPass.htm?code=" + code + "&u=" + userName;
        params.put("resetUrl", url);
        String content;
        try {
            content = VelocityHelper.renderVm(root, File.separator + "home" + File.separator + "mailTemplate"
                                                    + File.separator + "findPassword.vm", params);
            mailService.sendMail("农贸网用户重置密码", content, mailTo);
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
