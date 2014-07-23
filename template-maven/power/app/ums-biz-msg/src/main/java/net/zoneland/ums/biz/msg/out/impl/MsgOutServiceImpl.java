/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.out.impl;

import java.util.Date;

import net.zoneland.ums.biz.msg.out.MsgOutService;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 
 * @author gang
 * @version $Id: MsgOutServiceImpl.java, v 0.1 2012-9-8 下午6:07:25 gang Exp $
 */
public class MsgOutServiceImpl implements MsgOutService {

    @Autowired
    private UmsMsgOutMapper    umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper umsMsgOutUcsMapper;

    /** 
     * @see net.zoneland.ums.biz.msg.out.MsgOutService#updateMsgOut(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean updateMsgOut(String id, String status, String errorMsg) {
        UmsMsgOut umsMsgOut = new UmsMsgOut();
        umsMsgOut.setId(id);
        umsMsgOut.setStatus(status);
        if (StringUtils.hasText(errorMsg)) {
            umsMsgOut.setErrorMsg(errorMsg);
        }
        umsMsgOut.setGmtModified(new Date());
        if (MsgInfoStatusEnum.failure.getValue().equals(status)) {
            UmsMsgOut temp = umsMsgOutMapper.selectByPrimaryKey(id);
            if (temp != null) {
                umsMsgOut.setDocount(temp.getDocount() + 1);
            }
        }
        return umsMsgOutMapper.updateStatus(umsMsgOut) == 1;
    }

    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

    /** 
     * @see net.zoneland.ums.biz.msg.out.MsgOutService#updateMsgOutUcs(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean updateMsgOutUcs(String id, String status, String errorMsg) {
        UmsMsgOut umsMsgOut = new UmsMsgOut();
        umsMsgOut.setId(id);
        umsMsgOut.setStatus(status);
        if (StringUtils.hasText(errorMsg)) {
            umsMsgOut.setErrorMsg(errorMsg);
        }
        umsMsgOut.setGmtModified(new Date());
        if (MsgInfoStatusEnum.failure.getValue().equals(status)) {
            UmsMsgOut temp = umsMsgOutUcsMapper.selectByPrimaryKey(id);
            if (temp != null) {
                umsMsgOut.setDocount(temp.getDocount() + 1);
            }
        }
        return umsMsgOutUcsMapper.updateStatus(umsMsgOut) == 1;
    }

}
