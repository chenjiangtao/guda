package com.foodoon.info.web.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.velocity.VelocityToolboxView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.ClassifyMapper;
import com.foodoon.info.common.dal.DetailMapper;
import com.foodoon.info.common.dal.ImgMapper;
import com.foodoon.info.common.dal.SubClassifyMapper;
import com.foodoon.info.common.dataobject.Classify;
import com.foodoon.info.common.dataobject.ClassifyExample;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;
import com.foodoon.info.common.dataobject.Img;
import com.foodoon.info.common.dataobject.ImgExample;
import com.foodoon.info.common.dataobject.SubClassify;
import com.foodoon.info.common.dataobject.SubClassifyExample;
import com.foodoon.info.web.home.form.ListSearchForm;
import com.foodoon.info.web.home.vo.DetailVo;

@Controller
@RequestMapping("/list.htm")
public class ListController {

    @Autowired
    private ClassifyMapper    classifyMapper;

    @Autowired
    private SubClassifyMapper subClassifyMapper;

    @Autowired
    private DetailMapper      detailMapper;

    @Autowired
    private ImgMapper         imgMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ListSearchForm form, HttpServletRequest request,
                        HttpServletResponse response, ModelMap modelMap) {

        if (!StringUtils.hasText(form.getId())) {
            try {
                response.sendRedirect("/index.htm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modelMap.addAttribute("menuId", form.getId());

        ClassifyExample exa = new ClassifyExample();
        exa.createCriteria().andShowIndexEqualTo(1);
        exa.setOrderByClause("show_order asc");
        List<Classify> classifyList = classifyMapper.selectByExample(exa);
        modelMap.put("classify", classifyList);
        Map<String, Object> params = new HashMap<String, Object>();
        SubClassifyExample subExa = new SubClassifyExample();
        subExa.createCriteria().andClassifyIdEqualTo(form.getId());
        List<SubClassify> subList = subClassifyMapper.selectByExample(subExa);
        modelMap.put("subClassify", subList);
        if (StringUtils.hasText(form.getSubId())) {
            params.put("subClassifyId", form.getSubId());
        } else {

            params.put("subClassifyIds", convert(subList));
        }
        params.put("cityId", fillNull(form.getCityId()));
        params.put("key", fillNull(form.getKey()));
        form.calPage();
        params.put("start", (form.getPage() - 1) * form.getRows());
        params.put("rows", form.getRows());

        List<DetailWithBLOBs> details = detailMapper.search(params);
        List<DetailVo> vo = new ArrayList<DetailVo>(details.size());
        for (DetailWithBLOBs d : details) {
            DetailVo v = new DetailVo();
            v.setDetail(d);
            ImgExample imgExa = new ImgExample();
            imgExa.createCriteria().andDetailIdEqualTo(d.getId());
            List<Img> imgs = imgMapper.selectByExample(imgExa);
            if (imgs.size() > 0) {
                v.setImgPath(VelocityToolboxView.getFullContextURL(request) + imgs.get(0).getPath());
            } else {
                v.setImgPath(VelocityToolboxView.getFullContextURL(request)
                             + "/static/images/logo-4.png");
            }
            vo.add(v);
        }
        modelMap.put("form", form);
        modelMap.put("details", vo);
        return "list.vm";

    }

    private String fillNull(String str) {
        if (StringUtils.hasText(str)) {
            return str.trim();
        }
        return null;
    }

    private List<String> convert(List<SubClassify> subList) {

        if (subList == null) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>(subList.size());
        Iterator<SubClassify> it = subList.iterator();
        while (it.hasNext()) {
            list.add(it.next().getId());
        }
        return list;
    }

}
