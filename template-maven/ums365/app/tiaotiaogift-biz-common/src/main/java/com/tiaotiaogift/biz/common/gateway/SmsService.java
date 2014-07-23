/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.gateway;

/**
 * 
 * @author gang
 * @version $Id: SmsService.java, v 0.1 2013-5-4 下午9:07:17 gang Exp $
 */
public interface SmsService {

    public String sendSms(SmsMsg msg);

    public String getUserInfo();

    public String recvSms();

    public String recv106Sms();

}
