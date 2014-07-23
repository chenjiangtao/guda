/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.taobao;

/**
 * 
 * @author foodoon
 * @version $Id: SendMessage.java, v 0.1 2013-8-4 下午6:50:44 foodoon Exp $
 */
public interface SendMessageService {

    public void sendMsg(String userId, String content, String recv);

}
