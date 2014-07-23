package com.foodoon.monitor.dal;

import java.util.List;
import java.util.Map;

import com.foodoon.monitor.dal.dataobject.Warn;

public interface WarnMapper {
    int deleteByPrimaryKey(String id);

    int insert(Warn record);

    int insertSelective(Warn record);

    Warn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Warn record);

    int updateByPrimaryKey(Warn record);

    List<Warn> selectByPage(Map<String, Object> params);

    int selectByCount(Map<String, Object> params);

    Warn selectByIpAndKey(Map<String, Object> params);
}