/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import com.foodoon.monitor.agent.config.SysInfo;
import com.foodoon.monitor.agent.impl.GarTaskImpl;
import com.foodoon.monitor.agent.impl.ProcTask;
import com.foodoon.monitor.agent.impl.ProcessInfo;
import com.foodoon.monitor.agent.util.CronUtils;
import com.foodoon.monitor.agent.util.ValueTypeEnums;

/**
 * 
 * @author gang
 * @version $Id: GarTask.java, v 0.1 2013-4-20 上午9:05:53 gang Exp $
 */
public class GarTask {

    private final static Logger logger              = LoggerFactory.getLogger(GarTask.class);

    @Autowired
    private Task                task;

    private String              logReadFileChartset = "GBK";

    private int                 logLine             = 1000;

    private String              logDir              = "/var/log";

    public void runTask(List<SysInfo> sysinfos) {
        if (sysinfos == null) {
            return;
        }
        Iterator<SysInfo> it = sysinfos.iterator();
        while (it.hasNext()) {
            SysInfo sysinfo = it.next();

            if (CronUtils.getCron(sysinfo.getInterval()) > 0) {

                SchedulerPool.get().schedule(new SysExeTask(sysinfo),
                    new PeriodicTrigger(new Long(sysinfo.getInterval()), TimeUnit.SECONDS));
            } else {
                SchedulerPool.get().schedule(new SysExeTask(sysinfo),
                    new CronTrigger(sysinfo.getInterval()));
            }

        }
    }

    public class SysExeTask implements Runnable {

        private SysInfo sysInfo;

        public SysExeTask(SysInfo sysInfo) {
            this.sysInfo = sysInfo;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            // task.check(sysInfo.getKey(), ValueTypeEnums.server.getValue(), sysInfo.getComments());
            try {
                Object v = getValue(sysInfo.getKey());
                if (v != null) {
                    if (v instanceof Float) {
                        task.send(sysInfo.getKey(), sysInfo.getComments(), (Float) v, "",
                            ValueTypeEnums.serverFloat.getValue(), sysInfo.getOrder());
                    } else {
                        task.send(sysInfo.getKey(), sysInfo.getComments(), Float.MIN_VALUE,
                            String.valueOf(v), ValueTypeEnums.server.getValue(), sysInfo.getOrder());
                    }
                    // task.insert(sysInfo.getKey(), v, "");
                } else if ("dev-used".equals(sysInfo.getKey())) {
                    Map<String, Double> map = GarTaskImpl.devUsed();
                    if (map != null) {
                        Iterator<Map.Entry<String, Double>> it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, Double> entry = it.next();
                            task.send(sysInfo.getKey() + "-" + entry.getKey(),
                                sysInfo.getComments(), entry.getValue().floatValue(), "",
                                ValueTypeEnums.serverFloat.getValue(), sysInfo.getOrder());
                        }
                    }
                } else {
                    logger.error("获取" + sysInfo.getKey() + "返回null");
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }

    }

    private Object getValue(String key) {
        if (key == null) {
            return null;
        }
        try {
            if ("mem".equalsIgnoreCase(key)) {
                long u = GarTaskImpl.memoryUsed();
                return new Long(u).floatValue();

            } else if ("net-in".equalsIgnoreCase(key)) {
                long u = GarTaskImpl.net()[0];
                return new Long(u).floatValue();
            } else if ("net-out".equalsIgnoreCase(key)) {
                long u = GarTaskImpl.net()[1];
                return new Long(u).floatValue();
            } else if ("file-read".equalsIgnoreCase(key)) {
                long u = GarTaskImpl.file()[0];
                return new Long(u).floatValue();
            } else if ("file-write".equalsIgnoreCase(key)) {
                long u = GarTaskImpl.file()[1];
                return new Long(u).floatValue();
            } else if ("cpu".equalsIgnoreCase(key)) {
                Double u = GarTaskImpl.cpuMaxUsed();
                return u.floatValue();

            } else if ("load".equalsIgnoreCase(key)) {
                double u = GarTaskImpl.load();
                return new Double(u).floatValue();
            } else if ("processInfo".equalsIgnoreCase(key)) {
                ProcTask pt = new ProcTask();
                Iterator<ProcessInfo> it = pt.procInfo().iterator();
                StringBuilder buf = new StringBuilder();
                while (it.hasNext()) {
                    String info = String.valueOf(it.next());
                    buf.append(info.substring(12, info.length() - 1));
                }
                return buf.toString();
            } else if ("sysLog".equalsIgnoreCase(key)) {
                return readLastLine(logDir, logReadFileChartset, logLine);
            } else if ("runTime".equalsIgnoreCase(key)) {
                return GarTaskImpl.uptime();

            } else {
                logger.warn("无法找到key:" + key);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static String readLastLine(String dir, String charset, int line) throws IOException {
        String fileName = dir + File.separator + "messages";
        File file = new File(fileName);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            long len = raf.length();
            System.out.println(len);
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                int l = 0;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        ++l;
                        if (l == line) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                if (charset == null) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset);
                }
            }
        } catch (FileNotFoundException e) {
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e2) {
                }
            }
        }
        return "在目录" + dir + "无法找到日志文件";
    }

    public static void main(String[] args) throws IOException {
        System.out.println(readLastLine("D:\\logs\\monitor-agent", "gbk", 3));
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setLogReadFileChartset(String logReadFileChartset) {
        this.logReadFileChartset = logReadFileChartset;
    }

    public void setLogLine(int logLine) {
        this.logLine = logLine;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

}
