/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.queryadmin;

import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author yangjuanying
 * @version $Id: SelectMsgInBiz.java, v 0.1 2012-12-12 上午10:40:16 yangjuanying Exp $
 */
public interface SelectMsgInBiz {

    /**
     * 条件分页【短信查询上行短信】
     * 
     * @param bo
     * @param pageId
     * @return
     */
    public PageResult<UmsMsgIn> selectMsgIn(AppMsgInfoBO bo, int pageId);
}
