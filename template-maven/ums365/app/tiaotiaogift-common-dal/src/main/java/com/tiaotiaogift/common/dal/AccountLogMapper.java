package com.tiaotiaogift.common.dal;

import java.util.List;
import java.util.Map;

import com.tiaotiaogift.common.mysql.dataobject.AccountLog;

public interface AccountLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    AccountLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccountLog record);

    int updateByPrimaryKey(AccountLog record);

    List<AccountLog> selectByParam(Map<String, Object> params);

    int selectCountByParam(Map<String, Object> params);
}