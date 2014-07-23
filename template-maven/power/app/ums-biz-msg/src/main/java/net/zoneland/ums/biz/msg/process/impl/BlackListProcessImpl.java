/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zoneland.ums.biz.msg.process.BlackListProcess;
import net.zoneland.ums.common.dal.UmsBlackListMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;

import org.apache.log4j.Logger;

/**
 *
 * @author wangyong
 * @version $Id: BlackProcessImpl.java, v 0.1 2012-8-15 下午10:14:06 wangyong Exp $
 */
public class BlackListProcessImpl implements BlackListProcess {

    private final static Logger logger = Logger.getLogger(BlackListProcessImpl.class);

    private UmsBlackListMapper  umsBlackListMapper;

    /**
     * @see net.zoneland.ums.biz.msg.process.BlackListProcess#process(java.util.List)
     */
    public List<UmsMsgOut> process(List<UmsMsgOut> umsMsgOutList) {
        if (logger.isInfoEnabled()) {
            logger.info("黑名单检查！");
        }
        if (umsMsgOutList == null) {
            return Collections.emptyList();
        }
        List<UmsMsgOut> blackUmsMsgOutList = new ArrayList<UmsMsgOut>();
        int size = umsMsgOutList.size();
        int j = 0;
        UmsMsgOut umsMsgOut = null;
        for (int i = 0; i < size; i++, j++) {
            int count = umsBlackListMapper.getCountByUserId(umsMsgOutList.get(j).getRecvId());
            if (count > 0) {
                umsMsgOut = umsMsgOutList.remove(j);
                j--;
                umsMsgOut.setStatus(MsgInfoStatusEnum.refuse.getValue());
                blackUmsMsgOutList.add(umsMsgOut);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("黑名单数量:" + blackUmsMsgOutList.size());
        }
        return blackUmsMsgOutList;
    }

    public UmsBlackListMapper getUmsBlackListMapper() {
        return umsBlackListMapper;
    }

    public void setUmsBlackListMapper(UmsBlackListMapper umsBlackListMapper) {
        this.umsBlackListMapper = umsBlackListMapper;
    }

}
