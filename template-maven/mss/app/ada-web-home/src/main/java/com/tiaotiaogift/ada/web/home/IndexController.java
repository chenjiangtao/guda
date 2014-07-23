package com.tiaotiaogift.ada.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.ada.common.dal.NewsMapper;
import com.tiaotiaogift.ada.common.dal.ProdMapper;
import com.tiaotiaogift.ada.common.dal.dataobject.News;
import com.tiaotiaogift.ada.common.dal.dataobject.Prod;

@Controller
@RequestMapping("/index.htm")
public class IndexController {
    
    @Autowired
    private NewsMapper newsMapper;
    
    @Autowired
    private ProdMapper prodMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        List<News> news = newsMapper.selectNews();
        List<Prod> prod =  prodMapper.selectNew();
        modelMap.addAttribute("news",news);
        modelMap.addAttribute("prod",prod);
        return "index.vm";

    }

}
