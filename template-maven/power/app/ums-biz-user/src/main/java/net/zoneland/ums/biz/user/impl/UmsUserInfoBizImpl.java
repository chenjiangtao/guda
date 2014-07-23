/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsGroupMapper;
import net.zoneland.ums.common.dal.UmsGroupUserRelMapper;
import net.zoneland.ums.common.dal.UmsOrganizationMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.UmsUserRoleAppRelMapper;
import net.zoneland.ums.common.dal.UmsUserRoleRelMapper;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.dal.dataobject.UmsOrganization;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.dataobject.UmsUserRoleRel;
import net.zoneland.ums.common.util.MD5;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.helper.UUIDHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户业务类
 * 主要用于根据组织跟群组查询用户信息
 * @author wangyong
 * @version $Id: UmsUserInfoBiz.java, v 0.1 2012-8-11 下午4:33:16 wangyong Exp $
 */
public class UmsUserInfoBizImpl implements UmsUserInfoBiz {

    private static final Logger     logger = Logger.getLogger(UmsUserInfoBizImpl.class);

    @Autowired
    private UmsUserInfoMapper       umsUserInfoMapper;

    @Autowired
    private UmsGroupUserRelMapper   umsGroupUserRelMapper;

    @Autowired
    private UmsGroupMapper          umsGroupMapper;

    @Autowired
    private UmsOrganizationMapper   umsOrganizationMapper;

    @Autowired
    private UmsUserRoleRelMapper    umsRoleUserRelMapper;

    @Autowired
    private UmsUserRoleAppRelMapper umsUserRoleAppRelMapper;

    @Autowired
    private UmsContactMapper        umsContactMapper;

    /**
     * 根据组织查询用户
     *
     * @param orgId
     * @return
     */
    public List<UmsUserInfo> getUserByOrgId(String orgId) {
        List<UmsUserInfo> userList = new ArrayList<UmsUserInfo>();
        if (StringHelper.isNotEmpty(orgId)) {
            userList = umsUserInfoMapper.getUsersByOrgId(orgId);
        }
        return userList;
    }

    /**
     *  群组查询群组成员
     * 其中跟用户相关有两种：一种是ID还有一种手机号要分开处理。
     * @param userId
     * @param groupId
     * @return
     */
    public List<UmsUserInfo> getUsersByGroupId(String groupId) {
        List<UmsUserInfo> userInfoList = new ArrayList<UmsUserInfo>();
        if (StringHelper.isNotEmpty(groupId)) {
            List<UmsGroupUserRel> userList = umsGroupUserRelMapper.selectUserByGroupId(groupId);
            if (userList != null && userList.size() > 0) {
                //判断是ID还是手机号，如果是ID则查询用户表获取用户信息，如果是手机号则直接赋值
                for (int i = 0; i < userList.size(); i++) {
                    UmsUserInfo umsUserInfo = null;
                    //System.out.println("标识符长度" + userList.get(i).getUserDesc().length());
                    if (StringRegUtils.isPhoneNumber(userList.get(i).getUserDesc())) {
                        umsUserInfo = getUmsUserInfoByPhone(userList.get(i));
                        userInfoList.add(umsUserInfo);
                    } else {
                        //是ID的处理办法
                        umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(userList.get(i)
                            .getUserDesc());
                        umsUserInfo.setId(umsUserInfo.getId());
                        userInfoList.add(umsUserInfo);
                    }
                }
            }
        }
        return userInfoList;
    }

    /**
     * 通过名字或手机号搜索用户！
     *
     * @param name
     * @return
     */
    public List<ReceiveUser> getUsersByName(String name, String user) {
        List<UmsUserInfo> userInfos = new ArrayList<UmsUserInfo>();
        ReceiveUser receiveUser = null;
        if (!name.matches("^[0-9]+$") || (name.length() < 3 && name.matches("^[0-9]+$"))) {// 如果输入的不是匹配数字正则表达式或输入长度小于3位
            userInfos = null;// 说明不是按手机号匹配查询
        } else {
            // 根据所输入3位以上的手机号模糊查询出收件人姓名
            userInfos = umsUserInfoMapper.getUsersByRecvId(name);
            receiveUser = new ReceiveUser();
            receiveUser.setId(name);
            receiveUser.setName(name);
        }
        List<UmsUserInfo> users = umsUserInfoMapper.getUsersByUserName(name);
        List<UmsOrganization> orgs = null;
        List<UmsGroup> groups = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", SecurityContextHolder.getContext().getPrincipal().getUserId());
        map.put("userName", name);
        List<UmsContact> contacts = umsContactMapper.getContactsByUserName(map);
        if (!"user".equals(user)) {
            map.put("groupName", name);
            groups = umsGroupMapper.getGroupsByGroupName(map);
        }
        List<ReceiveUser> recvList = fillReceiveUser(groups, orgs, users, userInfos, contacts);
        if (receiveUser != null) {
            recvList.add(receiveUser);
        }
        return recvList;
    }

