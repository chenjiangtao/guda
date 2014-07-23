/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 *
 * @author wangyong
 * @version $Id: SaveMsgService.java, v 0.1 2012-8-14 下午2:55:35 wangyong Exp $
 */
public interface SaveMsgService {

    boolean batchSaveMsgOut(List<UmsMsgOut> sendInfoList);

    //    boolean saveMessage(List<UmsMsgOut> umsMsgOutList, List<UmsMsgOut> errorUmsMsgOut,
    //                        List<UmsMsgOut> blackMsgOuts);

    boolean saveMessage(List<UmsMsgOut> umsMsgOutList);

    boolean saveOutReply(UmsMsgOut umsMsgOut);

}
