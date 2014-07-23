package com.tiaotiaogift.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.DocMapper;
import com.tiaotiaogift.common.mysql.dataobject.Doc;
import com.tiaotiaogift.web.home.ums.form.LoginForm;

@Controller
@RequestMapping("/u.htm")
public class IndexController {

    @Autowired
    private DocMapper docMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(LoginForm loginForm, BindingResult result,HttpServletRequest request, ModelMap modelMap) {

        List<Doc> knowlege = docMapper.selectByType("knowlege");//DocLoader.getTypes().get("knowlege");
        List<Doc> uses = docMapper.selectByType("uses");//DocLoader.getTypes().get("uses");
        List<Doc> news = docMapper.selectByType("news");//DocLoader.getTypes().get("news");

        modelMap.addAttribute("knowlege", knowlege);
        modelMap.addAttribute("news", news);
        modelMap.addAttribute("uses", uses);

        return "u.vm";

    }

}
