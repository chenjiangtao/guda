package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsStat;

public interface UmsStatMapper {

    int insert(UmsStat record);

    int insertSelective(UmsStat record);

    List<UmsStat> searchByPage(Map<String, Object> params);

    int searchCountByPage(Map<String, Object> params);

    List<UmsStat> searchByTime(Map<String, Object> params);
}