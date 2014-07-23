/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.socket.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Properties;

import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.ResourceUtils;

/**
 * 
 * @author gag
 * @version $Id: HttpClientTest.java, v 0.1 2012-10-17 上午11:19:18 gag Exp $
 */
public class HttpClientTest {

    public static void main(String args[]) {
        PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
        Properties props = new Properties();
        try {
            File file = ResourceUtils.getFile("d:\\abc.txt");
            if (file.exists()) {
                propertiesPersister.load(props, new InputStreamReader(new FileInputStream(file)));
            } else {

            }
        } catch (Exception e) {

        }

        System.out.println(String.format("%06d", 1));
        System.out.println(props.get("b"));
        System.out.println(props.get("a"));

        try {
            ServerSocket serversocket = new ServerSocket();
            serversocket.setReuseAddress(true);

            serversocket.bind(new InetSocketAddress(9001));
            Thread.sleep(15 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
