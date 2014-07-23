package com.foodoon.info.common.dal;

import com.foodoon.info.common.dataobject.MsgReply;
import com.foodoon.info.common.dataobject.MsgReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgReplyMapper {
    int countByExample(MsgReplyExample example);

    int deleteByExample(MsgReplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(MsgReply record);

    int insertSelective(MsgReply record);

    List<MsgReply> selectByExampleWithBLOBs(MsgReplyExample example);

    List<MsgReply> selectByExample(MsgReplyExample example);

    MsgReply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MsgReply record, @Param("example") MsgReplyExample example);

    int updateByExampleWithBLOBs(@Param("record") MsgReply record, @Param("example") MsgReplyExample example);

    int updateByExample(@Param("record") MsgReply record, @Param("example") MsgReplyExample example);

    int updateByPrimaryKeySelective(MsgReply record);

    int updateByPrimaryKeyWithBLOBs(MsgReply record);

    int updateByPrimaryKey(MsgReply record);
}