/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 *
 * @author wangyong
 * @version $Id: BlackListProcess.java, v 0.1 2012-8-15 下午9:38:27 wangyong Exp $
 */
public interface BlackListProcess {
    List<UmsMsgOut> process(List<UmsMsgOut> umsMsgOutList);
}
