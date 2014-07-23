package com.tiaotiaogift.common.dal;

import java.util.List;
import java.util.Map;

import com.tiaotiaogift.common.mysql.dataobject.MsgIn;

public interface MsgInMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgIn record);

    int insertSelective(MsgIn record);

    MsgIn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgIn record);

    int updateByPrimaryKeyWithBLOBs(MsgIn record);

    int updateByPrimaryKey(MsgIn record);

    List<MsgIn> selectRecv(Map<String, Object> params);

    int selectCount();

    List<MsgIn> selectByParams(Map<String, Object> params);

    int selectCountByParams(Map<String, Object> params);

    List<MsgIn> fetchMsgIn(String userId);

    int updateStatusBatch(Map<String, Object> params);

}