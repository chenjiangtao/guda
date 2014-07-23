package net.zoneland.ums.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import net.zoneland.ums.socket.form.SocketForm;

import org.apache.log4j.Logger;

public class SocketClient {

    private static final Logger logger = Logger.getLogger(SocketClient.class);

    public Socket getConnect(String host, int port) {
        Socket socket;
        try {
            socket = new Socket(host, port);
            return socket;
        } catch (Exception e) {
            logger.error("创建socket客户有误", e);
            return null;
        }

    }

    //    /**
    //     * 多线程发送消息:</br>
    //     * 1.可以设置发送线程的个数。</br>
    //     * 2.可以设置每个线程发送消息的个数。
    //     * 
    //     * @param num 每个线程发送消息的个数
    //     * @param msg 消息内容
    //     * @param threadNum 线程个数
    //     */
    //    public void sendMsg(int num, byte[] msg, Socket socket, int threadNum) {
    //        for (int i = 0; i < threadNum; i++) {
    //            new SendMsg(num, msg, socket).start();
    //        }
    //    }

    /**
     * 单线程发送消息</br>
     * 1.可以设置发送消息的
     * @param num 发送条数
     * @param msg 发送内容
     */
    public List<SocketForm> sendMsg(int num, String appId, Socket socket, CountDownLatch signal) {
        try {
            return sendMsgSocket(appId, num, socket, signal);
        } catch (Exception e) {
            logger.error("socket发送消息出现异常", e);
            return Collections.emptyList();

        }
    }

    public boolean loginSocket(String reqLogin, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        outputStream.write(buildMsg(reqLogin).getBytes("GBK"));
        //outputStream.write("abdafdsafd");
        outputStream.flush();
        byte[] bytes = new byte[5];
        in.read(bytes);
        String str = new String(bytes).trim();
        int n = Integer.valueOf(str);
        byte[] byt = new byte[n];
        in.read(byt);
        str = new String(byt, "GBK");
        System.out.println(str);
        if ("0000".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    public List<SocketForm> sendMsgSocket(String appId, int num, Socket socket,
                                          CountDownLatch signal) throws Exception {

        OutputStream outputStream = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        List<SocketForm> list = new ArrayList<SocketForm>();

        for (int i = 0; i < num; i++) {
            byte[] msg = buildSendMsg(appId, "", "0", "18812341234", String.valueOf(i), "0", "",
                "1", "").getBytes("GBK");
            sendMsg(outputStream, msg, is, list);

            // Thread.sleep(3);
        }

        return list;

    }

    public List<SocketForm> fetchMsgSocket(String appId, int num, Socket socket,
                                           CountDownLatch signal) throws Exception {

        OutputStream outputStream = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        List<SocketForm> list = new ArrayList<SocketForm>();

        for (int i = 0; i < num; i++) {
            byte[] msg = buildFetchMsg(appId).getBytes("GBK");
            sendMsg(outputStream, msg, is, list);

            // Thread.sleep(3);
        }

        return list;

    }

    //TODO:如果单个socket多线程发送的话，一定要加同步锁
    public void sendMsg(OutputStream outputStream, byte[] msg, InputStream is, List<SocketForm> list)
                                                                                                     throws Exception {
        long start = new Date().getTime();
        outputStream.write(msg);
        outputStream.flush();
        byte[] bytes = new byte[1024];
        is.read(bytes);
        long end = new Date().getTime();
        long result = end - start;
        String name = Thread.currentThread().getName();
        String statue = new String(bytes, "GBK");
        System.out.println(statue);
        SocketForm socketForm = new SocketForm();
        socketForm.setThreadName(name);
        socketForm.setStatue(statue);
        socketForm.setTime(String.valueOf(result));
        socketForm.setSendTime(new Date());
        list.add(socketForm);

    }

    /**
     * 创建socket发送的消息
     * 
     * @param appId
     * @param AppSerialNo
     * @param MessageType
     * @param RecvId
     * @param Content
     * @param Ack
     * @param Reply
     * @param Priority
     * @param rep
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String buildSendMsg(String appId, String AppSerialNo, String MessageType, String RecvId,
                               String Content, String Ack, String Reply, String Priority, String rep)
                                                                                                     throws UnsupportedEncodingException {
        StringBuilder buf = new StringBuilder();
        buf.append("1002  ").append(getFixedStr(appId, 12)).append(getFixedStr(AppSerialNo, 35))

        .append(getFixedStr(MessageType, 3)).append(getFixedStr(RecvId, 255))
            .append(getFixedStr(Content, 160)).append(getFixedStr(Ack, 1))
            .append(getFixedStr(Reply, 30)).append(getFixedStr(Priority, 2))
            .append(getFixedStr(rep, 2));
        String msg = buf.toString();
        String lens = "506";
        //System.out.println("短信长度" + lens);
        StringBuilder buf2 = new StringBuilder();
        buf2.append(lens);
        for (int i = 0, len = lens.length(); i < 5 - len; ++i) {
            buf2.append(" ");
        }
        return buf2.toString() + msg;

    }

    public String buildFetchMsg(String appId) throws UnsupportedEncodingException {
        StringBuilder buf = new StringBuilder();
        buf.append("1004  ").append(getFixedStr(appId, 12));
        String msg = buf.toString();
        String lens = String.valueOf(msg.length());
        //System.out.println("短信长度" + lens);
        StringBuilder buf2 = new StringBuilder();
        buf2.append(lens);
        for (int i = 0, len = lens.length(); i < 5 - len; ++i) {
            buf2.append(" ");
        }
        return buf2.toString() + msg;

    }

    /**
     * 补充空白
     * 
     * @param msg
     * @param len
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String getFixedStr(String msg, int len) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer(msg);
        int bytes = msg.getBytes("gbk").length;
        for (int i = bytes + 1; i <= len; i++) {
            sb.append(' ');
        }

        return sb.toString();
    }

    public static String buildMsg(String msg) {

        String lens = "38";
        //System.out.println(lens);
        StringBuilder buf = new StringBuilder();
        buf.append(lens);
        for (int i = 0, len = lens.length(); i < 5 - len; ++i) {
            buf.append(" ");
        }
        return buf.toString() + msg;
    }

    //    class SendMsg extends Thread {
    //        int    num;
    //
    //        byte[] bytes;
    //
    //        Socket socket;
    //
    //        public SendMsg() {
    //
    //        }
    //
    //        public SendMsg(int num, byte[] bytes, Socket socket) {
    //            this.num = num;
    //            this.bytes = bytes;
    //            this.socket = socket;
    //        }
    //
    //        @Override
    //        public void run() {
    //
    //            sendMsg(num, bytes, socket);
    //        }
    //    }

}