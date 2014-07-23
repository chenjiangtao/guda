/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.biz.config.admin.SystemCheckBiz;
import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 查询系统状态业务类
 * @author XuFan
 * @version $Id: SystemCheck.java, v 0.1 Aug 25, 2012 2:18:35 PM XuFan Exp $
 */
public class SystemCheckBizImpl implements SystemCheckBiz {

    private static final Logger logger = Logger.getLogger(SystemCheckBizImpl.class);

    @Autowired
    private UmsMsgInMapper      umsMsgInMapper;

    @Autowired
    private UmsMsgOutMapper     umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper  umsMsgOutUcsMapper;

    /**
     * 查询数据库中数据，返回系统参数
     *
     * @return
     */
    public Map<String, Integer> getSystemParams() {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询当前系统数据");
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("in_count", umsMsgInMapper.getAllMsgIn());
        map.put("Out_count", umsMsgOutMapper.getAllMsgOut() + umsMsgOutUcsMapper.getAllMsgOut());
        map.put(
            "Queue_count",
            umsMsgOutMapper.getMsgByStatus(MsgInfoStatusEnum.ready.getValue())
                    + umsMsgOutUcsMapper.getMsgByStatus(MsgInfoStatusEnum.ready.getValue()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("status", MsgInfoStatusEnum.success.getValue());
        search.put("date", date);
        map.put(
            "success_count",
            umsMsgOutMapper.getDaySucessCount(search)
                    + umsMsgOutUcsMapper.getDaySucessCount(search));
        map.put("Recv_count", umsMsgInMapper.getMsgInToday(date));
        map.put("Send_count",
            umsMsgOutMapper.getMsgOutNow(date) + umsMsgOutUcsMapper.getMsgOutNow(date));

        return map;
    }
}
