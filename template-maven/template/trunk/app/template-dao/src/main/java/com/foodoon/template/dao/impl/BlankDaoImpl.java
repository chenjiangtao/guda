/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.template.dao.impl;

import com.foodoon.template.dao.BlankDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author gag
 * @version $Id: BlankDaoImpl.java, v 0.1 2012-5-3 ����2:10:04 gag Exp $
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
