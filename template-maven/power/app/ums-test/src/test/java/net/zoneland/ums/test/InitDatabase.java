package net.zoneland.ums.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class InitDatabase {

    private final static Logger logger = Logger.getLogger(InitDatabase.class);

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        String file = "C:/Users/Administrator.R6XKBB1TMYVW8VJ/Desktop/tel_init";
        BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(
            new FileInputStream(file)), "gbk"));
        String str;
        Connection conn = null;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            conn = DriverManager.getConnection("jdbc:db2://172.16.86.102:50000/UMSDB", "ums",
                "zone2012");
        } catch (ClassNotFoundException e) {
            logger.error("驱动加载有问题！", e);
        } catch (SQLException e) {
            logger.error("连接有问题", e);
        }
        java.sql.Statement stmt = conn.createStatement();
        int i = 0;
        while ((str = in.readLine()) != null) {
            i++;
            try {
                stmt.addBatch(str);
                if (i % 10 == 0) {
                    stmt.executeBatch();
                    stmt.clearBatch();
                    conn.commit();
                }
            } catch (SQLException e) {
                logger.error("sql语句有问题", e);
            }
            System.out.println(str);
        }
        in.close();
        conn.close();
    }
}
