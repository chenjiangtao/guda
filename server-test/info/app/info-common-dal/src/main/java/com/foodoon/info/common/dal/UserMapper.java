package com.foodoon.info.common.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foodoon.info.common.dataobject.User;
import com.foodoon.info.common.dataobject.UserExample;

public interface UserMapper {

    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> searchByPage(Map<String, Object> params);

    int searchCountByPage(Map<String, Object> params);
}
