/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.taobao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.tiaotiaogift.biz.common.taobao.TaobaoService;
import com.tiaotiaogift.common.dal.NotifyMapper;
import com.tiaotiaogift.common.mysql.dataobject.Notify;

/**
 * 
 * @author foodoon
 * @version $Id: InitService.java, v 0.1 2013-8-5 下午4:30:32 foodoon Exp $
 */
@Service("initService")
public class InitService {

    private final static Logger logger = Logger.getLogger(InitService.class);

    @Autowired
    private NotifyMapper        notifyMapper;

    @Autowired
    private TaobaoNotify        taobaoNotify;

    @Autowired
    private TaobaoService       taobaoService;

    public void init() {
        if (logger.isInfoEnabled()) {
            logger.info("start listener..");
        }
        taobaoNotify.startLisener();
        List<Notify> notifyList = notifyMapper.selectAll();
        Iterator<Notify> it = notifyList.iterator();
        while (it.hasNext()) {
            Notify notify = it.next();
            if (notify.getSessionKey() != null) {
                try {
                    taobaoService.sendPermit(TaobaoCallController.appKey,
                        TaobaoCallController.appSecret, notify.getSessionKey());
                    if (logger.isInfoEnabled()) {
                        logger.info("send permit .." + notify);
                    }
                } catch (ApiException e) {
                    logger.error("", e);
                }
            }

        }
    }

}
