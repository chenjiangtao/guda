/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.quartz;

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

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 * @version $Id: UmsMsgStatBiz.java, v 0.1 2013-1-23 下午2:27:09 Administrator Exp $
 */
public class UmsMsgStatBiz {

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

    public void statMsg() {
        //统计前一天数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);// 前一天的日期时间
        Date time = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(time);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", MsgInfoStatusEnum.success.getValue());
        map.put("date", date);
        List<UmsMsgOutStatByGatewayAndAppIdBo> outList = umsMsgOutMapper
            .statMsgByAppIdAndMedia(map);
        outList.addAll(umsMsgOutUcsMapper.statMsgByAppIdAndMedia(map));
        List<UmsGateWayInfo> gatewayList = umsGateWayInfoMapper.findAll();
        Map<String, UmsGateWayInfo> gateWayMap = new HashMap<String, UmsGateWayInfo>();
        //把网关转为Map
        for (UmsGateWayInfo umsGateWayInfo : gatewayList) {
            gateWayMap.put(umsGateWayInfo.getId(), umsGateWayInfo);
        }
        Map<String, UmsStat> statMap = new HashMap<String, UmsStat>();
        resolveStatMsg(statMap, outList, gateWayMap, "out", time);
        map.put("date", date);
        //清除掉上行数据！
        statMap.clear();
        List<UmsMsgOutStatByGatewayAndAppIdBo> inList = umsMsgInMapper.statMsgByAppIdAndMedia(map);
        resolveStatMsg(statMap, inList, gateWayMap, "in", time);

    }

    /**
     *把查询出来的数据转换为统计类的对象，然后插入数据库！
     *
     * @param statMap
     * @param list
     * @param gateWayMap
     * @param msgType
     * @param date
     */
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
                newStat.setAppId(statByGatewayAndAppIdBo.getAppId());
                newStat.setStatDate(date);
                newStat.setGmtCreated(new Date());
                newStat.setMsgType(msgType);
                setCount(newStat, type, spNumber, count);
                statMap.put(statByGatewayAndAppIdBo.getAppId(), newStat);
            } else {
                setCount(umsStat, type, spNumber, count);
            }
        }
        Collection<UmsStat> listStat = statMap.values();
        for (UmsStat umsStat : listStat) {
            //把106通道的总量加起来
            umsStat.setStat106(StatAll("106", umsStat));
            //把95598的通道数量加起来
            umsStat.setStat95598(StatAll("95598", umsStat));
            umsStatMapper.insert(umsStat);
        }
    }

    /**
     *把数量加到统计项里，通过spnumber，网关类型，判断加哪项
     *
     * @param umsStat
     * @param type
     * @param spNumber
     * @param count
     */
    private void setCount(UmsStat umsStat, String type, String spNumber, int count) {
        if (GateEnum.CMPP.getValue().equals(type) || GateEnum.CMPP30.getValue().equals(type)) {
            if (spNumber.startsWith("106")) {
                umsStat.setCmpp106(umsStat.getCmpp106() + count);
            }
            if (spNumber.startsWith("95598")) {
                umsStat.setCmpp95598(umsStat.getCmpp95598() + count);
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

    /**
     *按照spnumber计算数量总和
     *
     * @param spNumber
     * @param umsStat
     * @return
     */
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
