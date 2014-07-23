/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.msg.in.MsgInService;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.MSExcelHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

/**
 *
 * @author gag
 * @version $Id: MsgInServiceImpl.java, v 0.1 2012-8-24 下午6:02:39 gag Exp $
 */
public class MsgInServiceImpl implements MsgInService {

    private static final Logger  logger = Logger.getLogger(MsgInServiceImpl.class);

    @Autowired
    private UmsMsgInMapper       umsMsgInMapper;

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    /**
     * @see net.zoneland.ums.biz.msg.in.MsgInService#fetchMsgSuccess(java.lang.String, java.lang.String)
     */
    public boolean fetchMsgSuccess(String batchNo, String serialNo) {
        if (batchNo == null || serialNo == null) {
            return false;
        }
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("status", MsgInStatusEnum.success.getValue());
        params.put("gmtModified", new Date());
        params.put("batchNo", batchNo);
        params.put("serialNo", serialNo);
        umsMsgInMapper.updateByBatchNo(params);
        //if (rows == 1) {
        return true;
        //        } else {
        //            logger.error("根据batchNo,serial更新消息记录为success，返回记录数量不为1.batchNo:[" + batchNo
        //                         + "],serialNo:[" + serialNo + "].");
        //        }
        //        return false;
    }

    /**
     * @see net.zoneland.ums.biz.msg.in.MsgInService#fetchAndUpdateMsgIn(java.lang.String, java.lang.String)
     */
    public List<UmsMsgIn> fetchMsgIn(String appId, String subAppId, String status, int maxCount) {
        if (appId == null) {
            return Collections.emptyList();

        }
        Map<String, Object> params = new HashMap<String, Object>(8);
        params.put("appId", appId);
        params.put("subAppId", subAppId);
        params.put("maxCount", maxCount);
        List<UmsMsgIn> result = Collections.emptyList();
        result = umsMsgInMapper.selectByAppWithLimit(params);
        Iterator<UmsMsgIn> umsInIt = result.iterator();
        while (umsInIt.hasNext()) {
            UmsMsgIn umsIn = umsInIt.next();
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("id", umsIn.getId());
            p.put("status", status);
            p.put("docount", umsIn.getDocount() + 1);
            p.put("gmtModified", new Date());
            int c = umsMsgInMapper.updateStatusByPrimaryKey(p);

            if (c != 1) {
                throw new DataSourceLookupFailureException("更新ums_msg_in记录" + umsIn.getId()
                                                           + "状态为发送返回不等于1");
            }
        }
        return result;

    }

    /**
     * @see net.zoneland.ums.biz.msg.in.MsgInService#saveMsgIn(net.zoneland.ums.common.dal.dataobject.UmsMsgIn)
     */
    public boolean saveMsgIn(UmsMsgIn umsMsgIn) {
        umsMsgIn.setId(UUID.randomUUID().toString());
        umsMsgIn.setGmtCreated(new Date());
        umsMsgIn.setGmtModified(new Date());
        int res = umsMsgInMapper.insert(umsMsgIn);
        return res == 1;
    }

    public boolean saveMsgIn(String batchNo, String serialNo, String appId, String subApp,
                             String appSerialNo, String mediaId, String sendId, String recvId,
                             String content, int ack, String reply, int msgType, String retCode,
                             String errMsg) {
        UmsMsgIn u = new UmsMsgIn();
        u.setAck(ack);
        u.setAppId(appId);
        u.setAppSerialNo(appSerialNo);
        u.setBatchNo(batchNo);
        u.setContent(content);
        u.setDocount(0);
        u.setErrorMsg(errMsg);
        u.setGmtCreated(new Date());
        u.setGmtModified(new Date());
        u.setId(UUID.randomUUID().toString());
        u.setMediaId(mediaId);
        u.setMsgType(msgType);
        u.setRecvId(recvId);
        u.setReply(reply);
        u.setRetCode(retCode);
        u.setSendId(sendId);
        u.setSerialNo(serialNo);
        u.setStatus("0");
        u.setSubApp(subApp);
        if (logger.isInfoEnabled()) {
            logger.info("保存上行数据:" + u);
        }
        int res = umsMsgInMapper.insert(u);
        return res == 1;
    }

    public void setUmsMsgInMapper(UmsMsgInMapper umsMsgInMapper) {
        this.umsMsgInMapper = umsMsgInMapper;
    }

