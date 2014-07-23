/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.io.FileOutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.admin.AllAppMsgBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAreaMapper;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.MSExcelHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 管理员查询统计全部消息业务类
 * 
 * @author XuFan
 * @version $Id: AllAppMsgBizImpl.java, v 0.1 Aug 23, 2012 1:19:56 PM XuFan Exp
 *          $
 */
public class AllAppMsgBizImpl implements AllAppMsgBiz {

    private static final Logger  logger = Logger.getLogger(AllAppMsgBizImpl.class);

    @Autowired
    private UmsMsgOutMapper      umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper   umsMsgOutUcsMapper;

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    @Autowired
    private UmsUserInfoMapper    umsUserInfoMapper;

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    @Autowired
    private UmsAreaMapper        umsAreaMapper;

    /**
     * 条件分页查询统计全部应用【用户短信】
     * 
     * @param bo
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOut> searchAllAppMsg(AppMsgInfoBO bo, int curPage) {
        logger.debug("开始查询统计全部应用用户短信");
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());// 发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("bizName", bo.getBizName());// 业务系统
        params.put("bizType", bo.getBizType());// 业务类型
        params.put("flowNo", bo.getFlowNo());// 流程号
        params.put("createUser", bo.getCreateUser());// 生成人员 
        int total = 0;
        PageResult<UmsMsgOut> result = null;
        // 按所在单位名称查询
        String orgNoName = bo.getOrgNo();
        boolean orgNoNameFlag = false;
        if (orgNoName != null && !orgNoName.equals("")) {
            params.put("areaName", orgNoName);
            List<String> areaCodeList = umsAreaMapper.findByAreaName(params);
            if (areaCodeList != null && areaCodeList.size() > 0) {
                params.put("areaCodeList", areaCodeList);
            } else {
                orgNoNameFlag = true;
            }
        }
        // 按应用名查询
        String appName = bo.getAppName();
        boolean appNameFlag = false;
        if (appName != null && !appName.equals("")) {
            params.put("appName", appName);
            List<String> appIdList = umsAppInfoMapper.getAppIdByAppName(params);
            if (appIdList != null && appIdList.size() > 0) {
                params.put("appIdList", appIdList);
            } else {
                appNameFlag = true;
            }
        }
        // 按运营商查询
        boolean gatewaylistFlag = false;
        List<String> gatewayIds = new ArrayList<String>();
        List<UmsGateWayInfo> gatewaylist = umsGateWayInfoMapper.findAllByType(bo.getGatewaytype());
        if (gatewaylist != null && gatewaylist.size() > 0) {
            for (int j = 0; j < gatewaylist.size(); j++) {
                UmsGateWayInfo info = gatewaylist.get(j);
                gatewayIds.add(info.getId());
            }
        } else if (StringUtils.isNotEmpty(bo.getGatewaytype())) {
            gatewaylistFlag = true;
        }
        if (gatewayIds != null && gatewayIds.size() > 0) {
            params.put("gatewaylist", gatewayIds);
        }
        // 按发送人员姓名查询
        String destName = bo.getMsgdestName();// 发送人员姓名
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
        String srcname = bo.getMsgsrcName();
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
        if (destFlag || srcFlag || appNameFlag || orgNoNameFlag || gatewaylistFlag) {
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        total = umsMsgOutMapper.searchAllMsgNum(params);// 记录总数量
        result = new PageResult<UmsMsgOut>(total, curPage);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgOut> msgList = umsMsgOutMapper.searchAllMsgByPage(params);// 分页显示全部应用发出短信
        for (int i = 0; i < msgList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgList.get(i).getAppId());
            msgList.get(i).setAppId(info == null ? "" : info.getAppName());
            UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(msgList.get(i).getUserId());
            msgList.get(i).setUserId(user == null ? "" : user.getUserName());
            if (!StringUtils.isNumeric(msgList.get(i).getRecvId())) {// 判断接收id是否uuid
                msgList.get(i).setRecvId(umsUserInfoMapper.getUserName(msgList.get(i).getRecvId()));// 根据uuid获取接收方
            }
            UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(msgList.get(i)
                .getMediaId());// 获得渠道类型，将运营商ID转换为运营商姓名显示
            if (umsGateWayInfo != null) {
                msgList.get(i).setMediaId(GateEnum.getDescription(umsGateWayInfo.getType()));
            } else {
                msgList.get(i).setMediaId("");
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
     * 获取全部应用id 和 name
     * 
     * @return
     */
    public List<UmsAppInfo> getAllApp() {
        List<UmsAppInfo> appList = umsAppInfoMapper.selectAll();
        for (int i = 0; i < appList.size(); i++) {
            if (StringUtils.trim(appList.get(i).getAppId()).equals("0")) {
                appList.remove(i);
            }
        }
        Collections.sort(appList, new MyAppInfocomparator());
        return appList;
    }

