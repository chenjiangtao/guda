/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import java.util.HashMap;
import java.util.Map;

import net.zoneland.ums.common.mysql.dataobject.FilterMessage;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

/**
 * 
 * @author gag
 * @version $Id: FilterMessageMapperImpl.java, v 0.1 2012-11-15 下午3:18:20 gag Exp $
 */
public class FilterMessageMapperImpl extends NamedParameterJdbcDaoSupport implements
                                                                         FilterMessageMapper {

    private String sql = "select id, appid, content from filterMessage where content=:content limit 1";

    /** 
     * @see net.zoneland.ums.common.mysql.dal.FilterMessageMapper#selectByContent(java.lang.String)
     */
    public FilterMessage selectByContent(String content) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("content", content);
        return super.getNamedParameterJdbcTemplate().queryForObject(sql, parameters,
            ParameterizedBeanPropertyRowMapper.newInstance(FilterMessage.class));
    }

}
