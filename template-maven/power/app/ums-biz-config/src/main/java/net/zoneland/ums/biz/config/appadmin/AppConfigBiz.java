/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.appadmin;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.biz.config.admin.bo.AppInfoBO;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author XuFan
 * @version $Id: AppConfig.java, v 0.1 Aug 22, 2012 11:25:06 AM XuFan Exp $
 */
public interface AppConfigBiz {

    public PageResult<AppInfoBO> searchAppInfo(UmsAppInfo umsAppInfo, List<String> appids,
                                               int curPage);//应用管理员条件查询应用的分页

    public int searchTotal(UmsAppInfo umsAppInfo);//根据查询条件返回查询结果总数

    public int modfiyAppPassword(String id, String newPassword);//修改应用密码

    /**
     * 分页条件查询应用【用户短信】
     * 
     * @param appMsgInfoBO
     * @param appIdList
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOut> searchAppMsgInfo(AppMsgInfoBO appMsgInfoBO,
                                                  List<String> appIdList, int curPage);

    public List<UmsAppSub> getAppSub(String appId);//根据appId获取子应用

    public List<UmsAppInfo> getApp(List<String> appIdList);//根据应用管理员角色 查找对应的应用

    public List<UmsAppInfo> getAppByname(Map<String, Object> map, String appname);

    /**
     * 分页条件查询应用【数据短信】
     * 
     * @param appMsgInfoBO
     * @param appIdList
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOut> searchAppDataMsgInfo(AppMsgInfoBO appMsgInfoBO,
                                                      List<String> appIdList, int curPage);

    /**
     * 分页条件查询应用上行短信
     * 
     * @param appMsgInfoBO
     * @param appIdList
     * @param pageId
     * @return
     */
    public PageResult<UmsMsgIn> searchAppMsgIn(AppMsgInfoBO appMsgInfoBO, List<String> appIdList,
                                               int pageId);
}
