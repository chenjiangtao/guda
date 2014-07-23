/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author gang
 * @version $Id: Host.java, v 0.1 2013-1-21 下午5:31:34 gang Exp $
 */
public class Host {

    public static String getHost() {
        try {

            return InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException e1) {

        }
        return null;
    }

}
