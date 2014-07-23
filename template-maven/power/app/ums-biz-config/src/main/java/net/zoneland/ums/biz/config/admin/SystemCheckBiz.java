/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import java.util.Map;

/**
 *
 * @author XuFan
 * @version $Id: SystemCheck.java, v 0.1 Aug 25, 2012 2:37:57 PM XuFan Exp $
 */
public interface SystemCheckBiz {
    /**
     * 查询数据库中数据，返回系统参数.
     *
     * @return
     */
    public Map<String, Integer> getSystemParams();
}
