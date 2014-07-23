package com.foodoon.info.common.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foodoon.info.common.dataobject.Img;
import com.foodoon.info.common.dataobject.ImgExample;

public interface ImgMapper {
    int countByExample(ImgExample example);

    int deleteByExample(ImgExample example);

    int deleteByPrimaryKey(String id);

    int insert(Img record);

    int insertSelective(Img record);

    List<Img> selectByExample(ImgExample example);

    Img selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Img record, @Param("example") ImgExample example);

    int updateByExample(@Param("record") Img record, @Param("example") ImgExample example);

    int updateByPrimaryKeySelective(Img record);

    int updateByPrimaryKey(Img record);

    int deleteTempByPrimaryKey(String id);
}