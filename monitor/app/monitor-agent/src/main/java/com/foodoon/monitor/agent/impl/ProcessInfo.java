/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author foodoon
 * @version $Id: ProcessInfo.java, v 0.1 2013-6-12 上午9:26:35 foodoon Exp $
 */
public class ProcessInfo {

    private long   pid;

    private String name;

    private String cpu;

    private String cpuTime;

    private String state;

    private String memSize;

    private String memUse;

    private String memhare;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(String cpuTime) {
        this.cpuTime = cpuTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getMemSize() {
        return memSize;
    }

    public void setMemSize(String memSize) {
        this.memSize = memSize;
    }

    public String getMemUse() {
        return memUse;
    }

    public void setMemUse(String memUse) {
        this.memUse = memUse;
    }

    public String getMemhare() {
        return memhare;
    }

    public void setMemhare(String memhare) {
        this.memhare = memhare;
    }

}
