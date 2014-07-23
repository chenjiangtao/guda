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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiaotiaogift.common.dal.AccountLogMapper;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.common.mysql.dataobject.AccountLog;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.mysql.dataobject.UserAccount;
import com.tiaotiaogift.common.util.enums.ActionEnum;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.admin.form.ChargeForm;
import com.tiaotiaogift.web.home.ums.admin.form.UserForm;
import com.tiaotiaogift.web.home.ums.vo.Response;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

/**
 * 
 * @author gang
 * @version $Id: AdminUserJsonController.java, v 0.1 2013-4-30 下午8:57:58 gang Exp $
 */
@Controller
public class AdminUserJsonController extends BaseJsonController {

    @Autowired
    private UserMapper       userMapper;

    @Autowired
    private AccountMapper    accountMapper;

    @Autowired
    private AccountLogMapper accountLogMapper;

    @RequestMapping(value = "/ums/admin/loadUser.json")
    public void doGet(Integer page, Integer rows, HttpServletRequest request,
                      HttpServletResponse response, ModelMap modelMap) {
        if (page == null || page < 1) {
            page = 1;
        }
        List<UserAccount> list = new ArrayList<UserAccount>();
        int count = 0;
        list = userMapper.selectAll();
        count = userMapper.selectAllCount();

        UiVo v = new UiVo();
        v.setRows(list);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }

    @RequestMapping(value = "/ums/admin/saveUser.json")
    public void doSave(UserForm userForm, HttpServletRequest request, HttpServletResponse response,
                       ModelMap modelMap) {
        int row = 0;
        User temp = new User();
        temp.setEmail(userForm.getEmail());
        temp.setGrade(userForm.getGrade());
        temp.setId(userForm.getId());
        temp.setPassword((userForm.getPassword()));
        temp.setPhone(userForm.getPhone());
        temp.setStatus(userForm.getStatus());
        temp.setUserId(userForm.getUserId());
        Account a = new Account();
        a.setBalance(userForm.getBalance());
        a.setBalanceLock(userForm.getBalanceLock());
        if (StringUtils.hasText(userForm.getId())) {
            row = userMapper.updateByPrimaryKeySelective(temp);
            Account account = accountMapper.selectByUserId(userForm.getId());
            if (account == null) {
                a.setUserId(userForm.getId());
                a.setId(UUID.randomUUID().toString());
                a.setGmtModify(new Date());
                accountMapper.insert(a);
            } else {
                a.setId(account.getId());
                accountMapper.updateByPrimaryKeySelective(a);
            }
        } else {
            String id = UUID.randomUUID().toString();
            temp.setId(id);
            temp.setGmtCreated(new Date());
            temp.setCode("");
            Integer linkId = userMapper.selectMaxLinkId();
            if (linkId == null) {
                temp.setLinkId(1);
            } else {
                temp.setLinkId(linkId + 1);
            }
            row = userMapper.insert(temp);
            a.setUserId(id);
            a.setId(UUID.randomUUID().toString());
            a.setGmtModify(new Date());
            accountMapper.insert(a);
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

    @RequestMapping(value = "/ums/admin/chargeUser.json")
    public void doCharge(ChargeForm chargeForm, HttpServletRequest request,
                         HttpServletResponse response, ModelMap modelMap) {

        Account a = accountMapper.selectByUserId(chargeForm.getUserId());
        if (a == null) {
            a = new Account();
            a.setId(UUID.randomUUID().toString());
            a.setUserId(chargeForm.getUserId());
            a.setBalance(0);
            a.setBalanceLock(0);
            a.setGmtModify(new Date());
            accountMapper.insert(a);
        }
        Response res = new Response();
        a.setBalance(a.getBalance() + chargeForm.getSum());
        int rows = accountMapper.updateByPrimaryKeySelective(a);
        if (rows == 1) {
            AccountLog log = new AccountLog();
            log.setId(UUID.randomUUID().toString());
            log.setAction(ActionEnum.charge.getDescription());
            log.setAmountAfter(a.getBalance());
            log.setAmountBefore(a.getBalance() - chargeForm.getSum());
            log.setSum(chargeForm.getSum());
            log.setUserId(chargeForm.getUserId());
            log.setGmtCreated(new Date());
            int r = accountLogMapper.insert(log);
            if (r == 1) {
                res.setMsg("充值成功");
                res.setSuccess(true);
            } else {
                res.setMsg("充值失败");
            }
            super.ajaxReturn(res, response);
            return;
        }
        res.setMsg("充值失败");
        super.ajaxReturn(res, response);
    }

    @RequestMapping(value = "/ums/admin/delUser.json")
    public void doDel(UserForm form, HttpServletRequest request, HttpServletResponse response,
                      ModelMap modelMap) {
        int row = 0;
        if (StringUtils.hasText(form.getId())) {
            String id = form.getId();
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; ++i) {
                row += userMapper.deleteByPrimaryKey(ids[i]);
            }
        }
        Response res = new Response();
        if (row > 0) {
            res.setMsg("删除" + row + "条记录成功");
            res.setSuccess(true);
        } else {
            res.setMsg("删除失败");
        }
        super.ajaxReturn(res, response);
    }
}
