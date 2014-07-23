/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.search.service;

import java.util.Date;
import java.util.List;

import net.zoneland.ums.common.dal.bo.UmsMsgOutBo;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutIphone;
import net.zoneland.ums.common.dal.dataobject.UmsMsgIn;
import net.zoneland.ums.common.util.page.PageResult;

/**
 *
 * @author wangyong
 * @version $Id: MsgSearchBiz.java, v 0.1 2012-8-22 下午9:13:35 wangyong Exp $
 */
public interface MsgSearchService {
    /**
     *多条件查询消息记录
     *
     * @param bo
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOutDO> searchMsgInfoByPage(UmsMsgOutBo bo, int curPage);

    /**
     *查询个人已发送消息，对接收方是手机号或者ID做了分别处理
     *
     * @param bo
     * @param recvId 
     * @param curPage
     * @return
     */
    public PageResult<UmsMsgOutDO> searchSendMsgInfoByPage(UmsMsgOutBo bo, int curPage);

    /**
     * 查询当前用户已接收的消息。
     *
     * @param bo
     * @param curPage
     * @return
     */

    public PageResult<UmsMsgOutDO> searchAcceptMsgInfoByPage(UmsMsgOutBo bo, int curPage);

    /**
     * 通过id查询已发送的详细信息以及查询统计页面用户短信的详细信息
     *
     * @param id
     * @return
     */
    UmsMsgOutDO getSendMsgInfoById(String id);

    /**
     *通过id查询已接收的详细信息
     *
     * @param id
     * @return
     */
    UmsMsgOutDO getAcceptMsgInfoById(String id);

    /**
     * iphone客户端查询接收到的消息
     * @param recvId
     * @return
     */
    List<UmsMsgOutIphone> findByRecvId(String recvId, Date sinceTime, int pageId, int pageSize);

    /**
     * iphone客户端查询接收到的消息数量
     * @param recvId
     * @return
     */
    Integer findCountByRecvId(String recvId, Date sinceTime);

    /**
     * 逻辑删除消息
     * @param id
     * @return
     */
    boolean delMsg(String id);

    /**
     * 更新消息状态为已读
     * @param id
     * @return
     */
    boolean setMsgRead(String id);

    /**
     * 数据短信的详细信息
     * 
     * @param id
     * @return
     */
    public UmsMsgOutDO getDataMsgInfoById(String id);

    /**
     * 上行短信的详细信息
     * 
     * @param id
     * @return
     */
    public UmsMsgIn getMsgInById(String id);
}
