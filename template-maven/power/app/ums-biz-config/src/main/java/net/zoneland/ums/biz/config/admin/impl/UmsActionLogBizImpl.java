/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.List;

import net.zoneland.ums.biz.config.admin.UmsActionLogBiz;
import net.zoneland.ums.common.dal.UmsActionLogMapper;
import net.zoneland.ums.common.dal.bo.UmsActionLogBo;
import net.zoneland.ums.common.dal.dataobject.UmsActionLog;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class UmsActionLogBizImpl implements UmsActionLogBiz{

    private final static Logger logger = Logger.getLogger(UmsActionLogBizImpl.class);

    @Autowired
    private UmsActionLogMapper  umsActionLogMapper;

    public PageResult<UmsActionLog> searchByPage(UmsActionLogBo pojo, int curPage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询日志");
        }
        if (curPage == 0) {
            curPage = 1;
        }
        int total = umsActionLogMapper.searchAllNum(pojo);//查询总数
        PageResult<UmsActionLog> result = new PageResult<UmsActionLog>(total, curPage);//生成分页对象
        PageSearch ps = new PageSearch(pojo, result.getFirstrecode(), result.getEndrecode());
        List<UmsActionLog> list = umsActionLogMapper.searchSelectiveByPage(ps);//查询结果
        if (list != null && list.size() > 0) {//转化中文
            result.setResults(list);
        }

        return result;
    }
    

}
