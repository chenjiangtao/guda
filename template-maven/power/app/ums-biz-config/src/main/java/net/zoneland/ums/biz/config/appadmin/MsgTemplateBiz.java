/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.appadmin;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author wangyong
 * @version $Id: MsgTemplateBiz.java, v 0.1 2012-10-12 下午4:49:43 wangyong Exp $
 */
public interface MsgTemplateBiz {

    /**
     * 添加模版
     * 
     * @param umsMsgTemplate
     * @return
     */
    boolean saveTemplate(UmsMsgTemplate umsMsgTemplate);

    /**
     * 判断模版Id是否已经存在
     * 
     * @param tempId
     * @return
     */
    boolean isExist(String tempId);

    /**
     * 分页显示短信模版
     * 
     * @return
     */
    PageResult<UmsMsgTemplate> listUmsTemplate(String tempId, int pageId, List<String> appIds);

    /**
     * 根据主键Id删除短信模版
     * 
     * @param id
     * @return
     */
    boolean deleteById(String id);

    UmsMsgTemplate findById(String id);

    boolean updateTemplate(UmsMsgTemplate umsMsgTemplate);

    List<UmsMsgTemplate> fetchTemplate(String appId, String subAppId, String templateId);
}
