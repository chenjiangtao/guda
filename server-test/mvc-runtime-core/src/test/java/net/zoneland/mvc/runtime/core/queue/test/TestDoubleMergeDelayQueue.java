/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.queue.test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

import net.zoneland.mvc.runtime.core.queue.DoubleMergeDelayQueue;
import net.zoneland.mvc.runtime.core.queue.MergeDelayQueue;

/**
 * 
 * @author gag
 * @version $Id: TestDoubleMergeDelayQueue.java, v 0.1 2012-5-5 ÏÂÎç6:41:46 gag Exp $
 */
public class TestDoubleMergeDelayQueue {
    static DoubleMergeDelayQueue<Msg> dmd        = new DoubleMergeDelayQueue<Msg>(1000);
    static DelayQueue<Msg>            pq         = new DelayQueue<Msg>();

    static MergeDelayQueue<Msg>       mq         = new MergeDelayQueue<Msg>();

    private AtomicInteger             pollCount  = new AtomicInteger(0);

    private AtomicInteger             offerCount = new AtomicInteger(0);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        TestDoubleMergeDelayQueue t = new TestDoubleMergeDelayQueue();
        new Thread(t.new C()).start();
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            // logger.error("", e);
        }
        for (int i = 0; i < 10; ++i) {
            new Thread(t.new P()).start();
        }

        while (true) {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                // logger.error("", e);
            }
            if (mq.size() == 0) {
                System.err.println((System.currentTimeMillis() - start) / 1000);
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    // logger.error("", e);
                }
                if (mq.size() == 0) {
                    System.err.println((System.currentTimeMillis() - start) / 1000);
                    // System.exit(0);
                }

            }
        }
    }

    class P implements Runnable {
        int i = 0;

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {

            while (i < 5000) {
                Msg m = new Msg(offerCount.getAndIncrement(), offerCount.get(), true,
                    System.currentTimeMillis() + 5 * 1000);
                boolean f = mq.offer(m);
                if (f) {
                    if (m.getMsgId() > 4950)
                        System.out.println("offer:" + m);
                } else {
                    System.err.println("offer error");
                }
                ++i;

            }

        }
    }

    class C implements Runnable {

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            while (true) {
                Msg m = null;
                // try {
                m = mq.poll();
                //  } catch (InterruptedException e) {
                // logger.error("", e);
                //  }
                if (m == null) {
                    // System.out.println("poll is null:" + pollCount.get());
                } else {
                    pollCount.getAndIncrement();
                    //if (m.getUserId() > 499900)
                    System.err.println("poll:" + pollCount.get() + "-msg:" + m);
                }

            }

        }
    }

}
