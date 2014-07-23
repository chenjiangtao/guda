/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.helper;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * <code>java.lang.String</code>辅助类, 对字符串的一系列函数。
 *
 * @author hh
 * @date Dec 3, 2009 1:40:01 PM
 */
public class StringHelper {

    public static final Logger logger = Logger.getLogger(StringHelper.class);

    private static Pattern     p      = Pattern.compile("\\{\\d+\\}");

    /** 判断不可见字符，包括空格 */
    private static Pattern     p2     = Pattern.compile("\\s*|\t|\r|\n");

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null && str2 != null) {
            return false;
        }
        if (str2 == null && str1 != null) {
            return false;
        }
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    public static String replaceHideChar(String str) {

        if (str == null) {
            return null;
        }
        char[] c = str.toCharArray();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < c.length; ++i) {
            if (c[i] >= 0x20 && c[i] <= 0x7f) {
                buf.append(c[i]);
            } else {
                logger.error("字符含有不可见字符:[" + str + "]");
            }
        }
        return buf.toString();

    }

    /**
     * 分割接收方
     * @param desc
     * @return
     */
    public static String[] splitRecvId(String desc) {
        if (desc == null) {
            return new String[] {};
        }
        return desc.split("[, :;]");

    }

    /**
     * 判断字符串是否为空
     * @return boolean 空返回true 不为空返回lase
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符不为NULL，""，null值
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断一个对象是否为null ，String 值是否为空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else {
            return isEmpty(o.toString());
        }
    }

    /**
     * 从开始位置，删除指定个数的字符串
     * @return
     */
    public static String delStartChar(String src, int len) {
        if (null != src && src.length() > 0) {
            src = src.substring(len);
        }
        return src;
    }

    /**
     * 从结束位置，删除指定个数的字符串
     * @param src 源字符串
     * @param len 要删除的长度
     * @return
     */
    public static String delEndChar(String src, int len) {
        if (null != src && src.length() > 0) {
            src = src.substring(0, src.length() - len);
        }
        return src;
    }

    /**
     * 将给定的params按顺序拼接起来
     * @param params 需要拼接的参数
     * @return
     */
    public static String append(Serializable... params) {
        StringBuffer sb = new StringBuffer();
        for (Serializable s : params) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * <p>
     * 根据参数的类型,解析含有占位符的字符串，
     * 字符类型按:'参数'的格式替换 数字类型不加任何字符直接替换
     * </p>
     *
     * @param sql
     *            需要格式化的字符串
     * @param params
     *            要替换的可变参数
     * @return 格式化后的字符串
     */
    public static String format(String source, Object... params) {
        return parses(source, "'", params);
    }

    /**
     * <p>
     *  忽略参数的类型，按照给定的startChar,endChar替换字符串
     * </p>
     *
     * @param source
     *            源字符串
     * @param start
     *            参数首位需要加的字符
     * @param end
     *            参数后面需要添加的字符
     * @param params
     *            根据占位符给定的参数
     * @return
     */
    public static String formatIgnoreType(String source, Object... params) {
        return parses(source, "", params);
    }

    /**
     * <p> 功能：格式化带有'{0}'占位符的字符串 </p>
     * @param source 源字符串
     * @param split 添加在目标匹配的字符串开始和结尾的字符
     * @param params 可变的参数，根据{0}的个数传入相应的参数值
     * @return 格式化后的字符串
     */
    private static String parses(String source, String split, Object... params) {
        Matcher m = p.matcher(source);
        int i = 0;
        int endIndex = 0;
        StringBuffer buf = new StringBuffer();
        while (m.find()) {
            if ((params[i] instanceof String) || (params[i] instanceof Date)
                || (params[i] instanceof Character))
                m.appendReplacement(buf, split + params[i++].toString() + split);
            else
                m.appendReplacement(buf, params[i++].toString());
            endIndex = m.end();
        }
        if (endIndex > 0)
            buf.append(source.substring(endIndex)).toString();
        else
            return source;
        return buf.toString();
    }

    /**
     * 根据参数类型，生成随机主键
     * @param type
     * @return
     */
    public static String creatId(String type) {
        return type + "-" + System.currentTimeMillis() + "-"
               + RandomStringUtils.random(8, true, true);
    }

    /**
     * 判断指定的字符串在数组中是否存在
     * @param src 集合
     * @param target 要判断的字符串
     * @return
     */
    public static boolean contains(String[] src, String target) {
        if (src == null)
            return false;
        for (String s : src) {
            if (s.equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 追加配置的filter
     * @param src 源filter
     * @param content 配置的filter
     * @return
     */
    public static String appendFilter(String src, String content) {
        StringBuffer sb = new StringBuffer(src);
        if (StringHelper.isNotEmpty(src)) {
            sb.insert(sb.lastIndexOf(")"), content);
            return sb.toString();
        } else {
            return content;
        }
    }

    /**
     * 逗号隔开输出字符串数组
     *
     * @param src 字符串数组
     * @return
     */
    public static String toString(String[] src) {
        if (src == null || src.length == 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (String s : src) {
                sb.append(s + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    /**
     * 获取文本值，如果文本值为NULL,则返回""空字符串
     *
     * @return
     */
    public static String getText(String src) {
        return src == null ? "" : src;
    }

    /**
     * 截取方法名称并与制定的regex进行匹配，匹配成功返回true,反之.
     *
     * @param methodDefined
     *  examples: public java.util.List com.land.ldap.core.AbstractEntityManager.queryForList(java.lang.String,java.lang.String) throws com.land.ldap.core.LdapException
     * @param matches
     *  examples: ".*query.*"
     * @return
     */
    public static boolean methodMatches(String methodDefined, String matches) {
        Pattern p = Pattern.compile("\\..*\\(");
        Matcher m = p.matcher(methodDefined);
        if (m.find()) {
            String simpleMethodName = methodDefined.substring(m.start(), m.end());
            simpleMethodName = simpleMethodName.substring(simpleMethodName.lastIndexOf(".") + 1,
                simpleMethodName.length() - 1);
            return simpleMethodName.matches(matches);
        }
        return false;
    }

    /**
     * 去空格
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (StringHelper.isNotEmpty(str)) {
            return str.trim();
        }
        return str;
    }

    /**
     * 验证是否为数字
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        if (str != null && str.length() > 0) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 获取唯一的字符串
     * @return
     */
    public static String getUniqueString() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断字符串长度，如果是英文占1个字符，如果是中文占3个字符
     * 
     * @param value
     * @return
     */
    public static int getStringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 3;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 得到 全拼
     * 
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 得到中文首字母
     * 
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     * 
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * 将当前页数pageId的String类型转换成int类型
     * 
     * @param pageId
     * @return
     */
    public static int parseInt(String pageId) {
        if (StringUtils.isBlank(pageId)) {
            return 1;
        }
        try {
            int page = Integer.parseInt(pageId);
            if (page < 1) {
                page = 1;
            }
            return page;
        } catch (Exception e) {
            return 1;
        }
    }
}