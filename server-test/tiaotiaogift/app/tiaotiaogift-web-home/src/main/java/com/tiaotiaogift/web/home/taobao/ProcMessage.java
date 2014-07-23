/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.tiaotiaogift.biz.common.taobao.SendMessageService;

/**
 * 
 * @author foodoon
 * @version $Id: ProcMessage.java, v 0.1 2013-8-4 下午4:36:04 foodoon Exp $
 */
public class ProcMessage implements Runnable {

    private final static Logger logger = Logger.getLogger(ProcMessage.class);

    private String              userId;
    private String              content;
    private String              phone;

    private SendMessageService  sendMessageService;

    public ProcMessage(String userId, String content, String phone,
                       SendMessageService sendMessageService) {
        this.userId = userId;
        this.content = content;
        this.phone = phone;
        this.sendMessageService = sendMessageService;
    }

    /** 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            if (userId != null && content != null && phone != null && sendMessageService != null) {
                sendMessageService.sendMsg(userId, content, phone);
            } else {
                logger.warn("proc msg warn:" + this);
            }
        } catch (Exception e) {
            logger.error("proc msg error", e);
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
