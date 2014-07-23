/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.user.UmsOrganizationBiz;
import net.zoneland.ums.common.dal.UmsOrganizationMapper;
import net.zoneland.ums.common.dal.dataobject.UmsOrganization;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.tree.JSTree;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 组织业务类
 * 主要是为了实现树形结构的显示。
 * @author wangyong
 * @version $Id: UmsOrganizationBiz.java, v 0.1 2012-8-10 下午3:23:48 wangyong Exp $
 */
@Service
public class UmsOrganizationBizImpl implements UmsOrganizationBiz {

    private UmsOrganizationMapper umsOrganizationMapper;

    /**
     * 根据跟组织获取下级组织
     *
     * @param orgRootId
     * @return
     */
    public List<JSTree> queryFirstOrgForSelector(String orgId) {
        List<JSTree> jsTreeList = new ArrayList<JSTree>();
        //通过根节点获得根组织。
        if (StringHelper.isNotEmpty(orgId)) {
            List<UmsOrganization> orgList = umsOrganizationMapper.selectByParentId(orgId);
            UmsOrganization umsOrganization = null;
            if (orgList != null && orgList.size() > 0) {
                umsOrganization = orgList.get(0);
                JSTree jsTree = getJSTree(umsOrganization);
                jsTreeList.add(jsTree);
            }
        }
        //        String parentId = getParentId(orgId);
        //        if (!StringUtils.hasText(parentId)) {
        //            parentId = "0";
        //        }
        //        while (StringUtils.hasText(parentId)) {
        //            jsTreeList.addAll(queryChildrenOrgForSelector("org", parentId));
        //            parentId = getParentId(parentId);
        //        }
        return jsTreeList;
    }

    public List<String> getParentIdList(String orgId) {
        if (!StringUtils.hasText(orgId)) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>();
        list.add(orgId);
        UmsOrganization umsOrganization = umsOrganizationMapper.selectByPrimaryKey(orgId);
        if (umsOrganization != null) {
            list.add(umsOrganization.getParentId());
        }
        while (umsOrganization != null) {
            orgId = umsOrganization.getParentId();
            umsOrganization = umsOrganizationMapper.selectByPrimaryKey(orgId);
            if (umsOrganization != null) {
                list.add(umsOrganization.getParentId());
            }
        }
        return list;
    }

    /**
     *根据类型和组织ID获得下级组织。
     *这个类获得的组织是放在也面上第二个选框里，其条件是该组织下面是人员或者没有下级组织了
     * @param type
     * @param parentId
     * @return
     */
    public List<JSTree> queryChildrenOrgForSelector(String type, String parentId) {
        List<JSTree> jsTreeList = new ArrayList<JSTree>();
        if (StringHelper.isNotEmpty(parentId) && StringHelper.isNotEmpty(type)) {
            List<UmsOrganization> orgList = umsOrganizationMapper.selectByParentId(parentId);
            if (orgList != null && orgList.size() > 0) {
                for (int i = 0; i < orgList.size(); i++) {
                    UmsOrganization umsOrganization = orgList.get(i);
                    if (type.equalsIgnoreCase("person")
                        || (type.equalsIgnoreCase("org") && hasChildren(umsOrganization.getId()))) {
                        JSTree jsTree = getJSTree(umsOrganization);
                        jsTreeList.add(jsTree);
                    }
                }
            }
        }
        return jsTreeList;
    }

    /**
     * 把组织转化成树对象
     *其中传到页面上的ID我们添加组织标识符_org
     * @param umsOrganization
     * @return
     */
    public JSTree getJSTree(UmsOrganization umsOrganization) {
        JSTree jsTree = new JSTree();
        Map<String, String> attr = new HashMap<String, String>();
        attr.put("treeid", umsOrganization.getId() + "_org");
        attr.put("id", "tree" + umsOrganization.getId() + "_group");
        jsTree.setData(umsOrganization.getOrgName());
        jsTree.setAttr(attr);
        jsTree.setState("closed");
        return jsTree;
    }

    /**
     * 根据ID判断是否存在下级组织
     *
     * @param parentId
     * @return
     */
    public boolean hasChildren(String parentId) {
        boolean result = false;
        int childrenCount = umsOrganizationMapper.getChildrenCount(parentId);
        if (childrenCount > 0) {
            result = true;
        }
        return result;
    }

    /**
     *通过上级组织获得其下级组织
     *
     * @param parentId
     * @return
     */
    public List<UmsOrganization> getOrgListByParentId(String parentId) {
        List<UmsOrganization> orgList = umsOrganizationMapper.selectByParentId(parentId);
        return orgList;
    }

    public UmsOrganizationMapper getUmsOrganizationMapper() {
        return umsOrganizationMapper;
    }

    public void setUmsOrganizationMapper(UmsOrganizationMapper umsOrganizationMapper) {
        this.umsOrganizationMapper = umsOrganizationMapper;
    }

    /**
     * @see net.zoneland.ums.biz.user.UmsOrganizationBiz#getOgrNameById()
     */
    public String getOrgNameById(String id) {
        UmsOrganization org = umsOrganizationMapper.selectByPrimaryKey(id);
        if (org != null) {
            return org.getOrgName();
        }
        return null;
    }

}
