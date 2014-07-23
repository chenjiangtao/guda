/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author gang
 * @version $Id: SocketContext.java, v 0.1 2012-8-12 上午8:06:52 gang Exp $
 */
public class SocketContext {

    private SocketContext() {

    }

    private static Map<String, SocketOperator> map           = new HashMap<String, SocketOperator>();

    private static Map<String, Integer>        mapLoginCount = new HashMap<String, Integer>();

    public static boolean isLogin(String appId, String clientIp, int clientPort) {
        boolean res = map.get(getKey(appId, clientIp, clientPort)) != null;
        if (res) {

        }
        return res;

    }

    /**
     * 创建登录上下文
     * @param appId
     * @param clientIp
     * @param clientPort
     * @param serverPort
     * @return
     */
    public static SocketOperator createLoginContext(String appId, String clientIp, int clientPort,
                                                    int serverPort) {
        String key = getKey(appId, clientIp, clientPort);

        synchronized (mapLoginCount) {
            Integer count = mapLoginCount.get(appId);
            int c = 0;
            if (count != null) {
                c = count.intValue();
            }
            if (!"1053".equals(appId) && c > 10) {
                return null;
            } else {
                mapLoginCount.put(appId, c + 1);
                SocketOperator so = new SocketOperator();
                so.setAppId(appId);
                so.setClientIp(clientIp);
                so.setClientPort(clientPort);
                so.setServerPort(serverPort);
                so.setLoginTime(new Date());
                map.put(key, so);
                return so;
            }
        }

    }

    public static int getLoginAppCount() {
        return mapLoginCount.size();
    }

    public static int getLoginAppCount(String appId) {
        if (mapLoginCount.containsKey(appId)) {
            return mapLoginCount.get(appId);
        }
        return 0;
    }

    public static int getConnectCount() {
        return map.size();
    }

    /**
     * 从上下文中移除登录信息
     * @param appId
     * @param clientIp
     * @param clientPort
     */
    public static void removeLoginContext(String appId, String clientIp, int clientPort) {
        String key = getKey(appId, clientIp, clientPort);
        map.remove(key);
        synchronized (mapLoginCount) {
            Integer count = mapLoginCount.get(appId);
            if (count != null && count > 0) {
                mapLoginCount.put(appId, --count);
            }
        }

    }

    public static Map<String, SocketOperator> getMap() {
        return map;
    }

    public static Map<String, Integer> getMapLoginCount() {
        return mapLoginCount;
    }

    public static String getKey(String appId, String clientIp, int clientPort) {
        return new StringBuilder().append(appId).append("-").append(clientIp).append("-")
            .append(clientPort).toString();
    }

}
