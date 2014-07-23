/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import net.zoneland.ums.common.util.helper.StringHelper;

import org.junit.Test;

/**
 *
 * @author wangyong
 * @version $Id: phoneHelper.java, v 0.1 2012-9-17 下午1:48:04 wangyongs Exp $
 */
public class PhoneHelper {

    /**
     *对手机号进行简单的加密
     *1.数据位置调换（0->2->4->6->8->10->0)
     *2.数据转换0->1->2->3->4->5->6->7->8->9->0
     *3.加密结束
     * @param phone
     * @return
     */
    public static String encode(String phone) {
        if (phone == null) {
            return null;
        }
        char[] array = phone.toCharArray();
        char[] test = phone.toCharArray();
        for (int i = 0, len = array.length; i < len; i = i + 2) {
            if ((i + 2) > len) {
                array[0] = test[i];
            } else {
                array[i + 2] = test[i];
            }
        }
        for (int i = 0, len = array.length; i < len; i++) {
            if (array[i] == 57) {
                array[i] = 48;
            } else {
                array[i] = (char) (array[i] + 1);
            }
        }
        return String.valueOf(array);
    }

    /**
     *对手机号进行解密
     *1.数据位置调换（0<-2<-4<-6<-8<-10<-0)
     *2.数据转换0<-1<-2<-3<-4<-5<-6<-7<-8<-9<-0
     * @param str
     * @return
     */
    public static String decode(String str) {
        if (StringHelper.isEmpty(str)) {
            return null;
        }
        char[] array = str.toCharArray();
        char[] test = str.toCharArray();
        for (int i = 0, len = array.length; i < len; i = i + 2) {
            if ((i + 2) > len) {
                array[i] = test[0];
            } else {
                array[i] = test[i + 2];
            }
        }
        for (int i = 0, len = array.length; i < len; i++) {
            if (array[i] == 48) {
                array[i] = 57;
            } else {
                array[i] = (char) (array[i] - 1);
            }
        }
        String phone = String.valueOf(array);
        if (!StringRegUtils.isPhoneNumber(phone)) {
            return null;
        }
        return phone;
    }

    /**
     *测试方法
     *
     * @param args
     */
    @Test
    public void test() {
        String phone = "13586216598";
        String test = encode(phone);
        System.out.println("加密前的手机号：" + phone);
        System.out.println("加密后的手机号：" + test);
        System.out.println("解密后的手机号：" + decode(test));
    }
}
