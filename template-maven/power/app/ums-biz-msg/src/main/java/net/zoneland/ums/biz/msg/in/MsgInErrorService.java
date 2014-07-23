/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in;

import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInError;
import net.zoneland.ums.common.util.page.PageResult;

/**
 *
 * @author gang
 * @version $Id: MsgInErrorService.java, v 0.1 2012-8-31 下午8:07:46 gang Exp $
 */
public interface MsgInErrorService {

    /**
     * 保存错误的上行数据
     * @param sendNumber
     * @param recvNumber
     * @param recvMsg
     * @param mediaId
     * @param data_code
     * @return
     */
    int saveMsgInError(String sendNumber, String recvNumber, String recvMsg, String mediaId,
                       int data_code);

    /**
     * 分页查询上行错误信息
     * 
     * @param curPage
     * @param msgInErrorBo
     * @return
     */
    PageResult<UmsMsgInError> findAll(int curPage, AppMsgInfoBO msgInErrorBo);
}
