package com.foodoon.well.dao;

import com.foodoon.well.dao.domain.StaffDO;
import com.foodoon.well.dao.domain.StaffDOCriteria;
import java.util.List;

public interface StaffDOMapper {
    int countByExample(StaffDOCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(StaffDO record);

    int insertSelective(StaffDO record);

    List<StaffDO> selectByExample(StaffDOCriteria example);

    StaffDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaffDO record);

    int updateByPrimaryKey(StaffDO record);
}