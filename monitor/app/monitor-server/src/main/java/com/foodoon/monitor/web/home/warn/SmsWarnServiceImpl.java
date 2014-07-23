/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.warn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.foodoon.monitor.dal.dataobject.Warn;
import com.foodoon.monitor.web.home.form.GarForm;

/**
 * 
 * @author foodoon
 * @version $Id: SmsWarnService.java, v 0.1 2013-6-4 下午1:55:29 foodoon Exp $
 */
public class SmsWarnServiceImpl implements WarnService {

    private final static Logger logger = LoggerFactory.getLogger(SmsWarnServiceImpl.class);

    /** 
     * @see com.foodoon.monitor.web.home.warn.WarnService#warn(com.foodoon.monitor.web.home.form.GarForm, com.foodoon.monitor.dal.dataobject.Warn)
     */
    public void warn(GarForm form, Warn warn) {

        if (StringUtils.hasText(warn.getPhone())) {

            logger.warn("短信告警:" + form + ",告警设置:" + warn);

            String[] phones = warn.getPhone().split(";");
            for (int i = 0, len = phones.length; i < len; ++i) {
                WarnFactory.putSms(phones[i], EmailWarnServiceImpl.buildContent(form, warn));
            }

        }
    }

}
