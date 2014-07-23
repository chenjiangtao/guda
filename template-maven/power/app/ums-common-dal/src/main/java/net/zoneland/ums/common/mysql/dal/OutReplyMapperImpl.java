/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.mysql.dataobject.OutReply;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

/**
 * 
 * @author gag
 * @version $Id: OutReplyMapperImpl.java, v 0.1 2012-11-15 下午3:08:35 gag Exp $
 */
public class OutReplyMapperImpl extends NamedParameterJdbcDaoSupport implements OutReplyMapper {

    private String sql = "select batchno, serialno, sequenceno, retcode, errmsg, statusflag, appid, appserialno, mediaid, sendid, recvid, submittime, finishtime, rep, docount, priority, batchmode, contentmode, content, timesetflag, settime, ack, replydes, reply, fee, feetype, subapp, msgid  from out_reply where replyDes=:replyDes order by batchNO desc limit 1";

    //batchno, serialno, sequenceno, retcode, errmsg, statusflag, appid, appserialno, mediaid, sendid, recvid, submittime, finishtime, rep, docount, priority, batchmode, contentmode, content, timesetflag, settime, ack, replydes, reply, fee, feetype, subapp, msgid 
    /** 
     * @see net.zoneland.ums.common.mysql.dal.OutReplyMapper#selectByReplyDes(java.lang.String)
     */
    public OutReply selectByReplyDes(String replyDes) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("replyDes", replyDes);
        return super.getNamedParameterJdbcTemplate().queryForObject(sql, parameters,
            ParameterizedBeanPropertyRowMapper.newInstance(OutReply.class));
    }
}
