package com.foodoon.monitor.dal;

import com.foodoon.monitor.dal.dataobject.Config;

public interface ConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKeyWithBLOBs(Config record);

    int updateByPrimaryKey(Config record);
}