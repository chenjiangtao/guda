/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.common;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.zoneland.ums.biz.msg.socket.MsgHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 implements InitializingBean
 * @author gag
 * @version $Id: SocketServer.java, v 0.1 2013-1-1 下午10:55:38 gag Exp $
 */
public class SocketServer {

    private static final Logger   logger          = LoggerFactory.getLogger(SocketServer.class);

    protected static final Logger stat            = LoggerFactory.getLogger("STATISTIC");

    private String                port            = "10000";

    private boolean               stop            = false;

    private List<SocketReader>    runSocket       = new ArrayList<SocketReader>();

    private List<ServerSocket>    runServerSocket = new ArrayList<ServerSocket>();

    @Autowired
    private MsgHandler            msgHandler;

    public void stop() {
        logger.info("stop SocketServer..........");
        this.stop = true;
        for (int j = 0, lenj = runServerSocket.size(); j < lenj; ++j) {
            try {
                runServerSocket.get(j).close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        for (int j = 0, lenj = runSocket.size(); j < lenj; ++j) {
            try {
                runSocket.get(j).interrupt();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public void initSocketServer() {
        logger.info("initSocketServer..........");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MonitorTask(), 1, 60 * 1000);
        String[] addrs = port.split(",");
        for (int i = 0, len = addrs.length; i < len; ++i) {
            String[] addr = addrs[i].split(":");
            InetSocketAddress a = null;
            if (addr.length == 1) {
                a = new InetSocketAddress(Integer.parseInt(addr[0]));
            } else if (addr.length == 2) {
                a = new InetSocketAddress(addr[0], Integer.parseInt(addr[1]));
            }
            try {
                ServerSocket s = new ServerSocket(a.getPort(), 50, a.getAddress());
                runServerSocket.add(s);
                while (!stop) {
                    Socket socket = null;
                    SocketReader client = null;
                    try {
                        socket = s.accept();
                        client = new SocketReader(socket, msgHandler, runSocket);
                        runSocket.add(client);
                    } catch (Exception sx) {

                        sx.printStackTrace(System.out);
                        if (socket != null) {
                            socket.close();
                        }
                        if (client != null) {
                            client.close();
                        }
                    }
                }
                for (int j = 0, lenj = runServerSocket.size(); j < lenj; ++j) {
                    runServerSocket.get(j).close();
                }
            } catch (Exception ex) {
                logger.error("initSocketServer error", ex);
                throw new RuntimeException(ex);
            }

            logger.info("initSocketServer on port[" + addrs[i] + "]success.");
        }

    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setMsgHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    class MonitorTask extends TimerTask {

        public void run() {
            List<SocketReader> temp = new ArrayList<SocketReader>(runSocket);
            StringBuilder buff = new StringBuilder();
            for (int i = 0, len = temp.size(); i < len; ++i) {
                buff.append(temp).append("\n");
            }

            stat.warn(buff.toString());
        }
    }
}
