/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

/**
 * 
 * @author foodoon
 * @version $Id: TimeHelper.java, v 0.1 2013年9月14日 下午5:34:55 foodoon Exp $
 */
public class TimeHelper {

    public static char[] time = new char[] { 'b', 'f', '5', 'a', '9', 'd', '6', 'k', 'z', 'n' };

    public static String encodeTime(String date) {
        if (date == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        if (date.length() == 8) {
            for (int i = 0; i < 8; ++i) {
                char c = date.charAt(i);
                buf.append(time[c - '0']);
            }
        }
        return buf.toString();
    }

    public static String decodeTime(String date) {
        if (date == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        if (date.length() == 8) {
            for (int i = 0; i < 8; ++i) {
                char c = date.charAt(i);
                buf.append(indexOf(c));
            }
        }
        return buf.toString();
    }

    public static int indexOf(char c) {
        for (int i = 0; i < 10; ++i) {
            if (time[i] == c) {
                return i;
            }
        }
        return 11;
    }

    public static void main(String[] args) {
        String s = encodeTime("20991219");
        System.out.println((s));
        System.out.println(decodeTime(s));
    }

}
