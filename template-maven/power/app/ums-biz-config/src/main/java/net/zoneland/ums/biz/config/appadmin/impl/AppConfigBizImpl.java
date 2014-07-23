/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.appadmin.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.admin.bo.AppInfoBO;
import net.zoneland.ums.biz.config.appadmin.AppConfigBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.UmsAreaMapper;
import net.zoneland.ums.common.dal.UmsFlowLogMapper;
import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.common.dal.dataobject.UmsFlowLog;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.MD5;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用管理员 应用管理类
 *
 * @author XuFan
 * @version $Id: CheckApp.java, v 0.1 Aug 15, 2012 1:23:59 PM XuFan Exp $
 */

public class AppConfigBizImpl implements AppConfigBiz {

    private static final Logger logger = Logger.getLogger(AppConfigBizImpl.class);

    @Autowired
    private UmsAppInfoMapper    umsAppInfoMapper;

    @Autowired
    private UmsMsgOutMapper     umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper  umsMsgOutUcsMapper;

    @Autowired
    private UmsMsgInMapper      umsMsgInMapper;

    @Autowired
    private UmsAppSubMapper     umsAppSubMapper;

    @Autowired
    private UmsUserInfoMapper   umsUserInfoMapper;

    @Autowired
    private UmsFlowLogMapper    umsFlowLogMapper;

    @Autowired
    private UmsAreaMapper       umsAreaMapper;

