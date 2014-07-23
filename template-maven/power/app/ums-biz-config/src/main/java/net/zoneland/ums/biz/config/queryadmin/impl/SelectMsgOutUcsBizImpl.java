/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.queryadmin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.queryadmin.SelectMsgOutUcsBiz;
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
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author yangjuanying
 * @version $Id: SelectMsgOutUcsBizImpl.java, v 0.1 2012-12-11 下午9:45:40 yangjuanying Exp $
 */
public class SelectMsgOutUcsBizImpl implements SelectMsgOutUcsBiz {

    private static final Logger  logger = Logger.getLogger(SelectMsgOutUcsBizImpl.class);

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
     * 条件分页【短信查询数据短信】
     * 
     * @see net.zoneland.ums.biz.config.queryadmin.SelectMsgOutUcsBiz#selectMsgOutUcs(net.zoneland.ums.common.dal.bo.AppMsgInfoBO, int)
     */
    public PageResult<UmsMsgOut> selectMsgOutUcs(AppMsgInfoBO bo, int curPage) {
        logger.debug("开始【短信查询数据短信】");
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", bo.getRecvId());// 接收方手机号
        params.put("sendId", bo.getSendId());// 发送方手机号
        params.put("status", bo.getStatus());// 状态
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        int total = 0;
        PageResult<UmsMsgOut> result = null;
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
            result = new PageResult<UmsMsgOut>(total, curPage);
            return result;
        }
        total = umsMsgOutUcsMapper.queryMsgOutUcsNum(params);// 记录总数量
        result = new PageResult<UmsMsgOut>(total, curPage);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgOut> msgList = umsMsgOutUcsMapper.queryMsgOutUcsByPage(params);// 分页显示【短信查询数据短信】
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
}
