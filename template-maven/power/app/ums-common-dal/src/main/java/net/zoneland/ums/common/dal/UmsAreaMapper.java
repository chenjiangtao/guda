/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.common.util.annotation.Log;

/**
 * 
 * @author yangjuanying
 * @version $Id: UmsAreaMapper.java, v 0.1 2012-10-18 上午9:27:01 yangjuanying Exp
 *          $
 */
public interface UmsAreaMapper {

    @Log(name = "单位维护", comments = "删除单位")
    int deleteByPrimaryKey(String id);

    @Log(name = "单位维护", comments = "添加单位")
    int insert(UmsArea record);

    int insertSelective(UmsArea record);

    UmsArea selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsArea record);

    int updateByPrimaryKey(UmsArea record);

    /**
     * 通过单位号查询出单位信息
     * 
     * @param orgNo
     * @return
     */
    UmsArea selectByAreaCode(String orgNo);

    /**
     * 根据父单位查询子单位
     * 
     * @param pid
     * @return
     */
    List<UmsArea> selectByParentId(String pid);

    @Log(name = "单位维护", comments = "删除单位")
    int deleteByAreaCode(String areaCdoe);

    @Log(name = "单位维护", comments = "更新单位")
    int updateAreaNameByAreaCode(Map<String, String> map);

    /**
     * 查询出所有单位信息
     * 
     * @return
     */
    List<UmsArea> selectAll();

    UmsArea findByAreaCode(String areaCode);

    /**
     * 通过单位名称及父组织查询单位，用于单位名称是否唯一
     * 
     * @param map
     * @return
     */
    UmsArea findByAreaNameAndParentId(Map<String, String> map);

    /**
     * 通过模糊匹配单位名称查找所在单位编码
     * 
     * @param params
     * @return
     */
    List<String> findByAreaName(Map<String, Object> params);

}
