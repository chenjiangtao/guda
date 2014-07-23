/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.log;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.ums.common.dal.UmsActionLogMapper;
import net.zoneland.ums.common.dal.dataobject.UmsActionLog;
import net.zoneland.ums.common.util.annotation.Log;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gang
 * @version $Id: LogTraceAdvice.java, v 0.1 2012-9-6 下午7:57:50 gang Exp $
 */
public class LogTraceAdvice implements MethodBeforeAdvice, Serializable {

    /**  */
    private static final long   serialVersionUID = -7233873298242204770L;

    private static final Logger logger           = Logger.getLogger(LogTraceAdvice.class);

    @Autowired
    private UmsActionLogMapper  umsActionLogMapper;

    /**
     * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
     */
    public void before(Method method, Object[] args, Object target) throws Throwable {

        String methodName = method.getName();
        //如果没有Log注解的就不记录了
        if (!method.isAnnotationPresent(Log.class)) {
            return;
        }
        if (methodName.indexOf("update") > -1) {
            Log log = method.getAnnotation(Log.class);
            if (log != null) {
                saveAction("更新", log.name(), log.comments());
            } else {
                saveAction("更新", "", "");
            }

        } else if (methodName.indexOf("insert") > -1) {
            Log log = method.getAnnotation(Log.class);
            if (log != null) {
                saveAction("新增", log.name(), log.comments());
            } else {
                saveAction("新增", "", "");
            }

        } else if (methodName.indexOf("del") > -1) {
            Log log = method.getAnnotation(Log.class);

            if (log != null) {
                saveAction("删除", log.name(), log.comments());
            } else {
                saveAction("删除", "", "");
            }

        } else if (methodName.indexOf("queryMsg") > -1) {
            Log log = method.getAnnotation(Log.class);
            if (log != null) {
                saveAction("短信查询", log.name(), log.comments() + getStrParams(args));
            } else {
                saveAction("短信查询", "", "");
            }

        }

    }

    private String getStrParams(Object[] args) {
        if (args == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        buf.append(",参数:");
        for (int i = 0, len = args.length; i < len; i++) {
            if (args[i] instanceof String) {
                buf.append(String.valueOf(args[i]));
            } else if (args[i] instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) args[i];
                Set<String> set = map.keySet();
                for (String str : set) {
                    buf.append(str).append("=").append(map.get(str)).append(";");
                }
            } else if (args[i] instanceof Integer) {
                buf.append(String.valueOf(args[i]));
            } else {
                buf.append(String.valueOf(args[i]));
            }
        }
        return buf.toString();
    }

    private void saveAction(String type, String menu, String comments) {
        UmsActionLog action = new UmsActionLog();
        action.setId(UUID.randomUUID().toString());
        action.setComment(comments);
        action.setGmtCreated(new Date());
        if (OperationContextHolder.getPrincipal() != null) {
            action.setOperatorId(OperationContextHolder.getPrincipal().getLogonId());
            action.setOperatorIp(OperationContextHolder.getPrincipal().getIp());
            action.setOperatorName(OperationContextHolder.getPrincipal().getUserName());
        }
        action.setOperatorType(type);
        action.setOperatorMenu(menu);
        try {
            umsActionLogMapper.insert(action);
        } catch (Exception e) {
            logger.error("保存日志错误", e);
        }
    }

    public void setUmsActionLogMapper(UmsActionLogMapper umsActionLogMapper) {
        this.umsActionLogMapper = umsActionLogMapper;
    }

}
