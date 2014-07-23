/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gag
 * @version $Id: UmsFilingMapperImpl.java, v 0.1 2012-12-5 下午12:16:46 gag Exp $
 */
public class UmsFilingMapperImpl extends SqlSessionDaoSupport implements UmsFilingMapper {

    private static final Logger logger = LoggerFactory.getLogger("UMS-FILING");

    /** 
     * @see net.zoneland.ums.common.dal.UmsFilingMapper#filingUmsMsgOut()
     */
    public boolean filingUmsMsgOut(String tableName, Date date) {

        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("tableName", tableName);
        params.put("gmtCreated", date);

        SqlSession sql = super.getSqlSession();
        //        Integer c = (Integer) sql.selectOne(
        //            "net.zoneland.ums.common.dal.UmsMsgOutMapper.fileSelectCountUmsMsgOut", date);
        //        if (c == 0) {
        //            logger.warn("表:UMS_MSG_OUT_PREFIX" + tableName + "不需要归档");
        //            return true;
        //        }
        logger.info("需要归档" + tableName);
        int tab = (Integer) sql.selectOne(
            "net.zoneland.ums.common.dal.UmsMsgOutMapper.fileSelectUmsMsgOut", tableName);
        if (tab == 0) {
            sql.selectOne("net.zoneland.ums.common.dal.UmsMsgOutMapper.fileCreateUmsMsgOut", params);
        }
        int res = sql.insert("net.zoneland.ums.common.dal.UmsMsgOutMapper.fileInsertUmsMsgOut",
            params);
        logger.info("insert归档" + tableName + "记录数量:" + res);
        if (res > 0) {
            sql.delete("net.zoneland.ums.common.dal.UmsMsgOutMapper.fileDeleteUmsMsgOut", params);

        }
        logger.info("del归档" + tableName + "记录数量:" + res);
        return true;
    }

    /** 
     * @see net.zoneland.ums.common.dal.UmsFilingMapper#filingUmsMsgOutUcs()
     */
    public boolean filingUmsMsgOutUcs(String tableName, Date date) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("tableName", tableName);
        params.put("gmtCreated", date);

        SqlSession sql = super.getSqlSession();

        Integer c = (Integer) sql.selectOne(
            "net.zoneland.ums.common.dal.UmsMsgOutUcsMapper.fileSelectCountUmsMsgOutUcs", date);
        if (c == 0) {
            logger.warn("表:UMS_MSG_OUT_Ucs_PREFIX" + tableName + "不需要归档");
            return true;
        }
        int tab = (Integer) sql.selectOne(
            "net.zoneland.ums.common.dal.UmsMsgOutUcsMapper.fileSelectUmsMsgOutUcs", tableName);
        logger.info("需要归档" + tableName + "记录数量:" + c);
        if (tab == 0) {
            sql.selectOne("net.zoneland.ums.common.dal.UmsMsgOutUcsMapper.fileCreateUmsMsgOutUcs",
                params);
        }
        int res = sql.insert(
            "net.zoneland.ums.common.dal.UmsMsgOutUcsMapper.fileInsertUmsMsgOutUcs", params);
        logger.info("insert归档" + tableName + "记录数量:" + res);
        if (res > 0) {
            sql.delete("net.zoneland.ums.common.dal.UmsMsgOutUcsMapper.fileDeleteUmsMsgOutUcs",
                params);

        }
        logger.info("del归档" + tableName + "记录数量:" + res);
        return true;
    }

    /** 
     * @see net.zoneland.ums.common.dal.UmsFilingMapper#filingUmsMsgIn()
     */
    public boolean filingUmsMsgIn(String tableName, Date date) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("tableName", tableName);
        params.put("gmtCreated", date);

        SqlSession sql = super.getSqlSession();

        Integer c = (Integer) sql.selectOne(
            "net.zoneland.ums.common.dal.UmsMsgInMapper.fileSelectCountUmsMsgIn", date);
        if (c == 0) {
            logger.warn("表:UMS_MSG_IN_PREFIX" + tableName + "不需要归档");
            return true;
        }
        int tab = (Integer) sql.selectOne(
            "net.zoneland.ums.common.dal.UmsMsgInMapper.fileSelectUmsMsgIn", tableName);
        logger.info("需要归档" + tableName + "记录数量:" + c);
        if (tab == 0) {
            sql.selectOne("net.zoneland.ums.common.dal.UmsMsgInMapper.fileCreateUmsMsgIn", params);
        }
        int res = sql.insert("net.zoneland.ums.common.dal.UmsMsgInMapper.fileInsertUmsMsgIn",
            params);
        logger.info("insert归档" + tableName + "记录数量:" + res);
        if (res > 0) {
            sql.delete("net.zoneland.ums.common.dal.UmsMsgInMapper.fileDeleteUmsMsgIn", params);

        }
        logger.info("del归档" + tableName + "记录数量:" + res);
        return true;
    }

}
