/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.security;

import org.springframework.util.Assert;

/**
 * 保存当前操作上下文
 * @author gag
 * @version $Id: SecurityContextHolder.java, v 0.1 2012-5-21 下午1:39:48 gag Exp $
 */
public class SecurityContextHolder {

    private static ThreadLocal<OperationContext> contextHolder = new ThreadLocal<OperationContext>();

    public static void setContext(OperationContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(context);
    }

    public static OperationContext getContext() {

        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

}
