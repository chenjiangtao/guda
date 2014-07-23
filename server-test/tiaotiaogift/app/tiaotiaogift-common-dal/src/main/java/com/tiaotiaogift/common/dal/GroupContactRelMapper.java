package com.tiaotiaogift.common.dal;

import com.tiaotiaogift.common.mysql.dataobject.GroupContactRel;

public interface GroupContactRelMapper {
    int deleteByPrimaryKey(String id);

    int insert(GroupContactRel record);

    int insertSelective(GroupContactRel record);

    GroupContactRel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GroupContactRel record);

    int updateByPrimaryKey(GroupContactRel record);
}