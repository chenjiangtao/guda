/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.RoleConfigBiz;
import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.biz.config.util.RoleConfigAssignment;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAreaMapper;
import net.zoneland.ums.common.dal.UmsOrganizationMapper;
import net.zoneland.ums.common.dal.UmsUserAreaRelMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.UmsUserRoleAppRelMapper;
import net.zoneland.ums.common.dal.UmsUserRoleRelMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.dataobject.UmsUserRoleAppRel;
import net.zoneland.ums.common.dal.dataobject.UmsUserRoleRel;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 管理员分配角色业务类
 * @author XuFan
 * @version $Id: UmsRoleBiz.java, v 0.1 Aug 15, 2012 10:01:24 AM XuFan Exp $
 */
@Service
public class RoleConfigBizImpl implements RoleConfigBiz {

    private final static Logger     logger = Logger.getLogger(RoleConfigBizImpl.class);

    @Autowired
    private UmsUserInfoMapper       umsUserInfoMapper;

    @Autowired
    private UmsOrganizationMapper   umsOrganizationMapper;

    @Autowired
    private UmsAppInfoMapper        umsAppInfoMapper;

    @Autowired
    private UmsUserRoleRelMapper    umsUserRoleRelMapper;

    @Autowired
    private UmsUserRoleAppRelMapper umsUserRoleAppRelMapper;

    @Autowired
    private UmsAreaMapper           umsAreaMapper;

    @Autowired
    private UmsUserAreaRelMapper    umsUserAreaRelMapper;

