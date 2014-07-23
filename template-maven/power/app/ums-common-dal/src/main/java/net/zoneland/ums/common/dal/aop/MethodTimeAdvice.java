/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.aop;

import java.io.Serializable;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

/**
 *
 * @author wangyong
 * @version $Id: MethodTimeAdvice.java, v 0.1 2012-9-18 下午4:30:32 wangyong Exp $
 */
public class MethodTimeAdvice implements MethodInterceptor, Serializable {

    /**  */
    private static final long   serialVersionUID = 8253597878441640226L;
    private final static Logger logger           = Logger.getLogger("DAL-MONITOR");

    /**
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch
        StopWatch clock = new StopWatch();
        clock.start(); //计时开始
        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Throwable e) {
            Object[] objs = invocation.getArguments();
            String className = invocation.getMethod().getDeclaringClass().getSimpleName();
            String methodName = className + "." + invocation.getMethod().getName();
            logger.error("数据库执行异常,方法名：" + methodName + "参数:" + getString(objs) + e.getMessage());
            throw e;
        }
        clock.stop(); //计时结束
        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
        String methodName = className + "." + invocation.getMethod().getName();
        //if (logger.isInfoEnabled()) {
        logger.info("执行时间: " + clock.getTime() + " ms [" + methodName + "]");
        //}
        return result;
    }

    @SuppressWarnings("unchecked")
    public String getString(Object[] objs) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, len = objs.length; i < len; i++) {
            if (objs[i] instanceof String) {
                stringBuffer.append("String类型：" + objs[i].toString());
            } else if (objs[i] instanceof Map) {
                //                HashMap<String, Object> hashMap = (HashMap<String, Object>) objs[i];
                //                HashMap<String, Object> map = hashMap;
                //                HashSet<String> set = (HashSet<String>) map.keySet();
                //                stringBuffer.append("Map类型");
                //                for (String str : set) {
                //                    stringBuffer.append(str + "=" + map.get(str));
                //                }
            } else if (objs[i] instanceof Integer) {
                stringBuffer.append("整数类型：");
                stringBuffer.append(objs[i].toString());
            } else {
                stringBuffer.append(objs[i].toString());
            }
        }
        return stringBuffer.toString();
    }
}
