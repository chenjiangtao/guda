package com.foodoon.info.web.home;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.ClassifyMapper;
import com.foodoon.info.common.dal.SubClassifyMapper;
import com.foodoon.info.common.dataobject.Classify;
import com.foodoon.info.common.dataobject.ClassifyExample;
import com.foodoon.info.common.dataobject.SubClassify;
import com.foodoon.info.common.dataobject.SubClassifyExample;
import com.foodoon.info.web.home.vo.ClassifyVo;

@Controller
@RequestMapping("/index.htm")
public class IndexController {

    @Autowired
    private ClassifyMapper    classifyMapper;

    @Autowired
    private SubClassifyMapper subClassifyMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("menuId", 0);
        ClassifyExample exa = new ClassifyExample();
        exa.createCriteria().andShowIndexEqualTo(1);
        exa.setOrderByClause("show_order asc");
        List<Classify> classifyList = classifyMapper.selectByExample(exa);
        modelMap.put("classify", classifyList);
        Iterator<Classify> it = classifyList.iterator();
        List<ClassifyVo> list1 = new ArrayList<ClassifyVo>();
        List<ClassifyVo> list2 = new ArrayList<ClassifyVo>();
        List<ClassifyVo> list3 = new ArrayList<ClassifyVo>();
        List<ClassifyVo> list4 = new ArrayList<ClassifyVo>();
        List<ClassifyVo> list5 = new ArrayList<ClassifyVo>();
        List<ClassifyVo> list6 = new ArrayList<ClassifyVo>();
        while (it.hasNext()) {
            Classify classify = it.next();
            SubClassifyExample ce = new SubClassifyExample();
            ce.createCriteria().andClassifyIdEqualTo(classify.getId());
            List<SubClassify> scs = subClassifyMapper.selectByExample(ce);

            switch (classify.getColIndex()) {
                case 1:
                    list1.add(new ClassifyVo(classify, scs));
                    break;
                case 2:
                    list2.add(new ClassifyVo(classify, scs));
                    break;
                case 3:
                    list3.add(new ClassifyVo(classify, scs));
                    break;
                case 4:
                    list4.add(new ClassifyVo(classify, scs));
                    break;
                case 5:
                    list5.add(new ClassifyVo(classify, scs));
                    break;
                case 6:
                    list6.add(new ClassifyVo(classify, scs));
                    break;
            }

        }
        modelMap.put("list1", list1);
        modelMap.put("list2", list2);
        modelMap.put("list3", list3);
        modelMap.put("list4", list4);
        modelMap.put("list5", list5);
        modelMap.put("list6", list6);
        return "index.vm";

    }

}
