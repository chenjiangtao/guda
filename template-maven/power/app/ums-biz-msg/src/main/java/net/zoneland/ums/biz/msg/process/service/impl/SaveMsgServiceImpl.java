/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service.impl;

import java.util.List;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.biz.msg.process.service.SaveMsgService;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.UmsOutReplyMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsOutReply;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangyong
 * @version $Id: SaveMsgServiceImpl.java, v 0.1 2012-8-14 下午2:56:34 wangyong Exp $
 */
public class SaveMsgServiceImpl implements SaveMsgService {

    public static final int    defaultSaveDBRecordFactor = 200;

    private UmsMsgOutMapper    umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper umsMsgOutUcsMapper;

    @Autowired
    private UmsOutReplyMapper  umsOutReplyMapper;

    /**
     * 批存储消息。
     * @see net.zoneland.ums.biz.msg.process.service.SaveMsgService#batchSaveMsgSendInfo(java.util.List)
     */
    public boolean batchSaveMsgOut(List<UmsMsgOut> sendInfoList) {
        if (sendInfoList == null || sendInfoList.size() == 0) {
            return false;
        }
        int msgType = sendInfoList.get(0).getMsgType();

        boolean res = false;
        int size = sendInfoList.size();
        if (size > defaultSaveDBRecordFactor) {
            int count = size / defaultSaveDBRecordFactor;
            for (int i = 0; i < count; ++i) {
                List<UmsMsgOut> temp = null;
                if (i == count - 1) {
                    temp = sendInfoList.subList(i * defaultSaveDBRecordFactor, size);
                } else {
                    temp = sendInfoList.subList(i * defaultSaveDBRecordFactor,
                        (i + 1) * defaultSaveDBRecordFactor);
                }
                if (msgType != 15) {
                    umsMsgOutUcsMapper.insertBatch(temp);
                } else {
                    umsMsgOutMapper.insertBatch(temp);
                }
            }
        } else {
            if (msgType != 15) {
                umsMsgOutUcsMapper.insertBatch(sendInfoList);
            } else {
                umsMsgOutMapper.insertBatch(sendInfoList);
            }
        }
        res = true;
        return res;
    }

    //    /**
    //     *保存消息到数据库
    //     *
    //     * @param umsMsgOutList
    //     * @param errorUmsMsgOut
    //     * @return
    //     */
    //    public boolean saveMessage(List<UmsMsgOut> umsMsgOutList, List<UmsMsgOut> errorUmsMsgOut,
    //                               List<UmsMsgOut> blackMsgOuts) {
    //        logger.info("保存正确消息：" + umsMsgOutList.size());
    //        boolean save1 = saveMessage(umsMsgOutList);
    //        logger.info("保存错误消息：" + errorUmsMsgOut.size());
    //        saveMessage(errorUmsMsgOut);
    //        saveMessage(blackMsgOuts);
    //        if (save1) {
    //            return true;
    //        }
    //        return false;
    //    }

    /**
     * 保存消息
     * @see net.zoneland.ums.biz.msg.process.service.SaveMsgService#saveMessage(java.util.List)
     */
    public boolean saveMessage(List<UmsMsgOut> umsMsgOutList) {
        boolean save = false;
        if (umsMsgOutList == null) {
            return false;
        }
        if (umsMsgOutList.size() > 0) {
            save = batchSaveMsgOut(umsMsgOutList);
        }
        return save;
    }

    /**
     * @see net.zoneland.ums.biz.msg.process.service.SaveMsgService#saveOutReply(java.util.List)
     */
    public boolean saveOutReply(UmsMsgOut umsMsgOut) {

        UmsOutReply umsOutReply = new UmsOutReply();
        ObjectBuilder.copyObject(umsMsgOut, umsOutReply);

        try {
            int result = umsOutReplyMapper.insert(umsOutReply);
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UmsMsgOutMapper getUmsMsgOutMapper() {
        return umsMsgOutMapper;
    }

    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

    /**
     * Setter method for property <tt>umsOutReplyMapper</tt>.
     *
     * @param umsOutReplyMapper value to be assigned to property umsOutReplyMapper
     */
    public void setUmsOutReplyMapper(UmsOutReplyMapper umsOutReplyMapper) {
        this.umsOutReplyMapper = umsOutReplyMapper;
    }

}
