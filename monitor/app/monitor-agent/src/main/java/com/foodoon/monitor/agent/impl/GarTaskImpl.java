/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Uptime;

/**
 * 
 * @author gang
 * @version $Id: GarTaskImpl.java, v 0.1 2013-4-21 下午2:35:43 gang Exp $
 */
public class GarTaskImpl {

    public static String uptime() throws SigarException {
        Sigar sigar = new Sigar();
        Uptime uptime = sigar.getUptime();
        long time = Math.round(uptime.getUptime());
        return (time / 86400 + "天" + (time % 86400) / 3600 + "小时" + (time % 3600) / 60 + "分" + time
                % 60 + "秒");

    }

    public static String getIp() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }

    public static String getHost() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName();
    }

    public static long memoryUsed() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        return mem.getUsed() / 1024L / 1024L;

    }

    public static double load() throws SigarException {
        Sigar sigar = new Sigar();
        double[] mem = sigar.getLoadAverage();
        return mem[0];

    }

    public static long swapUsed() throws SigarException {
        Sigar sigar = new Sigar();
        Swap swap = sigar.getSwap();
        return swap.getUsed() / 1024L / 1024L;

    }

    public static Map<String, Double> cpuUsed() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        for (int i = 0; i < infos.length; i++) {
            map.put("CPU-" + (i + 1), cpuList[i].getCombined());
        }
        return map;

    }

    public static Double cpuMaxUsed() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        double max = 0;

        for (int i = 0; i < infos.length; i++) {
            if (cpuList[i].getCombined() > max) {
                max = cpuList[i].getCombined();
            }
        }
        return max;

    }

    public static long[] file() throws Exception {
        Sigar sigar = new Sigar();
        long[] io = new long[2];
        FileSystem fslist[] = sigar.getFileSystemList();
        long read = 0;
        long write = 0;
        for (int i = 0; i < fslist.length; i++) {
            FileSystem fs = fslist[i];
            FileSystemUsage usage = null;
            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
                read += usage.getDiskReads() / 1024L;
                write += usage.getDiskWrites() / 1024L;
            } catch (Exception e) {

            }

        }
        io[0] = read;
        io[1] = write;
        return io;
    }

    public static Map<String, Double> devUsed() throws Exception {
        Sigar sigar = new Sigar();
        Map<String, Double> map = new HashMap<String, Double>();
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++) {
            FileSystem fs = fslist[i];

            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知  
                    break;
                case 1: // TYPE_NONE  
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘  
                    // 文件系统总大小  
                    map.put(fs.getDirName(), sigar.getFileSystemUsage(fs.getDirName())
                        .getUsePercent());

                    break;
                case 3:// TYPE_NETWORK ：网络  
                    break;
                case 4:// TYPE_RAM_DISK ：闪存  
                    break;
                case 5:// TYPE_CDROM ：光驱  
                    break;
                case 6:// TYPE_SWAP ：页面交换  
                    break;
            }

        }
        return map;

    }

    public static long[] net() throws SigarException {
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        long in = 0;
        long out = 0;
        long[] net = new long[2];
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);

            long start = System.currentTimeMillis();

            long rxBytesStart = ifstat.getRxBytes();
            long txBytesStart = ifstat.getTxBytes();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            long end = System.currentTimeMillis();
            ifstat = sigar.getNetInterfaceStat(name);
            long rxBytesEnd = ifstat.getRxBytes();
            long txBytesEnd = ifstat.getTxBytes();

            long rxbps = (rxBytesEnd - rxBytesStart) / (end - start) * 1000;
            long txbps = (txBytesEnd - txBytesStart) / (end - start) * 1000;

            in += rxbps / 1024;
            out += txbps / 1024;
        }
        net[0] = in;
        net[1] = out;
        return net;
    }

}
