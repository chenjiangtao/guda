/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.user.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.user.UmsContactBiz;
import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsGroupMapper;
import net.zoneland.ums.common.dal.UmsGroupUserRelMapper;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.common.util.tree.JSTree;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author yangjuanying
 * @version $Id: UmsContactBizImpl.java, v 0.1 2013-2-1 下午10:02:12 yangjuanying Exp $
 */
public class UmsContactBizImpl implements UmsContactBiz {

    @Autowired
    private UmsContactMapper      umsContactMapper;

    @Autowired
    private UmsGroupMapper        umsGroupMapper;

    @Autowired
    private UmsGroupUserRelMapper umsGroupUserRelMapper;

    /** 
     * 按条件分页显示我的联系人信息
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#searchMyContactByPage(net.zoneland.ums.common.dal.dataobject.UmsContact, int)
     */
    public PageResult<UmsContact> searchMyContactByPage(UmsContact umsContact, int pageId) {
        int total = umsContactMapper.searchAllNum(umsContact);
        PageResult<UmsContact> result = new PageResult<UmsContact>(total, pageId);
        PageSearch ps = new PageSearch(umsContact, result.getFirstrecode(), result.getEndrecode());
        List<UmsContact> list = umsContactMapper.searchMyContactByPage(ps);
        if (list != null && list.size() > 0) {
            result.setResults(list);
        }
        return result;
    }

    /** 
     * 通过手机号和UserId查找联系人
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#searchByPhone(java.lang.String)
     */
    public UmsContact searchByPhone(String phone) {
        Map<String, String> contactMap = new HashMap<String, String>();
        String userId = OperationContextHolder.getPrincipal().getUserId();
        contactMap.put("phone", phone);
        contactMap.put("userId", userId);
        UmsContact umsContact = umsContactMapper.searchByUserIdAndPhone(contactMap);
        return umsContact;
    }

    /** 
     * 添加新的联系人
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#insertContact(net.zoneland.ums.common.dal.dataobject.UmsContact)
     */
    public boolean insertContact(UmsContact umsContact) {
        umsContact.setGmtCreated(new Date());
        umsContact.setId(UUID.randomUUID().toString());
        int res = umsContactMapper.insertSelective(umsContact);
        if (res != 1) {
            return false;
        }
        return true;
    }

    /** 
     * 修改联系人
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#updateContact(net.zoneland.ums.common.dal.dataobject.UmsContact)
     */
    public boolean updateContact(UmsContact umsContact) {
        if (umsContact == null) {
            return false;
        }
        umsContact.setGmtCreated(new Date());
        int res = umsContactMapper.updateByPrimaryKeySelective(umsContact);
        if (res != 1) {
            return false;
        }
        // 当更新我的联系人时，群组关联中的我的联系人信息也被更新
        List<UmsGroup> umsGroups = umsGroupMapper.getGroupsByUserId(SecurityContextHolder
            .getContext().getPrincipal().getUserId());
        List<String> groupIdList = new ArrayList<String>();
        for (UmsGroup umsGroup : umsGroups) {
            groupIdList.add(umsGroup.getId());
        }
        if (groupIdList != null && groupIdList.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupIdList", groupIdList);
            map.put("userDesc", umsContact.getId());
            map.put("comments", umsContact.getUserName());
            umsGroupUserRelMapper.updateByGroupIds(map);
        }
        return true;
    }

    /** 
     * 根据主键ID删除联系人
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#deleteContact(java.lang.String)
     */
    public boolean deleteContact(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        int res = umsContactMapper.deleteByPrimaryKey(id);
        if (res != 1) {
            return false;
        }
        // 当删除某联系人时，群组关联表中所添加的该联系人信息也要同时删除
        List<UmsGroup> umsGroups = umsGroupMapper.getGroupsByUserId(SecurityContextHolder
            .getContext().getPrincipal().getUserId());
        List<String> groupIdList = new ArrayList<String>();
        for (UmsGroup umsGroup : umsGroups) {
            groupIdList.add(umsGroup.getId());
        }
        if (groupIdList != null && groupIdList.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupIdList", groupIdList);
            map.put("userDesc", id);
            umsGroupUserRelMapper.deleteByContactId(map);
        }
        return true;
    }

    /** 
     * 获得用户的联系人，生成jstree
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#getFirstContactForSelector(java.lang.String)
     */
    public List<JSTree> getFirstContactForSelector(String userId) {
        List<JSTree> jsTreeList = new ArrayList<JSTree>();
        if (StringHelper.isNotEmpty(userId)) {
            List<UmsContact> contactList = umsContactMapper.searchByUserId(userId);
            UmsContact umsContact = null;
            if (contactList != null && contactList.size() > 0) {
                Collections.sort(contactList, new MyContactcomparator());
                if (contactList != null && contactList.size() > 0) {
                    for (int i = 0; i < contactList.size(); i++) {
                        umsContact = contactList.get(i);
                        JSTree jsTree = getJSTree(umsContact);
                        jsTreeList.add(jsTree);
                    }
                }
            }
        }
        return jsTreeList;
    }

    /**
     * 把联系人转换成jstree列表
     * 其中传到页面上的ID我们添加联系人标识符_contact
     * 
     * @param umsContact
     * @return
     */
    private JSTree getJSTree(UmsContact umsContact) {
        JSTree jsTree = new JSTree();
        Map<String, String> attr = new HashMap<String, String>();
        attr.put("treeid", umsContact.getId() + "_contact");
        attr.put("id", "tree" + umsContact.getId() + "_contact");
        if (StringUtils.isNotEmpty(umsContact.getUserName())) {
            jsTree.setData(umsContact.getUserName());
        } else {
            jsTree.setData(umsContact.getPhone());
        }
        jsTree.setAttr(attr);
        jsTree.setState("closed");
        return jsTree;
    }

    class MyContactcomparator implements Comparator<UmsContact> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsContact o1, UmsContact o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getUserName() == null || o2.getUserName() == null) {
                return 0;
            }
            return collator.compare(o1.getUserName(), o2.getUserName());
        }
    }

    /** 
     * 根据主键ID查出联系人信息
     * 
     * @see net.zoneland.ums.biz.user.UmsContactBiz#searchByPrimaryKey(java.lang.String)
     */
    public UmsContact searchByPrimaryKey(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        UmsContact umsContact = umsContactMapper.selectByPrimaryKey(id);
        return umsContact;
    }
}
