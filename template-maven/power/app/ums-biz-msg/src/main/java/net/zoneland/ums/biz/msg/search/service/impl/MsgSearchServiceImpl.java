/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.search.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.appadmin.AppInfoService;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAreaMapper;
import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.bo.UmsMsgOutBo;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutIphone;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.enums.msg.MsgInStatusEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.enums.msg.MsgStatusIphoneEnum;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author wangyong
 * @version $Id: MsgSearchServiceImpl.java, v 0.1 2012-8-22 下午9:20:10 wangyong Exp $
 */
public class MsgSearchServiceImpl implements MsgSearchService {

    private final static Logger  logger = Logger.getLogger(MsgSearchServiceImpl.class);

    @Autowired
    private UmsMsgOutMapper      umsMsgOutMapper;

    @Autowired
    private UmsMsgInMapper       umsMsgInMapper;

    @Autowired
    private UmsUserInfoMapper    umsUserInfoMapper;

    @Autowired
    private UmsMsgOutUcsMapper   umsMsgOutUcsMapper;

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    @Autowired
    private UmsAreaMapper        umsAreaMapper;

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    @Autowired
    private AppInfoService       appInfoService;

    @Autowired
    private UmsContactMapper     umsContactMapper;

    /**
     * 精确分页查询消息
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#searchMsgInfoByPage(net.zoneland.ums.common.dal.bo.UmsMsgOutBo, int)
     */
    public PageResult<UmsMsgOutDO> searchMsgInfoByPage(UmsMsgOutBo bo, int curPage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询消息表");
        }
        if (curPage == 0) {
            curPage = 1;
        }
        int total = umsMsgOutMapper.searchAllNum(bo);
        PageResult<UmsMsgOutDO> result = new PageResult<UmsMsgOutDO>(total, curPage);
        PageSearch ps = new PageSearch(bo, result.getFirstrecode(), result.getEndrecode());
        List<UmsMsgOutDO> list = umsMsgOutMapper.searchMyMsgByPage(ps);
        if (list != null && list.size() > 0) {
            for (UmsMsgOutDO umsMsgOut : list) {
                umsMsgOut.setStatus(MsgInfoStatusEnum.getDescription(umsMsgOut.getStatus()));
                if (StringUtils.hasText(umsMsgOut.getRecvId())) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", bo.getUserId());
                    map.put("phone", umsMsgOut.getRecvId());
                    UmsContact umsContact = umsContactMapper.searchByUserIdAndPhone(map);
                    if (umsContact != null) {
                        umsMsgOut.setRecvName(umsContact.getUserName());
                    } else {
                        List<UmsUserInfo> umsUserInfos = new ArrayList<UmsUserInfo>();
                        umsUserInfos = umsUserInfoMapper.getUsersByPhone(umsMsgOut.getRecvId());
                        if (umsUserInfos != null && umsUserInfos.size() > 0) {
                            umsMsgOut.setRecvName(umsUserInfos.get(0).getUserName());
                        }
                    }
                }
            }
            result.setResults(list);
        }
        return result;
    }

    /**
     * 查询发消息
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#searchSendMsgInfoByPage(net.zoneland.ums.common.dal.bo.UmsMsgOutBo, int)
     */
    public PageResult<UmsMsgOutDO> searchSendMsgInfoByPage(UmsMsgOutBo bo, int curPage) {
        List<UmsMsgOutDO> list = new ArrayList<UmsMsgOutDO>();
        String recvId = bo.getRecvId();
        if (StringUtils.hasText(recvId)) {
            recvId = recvId.trim();
        }
        if (!StringUtils.hasText(recvId) && !StringUtils.hasText(bo.getRecvName())) {// 如果输入的是空值
            bo.setRecvId(null);
            PageResult<UmsMsgOutDO> pageResult = searchMsgInfoByPage(bo, curPage);
            list = pageResult.getResults();
            pageResult.setResults(list);
            return pageResult;
        }
        if (!StringUtils.hasText(bo.getRecvName())
            && !StringHelper.trim(recvId).matches("^[0-9]+$")) {// 如果输入手机号是非数字正则表达式则直接返回
            PageResult<UmsMsgOutDO> pageResult = new PageResult<UmsMsgOutDO>();
            pageResult.setResults(list);
            return pageResult;// 查询出的应是空记录，直接返回
        }
        if (!StringUtils.hasText(bo.getRecvName()) && StringRegUtils.isPhoneNumber(recvId)) {// 如果是手机号就精确查询         
            PageResult<UmsMsgOutDO> pageResult = searchMsgInfoByPage(bo, curPage);
            list = pageResult.getResults();
            pageResult.setResults(list);
            return pageResult;
        }
        // 如果不是手机号就模糊匹配 
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("userId", bo.getUserId());
        params.put("recvId", recvId);
        params.put("status", bo.getStatus());
        if (StringUtils.hasText(bo.getRecvName())) {
            List<String> phones = umsUserInfoMapper.getAllUsersByUserName(bo.getRecvName());
            params.put("userName", bo.getRecvName());
            List<String> contactPhones = umsContactMapper.getMyContactsByUserName(params);
            phones.addAll(contactPhones);
            if (phones != null && phones.size() > 0) {
                params.put("recvIdList", phones);
            } else {
                PageResult<UmsMsgOutDO> pageResult = new PageResult<UmsMsgOutDO>();
                return pageResult;
            }
        }
        int total = umsMsgOutMapper.searchAllNumByRecvId(params);// 根据接收方手机号模糊匹配查询出当前用户所有已发送消息总数
        PageResult<UmsMsgOutDO> result = new PageResult<UmsMsgOutDO>(total, curPage);
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        list = umsMsgOutMapper.searchMyMsgByRecvId(params);// 根据接收方手机号模糊匹配查询出当前用户所有已发送消息
        if (list != null && list.size() > 0) {
            for (UmsMsgOutDO umsMsgOut : list) {
                umsMsgOut.setStatus(MsgInfoStatusEnum.getDescription(umsMsgOut.getStatus()));
                if (StringUtils.hasText(umsMsgOut.getRecvId())) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", bo.getUserId());
                    map.put("phone", umsMsgOut.getRecvId());
                    UmsContact umsContact = umsContactMapper.searchByUserIdAndPhone(map);
                    if (umsContact != null) {
                        umsMsgOut.setRecvName(umsContact.getUserName());
                    } else {
                        List<UmsUserInfo> umsUserInfos = new ArrayList<UmsUserInfo>();
                        umsUserInfos = umsUserInfoMapper.getUsersByPhone(umsMsgOut.getRecvId());
                        if (umsUserInfos != null && umsUserInfos.size() > 0) {
                            umsMsgOut.setRecvName(umsUserInfos.get(0).getUserName());
                        }
                    }
                }
            }
        }
        result.setResults(list);
        return result;
    }

    /**
     * 查询接收消息
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#searchAcceptMsgInfoByPage(net.zoneland.ums.common.dal.bo.UmsMsgOutBo, int)
     */
    public PageResult<UmsMsgOutDO> searchAcceptMsgInfoByPage(UmsMsgOutBo bo, int curPage) {
        bo.setStatus(MsgInfoStatusEnum.success.getValue());
        if (curPage == 0) {
            curPage = 1;
        }
        List<UmsMsgOutDO> list = new ArrayList<UmsMsgOutDO>();
        List<String> msgOutList = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("recvId", bo.getRecvId());
        params.put("status", bo.getStatus());
        int total = 0;
        // 根据当前输入发送方姓名模糊匹配查找用户
        if (StringUtils.hasText(bo.getUserName())) {
            List<UmsUserInfo> users = umsUserInfoMapper.getUsersByUserName(StringHelper.trim(bo
                .getUserName()));// 根据当前输入发送方姓名模糊匹配查找用户
            if (users != null && users.size() > 0) {
                for (UmsUserInfo user : users) {
                    msgOutList.add(user.getId());
                }
            }
        }
        boolean msgOutListFlag = false;
        if (msgOutList != null && msgOutList.size() > 0) {
            params.put("msgOutList", msgOutList);
        } else {
            msgOutListFlag = true;
        }
        total = umsMsgOutMapper.searchAllNumByUserId(params);// 根据发送方姓名模糊匹配查询出当前用户所有已接收消息总数
        PageResult<UmsMsgOutDO> result = new PageResult<UmsMsgOutDO>(total, curPage);
        if ((StringUtils.hasText(bo.getUserName()) && msgOutListFlag)) {
            result = new PageResult<UmsMsgOutDO>(0, curPage);
            return result;
        }
        System.out.println(result.getFirstrecode());
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        list = umsMsgOutMapper.searchMyMsgByUserId(params);// 根据发送方姓名模糊匹配查询出当前用户所有已接收消息
        if (list != null && list.size() > 0) {
            for (UmsMsgOutDO umsMsgOut : list) {
                if (StringUtils.hasText(umsMsgOut.getUserId())) {
                    if (!StringRegUtils.isPhoneNumber(umsMsgOut.getUserId())) {
                        UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(umsMsgOut
                            .getUserId());
                        if (umsUserInfo != null && StringUtils.hasText(umsUserInfo.getUserName())) {
                            umsMsgOut.setUserName(umsUserInfo.getUserName());
                            umsMsgOut.setUserId(umsUserInfo.getPhone());
                        }
                    }
                } else {
                    umsMsgOut.setUserId(umsMsgOut.getSendId());
                }
            }
        }
        result.setResults(list);
        return result;
    }

    /**
     * 通过id查询已发送的详细信息以及查询统计页面用户短信的详细信息
     * 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#getMsgInfoById(java.lang.String)
     */
    public UmsMsgOutDO getSendMsgInfoById(String id) {
        UmsMsgOutDO umsMsgOut = umsMsgOutMapper.selectDOByPrimaryKey(id);
        if (umsMsgOut != null) {
            String recvId = umsMsgOut.getRecvId();
            if (!StringRegUtils.isPhoneNumber(recvId)) {
                recvId = getName(recvId);
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(recvId);
                if (umsUserInfo != null) {
                    recvId = recvId + umsUserInfo.getPhone();
                }
                umsMsgOut.setRecvId(recvId);
            }
            umsMsgOut.setStatus(MsgInfoStatusEnum.getDescription(umsMsgOut.getStatus()));
            UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(umsMsgOut
                .getMediaId());
            if (umsGateWayInfo != null) {
                umsMsgOut.setGatewayName(umsGateWayInfo.getGatewayName());
            }
            UmsArea umsArea = umsAreaMapper.findByAreaCode(umsMsgOut.getOrgNo());
            if (umsArea != null) {
                umsMsgOut.setAreaName(umsArea.getAreaName());
            }

        }
        return umsMsgOut;
    }

    /**
     * 数据短信的详细信息
     * 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#getDataMsgInfoById(java.lang.String)
     */
    public UmsMsgOutDO getDataMsgInfoById(String id) {
        UmsMsgOutDO umsMsgOut = umsMsgOutUcsMapper.selectDOByPrimaryKey(id);
        if (umsMsgOut != null) {
            String recvId = umsMsgOut.getRecvId();
            if (!StringRegUtils.isPhoneNumber(recvId)) {
                recvId = getName(recvId);
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(recvId);
                if (umsUserInfo != null) {
                    recvId = recvId + umsUserInfo.getPhone();
                }
                umsMsgOut.setRecvId(recvId);
            }
            umsMsgOut.setStatus(MsgInfoStatusEnum.getDescription(umsMsgOut.getStatus()));
            UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(umsMsgOut
                .getMediaId());
            if (umsGateWayInfo != null) {
                umsMsgOut.setGatewayName(umsGateWayInfo.getGatewayName());
            }
            UmsArea umsArea = umsAreaMapper.findByAreaCode(umsMsgOut.getOrgNo());
            if (umsArea != null) {
                umsMsgOut.setAreaName(umsArea.getAreaName());
            }

        }
        return umsMsgOut;
    }

    /** 
     * 上行短信的详细信息
     * 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#getMsgInById(java.lang.String)
     */
    public UmsMsgIn getMsgInById(String id) {
        UmsMsgIn umsMsgIn = umsMsgInMapper.selectByPrimaryKey(id);
        if (umsMsgIn != null) {
            String recvId = umsMsgIn.getRecvId();
            if (!StringRegUtils.isPhoneNumber(recvId)) {
                recvId = getName(recvId);
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(recvId);
                if (umsUserInfo != null) {
                    recvId = recvId + umsUserInfo.getPhone();
                }
                umsMsgIn.setRecvId(recvId);
            }
            String appName = umsAppInfoMapper.getAppNameByAppId(umsMsgIn.getAppId());
            umsMsgIn.setAppId(appName);
            umsMsgIn.setStatus(MsgInStatusEnum.getDescription(umsMsgIn.getStatus()));
            UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(umsMsgIn
                .getMediaId());
            if (umsGateWayInfo != null) {
                umsMsgIn.setMediaId(umsGateWayInfo.getGatewayName());
            } else {
                umsMsgIn.setMediaId("");
            }
            if (!StringUtils.hasText(umsMsgIn.getErrorMsg())
                || "NULL".equalsIgnoreCase(umsMsgIn.getErrorMsg())) {
                umsMsgIn.setErrorMsg("");
            }
        }
        return umsMsgIn;
    }

    /**
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#getAcceptMsgInfoById(java.lang.String)
     */
    public UmsMsgOutDO getAcceptMsgInfoById(String id) {
        UmsMsgOutDO umsMsgOut = umsMsgOutMapper.selectDOByPrimaryKey(id);
        if (umsMsgOut == null) {
            return umsMsgOut;
        }
        String userId = umsMsgOut.getUserId();
        if (StringUtils.hasText(userId)) {
            UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(userId);
            if (umsUserInfo != null) {
                umsMsgOut.setSendId(umsUserInfo.getUserName());
            }
        }
        if (StringUtils.hasText(umsMsgOut.getAppId())) {
            UmsAppInfo umsAppInfo = appInfoService.findByAppId(umsMsgOut.getAppId());
            umsMsgOut.setApp(umsAppInfo);
        }
        UmsGateWayInfo umsGateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(umsMsgOut
            .getMediaId());
        if (umsGateWayInfo != null) {
            umsMsgOut.setGatewayName(umsGateWayInfo.getGatewayName());
        }
        UmsArea umsArea = umsAreaMapper.findByAreaCode(umsMsgOut.getOrgNo());
        if (umsArea != null) {
            umsMsgOut.setAreaName(umsArea.getAreaName());
        }
        return umsMsgOut;
    }

    private String getName(String id) {
        UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(id);
        String name = id;
        if (umsUserInfo != null) {
            name = umsUserInfo.getUserName();
        }
        return name;
    }

    /** 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#searchByRecvId(java.lang.String, int, int)
     */
    public List<UmsMsgOutIphone> findByRecvId(String recvId, Date sinceTime, int pageId,
                                              int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recvId", recvId);
        params.put("first", (pageId - 1) * pageSize + 1);
        params.put("end", pageId * pageSize);
        params.put("sinceTime", sinceTime);
        return umsMsgOutMapper.searchByRecvId(params);
    }

    /** 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#searchCountByRecvId(java.lang.String)
     */
    public Integer findCountByRecvId(String recvId, Date sinceTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recvId", recvId);
        params.put("sinceTime", sinceTime);
        return umsMsgOutMapper.searchCountByRecvId(params);
    }

    /** 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#delMsg(java.lang.String)
     */
    public boolean delMsg(String id) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("id", id);
        params.put("mobileStatus", MsgStatusIphoneEnum.del.getValue());
        return umsMsgOutMapper.updateMobileStatus(params) == 1;
    }

    /** 
     * @see net.zoneland.ums.biz.msg.search.service.MsgSearchService#setMsgRead(java.lang.String)
     */
    public boolean setMsgRead(String id) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("id", id);
        params.put("mobileStatus", MsgStatusIphoneEnum.read.getValue());
        return umsMsgOutMapper.updateMobileStatus(params) == 1;
    }

    /**
     * Getter method for property <tt>umsMsgOutMapper</tt>.
     * 
     * @return property value of umsMsgOutMapper
     */
    public UmsMsgOutMapper getUmsMsgOutMapper() {
        return umsMsgOutMapper;
    }

    /**
     * Setter method for property <tt>umsMsgOutMapper</tt>.
     * 
     * @param umsMsgOutMapper value to be assigned to property umsMsgOutMapper
     */
    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

    /**
     * Getter method for property <tt>umsUserInfoMapper</tt>.
     * 
     * @return property value of umsUserInfoMapper
     */
    public UmsUserInfoMapper getUmsUserInfoMapper() {
        return umsUserInfoMapper;
    }

    /**
     * Setter method for property <tt>umsUserInfoMapper</tt>.
     * 
     * @param umsUserInfoMapper value to be assigned to property umsUserInfoMapper
     */
    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    /**
     * Getter method for property <tt>umsMsgOutUcsMapper</tt>.
     * 
     * @return property value of umsMsgOutUcsMapper
     */
    public UmsMsgOutUcsMapper getUmsMsgOutUcsMapper() {
        return umsMsgOutUcsMapper;
    }

    /**
     * Setter method for property <tt>umsMsgOutUcsMapper</tt>.
     * 
     * @param umsMsgOutUcsMapper value to be assigned to property umsMsgOutUcsMapper
     */
    public void setUmsMsgOutUcsMapper(UmsMsgOutUcsMapper umsMsgOutUcsMapper) {
        this.umsMsgOutUcsMapper = umsMsgOutUcsMapper;
    }

    /**
     * Getter method for property <tt>umsGateWayInfoMapper</tt>.
     * 
     * @return property value of umsGateWayInfoMapper
     */
    public UmsGateWayInfoMapper getUmsGateWayInfoMapper() {
        return umsGateWayInfoMapper;
    }

    /**
     * Setter method for property <tt>umsGateWayInfoMapper</tt>.
     * 
     * @param umsGateWayInfoMapper value to be assigned to property umsGateWayInfoMapper
     */
    public void setUmsGateWayInfoMapper(UmsGateWayInfoMapper umsGateWayInfoMapper) {
        this.umsGateWayInfoMapper = umsGateWayInfoMapper;
    }

    /**
     * Getter method for property <tt>umsAreaMapper</tt>.
     * 
     * @return property value of umsAreaMapper
     */
    public UmsAreaMapper getUmsAreaMapper() {
        return umsAreaMapper;
    }

    /**
     * Setter method for property <tt>umsAreaMapper</tt>.
     * 
     * @param umsAreaMapper value to be assigned to property umsAreaMapper
     */
    public void setUmsAreaMapper(UmsAreaMapper umsAreaMapper) {
        this.umsAreaMapper = umsAreaMapper;
    }

    /**
     * Getter method for property <tt>appInfoService</tt>.
     * 
     * @return property value of appInfoService
     */
    public AppInfoService getAppInfoService() {
        return appInfoService;
    }

    /**
     * Setter method for property <tt>appInfoService</tt>.
     * 
     * @param appInfoService value to be assigned to property appInfoService
     */
    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    /**
     * Getter method for property <tt>umsMsgInMapper</tt>.
     * 
     * @return property value of umsMsgInMapper
     */
    public UmsMsgInMapper getUmsMsgInMapper() {
        return umsMsgInMapper;
    }

    /**
     * Setter method for property <tt>umsMsgInMapper</tt>.
     * 
     * @param umsMsgInMapper value to be assigned to property umsMsgInMapper
     */
    public void setUmsMsgInMapper(UmsMsgInMapper umsMsgInMapper) {
        this.umsMsgInMapper = umsMsgInMapper;
    }

    public UmsContactMapper getUmsContactMapper() {
        return umsContactMapper;
    }

    public void setUmsContactMapper(UmsContactMapper umsContactMapper) {
        this.umsContactMapper = umsContactMapper;
    }
}
