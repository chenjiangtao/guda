/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 * 
 * @author gag
 * @version $Id: RecvidAppMapperImpl.java, v 0.1 2012-11-15 下午3:24:56 gag Exp $
 */
public class RecvidAppMapperImpl extends NamedParameterJdbcDaoSupport implements RecvidAppMapper {

    private String sql = "select appid from recvid_app where recvid=:recvid";

    /** 
     * @see net.zoneland.ums.common.mysql.dal.RecvidAppMapper#selectAppId(java.lang.String)
     */
    public String selectAppId(String recvId) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("recvid", recvId);
        return super.getNamedParameterJdbcTemplate().queryForObject(sql, parameters, String.class);
    }

}
