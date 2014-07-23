/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.user.UmsGroupBiz;
import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsGroupMapper;
import net.zoneland.ums.common.dal.UmsGroupUserRelMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.dataobject.ZTree;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.tree.JSTree;

import org.springframework.util.StringUtils;

/**
 * 群组业务类
 *查询用户的群组
 * @author wangyong
 * @version $Id: UmsGroupBiz.java, v 0.1 2012-8-11 下午8:52:23 wangyong Exp $
 */
public class UmsGroupBizImpl implements UmsGroupBiz {

    private UmsGroupMapper        umsGroupMapper;

    private UmsGroupUserRelMapper umsGroupUserRelMapper;

    private UmsUserInfoMapper     umsUserInfoMapper;

    private UmsContactMapper      umsContactMapper;

    /**
     * 根据用户查询群组并转为树对象
     *
     * @param userId
     * @return
     */
    public List<JSTree> getFirstGroupForSelector(String userId) {
        List<JSTree> jsTreeList = new ArrayList<JSTree>();
        if (StringHelper.isNotEmpty(userId)) {
            List<UmsGroup> groupList = umsGroupMapper.getGroupsByUserId(userId);
            UmsGroup umsGroup = null;
            if (groupList != null && groupList.size() > 0) {
                Collections.sort(groupList, new MyGroupcomparator());
                if (groupList != null && groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        umsGroup = groupList.get(i);
                        JSTree jsTree = getJSTree(umsGroup);
                        jsTreeList.add(jsTree);
                    }
                }
            }
        }
        return jsTreeList;
    }

    /**
     * 把群组转化成树对象
     *其中传到页面上的ID我们添加组织标识符_group
     * @param umsOrganization
     * @return
     */
    public JSTree getJSTree(UmsGroup umsGroup) {
        JSTree jsTree = new JSTree();
        Map<String, String> attr = new HashMap<String, String>();
        attr.put("treeid", umsGroup.getId() + "_group");
        attr.put("id", "tree" + umsGroup.getId() + "_group");
        jsTree.setData(umsGroup.getGroupName());
        jsTree.setAttr(attr);
        jsTree.setState("closed");
        return jsTree;
    }

    public UmsGroupMapper getUmsGroupMapper() {
        return umsGroupMapper;
    }

    public void setUmsGroupMapper(UmsGroupMapper umsGroupMapper) {
        this.umsGroupMapper = umsGroupMapper;
    }

    /**
     * Getter method for property <tt>umsGroupUserRelMapper</tt>.
     * 
     * @return property value of umsGroupUserRelMapper
     */
    public UmsGroupUserRelMapper getUmsGroupUserRelMapper() {
        return umsGroupUserRelMapper;
    }

    /**
     * Setter method for property <tt>umsGroupUserRelMapper</tt>.
     * 
     * @param umsGroupUserRelMapper value to be assigned to property umsGroupUserRelMapper
     */
    public void setUmsGroupUserRelMapper(UmsGroupUserRelMapper umsGroupUserRelMapper) {
        this.umsGroupUserRelMapper = umsGroupUserRelMapper;
    }

    /**
     * Getter method for property <tt>umsUserInfoMapper</tt>.
     * 
     * @return property value of umsUserInfoMapper
     */
    public UmsUserInfoMapper getUmsUserInfoMapper() {
        return umsUserInfoMapper;
    }

    /**
     * Setter method for property <tt>umsUserInfoMapper</tt>.
     * 
     * @param umsUserInfoMapper value to be assigned to property umsUserInfoMapper
     */
    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    public UmsContactMapper getUmsContactMapper() {
        return umsContactMapper;
    }

    public void setUmsContactMapper(UmsContactMapper umsContactMapper) {
        this.umsContactMapper = umsContactMapper;
    }

    /** 
     * 根据传入的群组的groupId获取其成员集合
     * 
     * @see net.zoneland.ums.biz.user.UmsGroupBiz#queryGroupUserRelByGroupId(java.lang.String)
     */
    public List<ZTree> queryGroupUserRelByGroupId(String groupId) {
        List<ZTree> zTrees = new ArrayList<ZTree>();
        if (StringUtils.hasText(groupId)) {
            if (groupId.equals("0")) {
                String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
                List<UmsGroup> groupList = umsGroupMapper.getGroupsByUserId(userId);
                UmsGroup umsGroup = null;
                if (groupList != null && groupList.size() > 0) {
                    Collections.sort(groupList, new MyGroupcomparator());
                    if (groupList != null && groupList.size() > 0) {
                        for (int i = 0; i < groupList.size(); i++) {
                            umsGroup = groupList.get(i);
                            ZTree zTree = getZTree(umsGroup);
                            zTrees.add(zTree);
                        }
                    }
                }
                return zTrees;
            }
            List<UmsGroupUserRel> umsGroupUserRels = umsGroupUserRelMapper
                .selectUserByGroupId(groupId);
            if (umsGroupUserRels != null && umsGroupUserRels.size() > 0) {
                Collections.sort(umsGroupUserRels, new MyGroupRelcomparator());
                for (UmsGroupUserRel umsGroupUserRel : umsGroupUserRels) {
                    ZTree zTree = getGroupRelZTree(umsGroupUserRel);
                    zTrees.add(zTree);
                }
            }
        }
        return zTrees;
    }

    /**
     * 获得群组成员关联表Ztree生成树
     * 
     * @param umsGroupUserRel
     * @return
     */
    private ZTree getGroupRelZTree(UmsGroupUserRel umsGroupUserRel) {
        if (umsGroupUserRel == null) {
            return null;
        }
        ZTree zTree = new ZTree();
        UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(umsGroupUserRel
            .getUserDesc());
        if (umsUserInfo != null) {
            zTree.setName(umsGroupUserRel.getComments() + "(" + umsUserInfo.getPhone() + ")");
        } else {
            UmsContact umsContact = umsContactMapper.selectByPrimaryKey(umsGroupUserRel
                .getUserDesc());
            if (umsContact != null) {
                zTree.setName(umsGroupUserRel.getComments() + "(" + umsContact.getPhone() + ")");
            } else if (StringHelper.isNum(umsGroupUserRel.getUserDesc())) {
                zTree.setName(umsGroupUserRel.getComments() + "(" + umsGroupUserRel.getUserDesc()
                              + ")");
            } else {
                zTree.setName(umsGroupUserRel.getComments());
            }
        }
        zTree.setId(umsGroupUserRel.getId());
        //zTree.setName(umsGroupUserRel.getComments());
        zTree.setpId(umsGroupUserRel.getGroupId());
        zTree.setOpen(false);
        zTree.setParent(false);
        return zTree;
    }

    /** 
     * 查询个人群组表信息，生成zTree树
     * 
     * @see net.zoneland.ums.biz.user.UmsGroupBiz#queryGroupForSelector()
     */
    public List<ZTree> queryGroupForSelector() {
        String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
        List<ZTree> zTreeList = new ArrayList<ZTree>();
        if (StringHelper.isNotEmpty(userId)) {
            List<UmsGroup> groupList = umsGroupMapper.getGroupsByUserId(userId);
            ZTree zTreeRoot = getRootZTree();
            zTreeList.add(zTreeRoot);
            if (groupList != null && groupList.size() > 0) {
                Collections.sort(groupList, new MyGroupcomparator());
                UmsGroup umsGroup = null;
                if (groupList != null && groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        umsGroup = groupList.get(i);
                        ZTree zTree = getZTree(umsGroup);
                        zTreeList.add(zTree);
                    }
                }
            }
        }
        return zTreeList;
    }

    /**
     * 获得群组树的根节点
     * 
     * @return
     */
    private ZTree getRootZTree() {
        ZTree zTree = new ZTree();
        zTree.setId("0");
        zTree.setName("所有群组");
        zTree.setpId(null);
        zTree.setOpen(true);
        zTree.setParent(true);
        return zTree;
    }

    /**
     * 获得群组Ztree生成树
     * 
     * @param umsGatewayGroup
     * @return
     */
    private ZTree getZTree(UmsGroup umsGroup) {
        if (umsGroup == null) {
            return null;
        }
        ZTree zTree = new ZTree();
        zTree.setId(umsGroup.getId());
        zTree.setName(umsGroup.getGroupName());
        zTree.setpId("0");
        zTree.setOpen(false);
        zTree.setParent(true);
        return zTree;
    }

    class MyGroupcomparator implements Comparator<UmsGroup> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsGroup o1, UmsGroup o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getGroupName() == null || o2.getGroupName() == null) {
                return 0;
            }
            return collator.compare(o1.getGroupName(), o2.getGroupName());
        }
    }

    class MyGroupRelcomparator implements Comparator<UmsGroupUserRel> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsGroupUserRel o1, UmsGroupUserRel o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getComments() == null || o2.getComments() == null) {
                return 0;
            }
            return collator.compare(o1.getComments(), o2.getComments());
        }
    }

}
