package com.tiaotiaogift.common.dal;

import java.util.List;
import java.util.Map;

import com.tiaotiaogift.common.mysql.dataobject.Contact;

public interface ContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contact record);

    int insertSelective(Contact record);

    Contact selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKey(Contact record);

    List<Contact> selectByUserId(Map<String, Object> map);

    int selectCountByUserId(Map<String, Object> map);

    List<Contact> searchByParams(Map<String, Object> map);

    int searchCountByParams(Map<String, Object> map);

    int insertBatch(List<Contact> list);

    Contact selectByPhone(Map<String, Object> map);

    int delByPhone(Map<String, Object> map);
}