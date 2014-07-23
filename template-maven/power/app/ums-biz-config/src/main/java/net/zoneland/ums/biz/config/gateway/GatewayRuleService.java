/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.gateway;

import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayRule;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 网关选择规则业务类
 * @author gag
 * @version $Id: GatewayRuleService.java, v 0.1 2012-8-24 上午9:04:13 gag Exp $
 */
public interface GatewayRuleService {

    /**
     * 根据接收方手机号，内容，appid查找网关渠道
     * @param recvId 接收方手机号
     * @param content 内容
     * @param appId appID
     * @return
     */
    UmsGateWayInfo findGatewayWithRule(String recvId, String content, String appId);

    UmsGateWayInfo findOutProvGateway(String recvId);

    /**
     *插入网关规则
     *
     * @param umsGatewayRule 网关规则
     * @return
     */
    boolean add(UmsGatewayRule umsGatewayRule);

    boolean update(UmsGatewayRule umsGatewayRule);

    /**
     *分页查询网关规则
     *
     * @param pageId
     * @param pageSize
     * @return
     */
    public PageResult<UmsGatewayRule> findRule(int pageId);

    /**
     *通过ID查找网关规则
     *
     * @param id
     * @return
     */
    UmsGatewayRule findGatewayRuleById(String id);

    /**
     *通过ID删除对应的网关规则
     *
     * @param id
     * @return
     */
    boolean delRule(String id);

    /**
     *判断网关规则是否存在
     *
     * @param id
     * @return
     */
    boolean isExist(String id);
}
