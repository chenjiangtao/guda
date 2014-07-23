/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.biz.config.admin.bo.KeywordBO;
import net.zoneland.ums.common.dal.dataobject.UmsKeywordInfo;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 关键字业务接口
 * @author XuFan
 * @version $Id: Keyword.java, v 0.1 Aug 22, 2012 11:30:07 AM XuFan Exp $
 */
public interface KeywordBiz {
    /**
     * 条件查询分页显示关键字
     *
     * @param keyword
     * @param curpage
     * @return
     */
    public PageResult<KeywordBO> searchKeyword(String keyword, String appId, int curpage);

    /**
     * 通过id获取关键字
     *
     * @param id
     * @return
     */
    public UmsKeywordInfo getKeywordById(String id);

    /**
     * 批量保存关键词
     * @param appId
     * @param keywords
     */
    public String saveKeyword(String appId, String[] keywords);

    /**
     * 根据id删除关键字
     *
     * @param id
     */
    public void deleteKeyword(String id);

    /**
     * 查询消息内容中是否包含关键词
     *
     * @param msgContet
     * @param appId
     * @return
     */
    String includeKeyword(String msgContet, String appId);

    /**
     * 根据AppId删除关键字
     *
     * @param appId
     * @return
     */
    public boolean deleteKeywordByAppId(String appId);
}
