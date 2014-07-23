package net.zoneland.ums.common.dal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.bo.UmsMsgOutStatByGatewayAndAppIdBo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsMsgInMapper {

    int insert(UmsMsgIn record);

    int updateByPrimaryKeySelective(UmsMsgIn record);

    List<UmsMsgIn> selectByAppWithLimit(Map<String, Object> params);

    /**
     * 根据batchNo  serialNo更新消息状态
     * @param params
     * @return
     */
    int updateByBatchNo(Map<String, Object> params);

    /**
     * 获取数据库中所有上行记录数
     *
     * @return
     */
    int getAllMsgIn();

    int getMsgInToday(String date);

    void fileCreateUmsMsgIn(Map<String, Object> params);

    int fileInsertUmsMsgIn(Map<String, Object> params);

    void fileDeleteUmsMsgIn(Map<String, Object> params);

    int fileSelectCountUmsMsgIn(Date gmtCreated);

    int fileSelectUmsMsgIn(String tableName);

    /**
     * 条件查询上行短信总数
     *
     * @param params
     * @return
     */
    int searchMsgInNum(Map<String, Object> params);

    /**
     * 条件查询分页显示上行短信
     *
     * @param params
     * @return
     */
    List<UmsMsgIn> searchMsgInByPage(Map<String, Object> params);

    /**
     * 条件【短信查询上行短信】总数
     *
     * @param params
     * @return
     */
    int queryMsgInNum(Map<String, Object> params);

    /**
     * 条件分页显示【短信查询上行短信】
     *
     * @param params
     * @return
     */
    @Log(name = "短信查询", comments = "短信查询上行短信")
    List<UmsMsgIn> queryMsgInByPage(Map<String, Object> params);

    /**
     * 查询统计上行短信并导出excel表
     *
     * @param params
     * @return
     */
    List<UmsMsgIn> searchMsgInforExport(Map<String, Object> params);

    /**
     * 条件查询分页显示应用上行短信
     *
     * @param params
     * @return
     */
    int searchAppMsgInNum(Map<String, Object> params);

    /**
     * 条件查询分页显示应用上行短信
     *
     * @param params
     * @return
     */
    List<UmsMsgIn> searchAppMsgInByPage(Map<String, Object> params);

    /**
     *
     * @param id
     * @return
     */
    UmsMsgIn selectByPrimaryKey(String id);

    int updateStatusByPrimaryKey(Map<String, Object> params);

    List<UmsMsgOutStatByGatewayAndAppIdBo> statMsgByAppIdAndMedia(Map<String, Object> params);
}