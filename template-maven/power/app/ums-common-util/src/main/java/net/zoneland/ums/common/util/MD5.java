/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.security.MessageDigest;

/**
 * 
 * @author XuFan
 * @version $Id: MD5.java, v 0.1 Aug 23, 2012 12:45:56 PM XuFan Exp $
 */
public class MD5 {

    public final static String md5(String plainText) {
        String md5Str = null;
        try {
            StringBuffer buf = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            md5Str = buf.toString().substring(8, 24);
        } catch (Exception e) {

        }
        return md5Str;
    }

    public static void main(String[] args) {
        System.out.println(MD5.md5("qzep4101"));
    }

}