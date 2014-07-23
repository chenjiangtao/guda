/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.init;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.biz.msg.process.MessageProcess;
import net.zoneland.ums.common.dal.UmsLockAppMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ypz
 * @version $Id: SendWaitMsgBiz.java, v 0.1 2012-9-5 下午03:45:32 ypz Exp $
 */
public class SendWaitMsgBiz {
    /**
    * Logger for this class
    */
    private static final Log   logger = LogFactory.getLog(SendWaitMsgBiz.class);

    //    private final static int   PAGENUM = 10000;

    private String             dealInitServer;
    @Autowired
    private MessageProcess     messageProcess;
    @Autowired
    private UmsMsgOutMapper    umsMsgOutMapper;
    @Autowired
    private UmsMsgOutUcsMapper umsMsgOutUcsMapper;

    @Autowired
    private UmsLockAppMapper   umsLockAppMapper;

    /**
     * 指定应用服务器启动时更新本机队列状态的消息！
     */
    public void init() throws Exception {
        try {
            String hostName = InetAddress.getLocalHost().getHostAddress();
            logger.info("当前服务器hostname：" + hostName);
            //            if (StringUtils.isEmpty(dealInitServer)) {
            //                logger.warn("请指定服务器启动时发送初始化的消息的hostname");
            //                return;
            //            }
            //            if (dealInitServer.indexOf(hostName) == -1) {
            //                return;
            //            }
            Date date = new Date();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", MsgInfoStatusEnum.ready.getValue());
            map.put("targetStatus", MsgInfoStatusEnum.init.getValue());
            map.put("host", hostName);
            map.put("gmtModified", date);
            umsMsgOutUcsMapper.updateStatusByHost(map);
            umsMsgOutMapper.updateStatusByHost(map);
            //根据IP清除锁表记录
            Map<String, Object> lockMap = new HashMap<String, Object>();
            lockMap.put("host", hostName);
            lockMap.put("gmtCreated", date);
            umsLockAppMapper.deleteByHost(lockMap);
        } catch (UnknownHostException e) {
            logger.error("获取当前服务器hostname失败", e);
        }
    }

    public String getDealInitServer() {
        return dealInitServer;
    }

    public void setDealInitServer(String dealInitServer) {
        this.dealInitServer = dealInitServer;
    }

    public MessageProcess getMessageProcess() {
        return messageProcess;
    }

    public void setMessageProcess(MessageProcess messageProcess) {
        this.messageProcess = messageProcess;
    }

    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

    public void setUmsMsgOutUcsMapper(UmsMsgOutUcsMapper umsMsgOutUcsMapper) {
        this.umsMsgOutUcsMapper = umsMsgOutUcsMapper;
    }

    public void setUmsLockAppMapper(UmsLockAppMapper umsLockAppMapper) {
        this.umsLockAppMapper = umsLockAppMapper;
    }

    //    private int getPages(int count) {
    //        if (count % PAGENUM > 0) {
    //            return count / PAGENUM + 1;
    //        } else {
    //            return count / PAGENUM;
    //        }
    //    }
}
