/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.queue.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import net.zoneland.mvc.runtime.core.queue.MergeDelayQueue;

/**
 * 
 * @author gag
 * @version $Id: Test2.java, v 0.1 2012-5-3 ÏÂÎç9:21:26 gag Exp $
 */
public class Test2 extends Test {

    private transient final ReentrantLock lock      = new ReentrantLock();
    private transient final Condition     available = lock.newCondition();

    public String get2() {

        this.lock.lock();
        try {
            System.out.println("test2");
            return "test";
        } finally {
            this.lock.unlock();
        }

    }

    final MergeDelayQueue<Msg> mq = new MergeDelayQueue<Msg>();

    public static void main(String args[]) {
        Test2 tt = new Test2();
        Producter p = tt.new Producter();
        Thread pt = new Thread(p);

        Consumer c = tt.new Consumer();
        Thread ct = new Thread(c);
        pt.start();
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sleep...");
        ct.start();
    }

    class Producter implements Runnable {

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {

            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 5; ++j) {

                    if (i == 0) {
                        Msg msg = new Msg(j, false, System.currentTimeMillis() + 1 * 1000 * j);
                        mq.offerAndMerge(msg);
                        System.out.println("add:" + msg);
                    } else {
                        Msg msg = new Msg(j, false);
                        mq.offerAndMerge(msg);
                        System.out.println("add:" + msg);
                    }

                    /* if (i == 2) {
                         try {
                             Thread.sleep(5 * 1000);
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     }*/

                }
            }

        }
    }

    class Consumer implements Runnable {

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            while (true) {
                Msg msg = mq.poll();
                if (msg != null)
                    System.out.println("poll:" + System.currentTimeMillis() + "-" + msg + ".."
                                       + msg.getMsgs().size());
            }

        }

    }

}
