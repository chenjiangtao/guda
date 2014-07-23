package com.foodoon.info.web.home;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.CityMapper;
import com.foodoon.info.common.dal.ClassifyMapper;
import com.foodoon.info.common.dal.DetailMapper;
import com.foodoon.info.common.dal.ImgMapper;
import com.foodoon.info.common.dal.SubClassifyMapper;
import com.foodoon.info.common.dataobject.City;
import com.foodoon.info.common.dataobject.CityExample;
import com.foodoon.info.common.dataobject.Classify;
import com.foodoon.info.common.dataobject.ClassifyExample;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;
import com.foodoon.info.common.dataobject.Img;
import com.foodoon.info.common.dataobject.ImgExample;
import com.foodoon.info.common.dataobject.SubClassify;
import com.foodoon.info.common.dataobject.SubClassifyExample;
import com.foodoon.info.web.home.form.PublishForm;

@Controller
@RequestMapping("/prod/publish.htm")
public class PublishController {

    public static final String PUB_TOKEN = "PUB_TOKEN";

    @Autowired
    private ClassifyMapper     classifyMapper;

    @Autowired
    private SubClassifyMapper  subClassifyMapper;

    @Autowired
    private CityMapper         cityMapper;

    @Autowired
    private DetailMapper       detailMapper;

    @Autowired
    private ImgMapper          imgMapper;

    private void initMap(HttpServletRequest request, ModelMap modelMap) {
        List<Classify> classifyList = classifyMapper.selectByExample(new ClassifyExample());

        modelMap.put("classify", classifyList);
        CityExample exa = new CityExample();
        exa.createCriteria().andParentIdEqualTo(0);
        List<City> city = cityMapper.selectByExample(exa);

        modelMap.put("province", city);
        modelMap.put("_sessionid", request.getSession().getId());

    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(PublishForm publishForm, BindingResult bindResult,
                        HttpServletRequest request, ModelMap modelMap) {
        initMap(request, modelMap);
        if (StringUtils.hasText(publishForm.getId())) {
            DetailWithBLOBs detail = detailMapper.selectByPrimaryKey(publishForm.getId());
            convert2Form(publishForm, detail);
            if (publishForm.getSubClassifyId() != null) {
                SubClassifyExample ce = new SubClassifyExample();
                ce.createCriteria().andIdEqualTo(publishForm.getSubClassifyId());
                List<SubClassify> scs = subClassifyMapper.selectByExample(ce);
                modelMap.addAttribute("subClassify", scs);
                publishForm.setClassifyId(scs.get(0).getClassifyId());
            }
            if (publishForm.getCityId() != null) {
                Integer id = getInt(publishForm.getCityId());
                if (id != null) {
                    CityExample exa = new CityExample();
                    exa.createCriteria().andIdEqualTo(id);
                    List<City> city = cityMapper.selectByExample(exa);

                    modelMap.put("city", city);
                    publishForm.setProvinceId(String.valueOf(city.get(0).getParentId()));
                }
            }
            ImgExample exa = new ImgExample();
            exa.createCriteria().andDetailIdEqualTo(publishForm.getId());
            List<Img> lists = imgMapper.selectByExample(exa);
            modelMap.put("imgs", lists);
        } else {
            request.getSession(true).setAttribute(PUB_TOKEN, UUID.randomUUID().toString());
        }
        return "publish.vm";

    }

    private void convert2Form(PublishForm form, DetailWithBLOBs detail) {
        if (detail == null) {
            return;
        }
        form.setCityId(detail.getCityId());
        form.setSubClassifyId(detail.getSubClassifyId());
        form.setContactInfo(detail.getContactInfo());
        form.setContactUser(detail.getContactInfo());
        form.setContent(detail.getContent());
        form.setPrice(String.valueOf(detail.getPrice()));
        form.setTitle(detail.getTitle());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@Valid PublishForm publishForm, BindingResult bindResult,
                         HttpServletRequest request, ModelMap modelMap) {

        if (bindResult.hasErrors()) {
            if (publishForm.getClassifyId() != null) {
                SubClassifyExample ce = new SubClassifyExample();
                ce.createCriteria().andClassifyIdEqualTo(publishForm.getClassifyId());
                List<SubClassify> scs = subClassifyMapper.selectByExample(ce);
                modelMap.addAttribute("subClassify", scs);
            }
            if (publishForm.getProvinceId() != null) {
                Integer id = getInt(publishForm.getProvinceId());
                if (id != null) {
                    CityExample exa = new CityExample();
                    exa.createCriteria().andParentIdEqualTo(id);
                    List<City> city = cityMapper.selectByExample(exa);

                    modelMap.put("city", city);
                }
            }
            initMap(request, modelMap);

            ImgExample exa = new ImgExample();
            exa.createCriteria().andDetailIdEqualTo(
                String.valueOf(request.getSession().getAttribute(PUB_TOKEN)));
            List<Img> lists = imgMapper.selectByExample(exa);
            modelMap.put("imgs", lists);

            return "publish.vm";
        }
        int res = 0;
        String t = (String) request.getSession().getAttribute(PUB_TOKEN);
        if (StringUtils.hasText(publishForm.getId())) {
            DetailWithBLOBs detail = new DetailWithBLOBs();
            detail.setId(publishForm.getId());
            detail.setCityId(publishForm.getCityId());
            detail.setContactInfo(publishForm.getContactInfo());
            detail.setContactUser(publishForm.getContactUser());
            detail.setContent(publishForm.getContent());
            detail.setGmtCreated(new Date());
            detail.setGmtModify(new Date());
            detail.setPrice(Float.valueOf(publishForm.getPrice()));
            detail.setSubClassifyId(publishForm.getSubClassifyId());
            detail.setTitle(publishForm.getTitle());
            detail.setUserId(OperationContextHolder.getPrincipal().getUserId());
            res = detailMapper.updateByPrimaryKeySelective(detail);
            modelMap.put("tid", publishForm.getId());
        } else {
            DetailWithBLOBs detail = new DetailWithBLOBs();
            detail.setCityId(publishForm.getCityId());
            detail.setContactInfo(publishForm.getContactInfo());
            detail.setContactUser(publishForm.getContactUser());
            detail.setContent(publishForm.getContent());
            detail.setGmtCreated(new Date());
            detail.setGmtModify(new Date());
            detail.setId(t);
            detail.setPrice(Float.valueOf(publishForm.getPrice()));
            detail.setSubClassifyId(publishForm.getSubClassifyId());
            detail.setTitle(publishForm.getTitle());
            detail.setUserId(OperationContextHolder.getPrincipal().getUserId());
            res = detailMapper.insert(detail);
            modelMap.put("tid", t);
        }
        String tips = "";
        if (res > 0) {
            tips = "发布成功";
        } else {
            tips = "发布失败,服务器错误,请稍后再试。";
        }
        return "publishResult.vm";

    }

    public static Integer getInt(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return null;
    }

}
