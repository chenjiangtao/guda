/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.foodoon.monitor.agent.key.Key;
import com.foodoon.monitor.agent.util.TimeHelper;

/**
 * 
 * @author gang
 * @version $Id: App.java, v 0.1 2013-4-22 上午9:52:28 gang Exp $
 */
public class App {

    public static final String sigar_bin = "sigar-bin";

    public static String       root_dir;

    public static String       root_properties;

    public static final String KEY_FILE  = "agent.key";

    public static void check() throws IOException {
        String dir = System.getProperty("user.dir");
        File f = new File(dir + File.separator + KEY_FILE);

        FileReader fr = new FileReader(dir + File.separator + KEY_FILE);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        br.close();
        fr.close();
        if (line.length() == 40) {
            String d = line.substring(3, 11);
            d = TimeHelper.decodeTime(d);
            System.out.println(d);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = dateFormat.parse(d);
                if (date.getTime() < new Date().getTime()) {
                    throw new RuntimeException("序列号过期");
                }
            } catch (ParseException e) {

            }
            String k = line.substring(0, 3) + line.substring(11);
            if (!Key.newmd5(Key.getK()).equals(k)) {
                throw new RuntimeException("序列号错误");
            }
        } else {
            throw new RuntimeException("序列号错误");
        }

    }

    public static void set() throws IOException {

        String dir = System.getProperty("user.dir");
        File f = new File(dir);
        //File pf = f.getParentFile();
        root_dir = f.getAbsolutePath();
        root_properties = root_dir + File.separator + "monitor-agent.properties";
        String sigar_lib = f.getAbsolutePath() + File.separator + sigar_bin + File.separator
                           + "lib";
        String str = System.getProperty("java.library.path");
        System.out.println("current properties:" + root_properties);
        System.out.println("current lib:" + str + File.pathSeparator + sigar_lib);
        System.setProperty("java.library.path", str + File.pathSeparator + sigar_lib);

        //创建config.xml
        File temp = new File(root_dir + File.separator + "config.xml");

        if (!temp.exists()) {
            System.out.println("无法在目录" + temp.getAbsolutePath() + "找到config.xml ,创建config.xml");
            InputStream is = App.class.getClassLoader().getResourceAsStream("config.xml");
            FileOutputStream os = new FileOutputStream(root_dir + File.separator + "config.xml");
            byte b[] = new byte[1024];
            int len;
            try {
                len = is.read(b);
                while (len != -1) {
                    os.write(b, 0, len);
                    len = is.read(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null)
                        is.close();
                    if (os != null)
                        os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setTest() {
        // E:\google-code\foodoon\foodoon-monitor\deploy\sigar-bin\lib

        String dir = System.getProperty("user.dir");
        File f = new File(dir);
        File pf = f.getParentFile();
        root_dir = pf.getAbsolutePath();
        root_properties = root_dir + File.separator + "monitor-agent.properties";
        String sigar_lib = "d:\\google-code\\foodoon-mon\\deploy\\sigar-bin\\lib";
        String str = System.getProperty("java.library.path");
        System.out.println("current lib:" + str + sigar_lib);
        System.setProperty("java.library.path", str + sigar_lib);
    }

}
