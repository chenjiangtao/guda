package com.tiaotiaogift.common.dal;

import java.util.List;

import com.tiaotiaogift.common.mysql.dataobject.Doc;

public interface DocMapper {
    int deleteByPrimaryKey(String id);

    int insert(Doc record);

    int insertSelective(Doc record);

    Doc selectByPrimaryKey(String id);

    List<Doc> selectByPageId(int pageId);

    List<Doc> selectByType(String type);

    int updateByPrimaryKeySelective(Doc record);

    int updateByPrimaryKeyWithBLOBs(Doc record);

    int updateByPrimaryKey(Doc record);
}