    /**
     *用于搜索的显示！
     *
     * @param groups
     * @param orgs
     * @param users
     * @param userInfos
     * @param grouprels
     * @return
     */
    private List<ReceiveUser> fillReceiveUser(List<UmsGroup> groups, List<UmsOrganization> orgs,
                                              List<UmsUserInfo> users, List<UmsUserInfo> userInfos,
                                              List<UmsContact> contacts) {
        List<ReceiveUser> usersList = new ArrayList<ReceiveUser>();
        if (groups != null && groups.size() > 0) {
            Iterator<UmsGroup> itGroup = groups.iterator();
            while (itGroup.hasNext()) {
                UmsGroup group = itGroup.next();
                ReceiveUser user = new ReceiveUser(group.getId() + "_group", group.getGroupName());
                usersList.add(user);
            }
        }
        //        Iterator<UmsOrganization> itOrg = orgs.iterator();
        //        while (itOrg.hasNext()) {
        //            UmsOrganization org = itOrg.next();
        //            ReceiveUser user = new ReceiveUser(org.getId() + "_org", org.getOrgName());
        //            usersList.add(user);
        //        }
        if (users != null && users.size() > 0) {
            Iterator<UmsUserInfo> itUser = users.iterator();
            while (itUser.hasNext()) {
                UmsUserInfo umsUser = itUser.next();
                UmsOrganization umsOrganization = umsOrganizationMapper.selectByPrimaryKey(umsUser
                    .getOrganizationId());
                ReceiveUser user = new ReceiveUser(umsUser.getId() + "_person",
                    umsUser.getUserName());
                String parentName = "";
                if (umsOrganization != null) {
                    if (StringUtils.isNotEmpty(umsOrganization.getOrgName())) {
                        if ("领导干部".equals(umsOrganization.getOrgName())) {
                            continue;
                        }
                    }
                    List<UmsOrganization> orgLists = new ArrayList<UmsOrganization>();
                    List<UmsOrganization> umsOrgs = getParentOrgs(umsOrganization, orgLists, 0);
                    if (umsOrgs != null && umsOrgs.size() > 0) {
                        parentName = umsOrgs.get(0).getOrgName();// 由于是数组集合，递归后放入集合的第一个元素是即是树中的第二级的组织名
                    }
                    if (StringUtils.isNotEmpty(parentName)
                        && StringUtils.isNotEmpty(umsOrganization.getOrgName())) {
                        user.setName(user.getName() + " " + parentName + "/"
                                     + umsOrganization.getOrgName());
                    } else if (StringUtils.isNotEmpty(umsOrganization.getOrgName())) {
                        user.setName(user.getName() + " " + umsOrganization.getOrgName());
                    } else {
                        user.setName(user.getName());
                    }
                } else {
                    user.setName(user.getName());
                }

                usersList.add(user);
            }
        }
        if (userInfos != null && userInfos.size() > 0) {
            Iterator<UmsUserInfo> itUserInfos = userInfos.iterator();
            while (itUserInfos.hasNext()) {
                UmsUserInfo umsUser = itUserInfos.next();
                UmsOrganization umsOrganization = umsOrganizationMapper.selectByPrimaryKey(umsUser
                    .getOrganizationId());
                ReceiveUser user = new ReceiveUser(umsUser.getId() + "_person",
                    umsUser.getUserName());
                String parentName = "";
                if (umsOrganization != null) {
                    if (StringUtils.isNotEmpty(umsOrganization.getOrgName())) {
                        if ("领导干部".equals(umsOrganization.getOrgName())) {
                            continue;
                        }
                    }
                    List<UmsOrganization> orgLists = new ArrayList<UmsOrganization>();
                    List<UmsOrganization> umsOrgs = getParentOrgs(umsOrganization, orgLists, 0);
                    if (umsOrgs != null && umsOrgs.size() > 0) {
                        parentName = umsOrgs.get(0).getOrgName();// 由于是数组集合，放入集合的第一个元素是即是树中的第二级的组织名
                    }
                    if (StringUtils.isNotEmpty(parentName)
                        && StringUtils.isNotEmpty(umsOrganization.getOrgName())) {
                        user.setName(user.getName() + " " + parentName + "/"
                                     + umsOrganization.getOrgName());
                    } else if (StringUtils.isNotEmpty(umsOrganization.getOrgName())) {
                        user.setName(user.getName() + " " + umsOrganization.getOrgName());
                    } else {
                        user.setName(user.getName());
                    }
                } else {
                    user.setName(user.getName());
                }
                usersList.add(user);
            }
        }

        //        Iterator<UmsGroupUserRel> itrels = grouprels.iterator();
        //        while (itrels.hasNext()) {
        //            UmsGroupUserRel grouprel = itrels.next();
        //            ReceiveUser user = new ReceiveUser(grouprel.getUserDesc(), grouprel.getComments());
        //            usersList.add(user);
        //        }
        if (contacts != null && contacts.size() > 0) {
            Iterator<UmsContact> itContacts = contacts.iterator();
            while (itContacts.hasNext()) {
                UmsContact contact = itContacts.next();
                if (StringUtils.isEmpty(contact.getUserName())) {
                    continue;
                }
                ReceiveUser user = new ReceiveUser(contact.getId() + "_contact",
                    contact.getUserName());
                usersList.add(user);
            }
        }
        return usersList;
    }

