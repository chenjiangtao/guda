/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.DetailMapper;
import com.foodoon.info.common.dataobject.DetailExample;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;
import com.foodoon.info.web.home.BaseAjaxController;
import com.foodoon.info.web.home.vo.Response;
import com.foodoon.info.web.home.vo.UiVo;

/**
 * 
 * @author foodoon
 * @version $Id: MyPubJsonController.java, v 0.1 2013年10月19日 上午11:44:14 foodoon Exp $
 */
@Controller
public class MyPubJsonController extends BaseAjaxController {

    @Autowired
    private DetailMapper detailMapper;

    @RequestMapping(value = "/home/loadMyPub.json", method = RequestMethod.POST)
    public void doLoadPub(Integer page, Integer rows, HttpServletResponse response,
                          ModelMap modelMap) {
        if (page == null || page < 1) {
            page = 1;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", (page - 1) * rows);
        params.put("rows", rows);
        params.put("userId", OperationContextHolder.getPrincipal().getUserId());
        List<DetailWithBLOBs> details = detailMapper.search(params);
        int c = detailMapper.searchCount(params);

        UiVo v = new UiVo();
        v.setRows(details);
        v.setTotal(c);
        super.ajaxReturn(v, response);

    }

    @RequestMapping(value = "/home/delMyPub.json", method = RequestMethod.POST)
    public void doDel(String id, HttpServletResponse response, ModelMap modelMap) {

        int result = 0;
        String[] ids = id.split(",");
        DetailExample dexa = new DetailExample();
        for (int i = 0; i < ids.length; ++i) {
            dexa.clear();
            dexa.createCriteria().andIdEqualTo(ids[i])
                .andUserIdEqualTo(OperationContextHolder.getPrincipal().getUserId());
            result += detailMapper.deleteByExample(dexa);
        }
        Response res = new Response();
        if (result > 0) {
            res.setMsg("删除" + result + "条记录成功");
            res.setSuccess(true);
        } else {
            res.setMsg("删除失败");
        }
        super.ajaxReturn(res, response);

    }

}
