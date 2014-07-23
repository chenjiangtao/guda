/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.common.util;

/**
 * 
 * @author foodoon
 * @version $Id: StringHelper.java, v 0.1 2013年10月26日 上午8:43:58 foodoon Exp $
 */
public class StringHelper {

    public static String subStr(String str, int maxLen) {
        if (str == null) {
            return null;
        }
        if (maxLen < 1) {
            return str;
        }
        if (str.length() > maxLen) {
            return str.substring(0, maxLen) + "...";
        }
        return str;
    }
}
