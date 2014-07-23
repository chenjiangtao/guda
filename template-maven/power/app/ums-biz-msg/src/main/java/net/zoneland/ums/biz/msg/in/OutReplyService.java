/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in;

import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;

/**
 * 
 * @author gag
 * @version $Id: OutReplyService.java, v 0.1 2012-8-29 下午1:32:17 gag Exp $
 */
public interface OutReplyService {

    UmsOutReply findByReplyNum(String replyDes);

    UmsOutReply findByAppReplyNum(String replyDes);

    /**
     * 根据短信新增回复记录.
     * @param umsOutReply
     * @return
     */
    boolean saveOutReplyByMsg(UmsMsgOut umsMsgOut);

    //    int delOutReply(String replyDes, String recvId) ;

    /**
     * 通过手机号，APPID，SubAPP查询对应的回复号。
     * 
     * @param umsMsgOut
     * @return
     */
    UmsOutReply getOutReplyByMsg(UmsMsgOut umsMsgOut);

    UmsOutReply getOutAppReplyByMsg(UmsMsgOut umsMsgOut);

}
