package com.tiaotiaogift.common.dal;

import com.tiaotiaogift.common.mysql.dataobject.Account;

public interface AccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account selectByUserId(String userId);
}