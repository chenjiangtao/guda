package com.tiaotiaogift.common.dal;

import com.tiaotiaogift.common.mysql.dataobject.Group;

public interface GroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}