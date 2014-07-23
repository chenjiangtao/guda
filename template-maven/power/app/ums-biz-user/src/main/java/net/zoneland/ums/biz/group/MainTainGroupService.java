/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.group;

import java.io.IOException;
import java.util.List;

import net.zoneland.ums.common.dal.bo.UmsGroupBo;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.util.page.PageResult;

import org.springframework.web.multipart.MultipartFile;

/**
 *主要用于个人群组的维护
 * @author wangyong
 * @version $Id: MainTainGroupService.java, v 0.1 2012-8-27 下午11:49:17 Administrator Exp $
 */
public interface MainTainGroupService {

    UmsGroup getById(String id);

    PageResult<UmsGroup> searchMyGroupByPage(UmsGroupBo bo, int curPage);

    List<UmsGroupUserRel> getGroupUserRelByGroupId(String groupId);

    boolean existGroup(String groupName, String userId, String groupId);

    String saveGroup(String userId, String groupName, List<UmsGroupUserRel> groupUserRelList);

    void updateGroup(UmsGroup group, List<UmsGroupUserRel> groupUserRelList);

    void deleteGroup(String groupId);

    /**
     * 根据UmsGroupUserRel关联表的主键ID查询出关联信息
     *
     * @param id
     * @return
     */
    UmsGroupUserRel getGroupUserRelById(String id);

    /**
     * 添加群组成员
     *
     * @param groupId
     * @param groupUserRelList
     * @return
     */
    boolean saveGroupMembers(String groupId, List<UmsGroupUserRel> groupUserRelList);

    /**
     * 添加群组成员
     *
     * @param groupId
     * @param groupUserRelList
     * @return
     */
    boolean addGroupMembers(String groupId, List<UmsGroupUserRel> groupUserRelList);

    /**
     * 更新群组名称
     *
     * @param groupId
     * @param userId
     * @param groupName
     * @return
     */
    boolean updateGroupName(String groupId, String userId, String groupName);

    /**
     * 删除群组成员
     *
     * @param groupId
     * @param memberId
     */
    boolean deleteGroupMember(String groupId, String memberId);

    /**
     * 选择收件人页面“添加群组”
     *
     * @param userId
     * @param groupName
     * @return
     */
    UmsGroup saveGroup(String userId, String groupName);

    /**
     * 批量删除群组
     *
     * @param idStr
     * @return
     */
    boolean deleteGroupMember(String idStr);

    /**
     *导出用户群组.
     *
     * @param id
     * @param path
     */
    void exportExcel(String id, String path);

    /**
     *通过excel导入群组
     *
     * @param id
     * @param path
     */
    void importGroupExcel(String groupId, MultipartFile file, String type) throws IOException;

}