    /**
     *群组关联表里用户信息手机号的处理办法
     *
     * @param umsGroupUserRel
     * @return
     */
    public UmsUserInfo getUmsUserInfoByPhone(UmsGroupUserRel umsGroupUserRel) {
        UmsUserInfo umsUserInfo = new UmsUserInfo();
        //用户ID用手机号会造成不唯一，使用uuid_phone,后期解析注意
        String userId = umsGroupUserRel.getId() + "_" + umsGroupUserRel.getUserDesc();
        umsUserInfo.setId(userId);
        //如果有备注的话则把备注并且空格的话显示为用户名字
        if (umsGroupUserRel.getComments() != null
            && StringUtils.trim(umsGroupUserRel.getComments()).length() > 0) {
            umsUserInfo.setUserName(StringUtils.trim(umsGroupUserRel.getComments()));
        } else {
            //没有备注则把手机号作为名字
            umsUserInfo.setUserName(umsGroupUserRel.getUserDesc());
        }
        umsUserInfo.setPhone(umsGroupUserRel.getUserDesc());
        return umsUserInfo;
    }

    /**
     * @see net.zoneland.ums.biz.user.UmsUserInfoBiz#getUmsUserInfoById(java.lang.String)
     */
    public UmsUserInfo getUmsUserInfoById(String id) {
        return umsUserInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新用户信息
     * 因为在也面上显示的时候加了org所以要去掉
     * @see net.zoneland.ums.biz.user.UmsUserInfoBiz#updateUmsUserInfo(net.zoneland.ums.common.dal.dataobject.UmsUserInfo)
     */
    public int updateUmsUserInfo(UmsUserInfo umsUserInfo) {
        String orgId = UUIDHelper.getUUID(umsUserInfo.getOrganizationId());
        umsUserInfo.setOrganizationId(orgId);
        return umsUserInfoMapper.updateByPrimaryKeySelective(umsUserInfo);
    }

    /**
     * @see net.zoneland.ums.biz.user.UmsUserInfoBiz#getRoleByPhone(java.lang.String)
     */
    public List<String> getRoleByPhone(String phoneNum) {
        if (phoneNum == null) {
            return Collections.emptyList();
        }
        List<UmsUserInfo> list = umsUserInfoMapper.getUsersByPhone(phoneNum);
        List<String> roles = new ArrayList<String>();
        if (list.size() > 0) {
            String userId = list.get(0).getId();
            List<UmsUserRoleRel> listRole = umsRoleUserRelMapper.selectByUserId(userId);
            Iterator<UmsUserRoleRel> it = listRole.iterator();

            while (it.hasNext()) {
                UmsUserRoleRel rel = it.next();
                roles.add(rel.getRoleName());
            }
        }
        return roles;
    }

    /**
     * @see net.zoneland.ums.biz.user.UmsUserInfoBiz#isAdmin(java.lang.String)
     */
    public boolean isAdmin(String phoneNum) {
        if (phoneNum == null) {
            return false;
        }
        List<String> roles = getRoleByPhone(phoneNum);
        Iterator<String> it = roles.iterator();
        while (it.hasNext()) {
            if (RoleNameEnum.ADMIN.getValue().equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see net.zoneland.ums.biz.user.UmsUserInfoBiz#getRoleByUserId(java.lang.String)
     */
    public List<RoleInfo> getRoleByUserId(String userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        List<UmsUserRoleRel> listRole = umsRoleUserRelMapper.selectByUserId(userId);
        List<RoleInfo> roles = new ArrayList<RoleInfo>();

        Iterator<UmsUserRoleRel> it = listRole.iterator();

        while (it.hasNext()) {
            UmsUserRoleRel rel = it.next();
            if (RoleNameEnum.APP.getValue().equals(rel.getRoleName())) {
                List<String> appLists = umsUserRoleAppRelMapper.searchAllApp(rel.getId());
                roles.add(new RoleInfo(rel.getRoleName(), appLists));
            } else {
                roles.add(new RoleInfo(rel.getRoleName()));
            }
        }

        return roles;
    }

    public UmsUserInfo loginHandle(String userId, String password) {

        if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(userId)) {
            UmsUserInfo userInfo = umsUserInfoMapper.getUserByUserId(userId.toUpperCase());
            if (userInfo == null) {
                UmsUserInfo user = new UmsUserInfo();
                user.setUserId("isNotExistUserId");
                return user;
            }
            password = MD5.md5(password);
            if (password.equals(userInfo.getPassword())) {
                return userInfo;
            }

        }
        return null;
    }

    /**
     * 模糊匹配用户手机号查找用户
     *
     * @param recvId
     * @return
     */
    public List<UmsUserInfo> getUsersByRecvId(String recvId) {
        if (recvId == null) {
            return Collections.emptyList();
        }
        if (!StringUtils.trim(recvId).matches("^[0-9]+$")) {
            return Collections.emptyList();
        }
        return umsUserInfoMapper.getUsersByRecvId(recvId);
    }

    public void setUmsRoleUserRelMapper(UmsUserRoleRelMapper umsRoleUserRelMapper) {
        this.umsRoleUserRelMapper = umsRoleUserRelMapper;
    }

    public void setUmsUserRoleAppRelMapper(UmsUserRoleAppRelMapper umsUserRoleAppRelMapper) {
        this.umsUserRoleAppRelMapper = umsUserRoleAppRelMapper;
    }

    public UmsUserInfoMapper getUmsUserInfoMapper() {
        return umsUserInfoMapper;
    }

    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    public UmsGroupUserRelMapper getUmsGroupUserRelMapper() {
        return umsGroupUserRelMapper;
    }

    public void setUmsGroupUserRelMapper(UmsGroupUserRelMapper umsGroupUserRelMapper) {
        this.umsGroupUserRelMapper = umsGroupUserRelMapper;
    }

    public UmsGroupMapper getUmsGroupMapper() {
        return umsGroupMapper;
    }

    public void setUmsGroupMapper(UmsGroupMapper umsGroupMapper) {
        this.umsGroupMapper = umsGroupMapper;
    }

    public UmsOrganizationMapper getUmsOrganizationMapper() {
        return umsOrganizationMapper;
    }

    public void setUmsOrganizationMapper(UmsOrganizationMapper umsOrganizationMapper) {
        this.umsOrganizationMapper = umsOrganizationMapper;
    }

    //    public int updatePassword(String id, String password) {
    //        Map<String, String> map = new HashMap<String, String>();
    //        map.put("id", id);
    //        map.put("password", password);
    //        int update = umsUserInfoMapper.updatePassword(map);
    //        return update;
    //    }

    /**
     * @see net.zoneland.ums.biz.user.UmsUserInfoBiz#loginHandleNoMD5(java.lang.String, java.lang.String)
     */
    public UmsUserInfo loginHandleNoMD5(String userId, String password) {
        if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(userId)) {
            UmsUserInfo userInfo = umsUserInfoMapper.getUserByUserId(userId.toUpperCase());
            if (userInfo == null) {
                return null;
            }
            if (password.equals(userInfo.getPassword())) {
                return userInfo;
            }

        }
        return null;
    }

    /**
     * 获取父节点组织添加到集合中
     *
     * @param umsOrganization
     * @return
     */
    private List<UmsOrganization> getParentOrgs(UmsOrganization umsOrganization,
                                                List<UmsOrganization> orgLists, int i) {
        logger.info("组织搜索次数:" + i);
        if (i > 8) {
            return orgLists;
        }
        UmsOrganization parentOrg = new UmsOrganization();
        if (StringUtils.isNotEmpty(umsOrganization.getParentId())) {
            parentOrg = umsOrganizationMapper.selectByPrimaryKey(umsOrganization.getParentId());// 获取父节点
        }
        UmsOrganization grandParentOrg = new UmsOrganization();
        if (parentOrg != null) {
            grandParentOrg = umsOrganizationMapper.selectByPrimaryKey(parentOrg.getParentId());// 获取父节点的父节点
            if (grandParentOrg == null || !grandParentOrg.getParentId().equals("0")) {
                getParentOrgs(parentOrg, orgLists, ++i);
            }
            orgLists.add(parentOrg);
        }
        return orgLists;
    }
}
