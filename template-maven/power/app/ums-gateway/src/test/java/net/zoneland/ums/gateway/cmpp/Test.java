/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway.cmpp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author gang
 * @version $Id: Test.java, v 0.1 2013-2-20 上午11:19:56 gang Exp $
 */
public class Test {

    private TestB           b               = new TestB();

    private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 3, TimeUnit.SECONDS,
                                                new ArrayBlockingQueue(500));

    public Object send() {
        Send s = new Send();
        // Future future1 = executorService.submit(s);
        try {
            return s.call();
        } catch (Exception e) {
            System.out.println("timeout...........");
            e.printStackTrace();
        }
        return "err result";
    }

    class Send implements Callable {

        /** 
         * @see java.util.concurrent.Callable#call()
         */
        public Object call() throws Exception {
            return b.ready();
        }

    }

    public static void main(String args[]) {
        Test t = new Test();

        System.out.println(t.send());

        System.out.println("all");
    }
}