    /**
     * 查询统计全部应用【用户短信】导出excel表
     * 
     * @param bo
     * @param path
     */
    public void exportExcel(AppMsgInfoBO bo, String path) {
        logger.debug("开始查询统计全部应用用户短信，并导出统计excel");
        Map<String, Object> params = new HashMap<String, Object>();// 条件map
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());// 发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("bizName", bo.getBizName());// 业务系统
        params.put("bizType", bo.getBizType());// 业务类型
        params.put("flowNo", bo.getFlowNo());// 流程号
        params.put("createUser", bo.getCreateUser());// 生成人员     
        params.put("orderBy", "GMT_MODIFIED");
        // 按所在单位名称查询
        String orgNoName = bo.getOrgNo();
        boolean orgNoNameFlag = false;
        if (orgNoName != null && !orgNoName.equals("")) {
            params.put("areaName", orgNoName);
            List<String> areaCodeList = umsAreaMapper.findByAreaName(params);
            if (areaCodeList != null && areaCodeList.size() > 0) {
                params.put("areaCodeList", areaCodeList);
            } else {
                orgNoNameFlag = true;
            }
        }
        // 按应用名查询
        String appName = bo.getAppName();
        boolean appNameFlag = false;
        if (appName != null && !appName.equals("")) {
            params.put("appName", appName);
            List<String> appIdList = umsAppInfoMapper.getAppIdByAppName(params);
            if (appIdList != null && appIdList.size() > 0) {
                params.put("appIdList", appIdList);
            } else {
                appNameFlag = true;
            }
        }
        // 按运营商查询
        boolean gatewaylistFlag = false;
        List<String> gatewayIds = new ArrayList<String>();
        List<UmsGateWayInfo> gatewaylist = umsGateWayInfoMapper.findAllByType(bo.getGatewaytype());
        if (gatewaylist != null && gatewaylist.size() > 0) {
            for (int j = 0; j < gatewaylist.size(); j++) {
                UmsGateWayInfo info = gatewaylist.get(j);
                gatewayIds.add(info.getId());
            }
        } else if (StringUtils.isNotEmpty(bo.getGatewaytype())) {
            gatewaylistFlag = true;
        }
        if (gatewayIds != null && gatewayIds.size() > 0) {
            params.put("gatewaylist", gatewayIds);
        }
        // 按发送人员姓名查询
        String destName = bo.getMsgdestName();// 发送人员姓名
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
        String srcname = bo.getMsgsrcName();
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
        if (destFlag || srcFlag || appNameFlag || orgNoNameFlag || gatewaylistFlag) {
            List<UmsMsgOut> elist = new ArrayList<UmsMsgOut>();
            UmsMsgOut out = new UmsMsgOut();
            elist.add(out);
            packageExcelMsgOutlist(path, elist);
        } else {
            List<UmsMsgOut> list = umsMsgOutMapper.searchMsgforExport(params);// 条件查询后
            if (list != null && list.size() > 0) {
                List<UmsMsgOut> elist = new ArrayList<UmsMsgOut>();
                Iterator<UmsMsgOut> it = list.iterator();
                while (it.hasNext()) {
                    UmsMsgOut out = it.next();
                    UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(out.getAppId());
                    out.setAppId(info == null ? "" : info.getAppName());
                    UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(out.getUserId());
                    out.setUserId(user == null ? "" : user.getUserName());
                    if (!StringUtils.isNumeric(out.getRecvId())) {// 判断接收id是否uuid
                        out.setRecvId(umsUserInfoMapper.getUserName(out.getRecvId()));// 根据uuid获取接收方
                    }
                    UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(out
                        .getMediaId());// 获得渠道类型，将运营商ID转换为运营商姓名显示
                    if (umsGateWayInfo != null) {
                        out.setMediaId(GateEnum.getDescription(umsGateWayInfo.getType()));
                    } else {
                        out.setMediaId("");
                    }
                    out.setStatus(MsgInfoStatusEnum.getDescription(out.getStatus()));
                    UmsArea umsArea = umsAreaMapper.selectByAreaCode(out.getOrgNo());
                    if (umsArea != null) {
                        out.setOrgNo(umsArea.getAreaName());// 将组织号转换成组织名显示
                    } else {
                        out.setOrgNo("");
                    }
                    elist.add(out);
                }
                packageExcelMsgOutlist(path, elist);
            } else {
                List<UmsMsgOut> elist = new ArrayList<UmsMsgOut>();
                UmsMsgOut out = new UmsMsgOut();
                elist.add(out);
                packageExcelMsgOutlist(path, elist);
            }
        }
    }

    /**
     * 将查询统计全部应用【用户短信】的结果输出到excel
     * 
     * @param path
     * @param list
     */
    private void packageExcelMsgOutlist(String path, List<UmsMsgOut> list) {
        List<List<Object>> sheet1 = new ArrayList<List<Object>>();
        List<Object> totallist = new ArrayList<Object>();
        totallist.add("发送方人员");
        totallist.add("发送方手机号");
        totallist.add("接收方手机号");
        totallist.add("短信内容");
        totallist.add("业务系统");
        totallist.add("业务类别");
        totallist.add("所属组织");
        totallist.add("所属应用");
        totallist.add("所属运营商");
        totallist.add("流程号");
        totallist.add("生成人员");
        totallist.add("状态");
        totallist.add("操作时间");
        sheet1.add(totallist);
        if (list != null && list.size() > 0) {
            Iterator<UmsMsgOut> it = list.iterator();
            while (it.hasNext()) {
                UmsMsgOut umsMsgOut = it.next();
                List<Object> rowlist = new ArrayList<Object>();
                rowlist.add(umsMsgOut.getUserId() == null ? "" : umsMsgOut.getUserId());// 发送方人员
                rowlist.add(umsMsgOut.getSendId() == null ? "" : umsMsgOut.getSendId());// 发送方手机号           
                rowlist.add(umsMsgOut.getRecvId() == null ? "" : umsMsgOut.getRecvId());// 接收方手机号
                rowlist.add(umsMsgOut.getContent() == null ? "" : umsMsgOut.getContent());// 短信内容  
                rowlist.add(umsMsgOut.getBizName() == null ? "" : umsMsgOut.getBizName());// 业务系统    
                rowlist.add(umsMsgOut.getBizType() == null ? "" : umsMsgOut.getBizType());// 业务类别
                rowlist.add(umsMsgOut.getOrgNo() == null ? "" : umsMsgOut.getOrgNo());// 所属组织
                rowlist.add(umsMsgOut.getAppId() == null ? "" : umsMsgOut.getAppId());// 所属应用
                rowlist.add(umsMsgOut.getMediaId() == null ? "" : umsMsgOut.getMediaId());// 所属运营商
                rowlist.add(umsMsgOut.getFlowNo() == null ? "" : umsMsgOut.getFlowNo());// 流程号
                rowlist.add(umsMsgOut.getCreateUser() == null ? "" : umsMsgOut.getCreateUser());// 生成人员              
                rowlist.add(umsMsgOut.getStatus() == null ? "" : umsMsgOut.getStatus());// 状态
                rowlist.add(umsMsgOut.getGmtModified() == null ? "" : DateHelper
                    .getStrDateByFormat(umsMsgOut.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));// 操作时间
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
            logger.error("写入用户短信导出表excel sheet出错：" + e);
        } finally {
            try {
                fos.close();
            } catch (Exception e2) {
                logger.error("关闭查询统计用户短信输出文件流FileOutputStream出错：" + e2);
            }
        }
    }

    /** 
     * 条件分页查询统计全部应用【数据短信】
     * 
     * 
     */
    public PageResult<UmsMsgOut> searchAllAppMsgUcs(AppMsgInfoBO bo, int curPage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询统计全部应用数据短信");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());// 发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("bizName", bo.getBizName());// 业务系统
        params.put("bizType", bo.getBizType());// 业务类型
        params.put("flowNo", bo.getFlowNo());// 流程号
        params.put("createUser", bo.getCreateUser());// 生成人员 
        int total = 0;
        PageResult<UmsMsgOut> result = null;
        // 按所在单位名称查询
        String orgNoName = bo.getOrgNo();
        boolean orgNoNameFlag = false;
        if (orgNoName != null && !orgNoName.equals("")) {
            params.put("areaName", orgNoName);
            List<String> areaCodeList = umsAreaMapper.findByAreaName(params);
            if (areaCodeList != null && areaCodeList.size() > 0) {
                params.put("areaCodeList", areaCodeList);
            } else {
                orgNoNameFlag = true;
            }
        }
        // 按应用名查询
        String appName = bo.getAppName();
        boolean appNameFlag = false;
        if (appName != null && !appName.equals("")) {
            params.put("appName", appName);
            List<String> appIdList = umsAppInfoMapper.getAppIdByAppName(params);
            if (appIdList != null && appIdList.size() > 0) {
                params.put("appIdList", appIdList);
            } else {
                appNameFlag = true;
            }
        }
        // 按运营商查询
        boolean gatewaylistFlag = false;
        List<String> gatewayIds = new ArrayList<String>();
        List<UmsGateWayInfo> gatewaylist = umsGateWayInfoMapper.findAllByType(bo.getGatewaytype());
        if (gatewaylist != null && gatewaylist.size() > 0) {
            for (int j = 0; j < gatewaylist.size(); j++) {
                UmsGateWayInfo info = gatewaylist.get(j);
                gatewayIds.add(info.getId());
            }
        } else if (StringUtils.isNotEmpty(bo.getGatewaytype())) {
            gatewaylistFlag = true;
        }
        if (gatewayIds != null && gatewayIds.size() > 0) {
            params.put("gatewaylist", gatewayIds);
        }
        // 按发送人员姓名查询
        String destName = bo.getMsgdestName();// 发送人员姓名
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
        String srcname = bo.getMsgsrcName();
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
        if (destFlag || srcFlag || appNameFlag || orgNoNameFlag || gatewaylistFlag) {
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        total = umsMsgOutUcsMapper.searchAllMsgNum(params);// 记录总数量
        result = new PageResult<UmsMsgOut>(total, curPage);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgOut> msgList = umsMsgOutUcsMapper.searchAllMsgByPage(params);// 分页显示全部应用发出短信
        for (int i = 0; i < msgList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgList.get(i).getAppId());
            msgList.get(i).setAppId(info == null ? "" : info.getAppName());
            UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(msgList.get(i).getUserId());
            msgList.get(i).setUserId(user == null ? "" : user.getUserName());
            if (!StringUtils.isNumeric(msgList.get(i).getRecvId())) {// 判断接收id是否uuid
                msgList.get(i).setRecvId(umsUserInfoMapper.getUserName(msgList.get(i).getRecvId()));// 根据uuid获取接收方
            }
            UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(msgList.get(i)
                .getMediaId());// 获得渠道类型，将运营商ID转换为运营商姓名显示
            if (umsGateWayInfo != null) {
                msgList.get(i).setMediaId(GateEnum.getDescription(umsGateWayInfo.getType()));
            } else {
                msgList.get(i).setMediaId("");
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
     * 查询统计全部应用【数据短信】导出excel表
     * 
     * @see net.zoneland.ums.biz.config.admin.AllAppMsgBiz#exportDataMsgExcel(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, java.lang.String)
     */
    public void exportDataMsgExcel(AppMsgInfoBO bo, String path) {
        logger.debug("开始查询统计全部应用数据短信，并导出统计excel");
        Map<String, Object> params = new HashMap<String, Object>();// 条件map
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());//发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("bizName", bo.getBizName());// 业务系统
        params.put("bizType", bo.getBizType());// 业务类型
        params.put("flowNo", bo.getFlowNo());// 流程号
        params.put("createUser", bo.getCreateUser());// 生成人员     
        params.put("orderBy", "GMT_MODIFIED");
        // 按所在单位名称查询
        String orgNoName = bo.getOrgNo();
        boolean orgNoNameFlag = false;
        if (orgNoName != null && !orgNoName.equals("")) {
            params.put("areaName", orgNoName);
            List<String> areaCodeList = umsAreaMapper.findByAreaName(params);
            if (areaCodeList != null && areaCodeList.size() > 0) {
                params.put("areaCodeList", areaCodeList);
            } else {
                orgNoNameFlag = true;
            }
        }
        // 按应用名查询
        String appName = bo.getAppName();
        boolean appNameFlag = false;
        if (appName != null && !appName.equals("")) {
            params.put("appName", appName);
            List<String> appIdList = umsAppInfoMapper.getAppIdByAppName(params);
            if (appIdList != null && appIdList.size() > 0) {
                params.put("appIdList", appIdList);
            } else {
                appNameFlag = true;
            }
        }
        // 按运营商查询
        boolean gatewaylistFlag = false;
        List<String> gatewayIds = new ArrayList<String>();
        List<UmsGateWayInfo> gatewaylist = umsGateWayInfoMapper.findAllByType(bo.getGatewaytype());
        if (gatewaylist != null && gatewaylist.size() > 0) {
            for (int j = 0; j < gatewaylist.size(); j++) {
                UmsGateWayInfo info = gatewaylist.get(j);
                gatewayIds.add(info.getId());
            }
        } else if (StringUtils.isNotEmpty(bo.getGatewaytype())) {
            gatewaylistFlag = true;
        }
        if (gatewayIds != null && gatewayIds.size() > 0) {
            params.put("gatewaylist", gatewayIds);
        }
        // 按发送人员姓名查询
        String destName = bo.getMsgdestName();// 发送人员姓名
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
        String srcname = bo.getMsgsrcName();
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
        if (destFlag || srcFlag || appNameFlag || orgNoNameFlag || gatewaylistFlag) {
            List<UmsMsgOut> elist = new ArrayList<UmsMsgOut>();
            UmsMsgOut out = new UmsMsgOut();
            elist.add(out);
            packageExcelDataMsgOutlist(path, elist);
        } else {
            List<UmsMsgOut> list = umsMsgOutUcsMapper.searchDataMsgforExport(params);// 条件查询后
            if (list != null && list.size() > 0) {
                List<UmsMsgOut> elist = new ArrayList<UmsMsgOut>();
                Iterator<UmsMsgOut> it = list.iterator();
                while (it.hasNext()) {
                    UmsMsgOut out = it.next();
                    UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(out.getAppId());
                    out.setAppId(info == null ? "" : info.getAppName());
                    UmsUserInfo user = umsUserInfoMapper.selectByPrimaryKey(out.getUserId());
                    out.setUserId(user == null ? "" : user.getUserName());
                    if (!StringUtils.isNumeric(out.getRecvId())) {// 判断接收id是否uuid
                        out.setRecvId(umsUserInfoMapper.getUserName(out.getRecvId()));// 根据uuid获取接收方
                    }
                    UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(out
                        .getMediaId());// 获得渠道类型，将运营商ID转换为运营商姓名显示
                    if (umsGateWayInfo != null) {
                        out.setMediaId(GateEnum.getDescription(umsGateWayInfo.getType()));
                    } else {
                        out.setMediaId("");
                    }
                    out.setStatus(MsgInfoStatusEnum.getDescription(out.getStatus()));
                    UmsArea umsArea = umsAreaMapper.selectByAreaCode(out.getOrgNo());
                    if (umsArea != null) {
                        out.setOrgNo(umsArea.getAreaName());// 将组织号转换成组织名显示
                    } else {
                        out.setOrgNo("");
                    }
                    elist.add(out);
                }
                packageExcelDataMsgOutlist(path, elist);
            } else {
                List<UmsMsgOut> elist = new ArrayList<UmsMsgOut>();
                UmsMsgOut out = new UmsMsgOut();
                elist.add(out);
                packageExcelDataMsgOutlist(path, elist);
            }
        }
    }

    /**
     * 将查询统计全部应用【数据短信】的结果输出到excel
     * 
     * @param path
     * @param list
     */
    private void packageExcelDataMsgOutlist(String path, List<UmsMsgOut> list) {
        List<List<Object>> sheet1 = new ArrayList<List<Object>>();
        List<Object> totallist = new ArrayList<Object>();
        totallist.add("发送方人员");
        totallist.add("发送方手机号");
        totallist.add("接收方手机号");
        totallist.add("短信内容");
        totallist.add("业务系统");
        totallist.add("业务类别");
        totallist.add("所属组织");
        totallist.add("所属应用");
        totallist.add("所属运营商");
        totallist.add("流程号");
        totallist.add("生成人员");
        totallist.add("状态");
        totallist.add("操作时间");
        sheet1.add(totallist);
        if (list != null && list.size() > 0) {
            Iterator<UmsMsgOut> it = list.iterator();
            while (it.hasNext()) {
                UmsMsgOut umsMsgOut = it.next();
                List<Object> rowlist = new ArrayList<Object>();
                rowlist.add(umsMsgOut.getUserId() == null ? "" : umsMsgOut.getUserId());// 发送方人员
                rowlist.add(umsMsgOut.getSendId() == null ? "" : umsMsgOut.getSendId());// 发送方手机号           
                rowlist.add(umsMsgOut.getRecvId() == null ? "" : umsMsgOut.getRecvId());// 接收方手机号
                rowlist.add(umsMsgOut.getContent() == null ? "" : umsMsgOut.getContent());// 短信内容  
                rowlist.add(umsMsgOut.getBizName() == null ? "" : umsMsgOut.getBizName());// 业务系统    
                rowlist.add(umsMsgOut.getBizType() == null ? "" : umsMsgOut.getBizType());// 业务类别
                rowlist.add(umsMsgOut.getOrgNo() == null ? "" : umsMsgOut.getOrgNo());// 所属组织
                rowlist.add(umsMsgOut.getAppId() == null ? "" : umsMsgOut.getAppId());// 所属应用
                rowlist.add(umsMsgOut.getMediaId() == null ? "" : umsMsgOut.getMediaId());// 所属运营商
                rowlist.add(umsMsgOut.getFlowNo() == null ? "" : umsMsgOut.getFlowNo());// 流程号
                rowlist.add(umsMsgOut.getCreateUser() == null ? "" : umsMsgOut.getCreateUser());// 生成人员              
                rowlist.add(umsMsgOut.getStatus() == null ? "" : umsMsgOut.getStatus());// 状态
                rowlist.add(umsMsgOut.getGmtModified() == null ? "" : DateHelper
                    .getStrDateByFormat(umsMsgOut.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));// 操作时间
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
            logger.error("写入数据短信导出表excel sheet出错：" + e);
        } finally {
            try {
                fos.close();
            } catch (Exception e2) {
                logger.error("关闭查询统计数据短信输出文件流FileOutputStream出错：" + e2);
            }
        }
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
