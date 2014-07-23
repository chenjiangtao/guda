package net.zoneland.ums.common.dal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.bo.UmsMsgOutBo;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutIphone;
import net.zoneland.ums.common.dal.bo.UmsMsgOutStat;
import net.zoneland.ums.common.dal.bo.UmsMsgOutStatByGatewayAndAppIdBo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsMsgOutMapper {

    int insertBatch(List<UmsMsgOut> recordList);

    UmsMsgOut selectByPrimaryKey(String id);

    UmsMsgOutDO selectDOByPrimaryKey(String id);

    /**
     *通过ID更新信息状态
     *
     * @param umsMsgOut
     * @return
     */
    int updateStatusById(UmsMsgOut umsMsgOut);

    int updateStatus(UmsMsgOut umsMsgOut);

    /**
     *立即发送的流量
     *日流量，跟月流量
     *一个是时间限制，另一个是状态限制
     * @param map
     * @return
     */
    int getFlow(Map<String, Object> map);

    /**
     *定时发送的流量
     *
     * @param map
     * @return
     */
    int getSendTimeFlow(Map<String, Object> map);

    /**
     * 各种条件查询
     *
     * @param pagesearch
     * @return
     */
    List<UmsMsgOutDO> searchMyMsgByPage(PageSearch pagesearch);

    int searchAllNum(UmsMsgOutBo umsMsgOutBo);

    /**
     * 条件查询指定应用发出消息 统计数量
     *
     * @param params
     * @return
     */
    int searchAppMsgNum(Map<String, Object> params);

    /**
     * 分页查询指定应用发出消息
     *
     * @param params
     * @return
     */
    List<UmsMsgOut> searchAppMsgByPage(Map<String, Object> params);

    /**
     * 条件分页查询统计全部应用【用户短信】总数量
     *
     * @param params
     * @return
     */
    int searchAllMsgNum(Map<String, Object> params);

    /**
     * 条件分页查询统计全部应用【用户短信】
     *
     * @param params
     * @return
     */
    List<UmsMsgOut> searchAllMsgByPage(Map<String, Object> params);

    /**
     * 条件查询全部应用用户短信并导出excel
     *
     * @param params
     * @return
     */
    List<UmsMsgOut> searchMsgforExport(Map<String, Object> params);

    /**
     * 获取下行表中全部记录数
     *
     * @return
     */
    int getAllMsgOut();

    /**
     * 获取当前发送消息数量
     *
     * @return
     */
    int getMsgOutNow(String time);

    /**
     * 获取当前队列中待发送消息数量
     *
     * @return
     */
    int getMsgByStatus(String status);

    /**
     * 查询初始化的记录数量
     * @return
     */
    int getInitMsgOut();

    /**
     * 按照渠道分组查询非成功状态的消息
     * @return
     */
    List<UmsMsgOutStat> selectMsgOutStat();

    /**
     * 查询失败状态的消息
     * @return
     */
    List<UmsMsgOut> selectByStatusLimit1000ForMarket(Map<String, String> map);

    List<UmsMsgOut> selectByStatusLimit1000ForNotMarket(Map<String, String> map);

    /**
     * 查询失败状态的消息
     * @return
     */
    List<UmsMsgOut> selectByStatus(Map<String, Object> map);

    /**
     * 查询定时发送的消息
     * @return
     */
    List<UmsMsgOut> selectDelayLimit1000ForMarket(Map<String, Object> map);

    List<UmsMsgOut> selectDelayLimit1000ForNotMarket(Map<String, Object> map);

    /**
     * 查询当天短信发送成功数量
     *
     * @param map
     * @return
     */
    int getDaySucessCount(Map<String, Object> map);

    /**
     * 根据接收方手机号模糊匹配查询出当前用户所有已发送消息
     *
     * @param params
     * @return
     */
    List<UmsMsgOutDO> searchMyMsgByRecvId(Map<String, Object> params);

    /**
     * 根据接收方手机号模糊匹配查询出当前用户所有已发送消息总数
     *
     * @param params
     * @return
     */
    int searchAllNumByRecvId(Map<String, Object> params);

    /**
     * 根据发送方姓名模糊匹配查询出当前用户所有已接收消息总数
     *
     * @param params
     * @return
     */
    int searchAllNumByUserId(Map<String, Object> params);

    /**
     * 根据发送方姓名模糊匹配查询出当前用户所有已接收消息
     *
     * @param params
     * @return
     */
    List<UmsMsgOutDO> searchMyMsgByUserId(Map<String, Object> params);

    /**
     * 把原来的状态更新为需要的状态
     *
     * @param status1
     * @param status2
     * @return
     */
    int updateStatusByStauts(Map<String, Object> params);

    /**
     * iphone客户端查询接收到的消息
     * @param recvId
     * @return
     */
    List<UmsMsgOutIphone> searchByRecvId(Map<String, Object> params);

    /**
     * iphone客户端查询接收到的消息数量
     * @param recvId
     * @return
     */
    Integer searchCountByRecvId(Map<String, Object> params);

    int updateMobileStatus(Map<String, Object> params);

    void fileCreateUmsMsgOut(Map<String, Object> params);

    int fileInsertUmsMsgOut(Map<String, Object> params);

    void fileDeleteUmsMsgOut(Map<String, Object> params);

    int fileSelectUmsMsgOut(String tableName);

    int fileSelectCountUmsMsgOut(Date gmtCreated);

    /**
     * 短信查询用户短信总数
     *
     * @param params
     * @return
     */
    int queryMsgOutNum(Map<String, Object> params);

    /**
     * 分页显示【短信查询用户短信】
     *
     * @param params
     * @return
     */
    @Log(name = "短信查询", comments = "短信查询用户短信")
    List<UmsMsgOut> queryMsgOutByPage(Map<String, Object> params);

    //用于更新集群中一个服务器当掉之后队列状态的数据
    int updateStatusByHost(Map<String, Object> map);

    List<UmsMsgOutStatByGatewayAndAppIdBo> statMsgByAppIdAndMedia(Map<String, Object> params);

}