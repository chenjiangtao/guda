/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.gateway.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.zoneland.ums.biz.config.gateway.GatewayRuleService;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsGatewayRuleMapper;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayRule;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.GateOutProvEnum;
import net.zoneland.ums.common.util.enums.GateStateEnum;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author gag
 * @version $Id: GatewayRuleServiceImpl.java, v 0.1 2012-8-24 上午9:04:43 gag Exp $
 */
public class GatewayRuleServiceImpl implements GatewayRuleService {

    private final static Logger  logger = Logger.getLogger(GatewayRuleServiceImpl.class);

    @Autowired
    private UmsGatewayRuleMapper umsGatewayRuleMapper;

    @Autowired
    private UmsGateWayInfoMapper umsGateWayInfoMapper;

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#findGatewayRule(java.lang.String, java.lang.String, java.lang.String)
     */
    public UmsGateWayInfo findGatewayWithRule(String recvId, String content, String appId) {
        if (recvId == null) {
            return null;
        }
        recvId = recvId.trim();
        List<UmsGateWayInfo> list = new ArrayList<UmsGateWayInfo>();
        UmsGateWayInfo result = null;
        if (StringRegUtils.isMobile(recvId)) {
            if (logger.isInfoEnabled()) {
                logger.info("接收方为移动号码,recvId:" + recvId + ",content:" + content + ",appid:" + appId
                            + ",result:" + result);
            }
            list.addAll(umsGateWayInfoMapper.findWithType(GateEnum.CMPP30.getValue()));
            list.addAll(umsGateWayInfoMapper.findWithType(GateEnum.CMPP.getValue()));
            if (list.size() > 0) {
                int num = new Random().nextInt(1000) % list.size();
                result = list.get(num);
                //如果当前网关是启用失败的且个数是大于1的话 则重新换网关
                if (GateStateEnum.ERROR.getValue().equals(result.getGatewayState())
                    && list.size() > 1) {
                    UmsGateWayInfo res = getEnableGateway(list);
                    if (res != null) {
                        result = res;
                    }
                }
            }
        } else if (StringRegUtils.isUnicom(recvId)) {
            if (logger.isInfoEnabled()) {
                logger.info("接收方为联通号码,recvId:" + recvId + ",content:" + content + ",appid:" + appId
                            + ",result:" + result);
            }
            list = umsGateWayInfoMapper.findWithType(GateEnum.SGIP.getValue());
            if (list.size() > 0) {
                result = list.get(new Random().nextInt(1000) % list.size());
                if (GateStateEnum.ERROR.getValue().equals(result.getGatewayState())
                    && list.size() > 1) {
                    UmsGateWayInfo res = getEnableGateway(list);
                    if (res != null) {
                        result = res;
                    }
                }
            }
        } else if (StringRegUtils.isTelecom(recvId)) {
            if (logger.isInfoEnabled()) {
                logger.info("接收方为电信号码,recvId:" + recvId + ",content:" + content + ",appid:" + appId
                            + ",result:" + result);
            }
            list.addAll(umsGateWayInfoMapper.findWithType(GateEnum.SMGP3.getValue()));
            list.addAll(umsGateWayInfoMapper.findWithType(GateEnum.SMGP.getValue()));
            if (list.size() > 0) {
                result = list.get(new Random().nextInt(1000) % list.size());
                if (GateStateEnum.ERROR.getValue().equals(result.getGatewayState())
                    && list.size() > 1) {
                    UmsGateWayInfo res = getEnableGateway(list);
                    if (res != null) {
                        result = res;
                    }
                }
            }

        }
        if (logger.isInfoEnabled()) {
            logger.info("根据号码找到网关,recvId:" + recvId + ",content:" + content + ",appid:" + appId
                        + ",gateway:" + result);
        }
        List<UmsGatewayRule> rules = umsGatewayRuleMapper.selectAll();
        Iterator<UmsGatewayRule> it = rules.iterator();
        UmsGatewayRule tempRule = null;
        List<UmsGatewayRule> tempRuleAll = new ArrayList<UmsGatewayRule>();
        while (it.hasNext()) {
            UmsGatewayRule rule = it.next();
            if (appId != null && StringUtils.hasText(rule.getAppIdRegx())
                && appId.matches(rule.getAppIdRegx())) {
                tempRuleAll.add(rule);
            }
            if (content != null && StringUtils.hasText(rule.getContentRegx())
                && content.matches(rule.getContentRegx())) {
                tempRuleAll.add(rule);
            }
            if (recvId != null && StringUtils.hasText(rule.getReceiveRegx())
                && recvId.matches(rule.getReceiveRegx())) {
                tempRuleAll.add(rule);
            }
        }
        if (tempRuleAll.size() > 0) {
            List<UmsGateWayInfo> cmppRule = new ArrayList<UmsGateWayInfo>();
            List<UmsGateWayInfo> sgipRule = new ArrayList<UmsGateWayInfo>();
            List<UmsGateWayInfo> smgpRule = new ArrayList<UmsGateWayInfo>();
            Iterator<UmsGatewayRule> a = tempRuleAll.iterator();
            while (a.hasNext()) {
                String gatewayId = a.next().getGatewayId();
                UmsGateWayInfo temp = umsGateWayInfoMapper.selectByPrimaryKey(gatewayId);
                if (GateEnum.CMPP.getValue().equals(temp.getType())
                    || GateEnum.CMPP30.getValue().equals(temp.getType())) {
                    cmppRule.add(temp);
                } else if (GateEnum.SGIP.getValue().equals(temp.getType())) {
                    sgipRule.add(temp);
                } else if (GateEnum.SMGP.getValue().equals(temp.getType())
                           || GateEnum.SMGP3.getValue().equals(temp.getType())) {
                    smgpRule.add(temp);
                }
            }
            if (StringRegUtils.isMobile(recvId) && cmppRule.size() > 0) {
                result = cmppRule.get(new Random().nextInt(1000) % cmppRule.size());
                if (GateStateEnum.ERROR.getValue().equals(result.getGatewayState())
                    && list.size() > 1) {
                    UmsGateWayInfo res = getEnableGateway(cmppRule);
                    if (res != null) {
                        result = res;
                    }
                }
                if (logger.isInfoEnabled()) {
                    logger.info("根据规则找到网关,recvId:" + recvId + ",content:" + content + ",appid:"
                                + appId + ",rule:" + result);
                }
            } else if (StringRegUtils.isUnicom(recvId) && sgipRule.size() > 0) {
                result = sgipRule.get(new Random().nextInt(1000) % sgipRule.size());
                if (GateStateEnum.ERROR.getValue().equals(result.getGatewayState())
                    && list.size() > 1) {
                    UmsGateWayInfo res = getEnableGateway(sgipRule);
                    if (res != null) {
                        result = res;
                    }
                }
            } else if (StringRegUtils.isTelecom(recvId) && smgpRule.size() > 0) {
                result = smgpRule.get(new Random().nextInt(1000) % smgpRule.size());
                if (GateStateEnum.ERROR.getValue().equals(result.getGatewayState())
                    && list.size() > 1) {
                    UmsGateWayInfo res = getEnableGateway(smgpRule);
                    if (res != null) {
                        result = res;
                    }
                }
            }

        }
        if (result != null) {
            if (logger.isInfoEnabled()) {
                logger.info("找到网关,recvId:" + recvId + ",content:" + content + ",appid:" + appId
                            + ",rule:" + result);
            }
            return result;
        }

        if (logger.isInfoEnabled()) {
            logger.info("无法找到网关规则,recvId:" + recvId + ",content:" + content + ",appid:" + appId
                        + ",rule:" + tempRule);
        }
        return null;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#insert(net.zoneland.ums.common.dal.dataobject.UmsGatewayRule)
     */
    public boolean add(UmsGatewayRule umsGatewayRule) {
        if (umsGatewayRule == null) {
            return false;
        }
        umsGatewayRule.setId(UUID.randomUUID().toString());
        int count = umsGatewayRuleMapper.insert(umsGatewayRule);
        if (count <= 0) {
            return false;
        }
        return true;
    }

    public PageResult<UmsGatewayRule> findRule(int pageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count = umsGatewayRuleMapper.selectCount();
        if (count < 1) {
            return new PageResult<UmsGatewayRule>(count, pageId);
        }
        PageResult<UmsGatewayRule> pageResult = new PageResult<UmsGatewayRule>(count, pageId);
        map.put("start", pageResult.getFirstrecode());
        map.put("end", pageResult.getEndrecode());
        List<UmsGatewayRule> ruleList = umsGatewayRuleMapper.pageAll(map);
        pageResult.setResults(ruleList);
        return pageResult;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#delRule(java.lang.String)
     */
    public boolean delRule(String id) {
        if (id == null) {
            return false;
        }
        umsGatewayRuleMapper.deleteByPrimaryKey(id);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#findGatewayRuleById(java.lang.String)
     */
    public UmsGatewayRule findGatewayRuleById(String id) {
        UmsGatewayRule umsGatewayRule = umsGatewayRuleMapper.selectByPrimaryKey(id);
        if (umsGatewayRule == null) {
            logger.info("没有找到对应ID的网关规则！");
            return new UmsGatewayRule();
        }
        return umsGatewayRule;
    }

    public void setUmsGatewayRuleMapper(UmsGatewayRuleMapper umsGatewayRuleMapper) {
        this.umsGatewayRuleMapper = umsGatewayRuleMapper;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#isExist(java.lang.String)
     */
    public boolean isExist(String id) {
        UmsGatewayRule umsGatewayRule = umsGatewayRuleMapper.selectByPrimaryKey(id);
        if (umsGatewayRule == null) {
            return false;
        }
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#update(net.zoneland.ums.common.dal.dataobject.UmsGatewayRule)
     */
    public boolean update(UmsGatewayRule umsGatewayRule) {
        if (umsGatewayRule == null) {
            return false;
        }
        int count = umsGatewayRuleMapper.updateByPrimaryKey(umsGatewayRule);
        if (count <= 0) {
            return false;
        }
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.config.gateway.GatewayRuleService#findOutProvGateway(java.lang.String)
     */
    public UmsGateWayInfo findOutProvGateway(String recvId) {

        logger.info("外省号码查找网关:" + recvId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isOutProv", GateOutProvEnum.can.getValue());

        if (StringRegUtils.isMobile(recvId)) {
            map.put("type", GateEnum.CMPP.getValue());
            List<UmsGateWayInfo> list = umsGateWayInfoMapper.findWithParam(map);
            if (list.size() > 0) {
                return list.get(new Random().nextInt(1000) % list.size());
            } else {
                map.put("type", GateEnum.CMPP30.getValue());
                list = umsGateWayInfoMapper.findWithParam(map);
                if (list.size() > 0) {
                    return list.get(new Random().nextInt(1000) % list.size());
                }
            }
            return null;
        }
        if (StringRegUtils.isUnicom(recvId)) {
            map.put("type", GateEnum.SGIP.getValue());
            List<UmsGateWayInfo> list = umsGateWayInfoMapper.findWithParam(map);
            if (list.size() > 0) {
                return list.get(new Random().nextInt(1000) % list.size());
            }
            return null;
        }
        if (StringRegUtils.isTelecom(recvId)) {
            map.put("type", GateEnum.SMGP.getValue());
            List<UmsGateWayInfo> list = umsGateWayInfoMapper.findWithParam(map);
            if (list.size() > 0) {
                return list.get(new Random().nextInt(1000) % list.size());
            } else {
                map.put("type", GateEnum.SMGP3.getValue());
                list = umsGateWayInfoMapper.findWithParam(map);
                if (list.size() > 0) {
                    return list.get(new Random().nextInt(1000) % list.size());
                }
            }
            return null;
        }
        return null;
    }

    @SuppressWarnings("unused")
    private UmsGateWayInfo getRandomGatewayByRule(List<UmsGateWayInfo> gateWayInfos,
                                                  List<UmsGatewayRule> rules) {
        Map<String, String> map = new HashMap<String, String>();
        for (UmsGatewayRule umsGatewayRule : rules) {
            map.put(umsGatewayRule.getGatewayId(), umsGatewayRule.getGatewayId());
        }
        List<UmsGateWayInfo> gateWayResultList = new ArrayList<UmsGateWayInfo>();
        for (UmsGateWayInfo umsGateWayInfo : gateWayInfos) {
            if (map.containsKey(umsGateWayInfo.getId())) {
                gateWayResultList.add(umsGateWayInfo);
            }
        }
        if (gateWayResultList.size() > 0) {
            return gateWayResultList.get(new Random().nextInt(1000) % gateWayResultList.size());
        }
        return null;
    }

    private UmsGateWayInfo getEnableGateway(List<UmsGateWayInfo> list) {
        logger.info("网关个数：" + list.size() + "-" + list.get(0).getType() + "更换可用网关");
        UmsGateWayInfo result = null;
        for (UmsGateWayInfo umsGateWayInfo : list) {
            if (GateStateEnum.ENABLED.getValue().equals(umsGateWayInfo.getGatewayState())) {
                result = umsGateWayInfo;
                logger.info("更换成可用网关：" + umsGateWayInfo);
                break;
            }
        }
        return result;
    }
}
