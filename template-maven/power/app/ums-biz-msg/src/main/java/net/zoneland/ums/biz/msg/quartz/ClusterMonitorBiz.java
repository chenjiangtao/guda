/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsLockAppMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.util.cluster.MonitorCluster;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 * @version $Id: ClusteMonitorBiz.java, v 0.1 2013-1-15 上午10:55:04 Administrator Exp $
 */
public class ClusterMonitorBiz {

    private static final Logger logger       = LoggerFactory.getLogger("UMS-QUARTZ");

    private String              clusterHosts = "localhost:8080";

    @Autowired
    private UmsLockAppMapper    umsLockAppMapper;
    @Autowired
    private UmsMsgOutMapper     umsMsgOutMapper;
    @Autowired
    private UmsMsgOutUcsMapper  umsMsgOutUcsMapper;

    public void ClusterMoitor() {
        if (clusterHosts == null) {
            return;
        }
        String[] hosts = clusterHosts.split("-");
        for (int i = 0, len = hosts.length; i < len; i++) {
            String host = hosts[i];
            if (MonitorCluster.monitor(host)) {
                logger.info(host + "正常运行！");
                continue;
            }
            boolean errorResult = false;
            for (int j = 0; j < 5; j++) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
                errorResult = MonitorCluster.monitor(host);
                logger.info(host + "服务器出现问题！");
                if (errorResult) {
                    logger.info(host + "服务器恢复正常！");
                    break;
                }
            }
            if (!errorResult) {
                Date date = new Date();
                //去掉端口
                host = host.split(":")[0];
                logger.info("执行锁表的删除操作！" + host);
                Map<String, Object> lockMap = new HashMap<String, Object>();
                lockMap.put("host", host);
                lockMap.put("gmtCreated", date);
                int result = umsLockAppMapper.deleteByHost(lockMap);
                logger.info("删除" + result + "条记录！");
                logger.info("执行队列状态的更新：" + host);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", MsgInfoStatusEnum.ready.getValue());
                map.put("targetStatus", MsgInfoStatusEnum.init.getValue());
                map.put("host", host);
                map.put("gmtModified", date);
                result = umsMsgOutUcsMapper.updateStatusByHost(map);
                result = result + umsMsgOutMapper.updateStatusByHost(map);
                logger.info("更新" + host + ":" + result + "条记录！");
            }
        }
    }

    public String getClusterHosts() {
        return clusterHosts;
    }

    public void setClusterHosts(String clusterHosts) {
        this.clusterHosts = clusterHosts;
    }

    public UmsLockAppMapper getUmsLockAppMapper() {
        return umsLockAppMapper;
    }

    public void setUmsLockAppMapper(UmsLockAppMapper umsLockAppMapper) {
        this.umsLockAppMapper = umsLockAppMapper;
    }

}
