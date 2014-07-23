package com.tiaotiaogift.common.dal;

import java.util.List;

import com.tiaotiaogift.common.mysql.dataobject.Notify;

public interface NotifyMapper {
    int deleteByPrimaryKey(String id);

    int insert(Notify record);

    int insertSelective(Notify record);

    Notify selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Notify record);

    int updateByPrimaryKey(Notify record);

    Notify selectByUserId(String userId);

    List<Notify> selectAll();
}