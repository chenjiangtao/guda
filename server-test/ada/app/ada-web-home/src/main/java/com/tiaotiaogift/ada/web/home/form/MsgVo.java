/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.ada.web.home.form;

import java.util.List;

import com.tiaotiaogift.ada.common.dal.dataobject.Msg;
import com.tiaotiaogift.ada.common.dal.dataobject.MsgReply;

/**
 * 
 * @author gang
 * @version $Id: MsgVo.java, v 0.1 2012-12-22 下午10:51:04 gang Exp $
 */
public class MsgVo {
    
    private Msg msg;
    
    private List<MsgReply> msgReplys;

    /**
     * Getter method for property <tt>msg</tt>.
     * 
     * @return property value of msg
     */
    public Msg getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     * 
     * @param msg value to be assigned to property msg
     */
    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public List<MsgReply> getMsgReplys() {
        return msgReplys;
    }

    public void setMsgReplys(List<MsgReply> msgReplys) {
        this.msgReplys = msgReplys;
    }



}
