/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import net.zoneland.ums.common.mysql.dataobject.InReady;

/**
 * 
 * @author gag
 * @version $Id: InReadyMapper.java, v 0.1 2012-11-15 下午12:30:11 gag Exp $
 */
public interface InReadyMapper {

    int insert(InReady inReady);

    void fillingInReady(String batchNo, Integer serialNo, String retCode);

}
