/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.queue;

import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 双缓冲队列
 * @author gag
 * @version $Id: DoubleMergeDelayQueue.java, v 0.1 2012-5-5 ����11:28:35 gag Exp $
 */
public class DoubleMergeDelayQueue<E extends Merge> {

    private transient final ReentrantLock lock                = new ReentrantLock();

    protected final Log                   logger              = LogFactory.getLog(getClass());

    private MergeDelayQueue<E>            q1;
    private MergeDelayQueue<E>            q2;

    private transient int                 capacity            = 0;

    private transient boolean             currentQueue        = true;

    private static final float            DEFAULT_LOAD_FACTOR = 0.75f;

    private float                         factorCapacity;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "queue:q1:" + q1.size() + ";q2:" + q2.size();
    }

    public boolean isBlank() {
        return q1.size() == 0 && q2.size() == 0;
    }

    public DoubleMergeDelayQueue(int initialCapacity) {
        q1 = new MergeDelayQueue<E>(initialCapacity, null);
        q2 = new MergeDelayQueue<E>(initialCapacity, null);
        capacity = initialCapacity;
        factorCapacity = DEFAULT_LOAD_FACTOR * capacity;
    }

    public DoubleMergeDelayQueue(int initialCapacity, Comparator<? super E> comparator) {
        q1 = new MergeDelayQueue<E>(initialCapacity, comparator);
        q2 = new MergeDelayQueue<E>(initialCapacity, null);
        capacity = initialCapacity;
        factorCapacity = DEFAULT_LOAD_FACTOR * capacity;
    }

    public boolean offer(E e) {
        if (currentQueue) {
            if (q1.size() >= factorCapacity) {
                exchangeWrite();
            }
            return q1.offer(e);

        } else {
            if (q2.size() >= factorCapacity) {
                exchangeWrite();
            }
            return q2.offer(e);

        }
    }

    public boolean offerAndMerge(E e) {
        if (currentQueue) {
            if (q1.size() >= factorCapacity) {
                exchangeWrite();
            }
            return q1.offerAndMerge(e);

        } else {
            if (q2.size() >= factorCapacity) {
                exchangeWrite();
            }
            return q2.offerAndMerge(e);

        }
    }

    public E poll() {
        if (currentQueue) {
            E first = q2.poll();
            if (first == null) {
                exchangeRead();
                return q2.poll();
            }
            return first;
        } else {
            E first = q1.poll();
            if (first == null) {
                exchangeRead();
                return q1.poll();
            }
            return first;
        }
    }

    public void exchangeRead() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (currentQueue) {
                if (q1.size() > 0 && q1.peek() != null) {
                    //  System.err.println("read exchange," + currentQueue + ",q1 size:" + q1.size()
                    //         + " q2 size:" + q2.size());
                    exchangeQueue(q1, q2);
                    currentQueue = false;
                }
            } else {
                if (q2.size() > 0 && q2.peek() != null) {
                    // System.err.println("read exchange," + currentQueue + ",q1 size:" + q1.size()
                    //                    + " q2 size:" + q2.size());
                    exchangeQueue(q1, q2);
                    currentQueue = true;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void exchangeWrite() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (currentQueue) {
                if (q1.size() >= factorCapacity && q2.size() < factorCapacity) {
                    //System.err.println("write exchange," + currentQueue + ",q1 size:" + q1.size()
                    //                   + " q2 size:" + q2.size());
                    exchangeQueue(q1, q2);
                    currentQueue = false;
                }
            } else {
                if (q2.size() >= factorCapacity && q1.size() < factorCapacity) {
                    //System.err.println("write exchange," + currentQueue + ",q1 size:" + q1.size()
                    //                   + " q2 size:" + q2.size());
                    exchangeQueue(q2, q1);
                    currentQueue = true;
                }
            }
            if (capacity <= q1.size() && capacity <= q2.size()) {
                // System.err.println("both full," + currentQueue + " q1 size:" + q1.size()
                //                    + " q2 size:" + q2.size());
                logger.warn("write and read queue both full!!!!!!");
            }
        } finally {
            lock.unlock();
        }
    }

    public void exchangeQueue(MergeDelayQueue<E> q1, MergeDelayQueue<E> q2) {
        MergeDelayQueue<E> temp = q2;
        q2 = q1;
        q1 = temp;

    }
}
