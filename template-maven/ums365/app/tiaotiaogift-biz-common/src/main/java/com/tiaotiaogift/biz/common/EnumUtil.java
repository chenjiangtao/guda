/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author gag
 * @version $Id: EnumUtil.java, v 0.1 2012-5-21 下午4:14:44 gag Exp $
 */
public class EnumUtil {

    public static final String                defaultMethodName = "getNameByValue";

    public static final Map<String, Class<?>> map               = new HashMap<String, Class<?>>();

    static {
        map.put("MsgOut", com.tiaotiaogift.biz.common.MsgOutEnum.class);

    }

    public static String findValueByCode(String clazz, String code) {
        if (code == null) {
            return null;
        }
        Class<?> clz = null;
        if (map.containsKey(clazz)) {
            clz = map.get(clazz);
        }

        try {
            if (clz == null) {
                clz = Class.forName(clazz);
            }
            Method method = clz.getDeclaredMethod(defaultMethodName, String.class);
            if (method == null) {
                return null;
            }
            Object val = method.invoke(clz, code);
            return String.valueOf(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String f(String clazz, String methodName, String code) {
        if (code == null) {
            return null;
        }
        Class<?> clz = null;
        if (map.containsKey(clazz)) {
            clz = map.get(clazz);
        }
        try {
            if (clz == null) {
                clz = Class.forName(clazz);
            }
            Method method = clz.getDeclaredMethod(methodName, String.class);
            if (method == null) {
                return null;
            }
            Object val = method.invoke(clz, code);
            return String.valueOf(val);

        } catch (Exception e) {

        }
        return null;
    }

    public static String f(String clazz, String code) {
        if (code == null) {
            return null;
        }
        Class<?> clz = null;
        if (map.containsKey(clazz)) {
            clz = map.get(clazz);
        }
        try {
            if (clz == null) {
                clz = Class.forName(clazz);
            }
            Method method = clz.getDeclaredMethod(defaultMethodName, String.class);
            if (method == null) {
                return null;
            }
            Object val = method.invoke(clz, code);
            return String.valueOf(val);

        } catch (Exception e) {

        }
        return null;
    }

    public static void main(String args[]) {
        EnumUtil.f("net.zoneland.sms.biz.common.msg.enums.MsgTypeEnum", "getNameByValue", "1");
    }

}
