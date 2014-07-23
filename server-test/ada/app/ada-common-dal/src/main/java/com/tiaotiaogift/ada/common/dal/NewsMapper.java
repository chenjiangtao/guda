package com.tiaotiaogift.ada.common.dal;

import java.util.List;

import com.tiaotiaogift.ada.common.dal.dataobject.News;

public interface NewsMapper {
    int deleteByPrimaryKey(String id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKeyWithBLOBs(News record);

    int updateByPrimaryKey(News record);
    
    List<News> selectByPage(int start);
    
    List<News> selectByPage10(int start);
    
    int selectCount();
    
    List<News> selectNews();
}