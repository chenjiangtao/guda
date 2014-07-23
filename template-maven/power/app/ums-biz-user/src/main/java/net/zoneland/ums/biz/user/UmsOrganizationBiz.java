/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.user;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsOrganization;
import net.zoneland.ums.common.util.tree.JSTree;

/**
 *
 * @author wangyong
 * @version $Id: UmsOrganizationBiz.java, v 0.1 2012-8-27 上午10:54:44 Administrator Exp $
 */
public interface UmsOrganizationBiz {
    /**
     * 根据跟组织获取下级组织
     *
     * @param orgRootId
     * @return
     */
    public List<JSTree> queryFirstOrgForSelector(String orgRootId);

    public List<String> getParentIdList(String orgId);

    /**
     *根据类型和组织ID获得下级组织。
     *这个类获得的组织是放在也面上第二个选框里，其条件是该组织下面是人员或者没有下级组织了
     * @param type
     * @param parentId
     * @return
     */
    public List<JSTree> queryChildrenOrgForSelector(String type, String parentId);

    /**
     * 把组织转化成树对象
     *其中传到页面上的ID我们添加组织标识符_org
     * @param umsOrganization
     * @return
     */
    public JSTree getJSTree(UmsOrganization umsOrganization);

    /**
     * 根据ID判断是否存在下级组织
     *
     * @param parentId
     * @return
     */
    public boolean hasChildren(String parentId);

    /**
     *通过上级组织获得其下级组织
     *
     * @param parentId
     * @return
     */
    public List<UmsOrganization> getOrgListByParentId(String parentId);

    /**
     *通过组织ID获得名字
     *
     * @return
     */
    public String getOrgNameById(String id);
}
