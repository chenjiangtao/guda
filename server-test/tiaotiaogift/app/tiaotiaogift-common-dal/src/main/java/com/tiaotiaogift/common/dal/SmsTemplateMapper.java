package com.tiaotiaogift.common.dal;

import java.util.List;
import java.util.Map;

import com.tiaotiaogift.common.mysql.dataobject.SmsTemplate;

public interface SmsTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKeyWithBLOBs(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);

    List<SmsTemplate> selectByUserId(Map<String, Object> params);

    int selectCountByUserId(Map<String, Object> params);
}