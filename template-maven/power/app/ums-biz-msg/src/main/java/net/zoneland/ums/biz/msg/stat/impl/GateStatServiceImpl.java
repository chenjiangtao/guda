/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.stat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.msg.stat.GateStatService;
import net.zoneland.ums.biz.msg.stat.bo.GateStatBo;
import net.zoneland.ums.biz.msg.stat.bo.UmsStatVo;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsStatMapper;
import net.zoneland.ums.common.dal.dataobject.UmsStat;
import net.zoneland.ums.common.util.enums.msg.MsgTypeEnum;
import net.zoneland.ums.common.util.page.PageResult;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gang
 * @version $Id: GateStatServiceImpl.java, v 0.1 2013-3-26 下午4:20:24 gang Exp $
 */
public class GateStatServiceImpl implements GateStatService {

    @Autowired
    private UmsStatMapper    umsStatMapper;

    @Autowired
    private UmsAppInfoMapper umsAppInfoMapper;

    /** 
     * @see net.zoneland.ums.biz.msg.stat.GateStatService#searchGateStat(net.zoneland.ums.biz.msg.stat.bo.GateStatBo)
     */
    public PageResult<UmsStatVo> searchGateStat(GateStatBo bo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("msgType", bo.getType());
        int total = umsStatMapper.searchCountByPage(params);
        if (total > 0) {
            PageResult<UmsStatVo> result = new PageResult<UmsStatVo>(total, bo.getPageId());
            params.put("first", result.getFirstrecode());
            params.put("end", result.getEndrecode());
            List<UmsStat> results = umsStatMapper.searchByPage(params);
            Iterator<UmsStat> it = results.iterator();
            List<UmsStatVo> r = new ArrayList<UmsStatVo>(results.size());
            while (it.hasNext()) {
                UmsStat stat = it.next();
                stat.setMsgType(MsgTypeEnum.valueOf(stat.getMsgType()).getDescription());
                UmsStatVo v = new UmsStatVo(stat);
                v.setAppName(umsAppInfoMapper.getAppNameByAppId(stat.getAppId()));
                r.add(v);
            }
            result.setResults(r);
            return result;
        } else {
            return new PageResult<UmsStatVo>(total, bo.getPageId());

        }
    }

    /** 
     * @see net.zoneland.ums.biz.msg.stat.GateStatService#searchGateStatForExport(net.zoneland.ums.biz.msg.stat.bo.GateStatBo)
     */
    public List<UmsStatVo> searchGateStatForExport(GateStatBo bo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", bo.getStartTime());
        params.put("endTime", bo.getEndTime());
        params.put("msgType", bo.getType());
        List<UmsStat> results = umsStatMapper.searchByTime(params);
        Iterator<UmsStat> it = results.iterator();
        List<UmsStatVo> r = new ArrayList<UmsStatVo>(results.size());
        while (it.hasNext()) {
            UmsStat stat = it.next();
            stat.setMsgType(MsgTypeEnum.valueOf(stat.getMsgType()).getDescription());
            UmsStatVo v = new UmsStatVo(stat);
            v.setAppName(umsAppInfoMapper.getAppNameByAppId(stat.getAppId()));
            r.add(v);
        }
        return r;
    }

}
