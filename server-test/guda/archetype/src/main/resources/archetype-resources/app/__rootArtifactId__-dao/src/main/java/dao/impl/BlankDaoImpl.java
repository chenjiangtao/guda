#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package ${package}.dao.impl;

import ${package}.dao.BlankDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author gag
 * @version ${symbol_dollar}Id: BlankDaoImpl.java, v 0.1 2012-5-3 ����2:10:04 gag Exp ${symbol_dollar}
 */
public class BlankDaoImpl implements BlankDao {

    private final static Logger logger = LoggerFactory.getLogger(BlankDaoImpl.class);

    /** 
     * @see
     */
    public String queryUser() {


        return "test DAO";
    }

}
