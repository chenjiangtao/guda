/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import net.zoneland.ums.common.util.Counter;
import net.zoneland.ums.service.webservice.MsgService;
import net.zoneland.ums.service.webservice.form.SendRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * @author gag
 * @version $Id: MsgServicePoolTest.java, v 0.1 2012-10-22 下午4:49:20 gag Exp $
 */
public class MsgServicePoolTest {

    static Counter                counter = new Counter("webservice-client");

    private static CountDownLatch signal;

    public static void main(String[] args) throws UnsupportedEncodingException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("webservice.test.xml");
        MsgService s = (MsgService) appContext.getBean("msgServiceClient");
        SendRequest sr = new SendRequest();
        // XFireClientFactoryBean b = (XFireClientFactoryBean) appContext.getBean("baseWebService");
        //System.out.println(b.getOutHandlers().size());
        sr.setAppId("1002");
        sr.setContent("测试");
        sr.setRecvId("13588754677");

        sr.setPriority("1");
        sr.setAck("1");
        //sr.setTemplateId("1341");
        Gson gson = new Gson();
        String json = Base64.encode(gson.toJson(sr).getBytes("UTF-8"));
        //  s.sendMsgJson(json);

        long start = System.currentTimeMillis();
        signal = new CountDownLatch(50);
        Map<Long, Integer> count = new HashMap<Long, Integer>(200);
        MsgServicePoolTest test = new MsgServicePoolTest();
        for (int i = 0; i < 50; ++i) {
            new Thread(test.new Send(s, json, start, count, counter, signal)).start();

        }
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("实际执行时间: " + (System.currentTimeMillis() - start) + "ms");
    }

    class Send implements Runnable {
        MsgService         s;
        String             json;
        long               start;
        Map<Long, Integer> count;
        Counter            counter;
        CountDownLatch     signal;

        public Send(MsgService s, String json, long start, Map<Long, Integer> count,
                    Counter counter, CountDownLatch signal) {
            this.s = s;
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
                for (int i = 0; i < 100; ++i) {
                    counter.count();
                    String res = s.sendMsgJson(json);

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
