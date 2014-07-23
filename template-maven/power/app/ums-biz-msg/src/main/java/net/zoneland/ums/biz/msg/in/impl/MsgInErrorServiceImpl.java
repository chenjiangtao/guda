/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.msg.in.MsgInErrorService;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgInErrorMapper;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInError;
import net.zoneland.ums.common.util.page.PageResult;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gang
 * @version $Id: MsgInErrorServiceImpl.java, v 0.1 2012-8-31 下午8:07:56 gang Exp $
 */
public class MsgInErrorServiceImpl implements MsgInErrorService {

    @Autowired
    private UmsMsgInErrorMapper umsMsgInErrorMapper;

    @Autowired
    private UmsAppInfoMapper    umsAppInfoMapper;

    public int saveMsgInError(String sendNumber, String recvNumber, String recvMsg, String mediaId,
                              int data_code) {
        UmsMsgInError inError = new UmsMsgInError();
        inError.setId(UUID.randomUUID().toString());
        inError.setSendId(sendNumber);
        inError.setRecvId(recvNumber);
        inError.setContent(recvMsg);
        inError.setMsgType(data_code);
        inError.setMediaId(mediaId);
        inError.setAck(0);
        inError.setGmtCreated(new Date());
        return umsMsgInErrorMapper.insert(inError);
    }

    public void setUmsMsgInErrorMapper(UmsMsgInErrorMapper umsMsgInErrorMapper) {
        this.umsMsgInErrorMapper = umsMsgInErrorMapper;
    }

    /**
     * 分页查询上行错误信息表.
     * 
     * @see net.zoneland.ums.biz.msg.in.MsgInErrorService#findAll(int, int)
     */
    public PageResult<UmsMsgInError> findAll(int curPage, AppMsgInfoBO msgInErrorBo) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 查询条件
        params.put("recvId", msgInErrorBo.getRecvId());// 接收方手机号
        params.put("content", msgInErrorBo.getContent());// 短信内容
        params.put("sendId", msgInErrorBo.getSendId());// 发送方手机号
        // 按应用名查询
        String appName = msgInErrorBo.getAppName();
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
        int total = 0;
        PageResult<UmsMsgInError> result = null;
        if (appNameFlag) {
            result = new PageResult<UmsMsgInError>(total, curPage);
            return result;
        }
        total = umsMsgInErrorMapper.searchMsgInErrorNum(params);// 按条件查出上行错误消息的总数
        result = new PageResult<UmsMsgInError>(total, curPage);
        params.put("start", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgInError> errorList = umsMsgInErrorMapper.searchMsgInErrorByPage(params);// 按条件分页查出上行错误消息
        for (int i = 0; i < errorList.size(); i++) {
            UmsAppInfo info = umsAppInfoMapper.selectAppByAppId(errorList.get(i).getAppId());
            errorList.get(i).setAppId(info == null ? "" : info.getAppName());
        }
        result.setResults(errorList);
        return result;
    }

}
