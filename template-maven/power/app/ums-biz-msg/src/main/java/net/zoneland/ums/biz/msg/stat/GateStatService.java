/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.stat;

import java.util.List;

import net.zoneland.ums.biz.msg.stat.bo.GateStatBo;
import net.zoneland.ums.biz.msg.stat.bo.UmsStatVo;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 
 * @author gang
 * @version $Id: GateStatService.java, v 0.1 2013-3-26 下午4:20:01 gang Exp $
 */
public interface GateStatService {

    PageResult<UmsStatVo> searchGateStat(GateStatBo bo);

    List<UmsStatVo> searchGateStatForExport(GateStatBo bo);

}
