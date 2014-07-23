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
 * @version $Id: EmailWarn.java, v 0.1 2013-6-4 下午1:54:43 foodoon Exp $
 */
public class EmailWarnServiceImpl implements WarnService {

    private final static Logger logger = LoggerFactory.getLogger(EmailWarnServiceImpl.class);

    /** 
     * @see com.foodoon.monitor.web.home.warn.WarnService#warn(com.foodoon.monitor.web.home.form.GarForm)
     */
    public void warn(GarForm form, Warn warn) {
        if (StringUtils.hasText(warn.getEmail())) {

            String[] to = warn.getEmail().split(";");
            logger.warn("邮件告警:" + form + ",告警设置:" + warn);
            for (int i = 0, len = to.length; i < len; ++i) {
                WarnFactory.putMail(to[i], buildContent(form, warn));
            }

        }
    }

    public static String buildContent(GarForm form, Warn warn) {
        StringBuilder content = new StringBuilder();
        content.append("服务器:").append(form.getIp()).append("指标:").append(form.getK());
        if (StringUtils.hasText(form.getComment())) {
            content.append("(").append(form.getComment()).append(")");
        }
        content.append("当前值为:").append(form.getVal()).append("告警值为:" + warn.getVal());
        return content.toString();

    }
}
