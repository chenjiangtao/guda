package com.foodoon.info.web.home;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.CityMapper;
import com.foodoon.info.common.dal.ImgMapper;
import com.foodoon.info.common.dal.SubClassifyMapper;
import com.foodoon.info.common.dataobject.City;
import com.foodoon.info.common.dataobject.CityExample;
import com.foodoon.info.common.dataobject.Img;
import com.foodoon.info.common.dataobject.ImgExample;
import com.foodoon.info.common.dataobject.SubClassify;
import com.foodoon.info.common.dataobject.SubClassifyExample;
import com.foodoon.info.web.home.vo.Response;

@Controller
public class PublishAjaxController extends BaseAjaxController {

    @Autowired
    private SubClassifyMapper subClassifyMapper;

    @Autowired
    private CityMapper        cityMapper;

    @Autowired
    private ImgMapper         imgMapper;

    @RequestMapping(value = "/delImg.json", method = RequestMethod.POST)
    public void delImg(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String id = request.getParameter("id");
        String detailId = request.getParameter("detailId");
        Img img = imgMapper.selectByPrimaryKey(id);
        if (!StringUtils.hasText(detailId)) {
            detailId = (String) request.getSession().getAttribute(PublishController.PUB_TOKEN);
        }
        ImgExample exa = new ImgExample();
        exa.createCriteria().andDetailIdEqualTo(detailId).andIdEqualTo(id);
        int res = imgMapper.deleteByExample(exa);

        Response resp = new Response();
        if (res == 1) {
            if (img != null) {
                String path = UploadController.baseUploadDir + img.getPath();
                System.out.println(path);
                File f = new File(path);
                if (f.exists()) {
                    f.delete();
                }

            }
            resp.setSuccess(true);
        } else {
            resp.setSuccess(false);
        }
        super.ajaxReturn(resp, response);

    }

    @RequestMapping(value = "/loadSubClassify.json", method = RequestMethod.POST)
    public void showSubClassify(HttpServletRequest request, HttpServletResponse response,
                                ModelMap modelMap) {
        String classifyId = request.getParameter("classifyId");
        SubClassifyExample ce = new SubClassifyExample();
        ce.createCriteria().andClassifyIdEqualTo(classifyId);
        List<SubClassify> scs = subClassifyMapper.selectByExample(ce);
        super.ajaxReturn(scs, response);

    }

    @RequestMapping(value = "/loadCity.json", method = RequestMethod.POST)
    public void showCity(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String provinceId = request.getParameter("provinceId");
        CityExample ce = new CityExample();
        ce.createCriteria().andParentIdEqualTo(PublishController.getInt(provinceId));
        List<City> scs = cityMapper.selectByExample(ce);
        super.ajaxReturn(scs, response);

    }

}
