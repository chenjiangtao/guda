package com.tiaotiaogift.web.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.DocMapper;
import com.tiaotiaogift.common.mysql.dataobject.Doc;

@Controller
@RequestMapping("/info.htm")
public class InfoController {

    @Autowired
    private DocMapper docMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("d");
        Doc d = docMapper.selectByPrimaryKey(id);
        if (d != null) {
            modelMap.addAttribute("contents", d.getContent());
        }
        return "info.vm";

    }

}
