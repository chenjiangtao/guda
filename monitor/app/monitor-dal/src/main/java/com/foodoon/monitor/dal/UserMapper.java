package com.foodoon.monitor.dal;

import com.foodoon.monitor.dal.dataobject.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectValidByUserId(String userName);
}