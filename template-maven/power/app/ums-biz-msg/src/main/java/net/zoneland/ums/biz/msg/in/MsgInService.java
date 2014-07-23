/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in;

import java.util.List;

import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author gag
 * @version $Id: MsgInService.java, v 0.1 2012-8-24 下午6:01:58 gag Exp $
 */
public interface MsgInService {

    /**
     * 消息成功取走后，更新状态为成功
     * @param batchNo
     * @param serialNo
     * @return
     */
    boolean fetchMsgSuccess(String batchNo, String serialNo);

    /**
     * 取消息，同时更新消息状态为成功取走
     * 对同一appId进行同步
     * @param appId
     * @param subAppId
     * @return
     */
    List<UmsMsgIn> fetchMsgIn(String appId, String subAppId, String status, int maxCount);

    /**
     * 保存上行短信
     * @param umsMsgIn
     * @return
     */
    boolean saveMsgIn(String batchNo, String serialNo, String appId, String subApp,
                      String appSerialNo, String mediaId, String sendId, String recvId,
                      String content, int ack, String reply, int msgType, String retCode,
                      String errMsg);

    /**
     * 保存上行短信
     * @param umsMsgIn
     * @return
     */
    boolean saveMsgIn(UmsMsgIn umsMsgIn);

    /**
     * 条件分页查询统计上行短信
     * 
     * @param appMsgInfoBO
     * @param pageId
     * @return
     */
    PageResult<UmsMsgIn> searchMsgIn(AppMsgInfoBO appMsgInfoBO, int pageId);

    /**
     * 查询统计上行短信并导出excel表
     * 
     * @param appMsgInfoBO
     * @param path
     */
    public void exportMsgInExcel(AppMsgInfoBO appMsgInfoBO, String path);

}
