package com.tiaotiaogift.ada.web.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/info.htm")
public class InfoController {

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("d");
        //Doc d = DocLoader.getDocs().get(id);
        //        if (d != null) {
        //            modelMap.addAttribute("contents", d.getContents());
        //        }
        return "info.vm";

    }

}
