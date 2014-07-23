/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import com.foodoon.monitor.agent.config.DbInfo;
import com.foodoon.monitor.agent.config.SqlNode;
import com.foodoon.monitor.agent.enums.ResultTypeEnum;
import com.foodoon.monitor.agent.sql.PoolFactory;
import com.foodoon.monitor.agent.util.CronUtils;
import com.foodoon.monitor.agent.util.ValueTypeEnums;

/**
 * 
 * @author foodoon
 * @version $Id: SqlTask.java, v 0.1 2013-5-24 下午8:52:25 foodoon Exp $
 */
public class SqlTask {

    private final static Logger logger                 = LoggerFactory.getLogger(SqlTask.class);

    public static final String  ALERT_LOG              = "警告日志";
    public static final String  ALERT_LOG_NAME         = "alert_";
    public static final String  ALERT_LOG_NAME_SUFFIX  = ".log";

    public static final String  ALERT_LOG_DIR_SQL      = "select value from sys.v_$parameter where name = 'background_dump_dest'";
    public static final String  ALERT_LOG_INSTANCE_SQL = "select instance_name from v$instance";

    @Autowired
    private Task                task;

    public void runTask(List<DbInfo> dbinfos) {
        if (dbinfos == null) {
            return;
        }
        Iterator<DbInfo> it = dbinfos.iterator();
        while (it.hasNext()) {
            DbInfo dbinfo = it.next();
            List<SqlNode> sqlNodes = dbinfo.getSqlNode();
            if (sqlNodes == null) {
                return;
            }
            Iterator<SqlNode> sqlIt = sqlNodes.iterator();
            while (sqlIt.hasNext()) {
                SqlNode node = sqlIt.next();
                String inter = node.getInterval();
                SqlExeTask exe = new SqlExeTask(dbinfo, node);
                if (CronUtils.getCron(inter) > 0) {

                    SchedulerPool.get().schedule(exe,
                        new PeriodicTrigger(new Long(inter), TimeUnit.SECONDS));
                } else {
                    SchedulerPool.get().schedule(exe, new CronTrigger(node.getInterval()));
                }
            }
        }

    }

    class SqlExeTask implements Runnable {

        private DbInfo  dbInfo;

        private SqlNode sqlNode;

