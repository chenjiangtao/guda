/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import net.zoneland.gateway.util.Base64;
import net.zoneland.ums.common.util.Counter;
import net.zoneland.ums.service.webservice.form.SendRequest;

import org.codehaus.xfire.client.Client;

import com.google.gson.Gson;

/**
 * 
 * @author zhigang.ge
 * @version $Id: MsgService.java, v 0.1 2010-11-15 ����05:03:56 zhigang.ge Exp $
 */
public class MsgServiceTest {

    static Counter                counter = new Counter("webservice-client");
    private static CountDownLatch signal;

    public static void main(String[] args) throws Exception {
        String wsdl = "http://localhost:8080/ums/services/MsgService?wsdl";
        //  wsdl = "http://172.16.86.103:9081/ums/services/MsgService?wsdl";
        //        MsgService s = (MsgService) BaseWebServiceTest.setWsdlInfo(MsgService.class, wsdl);
        //        XFireProxy proxy = (XFireProxy)Proxy.getInvocationHandler(s);
        //        Client client = proxy.getClient();
        //
        //        client.addOutHandler(new ClientAuthenticationHandler("1000", "1111"));

        SendRequest sr = new SendRequest();

        sr.setAppId("0000");
        sr.setSubAppId("1234");
        sr.setRecvId("15558135733");
        sr.setContent("333测试本应用短信【admin】");
        sr.setAck("0");
        sr.setReply("");
        sr.setPriority("8");
        sr.setRetry("3");
        sr.setAreaNo("33402200112");
        sr.setFlowNo("3");
        sr.setCreateUser("生成人员小B");
        sr.setBizType("3");
        sr.setBizName("营销部");
        //        sr.setTemplateId("1000");
        //sr.setSendDate("20121130120212");
        sr.setValidTime("20");

        Gson gson = new Gson();
        String json = gson.toJson(sr);
        // String result = s.sendMsgJson(json);
        sr.setAppId("1001");
        String json2 = gson.toJson(sr);
        System.out.println(json);

        Client client = new Client(new URL(wsdl)); //根据WSDL创建客户实例  
        //增加用户名，密码验证
        client.addOutHandler(new ClientAuthenticationHandler("0000", "1111"));
        //
        //        Client client2 = new Client(new URL(wsdl)); //根据WSDL创建客户实例  
        //        //增加用户名，密码验证
        //        client2.addOutHandler(new ClientAuthenticationHandler("0000", "1111"));
        long start = System.currentTimeMillis();

        //        for (int i = 0; i < 10; ++i) {
        //            Object[] res = client.invoke("sendMsgJson",
        //            //                    new Object[] { "{\"ack\":\"1\",\"reply\":\"13777379760\",\"priority\":\"9\",\"retry\":\"3\",\"areaNo\":\"03\",\"flowNo\":\"\",\"createUser\":\"liming\",\"bizName\":\"\",\"bizType\":\"\",\"sendDate\":\"20121108133525\",\"validTime\":\"180\",\"appId\":\"1001\",\"subAppId\":\"\",\"recvId\":\"13777379760\",\"content\":\"测试短信\"}" });
        //                new Object[] { new String(Base64.encode(json.getBytes("utf-8"))) });
        //            System.out.println(new String(Base64.decode(res[0].toString().getBytes("utf-8"))));
        //        }
        signal = new CountDownLatch(5);
        Map<Long, Integer> count = new HashMap<Long, Integer>(200);
        MsgServiceTest test = new MsgServiceTest();
        for (int i = 0; i < 1; ++i) {
            new Thread(test.new Send(client, json, start, count, counter, signal)).start();

            //            new Thread(test.new Send(client2, json2, start, count, counter, signal)).start();

        }
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("实际执行时间: " + (System.currentTimeMillis() - start) + "ms");

        // System.out.println(System.currentTimeMillis() - start);

    }

    class Send implements Runnable {
        Client             client;
        String             json;
        long               start;
        Map<Long, Integer> count;
        Counter            counter;
        CountDownLatch     signal;

        public Send(Client client, String json, long start, Map<Long, Integer> count,
                    Counter counter, CountDownLatch signal) {
            this.client = client;
            this.json = json;
            this.start = start;
            this.count = count;
            this.counter = counter;
            this.signal = signal;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                for (int i = 0; i < 5; ++i) {
                    counter.count();
                    Object[] res = client.invoke("sendMsgJson",
                        new Object[] { new String(Base64.encode(json.getBytes("utf-8"))) });

                    System.out.println(json + "\n" + new String(Base64.decode(res[0].toString())));
                }
                signal.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void put() {
            long s = System.currentTimeMillis() / 1000;
            Integer i = count.get(s);
            if (i == null) {
                count.put(s, 1);
            } else {
                count.put(s, ++i);
            }
        }

    }

}
