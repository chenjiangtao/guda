/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.well.biz;

import com.foodoon.well.dao.BlankDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: BlankBiz.java, v 0.1 2012-4-26 ����10:48:09 gag Exp $
 */
public class BlankBiz {

    private final static Logger logger = Logger.getLogger(BlankBiz.class);

    @Autowired
    private BlankDao            blankDao;

    public String getTest() {
        if (logger.isInfoEnabled()) {
            logger.info("testbiz");
        }
        return "test" + blankDao.queryUser();
    }

    /**
     * Setter method for property <tt>blankDao</tt>.
     * 
     * @param blankDao value to be assigned to property blankDao
     */
    public void setBlankDao(BlankDao blankDao) {
        this.blankDao = blankDao;
    }

}
