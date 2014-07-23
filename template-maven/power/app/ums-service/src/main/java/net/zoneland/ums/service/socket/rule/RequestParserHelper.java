/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.rule;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.zoneland.ums.biz.msg.socket.ServiceRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * @author gag
 * @version $Id: RequestParserHelper.java, v 0.1 2012-8-10 下午4:43:14 gag Exp $
 */
public class RequestParserHelper {

    private static final Log             logger       = LogFactory
                                                          .getLog(RequestParserHelper.class);

    private static String                charset      = "GBK";

    private static Map<String, Class<?>> classTypeMap = new HashMap<String, Class<?>>();

    private static Map<String, Method>   methodMap    = new HashMap<String, Method>();

    static {
        init();
    }

    public static ServiceRequest parseRequest(String msgCode, byte[] bytes)
                                                                           throws UnsupportedEncodingException {
        if (msgCode == null || bytes == null) {
            return null;
        }

        Map<String, ParamRuleInfo> map = ParamRuleManager.getRuleMap().get(msgCode);
        if (map == null) {
            return null;
        }
        Iterator<String> it = map.keySet().iterator();
        int byteLen = bytes.length;
        int totalLen = 0;
        ServiceRequest sr = new ServiceRequest();
        sr.setRequestCode(msgCode);
        while (it.hasNext()) {
            String param = it.next();
            ParamRuleInfo pri = map.get(param);
            int len = pri.getLength();
            String paramVal = new String(bytes, totalLen, Math.min(len, byteLen - totalLen),
                charset);
            totalLen += len;
            if (pri.isNotNull()) {
                if (paramVal == null || !StringUtils.hasText(paramVal)) {
                    throw new RuntimeException("参数：[" + param + "] 不能为空。");
                }
            }
            if (StringUtils.hasText(paramVal)) {
                fillVal(sr, param, StringUtils.trimWhitespace(paramVal));
            }

        }
        return sr;

    }

    public static void fillVal(ServiceRequest sr, String param, String paramVal) {
        try {
            Method method = methodMap.get(param);
            Class<?> clz = classTypeMap.get(param);
            if (clz == String.class) {
                method.invoke(sr, paramVal);
            } else if (clz == int.class) {
                int val = Integer.parseInt(paramVal);
                method.invoke(sr, val);
            } else if (clz == float.class) {
                float val = Float.parseFloat(paramVal);
                method.invoke(sr, val);
            }
        } catch (NumberFormatException ee) {
            logger.error("参数格式化错误:param:[" + param + "],val:[" + paramVal + "]", ee);
        } catch (Exception e) {
            logger.error("参数set错误:param:[" + param + "],val:[" + paramVal + "]", e);
            throw new RuntimeException(e);
        }

    }

    private static void init() {
        Field[] fields = ServiceRequest.class.getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; ++i) {
            classTypeMap.put(fields[i].getName(), fields[i].getType());
        }

        Method[] methods = ServiceRequest.class.getDeclaredMethods();
        for (int i = 0, len = methods.length; i < len; ++i) {
            if (methods[i].getName().startsWith("set")) {
                methodMap.put(getFieldName(methods[i].getName()), methods[i]);
            }
        }
    }

    private static String getFieldName(String methodName) {
        if (methodName == null) {
            return null;
        }
        byte[] items = methodName.substring(3, methodName.length()).getBytes();
        items[0] = (byte) ((char) items[0] - 'A' + 'a');
        return new String(items);
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        String req = "  1234567890126666666666666669";
        ServiceRequest sr = RequestParserHelper.parseRequest("1001", req.getBytes("GBK"));
        System.out.println(sr);
        System.out.println(classTypeMap.get("ack"));
        System.out.println(methodMap.get("ack"));
    }

}
