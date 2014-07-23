package com.tiaotiaogift.common.dal;

import java.util.List;
import java.util.Map;

import com.tiaotiaogift.common.mysql.dataobject.MsgOut;
import com.tiaotiaogift.common.mysql.dataobject.MsgOutVo;

public interface MsgOutMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgOut record);

    int insertSelective(MsgOut record);

    MsgOut selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgOut record);

    int updateByPrimaryKeyWithBLOBs(MsgOut record);

    int updateByPrimaryKey(MsgOut record);

    List<MsgOut> selectByRecvId(String recvId);

    List<MsgOut> selectByParams(Map<String, Object> params);

    int selectCountByParams(Map<String, Object> params);

    List<MsgOut> selectWait();

    int selectCountToday();

    int selectCountByUser(String userId);

    List<MsgOutVo> selectByUserName(Map<String, Object> params);

    int selectCountByUserName(Map<String, Object> params);

    List<MsgOut> selectByRecvIdLimit(String recvId);

    MsgOut selectRecentByUserId(String userId);

    int updateStatusBatch(Map<String, Object> params);
}