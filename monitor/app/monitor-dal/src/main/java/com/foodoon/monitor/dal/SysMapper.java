package com.foodoon.monitor.dal;

import java.util.List;
import java.util.Map;

import com.foodoon.monitor.dal.dataobject.Sys;

public interface SysMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sys record);

    int insertSelective(Sys record);

    Sys selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sys record);

    int updateByPrimaryKey(Sys record);

    List<Sys> selectByType(Map<String, Object> parmas);

    List<Sys> selectByTypePage(Map<String, Object> parmas);

    int selectCountByType(Map<String, Object> parmas);

    int delByTime(Map<String, Object> parmas);

    int selectRecentCount(Map<String, Object> parmas);

}