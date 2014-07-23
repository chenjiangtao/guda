/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.MsgHandler;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.SocketContext;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.service.socket.rule.RequestParserHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gag
 * @version $Id: SocketReader.java, v 0.1 2013-1-1 下午10:59:30 gag Exp $
 */
public class SocketReader extends Thread {

    private static final Logger logger    = LoggerFactory.getLogger(SocketReader.class);

    private Socket              socket;                                                 // socket for reading
    private DataInputStream     in;                                                     // input stream
    private DataOutputStream    out;

    private String              clientIP  = null;
    private int                 clientPort;

    private MsgHandler          msgHandler;

    private String              loginAppId;

    private List<SocketReader>  runSocket = new ArrayList<SocketReader>();

    public SocketReader(Socket socket, MsgHandler msgHandler, List<SocketReader> runSocket) {
        this.socket = socket;
        this.msgHandler = msgHandler;
        this.runSocket = runSocket;
        this.setDaemon(true);
        start();
    }

    public void run() {
        int length = -10;
        byte[] msgBody = null;
        try {
            //socket.setSoTimeout(3 * 1000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            clientIP = socket.getInetAddress().getHostAddress();
            clientPort = socket.getPort();
            setName(String.format("%s:%d", clientIP, clientPort));

            while (socket != null) {
                if ((length = getMsgLen()) <= 0) {
                    break;
                }
                msgBody = this.readbytes(length);
                if (logger.isInfoEnabled()) {
                    logger.info("recv msg :[" + new String(msgBody, "GBK") + "]");
                }
                String msgCode = new String(msgBody, 0, 4);
                byte[] body = new byte[length - 4];
                System.arraycopy(msgBody, 4, body, 0, length - 4);
                ServiceRequest sr = null;
                sr = RequestParserHelper.parseRequest(msgCode, body);
                if (logger.isInfoEnabled()) {
                    logger.info("recv socket msg:[" + sr + "]");
                }
                if (sr != null) {
                    sr.setClientIp(clientIP);
                    sr.setClientPort(clientPort);
                    ProcessResult pr = msgHandler.processMsg(sr, loginAppId);
                    if (logger.isInfoEnabled()) {
                        logger.info("socket msg process result:[" + pr + "]");
                    }
                    if (!CodeConstants.REQUEST_1005.equals(sr.getRequestCode())) {
                        this.writeSocket(buildResponse(pr.getMsg()));
                    }
                    if (CodeConstants.REQUEST_1001.equals(sr.getRequestCode()) && pr.isSuccess()) {
                        loginAppId = sr.getAppId();
                    }
                }
            }
        } catch (NumberFormatException ex) {
            try {

                logger.error("数据包长度不正确", ex);
                this.writeSocket("1003");
                Thread.sleep(5 * 1000);
            } catch (Exception e) {
            }

        } catch (Exception e) {
            logger.error("socket error", e);
        } finally {
            close();
            SocketContext.removeLoginContext(loginAppId, clientIP, clientPort);

        }
    }

    public static String buildResponse(String msg) {
        int length = 0;
        try {
            length = msg.getBytes("GBK").length;
        } catch (Exception e) {
            length = msg.getBytes().length;
        }

        return String.format("%-5d%s", length, msg);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("appid=").append(loginAppId).append(",loginCount=")
            .append(SocketContext.getLoginAppCount(loginAppId)).append(",clientIP=")
            .append(clientIP).append(",clientPort").append(clientPort).append(",socket=")
            .append(socket);
        return buf.toString();
    }

    public void close() {
        if (in != null) {
            try {
                in.close();
                in = null;
            } catch (IOException ex) {
            }
        }
        if (out != null) {
            try {
                out.close();
                out = null;
            } catch (IOException ex) {
            }
        }
        if (socket != null) {
            try {
                socket.close();
                socket = null;

            } catch (IOException ex) {

            }
        }
        runSocket.remove(this);

    }

    public void writeSocket(String msg) throws IOException {

        out.write(msg.getBytes("GBK"));
        out.flush();
    }

    public int getMsgLen() throws IOException {
        String recv_len;

        recv_len = readbuffer(5);

        recv_len = recv_len.trim();
        if (recv_len.length() <= 0) {
            return -1;
        } else {
            return Integer.parseInt(recv_len);
        }
    }

    public String readbuffer(int n) throws IOException {
        return new String(this.readbytes(n));
    }

    public byte[] readbytes(int n) throws IOException {
        byte buf[] = new byte[n];

        int nn = in.read(buf);
        if (nn == -1) {
            return buf;
        } else if (nn == n) {
            return buf;
        }

        return buf;
    }

}
