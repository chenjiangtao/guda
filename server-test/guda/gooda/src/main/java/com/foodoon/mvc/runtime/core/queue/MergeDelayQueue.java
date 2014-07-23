/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 大部分代码从java.util.concurrent.DelayQueue复制过来。增加了offerAndMerge和takeAndRemove可以对对象进行合并
 * 
 * @author gag
 * @version $Id: MergeQueue.java, v 0.1 2012-5-3 ����9:12:57 gag Exp $
 */
public class MergeDelayQueue<E extends Merge> extends AbstractQueue<E> implements BlockingQueue<E> {

    private transient final ReentrantLock lock      = new ReentrantLock();
    private transient final Condition     available = lock.newCondition();
    private MPriorityQueue<E>             q;

    public MergeDelayQueue() {
        q = new MPriorityQueue<E>();
    }

    public MergeDelayQueue(int initialCapacity) {
        q = new MPriorityQueue<E>(initialCapacity, null);
    }

    public MergeDelayQueue(int initialCapacity, Comparator<? super E> comparator) {
        q = new MPriorityQueue<E>(initialCapacity, comparator);
    }

    /**
     * 从队列中移除元素
     * @param e
     * @return
     */
    public E takeAndRemove(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return q.removeObj(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 往队列放入元素，支持合并元素
     * @param e 
     * @return trur放入成功
     */
    public boolean offerAndMerge(E e) {
        if (e.needMerge()) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                E temp = q.removeObj(e);
                if (temp == null) {
                    E first = q.peek();
                    q.offer(e);
                    if (first == null || e.compareTo(first) < 0)
                        available.signalAll();
                    return true;
                } else {
                    temp.merge(e);
                    E first = q.peek();
                    q.offer(temp);
                    if (first == null || temp.compareTo(first) < 0)
                        available.signalAll();
                    return true;
                }
            } finally {
                lock.unlock();
            }
        } else {
            return offer(e);

        }
    }

    public boolean remove(Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return q.remove(o);
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.Queue#poll()
     */
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E first = q.peek();
            if (first == null || first.getDelay(TimeUnit.NANOSECONDS) > 0)
                return null;
            else {
                E x = q.poll();
                assert x != null;
                if (q.size() != 0)
                    available.signalAll();
                return x;
            }
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.Queue#peek()
     */
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return q.peek();
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#offer(java.lang.Object)
     */
    public boolean offer(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E first = q.peek();
            q.offer(e);
            if (first == null || e.compareTo(first) < 0) {
                available.signalAll();
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#put(java.lang.Object)
     */
    public void put(E e) throws InterruptedException {
        offer(e);
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#offer(java.lang.Object, long, java.util.concurrent.TimeUnit)
     */
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        throw new RuntimeException("unsupport method");

    }

    /** 
     * @see java.util.concurrent.BlockingQueue#take()
     */
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                E first = q.peek();
                if (first == null) {
                    available.await();
                } else {
                    long delay = first.getDelay(TimeUnit.NANOSECONDS);
                    if (delay > 0) {
                        available.awaitNanos(delay);
                    } else {
                        E x = q.poll();
                        assert x != null;
                        if (q.size() != 0)
                            available.signalAll(); // wake up other takers
                        return x;

                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#poll(long, java.util.concurrent.TimeUnit)
     */
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                E first = q.peek();
                if (first == null) {
                    if (nanos <= 0)
                        return null;
                    else
                        nanos = available.awaitNanos(nanos);
                } else {
                    long delay = first.getDelay(TimeUnit.NANOSECONDS);
                    if (delay > 0) {
                        if (nanos <= 0)
                            return null;
                        if (delay > nanos)
                            delay = nanos;
                        long timeLeft = available.awaitNanos(delay);
                        nanos -= delay - timeLeft;
                    } else {
                        E x = q.poll();
                        assert x != null;
                        if (q.size() != 0)
                            available.signalAll();
                        return x;
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#remainingCapacity()
     */
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#drainTo(java.util.Collection)
     */
    public int drainTo(Collection<? super E> c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int n = 0;
            for (;;) {
                E first = q.peek();
                if (first == null || first.getDelay(TimeUnit.NANOSECONDS) > 0)
                    break;
                c.add(q.poll());
                ++n;
            }
            if (n > 0)
                available.signalAll();
            return n;
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.concurrent.BlockingQueue#drainTo(java.util.Collection, int)
     */
    public int drainTo(Collection<? super E> c, int maxElements) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        if (maxElements <= 0)
            return 0;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int n = 0;
            while (n < maxElements) {
                E first = q.peek();
                if (first == null || first.getDelay(TimeUnit.NANOSECONDS) > 0)
                    break;
                c.add(q.poll());
                ++n;
            }
            if (n > 0)
                available.signalAll();
            return n;
        } finally {
            lock.unlock();
        }
    }

    /** 
     * @see java.util.AbstractCollection#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr(toArray());
    }

    /** 
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return q.size();
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    private class Itr implements Iterator<E> {
        final Object[] array;  // Array of all elements
        int            cursor; // index of next element to return;
        int            lastRet; // index of last element, or -1 if no such

        Itr(Object[] array) {
            lastRet = -1;
            this.array = array;
        }

        public boolean hasNext() {
            return cursor < array.length;
        }

        public E next() {
            if (cursor >= array.length)
                throw new NoSuchElementException();
            lastRet = cursor;
            return (E) array[cursor++];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            Object x = array[lastRet];
            lastRet = -1;
            // Traverse underlying queue to find == element,
            // not just a .equals element.
            lock.lock();
            try {
                for (Iterator<E> it = q.iterator(); it.hasNext();) {
                    if (it.next() == x) {
                        it.remove();
                        return;
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

}
