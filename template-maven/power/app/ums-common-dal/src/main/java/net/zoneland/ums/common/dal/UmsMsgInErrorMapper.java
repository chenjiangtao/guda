package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsMsgInError;

public interface UmsMsgInErrorMapper {

    int insert(UmsMsgInError record);

    /**
     * 按条件查出上行错误消息的总数.
     * 
     * @param params
     * @return
     */
    int searchMsgInErrorNum(Map<String, Object> params);

    /**
     * 按条件分页查出上行错误消息.
     * 
     * @param params
     * @return
     */
    List<UmsMsgInError> searchMsgInErrorByPage(Map<String, Object> params);

}