/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foodoon.monitor.agent.config.DbInfo;

/**
 * 
 * @author foodoon
 * @version $Id: PoolFactory.java, v 0.1 2013年9月14日 下午3:02:31 foodoon Exp $
 */
public class PoolFactory {

    private final static Logger                           logger             = LoggerFactory
                                                                                 .getLogger(PoolFactory.class);

    private static int                                    defaultConnections = 5;

    private static Lock                                   lock               = new ReentrantLock();                             // 锁对象  

    private static Map<DbInfo, MiniConnectionPoolManager> map                = new HashMap<DbInfo, MiniConnectionPoolManager>();

    public static MiniConnectionPoolManager init(DbInfo dbInfo) {
        if (dbInfo == null) {
            return null;
        }
        lock.lock();
        try {
            if (map.get(dbInfo) == null) {
                logger.info("connect to db:driverClass:[" + dbInfo.getDriverClass() + "],url:["
                            + dbInfo.getUrl() + "],userName:[" + dbInfo.getUserName()
                            + "],password:[" + dbInfo.getPassword() + "]");
                ;
                Class.forName(dbInfo.getDriverClass()).newInstance();
                if (dbInfo.getDriverClass().indexOf("oracle") > -1) {
                    oracle.jdbc.pool.OracleConnectionPoolDataSource ds = new oracle.jdbc.pool.OracleConnectionPoolDataSource();
                    ds.setURL(dbInfo.getUrl());
                    ds.setUser(dbInfo.getUserName());
                    ds.setPassword(dbInfo.getPassword());
                    MiniConnectionPoolManager poolMgr = new MiniConnectionPoolManager(ds,
                        defaultConnections);
                    map.put(dbInfo, poolMgr);
                    return poolMgr;
                } else if (dbInfo.getDriverClass().indexOf("SQLServerDriver") > -1) {
                    com.microsoft.sqlserver.jdbc.SQLServerXADataSource ds = new com.microsoft.sqlserver.jdbc.SQLServerXADataSource();
                    ds.setURL(dbInfo.getUrl());
                    ds.setUser(dbInfo.getUserName());
                    ds.setPassword(dbInfo.getPassword());
                    MiniConnectionPoolManager poolMgr = new MiniConnectionPoolManager(ds,
                        defaultConnections);
                    map.put(dbInfo, poolMgr);
                    return poolMgr;
                } else if (dbInfo.getDriverClass().indexOf("mysql") > -1) {

                } else if (dbInfo.getDriverClass().indexOf("postgresql") > -1) {

                }

            }
            return map.get(dbInfo);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static MiniConnectionPoolManager getPoolManager(DbInfo dbInfo) {
        return map.get(dbInfo);
    }

    public static Connection getConnection(DbInfo dbInfo) {
        MiniConnectionPoolManager pm = map.get(dbInfo);
        if (pm == null) {
            pm = init(dbInfo);
        }
        if (pm != null) {
            try {
                return pm.getConnection();
            } catch (SQLException e) {
                logger.error("", e);
            }
        }
        return null;
    }

}
