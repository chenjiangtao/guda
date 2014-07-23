/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.out;

/**
 * 
 * @author gang
 * @version $Id: MsgOutService.java, v 0.1 2012-9-8 下午6:04:46 gang Exp $
 */
public interface MsgOutService {

    /**
     * 更新消息状态
     * @param id
     * @param status
     * @param errorMsg
     * @return
     */
    boolean updateMsgOut(String id, String status, String errorMsg);

    boolean updateMsgOutUcs(String id, String status, String errorMsg);

}
