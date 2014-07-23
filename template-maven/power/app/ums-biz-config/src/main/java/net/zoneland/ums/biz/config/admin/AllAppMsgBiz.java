/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import java.util.List;

import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 管理员查询统计全部消息接口
 * @author XuFan
 * @version $Id: AllAppMsgBiz.java, v 0.1 Aug 23, 2012 6:55:08 PM XuFan Exp $
 */
public interface AllAppMsgBiz {

    /**
     * 条件分页查询全部应用消息。
     * 
     * 
     * @param bo
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOut> searchAllAppMsg(AppMsgInfoBO bo, int curPage);

    /**
     * 下拉框获取全部应用
     * 
     * @return
     */
    public List<UmsAppInfo> getAllApp();

    /**
     * 查询统计全部应用【用户短信】导出excel表
     * 
     * @param bo
     * @param path
     */
    public void exportExcel(AppMsgInfoBO bo, String path);

    /**
     * 条件分页查询全部应用数据短信消息
     * 
     * @param appMsgInfoBO
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOut> searchAllAppMsgUcs(AppMsgInfoBO appMsgInfoBO, int curPage);

    /**
     * 查询统计全部应用【数据短信】导出excel表
     * 
     * @param appMsgInfoBO
     * @param path
     */
    public void exportDataMsgExcel(AppMsgInfoBO appMsgInfoBO, String path);

}
