/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.queue.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author gag
 * @version $Id: Test.java, v 0.1 2012-5-3 ÏÂÎç9:16:29 gag Exp $
 */
public class Test {

    private transient final ReentrantLock lock      = new ReentrantLock();
    private transient final Condition     available = lock.newCondition();

    public String get() {
        this.lock.lock();
        try {
            try {
                System.out.println("test");
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "test";
        } finally {
            this.lock.unlock();
        }

    }

}