    /**
     * 应用管理员条件查询应用的分页
     *
     * @param roleUserRelId
     *            应用管理员角色id
     * @param umsAppInfo
     * @param curPage
     * @return
     */
    public PageResult<AppInfoBO> searchAppInfo(UmsAppInfo umsAppInfo, List<String> appids,
                                               int curPage) {

        Map<String, Object> params = new HashMap<String, Object>();
        /*
         * 查询条件
         */
        params.put("appName", umsAppInfo.getAppName());// 按应用名查询
        //params.put("appId", umsAppInfo.getAppId());
        params.put("appCode", umsAppInfo.getAppCode());
        params.put("status", umsAppInfo.getStatus());
        if (appids != null && appids.size() > 0) {
            params.put("appIdList", appids);
            int total = umsAppInfoMapper.searchAllApp(params);// 记录条件分页查询数量
            PageResult<AppInfoBO> result = new PageResult<AppInfoBO>(total, curPage);
            params.put("orderBy", "GMT_CREATED");
            params.put("first", result.getFirstrecode());
            params.put("end", result.getEndrecode());
            List<UmsAppInfo> list = umsAppInfoMapper.searchSelectiveByPage(params);// 分页显示应用信息
            List<AppInfoBO> listout = new ArrayList<AppInfoBO>();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    UmsAppInfo info = list.get(i);
                    int flowM = getMonthFlow(info.getAppId(), new Date());
                    int flowD = getDayFlow(info.getAppId(), new Date());
                    AppInfoBO bo = new AppInfoBO();
                    bo.setAppInfo(info);
                    String day = String.valueOf(flowD)
                                 + " / "
                                 + String
                                     .valueOf(info.getFlowDay() == null ? 0 : info.getFlowDay());
                    String month = String.valueOf(flowM)
                                   + " / "
                                   + String.valueOf(info.getFlowMonth() == null ? 0 : info
                                       .getFlowMonth());
                    bo.setDayflow(day);
                    bo.setMonthflow(month);
                    listout.add(bo);
                }
            }
            result.setResults(listout);
            return result;
        }
        // 如果应用管理员所能管理的应用集合为空，就查询不到任何应用的状态
        PageResult<AppInfoBO> result = new PageResult<AppInfoBO>(0, curPage);
        result.setResults(new ArrayList<AppInfoBO>());
        return result;
    }

    private int getMonthFlow(String appId, Date date) {
        Map<String, Object> params = new HashMap<String, Object>(8);
        params.put("appId", appId);
        params.put("startTime", DateHelper.setFirstDay(date));
        params.put("endTime", DateHelper.getEndOfMonth(date));
        List<UmsFlowLog> logs = umsFlowLogMapper.selectByAppIdWithTime(params);
        Iterator<UmsFlowLog> it = logs.iterator();
        int count = 0;
        while (it.hasNext()) {
            UmsFlowLog log = it.next();
            if (log.getFlowToday() != null) {
                count += log.getFlowToday();
            }
        }
        return count;
    }

    private int getDayFlow(String appId, Date sendTime) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("appId", appId);
        params.put("gmtCreated", sendTime);
        List<UmsFlowLog> logs = umsFlowLogMapper.selectByAppId(params);
        Iterator<UmsFlowLog> it = logs.iterator();
        while (it.hasNext()) {
            UmsFlowLog log = it.next();
            if (log != null && log.getFlowToday() != null) {
                return log.getFlowToday();
            }
        }
        return 0;
    }

    /**
     * 根据查询条件返回查询结果总数
     *
     * @param umsAppInfo
     * @return
     */
    public int searchTotal(UmsAppInfo umsAppInfo) {
        return umsAppInfoMapper.searchAllNum(umsAppInfo);
    }

    /**
     * 修改应用密码
     *
     * @param id
     *            应用id
     * @param newPassword
     *            新密码
     * @return
     */
    public int modfiyAppPassword(String id, String newPassword) {
        if (logger.isInfoEnabled()) {
            logger.info("开始修改应用密码");
        }
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        umsAppInfo.setId(id);
        String passwordMD5 = MD5.md5(newPassword);// MD5加密 成16位字符串
        umsAppInfo.setPassword(passwordMD5);
        int resultNum = umsAppInfoMapper.updateByPrimaryKeySelective(umsAppInfo); // 返回更新记录数量 成功返回 1
        return resultNum;

    }

    /**
     * 分页条件查询应用【用户短信】
     * 
     * @see net.zoneland.ums.biz.config.appadmin.AppConfigBiz#searchAppMsgInfo(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, java.util.List, int)
     */
    public PageResult<UmsMsgOut> searchAppMsgInfo(AppMsgInfoBO appMsgInfoBO,
                                                  List<String> appIdList, int curPage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询应用用户短信");
        }
        int total = 0;
        PageResult<UmsMsgOut> result = null;
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", appMsgInfoBO.getRecvId());// 接收方手机号
        params.put("sendId", appMsgInfoBO.getSendId());// 发送方手机号
        params.put("appId", appMsgInfoBO.getAppId());// 隐藏的应用ID
        params.put("appIdList", appIdList);// 应用管理员所能管理的所有应用ID集合
        params.put("status", appMsgInfoBO.getStatus());// 状态
        params.put("startTime", appMsgInfoBO.getStartTime());
        params.put("endTime", appMsgInfoBO.getEndTime());
        params.put("bizName", appMsgInfoBO.getBizName());// 业务系统
        params.put("bizType", appMsgInfoBO.getBizType());// 业务类型
        params.put("flowNo", appMsgInfoBO.getFlowNo());// 流程号
        params.put("createUser", appMsgInfoBO.getCreateUser());// 生成人员 
        // 按所在组织号显示其所在组织的短信消息
        List<String> areaCodeList = new ArrayList<String>();// 单位编码集合
        String areaIdList = SecurityContextHolder.getContext().getPrincipal().getProvinceId();
        if (StringUtils.isEmpty(areaIdList)) {// 如果用户不属于任何单位则不能查到任何短信
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        String[] areaIds = areaIdList.split(",");
        if (areaIds.length == 1) {// 如果当前用户单位关联表中只有一个单位，有可能是只保存了浙江省电力这一单位编码
            for (String areaId : areaIds) {
                UmsArea umsArea = umsAreaMapper.findByAreaCode(areaId);
                if (umsArea == null) {
                    continue;
                }
                UmsArea parentArea = umsAreaMapper.findByAreaCode(umsArea.getParentId());
                if (parentArea != null) {
                    if (parentArea.getParentId().equals("0")) {// 如果它的父节点的parentId是0的话表示它是浙江省电力
                        areaCodeList.add("334");// 如果是浙江省电力，则以334开头的短信都能查得到
                        areaCodeList.add(umsArea.getAreaCode());//并且浙江省电力本身这个单位的短信也查得到
                    } else {
                        areaCodeList.add(areaId);// 否则还是按当前单位编码开头来like模糊查询
                    }
                } else {
                    areaCodeList.add(areaId);
                }
            }
        } else {// 如果当前用户单位关联表中不止一个单位，则不可能是浙江省电力的单位编码
            for (String areaId : areaIds) {
                areaCodeList.add(areaId);
            }
        }
        boolean areaCodeFlag = false;
        if (areaCodeList != null && areaCodeList.size() > 0) {
            params.put("areaCodeList", areaCodeList);
        } else {
            areaCodeFlag = true;
        }
        // 按发送人员姓名查询
        String destName = appMsgInfoBO.getMsgdestName();// 发送人员姓名
        boolean destFlag = false;
        if (destName != null && !destName.equals("")) {
            List<UmsUserInfo> destList = umsUserInfoMapper.getUsersByUserName(destName);
            if (destList != null && destList.size() > 0) {
                List<String> destUserIds = new ArrayList<String>();
                for (int i = 0; i < destList.size(); i++) {
                    UmsUserInfo info = destList.get(i);
                    destUserIds.add(info.getId());
                }
                params.put("destlist", destUserIds);
            } else {
                destFlag = true;
            }
        }
        // 按接收方人员姓名查询
        String srcname = appMsgInfoBO.getMsgsrcName();
        boolean srcFlag = false;
        if (srcname != null && !srcname.equals("")) {
            List<UmsUserInfo> srcList = umsUserInfoMapper.getUsersByUserName(srcname);
            if (srcList != null && srcList.size() > 0) {
                List<String> srcUserids = new ArrayList<String>();
                for (int i = 0; i < srcList.size(); i++) {
                    UmsUserInfo info = srcList.get(i);
                    srcUserids.add(info.getId());
                }
                params.put("srclist", srcUserids);
            } else {
                srcFlag = true;
            }
        }
        if (areaCodeFlag || destFlag || srcFlag) {// 如果按发送人姓名或按接收方人员姓名查询没有查到结果直接返回空记录
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        total = umsMsgOutMapper.searchAppMsgNum(params);// 记录数量
        result = new PageResult<UmsMsgOut>(total, curPage);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgOut> msgList = umsMsgOutMapper.searchAppMsgByPage(params);// 分页显示应用消息list
        for (int i = 0; i < msgList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgList.get(i).getAppId());
            msgList.get(i).setAppId(info == null ? "" : info.getAppName());
            UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(msgList.get(i).getUserId());
            msgList.get(i).setUserId(user == null ? "" : user.getUserName());
            if (!StringUtils.isNumeric(msgList.get(i).getRecvId())) {// 判断接收id是否uuid
                msgList.get(i).setRecvId(umsUserInfoMapper.getUserName(msgList.get(i).getRecvId()));// 根据uuid获取
            }
            msgList.get(i).setStatus(MsgInfoStatusEnum.getDescription(msgList.get(i).getStatus()));
            UmsArea umsArea = umsAreaMapper.selectByAreaCode(msgList.get(i).getOrgNo());
            if (umsArea != null) {
                msgList.get(i).setOrgNo(umsArea.getAreaName());// 将组织号转换成组织名显示
            } else {
                msgList.get(i).setOrgNo("");
            }
        }
        result.setResults(msgList);
        return result;
    }

    /**
     * 根据应用管理员角色 查找对应的应用
     *
     * @param roleUserRelId
     * @return
     */
    public List<UmsAppInfo> getApp(List<String> appIdList) {
        if (appIdList == null) {
            return null;
        }
        List<UmsAppInfo> umsAppInfos = new ArrayList<UmsAppInfo>();
        Map<String, Object> params = new HashMap<String, Object>();
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        for (String id : appIdList) {
            params.put("id", id);
            umsAppInfo = umsAppInfoMapper.selectByAppId(params);
            if (umsAppInfo != null) {
                umsAppInfos.add(umsAppInfo);
            }
        }
        Collections.sort(umsAppInfos, new MyAppInfocomparator());
        return umsAppInfos;
    }

    /**
     * 根据appId获取子应用
     *
     * @param appId
     * @return
     */
    public List<UmsAppSub> getAppSub(String appId) {
        List<UmsAppSub> appSubs = umsAppSubMapper.selectAllSubApp(appId);
        Collections.sort(appSubs, new MySubAppInfocomparator());
        return appSubs;
    }

    /**
     * 根据名称右边模糊查询应用
     */
    public List<UmsAppInfo> getAppByname(Map<String, Object> map, String appname) {
        List<UmsAppInfo> list = new ArrayList<UmsAppInfo>();
        if (appname != null && !appname.equals("")) {
            map.put("appName", appname);
            list = umsAppInfoMapper.selectAppLikeName(map);
        }
        Collections.sort(list, new MyAppInfocomparator());
        return list;
    }

    /** 
     * 分页条件查询应用【数据短信】
     * 
     * @see net.zoneland.ums.biz.config.appadmin.AppConfigBiz#searchAppDataMsgInfo(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, int)
     */
    public PageResult<UmsMsgOut> searchAppDataMsgInfo(AppMsgInfoBO appMsgInfoBO,
                                                      List<String> appIdList, int curPage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询应用数据短信");
        }
        int total = 0;
        PageResult<UmsMsgOut> result = null;
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", appMsgInfoBO.getRecvId());// 接收方手机号
        params.put("sendId", appMsgInfoBO.getSendId());// 发送方手机号
        params.put("appId", appMsgInfoBO.getAppId());// 隐藏的应用ID
        params.put("appIdList", appIdList);// 应用管理员所能管理的所有应用ID集合
        params.put("status", appMsgInfoBO.getStatus());// 状态
        params.put("startTime", appMsgInfoBO.getStartTime());
        params.put("endTime", appMsgInfoBO.getEndTime());
        params.put("bizName", appMsgInfoBO.getBizName());// 业务系统
        params.put("bizType", appMsgInfoBO.getBizType());// 业务类型
        params.put("flowNo", appMsgInfoBO.getFlowNo());// 流程号
        params.put("createUser", appMsgInfoBO.getCreateUser());// 生成人员 
        // 按所在组织号显示其所在组织的短信消息
        List<String> areaCodeList = new ArrayList<String>();// 单位编码集合
        String areaIdList = SecurityContextHolder.getContext().getPrincipal().getProvinceId();
        if (StringUtils.isEmpty(areaIdList)) {// 如果用户不属于任何单位则不能查到任何短信
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        String[] areaIds = areaIdList.split(",");
        if (areaIds.length == 1) {// 如果当前用户单位关联表中只有一个单位，有可能是只保存了浙江省电力这一单位编码
            for (String areaId : areaIds) {
                UmsArea umsArea = umsAreaMapper.findByAreaCode(areaId);
                if (umsArea == null) {
                    continue;
                }
                UmsArea parentArea = umsAreaMapper.findByAreaCode(umsArea.getParentId());
                if (parentArea != null) {
                    if (parentArea.getParentId().equals("0")) {// 如果它的父节点的parentId是0的话表示它是浙江省电力
                        areaCodeList.add("334");// 如果是浙江省电力，则以334开头的短信都能查得到
                        areaCodeList.add(umsArea.getAreaCode());//并且浙江省电力本身这个单位的短信也查得到
                    } else {
                        areaCodeList.add(areaId);// 否则还是按当前单位编码开头来like模糊查询
                    }
                } else {
                    areaCodeList.add(areaId);
                }
            }
        } else {// 如果当前用户单位关联表中不止一个单位，则不可能是浙江省电力的单位编码
            for (String areaId : areaIds) {
                areaCodeList.add(areaId);
            }
        }
        boolean areaCodeFlag = false;
        if (areaCodeList != null && areaCodeList.size() > 0) {
            params.put("areaCodeList", areaCodeList);
        } else {
            areaCodeFlag = true;
        }
        // 按发送人员姓名查询
        String destName = appMsgInfoBO.getMsgdestName();// 发送人员姓名
        boolean destFlag = false;
        if (destName != null && !destName.equals("")) {
            List<UmsUserInfo> destList = umsUserInfoMapper.getUsersByUserName(destName);
            if (destList != null && destList.size() > 0) {
                List<String> destUserIds = new ArrayList<String>();
                for (int i = 0; i < destList.size(); i++) {
                    UmsUserInfo info = destList.get(i);
                    destUserIds.add(info.getId());
                }
                params.put("destlist", destUserIds);
            } else {
                destFlag = true;
            }
        }
        // 按接收方人员姓名查询
        String srcname = appMsgInfoBO.getMsgsrcName();
        boolean srcFlag = false;
        if (srcname != null && !srcname.equals("")) {
            List<UmsUserInfo> srcList = umsUserInfoMapper.getUsersByUserName(srcname);
            if (srcList != null && srcList.size() > 0) {
                List<String> srcUserids = new ArrayList<String>();
                for (int i = 0; i < srcList.size(); i++) {
                    UmsUserInfo info = srcList.get(i);
                    srcUserids.add(info.getId());
                }
                params.put("srclist", srcUserids);
            } else {
                srcFlag = true;
            }
        }
        if (areaCodeFlag || destFlag || srcFlag) {// 如果按发送人姓名或按接收方人员姓名查询没有查到结果直接返回空记录
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        total = umsMsgOutUcsMapper.searchAppDataMsgNum(params);// 记录数量
        result = new PageResult<UmsMsgOut>(total, curPage);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgOut> msgList = umsMsgOutUcsMapper.searchAppDataMsgByPage(params);// 分页显示应用消息list
        for (int i = 0; i < msgList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgList.get(i).getAppId());
            msgList.get(i).setAppId(info == null ? "" : info.getAppName());
            UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(msgList.get(i).getUserId());
            msgList.get(i).setUserId(user == null ? "" : user.getUserName());
            if (!StringUtils.isNumeric(msgList.get(i).getRecvId())) {// 判断接收id是否uuid
                msgList.get(i).setRecvId(umsUserInfoMapper.getUserName(msgList.get(i).getRecvId()));// 根据uuid获取
            }
            msgList.get(i).setStatus(MsgInfoStatusEnum.getDescription(msgList.get(i).getStatus()));
            UmsArea umsArea = umsAreaMapper.selectByAreaCode(msgList.get(i).getOrgNo());
            if (umsArea != null) {
                msgList.get(i).setOrgNo(umsArea.getAreaName());// 将组织号转换成组织名显示
            } else {
                msgList.get(i).setOrgNo("");
            }
        }
        result.setResults(msgList);
        return result;
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

    class MySubAppInfocomparator implements Comparator<UmsAppSub> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsAppSub o1, UmsAppSub o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getAppSubName() == null || o2.getAppSubName() == null) {
                return 0;
            }
            return collator.compare(o1.getAppSubName(), o2.getAppSubName());

        }
    }

    /** 
     * 分页查询统计应用上行短信
     * 
     * @see net.zoneland.ums.biz.config.appadmin.AppConfigBiz#searchAppMsgIn(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, java.util.List, int)
     */
    public PageResult<UmsMsgIn> searchAppMsgIn(AppMsgInfoBO appMsgInfoBO, List<String> appIdList,
                                               int pageId) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询应用上行短信");
        }
        int total = 0;
        PageResult<UmsMsgIn> result = null;
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", appMsgInfoBO.getRecvId());// 接收方手机号
        params.put("sendId", appMsgInfoBO.getSendId());// 发送方手机号
        params.put("appId", appMsgInfoBO.getAppId());// 隐藏的应用ID
        params.put("appIdList", appIdList);// 应用管理员所能管理的所有应用ID集合
        params.put("status", appMsgInfoBO.getStatus());// 状态
        params.put("startTime", appMsgInfoBO.getStartTime());
        params.put("endTime", appMsgInfoBO.getEndTime());
        total = umsMsgInMapper.searchAppMsgInNum(params);// 记录数量
        result = new PageResult<UmsMsgIn>(total, pageId);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgIn> msgList = umsMsgInMapper.searchAppMsgInByPage(params);// 分页显示应用消息list
        for (int i = 0; i < msgList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgList.get(i).getAppId());
            msgList.get(i).setAppId(info == null ? "" : info.getAppName());
            msgList.get(i).setStatus(MsgInStatusEnum.getDescription(msgList.get(i).getStatus()));
        }
        result.setResults(msgList);
        return result;
    }
}
