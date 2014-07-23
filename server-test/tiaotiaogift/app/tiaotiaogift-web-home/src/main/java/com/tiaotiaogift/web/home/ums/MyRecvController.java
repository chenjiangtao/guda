/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.MsgInMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgIn;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.form.BaseJsonForm;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

/**
 * 
 * @author gang
 * @version $Id: MyRecvController.java, v 0.1 2012-12-9 下午10:29:18 gang Exp $
 */
@Controller
public class MyRecvController extends BaseJsonController {

    @Autowired
    private MsgInMapper msgInMapper;

    @RequestMapping(value = "/ums/recv.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {

        return "ums/recv.vm";
    }

    @RequestMapping(value = "/ums/recv.json")
    public void doLoad(BaseJsonForm form, HttpServletRequest request, HttpServletResponse response,
                       ModelMap modelMap) {
        if (form.getPage() == null || form.getPage() < 1) {
            form.setPage(1);
        }

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("start", (form.getPage() - 1) * form.getRows());
        params.put("rows", form.getRows());
        Date startTime = MySendController.getDate(form.getStartTime());
        Date endTime = MySendController.getDate(form.getEndTime());
        if (startTime != null && endTime != null) {
            if (startTime.after(endTime)) {
                Date t = startTime;
                startTime = endTime;
                endTime = t;
            }
            params.put("startTime", startTime);
            params.put("endTime", endTime);
        }
        List<MsgIn> list = new ArrayList<MsgIn>();
        int count = 0;
        if (OperationContextHolder.getPrincipal().getAttrVal(LoginController.ADMIN_ATTR) == null) {
            params.put("recvId", OperationContextHolder.getPrincipal().getUserId());
            list = msgInMapper.selectByParams(params);
            count = msgInMapper.selectCountByParams(params);
        } else {
            list = msgInMapper.selectByParams(params);
            count = msgInMapper.selectCountByParams(params);
        }

        UiVo v = new UiVo();
        v.setRows(list);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }

}
