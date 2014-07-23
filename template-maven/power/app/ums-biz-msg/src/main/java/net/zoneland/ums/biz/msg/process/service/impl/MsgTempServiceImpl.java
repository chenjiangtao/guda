/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service.impl;

import java.util.Date;

import net.zoneland.ums.biz.msg.process.service.MsgTempService;
import net.zoneland.ums.common.dal.UmsMsgTemplateMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 
 * @author wangyong
 * @version $Id: validateMsgTempImpl.java, v 0.1 2012-12-28 下午4:09:23 wangyong Exp $
 */
public class MsgTempServiceImpl implements MsgTempService {

    private final static Logger  logger = Logger.getLogger(MsgTempServiceImpl.class);

    @Autowired
    private UmsMsgTemplateMapper umsMsgTemplateMapper;

    /**
     * 定时捞起的时候进行模版校验！
     * @see net.zoneland.ums.biz.msg.process.service.MsgTempService#validateMsgTemp(net.zoneland.ums.common.dal.dataobject.UmsMsgOut)
     */
    public boolean validateMsgTemp(UmsMsgOut umsMsgOut) {

        //如果没有模版限制的话就是就不在做校验可以发送
        if (!StringUtils.hasText(umsMsgOut.getTemplateId())) {
            return true;
        }
        UmsMsgTemplate template = umsMsgTemplateMapper.findByTempId(umsMsgOut.getTemplateId());
        //如果没有找到相应模版的话就是就不在做校验可以发送
        if (template == null) {
            logger.info("无法找到对应的模版:[" + umsMsgOut.getTemplateId() + "]");
            return true;
        }
        //如果模版不在模版的有效期内的话认为可以直接发送
        if (!DateHelper.withIn(template.getStartTime(), template.getEndTime())) {
            logger.info("模版不在有效期内，忽略该模版:[" + umsMsgOut.getTemplateId() + "]");
            return true;
        }
        if (template.getPriority() != null) {
            umsMsgOut.setPriority(template.getPriority());
        }
        Date temp = DateHelper.getSendDate(umsMsgOut.getSendTime(), template.getValidTimeScope());
        //不在模版定义的时间之内的话，认为校验不通过
        if (temp != null) {
            //如果下次发送的时间在有效时间范围之外，我们不会再发送该短信，状态设为消息过期
            logger.info("重新设定发送时间：" + temp);
            if (umsMsgOut.getValidTime() != null && temp.after(umsMsgOut.getValidTime())) {
                umsMsgOut.setSendTime(temp);
                umsMsgOut.setStatus(MsgInfoStatusEnum.expire.getValue());
                umsMsgOut.setErrorMsg(MsgInfoStatusEnum.expire.getDescription());
                umsMsgOut.setGmtModified(new Date());
                return false;
            }
            umsMsgOut.setSendTime(temp);
            umsMsgOut.setStatus(MsgInfoStatusEnum.wait.getValue());
            umsMsgOut.setGmtModified(new Date());
            return false;
        }
        return true;
    }
}
