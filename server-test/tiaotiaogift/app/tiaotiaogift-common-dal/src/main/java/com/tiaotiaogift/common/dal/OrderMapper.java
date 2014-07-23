package com.tiaotiaogift.common.dal;

import java.util.List;

import com.tiaotiaogift.common.mysql.dataobject.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUserId(String userId);

    List<Order> selectAll();

    int selectAllCount();
}