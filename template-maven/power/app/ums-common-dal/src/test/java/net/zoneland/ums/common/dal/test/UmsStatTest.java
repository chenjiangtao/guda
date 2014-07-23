/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsMsgInMapper;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsStatMapper;
import net.zoneland.ums.common.dal.bo.UmsMsgOutStatByGatewayAndAppIdBo;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsStat;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Administrator
 * @version $Id: UmsStatTest.java, v 0.1 2013-1-23 上午11:58:26 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsStatTest extends BaseDaoTest {
    private static final Logger  logger = LoggerFactory.getLogger("UMS-QUARTZ");

    @Autowired
    private UmsMsgOutMapper      umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper   umsMsgOutUcsMapper;

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    @Autowired
    private UmsStatMapper        umsStatMapper;

    @Autowired
    private UmsMsgInMapper       umsMsgInMapper;

    @Test
    public void statMsg() {
        //统计前一天数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);// 前一天的日期时间
        Date time = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(time);
        System.out.println(date);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", MsgInfoStatusEnum.success.getValue());
        map.put("date", date);
        List<UmsMsgOutStatByGatewayAndAppIdBo> outList = umsMsgOutMapper
            .statMsgByAppIdAndMedia(map);
        outList.addAll(umsMsgOutUcsMapper.statMsgByAppIdAndMedia(map));
        System.out.println(outList);
        List<UmsGateWayInfo> gatewayList = umsGateWayInfoMapper.findAll();
        System.out.println(gatewayList);
        Map<String, UmsGateWayInfo> gateWayMap = new HashMap<String, UmsGateWayInfo>();
        //把网关转为Map
        for (UmsGateWayInfo umsGateWayInfo : gatewayList) {
            gateWayMap.put(umsGateWayInfo.getId(), umsGateWayInfo);
        }
        Map<String, UmsStat> statMap = new HashMap<String, UmsStat>();
        resolveStatMsg(statMap, outList, gateWayMap, "out", time);
        map.put("date", date);
        //把上行数据清除掉！
        statMap.clear();
        List<UmsMsgOutStatByGatewayAndAppIdBo> inList = umsMsgInMapper.statMsgByAppIdAndMedia(map);
        resolveStatMsg(statMap, inList, gateWayMap, "in", time);
        //上行数据操作结束
    }

    private void resolveStatMsg(Map<String, UmsStat> statMap,
                                List<UmsMsgOutStatByGatewayAndAppIdBo> list,
                                Map<String, UmsGateWayInfo> gateWayMap, String msgType, Date date) {
        //把网关类型跟网关SPNumber贴进去,同时根据appId分类,获得上行表的统计数据
        for (UmsMsgOutStatByGatewayAndAppIdBo statByGatewayAndAppIdBo : list) {
            UmsGateWayInfo umsGateWayInfo = gateWayMap.get(statByGatewayAndAppIdBo.getMediaId());
            String type = umsGateWayInfo.getType();
            String spNumber = umsGateWayInfo.getSpNumber();
            int count = statByGatewayAndAppIdBo.getCount();
            if (spNumber == null) {
                continue;
            }
            statByGatewayAndAppIdBo.setMediaType(type);
            statByGatewayAndAppIdBo.setSpNumber(spNumber);
            UmsStat umsStat = statMap.get(statByGatewayAndAppIdBo.getAppId());
            if (umsStat == null) {
                UmsStat newStat = new UmsStat();
                newStat.setId(UUID.randomUUID().toString());
                newStat.setStatDate(date);
                newStat.setGmtCreated(new Date());
                newStat.setMsgType(msgType);
                newStat.setAppId(statByGatewayAndAppIdBo.getAppId());
                setCount(newStat, type, spNumber, count);
                statMap.put(statByGatewayAndAppIdBo.getAppId(), newStat);
            } else {
                setCount(umsStat, type, spNumber, count);
            }
        }
        Collection<UmsStat> listStat = statMap.values();
        for (UmsStat umsStat : listStat) {
            umsStat.setStat106(StatAll("106", umsStat));
            umsStat.setStat95598(StatAll("95598", umsStat));
            System.out.println("*******" + umsStat);
            umsStatMapper.insert(umsStat);
        }
    }

    private void setCount(UmsStat umsStat, String type, String spNumber, int count) {
        if (GateEnum.CMPP.getValue().equals(type) || GateEnum.CMPP30.getValue().equals(type)) {
            if (spNumber.startsWith("106")) {
                umsStat.setCmpp106(umsStat.getCmpp106() + count);
            }
            if (spNumber.startsWith("95598")) {
                umsStat.setCmpp95598(umsStat.getCmpp95598().intValue() + count);
            }
        }
        if (GateEnum.SGIP.getValue().equals(type)) {
            if (spNumber.startsWith("106")) {
                umsStat.setSgip106(umsStat.getSgip106() + count);
            }
            if (spNumber.startsWith("95598")) {
                umsStat.setSgip95598(umsStat.getSgip95598() + count);
            }
        }
        if (GateEnum.SMGP.getValue().equals(type) || GateEnum.SMGP3.getValue().equals(type)) {
            if (spNumber.startsWith("106")) {
                umsStat.setSmgp106(umsStat.getSmgp106() + count);
            }
            if (spNumber.startsWith("95598")) {
                umsStat.setSmgp95598(umsStat.getSmgp95598() + count);
            }
        }
    }

    private int StatAll(String spNumber, UmsStat umsStat) {
        if (spNumber.startsWith("106")) {
            int result = umsStat.getCmpp106() + umsStat.getSgip106() + umsStat.getSmgp106();
            return result;
        }
        if (spNumber.startsWith("95598")) {
            int result = umsStat.getCmpp95598() + umsStat.getSgip95598() + umsStat.getSmgp95598();
            return result;
        }
        return 0;
    }

}
