package com.tiaotiaogift.ada.common.dal;

import java.util.List;
import java.util.Map;

import com.tiaotiaogift.ada.common.dal.dataobject.Prod;

public interface ProdMapper {
    int deleteByPrimaryKey(String id);

    int insert(Prod record);

    int insertSelective(Prod record);

    Prod selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Prod record);

    int updateByPrimaryKeyWithBLOBs(Prod record);

    int updateByPrimaryKey(Prod record);
    
    List<Prod> selectNew();

    
    List<Prod> selectByType(Map<String,Object> params);
    
    int selectCountByType(Map<String,Object> params);
}