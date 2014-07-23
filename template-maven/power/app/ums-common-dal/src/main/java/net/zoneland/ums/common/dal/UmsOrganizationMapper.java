package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsOrganization;

import org.springframework.cache.annotation.Cacheable;

public interface UmsOrganizationMapper {

    @Cacheable(value = "orgCache")
    UmsOrganization selectByPrimaryKey(String id);

    /**
     *通过父组织的ID获取下一级组织
     * @param orgRootId
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsOrganization> selectByParentId(String orgRootId);

    /**
     * 通过父组织的ID获取其子组织的数量
     *
     * @param parentId
     * @return
     */
    @Cacheable(value = "orgCache")
    int getChildrenCount(String parentId);

    /**
     * 获取全部组织的id,name,pId
     *
     * @return
     */
    List<UmsOrganization> selectAll();

    /**
     *  获取同级组织的全部name
     *
     * @param pId
     * @return
     */
    List<String> selectPname(String parentId);

    /**
     *通过组织名称模糊查询组织
     *
     * @param userName
     * @return
     */
    @Cacheable(value = "orgCache")
    List<UmsOrganization> getOrgsByOrgName(String orgName);

    /**
     * 通过组织id查找组织name
     *
     * @param id
     * @return
     */
    String getOrgNameById(String id);
}