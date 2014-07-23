/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.group.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.zoneland.ums.biz.group.MainTainGroupService;
import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsGroupMapper;
import net.zoneland.ums.common.dal.UmsGroupUserRelMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.bo.UmsGroupBo;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.helper.MSExcelHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author wangyong
 * @version $Id: MainTainGroupServiceImpl.java, v 0.1 2012-8-27 下午11:50:49 Administrator Exp $
 */
public class MainTainGroupServiceImpl implements MainTainGroupService {

    private final Logger          logger = Logger.getLogger(MainTainGroupServiceImpl.class);
    @Autowired
    private UmsGroupUserRelMapper umsGroupUserRelMapper;
    @Autowired
    private UmsGroupMapper        umsGroupMapper;
    @Autowired
    private UmsUserInfoMapper     umsUserInfoMapper;
    @Autowired
    private UmsContactMapper      umsContactMapper;

    public UmsGroup getById(String id) {
        return umsGroupMapper.selectByPrimaryKey(id);
    }

    public PageResult<UmsGroup> searchMyGroupByPage(UmsGroupBo bo, int curPage) {
        if (curPage == 0) {
            curPage = 1;
        }
        int total = umsGroupMapper.searchAllNum(bo);
        PageResult<UmsGroup> result = new PageResult<UmsGroup>(total, curPage);
        PageSearch ps = new PageSearch(bo, result.getFirstrecode(), result.getEndrecode());
        List<UmsGroup> list = umsGroupMapper.searchMyGroupByPage(ps);
        if (list != null && list.size() > 0) {
            for (UmsGroup group : list) {
                group.setMembers(getGroupMember(group.getId()));
            }
            result.setResults(list);
        }
        return result;
    }

    public List<UmsGroupUserRel> getGroupUserRelByGroupId(String groupId) {
        return umsGroupUserRelMapper.selectUserByGroupId(groupId);
    }

    private String getGroupMember(String groupId) {
        List<UmsGroupUserRel> groupUserRelList = getGroupUserRelByGroupId(groupId);
        if (groupUserRelList != null && groupUserRelList.size() > 0) {
            StringBuffer memberBuf = new StringBuffer();
            for (int i = 0, len = groupUserRelList.size(); i < len; i++) {
                UmsGroupUserRel gourpUserRel = groupUserRelList.get(i);
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(gourpUserRel
                    .getUserDesc());
                UmsContact umsContact = umsContactMapper.selectByPrimaryKey(gourpUserRel
                    .getUserDesc());
                if (umsUserInfo == null && umsContact == null
                    && !StringRegUtils.isPhoneNumber(gourpUserRel.getUserDesc())) {
                    continue;
                }
                if (StringUtils.hasText(gourpUserRel.getComments())) {
                    memberBuf.append(gourpUserRel.getComments());
                    if (i < len - 1) {
                        memberBuf.append(",");
                    }
                }
            }
            if (memberBuf.length() > 0 && memberBuf.substring(memberBuf.length() - 1).equals(",")) {
                return memberBuf.substring(0, memberBuf.length() - 1).toString();
            }
            return memberBuf.toString();
        }
        return null;
    }

    private List<GroupUser> getGroupMembser(String groupId) {
        List<UmsGroupUserRel> groupUserRelList = getGroupUserRelByGroupId(groupId);
        List<GroupUser> list = new ArrayList<GroupUser>();
        if (groupUserRelList == null || groupUserRelList.size() == 0) {
            return list;
        }
        for (UmsGroupUserRel umsGroupUserRel : groupUserRelList) {
            GroupUser groupUser = new GroupUser();
            UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(umsGroupUserRel
                .getUserDesc());
            if (umsUserInfo != null) {
                groupUser.setId(umsGroupUserRel.getId());
                groupUser.setName(StringUtils.trimWhitespace(umsUserInfo.getUserName()));
                groupUser.setPhone(StringUtils.trimWhitespace(umsUserInfo.getPhone()));
                list.add(groupUser);
                continue;
            }
            UmsContact umsContact = umsContactMapper.selectByPrimaryKey(umsGroupUserRel
                .getUserDesc());
            if (umsContact != null) {
                groupUser.setId(umsGroupUserRel.getId());
                groupUser.setName(umsContact.getUserName());
                groupUser.setPhone(umsContact.getPhone());
                list.add(groupUser);
                continue;
            }
            groupUser.setId(umsGroupUserRel.getId());
            groupUser.setName(umsGroupUserRel.getComments());
            groupUser.setPhone(umsGroupUserRel.getUserDesc());
            list.add(groupUser);
        }
        return list;
    }

