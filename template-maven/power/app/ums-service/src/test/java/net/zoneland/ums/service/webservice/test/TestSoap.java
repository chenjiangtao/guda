/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.xfire.client.Client;

/**
 * 
 * @author gang
 * @version $Id: TestSoap.java, v 0.1 2013-5-4 下午8:35:15 gang Exp $
 */
public class TestSoap {
    public static void main(String[] args) throws MalformedURLException, Exception {
        String serviceUrl = "http://61.147.124.107:8080/SGIP/SGIPService.php?wsdl";
        Client client = new Client(new URL(serviceUrl));
        long start = System.currentTimeMillis();
        Object[] results = client.invoke("sendSms", new Object[] { "uid", "pwd", "mobile",
                new String("JAVA测试短信通过2008-11-13".getBytes(), "utf-8"), "", "", "" });
        System.out.println(results[0]);
        System.out.println((System.currentTimeMillis() - start) / 1000);
    }

}
