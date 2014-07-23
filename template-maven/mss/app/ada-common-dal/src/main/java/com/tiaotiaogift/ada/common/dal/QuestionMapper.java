package com.tiaotiaogift.ada.common.dal;

import java.util.List;

import com.tiaotiaogift.ada.common.dal.dataobject.Question;

public interface QuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);
    
    List<Question> selectByPage(int start);
    
    List<Question> selectByPage10(int start);
    
    int selectCount();
}