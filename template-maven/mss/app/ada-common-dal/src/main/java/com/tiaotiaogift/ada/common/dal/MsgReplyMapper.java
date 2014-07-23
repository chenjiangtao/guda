package com.tiaotiaogift.ada.common.dal;

import java.util.List;

import com.tiaotiaogift.ada.common.dal.dataobject.MsgReply;

public interface MsgReplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgReply record);

    int insertSelective(MsgReply record);

    MsgReply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgReply record);

    int updateByPrimaryKeyWithBLOBs(MsgReply record);

    int updateByPrimaryKey(MsgReply record);
    
    List<MsgReply> selectByMsgId(String msgId);
}