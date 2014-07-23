/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.user;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.common.util.tree.JSTree;

/**
 * 
 * @author yangjuanying
 * @version $Id: UmsContactBiz.java, v 0.1 2013-2-1 下午9:40:13 yangjuanying Exp $
 */
public interface UmsContactBiz {

    /**
     * 按条件分页显示我的联系人信息
     * 
     * @param umsContact
     * @param pageId
     * @return
     */
    PageResult<UmsContact> searchMyContactByPage(UmsContact umsContact, int pageId);

    /**
     * 通过手机号和UserId查找联系人
     * 
     * @param phone
     * @return
     */
    UmsContact searchByPhone(String phone);

    /**
     * 添加新的联系人
     * 
     * @param umsContact
     * @return
     */
    boolean insertContact(UmsContact umsContact);

    /**
     * 修改联系人
     * 
     * @param umsContact
     * @return
     */
    boolean updateContact(UmsContact umsContact);

    /**
     * 根据主键ID删除联系人
     * 
     * @param id
     * @return
     */
    boolean deleteContact(String id);

    /**
     * 获得用户的联系人，生成jstree
     * 
     * @param userId
     * @return
     */
    List<JSTree> getFirstContactForSelector(String userId);

    /**
     * 根据主键ID查出联系人信息
     * 
     * @param id
     * @return
     */
    UmsContact searchByPrimaryKey(String id);
}
