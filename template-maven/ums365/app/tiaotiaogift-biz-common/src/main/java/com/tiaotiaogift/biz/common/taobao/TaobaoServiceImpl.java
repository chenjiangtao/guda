/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.taobao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.IncrementCustomerPermitRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.IncrementCustomerPermitResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;

/**
 * 
 * @author foodoon
 * @version $Id: TaobaoServiceImpl.java, v 0.1 2013-7-24 下午1:29:29 foodoon Exp $
 */
@Service("taobaoService")
public class TaobaoServiceImpl implements TaobaoService {

    private Logger logger = LoggerFactory.getLogger(TaobaoServiceImpl.class);

    /** 
     * @throws ApiException 
     * @see com.tiaotiaogift.biz.common.taobao.TaobaoService#queryTrade(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.Date)
     */
    public TradesSoldGetResponse queryTrade(String appkey, String secret, String sessionKey,
                                            Date start, Date end) throws ApiException {

        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",
            appkey, secret);
        TradesSoldGetRequest req = new TradesSoldGetRequest();
        req.setFields("total_fee,receiver_mobile,receiver_address,buyer_nick,");
        req.setStartCreated(start);
        req.setEndCreated(end);

        req.setPageNo(1L);
        req.setPageSize(100L);
        req.setUseHasNext(true);
        req.setIsAcookie(false);
        return client.execute(req, sessionKey);

    }

    /** 
     * @throws ApiException 
     * @see com.tiaotiaogift.biz.common.taobao.TaobaoService#sendPermit()
     */
    public void sendPermit(String appkey, String secret, String sessionKey) throws ApiException {

        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",
            appkey, secret);
        IncrementCustomerPermitRequest req = new IncrementCustomerPermitRequest();
        req.setType("get,syn,notify");
        req.setTopics("trade;refund;item");
        req.setStatus("all;all;ItemAdd,ItemUpdate");
        logger.info("开通 增量消息服务");
        IncrementCustomerPermitResponse response = client.execute(req, sessionKey);
        logger.info("开通 增量消息服务返回：" + response.getBody() + ",errorcode," + response.getErrorCode()
                    + "" + response.getAppCustomer());

    }

    /** 
     * @see com.tiaotiaogift.biz.common.taobao.TaobaoService#queryFullTrade(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public TradeFullinfoGetResponse queryFullTrade(String appkey, String secret, String sessionKey,
                                                   long tid) throws ApiException {

        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",
            appkey, secret);
        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields("receiver_mobile,consign_time,received_payment,buyer_alipay_no");
        req.setTid(tid);
        return client.execute(req, sessionKey);
    }

}
