/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.common.dal.bo.UmsBlackListBO;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 黑名单业务接口
 * @author XuFan
 * @version $Id: BlackList.java, v 0.1 Aug 22, 2012 11:27:45 AM XuFan Exp $
 */
public interface BlackListBiz {

    /**
     * 条件查询分页显示黑名单
     * 
     * @param userId
     * @param appId
     * @param curpage
     * @return
     */
    public PageResult<UmsBlackListBO> searchByPage(String userId, String appId, int curpage);

    /**
     * 通过手动输入手机号添加黑名单
     * 
     * @param userId
     * @param appId
     * @return
     */
    public int insertBlackListByNum(String userId, String appId);

    /**
     * 通过手动输入手机号添加黑名单
     * 
     * @param userIds
     * @param appId
     * @return
     */
    public String saveBlackList(String[] userIds, String appId);

    /**
     * 根据userid和appID检查黑名单是否已经存在
     * 
     * @param userId
     * @param appId
     * @return
     */
    public boolean isExist(String userId, String appId);

    /**
     * 根据id删除黑名单
     * 
     * @param id
     */
    public boolean deleteBlackList(String id);

    /**
     * 查找手机号是否在黑名单中
     * @param phoneNumber
     * @param appId
     * @return
     */
    public boolean isBlackList(String phoneNumber, String appId);

    /**
     * 删除黑名单
     * @param phoneNumber
     * @param appId
     * @return
     */
    public int deleteBlackList(String phoneNumber, String appId);

    /**
     * 通过AppId删除黑名单
     * 
     * @param appId
     * @return
     */
    public boolean deleteBlackListByAppId(String appId);

}
