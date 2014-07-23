/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal;

import net.zoneland.ums.common.mysql.dataobject.OutReply;

/**
 * 
 * @author gag
 * @version $Id: OutReplyMapper.java, v 0.1 2012-11-15 下午1:10:14 gag Exp $
 */
public interface OutReplyMapper {

    OutReply selectByReplyDes(String replyDes);

}
