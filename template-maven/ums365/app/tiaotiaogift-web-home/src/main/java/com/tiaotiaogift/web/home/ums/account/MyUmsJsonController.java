/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.account;

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

import com.tiaotiaogift.common.dal.AccountLogMapper;
import com.tiaotiaogift.common.mysql.dataobject.AccountLog;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.MySendController;
import com.tiaotiaogift.web.home.ums.form.BaseJsonForm;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

/**
 * 
 * @author gang
 * @version $Id: MyUmsJsonController.java, v 0.1 2013-5-1 上午9:54:20 gang Exp $
 */
@Controller
public class MyUmsJsonController extends BaseJsonController {

    @Autowired
    private AccountLogMapper accountLogMapper;

    @RequestMapping(value = "/ums/loadAccountLog.json")
    public void doGet(BaseJsonForm form, HttpServletRequest request, HttpServletResponse response,
                      ModelMap modelMap) {
        if (form.getPage() == null || form.getPage() < 1) {
            form.setPage(1);
        }
        List<AccountLog> list = new ArrayList<AccountLog>();
        int count = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", OperationContextHolder.getPrincipal().getUserId());
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
        list = accountLogMapper.selectByParam(params);
        count = accountLogMapper.selectCountByParam(params);
        UiVo v = new UiVo();
        v.setRows(list);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }
}
