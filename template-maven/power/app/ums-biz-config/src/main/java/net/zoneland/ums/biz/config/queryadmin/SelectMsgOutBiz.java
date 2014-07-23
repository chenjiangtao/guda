/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.queryadmin;

import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author yangjuanying
 * @version $Id: SelectMsgOutBiz.java, v 0.1 2012-12-11 下午8:45:10 yangjuanying Exp $
 */
public interface SelectMsgOutBiz {

    /**
     * 条件分页【短信查询用户短信】
     * 
     * @param appMsgInfoBO
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOut> selectStatMsgOut(AppMsgInfoBO appMsgInfoBO, int curPage);
}
