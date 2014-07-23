/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.test;

import java.io.File;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import net.zoneland.ums.socket.client.ExportExcel;
import net.zoneland.ums.socket.client.SocketClient;
import net.zoneland.ums.socket.form.SocketForm;

import org.apache.log4j.Logger;

/**
 * 
 * @author Administrator
 * @version $Id: LongTest.java, v 0.1 2012-9-30 下午3:02:25 Administrator Exp $
 */
public class SocketTest {

    private static final Logger   logger = Logger.getLogger(SocketTest.class);

    public static final String    HOST   = "localhost";
    //public static final String    HOST   = "172.16.1.21";

    public static final int       PORT   = 7777;

    private static CountDownLatch signal;

    public static void main(String[] args) throws Exception {
        signal = new CountDownLatch(2);
        long start = System.currentTimeMillis();
        //testOneThread("socket_1", "1001");
        for (int i = 0; i < 2; i++) {
            int appId = 1000;
            appId = appId + i;
            String result = "1000";
            new SocketTest().testMoreSocket(i, result, signal);
        }

        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("实际执行时间: " + (System.currentTimeMillis() - start) + "ms");
        Thread.sleep(1000 * 1000);

    }

    public void testMoreSocket(int num, String appId, CountDownLatch signal) {
        String fileName = "socket_" + num;
        new SendMsg(fileName, appId, signal).start();
    }

    /**
     * 单个Socket发送
     * 
     * @throws Exception
     */
    public static void testOneThread(String fileName, String appId, CountDownLatch signal)
                                                                                          throws Exception {
        SocketClient socketClient = new SocketClient();
        Socket socket = new Socket(HOST, PORT);
        socket.setKeepAlive(true);
        String reqLogin = "1001  " + appId + "        1111                ";
        boolean login = socketClient.loginSocket(reqLogin, socket);
        if (!login) {
            System.out.println("登陆不成功");

            return;
        }
        //logger.info("登陆成功！");

        // List<SocketForm> list = socketClient.sendMsg(100, appId, socket, signal);

        List<SocketForm> list = socketClient.fetchMsgSocket(appId, 2, socket, signal);

        String path = getFile(fileName);
        new ExportExcel().packageExcelSocketFormlist(path, list);
    }

    public static String getFile(String fileName) throws Exception {

        String path_base = "e:/" + "/SocketFormExcel";
        File file_b = new File(path_base);
        if (!file_b.exists()) {
            file_b.mkdir();
        }
        String path = path_base + "/" + fileName + "测试导出表.xls";
        logger.info(path);
        File fileOld = new File(path);
        if (fileOld.exists()) {
            fileOld.delete();
        }
        File filenew = new File(path);
        if (!filenew.exists()) {
            filenew.createNewFile();
        }
        return path;
    }

    class SendMsg extends Thread {

        private String         fileName;

        private String         appId;

        private CountDownLatch signal;

        public SendMsg() {

        }

        public SendMsg(String fileName, String appId, CountDownLatch signal) {
            this.fileName = fileName;
            this.appId = appId;
            this.signal = signal;
        }

        @Override
        public void run() {
            try {
                testOneThread(fileName, appId, signal);
                signal.countDown();
                Thread.sleep(1000 * 1000);
            } catch (Exception e) {
                logger.error("线程出错！", e);
            }
        }
    }

}
