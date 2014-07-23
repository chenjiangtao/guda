/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.msg.process.service.ResolveUserService;
import net.zoneland.ums.common.dal.UmsGroupUserRelMapper;
import net.zoneland.ums.common.dal.UmsOrganizationMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsOrganization;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.helper.UUIDHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author wangyong
 * @version $Id: ResolveUserImpl.java, v 0.1 2012-8-14 上午10:46:21 wangyong Exp $
 */
public class ResolveUserServiceImpl implements ResolveUserService {

    private static final Logger   logger     = Logger.getLogger(ResolveUserServiceImpl.class);

    public static final String    split_char = ",";

    @Autowired
    private UmsGroupUserRelMapper umsGroupUserRelMapper;

    @Autowired
    private UmsUserInfoMapper     umsUserInfoMapper;

    @Autowired
    private UmsOrganizationMapper umsOrganizationMapper;

    public ResolveUserServiceImpl() {

    }

    public Map<String, HashSet<String>> getRecvIdList(String recvId) {
        if (!StringUtils.hasText(recvId)) {
            return Collections.emptyMap();
        }
        Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
        HashSet<String> userIdList = new HashSet<String>();
        HashSet<String> errorList = new HashSet<String>();
        HashSet<String> errorPhoneList = new HashSet<String>();
        HashSet<String> errorUserNameList = new HashSet<String>();
        String[] usersArray = recvId.split(split_char);
        if (logger.isInfoEnabled()) {
            logger.info("接收方个数:" + usersArray.length);
        }
        for (int i = 0, len = usersArray.length; i < len; ++i) {
            String user = StringUtils.trimWhitespace(usersArray[i]);
            String userIdentifier = UUIDHelper.getIdentifier(user);
            String userId = UUIDHelper.getUUID(user);
            UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(userId);
            if (StringRegUtils.isPhoneNumber(user)) {
                if (logger.isInfoEnabled()) {
                    logger.info("解析手机号:" + user);
                }
                userIdList.add(user);
            } else if (umsUserInfo != null && !StringUtils.hasText(umsUserInfo.getPhone())) {// 手机号为空的集合显示用户名
                if (logger.isInfoEnabled()) {
                    logger.info("手机号为空的接收方:" + user);
                }
                errorUserNameList.add(umsUserInfo.getUserName());
            } else if (userIdentifier != null) {
                if (logger.isInfoEnabled()) {
                    logger.info("用戶or群组or组织or联系人:" + user);
                }
                if (userIdentifier.equalsIgnoreCase("person")) {
                    if (userId.length() < 37) {
                        getPersonUserIdList(userIdList, userId, errorList);
                    }
                } else if (userIdentifier.equalsIgnoreCase("group")) {
                    if (userId.length() < 37) {
                        getGroupUserIdList(userIdList, userId, errorList);
                    }
                } else if (userIdentifier.equalsIgnoreCase("org")) {
                    if (userId.length() < 37) {
                        getOrgUserIdList(userIdList, userId, errorList);
                    }
                } else if ((userIdentifier.equalsIgnoreCase("contact"))) {
                    if (userId.length() < 37) {
                        getContactIdList(userIdList, userId, errorList);
                    }
                }
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("错误的接收方:" + user);
                }
                if (StringUtils.hasText(userId) && userId.length() < 37) {
                    errorList.add(userId);
                }
                errorPhoneList.add(user);
            }
        }
        map.put("errorUserNameList", errorUserNameList);
        map.put("correctUserIdList", userIdList);
        map.put("errorUserIdList", errorList);
        map.put("errorPhoneList", errorPhoneList);
        return map;
    }

    /**
     * 解析标识符为联系人
     * 
     * @param userIdList
     * @param userId
     * @param errorList
     */
    private void getContactIdList(HashSet<String> userIdList, String userId,
                                  HashSet<String> errorList) {
        logger.info("解析个人联系人" + userId);
        int num = userId.lastIndexOf("_");
        if (num > 0) {
            String id = userId.substring(num + 1);
            if (id.length() < 37) {
                userIdList.add(id);
            }
        } else {
            if (userId.length() < 37) {
                userIdList.add(userId);
            }
        }
    }

    /**
     *解析标识符为个人的
     *得到解析正确的与错误的
     *
     * @param userIdList
     * @param userId
     * @param errorUserIdList
     */
    public void getPersonUserIdList(HashSet<String> userIdList, String userId,
                                    HashSet<String> errorUserIdList) {
        logger.info("解析个人用户" + userId);
        int num = userId.lastIndexOf("_");
        if (num > 0) {
            String id = userId.substring(num + 1);
            if (id.length() < 37) {
                userIdList.add(id);
            }
        } else {
            if (userId.length() < 37) {
                userIdList.add(userId);
            }
        }
    }

    /**
     *解析标识符为群组的</br>
     *得到正确的与错误的
     *
     * @param userIdList
     * @param userId
     * @param errorUserIdList
     */
    public void getGroupUserIdList(HashSet<String> userIdList, String userId,
                                   HashSet<String> errorUserIdList) {
        logger.info("解析群组用户" + userId);
        List<String> userDescList = umsGroupUserRelMapper.selectUserDescByGroupId(userId);
        if (userDescList == null || userDescList.size() == 0) {
            errorUserIdList.add(userId);
            if (logger.isInfoEnabled()) {
                logger.info("该群组下无成员存在！");
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("该群组下用户个数" + userDescList.size());
            }
            userIdList.addAll(userDescList);
        }
    }

    /**
     *解析标识符为组织的,
     *得到正确与错误的
     *
     *
     * @param userIdList
     * @param userId
     * @param errorUserIdList
     */

    public void getOrgUserIdList(HashSet<String> userIdList, String userId,
                                 HashSet<String> errorUserIdList) {
        logger.info("解析组织用户" + userId);
        List<String> userIdListOrg = umsUserInfoMapper.getUserIdByOrgId(userId);
        if (userIdListOrg == null || userIdListOrg.size() == 0) {
            errorUserIdList.add(userId);
            if (logger.isInfoEnabled()) {
                logger.info("该组织下无成员存在！");
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("组织下的成员个数" + userIdListOrg.size());
            }
            userIdList.addAll(userIdListOrg);
        }
        List<UmsOrganization> orgs = umsOrganizationMapper.selectByParentId(userId);
        Iterator<UmsOrganization> it = orgs.iterator();
        while (it.hasNext()) {
            UmsOrganization org = it.next();
            getOrgUserIdList(userIdList, org.getId(), errorUserIdList);
        }
    }

    public void setUmsGroupUserRelMapper(UmsGroupUserRelMapper umsGroupUserRelMapper) {
        this.umsGroupUserRelMapper = umsGroupUserRelMapper;
    }

    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    public void setUmsOrganizationMapper(UmsOrganizationMapper umsOrganizationMapper) {
        this.umsOrganizationMapper = umsOrganizationMapper;
    }

}