    /**
     * 权限分配显示所有用户
     *
     * @param umsUserInfo
     * @param curPage
     * @return
     */
    public PageResult<UmsUserInfo> showAllUser(UmsUserInfo umsUserInfo, int curPage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询全部用户");
        }
        int total = umsUserInfoMapper.getAllUserNum(umsUserInfo);
        umsUserInfo.setOrderBy("GMT_CREATED");// 按创建时间逆序排列
        PageResult<UmsUserInfo> result = new PageResult<UmsUserInfo>(total, curPage);
        PageSearch ps = new PageSearch(umsUserInfo, result.getFirstrecode(), result.getEndrecode());
        List<UmsUserInfo> list = umsUserInfoMapper.searchSelectiveByPage(ps);// 分页查询
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOrganizationId(
                StringUtils.trimWhitespace(umsOrganizationMapper.getOrgNameById(list.get(i)
                    .getOrganizationId())));// 将组织ID转换成组织名
            String roleName = RoleNameEnum.getDescriptionByValue(StringUtils
                .trimWhitespace(umsUserRoleRelMapper.getRoleNameByUserId(list.get(i).getId())));// 通过用户表主键Id获取角色名
            if (!StringUtils.hasText(roleName)) {// 如果获得角色名为空
                list.get(i).setEmployeeId("普通员工");// 设置角色名为普通员工
            } else {
                list.get(i).setEmployeeId(roleName);// 否则设置为该用户的角色名
            }
        }
        result.setResults(list);
        return result;
    }

    /**
     * 获得分页记录总数
     *
     * @param umsUserInfo
     * @return
     */
    public int getAllUserNum(UmsUserInfo umsUserInfo) {
        return umsUserInfoMapper.getAllUserNum(umsUserInfo);
    }

    /**
     * 当点击【保存】按钮时，给用户分配具体的角色
     *
     * @param userId 用户ID
     * @param appIdStr 应用集合
     * @param isAppRole 判断是否是应用管理员角色
     * @param orgAdmin 是否是部门管理员
     * @return
     */
    public AjaxResult giveRole(String userId, String appIdStr, String isAppRole, String orgAdmin,
                               String customOrgName) {
        if (logger.isInfoEnabled()) {
            logger.info("分配具体角色开始");
        }
        boolean updateOrgAdmin = false;// 是否更新了部门管理员字段
        AjaxResult result = new AjaxResult();
        UmsUserRoleRel umsUserRoleRel = new UmsUserRoleRel();
        List<UmsUserRoleAppRel> umsUserRoleAppRels = new ArrayList<UmsUserRoleAppRel>();
        String roleUserRelId = null;
        if (StringHelper.isEmpty(userId)) {// 判断用户ID是否为空
            // 用户ID为空的情况
            logger.debug("分配给用户【" + userId + "】的角色为空");
            result.setInfo("分配角色失败：没有获取到当前用户信息");
            result.setResult(false);
            return result;
        }
        if (StringHelper.isEmpty(appIdStr) && "roleApp".equals(isAppRole)) {// 判断是否是应用管理员并且所获得的应用集合是否为空
            // 应用集合为空的情况
            logger.debug("当前用户角色是系统管理员且获取应用为空，不能分配角色");
            result.setInfo("请在下方的【应用选择】中选择具体所需管理的应用,再点击保存按钮");
            result.setResult(false);
            return result;
        }
        // 无论是什么角色都要删除角色用户应用关系表再建立新的用户角色应用关系表
        roleUserRelId = umsUserRoleRelMapper.getRoleUserId(userId);// 通过user表主键id获取用户角色表主键id
        String roleName = umsUserRoleRelMapper.getRoleNameByUserId(userId);// 通过userId获取用户角色表的角色名
        UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(userId);// 通过userId判断是否是部门管理员
        String curOrgAdmin = user.getOrgAdmin();
        String curOrgName = user.getOrgName();
        umsUserRoleAppRelMapper.deleteByRoleUserRelId(roleUserRelId);// 根据角色用户关联ID删除角色用户关系表信息

        if (StringHelper.isEmpty(orgAdmin)) {// 如果部门管理员属性字段是空值
            orgAdmin = "0";// 把部门管理员属性字段设成0，表示该用户不是部门管理员
        }
        updateOrgAdmin = updateOrgAdmin(curOrgAdmin, orgAdmin, userId, customOrgName, curOrgName);// 更新部门管理员字段信息

        result = updateRoleAdmin(isAppRole, roleName, userId, roleUserRelId, umsUserRoleRel,
            orgAdmin, updateOrgAdmin);// 如果当前选中的是系统管理员角色，则更新系统管理员角色
        if (!StringHelper.isEmpty(result)) {// 如果更新了角色，回调函数不为空
            return result;// 返回所更新的角色
        }

        result = updateRoleNormal(isAppRole, roleName, userId, roleUserRelId, umsUserRoleRel,
            orgAdmin, updateOrgAdmin);// 如果当前选中的是普通员工角色，则更新普通员工角色
        if (!StringHelper.isEmpty(result)) {// 如果更新了角色，回调函数不为空
            return result;// 返回所更新的角色
        }

        result = updateRoleQuery(isAppRole, roleName, userId, roleUserRelId, umsUserRoleRel,
            orgAdmin, updateOrgAdmin);// 如果当前选中的是短信查询角色，则更新短信查询角色
        if (!StringHelper.isEmpty(result)) {// 如果更新了角色，回调函数不为空
            return result;// 返回所更新的角色
        }

        result = updateRoleApp(isAppRole, roleName, userId, roleUserRelId, umsUserRoleRel,
            orgAdmin, updateOrgAdmin, umsUserRoleAppRels, appIdStr);// 如果当前选中的是应用管理员角色，则更新应用管理员角色
        return result;// 返回所更新的角色
    }

    /**
     * 如果当前选中的是应用管理员角色，则更新应用管理员角色
     * 
     * @param isAppRole
     * @param roleName
     * @param userId
     * @param roleUserRelId
     * @param umsUserRoleRel
     * @param orgAdmin
     * @param updateOrgAdmin
     * @param umsUserRoleAppRels
     * @param appIdStr
     * @return
     */
    private AjaxResult updateRoleApp(String isAppRole, String roleName, String userId,
                                     String roleUserRelId, UmsUserRoleRel umsUserRoleRel,
                                     String orgAdmin, boolean updateOrgAdmin,
                                     List<UmsUserRoleAppRel> umsUserRoleAppRels, String appIdStr) {
        AjaxResult result = new AjaxResult();
        // 选中单选按钮判断出不是系统管理员也不是普通员工，那么当前所选则是应用管理员
        String[] appIds = appIdStr.split(",");// 截取应用集合
        umsUserRoleRel.setRoleName("ROLE_APP");// 更新用户角色表的角色名为应用管理员
        umsUserRoleRel.setGmtCreated(new Date());
        umsUserRoleRel.setUserId(userId);
        if (StringHelper.isEmpty(roleUserRelId)) {// 如果角色用户关联ID是空值
            roleUserRelId = UUID.randomUUID().toString();// 设置一个新的角色用户关联ID
            umsUserRoleRel.setId(roleUserRelId);
            umsUserRoleRelMapper.insert(umsUserRoleRel);// 插入新的用户角色
        } else {
            umsUserRoleRel.setId(roleUserRelId);
            umsUserRoleRelMapper.updateByPrimaryKey(umsUserRoleRel);// 更新用户角色关系表
        }
        UmsUserRoleAppRel umsUserRoleAppRel = new UmsUserRoleAppRel();// 重新创建用户角色应用关系表
        for (String appId : appIds) {
            umsUserRoleAppRel.setId(UUID.randomUUID().toString());
            umsUserRoleAppRel.setRoleUserRelId(roleUserRelId);
            umsUserRoleAppRel.setAppId(appId);// 将应用ID添加进用户角色应用关系表
            umsUserRoleAppRel.setGmtCreated(new Date());
            umsUserRoleAppRels.add(umsUserRoleAppRel);
            umsUserRoleAppRelMapper.insert(umsUserRoleAppRel);// 插入新的用户角色应用关系表    
        }
        if (!StringUtils.trimWhitespace(roleName).equals(RoleNameEnum.APP.getValue())) {// 如果原先角色不是应用管理员    
            if (orgAdmin.equals("0")) {
                logger.debug("应用管理员角色(非部门管理员)分配成功");
                result.setInfo("应用管理员角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("应用管理员 (非部门管理员)");
                return result;
            }
            logger.debug("应用管理员角色(部门管理员)分配成功");
            result.setInfo("应用管理员角色(部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("应用管理员 (部门管理员)");
            return result;
        }
        // 如果原先角色是应用管理员
        if (updateOrgAdmin && orgAdmin.equals("0")) {// 如果更新了部门管理员字段
            logger.debug("应用管理员角色(非部门管理员)分配成功");
            result.setInfo("应用管理员角色(非部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("应用管理员 (非部门管理员)");
            return result;
        } else if (updateOrgAdmin) {// 如果更新了部门管理员字段且是部门管理员
            logger.debug("应用管理员角色(部门管理员)分配成功");
            result.setInfo("应用管理员角色(部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("应用管理员 (部门管理员)");
            return result;
        } else if (orgAdmin.equals("0")) {
            logger.debug("应用管理员角色(非部门管理员)分配成功");
            result.setInfo("应用管理员角色(非部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("应用管理员 (非部门管理员)");
            return result;
        }
        logger.debug("应用管理员角色(部门管理员)分配成功");
        result.setInfo("应用管理员角色(部门管理员)分配成功");
        result.setResult(true);
        result.setCurUserRole("应用管理员 (部门管理员)");
        return result;
    }

    /**
     * 如果当前选中的是普通员工角色，则更新普通员工角色
     * 
     * @param isAppRole
     * @param roleName
     * @param userId
     * @param roleUserRelId
     * @param umsUserRoleRel
     * @param orgAdmin
     * @param updateOrgAdmin
     * @return
     */
    private AjaxResult updateRoleNormal(String isAppRole, String roleName, String userId,
                                        String roleUserRelId, UmsUserRoleRel umsUserRoleRel,
                                        String orgAdmin, boolean updateOrgAdmin) {
        AjaxResult result = new AjaxResult();
        if ("roleNormal".equals(isAppRole)
            && !StringUtils.trimWhitespace(roleName).equals(RoleNameEnum.NORMAL.getValue())) {// 判断如果当前所选是普通员工且原先角色不是普通员工
            umsUserRoleRel.setRoleName("ROLE_NORMAL");// 如果是普通员工，则更新用户角色表的角色名为普通员工
            umsUserRoleRel.setGmtCreated(new Date());
            umsUserRoleRel.setUserId(userId);
            umsUserRoleRel.setId(roleUserRelId);
            umsUserRoleRelMapper.updateByPrimaryKey(umsUserRoleRel);// 更新用户角色关系表
            if (orgAdmin.equals("0")) {
                logger.debug("普通员工角色(非部门管理员)分配成功");
                result.setInfo("普通员工角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("普通员工 (非部门管理员)");
                return result;
            }
            logger.debug("普通员工角色(部门管理员)分配成功");
            result.setInfo("普通员工角色(部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("普通员工 (部门管理员)");
            return result;
        } else if ("roleNormal".equals(isAppRole)) {// 如果当前所选是普通员工且原先角色是普通员工
            if (updateOrgAdmin && orgAdmin.equals("0")) {// 如果更新了部门管理员字段且是非部门管理员
                logger.debug("普通员工角色(非部门管理员)分配成功");
                result.setInfo("普通员工角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("普通员工 (非部门管理员)");
                return result;
            } else if (updateOrgAdmin) {// 如果更新了部门管理员字段且是部门管理员
                logger.debug("普通员工角色(部门管理员)分配成功");
                result.setInfo("普通员工角色(部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("普通员工 (部门管理员)");
                return result;
            }
            if (orgAdmin.equals("0")) {
                logger.debug("普通员工角色(非部门管理员)无任何变动");
                result.setInfo("普通员工角色(非部门管理员)无任何变动");
                result.setResult(true);
                result.setCurUserRole("普通员工 (非部门管理员)");
                return result;
            }
            logger.debug("普通员工角色(部门管理员)无任何变动");
            result.setInfo("普通员工角色(部门管理员)无任何变动");
            result.setResult(true);
            result.setCurUserRole("普通员工 (部门管理员)");
            return result;
        }
        return null;
    }

    /**
     * 如果当前选中的是短信查询角色，则更新短信查询角色
     * 
     * @param isAppRole
     * @param roleName
     * @param userId
     * @param roleUserRelId
     * @param umsUserRoleRel
     * @param orgAdmin
     * @param updateOrgAdmin
     * @return
     */
    private AjaxResult updateRoleQuery(String isAppRole, String roleName, String userId,
                                       String roleUserRelId, UmsUserRoleRel umsUserRoleRel,
                                       String orgAdmin, boolean updateOrgAdmin) {
        AjaxResult result = new AjaxResult();
        if ("roleQuery".equals(isAppRole)
            && !StringUtils.trimWhitespace(roleName).equals(RoleNameEnum.QUERY.getValue())) {// 判断如果当前所选是短信查询角色且原先角色不是短信查询角色
            umsUserRoleRel.setRoleName("ROLE_QUERY");// 如果是短信查询角色，则更新用户角色表的角色名为短信查询角色
            umsUserRoleRel.setGmtCreated(new Date());
            umsUserRoleRel.setUserId(userId);
            umsUserRoleRel.setId(roleUserRelId);
            umsUserRoleRelMapper.updateByPrimaryKey(umsUserRoleRel);// 更新用户角色关系表
            if (orgAdmin.equals("0")) {
                logger.debug("短信查询角色(非部门管理员)分配成功");
                result.setInfo("短信查询角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("短信查询 (非部门管理员)");
                return result;
            }
            logger.debug("短信查询角色(部门管理员)分配成功");
            result.setInfo("短信查询角色(部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("短信查询 (部门管理员)");
            return result;
        } else if ("roleQuery".equals(isAppRole)) {// 如果当前所选是短信查询角色且原先角色是短信查询角色
            if (updateOrgAdmin && orgAdmin.equals("0")) {// 如果更新了部门管理员字段且是非部门管理员
                logger.debug("短信查询角色(非部门管理员)分配成功");
                result.setInfo("短信查询角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("短信查询 (非部门管理员)");
                return result;
            } else if (updateOrgAdmin) {// 如果更新了部门管理员字段且是部门管理员
                logger.debug("短信查询角色(部门管理员)分配成功");
                result.setInfo("短信查询角色(部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("短信查询 (部门管理员)");
                return result;
            }
            if (orgAdmin.equals("0")) {
                logger.debug("短信查询角色(非部门管理员)无任何变动");
                result.setInfo("短信查询角色(非部门管理员)无任何变动");
                result.setResult(true);
                result.setCurUserRole("短信查询 (非部门管理员)");
                return result;
            }
            logger.debug("短信查询角色(部门管理员)无任何变动");
            result.setInfo("短信查询角色(部门管理员)无任何变动");
            result.setResult(true);
            result.setCurUserRole("短信查询 (部门管理员)");
            return result;
        }
        return null;
    }

    /***
     * 如果当前选中的是系统管理员角色，则更新系统管理员角色
     * 
     * @param isAppRole
     * @param roleName
     * @param userId
     * @param roleUserRelId
     * @param umsUserRoleRel
     * @param orgAdmin
     * @param updateOrgAdmin
     * @return
     */
    private AjaxResult updateRoleAdmin(String isAppRole, String roleName, String userId,
                                       String roleUserRelId, UmsUserRoleRel umsUserRoleRel,
                                       String orgAdmin, boolean updateOrgAdmin) {
        AjaxResult result = new AjaxResult();
        if ("roleAdmin".equals(isAppRole)
            && !StringUtils.trimWhitespace(roleName).equals(RoleNameEnum.ADMIN.getValue())) {// 判断如果当前所选是系统管理员且原先角色不是系统管理员
            umsUserRoleRel.setRoleName("ROLE_ADMIN");// 如果是系统管理员，则更新用户角色表的角色名为系统管理员
            umsUserRoleRel.setGmtCreated(new Date());
            umsUserRoleRel.setUserId(userId);
            umsUserRoleRel.setId(roleUserRelId);
            umsUserRoleRelMapper.updateByPrimaryKey(umsUserRoleRel);// 更新用户角色关系表
            if (orgAdmin.equals("0")) {
                logger.debug("系统管理员角色(非部门管理员)分配成功");
                result.setInfo("系统管理员角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("系统管理员 (非部门管理员)");
                return result;
            }
            logger.debug("系统管理员角色(部门管理员)分配成功");
            result.setInfo("系统管理员角色(部门管理员)分配成功");
            result.setResult(true);
            result.setCurUserRole("系统管理员 (部门管理员)");
            return result;
        } else if ("roleAdmin".equals(isAppRole)) {// 如果当前所选是系统管理员且原先角色是系统管理员
            if (updateOrgAdmin && orgAdmin.equals("0")) {// 如果更新了部门管理员字段
                logger.debug("系统管理员角色(非部门管理员)分配成功");
                result.setInfo("系统管理员角色(非部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("系统管理员 (非部门管理员)");
                return result;
            } else if (updateOrgAdmin) {// 如果更新了部门管理员字段且是部门管理员
                logger.debug("系统管理员角色(部门管理员)分配成功");
                result.setInfo("系统管理员角色(部门管理员)分配成功");
                result.setResult(true);
                result.setCurUserRole("系统管理员 (部门管理员)");
                return result;
            }
            if (orgAdmin.equals("0")) {
                logger.debug("系统管理员角色(非部门管理员)无任何变动");
                result.setInfo("系统管理员角色(非部门管理员)无任何变动");
                result.setResult(true);
                result.setCurUserRole("系统管理员 (非部门管理员)");
                return result;
            }
            logger.debug("系统管理员角色(部门管理员)无任何变动");
            result.setInfo("系统管理员角色(部门管理员)无任何变动");
            result.setResult(true);
            result.setCurUserRole("系统管理员 (部门管理员)");
            return result;
        }
        return null;
    }

    /**
     * 是否更新用户信息表的部门管理员字段
     * 
     * @param curOrgAdmin
     * @param orgAdmin
     * @param userId
     * @return
     */
    private boolean updateOrgAdmin(String curOrgAdmin, String orgAdmin, String userId,
                                   String customOrgName, String curOrgName) {
        Map<String, String> orgMaps = new HashMap<String, String>();
        if (curOrgAdmin.equals(orgAdmin) && StringHelper.equals(customOrgName, curOrgName)) {
            return false;
        } else {
            orgMaps.put("orgAdmin", orgAdmin);
            orgMaps.put("userId", userId);
            orgMaps.put("orgName", customOrgName);
            umsUserInfoMapper.updateOrgAdmin(orgMaps);// 更新用户信息表的部门管理员字段
            return true;// 更新了部门管理员字段
        }
    }

    /**
     * 点击【分配角色】，进入给用户分配角色页面
     *
     * @param userId 用户ID
     * @return
     */
    public RoleConfigAssignment roleAssignment(String userId) {
        if (logger.isInfoEnabled()) {
            logger.info("分配角色开始");
        }
        Map<String, String> map = new HashMap<String, String>();
        UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(userId);// 查出选中赋予角色用户的信息
        if (umsUserInfo == null) {
            return null;
        }
        String orgName = umsOrganizationMapper.getOrgNameById(umsUserInfo.getOrganizationId());// 通过组织id查找组织name
        List<UmsAppInfo> umsAppInfos = getAllApp();// 查询全部应用ID和应用名
        List<UmsAppInfo> appStartWithABCD = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是A或B或C或D开头的集合
        List<UmsAppInfo> appStartWithEFGH = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是E或F或G或H开头的集合
        List<UmsAppInfo> appStartWithIJK = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是I或J或K开头的集合
        List<UmsAppInfo> appStartWithLMN = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是L或M或N开头的集合
        List<UmsAppInfo> appStartWithOPQR = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是O或P或Q或R开头的集合
        List<UmsAppInfo> appStartWithSTUV = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是S或T或U或V开头的集合
        List<UmsAppInfo> appStartWithWXYZ = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是W或X或Y或Z开头的集合
        List<UmsAppInfo> appStartWithOthers = new ArrayList<UmsAppInfo>();// 以应用名第一个汉字首字母是非字母的集合
        for (UmsAppInfo umsAppInfo : umsAppInfos) {
            String appHead = StringHelper.getPinYinHeadChar(umsAppInfo.getAppName());
            if (appHead.length() >= 1) {
                String appHeadCharacter = appHead.substring(0, 1);
                appHeadCharacter = StringUtils.trimWhitespace(appHeadCharacter.toUpperCase());
                if (appHeadCharacter.equals("A") || appHeadCharacter.equals("B")
                    || appHeadCharacter.equals("C") || appHeadCharacter.equals("D")) {
                    appStartWithABCD.add(umsAppInfo);
                } else if (appHeadCharacter.equals("E") || appHeadCharacter.equals("F")
                           || appHeadCharacter.equals("G") || appHeadCharacter.equals("H")) {
                    appStartWithEFGH.add(umsAppInfo);
                } else if (appHeadCharacter.equals("I") || appHeadCharacter.equals("J")
                           || appHeadCharacter.equals("K")) {
                    appStartWithIJK.add(umsAppInfo);
                } else if (appHeadCharacter.equals("L") || appHeadCharacter.equals("M")
                           || appHeadCharacter.equals("N")) {
                    appStartWithLMN.add(umsAppInfo);
                } else if (appHeadCharacter.equals("O") || appHeadCharacter.equals("P")
                           || appHeadCharacter.equals("Q") || appHeadCharacter.equals("R")) {
                    appStartWithOPQR.add(umsAppInfo);
                } else if (appHeadCharacter.equals("S") || appHeadCharacter.equals("T")
                           || appHeadCharacter.equals("U") || appHeadCharacter.equals("V")) {
                    appStartWithSTUV.add(umsAppInfo);
                } else if (appHeadCharacter.equals("W") || appHeadCharacter.equals("X")
                           || appHeadCharacter.equals("Y") || appHeadCharacter.equals("Z")) {
                    appStartWithWXYZ.add(umsAppInfo);
                } else {
                    appStartWithOthers.add(umsAppInfo);
                }
            }
        }
        UmsUserRoleRel userRoleRel = umsUserRoleRelMapper.selectRoleByUserId(userId);// 通过当前用户的userId获取当前点击的所要分配权限的用户的角色分配表的信息
        if (StringHelper.isEmpty(umsUserInfo.getOrgAdmin())) {// 如果所获得部门管理员为空
            umsUserInfo.setOrgAdmin("0");// 默认设为非部门管理员
            map.put("orgAdmin", umsUserInfo.getOrgAdmin());
            map.put("userId", userId);
            umsUserInfoMapper.updateOrgAdmin(map);// 用户信息表根据当前所选用户ID更新部门管理员字段
        }
        if (StringHelper.isEmpty(userRoleRel)) {// 如果查到当前点击的所要分配权限的用户为空，默认是普通员工
            UmsUserRoleRel umsUserRoleRel = new UmsUserRoleRel();// 新建一个用户角色关联表
            umsUserRoleRel.setRoleName("ROLE_NORMAL");// 把角色名设为普通员工
            umsUserRoleRel.setGmtCreated(new Date());
            umsUserRoleRel.setUserId(userId);
            umsUserRoleRel.setId(UUID.randomUUID().toString());// 设置一个新的角色用户关联ID
            umsUserRoleRelMapper.insert(umsUserRoleRel);
            List<String> selectappIds = umsUserRoleAppRelMapper
                .searchAllApp(umsUserRoleRel.getId());
            RoleConfigAssignment roleConfigAssignment = new RoleConfigAssignment(umsUserInfo,
                orgName, umsAppInfos, umsUserRoleRel.getRoleName(), selectappIds, appStartWithABCD,
                appStartWithEFGH, appStartWithIJK, appStartWithLMN, appStartWithOPQR,
                appStartWithSTUV, appStartWithWXYZ, appStartWithOthers);
            return roleConfigAssignment;
        }
        String curRoleName = userRoleRel.getRoleName();// 通过当前用户的userId获取当前点击的所要分配权限的用户的角色名
        List<String> selectappIds = umsUserRoleAppRelMapper.searchAllApp(userRoleRel.getId());
        RoleConfigAssignment roleConfigAssignment = new RoleConfigAssignment(umsUserInfo, orgName,
            umsAppInfos, curRoleName, selectappIds, appStartWithABCD, appStartWithEFGH,
            appStartWithIJK, appStartWithLMN, appStartWithOPQR, appStartWithSTUV, appStartWithWXYZ,
            appStartWithOthers);
        return roleConfigAssignment;
    }

    /**
     * 查询全部应用ID和应用名，并按应用名排序
     * 
     * @return
     */
    private List<UmsAppInfo> getAllApp() {
        List<UmsAppInfo> appList = umsAppInfoMapper.selectAll();
        for (int i = 0; i < appList.size(); i++) {
            if (StringHelper.trim(appList.get(i).getAppId()).equals("0")) {
                appList.remove(i);
            }
        }
        Collections.sort(appList, new MyAppInfocomparator());
        return appList;
    }

    /**
     * 点击【分配单位】，进入给用户分配单位页面
     *
     * @param userId 用户ID
     * @return
     */
    public RoleConfigAssignment areaAssignment(String userId) {
        if (logger.isInfoEnabled()) {
            logger.info("分配单位开始");
        }
        Map<String, String> map = new HashMap<String, String>();
        UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(userId);// 查出选中的用户的信息
        if (umsUserInfo == null) {
            return null;
        }
        String orgName = umsOrganizationMapper.getOrgNameById(umsUserInfo.getOrganizationId());// 通过组织id查找组织name
        UmsUserRoleRel userRoleRel = umsUserRoleRelMapper.selectRoleByUserId(userId);// 通过当前用户的userId获取当前点击的所要分配单位的用户的角色分配表的信息
        if (StringHelper.isEmpty(umsUserInfo.getOrgAdmin())) {// 如果所获得部门管理员为空
            umsUserInfo.setOrgAdmin("0");// 默认设为非部门管理员
            map.put("orgAdmin", umsUserInfo.getOrgAdmin());
            map.put("userId", userId);
            umsUserInfoMapper.updateOrgAdmin(map);// 用户信息表根据当前所选用户ID更新部门管理员字段
        }
        if (StringHelper.isEmpty(userRoleRel)) {// 如果查到当前点击的所要分配单位的用户关联表信息为空，默认是普通员工
            UmsUserRoleRel umsUserRoleRel = new UmsUserRoleRel();// 新建一个用户角色关联表
            umsUserRoleRel.setRoleName("ROLE_NORMAL");// 把角色名设为普通员工
            umsUserRoleRel.setGmtCreated(new Date());
            umsUserRoleRel.setUserId(userId);
            umsUserRoleRel.setId(UUID.randomUUID().toString());// 设置一个新的角色用户关联ID
            umsUserRoleRelMapper.insert(umsUserRoleRel);
            RoleConfigAssignment roleConfigAssignment = new RoleConfigAssignment(umsUserInfo,
                orgName, umsUserRoleRel.getRoleName());
            return roleConfigAssignment;
        }
        String curRoleName = userRoleRel.getRoleName();// 通过当前用户的userId获取当前点击的所要分配单位的用户的角色名
        RoleConfigAssignment roleConfigAssignment = new RoleConfigAssignment(umsUserInfo, orgName,
            curRoleName);
        return roleConfigAssignment;
    }

    class MyAppInfocomparator implements Comparator<UmsAppInfo> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsAppInfo o1, UmsAppInfo o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getAppName() == null || o2.getAppName() == null) {
                return 0;
            }
            return collator.compare(o1.getAppName(), o2.getAppName());
        }
    }
}
