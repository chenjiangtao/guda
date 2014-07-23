/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.common.util.cluster;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 * @version $Id: MonitorCluster.java, v 0.1 2013-1-8 下午3:43:22 Administrator Exp $
 */
public class MonitorCluster {

    public static final Logger logger = Logger.getLogger(MonitorCluster.class);

    /**
     * 监察集群里的各个服务器是否正常运行，如果五次检查都不正常运行的话，如果进行该台服务器的数据修改.
     */
    public static boolean monitor(String host) {
        if (host == null) {
            return false;
        }
        //测试服务器的状态，如果返回两百就表示正常
        int result = test(host);
        if (result == 200) {
            return true;
        }
        return false;
    }

    /**
     * 返回200表示正确表示.
     *
     * @param host
     * @return
     */
    public static int test(String host) {
        String url = "http://" + host + "/ums/msg/msg.htm";
        logger.info("监测地址：" + url);
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            logger.warn("url地址错误");
            return 0;
        }
        int result = 0;
        try {
            HttpURLConnection Connection = (HttpURLConnection) u.openConnection();
            result = Connection.getResponseCode();
        } catch (Exception e) {
            logger.warn("url连接错误：" + host);
            return 0;
        }
        return result;
    }

    @SuppressWarnings("unused")
    private String[] getHost(String host) {
        if (host == null) {
            return null;
        }
        String[] hosts = host.split(",");
        return hosts;
    }

}
