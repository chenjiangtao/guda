/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in.impl;

import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.biz.msg.in.OutReplyService;
import net.zoneland.ums.common.dal.UmsOutAppReplyMapper;
import net.zoneland.ums.common.dal.UmsOutReplyMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: OutReplyServiceImpl.java, v 0.1 2012-8-29 下午1:40:53 gag Exp $
 */
public class OutReplyServiceImpl implements OutReplyService {

    private static final Logger  logger = Logger.getLogger(OutReplyServiceImpl.class);

    @Autowired
    private UmsOutReplyMapper    umsOutReplyMapper;

    @Autowired
    private UmsOutAppReplyMapper umsOutAppReplyMapper;

    /** 
     * @see net.zoneland.ums.biz.msg.in.OutReplyService#saveOutReply(net.zoneland.ums.common.dal.dataobject.UmsOutReply)
     */
    public boolean saveOutReplyByMsg(UmsMsgOut umsMsgOut) {
        UmsOutReply umsOutReply = new UmsOutReply();
        ObjectBuilder.copyObject(umsMsgOut, umsOutReply);
        try {
            int result = umsOutReplyMapper.insert(umsOutReply);
            return result == 1;
        } catch (Exception e) {
            logger.error("保存回复号的时候出现错误！", e);
            return false;
        }
    }

    public boolean saveOutAppReplyByMsg(UmsMsgOut umsMsgOut) {
        UmsOutReply umsOutReply = new UmsOutReply();
        ObjectBuilder.copyObject(umsMsgOut, umsOutReply);
        try {
            int result = umsOutAppReplyMapper.insert(umsOutReply);
            return result == 1;
        } catch (Exception e) {
            logger.error("保存回复号的时候出现错误！", e);
            return false;
        }
    }

    /** 
     * @see net.zoneland.ums.biz.msg.in.OutReplyService#getReplyDes(net.zoneland.ums.common.dal.dataobject.UmsMsgOut)
     */
    public UmsOutReply getOutReplyByMsg(UmsMsgOut umsMsgOut) {
        if (umsMsgOut.getAck() == 1 || umsMsgOut.getAck() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", umsMsgOut.getAppId());
        map.put("reply", umsMsgOut.getReply());
        map.put("subApp", umsMsgOut.getSubApp());
        UmsOutReply umsOutReply = null;
        try {
            umsOutReply = umsOutReplyMapper.selectByReplyAppIdSubAppId(map);
        } catch (Exception e) {
            logger.error("查询回复号的时候出现错误！", e);
        }
        if (umsOutReply != null) {
            return umsOutReply;
        }
        saveOutReplyByMsg(umsMsgOut);
        try {
            umsOutReply = umsOutReplyMapper.selectByReplyAppIdSubAppId(map);
        } catch (Exception e) {
            logger.error("查询回复号的时候出现错误！", e);
        }
        return umsOutReply;
    }

    /** 
     * @see net.zoneland.ums.biz.msg.in.OutReplyService#findByReplyNum(java.lang.String, java.lang.String)
     */
    public UmsOutReply findByReplyNum(String replyDes) {
        if (replyDes == null) {
            return null;
        }
        Integer res = Integer.valueOf(replyDes);

        return umsOutReplyMapper.selectByReplyNum(res);
    }

    //
    //    public void setUmsOutReplyMapper(UmsOutReplyMapper umsOutReplyMapper) {
    //        this.umsOutReplyMapper = umsOutReplyMapper;
    //    }
    //
    //    /** 
    //     * @see net.zoneland.ums.biz.msg.in.OutReplyService#delOutReply(java.lang.String, java.lang.String)
    //     */
    //    public int delOutReply(String replyDes, String recvId) {
    //        if (replyDes == null) {
    //            return 0;
    //        }
    //        Map<String, String> params = new HashMap<String, String>(8);
    //        params.put("replyDes", replyDes);
    //        params.put("receiveId", recvId);
    //        return umsOutReplyMapper.delOutReply(params);
    //    }

    /** 
     * @see net.zoneland.ums.biz.msg.in.OutReplyService#getOutAppReplyByMsg(net.zoneland.ums.common.dal.dataobject.UmsMsgOut)
     */
    public UmsOutReply getOutAppReplyByMsg(UmsMsgOut umsMsgOut) {
        if (umsMsgOut.getAck() == 1 || umsMsgOut.getAck() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", umsMsgOut.getAppId());
        map.put("subApp", umsMsgOut.getSubApp());
        UmsOutReply umsOutReply = null;
        //        try {
        //            umsOutReply = umsOutAppReplyMapper.selectByReplyAppIdSubAppId(map);
        //        } catch (Exception e) {
        //            logger.error("查询回复号的时候出现错误！", e);
        //        }
        //        if (umsOutReply != null) {
        //            return umsOutReply;
        //        }
        //删除七天前的回复号，保证回复号可循环
        umsOutAppReplyMapper.deleteLastWeekReply(DateHelper.getLastDay());
        saveOutAppReplyByMsg(umsMsgOut);
        try {
            umsOutReply = umsOutAppReplyMapper.selectByReplyAppIdSubAppId(map);
        } catch (Exception e) {
            logger.error("查询回复号的时候出现错误！", e);
        }
        return umsOutReply;
    }

    /** 
     * @see net.zoneland.ums.biz.msg.in.OutReplyService#findByAppReplyNum(java.lang.String)
     */
    public UmsOutReply findByAppReplyNum(String replyDes) {
        if (replyDes == null) {
            return null;
        }
        Integer res = Integer.valueOf(replyDes);

        return umsOutAppReplyMapper.selectByReplyNum(res);
    }

}
