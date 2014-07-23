/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.queue.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author gag
 * @version $Id: TestObj.java, v 0.1 2012-5-5 ÏÂÎç5:48:48 gag Exp $
 */
public class TestObj {

    private transient final ReentrantLock lock = new ReentrantLock();

    private Obj1                          b1   = new Obj1("b1");

    private Obj1                          b2   = new Obj1("b2");

    public void exchange() {
        System.out.println("begin change");
        // System.out.println(b1.get());
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Obj1 temp = b1;
            b1 = b2;
            b2 = temp;
        } finally {
            lock.unlock();
        }

        System.out.println(" change end");
        System.out.println("exchange result:" + b1.get());
    }

    public String get() {
        return b1.get();
    }
}
