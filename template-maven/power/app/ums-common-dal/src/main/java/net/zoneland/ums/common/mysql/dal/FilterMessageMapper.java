/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import net.zoneland.ums.common.mysql.dataobject.FilterMessage;

/**
 * 
 * @author gag
 * @version $Id: FilterMessageMapper.java, v 0.1 2012-11-15 下午3:17:12 gag Exp $
 */
public interface FilterMessageMapper {

    FilterMessage selectByContent(String content);

}
