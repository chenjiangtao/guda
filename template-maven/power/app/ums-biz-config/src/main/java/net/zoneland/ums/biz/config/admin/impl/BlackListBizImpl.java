/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.BlackListBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsBlackListMapper;
import net.zoneland.ums.common.dal.bo.UmsBlackListBO;
import net.zoneland.ums.common.dal.dataobject.UmsBlackList;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 黑名单业务类
 * @author XuFan
 * @version $Id: UmsBlackListBiz.java, v 0.1 Aug 20, 2012 2:32:48 PM XuFan Exp $
 */

/**
 *
 * @author yangjuanying
 * @version $Id: BlackListBizImpl.java, v 0.1 2012-9-12 下午8:49:00 yangjuanying Exp $
 */
public class BlackListBizImpl implements BlackListBiz {

    private static final Logger logger = Logger.getLogger(BlackListBizImpl.class);

    @Autowired
    private UmsBlackListMapper  umsBlackListMapper;

    @Autowired
    private UmsAppInfoMapper    umsAppInfoMapper;

    /**
     * 条件查询分页显示黑名单
     *
     * @param userId
     * @param appId
     * @param curpage
     * @return
     */
    public PageResult<UmsBlackListBO> searchByPage(String userId, String appId, int curpage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询黑名单");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(appId)) {//如果应用不为空
            params.put("appId", appId);
        }

