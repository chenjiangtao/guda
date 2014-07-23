package com.tiaotiaogift.ada.common.dal;

import java.util.List;

import com.tiaotiaogift.ada.common.dal.dataobject.Publish;

public interface PublishMapper {
    int deleteByPrimaryKey(String id);

    int insert(Publish record);

    int insertSelective(Publish record);

    Publish selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Publish record);

    int updateByPrimaryKeyWithBLOBs(Publish record);

    int updateByPrimaryKey(Publish record);
    
    List<Publish> selectPublishByType(String type);
}