/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.NotifyMapper;
import com.tiaotiaogift.common.mysql.dataobject.Notify;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.vo.Response;

/**
 * 
 * @author foodoon
 * @version $Id: TaobaoServiceController.java, v 0.1 2013-8-3 下午10:06:01 foodoon Exp $
 */
@Controller
public class TaobaoServiceController extends BaseJsonController {

    @Autowired
    private NotifyMapper notifyMapper;

    @RequestMapping(value = "taobao/openService.json", method = RequestMethod.POST)
    public void oper(String code, HttpServletRequest request, HttpServletResponse response,
                     ModelMap modelMap) {
        int res = 0;
        if (StringUtils.hasText(code)) {
            Notify notify = notifyMapper.selectByUserId(OperationContextHolder.getPrincipal()
                .getUserId());
            code = code + ",";

            if (notify == null) {
                notify = new Notify();
                notify.setId(UUID.randomUUID().toString());
                notify.setUserId(OperationContextHolder.getPrincipal().getUserId());
                notify.setRule(code);
                notify.setGmtCreated(new Date());
                notify.setGmtModify(new Date());
                res = notifyMapper.insert(notify);
                if (res == 1) {
                    refreshNotifyRule(notify.getRule());
                }
            } else {
                String old = notify.getRule();
                if (old == null) {
                    Notify temp = new Notify();
                    temp.setId(notify.getId());
                    temp.setRule(code);
                    temp.setGmtModify(new Date());
                    res = notifyMapper.updateByPrimaryKeySelective(temp);
                    if (res == 1) {
                        refreshNotifyRule(temp.getRule());
                    }
                } else {
                    int index = old.indexOf(code);
                    if (index > -1) {
                        String[] temp = old.split(code);
                        Notify n = new Notify();
                        n.setId(notify.getId());
                        if (temp.length == 2) {
                            n.setRule(temp[0] + temp[1]);
                        } else if (temp.length == 1) {
                            n.setRule(temp[0]);
                        } else if (temp.length == 0) {
                            n.setRule("");
                        } else {
                            throw new RuntimeException("数组长度错误" + code);
                        }
                        n.setGmtModify(new Date());
                        res = notifyMapper.updateByPrimaryKeySelective(n);
                        if (res == 1) {
                            refreshNotifyRule(n.getRule());
                        }
                    } else {
                        Notify temp = new Notify();
                        temp.setId(notify.getId());
                        temp.setRule(notify.getRule() + code);
                        temp.setGmtModify(new Date());
                        res = notifyMapper.updateByPrimaryKeySelective(temp);
                        if (res == 1) {
                            refreshNotifyRule(temp.getRule());
                        }
                    }
                }
            }
        }
        Response resp = new Response();
        if (res == 1) {
            resp.setSuccess(true);
        }
        super.ajaxReturn(resp, response);

    }

    private void refreshNotifyRule(String notifyRule) {
        OperationContextHolder.getPrincipal().setAttrMap(TaobaoCallController.NOTIFY_RULE,
            notifyRule);
    }
}
