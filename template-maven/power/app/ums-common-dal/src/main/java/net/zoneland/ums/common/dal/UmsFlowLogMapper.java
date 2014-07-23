package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsFlowLog;

public interface UmsFlowLogMapper {

    int insert(UmsFlowLog record);

    /**
     * 根据APPID，时间查询当前流量值
     * @param params
     * @return
     */
    List<UmsFlowLog> selectByAppId(Map<String, Object> params);

    /**
     * 查询某个时间段最新的那条流量记录
     * @param params
     * @return
     */
    List<UmsFlowLog> selectByAppIdWithTime(Map<String, Object> params);

    /**
     * 更新流量
     * @param params
     * @return
     */
    int updateFlow(Map<String, Object> params);

    /**
     *通过appID删除流量记录
     *
     * @param appId
     * @return
     */
    int deleteByAppId(String appId);
}