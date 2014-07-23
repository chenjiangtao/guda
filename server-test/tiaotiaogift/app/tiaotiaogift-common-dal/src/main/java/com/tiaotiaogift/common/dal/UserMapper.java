package com.tiaotiaogift.common.dal;

import java.util.List;

import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.mysql.dataobject.UserAccount;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserId(String userId);

    User selectValidByUserId(String userId);

    User selectByEmail(String email);

    List<UserAccount> selectAll();

    int selectAllCount();

    Integer selectMaxLinkId();

    User selectUserByLinkId(Integer linkId);

}