/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user;

import java.util.List;

import net.zoneland.ums.biz.user.impl.ReceiveUser;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;

/**
 *
 * @author wangyong
 * @version $Id: UmsUserInfoBiz.java, v 0.1 2012-8-27 上午10:44:44 Administrator Exp $
 */
public interface UmsUserInfoBiz {

    /**
     * 处理登录
     * 如果登录成功返回用户信息，否则返回null
     * @param userId
     * @param password
     * @return
     */
    public UmsUserInfo loginHandle(String userId, String password);

    public UmsUserInfo loginHandleNoMD5(String userId, String password);

    public List<UmsUserInfo> getUserByOrgId(String orgId);

    /**
     *  群组查询群组成员
     * 其中跟用户相关有两种：一种是ID还有一种手机号要分开处理。
     * @param userId
     * @param groupId
     * @return
     */
    public List<UmsUserInfo> getUsersByGroupId(String groupId);

    /**
     * 通过名字搜索用户！
     *
     * @param name
     * @return
     */
    public List<ReceiveUser> getUsersByName(String name, String user);

    /**
     *群组关联表里用户信息手机号的处理办法
     *
     * @param umsGroupUserRel
     * @return
     */
    public UmsUserInfo getUmsUserInfoByPhone(UmsGroupUserRel umsGroupUserRel);

    /**
     *通过ID查找用户
     *
     * @param id
     * @return
     */
    public UmsUserInfo getUmsUserInfoById(String id);

    /**
     *更新用户信息
     *
     * @param umsUserInfo
     * @return
     */
    public int updateUmsUserInfo(UmsUserInfo umsUserInfo);

    /**
     * 根据手机号查找该用户的所有角色名
     * @param phoneNum
     * @return
     */
    public List<String> getRoleByPhone(String phoneNum);

    /**
     * 判断该手机号是否为管理员
     * @param phoneNum
     * @return
     */
    public boolean isAdmin(String phoneNum);

    /**
     * 根据用户ID查找角色信息
     * @param userId
     * @return
     */
    public List<RoleInfo> getRoleByUserId(String userId);

    //    public int updatePassword(String id, String password);

    /**
     * 模糊匹配用户手机号查找用户
     *
     * @param recvId
     * @return
     */
    public List<UmsUserInfo> getUsersByRecvId(String recvId);

}
