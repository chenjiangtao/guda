package net.zoneland.ums.common.dal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutStatByGatewayAndAppIdBo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsMsgOutUcsMapper {
    int deleteByPrimaryKey(String id);

    int insert(UmsMsgOut record);

    int insertSelective(UmsMsgOut record);

    UmsMsgOut selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsMsgOut record);

    int updateByPrimaryKey(UmsMsgOut record);

    int updateStatusById(UmsMsgOut umsMsgOut);

    /**
     * 把原来的状态更新为需要的状态
     *
     * @param status1
     * @param status2
     * @return
     */
    int updateStatusByStauts(Map<String, Object> params);

    /**
     * 查询定时发送的消息
     * @return
     */
    List<UmsMsgOut> selectDelayLimit1000(Map<String, Object> map);

    /**
     * 查询失败状态的消息
     * @return
     */
    List<UmsMsgOut> selectByStatusLimit2000(String status);

    /**
     * 条件分页查询统计全部应用【数据短信】总数量
     *
     * @param params
     * @return
     */
    int searchAllMsgNum(Map<String, Object> params);

    /**
     * 条件分页查询统计全部应用【数据短信】
     *
     * @param params
     * @return
     */
    List<UmsMsgOut> searchAllMsgByPage(Map<String, Object> params);

    int insertBatch(List<UmsMsgOut> recordList);

    /**
     * 条件查询全部应用数据短信并导出excel
     *
     * @param params
     * @return
     */
    List<UmsMsgOut> searchDataMsgforExport(Map<String, Object> params);

    /**
     * 条件查询应用数据短信总记录数
     *
     * @param params
     * @return
     */
    int searchAppDataMsgNum(Map<String, Object> params);

    /**
     * 条件查询应用数据短信
     *
     * @param params
     * @return
     */
    List<UmsMsgOut> searchAppDataMsgByPage(Map<String, Object> params);

    /**
     * 获取下行表中全部记录数
     *
     * @return
     */
    int getAllMsgOut();

    /**
     * 获取当前队列中待发送消息数量
     *
     * @return
     */
    int getMsgByStatus(String status);

    /**
     * 获取当前发送消息数量
     *
     * @return
     */
    int getMsgOutNow(String time);

    /**
     * 查询当天短信发送成功数量
     *
     * @param map
     * @return
     */
    int getDaySucessCount(Map<String, Object> map);

    void fileCreateUmsMsgOutUcs(Map<String, Object> params);

    int fileInsertUmsMsgOutUcs(Map<String, Object> params);

    void fileDeleteUmsMsgOutUcs(Map<String, Object> params);

    int fileSelectUmsMsgOutUcs(String tableName);

    int fileSelectCountUmsMsgOutUcs(Date gmtCreated);

    int updateStatus(UmsMsgOut umsMsgOut);

    /**
     * 短信查询数据短信总数
     *
     * @param params
     * @return
     */
    int queryMsgOutUcsNum(Map<String, Object> params);

    /**
     * 分页显示【短信查询数据短信】
     *
     * @param params
     * @return
     */
    @Log(name = "短信查询", comments = "短信查询数据短信")
    List<UmsMsgOut> queryMsgOutUcsByPage(Map<String, Object> params);

    /**
     * 通过主键查询消息表详细内容
     *
     * @param id
     * @return
     */
    UmsMsgOutDO selectDOByPrimaryKey(String id);

    int updateStatusByHost(Map<String, Object> map);

    List<UmsMsgOutStatByGatewayAndAppIdBo> statMsgByAppIdAndMedia(Map<String, Object> params);

}