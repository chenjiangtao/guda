package net.zoneland.ums.common.dal;

import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsLockApp;

public interface UmsLockAppMapper {

    int insert(UmsLockApp record);

    int deleteByPrimaryKey(String appId);

    int deleteByHost(Map<String, Object> map);

    int deleteAll();
}