/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;

import com.foodoon.monitor.agent.start.App;

/**
 * 
 * @author foodoon
 * @version $Id: AllInfo.java, v 0.1 2013-6-12 上午9:36:58 foodoon Exp $
 */
public class AllInfo {

    private Sigar         sigar = new Sigar();

    private StringBuilder builder;

    //获取进程的相关信息
    private List<ProcInfo> getProcessInfo() {
        Ps ps = new Ps();
        List<ProcInfo> processInfos = new ArrayList<ProcInfo>();
        try {
            long[] pids = sigar.getProcList();
            for (long pid : pids) {
                List<String> list = ps.getInfo(sigar, pid);
                ProcInfo info = new ProcInfo();
                for (int i = 0; i <= list.size(); i++) {
                    switch (i) {
                        case 0:
                            info.setPid(list.get(0));
                            break;
                        case 1:
                            info.setUser(list.get(1));
                            break;
                        case 2:
                            info.setStartTime(list.get(2));
                            break;
                        case 3:
                            info.setMemSize(list.get(3));
                            break;
                        case 4:
                            info.setMemUse(list.get(4));
                            break;
                        case 5:
                            info.setMemhare(list.get(5));
                            break;
                        case 6:
                            info.setState(list.get(6));
                            break;
                        case 7:
                            info.setCpuTime(list.get(7));
                            break;
                        case 8:
                            info.setName(list.get(8));
                            break;
                    }
                }
                processInfos.add(info);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return processInfos;
    }

    public static void main(String[] args) {
        App.setTest();
        AllInfo info = new AllInfo();
        List<ProcInfo> temp = info.getProcessInfo();
        Iterator<ProcInfo> it = temp.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}

//对进程信息进行包装
class ProcInfo {

    private String pid;

    private String user;

    private String startTime;

    private String memSize;

    private String memUse;

    private String memhare;

    private String state;

    private String cpuTime;

    private String name;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(String cpuTime) {
        this.cpuTime = cpuTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
