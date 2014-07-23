/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.jmx;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 
 * @author gag
 * @version $Id: LoggerManager.java, v 0.1 2012-5-30 下午12:22:36 gag Exp $
 */
@Component
@ManagedResource(objectName = "bean:name=loggerManager")
public class LoggerManager {

    protected static final Logger     logger = LoggerFactory.getLogger(LoggerManager.class);

    private static Map<String, Level> map    = new HashMap<String, Level>();

    static {
        map.put("debug", Level.DEBUG);
        map.put("info", Level.INFO);
        map.put("warn", Level.WARN);
        map.put("error", Level.ERROR);
    }

    @ManagedOperation(description = "设置所有日志级别:参数debug,info,warn,error")
    public void setLevel(String level) {
        if (StringUtils.hasLength(level)) {
            Level lev = map.get(level.toLowerCase());
            if (lev != null) {
                @SuppressWarnings("unchecked")
                Enumeration<org.apache.log4j.Logger> e = LogManager.getCurrentLoggers();
                while (e.hasMoreElements()) {
                    e.nextElement().setLevel(lev);
                }
                logger.info("设置所有日志级别成功，level:" + level);
            } else {
                logger.error("设置失败，无法找到日志级别:" + level);
            }
        }
    }

    @ManagedOperation(description = "设置所有日志级别:参数debug,info,warn,error")
    public void setLevel(String name, String level) {
        if (StringUtils.hasLength(level)) {
            Level lev = map.get(level.toLowerCase());
            if (lev != null) {
                org.apache.log4j.Logger log = LogManager.getLogger(name);
                if (log != null) {
                    log.setLevel(lev);
                    logger.info("设置日志级别成功，level:" + level + ",name:" + name);
                } else {
                    logger.error("设置失败，无法找到日志名:" + name);
                }

            } else {
                logger.error("设置失败，无法找到日志级别:" + level);
            }
        }
    }

}
