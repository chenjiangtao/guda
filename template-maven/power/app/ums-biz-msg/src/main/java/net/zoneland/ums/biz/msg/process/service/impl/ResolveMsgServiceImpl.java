/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.biz.msg.process.service.ResolveMsgService;
import net.zoneland.ums.biz.msg.process.service.ResolveUserService;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.SerialNoHelper;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.apache.log4j.Logger;

/**
 *
 * @author wangyong
 * @version $Id: ResolveMsgServiceImpl.java, v 0.1 2012-8-14 下午12:42:50 wangyong Exp $
 */
public class ResolveMsgServiceImpl implements ResolveMsgService {

    private static final Logger logger = Logger.getLogger(ResolveMsgServiceImpl.class);

    private ResolveUserService  resolveUserService;

    public ResolveMsgServiceImpl() {
    }

    /**
     * @see net.zoneland.ums.biz.msg.process.service.ResolveMsgService#gerUmsMsgOutList(net.zoneland.ums.biz.msg.PrimitiveMessage)
     */
    public Map<String, List<UmsMsgOut>> gerUmsMsgOutList(PrimitiveMessage primitiveMessage) {
        logger.info("解析接收方！");
        Map<String, HashSet<String>> map = resolveUserService.getRecvIdList(primitiveMessage
            .getRecvId());
        List<UmsMsgOut> correctUmsMsgOutList = getCorrectUmsMsgOutList(primitiveMessage,
            map.get("correctUserIdList"));
        List<UmsMsgOut> errorUmsMsgOutList = getErrorUmsMsgOutList(primitiveMessage,
            map.get("errorUserIdList"));
        Map<String, List<UmsMsgOut>> msgMap = new HashMap<String, List<UmsMsgOut>>();
        msgMap.put("correctUmsMsgOutList", correctUmsMsgOutList);
        msgMap.put("errorUmsMsgOutList", errorUmsMsgOutList);
        return msgMap;
    }

    /**
     * 初步得到正确的消息集合
     * 1.接收方是用户ID
     * 2.接收方是手机号
     * @see net.zoneland.ums.biz.msg.process.service.ResolveMsgService#getCorrectUmsMsgOutList()
     */
    private List<UmsMsgOut> getCorrectUmsMsgOutList(PrimitiveMessage primitiveMessage,
                                                    HashSet<String> correctRecvIdList) {
        List<UmsMsgOut> umsMsgOutList = new ArrayList<UmsMsgOut>();
        if (correctRecvIdList == null || correctRecvIdList.size() == 0) {
            return Collections.emptyList();
        }
        getUmsMsgOutList(primitiveMessage, correctRecvIdList, MsgInfoStatusEnum.ready.getValue(),
            umsMsgOutList);
        return umsMsgOutList;
    }

    private List<UmsMsgOut> getErrorUmsMsgOutList(PrimitiveMessage primitiveMessage,
                                                  HashSet<String> errortRecvIdList) {
        HashSet<String> userIdList = errortRecvIdList;
        List<UmsMsgOut> umsMsgOutList = new ArrayList<UmsMsgOut>();
        getUmsMsgOutList(primitiveMessage, userIdList, MsgInfoStatusEnum.error.getValue(),
            umsMsgOutList);
        return umsMsgOutList;
    }

    private List<UmsMsgOut> getUmsMsgOutList(PrimitiveMessage primitiveMessage,
                                             HashSet<String> userIdList, String status,
                                             List<UmsMsgOut> umsMsgOutList) {
        Iterator<String> it = userIdList.iterator();
        while (it.hasNext()) {
            UmsMsgOut umsMsgOut = new UmsMsgOut();
            umsMsgOut = primitiveMessage.cloneUmsMsgOut();
            String uuid = UUID.randomUUID().toString();
            umsMsgOut.setId(uuid);
            umsMsgOut.setSerialNo(String.valueOf(SerialNoHelper.nextSerial()));
            umsMsgOut.setRecvId(it.next());
            umsMsgOut.setStatus(status);
            umsMsgOut.setGmtCreated(new Date());
            umsMsgOut.setGmtModified(new Date());
            umsMsgOutList.add(umsMsgOut);
        }
        return umsMsgOutList;
    }

    public ResolveUserService getResolveUserService() {
        return resolveUserService;
    }

    public void setResolveUserService(ResolveUserService resolveUserService) {
        this.resolveUserService = resolveUserService;
    }

}
