/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.queryadmin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.queryadmin.SelectMsgInBiz;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author yangjuanying
 * @version $Id: SelectMsgInBizImpl.java, v 0.1 2012-12-12 上午10:40:47 yangjuanying Exp $
 */
public class SelectMsgInBizImpl implements SelectMsgInBiz {

    private static final Logger  logger = Logger.getLogger(SelectMsgInBizImpl.class);

    @Autowired
    private UmsMsgInMapper       umsMsgInMapper;

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    /** 
     * 条件分页【短信查询上行短信】
     * 
     * @see net.zoneland.ums.biz.config.queryadmin.SelectMsgInBiz#selectMsgIn(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, int)
     */
    public PageResult<UmsMsgIn> selectMsgIn(AppMsgInfoBO bo, int pageId) {
        logger.debug("开始【短信查询上行短信】");
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
        if (appNameFlag) {
            result = new PageResult<UmsMsgIn>(total, pageId);
            return result;
        }
        total = umsMsgInMapper.queryMsgInNum(params);// 记录总数量
        result = new PageResult<UmsMsgIn>(total, pageId);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgIn> msgInList = umsMsgInMapper.queryMsgInByPage(params);// 分页显示短信查询上行短信
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
}
