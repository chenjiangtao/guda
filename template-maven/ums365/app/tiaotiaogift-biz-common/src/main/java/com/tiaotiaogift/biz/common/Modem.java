/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * 
 * @author gag
 * @version $Id: Modem.java, v 0.1 2012-12-18 上午9:50:57 gag Exp $
 */
public class Modem {
    static {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        URL url = currentClassLoader.getResource("dll/MonDem.dll");
        String path = System.getProperty("java.library.path");
        //    System.setProperty("java.library.path",
        //      path + url.getPath().substring(0, url.getPath().length() - 11) + ";");

        System.loadLibrary("MonDem");
        try {
            // setDLL();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setDLL() throws Exception {
        String libFileName = "MonDem.dll";//dll 文件
        //获取到java.library.path
        String libpath = System.getProperty("java.library.path");
        if (libpath == null || libpath.length() == 0) {
            throw new RuntimeException("java.library.path is null");
        }

        String path = null;
        StringTokenizer st = new StringTokenizer(libpath, System.getProperty("path.separator"));
        if (st.hasMoreElements()) {
            path = st.nextToken();
        } else {
            throw new RuntimeException("can not split library path:" + libpath);
        }
        //把dll文件写入到java.library.path中该dll放在ConvertWord2HM相同目录下，这个可以是你的类名
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        URL url = currentClassLoader.getResource("dll/MonDem.dll");
        InputStream inputStream = url.openStream();
        final File dllFile = new File(new File(path), libFileName);
        if (!dllFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(dllFile);
            byte[] array = new byte[8192];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(array)) != -1) {
                outputStream.write(array, 0, bytesRead);
            }
            outputStream.flush();
            outputStream.close();
        }
        //动态加载dll
        System.out.println(dllFile.getAbsolutePath());
        System.load(dllFile.getAbsolutePath());
    }

    public native int SetModemType(int ComNo, int ModemType);

    public native int GetModemType(int ComNo);

    public native int InitModem(int PortNo);

    public native int SendMsg(int PortNo, String strHeader, String strMsg);

    public native String[] ReadMsgEx(int PortNo);

    public native int CloseModem(int PortNo);

    public native int GetPortMax();

    public native int GetStatus(int PortNo);

    public native int GetSndCount(int PortNo);

    public native int GetRecCount(int PortNo);

    public native int ClrSndBuf(int PortNo);

    public native int ClrRecBuf(int PortNo);

    public native int SetReceive(int Type);

    public native int CancelSend(int Count);

    public native int SetDelayTime(int PortNo, int DelayTime);

    public native String[] WapPushCvt(String strTitle, String strUrl);

    public native int SetThreadMode(int Mode);

    //public native int MonInitModem(String strDev,int num);
    //public native int MonSendMsg(int Chno,String strHeader,String strMsg);
    //public native String[] MonGetMsg(int Chno);
    //public native int MonCloseModem();

    public static void main(String[] args) {

        Modem m = new Modem();
        System.out.println(m.InitModem(-1));
    }

}
