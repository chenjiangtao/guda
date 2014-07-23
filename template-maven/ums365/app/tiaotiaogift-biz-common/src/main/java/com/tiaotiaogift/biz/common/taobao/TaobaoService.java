/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.taobao;

import java.util.Date;

import com.taobao.api.ApiException;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;

/**
 * 
 * @author foodoon
 * @version $Id: TaobaoService.java, v 0.1 2013-7-24 下午1:26:43 foodoon Exp $
 */
public interface TaobaoService {

    public TradesSoldGetResponse queryTrade(String appkey, String secret, String sessionKey,
                                            Date start, Date end) throws ApiException;

    public void sendPermit(String appkey, String secret, String sessionKey) throws ApiException;

    public TradeFullinfoGetResponse queryFullTrade(String appkey, String secret, String sessionKey,
                                                   long tid) throws ApiException;
}