    public boolean existGroup(String groupName, String userId, String groupId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupName", groupName);
        map.put("userId", userId);
        try {
            UmsGroup umsGroup = umsGroupMapper.findGroupByNameAndUserId(map);
            if (umsGroup == null) {
                return false;
            } else if (umsGroup.getId().equals(groupId)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("查询群组时数据库出现错误！", e);
            return true;
        }

    }

    public String saveGroup(String userId, String groupName, List<UmsGroupUserRel> groupUserRelList) {
        String groupId = UUID.randomUUID().toString();
        UmsGroup group = new UmsGroup();
        group.setId(groupId);
        group.setGroupName(groupName);
        group.setUserId(userId);
        group.setGmtCreated(new Date());
        umsGroupMapper.insert(group);
        //保存成员
        saveGroupMembers(groupId, groupUserRelList);
        return group.getId();
    }

    /**
     * 选择收件人页面点击“添加群组”
     *
     * @param userId
     * @param groupName
     * @return
     */
    public UmsGroup saveGroup(String userId, String groupName) {
        String groupId = UUID.randomUUID().toString();
        UmsGroup group = new UmsGroup();
        group.setId(groupId);
        group.setGroupName(groupName);
        group.setUserId(userId);
        group.setGmtCreated(new Date());
        int res = umsGroupMapper.insert(group);
        if (res != 1) {// 没插入成功
            group = null;
        }
        return group;
    }

    public boolean saveGroupMembers(String groupId, List<UmsGroupUserRel> groupUserRelList) {
        if (groupUserRelList != null && groupUserRelList.size() > 0) {
            for (UmsGroupUserRel umsGroupUserRel : groupUserRelList) {
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(umsGroupUserRel
                    .getUserDesc());
                if (umsUserInfo != null) {
                    umsGroupUserRel.setComments(umsUserInfo.getUserName());
                }
            }
            for (int i = 0, len = groupUserRelList.size(); i < len; i++) {
                UmsGroupUserRel groupUserRel = groupUserRelList.get(i);
                groupUserRel.setId(UUID.randomUUID().toString());
                groupUserRel.setGroupId(groupId);
                groupUserRel.setGmtCreated(new Date());
                int res = umsGroupUserRelMapper.insert(groupUserRel);
                if (res != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void updateGroup(UmsGroup group, List<UmsGroupUserRel> groupUserRelList) {
        umsGroupMapper.updateByPrimaryKeySelective(group);
        //删除成员
        deleteGroupMembers(group.getId());
        //保存成员
        saveGroupMembers(group.getId(), groupUserRelList);
    }

    private void deleteGroupMembers(String groupId) {
        umsGroupUserRelMapper.deleteByGroupId(groupId);
    }

    public void deleteGroup(String groupId) {
        deleteGroupMembers(groupId);
        umsGroupMapper.deleteByPrimaryKey(groupId);
    }

    public UmsGroupUserRelMapper getUmsGroupUserRelMapper() {
        return umsGroupUserRelMapper;
    }

    public void setUmsGroupUserRelMapper(UmsGroupUserRelMapper umsGroupUserRelMapper) {
        this.umsGroupUserRelMapper = umsGroupUserRelMapper;
    }

    /**
     * 根据UmsGroupUserRel关联表的主键ID查询出关联信息
     *
     * @see net.zoneland.ums.biz.group.MainTainGroupService#getGroupUserRelById(java.lang.String)
     */
    public UmsGroupUserRel getGroupUserRelById(String id) {
        return umsGroupUserRelMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加群组成员
     *
     * @see net.zoneland.ums.biz.group.MainTainGroupService#addGroupMembers(java.lang.String, java.util.List)
     */
    public boolean addGroupMembers(String groupId, List<UmsGroupUserRel> groupUserRelList) {
        List<UmsGroupUserRel> groupMembersList = getGroupUserRelByGroupId(groupId);
        if (groupUserRelList != null && groupUserRelList.size() > 0) {
            for (UmsGroupUserRel umsGroupUserRel : groupUserRelList) {
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(umsGroupUserRel
                    .getUserDesc());
                if (umsUserInfo != null) {
                    if (StringUtils.hasText(umsUserInfo.getUserName())) {
                        umsGroupUserRel.setComments(umsUserInfo.getUserName());
                    } else {
                        umsGroupUserRel.setComments(umsUserInfo.getPhone());
                    }
                } else {
                    UmsContact umsContact = umsContactMapper.selectByPrimaryKey(umsGroupUserRel
                        .getUserDesc());
                    if (umsContact != null) {
                        if (StringUtils.hasText(umsContact.getUserName())) {
                            umsGroupUserRel.setComments(umsContact.getUserName());
                        } else {
                            umsGroupUserRel.setComments(umsContact.getPhone());
                        }
                    }
                }
            }
        }
        groupMembersList.addAll(groupUserRelList);
        Set<UmsGroupUserRel> insertUserRelList = new HashSet<UmsGroupUserRel>();// 用hashset并重写UmsGroupUserRel类的hashcode和equals方法去重
        insertUserRelList.addAll(groupMembersList);
        if (insertUserRelList != null && insertUserRelList.size() > 0) {
            umsGroupUserRelMapper.deleteByGroupId(groupId);
            Iterator<UmsGroupUserRel> it = insertUserRelList.iterator();
            while (it.hasNext()) {
                UmsGroupUserRel groupUserRel = it.next();
                groupUserRel.setId(UUID.randomUUID().toString());
                groupUserRel.setGroupId(groupId);
                groupUserRel.setGmtCreated(new Date());
                int res = umsGroupUserRelMapper.insert(groupUserRel);
                if (res != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 更新群组名称
     *
     * @see net.zoneland.ums.biz.group.MainTainGroupService#updateGroupName(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean updateGroupName(String groupId, String userId, String groupName) {
        UmsGroup umsGroup = new UmsGroup();
        umsGroup.setGroupName(groupName);
        umsGroup.setId(groupId);
        umsGroup.setUserId(userId);
        int res = umsGroupMapper.updateByPrimaryKeySelective(umsGroup);
        if (res != 1) {
            return false;
        }
        return true;
    }

    /**
     * 删除群组成员
     *
     * @see net.zoneland.ums.biz.group.MainTainGroupService#deleteGroupMember(java.lang.String, java.lang.String)
     */
    public boolean deleteGroupMember(String groupId, String memberId) {
        int res = umsGroupUserRelMapper.deleteByPrimaryKey(memberId);
        if (res != 1) {
            return false;
        }
        return true;
    }

    /**
     * 批量删除群组
     *
     * @see net.zoneland.ums.biz.group.MainTainGroupService#deleteGroupMember(java.lang.String)
     */
    public boolean deleteGroupMember(String idStr) {
        if (!StringUtils.hasText(idStr)) {
            return false;
        }
        String[] ids = idStr.split(",");
        for (String id : ids) {
            int res = umsGroupMapper.deleteByPrimaryKey(id);// 如果删除群组成功，说明该ID是群组ID
            if (res == 1) {
                umsGroupUserRelMapper.deleteByGroupId(id);//删除该群组下的所有成员
            } else {
                deleteGroupMember(null, id);//否则说明是成员ID，删除该成员
            }
        }
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.group.MainTainGroupService#exportExcel(java.lang.String, java.lang.String)
     */
    public void exportExcel(String id, String path) {
        List<GroupUser> list = getGroupMembser(id);
        packageExcelGroupUser(path, list);
    }

    /**
     *导出群组成员，姓名跟手机号.
     *
     * @param path
     * @param list
     */
    private void packageExcelGroupUser(String path, List<GroupUser> list) {
        List<List<Object>> sheet1 = new ArrayList<List<Object>>();
        List<Object> totallist = new ArrayList<Object>();
        totallist.add("成员姓名");
        totallist.add("手机号");
        sheet1.add(totallist);
        if (list != null && list.size() > 0) {
            Iterator<GroupUser> it = list.iterator();
            while (it.hasNext()) {
                GroupUser groupUser = it.next();
                List<Object> rowlist = new ArrayList<Object>();
                rowlist.add(groupUser.getName() == null ? "" : groupUser.getName());// 成员姓名
                rowlist.add(groupUser.getPhone() == null ? "" : groupUser.getPhone());// 成员手机号
                sheet1.add(rowlist);
            }
        }
        // 输入到excel
        FileOutputStream fos = null;
        HSSFWorkbook demoWorkBook = new HSSFWorkbook();
        try {
            MSExcelHelper.writeSheetTextForxls(demoWorkBook, sheet1);
            fos = new FileOutputStream(path);
            demoWorkBook.write(fos);
        } catch (Exception e) {
            logger.error("群组成员导出表excel sheet出错：" + e);
        } finally {
            try {
                fos.close();
            } catch (Exception e2) {
                logger.error("关闭导出群组成员输出文件流FileOutputStream出错：" + e2);
            }
        }
    }

    /**
     * 导入群组功能.</br>
     * 1.先解析出姓名跟手机号.</br>
     *2.验证手机号.</br>
     *3.判断是否重复.</br>
     *4.覆盖与跳过的执行
     * @throws IOException
     * @see net.zoneland.ums.biz.group.MainTainGroupService#importGroupExcel(java.lang.String, java.lang.String)
     */
    public void importGroupExcel(String groupId, MultipartFile file, String type)
                                                                                 throws IOException {
        List<List<String>> list = Collections.emptyList();
        InputStream is = null;
        is = file.getInputStream();
        if (is == null) {
            return;
        }
        list = MSExcelHelper.readXlsText(is, 0, -1);
        List<GroupUser> groupUsers = new ArrayList<GroupUser>();
        groupUsers = getGroupMembser(groupId);
        if (list == null || list.size() < 2) {
            return;
        }
        for (int i = 1; i < list.size(); i++) {
            List<String> groupMember = list.get(i);
            if (groupMember == null || groupMember.size() == 0) {
                continue;
            }
            GroupUser groupUser = new GroupUser();
            //因为这里前面已经判断是否为空，所以后面可以直接使用.trim
            if (!StringUtils.hasText(groupMember.get(1))
                || !StringRegUtils.isPhoneNumber(groupMember.get(1).trim())) {
                continue;
            }
            //判断名字是否有，如果没有的话就使用手机号当做名字
            if (StringUtils.hasText(groupMember.get(0))) {
                groupUser.setName(StringUtils.trimWhitespace(groupMember.get(0)));
            } else {
                groupUser.setName(StringUtils.trimWhitespace(groupMember.get(1)));
            }
            groupUser.setPhone(groupMember.get(1));
            List<GroupUser> existGroupUsers = getExistGroupUser(groupUsers, groupUser);
            if (groupUsers.contains(groupUser)) {
                //覆盖的话，把跟原来手机号相同的成员用现在的信息替换.
                if ("cover".equals(type)) {
                    logger.info("执行覆盖");
                    for (GroupUser test : existGroupUsers) {
                        UmsGroupUserRel umsGroupUserRel = new UmsGroupUserRel();
                        umsGroupUserRel.setId(test.getId());
                        umsGroupUserRel.setComments(groupUser.getName());
                        umsGroupUserRel.setUserDesc(groupUser.getPhone());
                        umsGroupUserRel.setGmtCreated(new Date());
                        umsGroupUserRel.setGroupId(groupId);
                        logger.info("覆盖的成员：" + umsGroupUserRel);
                        umsGroupUserRelMapper.updateByPrimaryKey(umsGroupUserRel);
                    }
                }
                //跳过的话就是指本来群组就存在这个手机号,我们队新加的群组成员就不做操作.
                if ("pass".equals(type)) {
                    logger.info("执行跳过");
                    continue;
                }
            } else {
                //如果不包含的话就直接插入数据库
                UmsGroupUserRel umsGroupUserRel = new UmsGroupUserRel();
                umsGroupUserRel.setId(UUID.randomUUID().toString());
                umsGroupUserRel.setGroupId(groupId);
                umsGroupUserRel.setGmtCreated(new Date());
                umsGroupUserRel.setUserDesc(groupUser.getPhone());
                umsGroupUserRel.setComments(groupUser.getName());
                int result = umsGroupUserRelMapper.insert(umsGroupUserRel);
                if (result == 1) {
                    logger.info(groupUser);
                    groupUsers.add(groupUser);
                }
            }
        }
    }

    private List<GroupUser> getExistGroupUser(List<GroupUser> groupUsers, GroupUser groupUser) {
        List<GroupUser> list = new ArrayList<GroupUser>();
        if (groupUser == null || groupUsers == null) {
            return list;
        }
        for (GroupUser test : groupUsers) {
            if (groupUser.equals(test)) {
                list.add(test);
            }
        }
        return list;
    }
}
