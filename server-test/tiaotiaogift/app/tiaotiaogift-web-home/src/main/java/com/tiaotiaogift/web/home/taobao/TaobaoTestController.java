/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.NotifyMapper;
import com.tiaotiaogift.common.mysql.dataobject.Notify;
import com.tiaotiaogift.common.util.enums.NotifyEnum;
import com.tiaotiaogift.web.home.taobao.vo.NotifyVo;

/**
 * 
 * @author foodoon
 * @version $Id: TaobaoTestController.java, v 0.1 2013-8-1 上午7:59:59 foodoon Exp $
 */
@Controller
public class TaobaoTestController {

    @Autowired
    private NotifyMapper notifyMapper;

    @RequestMapping(value = "/taobao/taobaoTest.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Notify notify = notifyMapper.selectByUserId(OperationContextHolder.getPrincipal()
            .getUserId());
        String rule = null;
        if (notify != null) {
            rule = notify.getRule();
        }
        List<NotifyVo> tradeVo = new ArrayList<NotifyVo>();
        NotifyEnum[] es = NotifyEnum.values();
        for (int i = 0, len = es.length; i < len; ++i) {
            NotifyVo vo = new NotifyVo();
            vo.setDescribe(es[i].getDescribe());
            vo.setCode(es[i].getCode());
            vo.setName(es[i].getName());
            vo.setChecked(isCheck(es[i].getCode(), rule));
            tradeVo.add(vo);
        }
        modelMap.addAttribute("tradeVo", tradeVo);
        return "tb/taobaoCall.vm";

    }

    private boolean isCheck(String code, String rule) {
        if (code == null || rule == null) {
            return false;
        }
        code = code + ",";
        if (rule.indexOf(code) > -1) {
            return true;
        }
        return false;
    }
}
