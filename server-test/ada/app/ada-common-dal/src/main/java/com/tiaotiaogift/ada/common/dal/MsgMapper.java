package com.tiaotiaogift.ada.common.dal;

import java.util.List;

import com.tiaotiaogift.ada.common.dal.dataobject.Msg;

public interface MsgMapper {
    int deleteByPrimaryKey(String id);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKeyWithBLOBs(Msg record);

    int updateByPrimaryKey(Msg record);
    
    List<Msg> selectMsgByPage(int start);
    
    int selectMsgCount();
    
    List<Msg> selectMsgByPage10(int start);
    
    List<Msg> selectNewMsg();
    
}