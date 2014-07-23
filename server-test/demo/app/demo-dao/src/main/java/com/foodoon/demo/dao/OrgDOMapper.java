package com.foodoon.demo.dao;

import com.foodoon.demo.dao.domain.OrgDO;
import com.foodoon.demo.dao.domain.OrgDOCriteria;
import java.util.List;

public interface OrgDOMapper {
    int countByExample(OrgDOCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrgDO record);

    int insertSelective(OrgDO record);

    List<OrgDO> selectByExample(OrgDOCriteria example);

    OrgDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrgDO record);

    int updateByPrimaryKey(OrgDO record);
}