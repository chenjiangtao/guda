/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal;

import java.util.Date;

/**
 * 
 * @author gag
 * @version $Id: UmsFilingMapper.java, v 0.1 2012-12-5 下午12:16:02 gag Exp $
 */
public interface UmsFilingMapper {

    boolean filingUmsMsgOut(String tableName, Date date);

    boolean filingUmsMsgOutUcs(String tableName, Date date);

    boolean filingUmsMsgIn(String tableName, Date date);

}
