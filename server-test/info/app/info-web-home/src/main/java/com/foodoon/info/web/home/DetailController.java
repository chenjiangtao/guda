/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.velocity.VelocityToolboxView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.CityMapper;
import com.foodoon.info.common.dal.ClassifyMapper;
import com.foodoon.info.common.dal.DetailMapper;
import com.foodoon.info.common.dal.ImgMapper;
import com.foodoon.info.common.dal.SubClassifyMapper;
import com.foodoon.info.common.dataobject.City;
import com.foodoon.info.common.dataobject.Classify;
import com.foodoon.info.common.dataobject.ClassifyExample;
import com.foodoon.info.common.dataobject.DetailExample;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;
import com.foodoon.info.common.dataobject.Img;
import com.foodoon.info.common.dataobject.ImgExample;
import com.foodoon.info.common.dataobject.SubClassify;
import com.foodoon.info.common.dataobject.SubClassifyExample;

/**
 * @author foodoon
 * @version $Id: DetailController.java, v 0.1 2013年10月13日 下午4:36:35 foodoon Exp $
 */

@Controller
@RequestMapping("/prod/detail.htm")
public class DetailController {

    @Autowired
    private ClassifyMapper    classifyMapper;

    @Autowired
    private SubClassifyMapper subClassifyMapper;

    @Autowired
    private DetailMapper      detailMapper;

    @Autowired
    private CityMapper        cityMapper;

    @Autowired
    private ImgMapper         imgMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        String id = request.getParameter("id");

        DetailExample dexa = new DetailExample();
        dexa.createCriteria().andIdEqualTo(id);
        List<DetailWithBLOBs> detailList = detailMapper.selectByExampleWithBLOBs(dexa);
        if (detailList.size() != 1) {
            return "detailError.vm";
        }
        DetailWithBLOBs detail = detailList.get(0);
        modelMap.put("detail", detail);
        SubClassifyExample subexa = new SubClassifyExample();
        subexa.createCriteria().andIdEqualTo(detail.getSubClassifyId());
        List<SubClassify> SubClassifyList = subClassifyMapper.selectByExample(subexa);
        if (SubClassifyList.size() == 1) {
            SubClassify sub = SubClassifyList.get(0);

            modelMap.put("subClassify", sub);
            ClassifyExample cexa = new ClassifyExample();
            cexa.createCriteria().andIdEqualTo(sub.getClassifyId());
            List<Classify> classifyList = classifyMapper.selectByExample(cexa);
            modelMap.put("classify", classifyList.get(0));
        }
        City city = cityMapper.selectByPrimaryKey(Integer.parseInt(detail.getCityId()));
        modelMap.put("city", city);
        ImgExample imgExa = new ImgExample();
        imgExa.createCriteria().andDetailIdEqualTo(detail.getId());
        List<Img> imgs = imgMapper.selectByExample(imgExa);
        convert(request, imgs);
        modelMap.put("imgs", imgs);
        return "detail.vm";

    }

    private void convert(HttpServletRequest request, List<Img> imgs) {
        if (imgs == null || imgs.size() == 0) {
            return;
        }
        for (Img i : imgs) {
            i.setPath(VelocityToolboxView.getFullContextURL(request) + i.getPath());
        }
    }

}
