/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 * 负责对象的复制，源对象和目标对象中有相同的属性则会将对应的值复制过去
 * 这里对boolean变量是不进行复制的，需要另外特殊处理
 * @author gag
 * @version $Id: MessageBuilder.java, v 0.1 2012-5-12 下午11:55:44 gag Exp $
 */
public class ObjectBuilder {

    static Map<String, Map<Method, Method>> methodMap = new HashMap<String, Map<Method, Method>>();

    static {
        build(PrimitiveMessage.class, UmsMsgOut.class);
    }

    /**
     * 将源Object和目标object之间相同的属性进行复制
     * @param src 源object
     * @param dest 目标object
     */
    public static void copyObject(Object src, Object dest) {
        if (src == null || dest == null) {
            return;
        }
        Map<Method, Method> map = methodMap.get(src.getClass() + "+" + dest.getClass());
        if (map == null) {
            build(src.getClass(), dest.getClass());
            map = methodMap.get(src.getClass() + "+" + dest.getClass());
        }

        Set<Map.Entry<Method, Method>> set = map.entrySet();
        Iterator<Map.Entry<Method, Method>> it = set.iterator();
        try {
            while (it.hasNext()) {
                Map.Entry<Method, Method> entry = it.next();
                Method readMethod = entry.getKey();
                Method writeMethod = entry.getValue();
                Object val = readMethod.invoke(src, new Object[] {});
                writeMethod.invoke(dest, new Object[] { val });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 映射两个class之间的读写方法
     * 将class1的读方法和class2的写方法对应起来
     * @param readClass
     * @param writeClass
     */
    public static void build(Class<?> readClass, Class<?> writeClass) {
        Method[] readMethod = getAllDeclaredMethods(readClass);
        Method[] writeMethod = getAllDeclaredMethods(writeClass);
        Map<Method, Method> map = new HashMap<Method, Method>();
        for (int i = 0; i < writeMethod.length; i++) {
            Method method = writeMethod[i];
            if (method.getName().indexOf("set") > -1 && method.getParameterTypes().length == 1) {
                String fieldName = writeMethod[i].getName();
                Class<?> paramClass = method.getParameterTypes()[0];
                fieldName = fieldName.substring(fieldName.indexOf("set") + 3, fieldName.length());
                for (int j = 0; j < readMethod.length; j++) {
                    if (readMethod[j].getName().equals("get" + fieldName)
                        && paramClass == readMethod[j].getReturnType()) {
                        map.put(readMethod[j], writeMethod[i]);
                        break;
                    }
                }
            }
        }
        methodMap.put(readClass + "+" + writeClass, map);
    }

    /**
     * 获取class所有的方法，包括父类
     * @param clazz
     * @return
     */
    public static Method[] getAllDeclaredMethods(Class<?> clazz) {
        Method[] method = new Method[0];
        while (clazz != null) {
            Method[] temp = clazz.getDeclaredMethods();
            if (temp != null) {
                Method[] tmethod = new Method[method.length + temp.length];
                System.arraycopy(method, 0, tmethod, 0, method.length);
                System.arraycopy(temp, 0, tmethod, method.length, temp.length);
                method = tmethod;
            }
            clazz = clazz.getSuperclass();
            if (clazz == java.lang.Object.class) {
                break;
            }
        }
        return method;
    }

}
