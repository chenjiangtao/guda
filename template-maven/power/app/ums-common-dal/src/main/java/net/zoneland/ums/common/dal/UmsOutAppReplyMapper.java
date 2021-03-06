package net.zoneland.ums.common.dal;

import java.util.Date;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsOutReply;

public interface UmsOutAppReplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UMS_OUT_APP_REPLY
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UMS_OUT_APP_REPLY
     *
     * @mbggenerated
     */
    int insert(UmsOutReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UMS_OUT_APP_REPLY
     *
     * @mbggenerated
     */
    int insertSelective(UmsOutReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UMS_OUT_APP_REPLY
     *
     * @mbggenerated
     */
    UmsOutReply selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UMS_OUT_APP_REPLY
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UmsOutReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UMS_OUT_APP_REPLY
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsOutReply record);

    UmsOutReply selectByReplyAppIdSubAppId(Map<String, String> map);

    UmsOutReply selectByReplyNum(Integer replyDes);

    int deleteLastWeekReply(Date gmtCreated);
}