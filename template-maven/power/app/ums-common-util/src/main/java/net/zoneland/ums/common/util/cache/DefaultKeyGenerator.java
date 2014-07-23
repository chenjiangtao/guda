/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 
 * @author gag
 * @version $Id: DefaultKeyGenerator.java, v 0.1 2012-9-25 上午10:35:14 gag Exp $
 */
public class DefaultKeyGenerator implements KeyGenerator {

    /** 
     * @see org.springframework.cache.interceptor.KeyGenerator#generate(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object generate(Object target, Method method, Object... params) {

        //Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);

        //if (targetClass == null && target != null && AopUtils.isAopProxy(targetClass)) {
        Class<?> targetClass = method.getDeclaringClass();
        //}
        String methodName = method.getName();
        StringBuilder buf = new StringBuilder();
        buf.append(targetClass.getName()).append(".").append(methodName);
        for (int i = 0, len = params.length; i < len; ++i) {
            if (params[i] != null) {
                buf.append(params[i].hashCode()).append(";");
            }
        }
        return buf.toString();
    }

}
