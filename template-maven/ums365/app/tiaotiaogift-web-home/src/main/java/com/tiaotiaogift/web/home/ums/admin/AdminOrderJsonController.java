/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.common.dal.OrderMapper;
import com.tiaotiaogift.common.mysql.dataobject.Order;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.admin.form.OrderForm;
import com.tiaotiaogift.web.home.ums.vo.Response;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

/**
 * 
 * @author gang
 * @version $Id: AdminOrderJsonController.java, v 0.1 2013-4-30 下午10:01:28 gang Exp $
 */
@Controller
public class AdminOrderJsonController extends BaseJsonController {

    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping(value = "/ums/admin/loadOrder.json")
    public void doGet(Integer page, Integer rows, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {
        if (page == null || page < 1) {
            page = 1;
        }
        List<Order> list = new ArrayList<Order>();
        int count = 0;
        list = orderMapper.selectAll();
        count = orderMapper.selectAllCount();

        UiVo v = new UiVo();
        v.setRows(list);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }

    @RequestMapping(value = "/ums/admin/saveOrder.json")
    public void doSave(OrderForm orderForm, HttpServletRequest request,
                       HttpServletResponse response, ModelMap modelMap) {
        int row = 0;
        Order temp = new Order();
        temp.setAmount(orderForm.getAmount());
        temp.setStatus(orderForm.getStatus());
        temp.setSum(orderForm.getSum());
        temp.setUserId(orderForm.getUserId());
        temp.setGmtModify(new Date());
        if (StringUtils.hasText(orderForm.getId())) {
            temp.setId(orderForm.getId());
            row = orderMapper.updateByPrimaryKeySelective(temp);

        } else {

            temp.setId(UUID.randomUUID().toString());
            temp.setUserId(OperationContextHolder.getPrincipal().getUserId());
            temp.setGmtCreated(new Date());

            row = orderMapper.insert(temp);

        }

        Response res = new Response();
        if (row == 1) {
            res.setMsg("保存成功");
            res.setSuccess(true);
        } else {
            res.setMsg("保存失败");
        }
        super.ajaxReturn(res, response);
    }

}
