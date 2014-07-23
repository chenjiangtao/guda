package net.zoneland.ums.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocektServer {

    private static final Logger logger = Logger.getLogger(SocektServer.class);

    private static final int    PORT   = 8888;

    public static void main(String[] args) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("启动服务器");
        }
        SocektServer server = new SocektServer();
        server.start();

    }

    public void start() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            Socket s = ss.accept();
            new Service(s).start();
        }
    }

    /** 客户服务线程, 为每个客户开启一个线程实例 */
    private class Service extends Thread {
        Socket s;

        public Service(Socket s) {
            this.s = s;
        }

        /** run方法中要完成对客户的服务处理 */
        @Override
        public void run() {
            try {
                logger.info("服务器已收到！");
                InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream();
                //                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                out.write("服务器已收到".getBytes());
                out.flush();
                while (true) {
                    if (s.isClosed()) {
                        System.out.println("停止连接了！");
                        break;
                    }
                    logger.info("开始读取信息");
                    //                    String str = reader.readLine();
                    //                    System.out.println(str);
                    byte[] bytes = new byte[1024];
                    in.read(bytes);
                    String str = new String(bytes);
                    System.out.println(str);
                    if (s.isConnected()) {
                        out.write("服务器已收到".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
