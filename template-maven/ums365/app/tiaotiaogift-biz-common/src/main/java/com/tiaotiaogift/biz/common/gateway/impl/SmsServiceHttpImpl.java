/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.gateway.impl;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiaotiaogift.biz.common.gateway.SmsMsg;
import com.tiaotiaogift.biz.common.gateway.SmsService;
import com.tiaotiaogift.common.dal.MsgInMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgIn;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.enums.MsgInStatusEnum;

/**
 * 
 * @author gang
 * @version $Id: SmsServiceHttpImpl.java, v 0.1 2013-5-4 下午9:14:53 gang Exp $
 */
public class SmsServiceHttpImpl implements SmsService {

    private Logger      logger = LoggerFactory.getLogger(SmsServiceHttpImpl.class);

    @Autowired
    private HttpClient  httpClient;

    @Autowired
    private MsgInMapper msgInMapper;

    @Autowired
    private UserMapper  userMapper;

    private String      httpUrl;

    private String      smsUserName;

    private String      smsPassword;

    /** 
     * @see com.tiaotiaogift.biz.common.gateway.SmsService#sendSms(com.tiaotiaogift.biz.common.gateway.SmsMsg)
     */
    public String sendSms(SmsMsg msg) {
        logger.info("send:" + msg);

        PostMethod method = new GB2312PostMethod(httpUrl + "/post_send/");
        NameValuePair uid = new NameValuePair("uid", smsUserName);
        NameValuePair pwd = new NameValuePair("pwd", smsPassword);
        NameValuePair linkid = new NameValuePair("linkid", msg.getLinkId());
        NameValuePair mobile = new NameValuePair("mobile", msg.getRecv());
        NameValuePair content = new NameValuePair("msg", msg.getContent());
        method.addParameter(uid);
        method.addParameter(pwd);
        method.addParameter(linkid);
        method.addParameter(mobile);
        method.addParameter(content);
        try {
            int status = httpClient.executeMethod(method);
            if (status == 200) {
                String rst = method.getResponseBodyAsString();
                logger.info("POST结果：" + rst);
                if (rst != null && rst.startsWith("0")) {
                    return "0";
                }
                return rst;
            } else {
                String rst = method.getResponseBodyAsString();
                logger.info("send fail：status" + status + ",body:" + rst);
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /** 
     * @see com.tiaotiaogift.biz.common.gateway.SmsService#getUserInfo()
     */
    public String getUserInfo() {

        GetMethod method = new GB2312GetMethod(httpUrl + "/user_info/");

        method.getParams().setParameter("uid", smsUserName);
        method.getParams().setParameter("pwd", smsPassword);
        try {

            int status = httpClient.executeMethod(method);
            if (status == 200) {
                String rst = method.getResponseBodyAsString();
                logger.info("POST结果：" + rst);
                return rst;
            }
        } catch (HttpException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    /** 
     * @see com.tiaotiaogift.biz.common.gateway.SmsService#recvSms()
     */
    public String recvSms() {
        GetMethod method = new GB2312GetMethod(httpUrl + "/reve/?uid=" + smsUserName + "&pwd="
                                               + smsPassword);

        try {
            int status = httpClient.executeMethod(method);
            if (status == 200) {
                String rst = method.getResponseBodyAsString();
                logger.info("获取回复结果：" + rst);
                String[] str = rst.split("\n");
                for (int i = 0, len = str.length; i < len; ++i) {
                    String temp = str[i].replace("//", "_f_o_f_");
                    String[] msg = temp.split("/");
                    if (msg.length >= 5) {
                        MsgIn msgIn = new MsgIn();
                        msgIn.setGmtCreated(new Date());
                        String content = "";
                        for (int j = 3, jlen = msg.length - 2; j <= jlen; ++j) {
                            content += msg[j];
                            content += "/";
                        }
                        if (content.length() > 1) {
                            msgIn.setContent(content.substring(0, content.length() - 1).replace(
                                "_f_o_f_", "/"));
                        }
                        msgIn.setId(UUID.randomUUID().toString());
                        int linkID = getInt(msg[2]);
                        if (linkID != -1) {
                            User u = userMapper.selectUserByLinkId(linkID);
                            if (u != null) {
                                msgIn.setRecvId(u.getId());
                            }
                        } else {
                            msgIn.setRecvId("null");
                        }
                        msgIn.setSendId(msg[0]);
                        msgIn.setStatus(MsgInStatusEnum.init.getValue());
                        msgInMapper.insert(msgIn);
                    } else {
                        logger.warn("获取106回复结果：但是无法解析" + rst);
                    }

                }
                return rst;
            }
        } catch (HttpException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    public static class GB2312PostMethod extends PostMethod {
        public GB2312PostMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            return "gb2312";
        }

        @Override
        public String getResponseCharSet() {
            return "gb2312";
        }
    }

    public static class GB2312GetMethod extends GetMethod {
        public GB2312GetMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            return "gb2312";
        }

        @Override
        public String getResponseCharSet() {
            return "gb2312";
        }
    }

    /** 
     * @see com.tiaotiaogift.biz.common.gateway.SmsService#recv106Sms()
     */
    public String recv106Sms() {
        GetMethod method = new GB2312GetMethod(httpUrl + "/reve/?uid=" + smsUserName + "&pwd="
                                               + smsPassword);
        try {
            int status = httpClient.executeMethod(method);
            if (status == 200) {
                String rst = method.getResponseBodyAsString();
                logger.info("获取106回复结果：" + rst);
                String[] str = rst.split("\n");
                for (int i = 0, len = str.length; i < len; ++i) {
                    String temp = str[i].replace("\\/", "_f_o_f_");
                    String[] msg = temp.split("/");
                    if (msg.length == 5) {
                        MsgIn msgIn = new MsgIn();
                        msgIn.setGmtCreated(new Date());
                        String content = "";
                        for (int j = 3, jlen = msg.length - 2; j < jlen; ++j) {
                            content += msg[j];
                            content += "/";
                        }
                        if (content.length() > 1) {
                            msgIn.setContent(content.substring(0, content.length() - 1).replace(
                                "_f_o_f_", "/"));
                        }
                        msgIn.setId(UUID.randomUUID().toString());
                        int linkID = getInt(msg[2]);
                        if (linkID != -1) {
                            User u = userMapper.selectUserByLinkId(linkID);
                            if (u != null) {
                                msgIn.setRecvId(u.getId());
                            }
                        } else {
                            msgIn.setRecvId("null");
                        }
                        msgIn.setSendId(msg[0]);
                        msgIn.setStatus(MsgInStatusEnum.init.getValue());
                        msgInMapper.insert(msgIn);
                    } else {
                        logger.warn("获取106回复结果：但是无法解析" + rst);
                    }

                }
                return rst;
            }
        } catch (HttpException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    private int getInt(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return -1;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public void setSmsUserName(String smsUserName) {
        this.smsUserName = smsUserName;
    }

    public void setSmsPassword(String smsPassword) {
        this.smsPassword = smsPassword;
    }

}
