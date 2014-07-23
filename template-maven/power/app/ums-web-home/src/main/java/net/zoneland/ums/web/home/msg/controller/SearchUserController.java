/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.msg.controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.biz.user.impl.ReceiveUser;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author wangyong
 * @version $Id: SearchUserController.java, v 0.1 2012-8-19 上午10:33:19 wangyong Exp $
 */
@Controller
public class SearchUserController extends BaseController {

    @Autowired
    private UmsUserInfoBiz umsUserInfoBiz;

    @RequestMapping("/msg/searchUser.ajax")
    public void searchUser(HttpServletRequest request, HttpServletResponse response)
                                                                                    throws Exception {
        String q = request.getParameter("q").trim();
        String user = StringUtils.trim(request.getParameter("user"));
        // 选择收件人通过名字或手机号搜索用户
        UmsUserInfo umsUserInfo = new UmsUserInfo();
        List<UmsUserInfo> lists = new ArrayList<UmsUserInfo>();
        lists.add(umsUserInfo);
        List<ReceiveUser> rus = umsUserInfoBiz.getUsersByName(q, user);
        Collections.sort(rus, new MyReceiveUsercomparator());
        ajaxReturn(rus, response);
    }

    /**
     * 模糊匹配用户手机号查询用户
     *
     * @param request
     * @param response
     */
    @RequestMapping("/msg/searchUsers.ajax")
    public void searchUsers(HttpServletRequest request, HttpServletResponse response) {
        String recvId = request.getParameter("recvId");
        List<UmsUserInfo> list = umsUserInfoBiz.getUsersByRecvId(recvId);//模糊匹配用户手机号查找用户
        Collections.sort(list, new MyUserInfocomparator());
        ajaxReturn(list, response);
    }

    class MyReceiveUsercomparator implements Comparator<ReceiveUser> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(ReceiveUser o1, ReceiveUser o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getName() == null || o2.getName() == null) {
                return 0;
            }
            return collator.compare(o1.getName(), o2.getName());

        }
    }

    class MyUserInfocomparator implements Comparator<UmsUserInfo> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsUserInfo o1, UmsUserInfo o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getUserName() == null || o2.getUserName() == null) {
                return 0;
            }
            return collator.compare(o1.getUserName(), o2.getUserName());

        }
    }

}
