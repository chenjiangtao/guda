/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.common.dal.bo.UmsActionLogBo;
import net.zoneland.ums.common.dal.dataobject.UmsActionLog;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author ypz
 * @version $Id: UmsActionLogBiz.java, v 0.1 2012-9-7 上午10:33:24 ypz Exp $
 */
public interface UmsActionLogBiz {

    PageResult<UmsActionLog> searchByPage(UmsActionLogBo bo, int curPage);

}
