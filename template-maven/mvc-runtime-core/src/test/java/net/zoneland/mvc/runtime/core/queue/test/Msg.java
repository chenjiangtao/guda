/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.queue.test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import net.zoneland.mvc.runtime.core.queue.Merge;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author gag
 * @version $Id: Msg.java, v 0.1 2012-5-3 ÏÂÎç8:37:21 gag Exp $
 */
public class Msg implements Merge {

    private int msgId;

    /**
     * Getter method for property <tt>msgId</tt>.
     * 
     * @return property value of msgId
     */
    public int getMsgId() {
        return msgId;
    }

    /**
     * Setter method for property <tt>msgId</tt>.
     * 
     * @param msgId value to be assigned to property msgId
     */
    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    private int           userId;

    private AtomicInteger count     = new AtomicInteger(0);

    private List<Merge>   msgs      = new CopyOnWriteArrayList<Merge>();

    private boolean       needMerge = false;

    private long          time      = 0;

    private String        name;

    private int           size;

    public Msg() {

    }

    /**
     * Getter method for property <tt>size</tt>.
     * 
     * @return property value of size
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter method for property <tt>size</tt>.
     * 
     * @param size value to be assigned to property size
     */
    public void setSize(int size) {
        this.size = size;
    }

    public Msg(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Msg(int msgId, int userId, String name) {
        this.msgId = msgId;
        this.userId = userId;
        this.name = name;
    }

    public Msg(int userId, boolean needMerge) {
        this.userId = userId;
        this.needMerge = needMerge;
    }

    public Msg(int userId, boolean needMerge, long time) {
        this.userId = userId;
        this.needMerge = needMerge;
        this.time = time;
        msgs.add(this);
    }

    public Msg(int msgId, int userId, boolean needMerge, long time) {
        this.msgId = msgId;
        this.userId = userId;
        this.needMerge = needMerge;
        this.time = time;
        msgs.add(this);
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Delayed other) {
        if (other == null) {
            return 1;
        }
        if (other == this) {
            return 0;
        }

        if (other instanceof Msg) {
            Msg x = (Msg) other;
            long d = this.time - x.time;
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    /** 
     * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
     */
    public long getDelay(TimeUnit unit) {
        if (time == 0) {
            return 0;
        }
        return unit.convert(time - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>msgs</tt>.
     * 
     * @return property value of msgs
     */
    public List<Merge> getMsgs() {
        return msgs;
    }

    /**
     * Setter method for property <tt>msgs</tt>.
     * 
     * @param msgs value to be assigned to property msgs
     */
    public void setMsgs(List<Merge> msgs) {
        this.msgs = msgs;
    }

    /** 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.userId;
    }

    /** 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Msg) {
            Msg msg1 = (Msg) obj;
            return msg1.userId == this.userId;
        }
        return false;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /** 
     * @see net.zoneland.mvc.runtime.core.queue.Merge#merge(net.zoneland.mvc.runtime.core.queue.Merge)
     */
    public void merge(Merge msg) {
        this.msgs.add(msg);
        ++size;
        count.getAndIncrement();
    }

    /** 
     * @see net.zoneland.mvc.runtime.core.queue.Merge#needMerge()
     */
    public boolean needMerge() {
        return needMerge;

    }

    /**
     * Getter method for property <tt>time</tt>.
     * 
     * @return property value of time
     */
    public long getTime() {
        return time;
    }

    /**
     * Setter method for property <tt>time</tt>.
     * 
     * @param time value to be assigned to property time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Getter method for property <tt>needMerge</tt>.
     * 
     * @return property value of needMerge
     */
    public boolean isNeedMerge() {
        return needMerge;
    }

    /**
     * Setter method for property <tt>needMerge</tt>.
     * 
     * @param needMerge value to be assigned to property needMerge
     */
    public void setNeedMerge(boolean needMerge) {
        this.needMerge = needMerge;
    }

}
