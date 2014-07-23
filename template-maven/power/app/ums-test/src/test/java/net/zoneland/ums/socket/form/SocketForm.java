/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.socket.form;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author wangyong
 * @version $Id: SocketForm.java, v 0.1 2012-10-9 上午9:12:15 wangyong Exp $
 */
public class SocketForm {

    private String socket;

    private String threadName;

    private String time;

    private String statue;

    private Date   sendTime;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName == null ? null : threadName.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue == null ? null : statue.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket == null ? null : socket.trim();
    }

    /**
     * Getter method for property <tt>sendTime</tt>.
     * 
     * @return property value of sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * Setter method for property <tt>sendTime</tt>.
     * 
     * @param sendTime value to be assigned to property sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

}
