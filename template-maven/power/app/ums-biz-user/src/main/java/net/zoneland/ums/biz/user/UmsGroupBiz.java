/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.ZTree;
import net.zoneland.ums.common.util.tree.JSTree;

/**
 *
 * @author wangyong
 * @version $Id: UmsGroupBiz.java, v 0.1 2012-8-27 上午10:50:30 Administrator Exp $
 */
public interface UmsGroupBiz {

    /**
     * 根据用户查询群组并转为树对象
     *
     * @param userId
     * @return
     */
    public List<JSTree> getFirstGroupForSelector(String userId);

    /**
     * 把群组转化成树对象
     *其中传到页面上的ID我们添加组织标识符_group
     * @param umsOrganization
     * @return
     */
    public JSTree getJSTree(UmsGroup umsGroup);

    /**
     * 根据传入的群组的groupId获取其成员集合
     * 
     * @param groupId
     * @return
     */
    public List<ZTree> queryGroupUserRelByGroupId(String groupId);

    /**
     * 查询个人群组表信息，生成zTree树
     * 
     * @return
     */
    public List<ZTree> queryGroupForSelector();
}
