package net.zoneland.ums.common.dal;

import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsOutReply;

public interface UmsOutReplyMapper {

    int deleteByPrimaryKey(String id);

    int insert(UmsOutReply record);

    int insertSelective(UmsOutReply record);

    UmsOutReply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsOutReply record);

    int updateByPrimaryKey(UmsOutReply record);

    UmsOutReply selectByReplyAppIdSubAppId(Map<String, String> map);

    UmsOutReply selectByReplyNum(Integer replyDes);
}