    /** 
     * 条件分页查询统计上行短信
     * 
     * @see net.zoneland.ums.biz.msg.in.MsgInService#searchMsgIn(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, int)
     */
    public PageResult<UmsMsgIn> searchMsgIn(AppMsgInfoBO bo, int pageId) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询统计上行短信");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());// 发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        int total = 0;
        PageResult<UmsMsgIn> result = null;
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
        if (appNameFlag || gatewaylistFlag) {
            result = new PageResult<UmsMsgIn>(total, pageId);
            return result;
        }
        total = umsMsgInMapper.searchMsgInNum(params);// 记录总数量
        result = new PageResult<UmsMsgIn>(total, pageId);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgIn> msgInList = umsMsgInMapper.searchMsgInByPage(params);// 分页显示上行短信
        for (int i = 0; i < msgInList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgInList.get(i).getAppId());
            msgInList.get(i).setAppId(info == null ? "" : info.getAppName());
            UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(msgInList
                .get(i).getMediaId());// 获得渠道类型，将运营商ID转换为运营商姓名显示
            if (umsGateWayInfo != null) {
                msgInList.get(i).setMediaId(GateEnum.getDescription(umsGateWayInfo.getType()));
            } else {
                msgInList.get(i).setMediaId("");
            }
            msgInList.get(i)
                .setStatus(MsgInStatusEnum.getDescription(msgInList.get(i).getStatus()));
        }
        result.setResults(msgInList);
        return result;
    }

    /** 
     * 查询统计上行短信并导出excel表
     * 
     * @see net.zoneland.ums.biz.msg.in.MsgInService#exportMsgInExcel(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, java.lang.String)
     */
    public void exportMsgInExcel(AppMsgInfoBO bo, String path) {
        logger.debug("开始统计上行短信，并导出统计excel");
        Map<String, Object> params = new HashMap<String, Object>();// 条件map
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());//发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("orderBy", "GMT_MODIFIED");
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
        if (appNameFlag || gatewaylistFlag) {
            List<UmsMsgIn> elist = new ArrayList<UmsMsgIn>();
            UmsMsgIn msgIn = new UmsMsgIn();
            elist.add(msgIn);
            packageExcelMsgInlist(path, elist);
        } else {
            List<UmsMsgIn> list = umsMsgInMapper.searchMsgInforExport(params);// 条件查询后
            if (list != null && list.size() > 0) {
                List<UmsMsgIn> elist = new ArrayList<UmsMsgIn>();
                Iterator<UmsMsgIn> it = list.iterator();
                while (it.hasNext()) {
                    UmsMsgIn msgIn = it.next();
                    UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(msgIn.getAppId());
                    msgIn.setAppId(info == null ? "" : info.getAppName());
                    UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(msgIn
                        .getMediaId());// 获得渠道类型，将运营商ID转换为运营商姓名显示
                    if (umsGateWayInfo != null) {
                        msgIn.setMediaId(GateEnum.getDescription(umsGateWayInfo.getType()));
                    } else {
                        msgIn.setMediaId("");
                    }
                    msgIn.setStatus(MsgInStatusEnum.getDescription(msgIn.getStatus()));
                    elist.add(msgIn);
                }
                packageExcelMsgInlist(path, elist);
            } else {
                List<UmsMsgIn> elist = new ArrayList<UmsMsgIn>();
                UmsMsgIn msgIn = new UmsMsgIn();
                elist.add(msgIn);
                packageExcelMsgInlist(path, elist);
            }
        }
    }

    /**
     * 将查询统计上行短信的结果输出到excel
     * 
     * @param path
     * @param elist
     */
    private void packageExcelMsgInlist(String path, List<UmsMsgIn> list) {
        List<List<Object>> sheet1 = new ArrayList<List<Object>>();
        List<Object> totallist = new ArrayList<Object>();
        totallist.add("发送方手机号");
        totallist.add("接收方手机号");
        totallist.add("短信内容");
        totallist.add("所属应用");
        totallist.add("所属运营商");
        totallist.add("状态");
        totallist.add("操作时间");
        sheet1.add(totallist);
        if (list != null && list.size() > 0) {
            Iterator<UmsMsgIn> it = list.iterator();
            while (it.hasNext()) {
                UmsMsgIn umsMsgIn = it.next();
                List<Object> rowlist = new ArrayList<Object>();
                rowlist.add(umsMsgIn.getSendId() == null ? "" : umsMsgIn.getSendId());// 发送方手机号           
                rowlist.add(umsMsgIn.getRecvId() == null ? "" : umsMsgIn.getRecvId());// 接收方手机号
                rowlist.add(umsMsgIn.getContent() == null ? "" : umsMsgIn.getContent());// 短信内容  
                rowlist.add(umsMsgIn.getAppId() == null ? "" : umsMsgIn.getAppId());// 所属应用
                rowlist.add(umsMsgIn.getMediaId() == null ? "" : umsMsgIn.getMediaId());// 所属运营商              
                rowlist.add(umsMsgIn.getStatus() == null ? "" : umsMsgIn.getStatus());// 状态
                rowlist.add(umsMsgIn.getGmtModified() == null ? "" : DateHelper.getStrDateByFormat(
                    umsMsgIn.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));// 操作时间
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
            logger.error("写入上行短信导出表excel sheet出错：" + e);
        } finally {
            try {
                fos.close();
            } catch (Exception e2) {
                logger.error("关闭查询统计上行短信输出文件流FileOutputStream出错：" + e2);
            }
        }
    }

}