        public SqlExeTask(DbInfo dbInfo, SqlNode node) {
            this.dbInfo = dbInfo;
            this.sqlNode = node;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                //                Class.forName(dbInfo.getDriverClass()).newInstance();
                //                logger.info("connect to db:driverClass:[" + dbInfo.getDriverClass() + "],url:["
                //                            + dbInfo.getUrl() + "],userName:[" + dbInfo.getUserName()
                //                            + "],password:[" + dbInfo.getPassword() + "],sql:[" + sqlNode + "]");
                //                ;
                //                Connection conn = DriverManager.getConnection(dbInfo.getUrl(),
                //                    dbInfo.getUserName(), dbInfo.getPassword());
                Connection conn = PoolFactory.getConnection(dbInfo);
                if (conn == null) {
                    logger.error("无法创建sql连接.:dbinfo:" + dbInfo);
                    return;
                }
                try {
                    if (ALERT_LOG.equalsIgnoreCase(sqlNode.getKey())
                        || "alertLog".equalsIgnoreCase(sqlNode.getKey())) {
                        QueryRunner runner = new QueryRunner();
                        List<Map<String, Object>> result = runner.query(conn, ALERT_LOG_DIR_SQL,
                            new MapListHandler());
                        Map<String, Object> objs = result.get(0);
                        if (objs.size() == 1) {
                            String dir = String.valueOf(objs.values().iterator().next());
                            logger.info("日志文件路径:" + dir);
                            result = runner.query(conn, ALERT_LOG_INSTANCE_SQL,
                                new MapListHandler());
                            Map<String, String> info = parseLog(dir, result);
                            Iterator<Entry<String, String>> itt = info.entrySet().iterator();
                            while (itt.hasNext()) {
                                Entry<String, String> entry = itt.next();
                                task.send(sqlNode.getKey() + "实例名:" + entry.getKey(), null,
                                    Float.MIN_VALUE, entry.getValue(),
                                    ValueTypeEnums.db.getValue(), sqlNode.getOrder());
                            }

                        }

                    } else {
                        QueryRunner runner = new QueryRunner();
                        List<Map<String, Object>> result = runner.query(conn, sqlNode.getSql(),
                            new MapListHandler());
                        if (sqlNode.getResultType() == ResultTypeEnum.intVal
                            || sqlNode.getResultType() == ResultTypeEnum.floatVal) {
                            Map<String, Object> objs = result.get(0);
                            if (objs.size() == 1) {
                                String showName = sqlNode.getKey();
                                task.send(showName, null,
                                    getFloat(objs.values().iterator().next()), "",
                                    ValueTypeEnums.dbFloat.getValue(), sqlNode.getOrder());
                            }
                        } else if (sqlNode.getResultType() == ResultTypeEnum.stringVal) {

                            task.send(sqlNode.getKey(), null, Float.MIN_VALUE,
                                getStr(result, sqlNode.getMap()), ValueTypeEnums.db.getValue(),
                                sqlNode.getOrder());
                        } else {
                            //multi-value
                            String suffix = sqlNode.getSuffixColumn();
                            String[] suffixs = null;
                            if (suffix != null) {
                                suffixs = suffix.split(",");
                            }
                            if (suffixs == null) {
                                throw new RuntimeException("无法找到:" + sqlNode + "对应的suffix:"
                                                           + suffix);
                            }
                            Iterator<Map<String, Object>> it = result.iterator();
                            while (it.hasNext()) {
                                Map<String, Object> maps = it.next();
                                StringBuilder suf = new StringBuilder();
                                for (int i = 0, len = suffixs.length; i < len; ++i) {
                                    Object suff = maps.get(suffixs[i]);
                                    if (suff == null) {
                                        throw new RuntimeException("无法找到:" + sqlNode
                                                                   + "对应的suffixColumn:"
                                                                   + sqlNode.getSuffixColumn());
                                    }
                                    suf.append("-").append(String.valueOf(suff));
                                }

                                Object val = maps.get(sqlNode.getValueColumn());
                                if (val == null) {
                                    throw new RuntimeException("无法找到:" + sqlNode
                                                               + "对应的ValueColumn:"
                                                               + sqlNode.getValueColumn());
                                }
                                String key = sqlNode.getKey() + suf.toString();
                                task.send(key, null, getFloat(val), "",
                                    ValueTypeEnums.dbFloat.getValue(), sqlNode.getOrder());
                            }

                        }
                    }
                } finally {
                    conn.close();
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public Map<String, String> parseLog(String dir, List<Map<String, Object>> result) {
        Map<String, String> info = new HashMap<String, String>();
        if (dir == null || result == null) {
            return info;
        }
        Iterator<Map<String, Object>> it = result.iterator();

        while (it.hasNext()) {
            Map<String, Object> map = it.next();
            String instance = String.valueOf(map.values().iterator().next());
            String fileName = dir + File.separator + ALERT_LOG_NAME + instance
                              + ALERT_LOG_NAME_SUFFIX;
            File f = new File(fileName);
            Map<String, Integer> error = new HashMap<String, Integer>();
            if (f.exists()) {
                BufferedReader in = null;
                FileReader fr = null;
                try {
                    fr = new FileReader(f);
                    in = new BufferedReader(fr, 1024);
                    String line = null;
                    while ((line = in.readLine()) != null) {

                        if (line.contains("ORA-")) {
                            Integer c = error.get(line);
                            if (c == null) {
                                error.put(line, 1);
                            } else {
                                error.put(line, ++c);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("", e);
                } finally {

                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            logger.error("", e);
                        }
                    }
                    if (fr != null) {
                        try {
                            fr.close();
                        } catch (IOException e) {
                            logger.error("", e);
                        }
                    }
                }
                info.put(instance, getStr(error));
            } else {
                logger.warn("日志文件:" + fileName + "不存在，无法解析警告信息");
                info.put(instance, "日志文件:" + fileName + "不存在，无法解析警告信息");
            }

        }
        return info;
    }

    private String getStr(Map<String, Integer> error) {
        if (error == null) {
            return "";
        }
        if (error.size() == 0) {
            return "无ORA-类型的错误信息";
        }
        Iterator<Entry<String, Integer>> it = error.entrySet().iterator();
        StringBuilder buf = new StringBuilder();
        while (it.hasNext()) {
            Entry<String, Integer> entry = it.next();
            buf.append("出现次数:").append(entry.getValue()).append(",警告信息:[").append(entry.getKey())
                .append("]");

        }
        return buf.toString();
    }

    public Float getFloat(Object obj) {
        if (obj == null) {
            return Float.MIN_VALUE;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).floatValue();
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).floatValue();
        } else if (obj instanceof Float) {
            return (Float) obj;
        } else if (obj instanceof Long) {
            return ((Long) obj).floatValue();
        } else {
            return Float.MIN_VALUE;
        }
    }

    private String getStr(List<Map<String, Object>> result) {

        return getStr(result, null);
    }

    private String getStr(List<Map<String, Object>> result, Map<String, String> map) {
        Iterator<Map<String, Object>> it = result.iterator();
        StringBuilder buf = new StringBuilder();
        while (it.hasNext()) {
            Map<String, Object> maps = it.next();
            Iterator<Map.Entry<String, Object>> mapIt = maps.entrySet().iterator();
            while (mapIt.hasNext()) {
                Entry<String, Object> entry = mapIt.next();
                String key = null;
                if (map != null) {
                    key = map.get(entry.getKey());
                    if (key == null) {
                        key = entry.getKey();
                    }
                } else {
                    key = entry.getKey();
                }
                buf.append(key).append("=").append(entry.getValue()).append(";");
            }
            buf.append("<br/>");

        }
        return buf.toString();
    }

    public static void main(String[] args) {
        SqlTask st = new SqlTask();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("instance", "UUMDB");
        result.add(map);
        Map<String, String> mm = st.parseLog("C:\\Users\\foodoon\\Downloads", result);
        Iterator<Entry<String, String>> itt = mm.entrySet().iterator();
        while (itt.hasNext()) {
            Entry<String, String> entry = itt.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

}