        params.put("userId", userId);
        int total = umsBlackListMapper.searchNum(params);//返回记录总条数
        PageResult<UmsBlackListBO> result = new PageResult<UmsBlackListBO>(total, curpage);
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsBlackList> list = umsBlackListMapper.searchSelectiveByPage(params);//分页查询结果集
        List<UmsBlackListBO> bos = new ArrayList<UmsBlackListBO>(list.size());
        for (int i = 0; i < list.size(); i++) {
            UmsBlackListBO bo = new UmsBlackListBO();
            bo.setUmsBlackList(list.get(i));
            if (StringUtils.trim(list.get(i).getAppId()).equals("0")) {//如果APPID是0
                bo.setAppName("全部应用");//将应用名设为“全部应用”
            } else {
                bo.setAppName(StringUtils.trim(umsAppInfoMapper.getAppNameByAppId(list.get(i)
                    .getAppId())));//把appId转换成appName显示
            }
            bos.add(bo);
        }
        result.setResults(bos);
        return result;
    }

    /**
     * 通过手动输入手机号添加黑名单
     *
     * @param userId
     * @param appId
     */
    public int insertBlackListByNum(String userId, String appId) {
        int num = 0;
        if (!isExist(userId, appId)) {//判断输入手机号是否在黑名单中已经存在
            UmsBlackList umsBlackList = new UmsBlackList();
            umsBlackList.setId(UUID.randomUUID().toString());
            umsBlackList.setAppId(appId);//0表示不接收所有应用的消息
            umsBlackList.setGmtCreated(new Date());
            umsBlackList.setGmtModified(new Date());
            umsBlackList.setUserId(userId);
            num = umsBlackListMapper.insertSelective(umsBlackList);//如果在用户表中查不到有此人，则插入
            return num;
        } else {
            return 0;
        }
    }

    /**
     * 创建新的黑名单手机号
     *
     * @param umsBlackList
     * @return
     */
    private int createBlackList(UmsBlackList umsBlackList) {
        boolean belongAllApp = false;
        List<String> userIds = umsBlackListMapper.selectByAppId("0");
        List<UmsBlackList> blackLists = umsBlackListMapper.getUserId(umsBlackList);
        //根据应用和黑名单手机号判断数据库中该应用是否已经有相同黑名单手机号
        List<UmsBlackList> blackListInfo = umsBlackListMapper.getByUserIdAndAppId(umsBlackList);
        if (blackLists != null && blackLists.size() > 0) { //有相同的黑名单手机号
            if (userIds.size() == 0) { //数据库中没有属于【全部应用】的黑名单手机号
                if (StringUtils.trim(umsBlackList.getAppId()).equals("0")) { //下拉框选中的【全部应用】
                    umsBlackListMapper.deleteBlackListByUserId(StringUtils.trim(umsBlackList
                        .getUserId())); //则把该黑名单手机号相关的个别应用删除再插入一条属于全部应用的黑名单手机号
                    saveNewBlackList(umsBlackList); //插入新黑名单手机号
                    return 2;
                }
            }
            if (userIds != null && userIds.size() > 0) { //获得属于【全部应用】的黑名单手机号
                for (int i = 0; i < userIds.size(); ++i) {
                    if (StringUtils.trim(userIds.get(i)).equals(
                        StringUtils.trim(umsBlackList.getUserId()))) {
                        belongAllApp = true;
                    }
                }
                if (!belongAllApp) {
                    //情况一：当前黑名单手机号没有添加到全部应用(即属于个别应用),并且下拉框选择了【全部应用】
                    //如果当前黑名单手机号没有添加到全部应用的记录
                    if (StringUtils.trim(umsBlackList.getAppId()).equals("0")) {//下拉框选中的【全部应用】
                        umsBlackListMapper.deleteBlackListByUserId(StringUtils.trim(umsBlackList
                            .getUserId()));//则把该黑名单手机号相关的个别应用删除再插入一条属于全部应用的黑名单手机号
                        saveNewBlackList(umsBlackList);//插入新黑名单手机号
                        return 2;
                    }
                    //情况二：当前黑名单手机号没有添加到全部应用(即属于个别应用)，并且下拉框没有选择【全部应用】(即选择了个别应用)
                    if (blackListInfo != null && blackListInfo.size() > 0) {//判断数据库中该应用是否已经有相同黑名单手机号
                        return -1;//数据库中有该黑名单手机号，提示已存在该黑名单手机号
                    }
                    //该应用中不存在相同黑名单手机号则作为新黑名单手机号插入
                    int insertNum = saveNewBlackList(umsBlackList);//插入新黑名单手机号
                    return insertNum;
                }
                //情况三：当前黑名单手机号已经添加到了全部应用，并且在全部应用中找到该黑名单手机号
                if (blackListInfo != null && blackListInfo.size() > 0) {//判断数据库中该应用是否已经有相同黑名单手机号
                    return -1;//数据库中有该黑名单手机号，提示已存在该黑名单手机号
                }
                //情况四：当前黑名单手机号已经添加到了全部应用，并且在全部应用中没有找到该黑名单手机号
                //如果该黑名单手机号选择了下拉框【全部应用】
                if (StringUtils.trim(umsBlackList.getAppId()).equals("0")) {
                    int insertNum = saveNewBlackList(umsBlackList);//插入新黑名单手机号
                    return insertNum;
                }
                //如果该黑名单手机号没有选择下拉框【全部应用】，即选择了个别应用
                return 0;
            }
            //如果该黑名单手机号中不是属于【全部应用】的黑名单手机号
            if (blackListInfo != null && blackListInfo.size() > 0) {//判断数据库中该应用是否已经有相同黑名单手机号
                return -1;//数据库中有该黑名单手机号，提示已存在该黑名单手机号
            }
            //如果该黑名单手机号中不是属于【全部应用】的黑名单手机号，且数据库在所选的该应用下没有该黑名单手机号
            int insertNum = saveNewBlackList(umsBlackList);//插入新黑名单手机号
            return insertNum;
        } else {//数据库中没有相同黑名单手机号则插入一条新记录
            int insertNum = saveNewBlackList(umsBlackList);//插入新黑名单手机号
            return insertNum;
        }
    }

    /**
     * 保存新的黑名单手机号
     *
     * @param umsBlackList
     * @return
     */
    private int saveNewBlackList(UmsBlackList umsBlackList) {
        umsBlackList.setId(UUID.randomUUID().toString());
        umsBlackList.setGmtCreated(new Date());
        umsBlackList.setGmtModified(new Date());
        int insertNum = umsBlackListMapper.insertSelective(umsBlackList);//插入新黑名单手机号
        return insertNum;
    }

    /**
     * 通过手动输入手机号添加黑名单
     *
     * @param userId
     * @param appId
     */
    public String saveBlackList(String[] userIds, String appId) {
        int count = 0;
        int error = 0;
        int existError = 0;
        boolean regPhone = false;//判断是否有手机号超过最大长度或是否非法
        boolean isAllBlack = false;//判断该黑名单手机号是否已经添加了全部应用
        boolean toAllBlack = false;//判断该黑名单手机号是否从个别应用变为属于全部应用
        String blackList = "";//保存成功的黑名单手机号集合
        StringBuilder buf = new StringBuilder();
        for (int i = 0, len = userIds.length; i < len; ++i) {
            UmsBlackList umsBlackList = new UmsBlackList();
            if (userIds[i].length() > 11) {//某个手机号长度大于11个字符
                regPhone = true;
                continue;//不保存此手机号
            }
            if (!StringRegUtils.isPhoneNumber(StringRegUtils.regExpMobile, userIds[i])//判断手机号是否满足移动手机号
                && !StringRegUtils.isPhoneNumber(StringRegUtils.regExpUnicom, userIds[i])//判断手机号是否满足联通手机号
                && !StringRegUtils.isPhoneNumber(StringRegUtils.regExpTelecom, userIds[i])) {//判断手机号是否满足移动手机号
                regPhone = true;
                continue;//不保存此手机号
            }
            umsBlackList.setAppId(appId);
            umsBlackList.setUserId(StringUtils.trim(userIds[i]));
            int res = createBlackList(umsBlackList);
            if (res == 1) {//成功插入该黑名单手机号
                ++count;
                blackList += userIds[i] + ",";
                if (count % 4 == 0) {
                    blackList += "<br/>";
                }
            } else if (res == -1) {//已经存在该黑名单手机号
                ++existError;
            } else if (res == 2) {//由属于个别应用改为属于全部应用
                ++count;
                blackList += userIds[i] + ",";
                if (count % 4 == 0) {
                    blackList += "<br/>";
                }
                toAllBlack = true;
            } else if (res == 0) {//有黑名单手机号已经属于全部应用
                isAllBlack = true;
            } else {
                ++error;
            }
        }
        if (isAllBlack) {
            buf.append("有手机号已经属于全部应用，该黑名单手机号保存失败.<br/>(若要分配该黑名单手机号到个别的应用请先删除该黑名单手机号).<br/>");
        }
        if (toAllBlack) {
            buf.append("有黑名单由属于个别应用改为属于全部应用.该黑名单手机号之前相关的个别应用信息已删除.<br/>");
        }
        if (regPhone) {
            buf.append("有手机号长度大于11个数字或输入非法手机号，该手机号保存失败.<br/>(添加黑名单手机号规则：手机号长度必须为11位数字).<br/>");
        }
        if (count > 0) {
            buf.append("手机号：<br/>");
            if (blackList.endsWith("<br/>")) {
                blackList = blackList.substring(0, blackList.lastIndexOf(","));
                blackList = blackList + ".";
            }
            if (blackList.endsWith(",")) {
                blackList = blackList.substring(0, blackList.lastIndexOf(","));
                blackList = blackList + ".";
            }
            buf.append(blackList);
        }
        buf.append("<br/>").append(count).append("个黑名单手机号保存成功.");
        if (existError > 0) {
            buf.append(existError).append("个黑名单手机号由于已经存在，保存失败.");
        }
        if (error > 0) {
            buf.append(error).append("个黑名单手机号保存失败.");
        }
        return buf.toString();
    }

    /**
     * 检查黑名单是否已经存在
     *
     * @param userId blacklist中的userId字段
     * @param appId
     * @return
     */
    public boolean isExist(String userId, String appId) {
        int num = 0;
        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", appId);
        params.put("userId", userId);
        num = umsBlackListMapper.selectByPhone(params);// 根据黑名单手机号和应用ID查询检查黑名单是否已经存在
        if (num > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据id删除黑名单
     *
     * @param id
     */
    public boolean deleteBlackList(String id) {
        return umsBlackListMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 判断是否是黑名单手机号
     *
     * @see net.zoneland.ums.biz.config.admin.BlackListBiz#isBlackList(java.lang.String, java.lang.String)
     */
    public boolean isBlackList(String phoneNumber, String appId) {
        if (phoneNumber == null) {
            return false;
        }
        List<String> list = umsBlackListMapper.selectByPhoneNumber(phoneNumber);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String app = it.next();
            if ("0".equals(app) || app.equalsIgnoreCase(appId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据应用ID和手机号删除该黑名单手机号
     *
     * @see net.zoneland.ums.biz.config.admin.BlackListBiz#deleteBlackList(java.lang.String, java.lang.String)
     */
    public int deleteBlackList(String phoneNumber, String appId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phoneNumber", phoneNumber);
        params.put("appId", appId);
        return umsBlackListMapper.delBlackList(params);
    }

    /**
     * 根据应用ID删除黑名单手机号
     *
     * @see net.zoneland.ums.biz.config.admin.BlackListBiz#deleteBlackListByAppId(java.lang.String)
     */
    public boolean deleteBlackListByAppId(String appId) {
        if (StringHelper.isEmpty(appId)) {
            return false;
        }
        umsBlackListMapper.deleteBlackListByAppId(appId);
        return true;
    }
}
