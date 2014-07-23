package com.foodoon.info.common.dal;

import com.foodoon.info.common.dataobject.Msg;
import com.foodoon.info.common.dataobject.MsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgMapper {
    int countByExample(MsgExample example);

    int deleteByExample(MsgExample example);

    int deleteByPrimaryKey(String id);

    int insert(Msg record);

    int insertSelective(Msg record);

    List<Msg> selectByExampleWithBLOBs(MsgExample example);

    List<Msg> selectByExample(MsgExample example);

    Msg selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Msg record, @Param("example") MsgExample example);

    int updateByExampleWithBLOBs(@Param("record") Msg record, @Param("example") MsgExample example);

    int updateByExample(@Param("record") Msg record, @Param("example") MsgExample example);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKeyWithBLOBs(Msg record);

    int updateByPrimaryKey(Msg record);
}