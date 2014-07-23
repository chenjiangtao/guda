/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import net.zoneland.ums.common.mysql.dataobject.InError;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 * 
 * @author gag
 * @version $Id: InErrorMapperImpl.java, v 0.1 2012-11-15 下午1:20:23 gag Exp $
 */
public class InErrorMapperImpl extends NamedParameterJdbcDaoSupport implements InErrorMapper {

    private String insertSql = "insert into in_error (BatchNo, SerialNo, sequenceNO, RetCode, Errmsg, docount, AppId, AppSerialNo, mediaID, SendId, RecvId, SubmitTime, FinishTime, content, Ack, Reply, msgType, subApp)"
                               + "values (:batchno, :serialno, :sequenceno, :retcode, :errmsg, :docount, :appid, :appserialno, :mediaid, :sendid, :recvid, :submittime, :finishtime, :content, :ack,  :reply, :msgtype, :subapp)";

    /** 
     * @see net.zoneland.ums.common.mysql.dal.InErrorMapper#insert(net.zoneland.ums.common.mysql.dataobject.InError)
     */
    public int insert(InError inError) {
        int res = super.getNamedParameterJdbcTemplate().update(insertSql,
            new BeanPropertySqlParameterSource(inError));

        return res;
    }

}
