/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.impl;

import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;

/**
 * 
 * @author foodoon
 * @version $Id: ProcTask.java, v 0.1 2013-6-12 上午7:20:45 foodoon Exp $
 */
public class ProcTask {

    public List<ProcessInfo> procInfo() throws SigarException {
        // long[] pids = this.shell.findPids(args);
        Sigar sigar = new Sigar();
        List<ProcessInfo> list = new ArrayList<ProcessInfo>();
        long[] pids = sigar.getProcList();
        Ps ps = new Ps();
        for (long pid : pids) {
            try {
                ProcState prs = sigar.getProcState(pid);
                ProcCpu pCpu = sigar.getProcCpu(pid);
                ProcMem pm = sigar.getProcMem(pid);
                // ProcCpu pCpu = new ProcCpu();
                //ProcMem pm = new ProcMem();

                // pCpu.gather(sigar, pid);
                // pm.gather(sigar, pid);
                ProcessInfo pi = new ProcessInfo();
                pi.setPid(pid);
                pi.setCpu(String.valueOf(pCpu.getPercent()));

                pi.setName(prs.getName());
                List<String> info = ps.getInfo(sigar, pid);
                pi.setCpuTime(info.get(7));
                pi.setState(info.get(6));
                pi.setMemSize(info.get(3));
                pi.setMemUse(info.get(4));
                pi.setMemhare(info.get(5));
                list.add(pi);
            } catch (Exception e) {

            }

        }
        return list;
    }
}
