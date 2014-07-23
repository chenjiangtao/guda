/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.key;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.Enumeration;

/**
 * 
 * @author foodoon
 * @version $Id: Key.java, v 0.1 2013-6-8 上午10:06:59 foodoon Exp $
 */
public class Key {

    public static final String solt = "";

    public static void main(String[] args) throws Exception {
        String key = (md5(getMac() + System.getProperty("user.dir")));
        String dir = (System.getProperty("user.dir"));
        File f = new File(dir + File.separator + "agent.key");
        BufferedWriter output = new BufferedWriter(new FileWriter(f));
        output.write(key);
        output.close();
        System.out.println("key 生成成功");
    }

    public static String getK() throws SocketException {
        return (md5(getMac() + System.getProperty("user.dir")));
    }

    private static String getMac() throws SocketException {
        StringBuilder buf = new StringBuilder();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface interface1 = networkInterfaces.nextElement();
            if (interface1.getParent() == null && interface1.getInetAddresses() != null
                && interface1.getHardwareAddress() != null) {
                Enumeration<InetAddress> inetAddrs = interface1.getInetAddresses();

                byte[] btMac = interface1.getHardwareAddress();
                for (int i = 0; i < btMac.length; i++) {
                    buf.append(Integer.toHexString(btMac[i] & 0xff).toUpperCase());
                    if (i < btMac.length - 1)
                        buf.append(":");
                }

            }
        }
        return buf.toString();
    }

    public final static String md5(String plainText) {
        if (plainText == null) {
            return null;
        }
        plainText = plainText + solt;
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
            md5Str = buf.toString().substring(7, 23);
        } catch (Exception e) {

        }
        return md5Str;
    }

    public final static String newmd5(String plainText) {
        if (plainText == null) {
            return null;
        }
        plainText = plainText + "medi";
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
            md5Str = buf.toString();
        } catch (Exception e) {

        }
        return md5Str;
    }

}
