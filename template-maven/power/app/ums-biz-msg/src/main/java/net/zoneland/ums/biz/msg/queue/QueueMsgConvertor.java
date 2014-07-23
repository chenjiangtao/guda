/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;

/**
 *
 * @author gag
 * @version $Id: QueueMsgConvertor.java, v 0.1 2012-9-13 下午5:15:25 gag Exp $
 */
public class QueueMsgConvertor {

    public static Collection<QueueMessage> groupBySendIdMediaId(List<UmsMsgOut> umsMsgOutList) {
        if (umsMsgOutList != null && umsMsgOutList.size() > 0) {
            List<QueueMessage> out = new ArrayList<QueueMessage>(umsMsgOutList.size());
            for (int i = 0, len = umsMsgOutList.size(); i < len; i++) {
                UmsMsgOut temp = umsMsgOutList.get(i);
                //                String key = getKey(temp);
                //                QueueMessage qm = out.get(key);
                //                if (qm != null) {
                //                    qm.getIdArray().add(temp.getId());
                //                    qm.getRecvIdArray().add(temp.getRecvId());
                //                } else {
                QueueMessage qm = new QueueMessage();
                qm.setAck(temp.getAck());
                qm.setAppId(temp.getAppId());
                qm.setAppSerialNo(temp.getAppSerialNo());
                qm.setBatchNo(temp.getBatchNo());
                qm.setContent(temp.getContent());
                qm.setContentType(temp.getMsgType());
                qm.setFee(temp.getFee());
                qm.setFeeType(temp.getFeeType());
                List<String> idArray = new ArrayList<String>();
                idArray.add(temp.getId());
                qm.setIdArray(idArray);
                qm.setMediaId(temp.getMediaId());
                qm.setPriority(temp.getPriority());
                List<String> recvIdArray = new ArrayList<String>();
                recvIdArray.add(temp.getRecvId());
                qm.setRecvIdArray(recvIdArray);
                qm.setSendTime(temp.getSendTime());
                qm.setReply(temp.getReply());
                qm.setReplyDes(temp.getReplyDes());
                qm.setSendId(temp.getSendId());
                qm.setSerialNo(temp.getSerialNo());
                qm.setSubAppId(temp.getSubApp());
                qm.setStatus(temp.getStatus());
                out.add(qm);
            }
            return out;
        }
        return Collections.emptyList();
    }

    //    private static String getKey(UmsMsgOut out) {
    //        StringBuilder buf = new StringBuilder();
    //        buf.append(out.getSendId()).append(out.getGroupSerial()).append(out.getMediaId())
    //            .append(out.getContent());
    //        return buf.toString();
    //    }

}
