/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service;

import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 * 
 * @author Administrator
 * @version $Id: MsgTempService.java, v 0.1 2012-12-28 下午4:06:04 Administrator Exp $
 */
public interface MsgTempService {

    public boolean validateMsgTemp(UmsMsgOut umsMsgOut);

}
