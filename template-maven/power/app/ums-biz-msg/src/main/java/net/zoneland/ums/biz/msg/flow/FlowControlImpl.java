/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsFlowLogMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsFlowLog;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: FlowControlImpl.java, v 0.1 2012-8-27 上午11:19:42 gag Exp $
 */
public class FlowControlImpl implements FlowControl {

	private static final Logger logger = Logger
			.getLogger(FlowControlImpl.class);

	@Autowired
	private UmsAppInfoMapper umsAppInfoMapper;

	@Autowired
	private UmsFlowLogMapper umsFlowLogMapper;

	/**
	 * @see net.zoneland.ums.biz.msg.flow.FlowControl#checkFlow(java.lang.String,
	 *      int)
	 */
	public int checkFlow(String appId, int msgCount) {
		if (appId == null || msgCount <= 0) {
			return -1;
		}
		UmsAppInfo app = umsAppInfoMapper.selectAppByAppId(appId);
		if (app == null) {
			return -2;
		}
		Integer flowDay = app.getFlowDay();
		Integer flowMon = app.getFlowMonth();
		if (flowDay == null && flowMon == null) {
			if (logger.isInfoEnabled()) {
				logger.info("app:[" + appId + "]没有配置流量阀值。");
			}
			return -1;
		}
		return checkAndSaveFlow(appId, msgCount, flowDay, flowMon, new Date());
	}

	/**
	 * 检查流量是否超过阀值，先检查天流量，再检查月流量 如果超过阀值，则返回超过的量，否则更新数据库中的流量
	 * 
	 * @param appId
	 * @param msgCount
	 * @param flowDay
	 * @param flowMon
	 * @return
	 */
	private int checkAndSaveFlow(String appId, int msgCount, Integer flowDay,
			Integer flowMon, Date date) {

		int curFlowMon = getMonthFlow(appId, date);
		int curFlowDay = getFlowDay(appId, date);
		int flowD = 0;
		int flowM = 0;

		if (flowDay != null) {
			flowD = curFlowDay + msgCount - flowDay;
		}
		if (flowMon != null) {
			flowM = curFlowMon + msgCount - flowMon;
		}

		if (flowD > 0 || flowM > 0) {
			if (flowD > 0) {
				flowD = Integer.valueOf("1" + String.valueOf(flowD));
				return flowD;
			}
			flowM = Integer.valueOf("2" + String.valueOf(flowM));
			return flowM;
		} else {
			// 记录流量
			createOrUpdateDayFlowLog(appId, msgCount, date);

		}
		return 0;
	}

	private int getMonthFlow(String appId, Date date) {
		Map<String, Object> params = new HashMap<String, Object>(8);
		params.put("appId", appId);
		params.put("startTime", DateHelper.setFirstDay(date));
		params.put("endTime", DateHelper.getEndOfMonth(date));
		List<UmsFlowLog> logs = umsFlowLogMapper.selectByAppIdWithTime(params);
		Iterator<UmsFlowLog> it = logs.iterator();
		int count = 0;
		while (it.hasNext()) {
			UmsFlowLog log = it.next();
			if (log.getFlowToday() != null) {
				count += log.getFlowToday();
			}
		}
		return count;
	}

	private int getFlowDay(String appId, Date sendTime) {
		Map<String, Object> params = new HashMap<String, Object>(6);
		params.put("appId", appId);
		params.put("gmtCreated", sendTime);
		List<UmsFlowLog> logs = umsFlowLogMapper.selectByAppId(params);
		Iterator<UmsFlowLog> it = logs.iterator();
		while (it.hasNext()) {
			UmsFlowLog log = it.next();
			if (log != null && log.getFlowToday() != null) {
				return log.getFlowToday();
			}
		}
		return 0;
	}

	private int getInt(Integer num) {
		if (num == null) {
			return 0;
		}
		return num.intValue();
	}

	/**
	 * 新建一条只有日流量的记录。
	 * 
	 * @param appId
	 * @param flow
	 * @param date
	 */
	private void createOrUpdateDayFlowLog(String appId, int flow, Date date) {
		Map<String, Object> params = new HashMap<String, Object>(6);
		params.put("appId", appId);
		params.put("gmtCreated", date);
		List<UmsFlowLog> logs = umsFlowLogMapper.selectByAppId(params);
		if (logs.size() == 0) {
			UmsFlowLog flowLog = new UmsFlowLog();
			flowLog.setId(UUID.randomUUID().toString());
			flowLog.setAppId(appId);
			flowLog.setFlowToday(flow);
			flowLog.setFlowMonTotal(null);
			flowLog.setGmtCreated(date);
			umsFlowLogMapper.insert(flowLog);
		} else {
			UmsFlowLog log = logs.get(0);
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("id", log.getId());
			p.put("flowToday", getInt(log.getFlowToday()) + flow);
			umsFlowLogMapper.updateFlow(p);

		}
	}

	/**
	 * @see net.zoneland.ums.biz.msg.flow.FlowControl#checkFlowWithSendTime(java.lang.String,
	 *      int, java.util.Date)
	 */
	public int checkFlowWithSendTime(String appId, int msgCount, Date sendTime) {
		if (appId == null || msgCount <= 0) {
			return -1;
		}
		if (sendTime == null) {
			return checkFlow(appId, msgCount);
		}
		UmsAppInfo app = umsAppInfoMapper.selectAppByAppId(appId);
		Integer flowDay = app.getFlowDay();
		Integer flowMon = app.getFlowMonth();
		if (flowDay == null && flowMon == null) {
			if (logger.isInfoEnabled()) {
				logger.info("app:[" + appId + "]没有配置流量阀值。");
			}
			return -1;
		}
		return checkAndSaveFlow(appId, msgCount, flowDay, flowMon, sendTime);

	}

	public void setUmsFlowLogMapper(UmsFlowLogMapper umsFlowLogMapper) {
		this.umsFlowLogMapper = umsFlowLogMapper;
	}

	public void setUmsAppInfoMapper(UmsAppInfoMapper umsAppInfoMapper) {
		this.umsAppInfoMapper = umsAppInfoMapper;
	}

}
