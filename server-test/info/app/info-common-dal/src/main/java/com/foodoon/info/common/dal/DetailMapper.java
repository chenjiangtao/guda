package com.foodoon.info.common.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foodoon.info.common.dataobject.Detail;
import com.foodoon.info.common.dataobject.DetailExample;
import com.foodoon.info.common.dataobject.DetailWithBLOBs;

public interface DetailMapper {

    int countByExample(DetailExample example);

    int deleteByExample(DetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(DetailWithBLOBs record);

    int insertSelective(DetailWithBLOBs record);

    List<DetailWithBLOBs> selectByExampleWithBLOBs(DetailExample example);

    List<Detail> selectByExample(DetailExample example);

    DetailWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DetailWithBLOBs record, @Param("example") DetailExample example);

    int updateByExampleWithBLOBs(@Param("record") DetailWithBLOBs record, @Param("example") DetailExample example);

    int updateByExample(@Param("record") Detail record, @Param("example") DetailExample example);

    int updateByPrimaryKeySelective(DetailWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DetailWithBLOBs record);

    int updateByPrimaryKey(Detail record);

    List<DetailWithBLOBs> search(Map<String, Object> params);

    int searchCount(Map<String, Object> params);

}
