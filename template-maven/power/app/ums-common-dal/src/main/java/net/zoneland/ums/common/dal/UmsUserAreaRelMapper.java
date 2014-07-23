package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsUserAreaRel;

public interface UmsUserAreaRelMapper {
    int deleteByPrimaryKey(String id);

    int insert(UmsUserAreaRel record);

    int insertSelective(UmsUserAreaRel record);

    UmsUserAreaRel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsUserAreaRel record);

    int updateByPrimaryKey(UmsUserAreaRel record);

    List<String> getAreaIdByUserId(String userId);

    /**
     * 删除当前用户的用户单位关联表信息
     * 
     * @param userId
     */
    void deleteByUserId(String userId);

    int deleteByAreaId(String areaCode);

}