/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.mysql.dataobject.InOk;
import net.zoneland.ums.common.mysql.dataobject.InReady;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

/**
 * 
 * @author gag
 * @version $Id: InReadyMapperImpl.java, v 0.1 2012-11-15 下午1:20:34 gag Exp $
 */
public class InReadyMapperImpl extends NamedParameterJdbcDaoSupport implements InReadyMapper {

    private final String insertSql     = "insert into in_ready (BatchNo, SerialNo, SequenceNo, RetCode, Errmsg, StatusFlag, AppId, AppSerialNo, mediaID, SendId, RecvId, SubmitTime,  FinishTime, content, Ack, Reply, doCount, msgType, subApp, ContentMode, cacheTime)"
                                         + "values (:batchno, :serialno, :sequenceno, :retcode, :errmsg, :statusflag, :appid, :appserialno, :mediaid, :sendid, :recvid, :submittime, :finishtime, :content, :ack,  :reply, :docount, :msgtype, :subapp, :contentmode, :cachetime)";

    private final String insertInOkSql = "insert into :in_ok (BatchNo, SerialNo, SequenceNo, RetCode, Errmsg,  AppId, AppSerialNo, mediaID, SendId, RecvId, SubmitTime,  FinishTime, content, Ack, Reply, doCount, msgType, subApp)"
                                         + "values (:batchno, :serialno, :sequenceno, :retcode, :errmsg,:appid, :appserialno, :mediaid, :sendid, :recvid, :submittime, :finishtime, :content, :ack,  :reply, :docount, :msgtype, :subapp)";
    private final String delSql        = "delete from in_ready where BatchNo=:batchno  and SerialNo= :serialno ; ";
    private final String selectSql     = "select BatchNo, SerialNo, SequenceNo, RetCode, Errmsg, StatusFlag, AppId, AppSerialNo, mediaID, SendId, RecvId, SubmitTime,  FinishTime, content, Ack, Reply, doCount, msgType, subApp, ContentMode, cacheTime  from in_ready where batchno=:batchno and serialno=:serialno";

    private final String createInOk    = "CREATE TABLE if not exists :in_ok ("
                                         + "`BatchNo` varchar(14) NOT NULL DEFAULT '',"
                                         + "`SerialNo` int(11) NOT NULL DEFAULT '1',"
                                         + "`sequenceNO` tinyint(4) DEFAULT '0',"
                                         + "`RetCode` varchar(4) ,"
                                         + "`Errmsg` varchar(60) ,"
                                         + "`docount` tinyint(4) DEFAULT '0',"
                                         + "`AppId` varchar(12) ,"
                                         + "`AppSerialNo` varchar(35) ,"
                                         + "`mediaID` varchar(3) DEFAULT NULL,"
                                         + "`SendId` varchar(60),"
                                         + "`RecvId` varchar(60),"
                                         + "`SubmitTime` datetime DEFAULT NULL,"
                                         + "`FinishTime` datetime DEFAULT NULL,"
                                         + "`content` varchar(2048) DEFAULT NULL,"
                                         + "`Ack` tinyint(4) DEFAULT '0',"
                                         + "`Reply` varchar(30) ,"
                                         + "`msgType` tinyint(4) DEFAULT '0',"
                                         + "`subApp` varchar(6) DEFAULT NULL,"
                                         + "PRIMARY KEY (`BatchNo`,`SerialNo`),"
                                         + "KEY `AppId` (`AppId`),"
                                         + "KEY `SendId` (`SendId`),"
                                         + "KEY `SubmitTime` ( `SubmitTime` )"
                                         + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;";

    /** 
     * @see net.zoneland.ums.common.mysql.dal.InReadyMapper#insert(net.zoneland.ums.common.mysql.dataobject.InReady)
     */
    public int insert(InReady inReady) {
        int res = super.getNamedParameterJdbcTemplate().update(insertSql,
            new BeanPropertySqlParameterSource(inReady));

        return res;
    }

    /** 
     * @see net.zoneland.ums.common.mysql.dal.InReadyMapper#fillingInReady(java.lang.String, java.lang.Integer, java.lang.String)
     */
    public void fillingInReady(String batchno, Integer serialno, String retCode) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("batchno", batchno);
        parameters.put("serialno", serialno);
        InReady in = super.getNamedParameterJdbcTemplate().queryForObject(selectSql, parameters,
            ParameterizedBeanPropertyRowMapper.newInstance(InReady.class));
        if (in != null) {
            try {
                super.getNamedParameterJdbcTemplate().getJdbcOperations()
                    .execute(createInOk.replace(":in_ok", "in_ok_" + getDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            InOk ok = new InOk(in);
            ok.setRetcode(retCode);
            int res = super.getNamedParameterJdbcTemplate().update(
                insertInOkSql.replace(":in_ok", "in_ok_" + getDate()),
                new BeanPropertySqlParameterSource(ok));
            if (res == 1) {
                res = super.getNamedParameterJdbcTemplate().update(delSql, parameters);

            }
        }
    }

    private String getDate() {
        SimpleDateFormat monFormat = new SimpleDateFormat("yyyyMM");
        return monFormat.format(new Date());
    }
}
