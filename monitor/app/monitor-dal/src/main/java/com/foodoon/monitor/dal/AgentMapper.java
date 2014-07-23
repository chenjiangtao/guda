package com.foodoon.monitor.dal;

import java.util.List;
import java.util.Map;

import com.foodoon.monitor.dal.dataobject.Agent;

public interface AgentMapper {
    int deleteByPrimaryKey(String ip);

    int insert(Agent record);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(String ip);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    List<Agent> selectAll();

    Agent selectByIpAndKey(Map<String, Object> params);

    List<Agent> selectByIpAndType(Map<String, Object> params);

    int deleteByIp(String ip);
}