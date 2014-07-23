package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.bo.UmsActionLogBo;
import net.zoneland.ums.common.dal.dataobject.UmsActionLog;
import net.zoneland.ums.common.dal.util.PageSearch;

public interface UmsActionLogMapper {

    int insert(UmsActionLog record);
    
    int searchAllNum(UmsActionLogBo bo);
    
    List<UmsActionLog> searchSelectiveByPage(PageSearch ps);
}