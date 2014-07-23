package com.foodoon.info.common.dal;

import com.foodoon.info.common.dataobject.SubClassify;
import com.foodoon.info.common.dataobject.SubClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubClassifyMapper {
    int countByExample(SubClassifyExample example);

    int deleteByExample(SubClassifyExample example);

    int deleteByPrimaryKey(String id);

    int insert(SubClassify record);

    int insertSelective(SubClassify record);

    List<SubClassify> selectByExample(SubClassifyExample example);

    SubClassify selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SubClassify record, @Param("example") SubClassifyExample example);

    int updateByExample(@Param("record") SubClassify record, @Param("example") SubClassifyExample example);

    int updateByPrimaryKeySelective(SubClassify record);

    int updateByPrimaryKey(SubClassify record);
}