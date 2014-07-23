/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.user.UmsContactBiz;
import net.zoneland.ums.biz.user.UmsGroupBiz;
import net.zoneland.ums.biz.user.UmsOrganizationBiz;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsOrganization;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.dataobject.ZTree;
import net.zoneland.ums.common.util.constants.LoginInfoConstants;
import net.zoneland.ums.common.util.helper.UUIDHelper;
import net.zoneland.ums.common.util.tree.JSTree;
import net.zoneland.ums.web.home.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *人员选择控制类
 *
 * @author wangyong
 * @version $Id: SelectorController.java, v 0.1 2012-8-9 下午3:11:53 wangyong Exp $
 */
@Controller
@RequestMapping("selector")
public class SelectorController extends BaseController {

    @Autowired
    private UmsOrganizationBiz umsOrganizationBiz;

    @Autowired
    private UmsUserInfoBiz     umsUserInfoBiz;

    @Autowired
    private UmsGroupBiz        umsGroupBiz;

    @Autowired
    private UmsContactBiz      umsContactBiz;

    private List<String>       parentIdList;

    @RequestMapping("selectorView.htm")
    public String selectorView() {
        return "/selector/selector.vm";
    }

    @RequestMapping("groupSelectorView.htm")
    public String groupSelectorView() {
        return "/selector/groupSelector.vm";
    }

    /**
     *获得一级组织
     *主要用于在第一个选框显示
     * @param request
     * @param response
     */
    @RequestMapping("getFirstOrg.ajax")
    public void getFirstOrg(HttpServletRequest request, HttpServletResponse response) {
        //最顶层组织的父组织的ID是0
        String orgId = SecurityContextHolder.getContext().getPrincipal()
            .getAttrVal(LoginInfoConstants.ORG_ID_ATTR).toString();
        List<JSTree> jsTreeList = umsOrganizationBiz.queryFirstOrgForSelector("0");
        parentIdList = umsOrganizationBiz.getParentIdList(orgId);
        ajaxReturn(jsTreeList, response);
    }

    /**
     *获得用户的群组
     *主要用于在第一个选框显示
     * @param request
     * @param response
     */
    @RequestMapping("getFirstGroup.ajax")
    public void getFirstGroup(HttpServletRequest request, HttpServletResponse response) {
        String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
        List<JSTree> jsTreeList = umsGroupBiz.getFirstGroupForSelector(userId);
        ajaxReturn(jsTreeList, response);
    }

    /**
     * 获得用户的联系人
     * 主要用于在第一个选框显示
     *
     * @param request
     * @param response
     */
    @RequestMapping("getFirstContact.ajax")
    public void getFirstContact(HttpServletRequest request, HttpServletResponse response) {
        String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
        List<JSTree> jsTreeList = umsContactBiz.getFirstContactForSelector(userId);
        ajaxReturn(jsTreeList, response);
    }

    @RequestMapping("getChildrenContact.ajax")
    public void getChildrenContact(HttpServletRequest request, HttpServletResponse response) {
        List<JSTree> jsTreeList = new ArrayList<JSTree>();
        ajaxReturn(jsTreeList, response);
    }

    /**
     *根据上级组织获得下级组织
     *
     * @param request
     * @param response
     */
    @RequestMapping("getChildrenOrg.ajax")
    public void getChildrenOrg(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("parentId");
        //解析页面传来的parentId；
        parentId = UUIDHelper.getUUID(parentId);
        String type = request.getParameter("type");
        List<JSTree> jsTreeList = umsOrganizationBiz.queryChildrenOrgForSelector(type, parentId);
        for (int i = 0; i < jsTreeList.size(); i++) {
            JSTree JsTree = jsTreeList.get(i);
            for (String parent : parentIdList) {
                if (JsTree.getAttr().containsValue(parent + "_org")) {
                    jsTreeList.set(i, jsTreeList.get(0));
                    jsTreeList.set(0, JsTree);
                }
            }
        }
        ajaxReturn(jsTreeList, response);
    }

    /**
     *获得在第二个框显示的组织列表
     *当选择部门的时候用于显示可选值
     * @param request
     * @param response
     */
    @RequestMapping("getOrgs.ajax")
    public void getOrgs(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("parentId");
        parentId = UUIDHelper.getUUID(parentId);
        List<UmsOrganization> orgList = umsOrganizationBiz.getOrgListByParentId(parentId);
        ajaxReturn(orgList, response);
    }

    /**
     *获得组织下面的用户用于显示在可选值
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getOrgUsers.ajax")
    public void getOrgUsers(HttpServletRequest request, HttpServletResponse response)
                                                                                     throws Exception {
        String orgId = request.getParameter("orgId");
        orgId = UUIDHelper.getUUID(orgId);
        List<UmsUserInfo> userlist = umsUserInfoBiz.getUserByOrgId(orgId);
        ajaxReturn(userlist, response);
    }

    /**
     * 获得联系人用于显示在可选值
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getContact.ajax")
    public void getContact(HttpServletRequest request, HttpServletResponse response)
                                                                                    throws Exception {
        String contactId = request.getParameter("contactId");
        contactId = UUIDHelper.getUUID(contactId);
        UmsContact umsContact = umsContactBiz.searchByPrimaryKey(contactId);
        ajaxReturn(umsContact, response);
    }

    /**
     * 显示群组下面的成员用于显示可选值
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getGroupUsers.ajax")
    public void getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String groupId = request.getParameter("groupId");
        //System.out.println(groupId);
        groupId = UUIDHelper.getUUID(groupId);
        //System.out.println(groupId);
        List<UmsUserInfo> userlist = umsUserInfoBiz.getUsersByGroupId(groupId);
        ajaxReturn(userlist, response);
    }

    @RequestMapping("getChildrenGroup.ajax")
    public void getChildrenGroup(HttpServletRequest request, HttpServletResponse response) {
        List<JSTree> jsTreeList = new ArrayList<JSTree>();
        ajaxReturn(jsTreeList, response);
    }

    @RequestMapping("/getFirstGroupZTree.ajax")
    public void getFirstGroupZTree(HttpServletRequest request, HttpServletResponse response) {
        List<ZTree> zTreeFirstList = umsGroupBiz.queryGroupForSelector();
        ajaxReturn(zTreeFirstList, response);
    }

    @RequestMapping("/getChildrenGroupZTree.ajax")
    public void getChildrenGroupZTree(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("id");
        // 解析页面传来的groupId
        List<ZTree> zTreeList = umsGroupBiz.queryGroupUserRelByGroupId(groupId);
        if (zTreeList != null && zTreeList.size() > 0) {
            ajaxReturn(zTreeList, response);
        }
    }

    /**
     * Getter method for property <tt>parentIdList</tt>.
     *
     * @return property value of parentIdList
     */
    public List<String> getParentIdList() {
        return parentIdList;
    }

    /**
     * Setter method for property <tt>parentIdList</tt>.
     *
     * @param parentIdList value to be assigned to property parentIdList
     */
    public void setParentIdList(List<String> parentIdList) {
        this.parentIdList = parentIdList;
    }

    //    class MyUserInfocomparator implements Comparator<UmsUserInfo> {
    //
    //        Collator collator = Collator.getInstance(java.util.Locale.CHINA);
    //
    //        public int compare(UmsUserInfo o1, UmsUserInfo o2) {
    //            if (o1 == null || o2 == null) {
    //                return 0;
    //            }
    //            if (o1.getUserName() == null || o2.getUserName() == null) {
    //                return 0;
    //            }
    //            return collator.compare(o1.getUserName(), o2.getUserName());
    //
    //        }
    //    }

}
