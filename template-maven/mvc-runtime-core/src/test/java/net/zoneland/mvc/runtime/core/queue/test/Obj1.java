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
 * @version $Id: Obj1.java, v 0.1 2012-5-5 ÏÂÎç5:47:11 gag Exp $
 */
public class Obj1 {

    private transient final ReentrantLock lock      = new ReentrantLock();
    private transient final Condition     available = lock.newCondition();

    public String                         name;

    public Obj1(String name) {
        this.name = name;
    }

    public String get() {

        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            return name;
        } finally {
            lock.unlock();
        }
    }

}
