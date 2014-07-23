/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gag
 * @version $Id: StringUtils.java, v 0.1 2012-5-12 上午11:41:44 gag Exp $
 */
public class StringRegUtils {

    public static final String regExp         = "^[1]([3][0-9]|[5][0-9]|[8][0-9])[0-9]{8}$";
    public static final String regExpMobile   = "^[1]([3][4-9]|[4][7]|[5][0-2]|[5][7-9]|[8][2-3]|[8][7-8])[0-9]{8}$"; //匹配移动手机用户的正则表达式
    public static final String regExpUnicom   = "^[1]([3][0-2]|[4][5]|[5][5-6]|[8][5-6])[0-9]{8}$";                  //匹配联通手机用户的正则表达式
    public static final String regExpTelecom  = "^[1]([3][3]|[5][3]|[8][0]|[8][9])[0-9]{8}$";                        //匹配电信手机用户的正则表达式
    public static final String regExpNumber   = "^[0-9]+$";

    public static Pattern      pattern        = Pattern.compile(regExp);
    public static Pattern      patternMobile  = Pattern.compile(regExpMobile);
    public static Pattern      patternUnicom  = Pattern.compile(regExpUnicom);
    public static Pattern      patternTelecom = Pattern.compile(regExpTelecom);
    public static Pattern      patternNumber  = Pattern.compile(regExpNumber);

    /**
     * 判断字符串是否为手机号
     * @param str 需要判断的字符串
     * @return true-》字符串为手机号
     */
    public static boolean isPhoneNumber(String str) {
        if (str == null || !org.springframework.util.StringUtils.hasText(str)) {
            return false;
        }
        str = org.springframework.util.StringUtils.trimWhitespace(str);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();

    }

    /**
     * 根据制定的字符串判断字符串是否为手机号
     * @param regExp 正则表达式
     * @param str  需要判断的字符串
     * @return true->根据regExp判断str为手机号
     */
    public static boolean isPhoneNumber(String regExp, String str) {
        if (str == null || !org.springframework.util.StringUtils.hasText(str)) {
            return false;
        }
        str = org.springframework.util.StringUtils.trimWhitespace(str);
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();

    }

    public static boolean getBoolean(String str) {
        if (str == null || !org.springframework.util.StringUtils.hasText(str)) {
            return false;
        }
        str = org.springframework.util.StringUtils.trimWhitespace(str);
        if ("1".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定的字符窜是否为移动号码
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (str == null) {
            return false;
        }
        return patternMobile.matcher(str).find();
    }

    /**
     * 判断给定的字符窜是否为联通号码
     * @param str
     * @return
     */
    public static boolean isUnicom(String str) {
        if (str == null) {
            return false;
        }
        return patternUnicom.matcher(str).find();
    }

    /**
     * 判断给定的字符窜是否为电信号码
     * @param str
     * @return
     */
    public static boolean isTelecom(String str) {
        if (str == null) {
            return false;
        }
        return patternTelecom.matcher(str).find();
    }

    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        }
        return patternNumber.matcher(str).find();
    }

    public static void main(String argsp[]) {
        System.out.println(isMobile("13588776655"));
        System.out.println(isMobile("13588771122"));
    }

}
