/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent;

import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.foodoon.monitor.agent.impl.GarTaskImpl;

/**
 * 
 * @author foodoon
 * @version $Id: Task.java, v 0.1 2013-5-25 上午7:57:28 foodoon Exp $
 */
public class Task {

    private final static Logger logger = LoggerFactory.getLogger(Task.class);

    //    @Autowired
    //    private AgentMapper         agentMapper;
    //
    //    @Autowired
    //    private SysMapper           sysMapper;

    @Autowired
    private HttpClient          httpClient;

    private String              host;

    private String              ip;

    private String              apiUrl;

    public Task() {
        try {
            initHost();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initHost() throws UnknownHostException {

        ip = GarTaskImpl.getIp();

        host = GarTaskImpl.getHost();
    }

    //    public void check(String key, int type, String comment) {
    //        Map<String, Object> params = new HashMap<String, Object>();
    //        params.put("key", key);
    //        params.put("ip", ip);
    //        Agent a = agentMapper.selectByIpAndKey(params);
    //        if (a == null) {
    //            Agent agent = new Agent();
    //            agent.setId(UUID.randomUUID().toString());
    //            agent.setComment(comment);
    //            agent.setGmtCreated(new Date());
    //            agent.setHost(host);
    //            agent.setIp(ip);
    //            agent.setK(key);
    //            agent.setValueType(type);
    //            agentMapper.insert(agent);
    //        }
    //    }
    //
    //    public void insert(String key, Float val, String varText) {
    //        Sys sys = new Sys();
    //        sys.setId(UUID.randomUUID().toString());
    //        sys.setIp(ip);
    //        sys.setK(key);
    //        sys.setVal(val);
    //        sys.setValText(varText);
    //        sys.setGmtCreated(new Date());
    //        sysMapper.insert(sys);
    //    }

    public void send(String key, String comment, Float val, String varText, int valueType,
                     int orderNum) {
        PostMethod method = new UTF8PostMethod(apiUrl);
        if (comment != null) {
            NameValuePair _comment = new NameValuePair("comment", comment);
            method.addParameter(_comment);
        }
        NameValuePair _valueType = new NameValuePair("valueType", getStr(valueType));
        NameValuePair _ip = new NameValuePair("ip", ip);
        NameValuePair _host = new NameValuePair("host", host);
        NameValuePair _k = new NameValuePair("k", key);
        NameValuePair _val = new NameValuePair("val", getStr(val));
        NameValuePair _valText = new NameValuePair("valText", varText);
        NameValuePair _orderText = new NameValuePair("order", getStr(orderNum));
        method.addParameter(_valueType);
        method.addParameter(_ip);
        method.addParameter(_k);
        method.addParameter(_val);
        method.addParameter(_valText);
        method.addParameter(_host);
        method.addParameter(_orderText);
        try {
            int status = httpClient.executeMethod(method);
            if (status == 200) {
                String rst = method.getResponseBodyAsString();
                logger.info("POST结果：" + rst);

            } else {
                String rst = method.getResponseBodyAsString();
                logger.info("send fail：status" + status + ",body:" + rst);
            }

        } catch (Exception e) {
            logger.error("", e);
        }

    }

    private String getStr(Object obj) {
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static class UTF8PostMethod extends PostMethod {
        public UTF8PostMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            return "utf-8";
        }

        @Override
        public String getResponseCharSet() {
            return "utf-8";
        }
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

}
